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
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import net.adrianromero.tpv.ticket.*; 
import net.adrianromero.tpv.forms.*; 
import net.adrianromero.data.loader.StaticSentence;
import net.adrianromero.data.loader.SerializerReadClass;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.loader.ImageUtils;
import net.adrianromero.data.gui.NullIcon;
import net.adrianromero.data.loader.SentenceList;
import net.adrianromero.tpv.panelsales.Floor;
import net.adrianromero.tpv.panelsales.Place;


public class JTicketsBagRestaurantMap extends JTicketsBag {

    private static final Icon ICO_OCU = new ImageIcon(JTicketsBag.class.getResource("/net/adrianromero/images/edit_group.png"));
    private static final Icon ICO_FRE = new NullIcon(22, 22);
        
    private java.util.List m_aplaces;
    private java.util.List m_afloors;

//    private SharedObjects m_sharedobjects;
    
    private JTicketsBagRestaurant m_restaurantmap;  
    private JTicketsBagRestaurantRes m_jreservations;
    private TicketsEditor m_panelticket;    
    
    private Place m_PlaceCurrent;
    private Place m_PlaceClipboard;   
    
    /** Creates new form JTicketsBagRestaurant */
    public JTicketsBagRestaurantMap(AppView oApp, TicketsEditor panelticket) {
        
        super(oApp);
        m_panelticket = panelticket;
        
        m_restaurantmap = new JTicketsBagRestaurant(this);
        m_PlaceCurrent = null;
        m_PlaceClipboard = null;
        m_restaurantmap.setTableName(null);
            
        try {
            SentenceList sent = new StaticSentence(
                    oApp.getConnection(), 
                    "SELECT ID, NAME, IMAGE FROM FLOORS ORDER BY ID", 
                    null, 
                    new SerializerReadClass(Floor.class));
            m_afloors = sent.list();
        } catch (BasicException eD) {
            m_afloors = new ArrayList();
        }
        try {
            SentenceList sent = new StaticSentence(
                    oApp.getConnection(), 
                    "SELECT NAME, X, Y, FLOOR FROM PLACES ORDER BY FLOOR", 
                    null, 
                    new SerializerReadClass(Place.class));
            m_aplaces = sent.list();
        } catch (BasicException eD) {
            m_aplaces = new ArrayList();
        } 
        
        initComponents(); 
        
        // Por ahora hasta que no esten las reservas finas lo deshabilitamos.
        // m_jbtnReservations.setVisible(false);
          
        // anado los contenedores
        if (m_afloors.size() > 1) {
            JTabbedPane jTabFloors = new JTabbedPane();
            jTabFloors.setBorder(new javax.swing.border.EmptyBorder(new Insets(5, 5, 5, 5)));
            jTabFloors.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
            jTabFloors.setFocusable(false);
            jTabFloors.setRequestFocusEnabled(false);
            m_jPanelMap.add(jTabFloors, BorderLayout.CENTER);
            
            for (int i = 0; i < m_afloors.size(); i++) {
                Floor f = (Floor) m_afloors.get(i);
                JScrollPane jScrCont = new JScrollPane();
                JPanel jPanCont = new JPanel();   
                
                jTabFloors.addTab(f.getName(), f.getIcon(), jScrCont);     
                jScrCont.setViewportView(jPanCont);
                jPanCont.add(f.getContainer());
            }
        } else if (m_afloors.size() == 1) {
            // solo una planta, entonces sin tabs...
            Floor f = (Floor) m_afloors.get(0);
            JPanel jPlaces = new JPanel();
            jPlaces.setLayout(new BorderLayout());
            jPlaces.setBorder(new javax.swing.border.CompoundBorder(
                    new javax.swing.border.EmptyBorder(new Insets(5, 5, 5, 5)),
                    new javax.swing.border.TitledBorder(f.getName())));
            JScrollPane jScrCont = new JScrollPane();
            JPanel jPanCont = new JPanel();
            
            // jPlaces.setLayout(new FlowLayout());           
            m_jPanelMap.add(jPlaces, BorderLayout.CENTER);
            jPlaces.add(jScrCont, BorderLayout.CENTER);
            jScrCont.setViewportView(jPanCont);            
            jPanCont.add(f.getContainer());
        }   
        
        // anado los botones a los contenedores
        Floor currfloor = null;
        int iFloor = 0;

        for (int i = 0; i < m_aplaces.size(); i++) {
            
            Place pl = (Place) m_aplaces.get(i);
            
            if (currfloor == null || pl.getFloor() != currfloor.getID()) {
                // tenemos que buscar una nueva planta...
                do {
                    currfloor = (Floor) m_afloors.get(iFloor++);
                } while (pl.getFloor() != currfloor.getID());
            }

            currfloor.getContainer().add(pl.getButton());
            pl.setButtonBounds();
            pl.getButton().addActionListener(new MyActionListener(pl)); // anado el evento
        }
        
        // Y alora el panel de reservas..
        m_jreservations = new JTicketsBagRestaurantRes(oApp, this);
        add(m_jreservations, "res");
    }
    
