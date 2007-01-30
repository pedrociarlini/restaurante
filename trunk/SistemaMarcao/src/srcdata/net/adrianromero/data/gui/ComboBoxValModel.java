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
 * ComboBoxValModel.java
 *
 * Created on 22 de marzo de 2004, 12:36
 */

package net.adrianromero.data.gui;

import javax.swing.*;
import java.util.*;
import net.adrianromero.data.loader.IKeyGetter;

/**
 *
 * @author  adrian
 */
public class ComboBoxValModel extends AbstractListModel implements ComboBoxModel {  
   
    private List m_aData;
    private IKeyGetter m_keygetter;
    private Object m_selected;
    
    /** Creates a new instance of ComboBoxValModel */
    public ComboBoxValModel(List aData, IKeyGetter keygetter) {
        m_aData = aData;
        m_keygetter = keygetter;
        m_selected = null;
    }
    public ComboBoxValModel(IKeyGetter keygetter) {
        this(new ArrayList(), keygetter);
    }
    
    public void refresh(List aData) {
        m_aData = aData;
        m_selected = null;
    }
    
    public Object getSelectedKey() {
        if (m_selected == null) {
            return null;
        } else {
            return m_keygetter.getKey(m_selected);  // Si casca, excepcion parriba
        }
    }
    
    public void setSelectedKey(Object aKey) {
        setSelectedItem(getElementByKey(aKey));
//        if (aKey != null) {
//            Iterator it = m_aData.iterator();
//            while (it.hasNext()) {
//                Object value = it.next();
//                if (aKey.equals(m_keygetter.getKey(value))) {
//                    setSelectedItem(value);
//                    return;
//                }
//            }           
//        }
//        setSelectedItem(null);
    }
    
    public Object getElementByKey(Object aKey) {
        if (aKey != null) {
            Iterator it = m_aData.iterator();
            while (it.hasNext()) {
                Object value = it.next();
                if (aKey.equals(m_keygetter.getKey(value))) {
                    return value;
                }
            }           
        }
        return null;
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
            m_selected = anItem;
            fireContentsChanged(this, -1, -1);
        }
    }
    
}
