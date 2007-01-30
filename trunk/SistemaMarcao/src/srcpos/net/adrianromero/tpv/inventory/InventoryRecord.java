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

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.adrianromero.format.Formats;

public class InventoryRecord {
    
    private Date m_dDate;
    private Integer m_iReason;
    private String m_sReason;
    private Integer m_iLocationOri;
    private String m_sLocationOri;
    private Integer m_iLocationDes;
    private String m_sLocationDes;
    
    private List<InventoryLine> m_invlines;
    
    /** Creates a new instance of InventoryRecord */
    public InventoryRecord(Date d, Integer iReason, String sReason, Integer iLocation, String sLocation, List<InventoryLine> invlines) {
        m_dDate = d;
        m_iReason = iReason;
        m_sReason = sReason;
        m_iLocationOri = iLocation;
        m_sLocationOri = sLocation;
        m_iLocationDes = null;
        m_sLocationDes = null;
        m_invlines = invlines;
    }
    
    public Date getDate() {
        return m_dDate;
    }   
    public Integer getReason() {
        return m_iReason;
    }    
    public Integer getLocation() {
        return m_iLocationOri;
    }   
    
    public boolean isInput() {
        return m_iReason.intValue() > 0;
    }
    
    public List<InventoryLine> getLines() {
        return m_invlines;
    }   
    
    public double getSubTotal() {
        double dSuma = 0.0;
        InventoryLine oLine;            
        for (Iterator<InventoryLine> i = m_invlines.iterator(); i.hasNext();) {
            oLine = i.next();
            dSuma += oLine.getSubValue();
        }        
        return dSuma;
    }
    
    public String printDate() {
        return Formats.TIMESTAMP.formatValue(m_dDate);
    }    
    public String printLocation() {
        return m_sLocationOri;
    }
    public String printReason() {
        return m_sReason;
    }
    
    public String printSubTotal() {
        return Formats.CURRENCY.formatValue(new Double(getSubTotal()));
    }    
}
