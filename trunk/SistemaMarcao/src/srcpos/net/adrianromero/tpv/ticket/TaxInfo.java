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

package net.adrianromero.tpv.ticket;
import java.io.Serializable;
import net.adrianromero.data.loader.DataRead;
import net.adrianromero.data.loader.SerializableRead;
import net.adrianromero.data.loader.DataWrite;
import net.adrianromero.format.Formats;
import net.adrianromero.data.loader.SerializableWrite;
import net.adrianromero.basic.BasicException;

public class TaxInfo implements SerializableRead, SerializableWrite, Serializable {

    private Integer m_iID;
    private String m_sName;
    private double m_dRate;
    
    /** Creates new TaxInfo */
    public TaxInfo() {
        m_iID = null;
        m_sName = null;
        m_dRate = 0.0;         
    }
    
    /** Creates new TaxInfo */
    public TaxInfo(Integer iID, String sName, double dRate) {
        m_iID = iID;
        m_sName = sName;
        m_dRate = dRate;         
    }
    
    public void readValues(DataRead dr) throws BasicException {
        m_iID = dr.getInt(1);
        m_sName = dr.getString(2);
        m_dRate = dr.getDouble(3).doubleValue();
    }
    
    public void writeValues(DataWrite dp) throws BasicException {
        dp.setInt(1, m_iID);
        dp.setString(2, m_sName);
        dp.setDouble(3, new Double(m_dRate));
    }
    
    public void setID(Integer iID) {
        m_iID = iID;
    }
    
    public Integer getID() {
        return m_iID;
    }

    public String getName() {
        return m_sName;
    }
    
    public void setName(String sName) {
        m_sName = sName;
    }
    
    public double getRate() {
        return m_dRate;
    }
    
    public void setRate(double dValue) {
        m_dRate = dValue;
    }

    public String toString(){
        return Formats.INT.formatValue(m_iID) + " - " + Formats.STRING.formatValue(m_sName);
    }
    
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	} else if (obj instanceof TaxInfo) {
            TaxInfo t = (TaxInfo) obj;
            
            // el id
            if (m_iID == null) {
                if (t.m_iID != null) return false;
            } else {
                if (!m_iID.equals(t.m_iID)) return false;
            }
            
            // el nombre
            if (m_sName == null) {
                if (t.m_sName != null) return false;
            } else {
                if (!m_sName.equals(t.m_sName)) return false;
            }          
            
            // el porcentage
            if (m_dRate != t.m_dRate) return false;
            
            return true;
        } else {
            return false;
        }           
    }
    
    public int hashCode() {      
        return (m_iID == null ? 0 : m_iID.hashCode()) + (m_sName == null ? 0 : m_sName.hashCode()) + new Double(m_dRate).hashCode();
    }    
}
