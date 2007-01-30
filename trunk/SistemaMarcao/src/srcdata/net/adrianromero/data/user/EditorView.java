/*
 * EditorView.java
 *
 * Created on 3 de agosto de 2006, 22:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.adrianromero.data.user;

import javax.swing.JComponent;
import net.adrianromero.basic.BasicException;

public interface EditorView {
    
    public void init(EditorRecordBasic editor);
    public void writeValue(Object value);
    public Object readValue() throws BasicException ;
    public JComponent getComponent();
}
