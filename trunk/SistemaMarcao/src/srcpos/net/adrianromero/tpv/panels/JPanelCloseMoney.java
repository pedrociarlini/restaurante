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

package net.adrianromero.tpv.panels;

import net.adrianromero.tpv.forms.JPanelView;
import net.adrianromero.tpv.forms.AppView;
import net.adrianromero.tpv.forms.AppLocal;
import java.awt.*;
import javax.swing.*;
import java.util.Date;
import javax.swing.table.*;
import net.adrianromero.data.loader.StaticSentence;
import net.adrianromero.data.loader.SerializerWriteBasic;
import net.adrianromero.format.Formats;
import net.adrianromero.basic.BasicException;
import net.adrianromero.format.FormatsRESOURCE;
import net.adrianromero.data.loader.Datas;
import net.adrianromero.data.gui.MessageInf;
import net.adrianromero.data.gui.TableRendererBasic;
import net.adrianromero.tpv.forms.DataLogicSystem;
import net.adrianromero.tpv.printer.TicketParser;
import net.adrianromero.tpv.printer.TicketPrinterException;
import org.apache.velocity.VelocityContext;

public class JPanelCloseMoney extends JPanel implements JPanelView {

    
    private AppView m_App;
    
    private PaymentsModel m_PaymentsToClose = null;   
    
    private TicketParser m_TTP;
    
    /** Creates new form JPanelCloseMoney */
    public JPanelCloseMoney(AppView oApp) {
        
        m_App = oApp;        
        m_TTP = new TicketParser(m_App.getDeviceTicket(), m_App.lookupDataLogic(DataLogicSystem.class));
        
        initComponents();    
        
        m_jTicketTable.setDefaultRenderer(Object.class, new TableRendererBasic(
                new Formats[] {new FormatsRESOURCE(AppLocal.getResourceBundle(), "transpayment."), Formats.CURRENCY}));
        m_jTicketTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m_jScrollTableTicket.getVerticalScrollBar().setPreferredSize(new Dimension(25,25));       
        m_jTicketTable.getTableHeader().setReorderingAllowed(false);         
        m_jTicketTable.setRowHeight(25);
        m_jTicketTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);         
        
