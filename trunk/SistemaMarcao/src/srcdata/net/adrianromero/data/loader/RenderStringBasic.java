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
 * StringRenderBasic.java
 *
 * Created on 14 de septiembre de 2004, 19:33
 */

package net.adrianromero.data.loader;

import net.adrianromero.format.Formats;

/**
 *
 * @author  adrian
 */
public class RenderStringBasic implements IRenderString {
    
    private Formats[] m_aFormats;
    
    /** Creates a new instance of StringnizerBasic */
    public RenderStringBasic(Formats[] fmts) {
        m_aFormats = fmts; // los nulos se saltan el elemento del array
    }
    public String getRenderString(Object value) {
        
        if (value == null) {
            return null; 
        } else {
            Object [] avalue = (Object[]) value;
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < m_aFormats.length; i++) {
                if (m_aFormats[i] != null) {
                    String sval = m_aFormats[i].formatValue(avalue[i]); // el resultado de un formateo nunca es null
                    if (!"".equals(sval)) { 
                        if (sb.length() > 0) {
                            sb.append(" - ");
                        }
                        sb.append(sval);
                    }
                }
            }
            
            return sb.toString();
        }
    }    
}
