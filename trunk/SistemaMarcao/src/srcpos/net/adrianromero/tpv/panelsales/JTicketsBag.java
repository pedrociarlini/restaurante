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

import java.util.Date;
import net.adrianromero.basic.BasicException;
import net.adrianromero.tpv.ticket.*; 
import net.adrianromero.tpv.forms.*; 
import javax.swing.*;
import net.adrianromero.data.gui.MessageInf;

public abstract class JTicketsBag extends JPanel {
    
    protected AppView m_App;
    
    /** Creates new form JTicketsBag */
    public JTicketsBag(AppView oApp) {        
        m_App = oApp;     
        // initComponents();
    }
    
    public abstract void activate();
    public abstract boolean deactivate();
    public abstract void cancelTicket();
    public abstract void saveTicket();
    
    protected abstract JComponent getBagComponent();
    protected abstract JComponent getNullComponent();
    
    public static JTicketsBag createTicketsBag(String sName, AppView oApp, TicketsEditor panelticket) {
        
        if ("standard".equals(sName)) {
            return new JTicketsBagMulti(oApp, panelticket);
        } else if ("restaurant".equals(sName)) {
            return new JTicketsBagRestaurantMap(oApp, panelticket);
        } else { // "simple"
            return new JTicketsBagSimple(oApp, panelticket);
        }
    }
    
    protected final TicketInfo createTicketModel(){

        // creo el nuevo ticket
        TicketInfo ticket = new TicketInfo();

        try {
            Integer index = (Integer) m_App.lookupDataLogic(SentenceContainer.class).getNextTicketIndex().find();
            ticket.setId(index.intValue());
        } catch (BasicException eD) {
            ticket.setId(0);
        }       

        // Pinto el numero del ticket
        return ticket;
    }    
    
    protected final void saveTicket(TicketInfo ticket) {
        // Guardo el ticket
        try {
            m_App.lookupDataLogic(SentenceContainer.class).getTicketInsert().exec(ticket);
            for (int i = 0; i < ticket.getLinesCount(); i++) {
                m_App.lookupDataLogic(SentenceContainer.class).getTicketLineInsert().exec(ticket.getLine(i));

                if (ticket.getLine(i).getProductReference() != null)  {
                    // Hay que actualizar el stock si el hay producto
                    Object[] diary = new Object[7];
                    diary[0] = m_App.lookupDataLogic(SentenceContainer.class).getNextStockDiary().find();
                    diary[1] = ticket.getDate();
                    diary[2] = new Integer(-ticket.getLine(i).getMultiply() >= 0.0 ? +2 : -1); // Devolucion o Venta
                    diary[3] = m_App.getInventoryLocation();
                    diary[4] = ticket.getLine(i).getProductReference() ;
                    diary[5] = new Double(-ticket.getLine(i).getMultiply());
                    diary[6] = new Double(ticket.getLine(i).getPrice());                                
                    m_App.lookupDataLogic(SentenceContainer.class).getStockDiaryInsert().exec(diary);
                }
            }
            for (int i = 0; i < ticket.getPayment().length; i++) {
                Object[] payment = new Object[5];
                payment[0] = m_App.lookupDataLogic(SentenceContainer.class).getNextPayment().find();
                payment[1] = new Integer(ticket.getId());
                payment[2] = ticket.getPayment()[i].getName();
                payment[3] = new Double(ticket.getPayment()[i].getTotal());
                m_App.lookupDataLogic(SentenceContainer.class).getPaymentInsert().exec(payment);
            }                          
        } catch (BasicException eData) {
            MessageInf msg = new MessageInf(MessageInf.SGN_NOTICE, AppLocal.getIntString("message.nosave"), eData);
            msg.show(this);
        }
    }
    
    protected final void deleteTicket(TicketInfo ticket) {
        
        try {               
            // actualizamos el inventario.
            Date d = new Date();
            for (int i = 0; i < ticket.getLinesCount(); i++) {
                if (ticket.getLine(i).getProductReference() != null)  {
                    // Hay que actualizar el stock si el hay producto
                    Object[] diary = new Object[7];
                    diary[0] = m_App.lookupDataLogic(SentenceContainer.class).getNextStockDiary().find();
                    diary[1] = d;
                    diary[2] = new Integer(ticket.getLine(i).getMultiply() >= 0.0 ? +2 : -1); // Devolucion o Venta
                    diary[3] = m_App.getInventoryLocation();
                    diary[4] = ticket.getLine(i).getProductReference() ;
                    diary[5] = new Double(ticket.getLine(i).getMultiply());
                    diary[6] = new Double(ticket.getLine(i).getPrice());                                
                    m_App.lookupDataLogic(SentenceContainer.class).getStockDiaryInsert().exec(diary);
                }
            }   
            // Y borramos el ticket definitivamente
            m_App.lookupDataLogic(SentenceContainer.class).getTicketDelete().exec(new Integer(ticket.getId()));
        } catch (BasicException be) {
        }
    }    
}