        m_jsalestable.setDefaultRenderer(Object.class, new TableRendererBasic(
                new Formats[] {Formats.STRING, Formats.CURRENCY, Formats.CURRENCY}));
        m_jsalestable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        m_jScrollSales.getVerticalScrollBar().setPreferredSize(new Dimension(25,25));       
        m_jsalestable.getTableHeader().setReorderingAllowed(false);         
        m_jsalestable.setRowHeight(25);
        m_jsalestable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);         
    }

    public JComponent getComponent() {
        return this;
    }

    public String getTitle() {
        return AppLocal.getIntString("Menu.CloseTPV");
    }    
    
    public void activate() {
        loadData();
    }   
    
    public boolean deactivate() {
        // se me debe permitir cancelar el deactivate   
        return true;
    }  
    
    private void loadData() {
                        
        try {           
            m_PaymentsToClose = PaymentsModel.loadInstance(m_App);                      
        } catch (BasicException e) {            
            m_PaymentsToClose = PaymentsModel.emptyInstance();
            MessageInf msg = new MessageInf(MessageInf.SGN_NOTICE, AppLocal.getIntString("message.cannotclosecash"), e);
            msg.show(this);
        }
        
        m_jMinDate.setText(m_PaymentsToClose.printDateStart());
        m_jMaxDate.setText(m_PaymentsToClose.printDateEnd());
        
        if (m_PaymentsToClose.getPayments() == 0 && m_PaymentsToClose.getSales() == 0) {
            m_jCloseCash.setEnabled(false);

            m_jCount.setText(null); // AppLocal.getIntString("label.noticketstoclose");
            m_jCash.setText(null);
            
            m_jSales.setText(null);
            m_jSalesSubtotal.setText(null);
            m_jSalesTotal.setText(null);
        } else {
            m_jCloseCash.setEnabled(true);

            m_jCount.setText(m_PaymentsToClose.printPayments());
            m_jCash.setText(m_PaymentsToClose.printPaymentsTotal());
            
            m_jSales.setText(m_PaymentsToClose.printSales());
            m_jSalesSubtotal.setText(m_PaymentsToClose.printSalesSubtotal());
            m_jSalesTotal.setText(m_PaymentsToClose.printSalesTotal());
        }          
        
        m_jTicketTable.setModel(m_PaymentsToClose.getPaymentsModel());
                
        TableColumnModel jColumns = m_jTicketTable.getColumnModel();
        jColumns.getColumn(0).setPreferredWidth(150);
        jColumns.getColumn(0).setResizable(false);
        jColumns.getColumn(1).setPreferredWidth(100);
        jColumns.getColumn(1).setResizable(false);
//        jColumns.getColumn(2).setPreferredWidth(100);
//        jColumns.getColumn(2).setResizable(false);
//        jColumns.getColumn(3).setPreferredWidth(100);
//        jColumns.getColumn(3).setResizable(false);    
        
        m_jsalestable.setModel(m_PaymentsToClose.getSalesModel());
        jColumns = m_jsalestable.getColumnModel();
        jColumns.getColumn(0).setPreferredWidth(150);
        jColumns.getColumn(0).setResizable(false);
        jColumns.getColumn(1).setPreferredWidth(100);
        jColumns.getColumn(1).setResizable(false);
        jColumns.getColumn(1).setPreferredWidth(100);
        jColumns.getColumn(1).setResizable(false);
    }   
    
    private void printPayments() {
        
        String sresource = m_App.lookupDataLogic(DataLogicSystem.class).getResourceAsXML("Printer.CloseCash");
        if (sresource == null) {
            MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotprintticket"));
            msg.show(this);
        } else {
            VelocityContext vc = new VelocityContext();
            vc.put("payments", m_PaymentsToClose);
            try {
                m_TTP.printTicket(m_App.evaluateTemplate(sresource, vc));
            } catch (TicketPrinterException eTP) {
                MessageInf msg = new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotprintticket"), eTP);
                msg.show(this);
            }
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        m_jCount = new javax.swing.JTextField();
        m_jMinDate = new javax.swing.JTextField();
        m_jMaxDate = new javax.swing.JTextField();
        m_jCash = new javax.swing.JTextField();
        m_jCloseCash = new javax.swing.JButton();
        m_jScrollTableTicket = new javax.swing.JScrollPane();
        m_jTicketTable = new javax.swing.JTable();
        m_jScrollSales = new javax.swing.JScrollPane();
        m_jsalestable = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        m_jSales = new javax.swing.JTextField();
        m_jSalesTotal = new javax.swing.JTextField();
        m_jSalesSubtotal = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setLayout(null);

        jLabel1.setText(AppLocal.getIntString("Label.Tickets"));
        add(jLabel1);
        jLabel1.setBounds(430, 150, 90, 14);

        jLabel2.setText(AppLocal.getIntString("Label.StartDate"));
        add(jLabel2);
        jLabel2.setBounds(10, 50, 90, 14);

        jLabel3.setText(AppLocal.getIntString("Label.EndDate"));
        add(jLabel3);
        jLabel3.setBounds(10, 80, 90, 14);

        jLabel4.setText(AppLocal.getIntString("Label.Cash"));
        add(jLabel4);
        jLabel4.setBounds(430, 180, 90, 14);

        m_jCount.setEditable(false);
        m_jCount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        add(m_jCount);
        m_jCount.setBounds(520, 150, 100, 19);

        m_jMinDate.setEditable(false);
        m_jMinDate.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        add(m_jMinDate);
        m_jMinDate.setBounds(100, 50, 160, 19);

        m_jMaxDate.setEditable(false);
        m_jMaxDate.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        add(m_jMaxDate);
        m_jMaxDate.setBounds(100, 80, 160, 19);

        m_jCash.setEditable(false);
        m_jCash.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        add(m_jCash);
        m_jCash.setBounds(520, 180, 100, 19);

        m_jCloseCash.setText(AppLocal.getIntString("Button.CloseCash"));
        m_jCloseCash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jCloseCashActionPerformed(evt);
            }
        });

        add(m_jCloseCash);
        m_jCloseCash.setBounds(10, 490, 110, 30);

        m_jTicketTable.setFocusable(false);
        m_jTicketTable.setIntercellSpacing(new java.awt.Dimension(0, 1));
        m_jTicketTable.setRequestFocusEnabled(false);
        m_jTicketTable.setShowVerticalLines(false);
        m_jScrollTableTicket.setViewportView(m_jTicketTable);

        add(m_jScrollTableTicket);
        m_jScrollTableTicket.setBounds(10, 150, 400, 140);

        m_jsalestable.setFocusable(false);
        m_jsalestable.setIntercellSpacing(new java.awt.Dimension(0, 1));
        m_jsalestable.setRequestFocusEnabled(false);
        m_jsalestable.setShowVerticalLines(false);
        m_jScrollSales.setViewportView(m_jsalestable);

        add(m_jScrollSales);
        m_jScrollSales.setBounds(10, 330, 400, 140);

        jLabel5.setText(AppLocal.getIntString("label.sales"));
        add(jLabel5);
        jLabel5.setBounds(430, 340, 90, 14);

        m_jSales.setEditable(false);
        m_jSales.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        add(m_jSales);
        m_jSales.setBounds(520, 340, 100, 19);

        m_jSalesTotal.setEditable(false);
        m_jSalesTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        add(m_jSalesTotal);
        m_jSalesTotal.setBounds(520, 400, 100, 19);

        m_jSalesSubtotal.setEditable(false);
        m_jSalesSubtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        add(m_jSalesSubtotal);
        m_jSalesSubtotal.setBounds(520, 370, 100, 19);

        jLabel6.setText(AppLocal.getIntString("label.subtotalcash"));
        add(jLabel6);
        jLabel6.setBounds(430, 370, 90, 14);

        jLabel7.setText(AppLocal.getIntString("label.totalcash"));
        add(jLabel7);
        jLabel7.setBounds(430, 400, 90, 14);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel8.setText(AppLocal.getIntString("label.paymentstitle"));
        jLabel8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        add(jLabel8);
        jLabel8.setBounds(10, 120, 660, 17);

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel9.setText(AppLocal.getIntString("label.salestitle"));
        jLabel9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        add(jLabel9);
        jLabel9.setBounds(10, 300, 660, 17);

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel10.setText(AppLocal.getIntString("label.datestitle"));
        jLabel10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        add(jLabel10);
        jLabel10.setBounds(10, 20, 660, 17);

    }// </editor-fold>//GEN-END:initComponents

    private void m_jCloseCashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jCloseCashActionPerformed
        // TODO add your handling code here:
        int res = JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.wannaclosecash"), AppLocal.getIntString("message.title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (res == JOptionPane.YES_OPTION) {
            
            Date dNow = new Date();
            
            try {               
                // Cerramos la caja si esta pendiente de cerrar.
                if (m_App.getActiveCashDateEnd() == null) {
                    new StaticSentence(m_App.getConnection()
                        , "UPDATE CLOSEDCASH SET DATEEND = ? WHERE HOST = ? AND MONEY = ?"
                        , new SerializerWriteBasic(new Datas[] {Datas.TIMESTAMP, Datas.STRING, Datas.INT}))
                        .exec(new Object[] {dNow, m_App.getHost(), m_App.getActiveCashIndex()}); 
                }
            } catch (BasicException e) {
                MessageInf msg = new MessageInf(MessageInf.SGN_NOTICE, AppLocal.getIntString("message.cannotclosecash"), e);
                msg.show(this);
            }
            
            try {
                // Creamos una nueva caja          
                m_App.setActiveCash(m_App.lookupDataLogic(DataLogicSystem.class).getNextClosedCash(), dNow, null);
                
                // creamos la caja activa      
                m_App.lookupDataLogic(DataLogicSystem.class).execInsertCash(
                        new Object[] {m_App.getActiveCashIndex(), m_App.getHost(), m_App.getActiveCashDateStart(), m_App.getActiveCashDateEnd()});                  
               
                // ponemos la fecha de fin
                m_PaymentsToClose.setDateEnd(dNow);
                
                // Imprimimos el miniinforme
                printPayments();
                // Mostramos el mensaje
                JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.closecashok"), AppLocal.getIntString("message.title"), JOptionPane.INFORMATION_MESSAGE);
            } catch (BasicException e) {
                MessageInf msg = new MessageInf(MessageInf.SGN_NOTICE, AppLocal.getIntString("message.cannotclosecash"), e);
                msg.show(this);
            }
            loadData();
        }         
    }//GEN-LAST:event_m_jCloseCashActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField m_jCash;
    private javax.swing.JButton m_jCloseCash;
    private javax.swing.JTextField m_jCount;
    private javax.swing.JTextField m_jMaxDate;
    private javax.swing.JTextField m_jMinDate;
    private javax.swing.JTextField m_jSales;
    private javax.swing.JTextField m_jSalesSubtotal;
    private javax.swing.JTextField m_jSalesTotal;
    private javax.swing.JScrollPane m_jScrollSales;
    private javax.swing.JScrollPane m_jScrollTableTicket;
    private javax.swing.JTable m_jTicketTable;
    private javax.swing.JTable m_jsalestable;
    // End of variables declaration//GEN-END:variables
    
}
