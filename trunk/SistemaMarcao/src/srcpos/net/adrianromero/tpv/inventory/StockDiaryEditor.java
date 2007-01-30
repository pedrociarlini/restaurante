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

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import net.adrianromero.beans.DateUtils;
import net.adrianromero.beans.JCalendarDialog;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.gui.ComboBoxValModel;
import net.adrianromero.data.gui.ListCellRendererBasic;
import net.adrianromero.data.gui.MessageInf;
import net.adrianromero.data.loader.SentenceList;
import net.adrianromero.data.loader.TableDefinition;
import net.adrianromero.format.Formats;
import net.adrianromero.data.user.DirtyManager;
import net.adrianromero.data.user.EditorRecord;
import net.adrianromero.tpv.forms.AppLocal;
import net.adrianromero.tpv.forms.AppView;
import net.adrianromero.tpv.forms.SentenceContainer;
import net.adrianromero.tpv.panels.ComboBoxModelKey;
import net.adrianromero.tpv.panels.JCatalog;
import net.adrianromero.tpv.panels.JProductFinder;
import net.adrianromero.tpv.ticket.ProductInfoExt;

public class StockDiaryEditor extends javax.swing.JPanel implements EditorRecord {
    
    private DirtyManager m_Dirty = new DirtyManager();
    private ComboBoxModelKey m_ReasonModel;
    private JCatalog m_cat;
    
    private Integer m_iID;
    private ProductInfoExt m_product;
    
    private SentenceList m_sentlocations;
    private ComboBoxValModel m_LocationsModel;    

    private AppView m_App;
    
    /** Creates new form StockDiaryEditor */
    public StockDiaryEditor(AppView oApp) {
        
        m_App = oApp;
        
        initComponents();      
        
        m_cat = new JCatalog(m_App);
        m_cat.addActionListener(new CatalogListener());
        m_jcatcontainer.add(m_cat, BorderLayout.CENTER);

        // El modelo de locales
        TableDefinition tlocations = m_App.lookupDataLogic(SentenceContainer.class).getTableLocations();
        m_sentlocations = tlocations.getListSentence();
        m_LocationsModel = new ComboBoxValModel(tlocations.getKeyGetterBasic());
        m_jLocation.setRenderer(new ListCellRendererBasic(tlocations.getRenderStringBasic()));
        
        m_ReasonModel = new ComboBoxModelKey();
        m_ReasonModel.add(new Integer(+1), AppLocal.getIntString("stock.in.purchase"));
        m_ReasonModel.add(new Integer(+2), AppLocal.getIntString("stock.in.refund"));
        m_ReasonModel.add(new Integer(+4), AppLocal.getIntString("stock.in.movement"));
        m_ReasonModel.add(new Integer(-1), AppLocal.getIntString("stock.out.sale"));
        m_ReasonModel.add(new Integer(-2), AppLocal.getIntString("stock.out.refund"));
        m_ReasonModel.add(new Integer(-3), AppLocal.getIntString("stock.out.break"));        
        m_ReasonModel.add(new Integer(-4), AppLocal.getIntString("stock.out.movement"));               
        
        m_jreason.setModel(m_ReasonModel);
        
        m_jdate.getDocument().addDocumentListener(m_Dirty);
        m_jreason.addActionListener(m_Dirty);
        m_jLocation.addActionListener(m_Dirty);
        m_jproduct.getDocument().addDocumentListener(m_Dirty);
        m_junits.getDocument().addDocumentListener(m_Dirty);
        m_jprice.getDocument().addDocumentListener(m_Dirty);
         
        writeValueEOF();
    }
    
    public void activate() {
        m_cat.loadCatalog();
        
        try {
            m_LocationsModel.refresh(m_sentlocations.list());
            m_jLocation.setModel(m_LocationsModel); // para que lo refresque
        } catch (BasicException eD) {
            // no hay validacion
            eD.printStackTrace();
        }        
    }
    
    public void writeValueEOF() {
        m_iID = null;
        m_jdate.setText(null);
        m_ReasonModel.setSelectedKey(null);
        m_product = null;
        m_jreference.setText(null);
        m_jcodebar.setText(null);
        m_LocationsModel.setSelectedKey(m_App.getInventoryLocation());
        m_jproduct.setText(null);
        m_junits.setText(null);
        m_jprice.setText(null);
        m_jdate.setEnabled(false);
        m_jbtndate.setEnabled(false);
        m_jreason.setEnabled(false);
        m_jreference.setEnabled(false);
        m_jEnter1.setEnabled(false);
        m_jcodebar.setEnabled(false);
        m_jEnter.setEnabled(false);
        m_jLocation.setEnabled(false);
        m_jproduct.setEnabled(false);
        m_jbtnproduct.setEnabled(false);
        m_junits.setEnabled(false);
        m_jprice.setEnabled(false);
        m_cat.setEnabled(false);
    }
    
