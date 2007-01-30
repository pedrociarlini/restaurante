//    Tina POS is a point of sales application designed for touch screens.
//    Copyright (C) 2005 Adrian Romero Corchado.
//    http://sourceforge.net/projects/tinapos
//
//    This program is free software; you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation; either version 2 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program; if not, write to the Free Software
//    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

package net.adrianromero.tpv.inventory;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import net.adrianromero.basic.BasicException;
import net.adrianromero.beans.*;
import net.adrianromero.data.gui.*;
import net.adrianromero.data.loader.*;
import net.adrianromero.format.Formats;
import net.adrianromero.tpv.forms.*;
import net.adrianromero.tpv.panels.ComboBoxModelKey;
import net.adrianromero.tpv.panels.JCatalog;
import net.adrianromero.tpv.printer.TicketParser;
import net.adrianromero.tpv.printer.TicketPrinterException;
import net.adrianromero.tpv.scanpal2.DeviceScanner;
import net.adrianromero.tpv.scanpal2.ProductDownloaded;
import net.adrianromero.tpv.ticket.ProductInfoExt;
import org.apache.velocity.VelocityContext;

public class StockManagement extends JPanel implements JPanelView {
    
    private final static int NUMBER_INPUTZERO = 0;
    private final static int NUMBER_INPUTZERODEC = 1;
    private final static int NUMBER_INPUTINT = 2;
    private final static int NUMBER_INPUTDEC = 3; 
    private final static int NUMBER_PORZERO = 4; 
    private final static int NUMBER_PORZERODEC = 5; 
    private final static int NUMBER_PORINT = 6; 
    private final static int NUMBER_PORDEC = 7; 
    
    protected AppView m_App;
    private TicketParser m_TTP;

    private JCatalog m_cat;
    private ComboBoxModelKey m_ReasonModel;
    
    private SentenceList m_sentlocations;
    private IRenderString m_LocationsRender;
    private ComboBoxValModel m_LocationsModel;   
    private ComboBoxValModel m_LocationsModelDes;   
    
    // Estas tres variables forman el estado...
    private int m_iNumberStatus;
//    private int m_iNumberStatusInput;
//    private int m_iNumberStatusPor;
    private StringBuffer m_sBarcode;    
    
    private JInventoryLines m_invlines;
    
    /** Creates new form StockManagement */
    public StockManagement(AppView oApp) {
        m_App = oApp;
        m_TTP = new TicketParser(m_App.getDeviceTicket(), m_App.lookupDataLogic(DataLogicSystem.class));

        initComponents();
        
        btnDownloadProducts.setEnabled(m_App.getDeviceScanner() != null);

        
        // El modelo de locales
        TableDefinition tlocations = m_App.lookupDataLogic(SentenceContainer.class).getTableLocations();
        m_sentlocations = tlocations.getListSentence();
        m_LocationsRender = tlocations.getRenderStringBasic();

        m_LocationsModel = new ComboBoxValModel(tlocations.getKeyGetterBasic());
        m_jLocation.setRenderer(new ListCellRendererBasic(m_LocationsRender));
        
        m_LocationsModelDes = new ComboBoxValModel(tlocations.getKeyGetterBasic());
        m_jLocationDes.setRenderer(new ListCellRendererBasic(m_LocationsRender));
        
        m_ReasonModel = new ComboBoxModelKey();
        m_ReasonModel.add(new Integer(+1), AppLocal.getIntString("stock.in.purchase"));
        m_ReasonModel.add(new Integer(+2), AppLocal.getIntString("stock.in.refund"));
        m_ReasonModel.add(new Integer(+4), AppLocal.getIntString("stock.in.movement"));
        m_ReasonModel.add(new Integer(-1), AppLocal.getIntString("stock.out.sale"));
        m_ReasonModel.add(new Integer(-2), AppLocal.getIntString("stock.out.refund"));
        m_ReasonModel.add(new Integer(-3), AppLocal.getIntString("stock.out.break"));        
        m_ReasonModel.add(new Integer(-4), AppLocal.getIntString("stock.out.movement"));        
        m_ReasonModel.add(new Integer(1000), "Traspaso");
        
        m_jreason.setModel(m_ReasonModel);
        
        m_cat = new JCatalog(m_App);
        m_cat.addActionListener(new CatalogListener());
        add(m_cat, BorderLayout.SOUTH);
        
        // Las líneas de inventario
        m_invlines = new JInventoryLines();
        jPanel5.add(m_invlines, BorderLayout.CENTER);
    }
     
