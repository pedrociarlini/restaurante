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

import java.util.*;
import javax.swing.*;

import net.adrianromero.data.loader.ImageUtils;
import net.adrianromero.tpv.ticket.*; 
import net.adrianromero.tpv.forms.*; 

public class JTicketsBagMulti extends JTicketsBag {
    
    private TicketsEditor m_panelticket;    

    private int m_iCurrent;    
    private java.util.List<TicketInfo> m_TicketList;
    
    /** Creates new form JTicketsBagSimple */
    public JTicketsBagMulti(AppView oApp, TicketsEditor panelticket) {
        
        super(oApp);
        m_panelticket = panelticket;
        
        initComponents();
    }
    
    public void activate() {
        
        // precondicion es que no tenemos ticket activado ni ticket en el panel
        
        m_TicketList = (java.util.List<TicketInfo>) ImageUtils.readSerializable(m_App.lookupDataLogic(DataLogicSystem.class).getResourceAsBinary(m_App.getHost() + "/ticketsbagmulti"));
        if (m_TicketList == null) {
            m_TicketList = new ArrayList<TicketInfo>();
        }
        
        m_iCurrent = -1;
        selectValidTicket();     
       
        // postcondicion es que tenemos ticket activado aqui y ticket en el panel
    }
    
    public boolean deactivate() {
        
        // precondicion es que tenemos ticket activado aqui y ticket en el panel 
        
        if (m_iCurrent >= 0) {
            m_TicketList.set(m_iCurrent, getActiveTicket());
        }
        m_iCurrent = -1;
        setActiveTicket(null);       
        
        //ImageUtils.writeToFile("ticketsbagmulti.ser", atickets);
        m_App.lookupDataLogic(DataLogicSystem.class).setResourceAsBinary(m_App.getHost() + "/ticketsbagmulti", ImageUtils.writeSerializable(m_TicketList));
        
        return true;
        
        // postcondicion es que no tenemos ticket activado ni ticket en el panel
    }
    
    protected JComponent getBagComponent() {
        return this;
    }
    protected JComponent getNullComponent() {
        return new JPanel();
    }
    
    private TicketInfo getActiveTicket() {
        return m_panelticket.getActiveTicket();
    }
   
    private void setActiveTicket(TicketInfo ticket) {
        m_panelticket.setActiveTicket(ticket, null);
    }
    
    private void selectValidTicket() {

        if (m_TicketList.size() == 0) {
            newTicket();               
        } else {
            m_iCurrent = m_TicketList.size() - 1;
            // activamos el ticket seleccionado
            setActiveTicket(m_TicketList.get(m_iCurrent)); 
        }
    }    
    
    private void newTicket() {      
        
        if (m_iCurrent >= 0) {
            m_TicketList.set(m_iCurrent, getActiveTicket());
        }

        TicketInfo ticket = createTicketModel();
        m_iCurrent = m_TicketList.size();
        m_TicketList.add(ticket);   
        
        setActiveTicket(ticket);        
    }
    
    public void saveTicket() {
        saveTicket(getActiveTicket());
    }
    
    public void cancelTicket() {        
        
        if (m_iCurrent >= 0) {
            m_TicketList.remove(m_iCurrent);
            m_iCurrent = -1;
        }        
        selectValidTicket();      
    }

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        m_jNewTicket = new javax.swing.JButton();
        m_jDelTicket = new javax.swing.JButton();
        m_jListTickets = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        m_jNewTicket.setText(AppLocal.getIntString("Button.NewTicket"));
        m_jNewTicket.setFocusPainted(false);
        m_jNewTicket.setFocusable(false);
        m_jNewTicket.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jNewTicket.setRequestFocusEnabled(false);
        m_jNewTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jNewTicketActionPerformed(evt);
            }
        });

        jPanel1.add(m_jNewTicket);

        m_jDelTicket.setText(AppLocal.getIntString("Button.DeleteTicket"));
        m_jDelTicket.setFocusPainted(false);
        m_jDelTicket.setFocusable(false);
        m_jDelTicket.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jDelTicket.setRequestFocusEnabled(false);
        m_jDelTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jDelTicketActionPerformed(evt);
            }
        });

        jPanel1.add(m_jDelTicket);

        m_jListTickets.setText(AppLocal.getIntString("button.listtickets"));
        m_jListTickets.setFocusPainted(false);
        m_jListTickets.setFocusable(false);
        m_jListTickets.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jListTickets.setRequestFocusEnabled(false);
        m_jListTickets.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jListTicketsActionPerformed(evt);
            }
        });

        jPanel1.add(m_jListTickets);

        add(jPanel1, java.awt.BorderLayout.WEST);

    }// </editor-fold>//GEN-END:initComponents

    private void m_jListTicketsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jListTicketsActionPerformed

        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JTicketsBagMultiList listDialog = JTicketsBagMultiList.newJDialog(JTicketsBagMulti.this);

                int iTicket = listDialog.showTicketsList(m_TicketList); 

                if (iTicket >= 0) {
                    if (m_iCurrent >= 0) {
                        m_TicketList.set(m_iCurrent, getActiveTicket());
                    }

                    m_iCurrent = iTicket;
                    setActiveTicket(m_TicketList.get(m_iCurrent)); 
                }
            }
        });
        
    }//GEN-LAST:event_m_jListTicketsActionPerformed

    private void m_jDelTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jDelTicketActionPerformed
        
        int res = JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.wannadelete"), AppLocal.getIntString("title.editor"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (res == JOptionPane.YES_OPTION) {
            cancelTicket();
        }
        
    }//GEN-LAST:event_m_jDelTicketActionPerformed

    private void m_jNewTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jNewTicketActionPerformed

        newTicket();
        
    }//GEN-LAST:event_m_jNewTicketActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton m_jDelTicket;
    private javax.swing.JButton m_jListTickets;
    private javax.swing.JButton m_jNewTicket;
    // End of variables declaration//GEN-END:variables
    
}