    public void writeValueInsert() {
        m_iID = null;
        m_jdate.setText(Formats.TIMESTAMP.formatValue(DateUtils.getTodayMinutes()));
        m_ReasonModel.setSelectedKey(new Integer(+1));
        m_product = null;
        m_jreference.setText(null);
        m_jcodebar.setText(null);
        m_LocationsModel.setSelectedKey(m_App.getInventoryLocation());
        m_jproduct.setText(null);
        m_jcodebar.setText(null);
        m_junits.setText(null);
        m_jprice.setText(null);
        m_jdate.setEnabled(true);
        m_jbtndate.setEnabled(true);
        m_jreason.setEnabled(true);
        m_jreference.setEnabled(true);
        m_jEnter1.setEnabled(true);
        m_jcodebar.setEnabled(true);
        m_jEnter.setEnabled(true);
        m_jLocation.setEnabled(true);
        m_jproduct.setEnabled(true);
        m_jbtnproduct.setEnabled(true);
        m_junits.setEnabled(true);
        m_jprice.setEnabled(true);   
        m_cat.setEnabled(true);
    }

    public void writeValueDelete(Object value) {
        Object[] diary = (Object[]) value;
        m_iID = (Integer) diary[0];
        m_jdate.setText(Formats.TIMESTAMP.formatValue(diary[1]));
        m_ReasonModel.setSelectedKey(diary[2]);
        m_LocationsModel.setSelectedKey(diary[3]);
        m_product = getProductByReference((String) diary[4]);
        m_jreference.setText(m_product.getReference());
        m_jcodebar.setText(m_product.getCode());
        m_jproduct.setText(m_product.toString());
        m_junits.setText(Formats.DOUBLE.formatValue(signum((Double)diary[5], (Integer) diary[2])));
        m_jprice.setText(Formats.CURRENCY.formatValue(diary[6]));
        m_jdate.setEnabled(false);
        m_jbtndate.setEnabled(false);
        m_jreason.setEnabled(false);
        m_jreference.setEnabled(false);
        m_jEnter1.setEnabled(false);
        m_jcodebar.setEnabled(false);
        m_jEnter.setEnabled(false);
        m_jLocation.setEnabled(false);
        m_jproduct.setEnabled(false);
        m_jbtnproduct.setEnabled(false);
        m_junits.setEnabled(false);
        m_jprice.setEnabled(false);   
        m_cat.setEnabled(false);
    }
    
    public void writeValueEdit(Object value) {
        Object[] diary = (Object[]) value;
        m_iID = (Integer) diary[0];
        m_jdate.setText(Formats.TIMESTAMP.formatValue(diary[1]));
        m_ReasonModel.setSelectedKey(diary[2]);
        m_LocationsModel.setSelectedKey(diary[3]);
        m_product = getProductByReference((String) diary[4]);
        m_jreference.setText(m_product.getReference());
        m_jcodebar.setText(m_product.getCode());
        m_jproduct.setText(m_product.toString());
        m_junits.setText(Formats.DOUBLE.formatValue(signum((Double)diary[5], (Integer) diary[2])));
        m_jprice.setText(Formats.CURRENCY.formatValue(diary[6]));
        m_jdate.setEnabled(false);
        m_jbtndate.setEnabled(false);
        m_jreason.setEnabled(false);
        m_jreference.setEnabled(false);
        m_jEnter1.setEnabled(false);
        m_jcodebar.setEnabled(false);
        m_jEnter.setEnabled(false);
        m_jLocation.setEnabled(false);
        m_jproduct.setEnabled(false);
        m_jbtnproduct.setEnabled(false);
        m_junits.setEnabled(false);
        m_jprice.setEnabled(false);  
        m_cat.setEnabled(false);
    }
    
    public Object createValue() throws BasicException {
        Object[] diary = new Object[7];
        diary[0] = m_iID == null ? m_App.lookupDataLogic(SentenceContainer.class).getNextStockDiary().find() : m_iID; // si casca que suba la excepcion hacia arriba.
        diary[1] = Formats.TIMESTAMP.parseValue(m_jdate.getText());
        diary[2] = m_ReasonModel.getSelectedKey();
        diary[3] = m_LocationsModel.getSelectedKey();
        diary[4] = (m_product == null) ? null : m_product.getReference();
        diary[5] = samesignum((Double) Formats.DOUBLE.parseValue(m_junits.getText()), (Integer) diary[2]);
        diary[6] = Formats.CURRENCY.parseValue(m_jprice.getText());
        return diary;
    }
    