    public void activate() {
        
        // precondicion es que no tenemos ticket activado ni ticket en el panel

        loadTickets();
        
        printState();     
        m_panelticket.setActiveTicket(null, null); 
       
        showView("map"); // arrancamos en la vista de las mesas.
        
        // postcondicion es que tenemos ticket activado aqui y ticket en el panel
    }
    
    public boolean deactivate() {
        
        // precondicion es que tenemos ticket activado aqui y ticket en el panel
        
        if (viewTables()) {
        
            // borramos el clipboard
            m_PlaceClipboard = null;

            // guardamos el ticket
            if (m_PlaceCurrent != null) {
                m_PlaceCurrent.setTicket(m_panelticket.getActiveTicket());
    //            m_sharedobjects.checkin(m_PlaceCurrent.getName(), m_PlaceCurrent.getTicket());
                m_PlaceCurrent = null;
                m_restaurantmap.setTableName(null);
            }

            // desactivamos cositas.
            printState();     
            m_panelticket.setActiveTicket(null, null); 

            // Guardamos los tickets
            HashMap atickets = new HashMap();
            for (int i = 0; i < m_aplaces.size(); i++) {
                Place p = (Place) m_aplaces.get(i);
                if (p.getTicket() != null) {   
                    atickets.put(p.getName(), p.getTicket());
                }
            }
            // ImageUtils.writeToFile("ticketsbagrest.ser", atickets);
            m_App.lookupDataLogic(DataLogicSystem.class).setResourceAsBinary(m_App.getHost() + "/ticketsbagrest", ImageUtils.writeSerializable(atickets));

            return true;
        } else {
            return false;
        }
        
        // postcondicion es que no tenemos ticket activado
    }

        
    protected JComponent getBagComponent() {
        return m_restaurantmap;
    }
    protected JComponent getNullComponent() {
        return this;
    }
   
    public void moveTicket() {
        
        // guardamos el ticket
        if (m_PlaceCurrent != null) {
            m_PlaceCurrent.setTicket(m_panelticket.getActiveTicket());
//            m_sharedobjects.commit(m_PlaceCurrent.getName(), m_PlaceCurrent.getTicket());
           
            // me guardo el ticket que quiero copiar.
            m_PlaceClipboard = m_PlaceCurrent;        
            m_PlaceCurrent = null;
            m_restaurantmap.setTableName(null);
        }
        
        printState();     
        m_panelticket.setActiveTicket(null, null); 
    }
    
    public boolean viewTables() {
        
        // deberiamos comprobar si estamos en reservations o en tables...
        if (m_jreservations.deactivate()) {
            showView("map"); // arrancamos en la vista de las mesas.
            return true;
        } else {
            return false;
        }
    }
    
    public void saveTicket() {
        saveTicket(m_panelticket.getActiveTicket());
    }
    
    public void newTicket() {
        
        // guardamos el ticket
        if (m_PlaceCurrent != null) {
            m_PlaceCurrent.setTicket(m_panelticket.getActiveTicket());
//            m_sharedobjects.checkin(m_PlaceCurrent.getName(), m_PlaceCurrent.getTicket());
            m_PlaceCurrent = null;
            m_restaurantmap.setTableName(null);
        }
        
        printState();     
        m_panelticket.setActiveTicket(null, null);     
    }
    
    public void cancelTicket() {
        
        // no guardamos el ticket porque no hace falta
        if (m_PlaceCurrent != null) {
            m_PlaceCurrent.setTicket(null);
//            m_sharedobjects.delete(m_PlaceCurrent.getName());
            m_PlaceCurrent = null;
            m_restaurantmap.setTableName(null);
        }        
        
        printState();     
        m_panelticket.setActiveTicket(null, null); 
    }
    
    public void loadTickets() {

        // leo los tickets        
        // HashMap atickets = (HashMap) ImageUtils.readFromFile("ticketsbagrest.ser");
        HashMap atickets = (HashMap) ImageUtils.readSerializable(m_App.lookupDataLogic(DataLogicSystem.class).getResourceAsBinary(m_App.getHost() + "/ticketsbagrest"));

        if (atickets == null) {
            atickets = new HashMap();
        }
        
//        HashMap atickets = m_sharedobjects.listObjects();
            
        for (int i = 0 ; i < m_aplaces.size(); i++) {
            Place table = (Place) m_aplaces.get(i);
            TicketInfo ticket = (TicketInfo) atickets.get(table.getName());
            table.setTicket(ticket);
        }
    }
    
