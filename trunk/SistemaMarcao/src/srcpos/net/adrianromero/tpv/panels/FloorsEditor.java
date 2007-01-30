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

import java.awt.image.BufferedImage;
import javax.swing.*;
import net.adrianromero.tpv.forms.AppLocal;
import net.adrianromero.format.Formats;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.user.EditorRecord;
import net.adrianromero.data.user.DirtyManager;


/**
 *
 * @author  Adrian
 */
public class FloorsEditor extends JPanel implements EditorRecord {
    
    private DirtyManager m_Dirty = new DirtyManager();
    
    /** Creates new form FloorsEditor */
    public FloorsEditor() {
        initComponents();
        
        m_Dirty = new DirtyManager();        
        m_jId.getDocument().addDocumentListener(m_Dirty);
        m_jName.getDocument().addDocumentListener(m_Dirty);
        m_jImage.addPropertyChangeListener("image", m_Dirty);
        
//        JLabel jlblDirty = new JLabelDirty(m_Dirty);
//        jlblDirty.setBounds(0, 0, 16, 16);
//        add(jlblDirty);
        
        writeValueEOF();
    }

    public void writeValueEOF() {
        m_jId.setText(null);
        m_jName.setText(null);
        m_jImage.setImage(null);
        m_jId.setEnabled(false);
        m_jName.setEnabled(false);
        m_jImage.setEnabled(false);
    }  
    public void writeValueInsert() {
        m_jId.setText(null);
        m_jName.setText(null);
        m_jImage.setImage(null);
        m_jId.setEnabled(true);
        m_jName.setEnabled(true);
        m_jImage.setEnabled(true);
    }
    public void writeValueDelete(Object value) {
        Object[] floor = (Object[]) value;
        m_jId.setText(Formats.INT.formatValue(floor[0]));
        m_jName.setText(Formats.STRING.formatValue(floor[1]));
        m_jImage.setImage((BufferedImage) floor[2]);
        m_jId.setEnabled(false);
        m_jName.setEnabled(false);
        m_jImage.setEnabled(false);
    }    
    public void writeValueEdit(Object value) {
        Object[] floor = (Object[]) value;
        m_jId.setText(Formats.INT.formatValue(floor[0]));
        m_jName.setText(Formats.STRING.formatValue(floor[1]));
        m_jImage.setImage((BufferedImage) floor[2]);
        m_jId.setEnabled(false);
        m_jName.setEnabled(true);
        m_jImage.setEnabled(true);
    }

    public Object createValue() throws BasicException {
        
        Object[] floor = new Object[3];

        floor[0] = Formats.INT.parseValue(m_jId.getText());
        floor[1] = m_jName.getText();
        floor[2] = m_jImage.getImage();
        return floor;
    }    
    
    public DirtyManager getDirtyManager() {
        return m_Dirty;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel2 = new javax.swing.JLabel();
        m_jId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        m_jName = new javax.swing.JTextField();
        m_jImage = new net.adrianromero.data.gui.JImageEditor();

        setLayout(null);

        jLabel2.setText(AppLocal.getIntString("label.floorid"));
        add(jLabel2);
        jLabel2.setBounds(20, 20, 90, 20);

        add(m_jId);
        m_jId.setBounds(110, 20, 180, 19);

        jLabel3.setText(AppLocal.getIntString("label.floorname"));
        add(jLabel3);
        jLabel3.setBounds(20, 50, 90, 20);

        add(m_jName);
        m_jName.setBounds(110, 50, 180, 19);

        add(m_jImage);
        m_jImage.setBounds(20, 80, 520, 400);

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField m_jId;
    private net.adrianromero.data.gui.JImageEditor m_jImage;
    private javax.swing.JTextField m_jName;
    // End of variables declaration//GEN-END:variables
    
}