    public DirtyManager getDirtyManager() {
        return m_Dirty;
    }
 
    private ProductInfoExt getProductByReference(String sRef)  {
        
        try {
            return (ProductInfoExt) m_App.lookupDataLogic(SentenceContainer.class).getProductInfo2().find(sRef);
        } catch (BasicException e) {
            return null;
        }
    } 
    
    private Double signum(Double d, Integer i) {
        if (d == null || i == null) {
            return d;
        } else if (i.intValue() < 0) {
            return new Double(-d.doubleValue());
        } else {
            return d;
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
    
    private void assignProduct(ProductInfoExt prod) {
        
        if (m_jproduct.isEnabled()) {
            if (prod == null) {
                m_product = null;
                m_jproduct.setText(null);
                m_jcodebar.setText(null);
                m_jreference.setText(null);
            } else {
                m_product = prod;
                m_jproduct.setText(m_product.toString());
                m_jcodebar.setText(m_product.getCode());
                m_jreference.setText(m_product.getReference());

                // calculo el precio sugerido para la entrada.
                Integer i = (Integer)  m_ReasonModel.getSelectedKey();
                Double dPrice = null;
                if (i != null) {
                    switch (i.intValue()){
                        case +1: 
                        case -2:
                        case -3:
                            dPrice = new Double(m_product.getPriceBuy());
                            break;
                        case -1: // Venta
                        case +2: // Devolucion de venta
                            dPrice = new Double(m_product.getPriceSell());
                            break;
                    }
                }

                m_jprice.setText(Formats.CURRENCY.formatValue(dPrice));
            }
        }
    }
    
    private void assignProductByCode() {
        try {
            ProductInfoExt oProduct = (ProductInfoExt) m_App.lookupDataLogic(SentenceContainer.class).getProductInfo().find(m_jcodebar.getText());
            if (oProduct == null) {       
                assignProduct(null);
                Toolkit.getDefaultToolkit().beep();                   
            } else {
                // Se anade directamente una unidad con el precio y todo
                assignProduct(oProduct);
            }
        } catch (BasicException eData) {        
            assignProduct(null);
            MessageInf msg = new MessageInf(eData);
            msg.show(this);            
        }        
    }
    
    private void assignProductByReference() {
        try {
            ProductInfoExt oProduct = (ProductInfoExt) m_App.lookupDataLogic(SentenceContainer.class).getProductInfo2().find(m_jreference.getText());
            if (oProduct == null) {       
                assignProduct(null);
                Toolkit.getDefaultToolkit().beep();                   
            } else {
                // Se anade directamente una unidad con el precio y todo
                assignProduct(oProduct);
            }
        } catch (BasicException eData) {        
            assignProduct(null);
            MessageInf msg = new MessageInf(eData);
            msg.show(this);            
        }        
    }
    
    private class CatalogListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            assignProduct((ProductInfoExt) e.getSource());
        }  
    }    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        m_jdate = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        m_jproduct = new javax.swing.JTextField();
        m_jreason = new javax.swing.JComboBox();
        m_jbtnproduct = new javax.swing.JButton();
        m_jbtndate = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        m_junits = new javax.swing.JTextField();
        m_jprice = new javax.swing.JTextField();
        m_jcatcontainer = new javax.swing.JPanel();
        m_jcodebar = new javax.swing.JTextField();
        m_jEnter = new javax.swing.JButton();
        m_jreference = new javax.swing.JTextField();
        m_jEnter1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        m_jLocation = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();

        setLayout(null);

        jLabel1.setText(AppLocal.getIntString("label.stockdate"));
        add(jLabel1);
        jLabel1.setBounds(10, 30, 150, 14);

        add(m_jdate);
        m_jdate.setBounds(160, 30, 200, 19);

        jLabel2.setText(AppLocal.getIntString("label.stockreason"));
        add(jLabel2);
        jLabel2.setBounds(10, 60, 150, 14);

        jLabel3.setText(AppLocal.getIntString("label.stockproduct"));
        add(jLabel3);
        jLabel3.setBounds(10, 120, 150, 14);

        m_jproduct.setEditable(false);
        add(m_jproduct);
        m_jproduct.setBounds(160, 180, 200, 19);

