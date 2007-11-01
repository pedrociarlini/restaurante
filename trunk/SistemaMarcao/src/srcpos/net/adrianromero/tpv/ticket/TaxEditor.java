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

package net.adrianromero.tpv.ticket;

import javax.swing.*;

import net.adrianromero.tpv.forms.AppLocal;
import net.adrianromero.format.Formats;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.user.EditorRecord;
import net.adrianromero.data.user.DirtyManager;

public class TaxEditor extends JPanel implements EditorRecord {
    
    private DirtyManager m_Dirty;
    
    /** Creates new form taxEditor */
    public TaxEditor() {
        initComponents();
        
        m_Dirty = new DirtyManager();
        m_jId.getDocument().addDocumentListener(m_Dirty);
        m_jName.getDocument().addDocumentListener(m_Dirty);
        m_jRate.getDocument().addDocumentListener(m_Dirty);
        
        writeValueEOF();
    }
    public void writeValueEOF() {
        m_jId.setText(null);
        m_jName.setText(null);
        m_jRate.setText(null);
        m_jId.setEnabled(false);
        m_jName.setEnabled(false);
        m_jRate.setEnabled(false);
    }
    public void writeValueInsert() {
        m_jId.setText(null);
        m_jName.setText(null);
        m_jRate.setText(null);
        m_jId.setEnabled(true);
        m_jName.setEnabled(true);
        m_jRate.setEnabled(true);
    }
    public void writeValueDelete(Object value) {

        Object[] tax = (Object[]) value;
        m_jId.setText(Formats.INT.formatValue(tax[0]));
        m_jName.setText(Formats.STRING.formatValue(tax[1]));
        m_jRate.setText(Formats.PERCENT.formatValue(tax[2]));
        m_jId.setEnabled(false);
        m_jName.setEnabled(false);
        m_jRate.setEnabled(false);
    }    
    public void writeValueEdit(Object value) {

        Object[] tax = (Object[]) value;
        m_jId.setText(Formats.INT.formatValue(tax[0]));
        m_jName.setText(Formats.STRING.formatValue(tax[1]));
        m_jRate.setText(Formats.PERCENT.formatValue(tax[2]));
        m_jId.setEnabled(false);
        m_jName.setEnabled(true);
        m_jRate.setEnabled(true);
    }

    public Object createValue() throws BasicException {
        
        Object[] tax = new Object[4];

        tax[0] = Formats.INT.parseValue(m_jId.getText());
        tax[1] = m_jName.getText();
        tax[2] = Formats.PERCENT.parseValue(m_jRate.getText());

        return tax;
    }    
    
    public DirtyManager getDirtyManager() {
        return m_Dirty;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        m_jName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        m_jId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        m_jRate = new javax.swing.JTextField();

        setLayout(null);

        add(m_jName);
        m_jName.setBounds(100, 50, 200, 19);

        jLabel2.setText(AppLocal.getIntString("label.dutyname"));
        add(jLabel2);
        jLabel2.setBounds(20, 50, 80, 14);

        jLabel1.setText(AppLocal.getIntString("label.dutyid"));
        add(jLabel1);
        jLabel1.setBounds(20, 20, 80, 14);

        add(m_jId);
        m_jId.setBounds(100, 20, 60, 19);

        jLabel3.setText(AppLocal.getIntString("label.dutyrate"));
        add(jLabel3);
        jLabel3.setBounds(20, 80, 80, 14);

        add(m_jRate);
        m_jRate.setBounds(100, 80, 60, 19);

    }//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField m_jId;
    private javax.swing.JTextField m_jName;
    private javax.swing.JTextField m_jRate;
    // End of variables declaration//GEN-END:variables
    
}