    private void printState() {
        
        if (m_PlaceClipboard == null) {
            // Decimos que vamos a seleccionar una nueva mesa vacia o no
            m_jText.setText(null);
            // Habilitamos todas las mesas
            for (int i = 0; i < m_aplaces.size(); i++) {
                Place pl = (Place) m_aplaces.get(i);
                pl.getButton().setEnabled(true);
            }
        } else {
            // Decimos que vamos a copiar
            m_jText.setText(AppLocal.getIntString("label.restaurantmove", new Object[] {m_PlaceClipboard.getName()}));
            // Habilitamos las mesas vacias y la mesa de origen.
            for (int i = 0; i < m_aplaces.size(); i++) {
                Place pl = (Place) m_aplaces.get(i);
                if (pl.getTicket() == null) {   
                    pl.getButton().setEnabled(true);
                } else {
                    pl.getButton().setEnabled(m_PlaceClipboard.getName().equals(pl.getName()));
                }
            }  
        }
    }
      
    private class MyActionListener implements ActionListener {
        
        private Place m_place;
        
        public MyActionListener(Place place) {
            m_place = place;
        }
        
        public void actionPerformed(ActionEvent evt) {    
            
//            // han pulsado sobre una mesa vacia o no...
//            boolean bresult = m_sharedobjets.checkout(m_place.getName());
//            m_place.setTicket((TicketInfo) m_sharedobjets.loadObject(m_place.getName()));
//            if (!bresult) {
//                // no se ha podido checkear porque otro tiene pillado el ticket.
//                Toolkit.getDefaultToolkit().beep();
//                return;
//             }  
            
            if (m_PlaceClipboard == null) {  

                // creamos el ticket si era una mesa vacia.
                if (m_place.getTicket() == null) {
                    m_place.setTicket(createTicketModel());
                }                
                // activamos el ticket seleccionado
                m_PlaceCurrent = m_place;
                m_restaurantmap.setTableName(m_PlaceCurrent.getName());
                m_panelticket.setActiveTicket(m_PlaceCurrent.getTicket(), m_PlaceCurrent.getName());

            } else {
                
                // tenemos que copiar el ticket del clipboard
                if (m_PlaceClipboard == m_place) {
                    // hemos pulsado el mismo entonces deshacemos
                    m_PlaceClipboard = null;
                    // activamos el ticket seleccionado
                    m_PlaceCurrent = m_place;
                    m_restaurantmap.setTableName(m_PlaceCurrent.getName());
                    m_panelticket.setActiveTicket(m_PlaceCurrent.getTicket(), m_PlaceCurrent.getName());                               
                } else if (m_place.getTicket() == null) {
                    // Movemos el ticket a una mesa vacia
                    m_place.setTicket(m_PlaceClipboard.getTicket());
                    // m_place.saveTicket(m_PlaceClipboard.getTicket(), true);
                    m_PlaceClipboard.setTicket(null);
                    // m_place.saveTicket(null, false);
                    m_PlaceClipboard = null;
                    
                    // No hace falta preguntar si estaba bloqueado porque ya lo estaba antes
                    // activamos el ticket seleccionado
                    m_PlaceCurrent = m_place;
                    m_restaurantmap.setTableName(m_PlaceCurrent.getName());
                    m_panelticket.setActiveTicket(m_PlaceCurrent.getTicket(), m_PlaceCurrent.getName());                           
                } else {
                    // No podemos meterlo donde ya hay gente. Esto esta ya controlado porque deberia estar el boton deshabilitado.
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        }
    }  

    private void showView(String view) {
        CardLayout cl = (CardLayout)(getLayout());
        cl.show(this, view);  
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        m_jPanelMap = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        m_jbtnReservations = new javax.swing.JButton();
        m_jText = new javax.swing.JLabel();

        setLayout(new java.awt.CardLayout());

        m_jPanelMap.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        m_jbtnReservations.setText(AppLocal.getIntString("button.reservations"));
        m_jbtnReservations.setFocusPainted(false);
        m_jbtnReservations.setFocusable(false);
        m_jbtnReservations.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jbtnReservations.setRequestFocusEnabled(false);
        m_jbtnReservations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jbtnReservationsActionPerformed(evt);
            }
        });

        jPanel2.add(m_jbtnReservations);

        jPanel2.add(m_jText);

        m_jPanelMap.add(jPanel2, java.awt.BorderLayout.NORTH);

        add(m_jPanelMap, "map");

    }
    // </editor-fold>//GEN-END:initComponents

    private void m_jbtnReservationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jbtnReservationsActionPerformed

        showView("res");
        m_jreservations.activate();
        
    }//GEN-LAST:event_m_jbtnReservationsActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel m_jPanelMap;
    private javax.swing.JLabel m_jText;
    private javax.swing.JButton m_jbtnReservations;
    // End of variables declaration//GEN-END:variables
    
}