        add(m_jreason);
        m_jreason.setBounds(160, 60, 200, 20);

        m_jbtnproduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/adrianromero/images/viewmag.png")));
        m_jbtnproduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jbtnproductActionPerformed(evt);
            }
        });

        add(m_jbtnproduct);
        m_jbtnproduct.setBounds(370, 180, 40, 25);

        m_jbtndate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/adrianromero/images/date.png")));
        m_jbtndate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jbtndateActionPerformed(evt);
            }
        });

        add(m_jbtndate);
        m_jbtndate.setBounds(370, 30, 40, 25);

        jLabel4.setText(AppLocal.getIntString("label.units"));
        add(jLabel4);
        jLabel4.setBounds(10, 210, 150, 14);

        jLabel5.setText(AppLocal.getIntString("label.price"));
        add(jLabel5);
        jLabel5.setBounds(10, 240, 150, 14);

        m_junits.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        add(m_junits);
        m_junits.setBounds(160, 210, 70, 19);

        m_jprice.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        add(m_jprice);
        m_jprice.setBounds(160, 240, 70, 19);

        m_jcatcontainer.setLayout(new java.awt.BorderLayout());

        add(m_jcatcontainer);
        m_jcatcontainer.setBounds(0, 340, 780, 190);

        m_jcodebar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jcodebarActionPerformed(evt);
            }
        });

        add(m_jcodebar);
        m_jcodebar.setBounds(230, 150, 130, 19);

        m_jEnter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/adrianromero/images/apply.png")));
        m_jEnter.setFocusPainted(false);
        m_jEnter.setFocusable(false);
        m_jEnter.setRequestFocusEnabled(false);
        m_jEnter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jEnterActionPerformed(evt);
            }
        });

        add(m_jEnter);
        m_jEnter.setBounds(370, 150, 40, 24);

        m_jreference.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jreferenceActionPerformed(evt);
            }
        });

        add(m_jreference);
        m_jreference.setBounds(230, 120, 130, 19);

        m_jEnter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/adrianromero/images/apply.png")));
        m_jEnter1.setFocusPainted(false);
        m_jEnter1.setFocusable(false);
        m_jEnter1.setRequestFocusEnabled(false);
        m_jEnter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jEnter1ActionPerformed(evt);
            }
        });

        add(m_jEnter1);
        m_jEnter1.setBounds(370, 120, 40, 24);

        jLabel6.setText(AppLocal.getIntString("label.prodref"));
        add(jLabel6);
        jLabel6.setBounds(160, 120, 70, 14);

        jLabel7.setText(AppLocal.getIntString("label.prodbarcode"));
        add(jLabel7);
        jLabel7.setBounds(160, 150, 70, 14);

        add(m_jLocation);
        m_jLocation.setBounds(160, 90, 200, 20);

        jLabel8.setText(AppLocal.getIntString("label.warehouse"));
        add(jLabel8);
        jLabel8.setBounds(10, 90, 150, 14);

    }// </editor-fold>//GEN-END:initComponents

    private void m_jEnter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jEnter1ActionPerformed

        assignProductByReference();
        
    }//GEN-LAST:event_m_jEnter1ActionPerformed

    private void m_jreferenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jreferenceActionPerformed

        assignProductByReference();

    }//GEN-LAST:event_m_jreferenceActionPerformed

    private void m_jcodebarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jcodebarActionPerformed
       
        assignProductByCode();

    }//GEN-LAST:event_m_jcodebarActionPerformed

    private void m_jEnterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jEnterActionPerformed
            
        assignProductByCode();
   
    }//GEN-LAST:event_m_jEnterActionPerformed

    private void m_jbtnproductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jbtnproductActionPerformed

        assignProduct(JProductFinder.showMessage(this, m_App));
        
    }//GEN-LAST:event_m_jbtnproductActionPerformed

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
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JButton m_jEnter;
    private javax.swing.JButton m_jEnter1;
    private javax.swing.JComboBox m_jLocation;
    private javax.swing.JButton m_jbtndate;
    private javax.swing.JButton m_jbtnproduct;
    private javax.swing.JPanel m_jcatcontainer;
    private javax.swing.JTextField m_jcodebar;
    private javax.swing.JTextField m_jdate;
    private javax.swing.JTextField m_jprice;
    private javax.swing.JTextField m_jproduct;
    private javax.swing.JComboBox m_jreason;
    private javax.swing.JTextField m_jreference;
    private javax.swing.JTextField m_junits;
    // End of variables declaration//GEN-END:variables
    
}
