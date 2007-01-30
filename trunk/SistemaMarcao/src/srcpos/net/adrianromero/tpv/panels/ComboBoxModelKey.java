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

package net.adrianromero.tpv.panels;

import javax.swing.*;
import java.util.*;

public class ComboBoxModelKey extends AbstractListModel implements ComboBoxModel {  
   
    private ArrayList<ComboItem> m_aData;
    private ComboItem m_selected;
    
    /** Creates a new instance of ComboBoxValModel */
    public ComboBoxModelKey() {
        m_aData = new ArrayList();
        m_selected = null;
    }

    public void add(Object key, Object textvalue) {
        m_aData.add(new ComboItem(key, textvalue));        
    }
    
    public Object getSelectedKey() {
        if (m_selected == null) {
            return null;
        } else {
            return m_selected.getKey();
        }
    }
    public Object getSelectedValue() {
        if (m_selected == null) {
            return null;
        } else {
            return m_selected.getValue();
        }
    }
    public void setSelectedKey(Object value) {
        if (value != null) {
            Iterator it = m_aData.iterator();
            while (it.hasNext()) {
                ComboItem v = (ComboItem) it.next();
                if (value.equals(v.getKey())) {
                    setSelectedItem(v);
                    return;
                }
            }           
        }
        setSelectedItem(null);
    }
    
    public Object getElementAt(int index) {
        return m_aData.get(index);
    }
    
    public Object getSelectedItem() {
        return m_selected;
    }
    
    public int getSize() {
        return m_aData.size();
    }
    
    public void setSelectedItem(Object anItem) {
        
        if ((m_selected != null && !m_selected.equals(anItem)) || m_selected == null && anItem != null) {
            m_selected = (ComboItem) anItem;
            fireContentsChanged(this, -1, -1);
        }
    }
    
    public static class ComboItem {
        
        private Object m_oKey;
        private Object m_oValue;
        
        private ComboItem(Object key, Object value) {
            m_oKey = key;
            m_oValue = value;
        }
        public Object getKey() {
            return m_oKey;
        }
        public Object getValue() {
            return m_oValue;
        }
        public String toString() {
            return m_oValue.toString();
        }
    }  
}
