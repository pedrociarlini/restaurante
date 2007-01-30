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

package net.adrianromero.tpv.admin;

import javax.swing.*;
import net.adrianromero.tpv.forms.AppLocal;
import java.awt.CardLayout;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.loader.ImageUtils;
import net.adrianromero.data.user.DirtyManager;
import net.adrianromero.data.user.EditorItem;
import net.adrianromero.data.user.EditorRecordBasic;
import net.adrianromero.data.user.EditorView;
import net.adrianromero.tpv.util.Base64;

public class ResourcesView extends JPanel implements EditorView {
     
    public static final int RESOURCE_TEXT = 0;
    public static final int RESOURCE_IMAGE = 1;
    public static final int RESOURCE_BINARY = 2;
    
//    private DirtyManager m_Dirty;
    
    private String m_sLabelText;
    private String m_sLabelImage;
    private String m_sLabelBinary;
        
    /** Creates new form ResourcesEditor */
    public ResourcesView() {
        initComponents();
        
        m_sLabelText = "Text";
        m_sLabelImage = "Image";
        m_sLabelBinary = "Binary";
        
        m_jType.addItem(m_sLabelText);
        m_jType.addItem(m_sLabelImage);
        m_jType.addItem(m_sLabelBinary);
    }
    
    public void init(EditorRecordBasic editor) {
        editor.addTextComponent(m_jName, EditorItem.EDITABLE_PK);
        editor.addComboBox(m_jType, EditorItem.EDITABLE_NORMAL);
        editor.addTextComponent(m_jText, EditorItem.EDITABLE_NORMAL);
        editor.addImageEditor(m_jImage, EditorItem.EDITABLE_NORMAL);
    }    
    
    public void writeValue(Object value) {
        
        Object[] resource = (Object[]) value;
        m_jName.setText((String) resource[0]);
        switch (((Integer) resource[1]).intValue()) {
        case RESOURCE_TEXT: // texto
            m_jType.setSelectedItem(m_sLabelText);
            try {
                m_jText.setText(resource[2] == null
                        ? null
                        : new String((byte[]) resource[2], "UTF-8"));
            } catch (java.io.UnsupportedEncodingException eu) {
                m_jText.setText(null);
            }
            m_jText.setCaretPosition(0);
            m_jImage.setImage(null);
            break;
        case RESOURCE_IMAGE: // imagen
            m_jType.setSelectedItem(m_sLabelImage);
            m_jText.setText(null);
            m_jImage.setImage(ImageUtils.readImage((byte[]) resource[2]));
            break;
        case RESOURCE_BINARY: // binario
            m_jType.setSelectedItem(m_sLabelBinary);
            m_jText.setText(resource[2] == null
                    ? null
                    : Base64.encode((byte[]) resource[2]));
            m_jText.setCaretPosition(0);
            m_jImage.setImage(null);
            break;
            
        default:
            m_jType.setSelectedItem(m_sLabelText);
            m_jText.setText(null);
            m_jImage.setImage(null);
            break;
        }
    }
    
    public Object readValue() throws BasicException {
        Object[] resource = new Object[3];

        resource[0] = m_jName.getText();
        if (m_jType.getSelectedItem() == m_sLabelText) {
            resource[1] = new Integer(RESOURCE_TEXT);
            try {
                resource[2] = m_jText.getText().getBytes("UTF-8");
            } catch (java.io.UnsupportedEncodingException eu) {
                resource[2] = null;
            }
        } else if (m_jType.getSelectedItem() == m_sLabelImage) {
            resource[1] = new Integer(RESOURCE_IMAGE);
            resource[2] = ImageUtils.writeImage(m_jImage.getImage());
        } else if (m_jType.getSelectedItem() == m_sLabelBinary) {
            resource[1] = new Integer(RESOURCE_BINARY);
            resource[2] = Base64.decode(m_jText.getText());
        } else {
            resource[1] = null;
            resource[2] = null;
        }
        return resource;
    }
    
