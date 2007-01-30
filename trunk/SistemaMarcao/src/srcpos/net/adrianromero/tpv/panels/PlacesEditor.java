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

import net.adrianromero.tpv.forms.AppView;
import net.adrianromero.tpv.forms.AppLocal;
import net.adrianromero.data.loader.TableDefinition;
import net.adrianromero.format.Formats;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.loader.Datas;
import net.adrianromero.data.gui.ComboBoxValModel;
import net.adrianromero.data.gui.ListCellRendererBasic;
import net.adrianromero.data.loader.SentenceList;
import net.adrianromero.data.user.EditorRecord;
import net.adrianromero.data.user.DirtyManager;

public class PlacesEditor extends JPanel implements EditorRecord {
    
    private SentenceList m_sentfloor;
    private ComboBoxValModel m_FloorModel;
    
    private TableDefinition tfloors;
    
    private DirtyManager m_Dirty = new DirtyManager();
    
    /** Creates new form PlacesEditor */
    public PlacesEditor(AppView app) {
        initComponents();
        
        // El modelo de impuestos
        tfloors = new TableDefinition(app.getConnection(),
            "FLOORS"
            , new int[] {0}
            , new String[] {"ID", "NAME"}
            , new Datas[] {Datas.INT, Datas.STRING}
            , new Formats[] {Formats.INT, Formats.STRING}
            , new Formats[] {Formats.INT, Formats.STRING}
        );  
        m_sentfloor = tfloors.getListSentence();
        m_FloorModel = new ComboBoxValModel(tfloors.getKeyGetterBasic());
        m_jFloor.setRenderer(new ListCellRendererBasic(tfloors.getRenderStringBasic()));

        m_jName.getDocument().addDocumentListener(m_Dirty);
        m_jFloor.addActionListener(m_Dirty);        
        m_jX.getDocument().addDocumentListener(m_Dirty);
        m_jY.getDocument().addDocumentListener(m_Dirty);
        
//        JLabel jlblDirty = new JLabelDirty(m_Dirty);
//        jlblDirty.setBounds(0, 0, 16, 16);
//        add(jlblDirty);
        
        writeValueEOF();
    }
    
    public void activate() {
        try {
            m_FloorModel = new ComboBoxValModel(m_sentfloor.list(), tfloors.getKeyGetterBasic());
            m_jFloor.setModel(m_FloorModel);
        } catch (BasicException eD) {
            // no hay validacion
        }        
    }
    
    public void writeValueEOF() {
        m_jName.setText(null);
        m_FloorModel.setSelectedKey(null);
        m_jX.setText(null);
        m_jY.setText(null);
        m_jName.setEnabled(false);
        m_jFloor.setEnabled(false);
        m_jX.setEnabled(false);
        m_jY.setEnabled(false);
    }  
    public void writeValueInsert() {
        m_jName.setText(null);
        m_FloorModel.setSelectedKey(null);
        m_jX.setText(null);
        m_jY.setText(null);
        m_jName.setEnabled(true);
        m_jFloor.setEnabled(true);
        m_jX.setEnabled(true);
        m_jY.setEnabled(true);
    } 
    public void writeValueDelete(Object value) {
        Object[] place = (Object[]) value;
        m_jName.setText(Formats.STRING.formatValue(place[0]));
        m_jX.setText(Formats.INT.formatValue(place[1]));
        m_jY.setText(Formats.INT.formatValue(place[2]));
        m_FloorModel.setSelectedKey(place[3]);
        m_jName.setEnabled(false);
        m_jFloor.setEnabled(false);
        m_jX.setEnabled(false);
        m_jY.setEnabled(false);        
    }    
    public void writeValueEdit(Object value) {
        Object[] place = (Object[]) value;
        m_jName.setText(Formats.STRING.formatValue(place[0]));
        m_jX.setText(Formats.INT.formatValue(place[1]));
        m_jY.setText(Formats.INT.formatValue(place[2]));
        m_FloorModel.setSelectedKey(place[3]);
        m_jName.setEnabled(false);
        m_jFloor.setEnabled(true);
        m_jX.setEnabled(true);
        m_jY.setEnabled(true);     
    }  
    public Object createValue() throws BasicException {
        Object[] place = new Object[4];
        place[0] = m_jName.getText();
        place[1] = Formats.INT.parseValue(m_jX.getText());
        place[2] = Formats.INT.parseValue(m_jY.getText());
        place[3] = m_FloorModel.getSelectedKey();
        return place;
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
        m_jName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        m_jX = new javax.swing.JTextField();
        m_jY = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        m_jFloor = new javax.swing.JComboBox();

        setLayout(null);

        jLabel2.setText(AppLocal.getIntString("label.placename"));
        add(jLabel2);
        jLabel2.setBounds(20, 20, 90, 14);

        add(m_jName);
        m_jName.setBounds(110, 20, 180, 19);

        jLabel3.setText(AppLocal.getIntString("label.placeposition"));
        add(jLabel3);
        jLabel3.setBounds(20, 80, 90, 14);

        add(m_jX);
        m_jX.setBounds(110, 80, 50, 19);

        add(m_jY);
        m_jY.setBounds(170, 80, 50, 19);

        jLabel1.setText(AppLocal.getIntString("label.placefloor"));
        add(jLabel1);
        jLabel1.setBounds(20, 50, 90, 14);

        add(m_jFloor);
        m_jFloor.setBounds(110, 50, 170, 20);

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JComboBox m_jFloor;
    private javax.swing.JTextField m_jName;
    private javax.swing.JTextField m_jX;
    private javax.swing.JTextField m_jY;
    // End of variables declaration//GEN-END:variables
    
}
