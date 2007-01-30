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

import javax.swing.JComboBox;
import net.adrianromero.data.gui.ComboBoxValModel;

public class EditorItemComboBox implements EditorItem {
    
    private JComboBox m_c;
    private int m_ieditable;
    
    /** Creates a new instance of EditorItemComboBox */
    public EditorItemComboBox(JComboBox c, int ieditable) {
        m_c = c;
        m_ieditable = ieditable;
    }
    public void setValueNull() {
        if (m_c.getModel() instanceof ComboBoxValModel) {
            ((ComboBoxValModel) m_c.getModel()).setSelectedKey(null);
        } else {
            m_c.setSelectedItem(null);
        }
    }
    public void setValue(Object value) {
        if (m_c.getModel() instanceof ComboBoxValModel) {
            ((ComboBoxValModel) m_c.getModel()).setSelectedKey(value);
        } else {
            m_c.setSelectedItem(value);
        }
    }
    public void setEnabled(int ieditable) {
        m_c.setEnabled((ieditable & m_ieditable) != 0);
    }
    
}