    public String getTitle() {
        return AppLocal.getIntString("Menu.StockMovement");
    }         
    
    public JComponent getComponent() {
        return this;
    }

    public void activate() {
        m_cat.loadCatalog();
        try {
            java.util.List l = m_sentlocations.list();
            m_LocationsModel.refresh(l);
            m_jLocation.setModel(m_LocationsModel); // para que lo refresque
            m_LocationsModelDes.refresh(l);
            m_jLocationDes.setModel(m_LocationsModelDes); // para que lo refresque
        } catch (BasicException eD) {
            // no hay validacion
            eD.printStackTrace();
        }   
        
        stateToInsert();
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                m_jcodebar.requestFocus();
            }
        });        
    }   
    
    
    public void stateToInsert() {
        // Inicializamos las cajas de texto
        m_jdate.setText(Formats.TIMESTAMP.formatValue(DateUtils.getTodayMinutes()));
        m_ReasonModel.setSelectedKey(new Integer(1000)); // Antes +1, Compras.
        m_LocationsModel.setSelectedKey(m_App.getInventoryLocation());     
        m_LocationsModelDes.setSelectedKey(m_App.getInventoryLocation());         
        m_invlines.clear();
        m_jcodebar.setText(null);
    }
    
    public boolean deactivate() {

        if (m_invlines.getCount() > 0) {
            int res = JOptionPane.showConfirmDialog(this, LocalRes.getIntString("message.wannasave"), LocalRes.getIntString("title.editor"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (res == JOptionPane.YES_OPTION) {
                saveData();
                return true;
            } else if (res == JOptionPane.NO_OPTION) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }        
    }  
    
    private void stateToZero(){
//        m_jPor.setText("");
//        m_jPrice.setText("");
        m_sBarcode = new StringBuffer();

        m_iNumberStatus = NUMBER_INPUTZERO;
//        m_iNumberStatusInput = NUMBERZERO;
//        m_iNumberStatusPor = NUMBERZERO;
    }    

    private void addLine(ProductInfoExt oProduct, double dpor, double dprice) {
        m_invlines.addLine(new InventoryLine(oProduct, dpor, dprice));
    }
    
    private void deleteLine(int index) {
        if (index < 0){
            Toolkit.getDefaultToolkit().beep(); // No hay ninguna seleccionada
        } else {
            m_invlines.deleteLine(index);          
        }        
    }
    
    private void incProduct(double dPor, ProductInfoExt prod) {
        // precondicion: prod != null
        addLine(prod, dPor, prod.getPriceBuy());    
    }
    
    private void incProductByCode(String sCode) {
        incProductByCode(sCode, 1.0);
    }
    private void incProductByCode(String sCode, double dQuantity) {
    // precondicion: sCode != null
        
        try {
            ProductInfoExt oProduct = (ProductInfoExt) m_App.lookupDataLogic(SentenceContainer.class).getProductInfo().find(sCode);
            if (oProduct == null) {                  
                Toolkit.getDefaultToolkit().beep();                   
                stateToZero();
            } else {
                // Se anade directamente una unidad con el precio y todo
                incProduct(dQuantity, oProduct);
            }
        } catch (BasicException eData) {
            stateToZero();           
            MessageInf msg = new MessageInf(eData);
            msg.show(this);            
        }
    }
    
    private void addUnits(double dUnits) {
        int i  = m_invlines.getSelectedRow();
        if (i >= 0 ) {
            InventoryLine inv = m_invlines.getLine(i);
            double dunits = inv.getMultiply() + dUnits;
            if (dunits == 0.0) {
                deleteLine(i);
            } else {            
                inv.setMultiply(inv.getMultiply() + dUnits);
                m_invlines.setLine(i, inv);
            }
        }
    }
    
    private void stateTransition(char cTrans) {
        
        if (cTrans == '\u007f') { 
            m_jcodebar.setText(null);
        } else if (cTrans == '+') {
            if (m_jcodebar.getText() == null || m_jcodebar.getText().equals("")) {
                // anadimos una unidad 
                addUnits(1.0);
            } else {
                addUnits(Double.parseDouble(m_jcodebar.getText()));
                m_jcodebar.setText(null);
            }
        } else if (cTrans == '-') {
            if (m_jcodebar.getText() == null || m_jcodebar.getText().equals("")) {
                // anadimos una unidad 
                addUnits(-1.0);
            } else {
                addUnits(-Double.parseDouble(m_jcodebar.getText()));
                m_jcodebar.setText(null);                
            }
        } else if (cTrans == ' ' || cTrans == '=') {
            if (m_invlines.getCount() == 0) {
                // No podemos grabar, no hay ningun registro.
                Toolkit.getDefaultToolkit().beep();
            } else {
                saveData();
            }
        } else {
            m_jcodebar.setText(m_jcodebar.getText() + cTrans);
        }
    }
    
    private void saveData() {
        try {

            Date d = (Date) Formats.TIMESTAMP.parseValue(m_jdate.getText());
            Integer iReason = (Integer) m_ReasonModel.getSelectedKey();

            if (iReason.equals(1000)) {
                // Es una doble entrada
                saveData(new InventoryRecord(
                        d, new Integer(-4), AppLocal.getIntString("stock.out.movement"),
                        (Integer) m_LocationsModel.getSelectedKey(),
                        m_LocationsRender.getRenderString(m_LocationsModel.getSelectedItem()),
                        m_invlines.getLines()
                    ));
                saveData(new InventoryRecord(
                        d, new Integer(+4), AppLocal.getIntString("stock.in.movement"),
                        (Integer) m_LocationsModelDes.getSelectedKey(),
                        m_LocationsRender.getRenderString(m_LocationsModelDes.getSelectedItem()),
                        m_invlines.getLines()
                    ));                
            } else {  
                // Es un movimiento
                saveData(new InventoryRecord(
                        d, iReason, m_ReasonModel.getSelectedValue().toString(),
                        (Integer) m_LocationsModel.getSelectedKey(),
                        m_LocationsRender.getRenderString(m_LocationsModel.getSelectedItem()),
                        m_invlines.getLines()
                    ));
            }
            
            stateToInsert();  
        } catch (BasicException eData) {
            MessageInf msg = new MessageInf(MessageInf.SGN_NOTICE, "No se ha podido guardar la informacion de movimiento de inventario", eData);
            msg.show(this);
        }             
    }
        
    private void saveData(InventoryRecord rec) throws BasicException {
        
        // A grabar.
        SentenceExec sent = m_App.lookupDataLogic(SentenceContainer.class).getStockDiaryInsert();
        
        for (int i = 0; i < m_invlines.getCount(); i++) {
            Object[] diary = new Object[7];
            diary[0] = m_App.lookupDataLogic(SentenceContainer.class).getNextStockDiary().find(); // si casca que suba la excepcion hacia arriba.
            diary[1] = rec.getDate();
            diary[2] = rec.getReason();
            diary[3] = rec.getLocation();

            InventoryLine inv = rec.getLines().get(i);
            diary[4] = inv.getProductReference();
            diary[5] = samesignum(inv.getMultiply(), rec.getReason());
            diary[6] = inv.getPrice();
            sent.exec(diary);
        }

        // si se ha grabado se imprime, si no, no.
        printTicket(rec);   
    }
    
    private void printTicket(InventoryRecord invrec) {

        String sresource = m_App.lookupDataLogic(DataLogicSystem.class).getResourceAsXML("Printer.Inventory");
        if (sresource == null) {
            MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotprintticket"));
            msg.show(this);
        } else {
            VelocityContext vc = new VelocityContext();
            vc.put("inventoryrecord", invrec);
            try {
                m_TTP.printTicket(m_App.evaluateTemplate(sresource, vc));
            } catch (TicketPrinterException eTP) {
                MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotprintticket"), eTP);
                msg.show(this);
            }
        }
    }
    
    private Double samesignum(Double d, Integer i) {
        
        if (d == null || i == null) {
            return d;
        } else if ((i.intValue() > 0 && d.doubleValue() < 0.0) ||
            (i.intValue() < 0 && d.doubleValue() > 0.0)) {
            return new Double(-d.doubleValue());
        } else {
            return d;
        }            
    }    
    
    private class CatalogListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            incProduct(1.0, (ProductInfoExt) e.getSource());
        }  
    }  
  
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jNumberKeys = new net.adrianromero.beans.JNumberKeys();
        jPanel4 = new javax.swing.JPanel();
        m_jcodebar = new javax.swing.JTextField();
        m_jEnter = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        btnDownloadProducts = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        m_jdate = new javax.swing.JTextField();
        m_jbtndate = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        m_jreason = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        m_jLocation = new javax.swing.JComboBox();
        m_jDelete = new javax.swing.JButton();
        m_jUp = new javax.swing.JButton();
        m_jDown = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        m_jLocationDes = new javax.swing.JComboBox();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        jNumberKeys.addJNumberEventListener(new net.adrianromero.beans.JNumberEventListener() {
            public void keyPerformed(net.adrianromero.beans.JNumberEvent evt) {
                jNumberKeysKeyPerformed(evt);
            }
        });

        jPanel2.add(jNumberKeys);

        m_jcodebar.setPreferredSize(new java.awt.Dimension(110, 19));
        m_jcodebar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jcodebarActionPerformed(evt);
            }
        });

        jPanel4.add(m_jcodebar);

        m_jEnter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/adrianromero/images/apply.png")));
        m_jEnter.setFocusPainted(false);
        m_jEnter.setFocusable(false);
        m_jEnter.setRequestFocusEnabled(false);
        m_jEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jEnterActionPerformed(evt);
            }
        });

        jPanel4.add(m_jEnter);

        jPanel2.add(jPanel4);

        btnDownloadProducts.setText("ScanPal");
        btnDownloadProducts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownloadProductsActionPerformed(evt);
            }
        });

        jPanel6.add(btnDownloadProducts);

        jPanel2.add(jPanel6);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        add(jPanel1, java.awt.BorderLayout.EAST);

        jPanel3.setLayout(null);

        jLabel1.setText(AppLocal.getIntString("label.stockdate"));
        jPanel3.add(jLabel1);
        jLabel1.setBounds(10, 30, 150, 14);

        jPanel3.add(m_jdate);
        m_jdate.setBounds(160, 30, 200, 19);

        m_jbtndate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/adrianromero/images/date.png")));
        m_jbtndate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jbtndateActionPerformed(evt);
            }
        });

        jPanel3.add(m_jbtndate);
        m_jbtndate.setBounds(370, 30, 40, 25);

        jLabel2.setText(AppLocal.getIntString("label.stockreason"));
        jPanel3.add(jLabel2);
        jLabel2.setBounds(10, 60, 150, 14);

        m_jreason.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jreasonActionPerformed(evt);
            }
        });

        jPanel3.add(m_jreason);
        m_jreason.setBounds(160, 60, 200, 20);

        jLabel8.setText(AppLocal.getIntString("label.warehouse"));
        jPanel3.add(jLabel8);
        jLabel8.setBounds(10, 90, 150, 14);

        jPanel3.add(m_jLocation);
        m_jLocation.setBounds(160, 90, 200, 20);

        m_jDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/adrianromero/images/locationbar_erase.png")));
        m_jDelete.setFocusPainted(false);
        m_jDelete.setFocusable(false);
        m_jDelete.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jDelete.setRequestFocusEnabled(false);
        m_jDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jDeleteActionPerformed(evt);
            }
        });

        jPanel3.add(m_jDelete);
        m_jDelete.setBounds(430, 260, 54, 42);

        m_jUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/adrianromero/images/1uparrow22.png")));
        m_jUp.setFocusPainted(false);
        m_jUp.setFocusable(false);
        m_jUp.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jUp.setRequestFocusEnabled(false);
        m_jUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jUpActionPerformed(evt);
            }
        });

        jPanel3.add(m_jUp);
        m_jUp.setBounds(430, 160, 54, 42);

        m_jDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/adrianromero/images/1downarrow22.png")));
        m_jDown.setFocusPainted(false);
        m_jDown.setFocusable(false);
        m_jDown.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jDown.setRequestFocusEnabled(false);
        m_jDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jDownActionPerformed(evt);
            }
        });

        jPanel3.add(m_jDown);
        m_jDown.setBounds(430, 210, 54, 42);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel3.add(jPanel5);
        jPanel5.setBounds(10, 160, 410, 190);

        jLabel9.setText(AppLocal.getIntString("label.warehouse"));
        jPanel3.add(jLabel9);
        jLabel9.setBounds(10, 120, 150, 14);

        jPanel3.add(m_jLocationDes);
        m_jLocationDes.setBounds(160, 120, 200, 20);

        add(jPanel3, java.awt.BorderLayout.CENTER);

    }// </editor-fold>//GEN-END:initComponents

    private void btnDownloadProductsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownloadProductsActionPerformed

        // Ejecutamos la descarga...
        DeviceScanner s = m_App.getDeviceScanner();
        try {
            s.connectDevice();
            s.startDownloadProduct();
            
            ProductDownloaded p = s.recieveProduct();
            while (p != null) {
                incProductByCode(p.getCode(), p.getQuantity());
                p = s.recieveProduct();
            }
            // MessageInf msg = new MessageInf(MessageInf.SGN_SUCCESS, "Se ha subido con exito la lista de productos al ScanPal.");
            // msg.show(this);            
        } catch (BasicException e) {
            MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.scannerfail2"), e);
            msg.show(this);            
        } finally {
            s.disconnectDevice();
        }        
        
    }//GEN-LAST:event_btnDownloadProductsActionPerformed

    private void m_jreasonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jreasonActionPerformed

        m_jLocationDes.setEnabled(new Integer(1000).equals(m_ReasonModel.getSelectedKey())); 
        
    }//GEN-LAST:event_m_jreasonActionPerformed

    private void m_jDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jDownActionPerformed
        
        m_invlines.goDown();
        
    }//GEN-LAST:event_m_jDownActionPerformed

    private void m_jUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jUpActionPerformed

        m_invlines.goUp();
        
    }//GEN-LAST:event_m_jUpActionPerformed

    private void m_jDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jDeleteActionPerformed
        
        deleteLine(m_invlines.getSelectedRow());

    }//GEN-LAST:event_m_jDeleteActionPerformed

    private void m_jEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jEnterActionPerformed
        
        incProductByCode(m_jcodebar.getText());
        m_jcodebar.setText(null);
        
    }//GEN-LAST:event_m_jEnterActionPerformed

    private void m_jcodebarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jcodebarActionPerformed
        
        incProductByCode(m_jcodebar.getText());
        m_jcodebar.setText(null);
        
    }//GEN-LAST:event_m_jcodebarActionPerformed

    private void m_jbtndateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jbtndateActionPerformed
        
        Date date;
        try {
            date = (Date) Formats.TIMESTAMP.parseValue(m_jdate.getText());
        } catch (BasicException e) {
            date = null;
        }
        date = JCalendarDialog.showCalendarTime(this, date);
        if (date != null) {
            m_jdate.setText(Formats.TIMESTAMP.formatValue(date));
        }
    }//GEN-LAST:event_m_jbtndateActionPerformed

    private void jNumberKeysKeyPerformed(net.adrianromero.beans.JNumberEvent evt) {//GEN-FIRST:event_jNumberKeysKeyPerformed
        
        stateTransition(evt.getKey());
        
    }//GEN-LAST:event_jNumberKeysKeyPerformed


    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDownloadProducts;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private net.adrianromero.beans.JNumberKeys jNumberKeys;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JButton m_jDelete;
    private javax.swing.JButton m_jDown;
    private javax.swing.JButton m_jEnter;
    private javax.swing.JComboBox m_jLocation;
    private javax.swing.JComboBox m_jLocationDes;
    private javax.swing.JButton m_jUp;
    private javax.swing.JButton m_jbtndate;
    private javax.swing.JTextField m_jcodebar;
    private javax.swing.JTextField m_jdate;
    private javax.swing.JComboBox m_jreason;
    // End of variables declaration//GEN-END:variables
    
}
