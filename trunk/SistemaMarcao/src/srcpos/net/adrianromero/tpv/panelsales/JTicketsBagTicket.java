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

package net.adrianromero.tpv.panelsales;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import net.adrianromero.data.gui.MessageInf;
import net.adrianromero.tpv.forms.AppView; 
import net.adrianromero.tpv.forms.AppLocal; 
import net.adrianromero.tpv.ticket.*;
import net.adrianromero.tpv.printer.*;
import org.apache.velocity.VelocityContext;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.gui.JMessageDialog;
import net.adrianromero.tpv.forms.DataLogicSystem;
import net.adrianromero.tpv.forms.SentenceContainer;

public class JTicketsBagTicket extends JTicketsBag {
    
//    private Template m_tempTicket;
    private DeviceTicket m_TP;    
    private TicketParser m_TTP;    
    private TicketParser m_TTP2; 
    
    private TicketInfo m_ticket;
    private TicketInfo m_ticketCopy;
    
    private JTicketsBagTicketBag m_TicketsBagTicketBag;

    private JPanelTicketEdits m_panelticket;    
    
    /** Creates new form JTicketsBagTicket */
    public JTicketsBagTicket(AppView oApp, JPanelTicketEdits panelticket) {
        
// Lista de tickets del cierre actual en orden desdencente...        
//SELECT TICKETS.TICKETID, TICKETS.DATENEW, TICKETS.MONEY, SUM(PRODUCTSOUT.UNITS * PRODUCTSOUT.PRICE), SUM(PRODUCTSOUT.UNITS * PRODUCTSOUT.PRICE * (1 + PRODUCTSOUT.TAXRATE))
//FROM TICKETS, PRODUCTSOUT WHERE TICKETS.TICKETID = PRODUCTSOUT.TICKETID
//AND TICKETS.MONEY = 20
//GROUP BY TICKETS.TICKETID
//ORDER BY TICKETS.DATENEW DESC
        
        super(oApp);
        m_panelticket = panelticket;
        
//        m_tempTicket = m_App.getTemplate("Printer.TicketPreview");
         
        // Inicializo la impresora...
        m_TP = new DeviceTicket();
   
        // Inicializo el parser de documentos de ticket
        m_TTP = new TicketParser(m_TP, m_App.lookupDataLogic(DataLogicSystem.class)); // para visualizar el ticket
        m_TTP2 = new TicketParser(m_App.getDeviceTicket(), m_App.lookupDataLogic(DataLogicSystem.class)); // para imprimir el ticket
        
        initComponents();
        
        m_TicketsBagTicketBag = new JTicketsBagTicketBag(this);
        
        m_jTicketEditor.addEditorKeys(m_jKeys);
        
        // Este deviceticket solo tiene una impresora, la de pantalla
        m_jPanelTicket.add(m_TP.getDevicePrinter("1").getPrinterComponent(), BorderLayout.CENTER);
    }
    
    public void activate() {
        
        // precondicion es que no tenemos ticket activado ni ticket en el panel
        
        m_ticket = null;
        m_ticketCopy = null;
        
        printTicket();        
        
        m_jTicketEditor.reset();
        m_jTicketEditor.activate();
        
        m_panelticket.setActiveTicket(null, null); 
             
        // postcondicion es que tenemos ticket activado aqui y ticket en el panel
    }
    
    public boolean deactivate() {
        
        // precondicion es que tenemos ticket activado aqui y ticket en el panel        
        m_ticket = null;   
        m_ticketCopy = null;
        return true;       
        // postcondicion es que no tenemos ticket activado ni ticket en el panel
    }
    
    public void saveTicket() {
        if (m_ticketCopy != null) {           
            // Para editar borramos el ticket anterior
            deleteTicket(m_ticketCopy);
        }
        saveTicket(m_panelticket.getActiveTicket()); // guardamos el ticket nuevo sea editar o devolver
        
        m_ticket = null;
        m_ticketCopy = null;
    }

    
    public void cancelTicket() {
        
        m_ticket = null;
        m_ticketCopy = null;
        resetToTicket(); 
    }    
    
