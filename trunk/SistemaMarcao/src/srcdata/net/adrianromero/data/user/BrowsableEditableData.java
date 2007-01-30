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

import java.util.*;
import javax.swing.*;
import java.awt.Component;
import javax.swing.event.EventListenerList;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.loader.LocalRes;

public class BrowsableEditableData {
    
    public static final int ST_NORECORD = 0;
    public static final int ST_UPDATE = 1;
    public static final int ST_DELETE = 2;
    public static final int ST_INSERT = 3;
    
    private final static int INX_EOF = -1;
    
    private BrowsableData m_bd;
    
    protected EventListenerList listeners = new EventListenerList();

    private EditorRecord m_editorrecord;
    private int m_iState; // vinculado siempre al m_editorrecord
    private int m_iIndex;
    private boolean m_bIsAdjusting;
    
    /** Creates a new instance of BrowsableEditableData */
    public BrowsableEditableData(BrowsableData bd, EditorRecord ed) {
        m_bd = bd;

        m_editorrecord = ed;
        m_iState = ST_NORECORD;
        m_iIndex = INX_EOF; // En EOF
        m_bIsAdjusting = false;
        
        // Inicializo ?
        m_editorrecord.writeValueEOF();
        m_editorrecord.getDirtyManager().setDirty(false);
    }
    
    public BrowsableEditableData(ListProvider dataprov, SaveProvider saveprov, Comparator c, EditorRecord ed) {
        this(new BrowsableData(dataprov, saveprov, c), ed);
    }
    public BrowsableEditableData(ListProvider dataprov, SaveProvider saveprov, EditorRecord ed) {
        this(dataprov, saveprov, null, ed);
    }
    
    public final ListModel getListModel() {
        return m_bd;
    }
    public final boolean isAdjusting() {
        return m_bIsAdjusting || m_bd.isAdjusting();
    }
    
    public final Object getCurrentElement() {           
        return (m_iIndex >= 0 && m_iIndex < m_bd.getSize()) ? m_bd.getElementAt(m_iIndex) : null;
    }    
    public final int getIndex() {
        return m_iIndex;
    }   
    
    public final void addStateListener(StateListener l) {
        listeners.add(StateListener.class, l);
    }
    public final void removeStateListener(StateListener l) {
        listeners.remove(StateListener.class, l);
    }
    public final void addEditorListener(EditorListener l) {
        listeners.add(EditorListener.class, l);
    }
    public final void removeEditorListener(EditorListener l) {
        listeners.remove(EditorListener.class, l);
    }
    public final void addBrowseListener(BrowseListener l) {
        listeners.add(BrowseListener.class, l);
    }
    public final void removeBrowseListener(BrowseListener l) {
        listeners.remove(BrowseListener.class, l);
    }   
    public DirtyManager getDirtyManager() {
        return m_editorrecord.getDirtyManager();
    }
    
    public int getState() {
        return m_iState;
    }
    
    private void fireStateUpdate() { 
        EventListener[] l = listeners.getListeners(StateListener.class);
        int iState = getState();
        for (int i = 0; i < l.length; i++) {
            ((StateListener) l[i]).updateState(iState);	       
        }
    }
    protected void fireDataBrowse() { 
        
        m_bIsAdjusting = true;
        // Lanzamos los eventos...
        Object obj = getCurrentElement();
        int iIndex = getIndex();
        int iCount = m_bd.getSize();
        
        // actualizo el registro
        if (obj == null) {
            m_iState = ST_NORECORD;
            m_editorrecord.writeValueEOF();
        } else {
            m_iState = ST_UPDATE;
            m_editorrecord.writeValueEdit(obj);
        }
        m_editorrecord.getDirtyManager().setDirty(false);
        fireStateUpdate();   
        
        // Invoco a los Editor Listener
        EventListener[] l = listeners.getListeners(EditorListener.class);
        for (int i = 0; i < l.length; i++) {
            ((EditorListener) l[i]).updateValue(obj);
        }
        // Y luego a los Browse Listener
        l = listeners.getListeners(BrowseListener.class);
        for (int i = 0; i < l.length; i++) {
            ((BrowseListener) l[i]).updateIndex(iIndex, iCount);
        }
        m_bIsAdjusting = false;
    }
    
    public boolean canLoadData() {
        return m_bd.canLoadData();
    }
   
    public void refreshCurrent() {
        baseMoveTo(m_iIndex);
    }    
    public void moveTo(int i) throws BasicException {        
        saveData();
        if (m_iIndex != i) {
            baseMoveTo(i);
        }
    }    
    public void refreshData() throws BasicException {
        saveData();
        m_bd.refreshData();
        baseMoveTo(0);
    }    
    public void loadData() throws BasicException {
        saveData();
        m_bd.loadData();
        baseMoveTo(0);
    }
    public void unloadData() throws BasicException {
        saveData();
        m_bd.unloadData();
        baseMoveTo(0);
    }
  
