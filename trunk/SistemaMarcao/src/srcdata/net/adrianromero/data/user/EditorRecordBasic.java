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

import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.text.JTextComponent;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.gui.ComboBoxValModel;
import net.adrianromero.data.gui.JImageEditor;

public class EditorRecordBasic implements EditorRecord {

    
    private DirtyManager m_Dirty;    
    private ArrayList<EditorItem> m_aitems; 
    private EditorView m_view; 
    
    /** Creates a new instance of EditorRecordBasic */
    public EditorRecordBasic(EditorView view) {
        m_Dirty = new DirtyManager();
        m_aitems = new ArrayList<EditorItem>();     
        m_view = view;
        
        m_view.init(this);
        
        writeValueEOF();
    }
    
    public void addTextComponent(JTextComponent c, int iEditable) {
        m_aitems.add(new EditorItemTextComponent(c, iEditable));
        c.getDocument().addDocumentListener(m_Dirty);
    } 
    public void addComboBox(JComboBox c, int iEditable) {
        m_aitems.add(new EditorItemComboBox(c, iEditable));
        c.addActionListener(m_Dirty);
    }
    public void addCheckBox(JCheckBox c, int iEditable) {
        m_aitems.add(new EditorItemCheckBox(c, iEditable));
        c.addActionListener(m_Dirty);
    }
    public void addImageEditor(JImageEditor c, int iEditable) {
        m_aitems.add(new EditorItemImageEditor(c, iEditable));
        c.addPropertyChangeListener("image", m_Dirty);
    }
    public void addObjectContainer(ObjectContainer c) {
        m_aitems.add(new EditorItemObjectContainer(c));
        c.addPropertyChangeListener(m_Dirty);
    }
    
    public void writeValueEOF() {
        for (EditorItem i : m_aitems) {
            i.setValueNull();
            i.setEnabled(EditorItem.EDITABLE_EOF);
        }
    }
    public void writeValueInsert() {
        for (EditorItem i : m_aitems) {
            i.setValueNull();
            i.setEnabled(EditorItem.EDITABLE_INSERT);
        }
    }
    public void writeValueDelete(Object value) {
        m_view.writeValue(value);
        for (EditorItem i : m_aitems) {
            i.setEnabled(EditorItem.EDITABLE_DELETE);
        }
    }    
    public void writeValueEdit(Object value) {
        m_view.writeValue(value);
        for (EditorItem i : m_aitems) {          
            i.setEnabled(EditorItem.EDITABLE_EDIT);
        }
    }
    
    public Object createValue() throws BasicException {
        return m_view.readValue();
    }    
    
    public DirtyManager getDirtyManager() {
        return m_Dirty;
    }
    
}
