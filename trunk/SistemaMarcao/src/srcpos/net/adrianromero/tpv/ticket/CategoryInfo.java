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

/*
 * CategoryInfo.java
 *
 * Created on 4 de marzo de 2002, 12:29
 */

package net.adrianromero.tpv.ticket;
import java.awt.image.*;
import net.adrianromero.data.loader.DataRead;
import net.adrianromero.data.loader.SerializableRead;
import net.adrianromero.data.loader.DataWrite;
import net.adrianromero.format.Formats;
import net.adrianromero.data.loader.SerializableWrite;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.loader.ImageUtils;

/**
 *
 * @author  Adrian
 * @version 
 */
public class CategoryInfo implements SerializableRead, SerializableWrite {

    private Integer m_iID;
    private String m_sName;
    private BufferedImage m_Image;
    
    /** Creates new CategoryInfo */
    public CategoryInfo() {
        m_iID = null;
        m_sName = null;
        m_Image = null;
    }
    
    public void readValues(DataRead dr) throws BasicException {
        m_iID = dr.getInt(1);
        m_sName = dr.getString(2);
        m_Image = ImageUtils.readImage(dr.getBytes(3));
    }
    
    public void writeValues(DataWrite dp) throws BasicException {
        dp.setInt(1, m_iID);
        dp.setString(2, m_sName);
        dp.setBytes(3, ImageUtils.writeImage(m_Image));        
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
    
    public BufferedImage getImage() {
        return m_Image;
    }
    
    public void setImage(BufferedImage img) {
        m_Image = img;
    }    
    public String toString(){
        return Formats.INT.formatValue(m_iID) + " - " + Formats.STRING.formatValue(m_sName);
    }
}