    public void deleteeraseTicket() {
        // Solo se nos invoca desde editar
        if (m_ticketCopy != null) {           
            // Para editar borramos el ticket anterior
            deleteTicket(m_ticketCopy);
        }
        
        m_ticket = null;
        m_ticketCopy = null;
        resetToTicket();
    }    
    
    public void canceleditionTicket() {
        
        // m_ticket = null; // Nos queremos quedar con el ticket anterior
        m_ticketCopy = null;
        resetToTicket();
    }    
    
    private void resetToTicket() {       
        printTicket();
        m_jTicketEditor.reset();        
        m_panelticket.setActiveTicket(null, null); 
    }
    
    protected JComponent getBagComponent() {
        return m_TicketsBagTicketBag;
    }
    
    protected JComponent getNullComponent() {
        return this;
    }
      
    private void readTicket() {
        
        try {
            Object[] ticketid = new Object[] { new Integer(m_jTicketEditor.getValueInteger()) };
            TicketInfo ticket = (TicketInfo) m_App.lookupDataLogic(SentenceContainer.class).getLoadTicket().find(ticketid);
            if (ticket != null) {
                ticket.setLines(m_App.lookupDataLogic(SentenceContainer.class).getLoadTicketLines().list(ticketid));  
                
                m_ticket = ticket;
                m_ticketCopy = null; // se asigna al pulsar el boton de editar o devolver
                printTicket();
            }
            
        } catch (BasicException e) {
            // No se puede leer el ticket
        } catch (NumberFormatException ee) {
            // No hay valor...
        }
        
        m_jTicketEditor.reset();
    }
    
    private void printTicket() {
        
        // imprimo m_ticket
        
        m_jEdit.setEnabled(m_ticket != null);
        m_jRefund.setEnabled(m_ticket != null && m_ticket.getTotal() > 0.0);
        m_jPrint.setEnabled(m_ticket != null);
        
        // Este deviceticket solo tiene una impresora, la de pantalla
        m_TP.getDevicePrinter("1").reset();
        
        if (m_ticket == null) {
            m_jTicketId.setText(null);            
        } else {
            m_jTicketId.setText(String.valueOf(m_ticket.getId()));
            VelocityContext vc = new VelocityContext();
            vc.put("ticket", m_ticket);
            try {
                m_TTP.printTicket(m_App.evaluateResTemplate("Printer.TicketPreview", vc));
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
        java.awt.GridBagConstraints gridBagConstraints;

        m_jOptions = new javax.swing.JPanel();
        m_jButtons = new javax.swing.JPanel();
        m_lblTicketId = new javax.swing.JLabel();
        m_jTicketId = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        m_jEdit = new javax.swing.JButton();
        m_jRefund = new javax.swing.JButton();
        m_jPrint = new javax.swing.JButton();
        m_jPanelTicket = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        m_jKeys = new net.adrianromero.editor.JEditorKeys();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        m_jTicketEditor = new net.adrianromero.editor.JEditorIntegerPositive();

        setLayout(new java.awt.BorderLayout());

        m_jOptions.setLayout(new java.awt.BorderLayout());

        m_jButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        m_lblTicketId.setText(AppLocal.getIntString("label.ticketid"));
        m_jButtons.add(m_lblTicketId);

        m_jTicketId.setBackground(java.awt.Color.white);
        m_jTicketId.setFont(new java.awt.Font("Dialog", 1, 14));
        m_jTicketId.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        m_jTicketId.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.darkShadow")), javax.swing.BorderFactory.createEmptyBorder(1, 4, 1, 4)));
        m_jTicketId.setOpaque(true);
        m_jTicketId.setPreferredSize(new java.awt.Dimension(75, 25));
        m_jTicketId.setRequestFocusEnabled(false);
        m_jButtons.add(m_jTicketId);

        m_jOptions.add(m_jButtons, java.awt.BorderLayout.WEST);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        m_jEdit.setText(AppLocal.getIntString("button.edit"));
        m_jEdit.setFocusPainted(false);
        m_jEdit.setFocusable(false);
        m_jEdit.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jEdit.setRequestFocusEnabled(false);
        m_jEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jEditActionPerformed(evt);
            }
        });