    public void sort(Comparator c) throws BasicException {
        saveData();
        m_bd.sort(c);
        baseMoveTo(0);
    }
    public final void movePrev() throws BasicException {
        saveData();
        if (m_iIndex > 0) {        
            moveTo(m_iIndex - 1);
        }
    }
    public final void moveNext() throws BasicException {
        saveData();
        if (m_iIndex < m_bd.getSize() - 1) {        
            moveTo(m_iIndex + 1);
        }
    }
    public final void moveFirst() throws BasicException {
        saveData();
        if (m_bd.getSize() > 0) {
            moveTo(0);
        };
    }
    public final void moveLast() throws BasicException {
        saveData();
        if (m_bd.getSize() > 0) {
            moveTo(m_bd.getSize() - 1);
        }
    }
    public final int findNext(Finder f) throws BasicException {
        return m_bd.findNext(m_iIndex, f);
    }
    
//    public void updateValue(Object value) {
//        if (value == null) {
//            m_iState = ST_NORECORD;
//            m_editorrecord.writeValueEOF();
//        } else {
//            m_iState = ST_UPDATE;
//            m_editorrecord.writeValueEdit(value);
//        }
//        m_editorrecord.getDirtyManager().setDirty(false);
//        fireStateUpdate();            
//    }
    
    public void saveData() throws BasicException {
            
        if (m_editorrecord.getDirtyManager().isDirty()) {
            if (m_iState == ST_UPDATE) {
                updateCurrent(m_editorrecord.createValue());
            } else if (m_iState == ST_INSERT) {
                insertCurrent(m_editorrecord.createValue());
            } else if (m_iState == ST_DELETE) {
                removeCurrent();
            } // queda ST_NORECORD  
        }      
    }
      
    public void actionReloadCurrent(Component c) {        
        if (!m_editorrecord.getDirtyManager().isDirty() ||
                JOptionPane.showConfirmDialog(c, LocalRes.getIntString("message.changeslost"), LocalRes.getIntString("title.editor"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {  
            refreshCurrent();
        }             
    }
  
    public boolean actionClosingForm(Component c) throws BasicException {
        if (m_editorrecord.getDirtyManager().isDirty()) {
            int res = JOptionPane.showConfirmDialog(c, LocalRes.getIntString("message.wannasave"), LocalRes.getIntString("title.editor"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (res == JOptionPane.YES_OPTION) {
                saveData();
                return true;
            } else if (res == JOptionPane.NO_OPTION) {
                refreshCurrent();
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /*
     * Metodos publicos finales (algunos protegidos que podrian ser finales
     */
    
    public final void actionLoad() throws BasicException {
        loadData();
        if (m_bd.getSize() == 0) {
            actionInsert();
        }
    }
    
    public final void actionInsert() throws BasicException {
        // primero persistimos
        saveData();
        
        // Y nos ponemos en estado de insert
        int iCount = m_bd.getSize();
        m_iState = ST_INSERT;
        m_editorrecord.writeValueInsert();
        m_editorrecord.getDirtyManager().setDirty(false);
        fireStateUpdate(); // ?
    }
    
    public final void actionDelete() throws BasicException {
        // primero persistimos
        saveData();
        
        // Y nos ponemos en estado de delete
        Object obj = getCurrentElement();
        int iIndex = getIndex();
        int iCount = m_bd.getSize();
        if (iIndex >= 0 && iIndex < iCount) {
            m_iState = ST_DELETE;
            m_editorrecord.writeValueDelete(obj);
            m_editorrecord.getDirtyManager().setDirty(true);
            fireStateUpdate(); // ?
        }
    }  
    
    public final void removeCurrent() throws BasicException {
        baseMoveTo(m_bd.removeRecord(m_iIndex));
    }  
    public final void updateCurrent(Object value) throws BasicException {
        baseMoveTo(m_bd.updateRecord(m_iIndex, value));
    }
    public final void insertCurrent(Object value) throws BasicException { 
        baseMoveTo(m_bd.insertRecord(value));
    }  
    
    private final void baseMoveTo(int i) {
    // Este senor y el constructor a INX_EOF, son los unicos que tienen potestad de modificar m_iIndex.
        if (i >= 0 && i < m_bd.getSize()) {
            m_iIndex = i;
        } else {
            m_iIndex = INX_EOF;
        }
        fireDataBrowse();
    }    
}