    public JComponent getComponent() {
        return this;
    }
    
//    public void writeValueEOF() {
//        m_jName.setText(null);
//        m_jType.setSelectedItem(null);
//        m_jText.setText(null);
//        m_jImage.setImage(null);
//        m_jName.setEnabled(false);
//        m_jType.setEnabled(false);
//        m_jText.setEnabled(false);
//        m_jImage.setEnabled(false);
//    }  
//    public void writeValueInsert() {
//        m_jName.setText(null);
//        m_jType.setSelectedItem(m_sLabelText);
//        m_jText.setText(null);
//        m_jImage.setImage(null);
//        m_jName.setEnabled(true);
//        m_jType.setEnabled(true);
//        m_jText.setEnabled(true);
//        m_jImage.setEnabled(true);
//    }    
//    public void writeValueDelete(Object value) {
//        Object[] resource = (Object[]) value;
//        m_jName.setText((String) resource[0]);
//        switch (((Integer) resource[1]).intValue()) {
//        case RESOURCE_TEXT: // texto
//            m_jType.setSelectedItem(m_sLabelText);
//            try {
//                m_jText.setText(new String((byte[]) resource[2], "UTF-8"));
//                m_jText.setCaretPosition(0);
//            } catch (java.io.UnsupportedEncodingException eu) {
//                m_jText.setText(null);
//            }
//            m_jImage.setImage(null);
//            break;
//        case RESOURCE_IMAGE: // imagen
//            m_jType.setSelectedItem(m_sLabelImage);
//            m_jText.setText(null);
//            m_jImage.setImage(ImageUtils.readImage((byte[]) resource[2]));
//            break;
//        case RESOURCE_BINARY: // binario
//            m_jType.setSelectedItem(m_sLabelBinary);
//            m_jText.setText(Base64.encode((byte[]) resource[2]));
//            m_jText.setCaretPosition(0);
//            m_jImage.setImage(null);
//            break;
//        default:
//            m_jType.setSelectedItem(m_sLabelText);
//            m_jText.setText(null);
//            m_jImage.setImage(null);
//            break;
//        }
//        m_jName.setEnabled(false);
//        m_jType.setEnabled(false);
//        m_jText.setEnabled(false);
//        m_jImage.setEnabled(false);   
//    }  
//    public void writeValueEdit(Object value) {
//        Object[] resource = (Object[]) value;
//        m_jName.setText((String) resource[0]);
//        switch (((Integer) resource[1]).intValue()) {
//        case RESOURCE_TEXT: // texto
//            m_jType.setSelectedItem(m_sLabelText);
//            try {
//                m_jText.setText(new String((byte[]) resource[2], "UTF-8"));
//                m_jText.setCaretPosition(0);
//            } catch (java.io.UnsupportedEncodingException eu) {
//                m_jText.setText(null);
//            }
//            m_jImage.setImage(null);
//            break;
//        case RESOURCE_IMAGE: // imagen
//            m_jType.setSelectedItem(m_sLabelImage);
//            m_jText.setText(null);
//            m_jImage.setImage(ImageUtils.readImage((byte[]) resource[2]));
//            break;
//        case RESOURCE_BINARY: // binario
//            m_jType.setSelectedItem(m_sLabelBinary);
//            m_jText.setText(Base64.encode((byte[]) resource[2]));
//            m_jText.setCaretPosition(0);
//            m_jImage.setImage(null);
//            break;
//            
//        default:
//            m_jType.setSelectedItem(m_sLabelText);
//            m_jText.setText(null);
//            m_jImage.setImage(null);
//            break;
//        }
//        m_jName.setEnabled(false);
//        m_jType.setEnabled(true);
//        m_jText.setEnabled(true);
//        m_jImage.setEnabled(true); 
//    }    
//
//    public Object createValue() throws BasicException {
//
//        Object[] resource = new Object[3];
//
//        resource[0] = m_jName.getText();
//        if (m_jType.getSelectedItem() == m_sLabelText) {
//            resource[1] = new Integer(RESOURCE_TEXT);
//            try {
//                resource[2] = m_jText.getText().getBytes("UTF-8");
//            } catch (java.io.UnsupportedEncodingException eu) {
//                resource[2] = null;
//            }
//        } else if (m_jType.getSelectedItem() == m_sLabelImage) {
//            resource[1] = new Integer(RESOURCE_IMAGE);
//            resource[2] = ImageUtils.writeImage(m_jImage.getImage());
//        } else if (m_jType.getSelectedItem() == m_sLabelBinary) {
//            resource[1] = new Integer(RESOURCE_BINARY);
//            resource[2] = Base64.decode(m_jText.getText());
//        } else {
//            resource[1] = null;
//            resource[2] = null;
//        }
//        return resource;
//    }    
//    
//    public DirtyManager getDirtyManager() {
//        return m_Dirty;
//    }
    
    private void showView(String view) {
        CardLayout cl = (CardLayout)(m_jContainer.getLayout());
        cl.show(m_jContainer, view);  
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        m_jGroupType = new javax.swing.ButtonGroup();
        m_jName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        m_jType = new javax.swing.JComboBox();
        m_jContainer = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        m_jText = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        m_jImage = new net.adrianromero.data.gui.JImageEditor();

        setLayout(null);

        add(m_jName);
        m_jName.setBounds(110, 20, 180, 19);

        jLabel2.setText(AppLocal.getIntString("label.resname"));
        add(jLabel2);
        jLabel2.setBounds(20, 20, 90, 14);

        m_jType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jTypeActionPerformed(evt);
            }
        });

        add(m_jType);
        m_jType.setBounds(300, 20, 90, 20);

        m_jContainer.setLayout(new java.awt.CardLayout());

        m_jText.setFont(new java.awt.Font("DialogInput", 0, 13));
        jScrollPane1.setViewportView(m_jText);

        m_jContainer.add(jScrollPane1, "text");

        m_jContainer.add(jPanel1, "null");

        m_jContainer.add(m_jImage, "image");

        add(m_jContainer);
        m_jContainer.setBounds(20, 50, 440, 430);

    }// </editor-fold>//GEN-END:initComponents

    private void m_jTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jTypeActionPerformed
        // TODO add your handling code here:
        if (m_jType.getSelectedItem() == m_sLabelText) {
            showView("text");
        } else if (m_jType.getSelectedItem() == m_sLabelImage) {
            showView("image");
        } else if (m_jType.getSelectedItem() == m_sLabelBinary) {
            showView("text");
        } else {
            showView("null");
        }        
    }//GEN-LAST:event_m_jTypeActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel m_jContainer;
    private javax.swing.ButtonGroup m_jGroupType;
    private net.adrianromero.data.gui.JImageEditor m_jImage;
    private javax.swing.JTextField m_jName;
    private javax.swing.JTextArea m_jText;
    private javax.swing.JComboBox m_jType;
    // End of variables declaration//GEN-END:variables
    
}
