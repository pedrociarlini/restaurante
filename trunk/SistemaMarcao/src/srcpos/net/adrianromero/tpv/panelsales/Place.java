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

import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import net.adrianromero.data.gui.NullIcon;
import net.adrianromero.tpv.ticket.TicketInfo;
import net.adrianromero.data.loader.DataRead;
import net.adrianromero.data.loader.SerializableRead;
import net.adrianromero.basic.BasicException;

public class Place implements SerializableRead, java.io.Serializable {
    
    private static final Icon ICO_OCU = new ImageIcon(Place.class.getResource("/net/adrianromero/images/edit_group.png"));
    private static final Icon ICO_FRE = new NullIcon(22, 22);
    
    private String m_sName;
    private int m_ix;
    private int m_iy;
    private int m_ifloor;
    
    private TicketInfo m_ticket;
    private JButton m_btn;
        
    /** Creates a new instance of TablePlace */
    public Place() {
    }        
    
    public void readValues(DataRead dr) throws BasicException {
        m_sName = dr.getString(1);
        m_ix = dr.getInt(2).intValue();
        m_iy = dr.getInt(3).intValue();
        m_ifloor = dr.getInt(4).intValue();
        
        m_ticket = null;
        m_btn = new JButton();

        m_btn.setFocusPainted(false);
        m_btn.setFocusable(false);
        m_btn.setRequestFocusEnabled(false);
        m_btn.setHorizontalTextPosition(SwingConstants.CENTER);
        m_btn.setVerticalTextPosition(SwingConstants.BOTTOM);            
        //setMargin(new Insets(8, 14, 8, 14));
        m_btn.setIcon(ICO_FRE);
        m_btn.setText(m_sName);
    }

    public String getName() { return m_sName; }
    // public void setName(String sValue) { m_sName = sValue; }
    public int getX() { return m_ix; }
    // public void setX(int iValue) {m_ix = iValue; }
    public int getY() { return m_iy; }
    // public void setY(int iValue) { m_iy = iValue; }
    public int getFloor() { return m_ifloor; }
    // public void setFloor(int iValue) { m_ifloor = iValue; }    
    public JButton getButton() { return m_btn; }

    public TicketInfo getTicket() {
        return m_ticket;
    }   
    public void setTicket(TicketInfo ticket) {
        m_ticket = ticket;
        m_btn.setIcon(m_ticket == null ? ICO_FRE : ICO_OCU); // el icono tambien depende si esta o no bloqueado...
    }     
    public void setButtonBounds() {
        Dimension d = m_btn.getPreferredSize();
        m_btn.setBounds(m_ix - d.width / 2, m_iy - d.height / 2, d.width, d.height); //  btn.getWidth(), btn.getHeight());
    }
}    

    
