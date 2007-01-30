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

package net.adrianromero.tpv.inventory;

import javax.swing.JComponent;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.user.DirtyManager;
import net.adrianromero.data.user.EditorItem;
import net.adrianromero.data.user.EditorRecordBasic;
import net.adrianromero.data.user.EditorView;
import net.adrianromero.format.Formats;
import net.adrianromero.tpv.forms.AppLocal;

public class LocationsView extends javax.swing.JPanel implements EditorView {
    
    /** Creates new form LocationsEditor */
    public LocationsView() {
        initComponents();
    }
    public void init(EditorRecordBasic editor) {
        editor.addTextComponent(m_jId, EditorItem.EDITABLE_PK);
        editor.addTextComponent(m_jName, EditorItem.EDITABLE_NORMAL);
        editor.addTextComponent(m_jAddress, EditorItem.EDITABLE_NORMAL);
    }
    public void writeValue(Object value) {
        Object[] location = (Object[]) value;
        m_jId.setText(Formats.INT.formatValue(location[0]));
        m_jName.setText(Formats.STRING.formatValue(location[1]));
        m_jAddress.setText(Formats.STRING.formatValue(location[2]));
    }
    public Object readValue() throws BasicException {
        Object[] location = new Object[3];
        location[0] = Formats.INT.parseValue(m_jId.getText());
        location[1] = m_jName.getText();
        location[2] = m_jAddress.getText();
        return location;
    }
    public JComponent getComponent() {
        return this;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        m_jId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        m_jName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        m_jAddress = new javax.swing.JTextField();

        setLayout(null);

        jLabel1.setText(AppLocal.getIntString("label.locationid"));
        add(jLabel1);
        jLabel1.setBounds(20, 20, 80, 14);

        add(m_jId);
        m_jId.setBounds(100, 20, 60, 19);

        jLabel2.setText(AppLocal.getIntString("label.locationname"));
        add(jLabel2);
        jLabel2.setBounds(20, 50, 80, 14);

        add(m_jName);
        m_jName.setBounds(100, 50, 260, 19);

        jLabel3.setText(AppLocal.getIntString("label.locationaddress"));
        add(jLabel3);
        jLabel3.setBounds(20, 80, 80, 14);

        add(m_jAddress);
        m_jAddress.setBounds(100, 80, 260, 19);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField m_jAddress;
    private javax.swing.JTextField m_jId;
    private javax.swing.JTextField m_jName;
    // End of variables declaration//GEN-END:variables
    
}
