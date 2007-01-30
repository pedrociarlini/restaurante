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

package net.adrianromero.data.user;

import java.awt.image.BufferedImage;
import javax.swing.text.JTextComponent;
import net.adrianromero.data.gui.JImageEditor;

public class EditorItemImageEditor implements EditorItem {
    
    private JImageEditor m_c;
    private int m_ieditable;
    
    /** Creates a new instance of EditorItemImageEditor */
    public EditorItemImageEditor(JImageEditor c, int ieditable) {
        m_c = c;
        m_ieditable = ieditable;
    }
    public void setValueNull() {
        m_c.setImage(null);
    }
    public void setValue(Object value) {
        m_c.setImage((BufferedImage) value);
    }
    public void setEnabled(int ieditable) {
        m_c.setEnabled((ieditable & m_ieditable) != 0);
    }
    
}