        jPanel2.add(m_jEdit);

        m_jRefund.setText(AppLocal.getIntString("button.refund"));
        m_jRefund.setFocusPainted(false);
        m_jRefund.setFocusable(false);
        m_jRefund.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jRefund.setRequestFocusEnabled(false);
        m_jRefund.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jRefundActionPerformed(evt);
            }
        });

        jPanel2.add(m_jRefund);

        m_jPrint.setText(AppLocal.getIntString("button.print"));
        m_jPrint.setFocusPainted(false);
        m_jPrint.setFocusable(false);
        m_jPrint.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jPrint.setRequestFocusEnabled(false);
        m_jPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jPrintActionPerformed(evt);
            }
        });

        jPanel2.add(m_jPrint);

        m_jOptions.add(jPanel2, java.awt.BorderLayout.CENTER);

        add(m_jOptions, java.awt.BorderLayout.NORTH);

        m_jPanelTicket.setLayout(new java.awt.BorderLayout());

        m_jPanelTicket.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(m_jPanelTicket, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));

        jPanel4.add(m_jKeys);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jPanel5.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/adrianromero/images/button_ok.png")));
        jButton1.setFocusPainted(false);
        jButton1.setFocusable(false);
        jButton1.setMargin(new java.awt.Insets(8, 14, 8, 14));
        jButton1.setRequestFocusEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        jPanel5.add(jButton1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel5.add(m_jTicketEditor, gridBagConstraints);

        jPanel4.add(jPanel5);

        jPanel3.add(jPanel4, java.awt.BorderLayout.NORTH);

        add(jPanel3, java.awt.BorderLayout.EAST);

    }// </editor-fold>//GEN-END:initComponents

    private void m_jEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jEditActionPerformed
          
        m_ticketCopy = m_ticket.cloneTicket();
        m_TicketsBagTicketBag.showEdit();
        m_panelticket.showCatalog();
        m_panelticket.setActiveTicket(m_ticket, null);  
        
    }//GEN-LAST:event_m_jEditActionPerformed

    private void m_jPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jPrintActionPerformed
       
        if (m_ticket != null) {
            VelocityContext vc = new VelocityContext();
            vc.put("ticket", m_ticket);
            try {
                m_TTP2.printTicket(m_App.evaluateResTemplate("Printer.TicketPreview", vc)); // a la impresion general.
            } catch (TicketPrinterException eTP) {
                JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_NOTICE, AppLocal.getIntString("message.CannotPrint"), eTP));
            }
        }  
        
    }//GEN-LAST:event_m_jPrintActionPerformed

    private void m_jRefundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jRefundActionPerformed
        
        TicketInfo ticketrefund = createTicketModel();
        java.util.List aRefundLines = new ArrayList();
        
        for(int i = 0; i < m_ticket.getLinesCount(); i++) {
            TicketLineInfo newline = new TicketLineInfo(m_ticket.getLine(i));
            newline.setMultiply(newline.getMultiply());
            aRefundLines.add(newline);
        } 
        
        m_ticketCopy = null;
        m_TicketsBagTicketBag.showRefund();
        m_panelticket.showRefundLines(aRefundLines);
        m_panelticket.setActiveTicket(ticketrefund, null);      
        
    }//GEN-LAST:event_m_jRefundActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        readTicket();
        
    }//GEN-LAST:event_jButton1ActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel m_jButtons;
    private javax.swing.JButton m_jEdit;
    private net.adrianromero.editor.JEditorKeys m_jKeys;
    private javax.swing.JPanel m_jOptions;
    private javax.swing.JPanel m_jPanelTicket;
    private javax.swing.JButton m_jPrint;
    private javax.swing.JButton m_jRefund;
    private net.adrianromero.editor.JEditorIntegerPositive m_jTicketEditor;
    private javax.swing.JLabel m_jTicketId;
    private javax.swing.JLabel m_lblTicketId;
    // End of variables declaration//GEN-END:variables
    
}
