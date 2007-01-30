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

import java.util.List;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.gui.ComboBoxValModel;
import net.adrianromero.data.gui.ListCellRendererBasic;
import net.adrianromero.data.gui.ListQBFModelNumber;
import net.adrianromero.data.loader.QBFCompareEnum;
import net.adrianromero.data.loader.SentenceList;
import net.adrianromero.data.loader.TableDefinition;
import net.adrianromero.data.user.EditorCreator;
import net.adrianromero.editor.JEditorString;
import net.adrianromero.tpv.forms.AppLocal;
import net.adrianromero.tpv.forms.AppView;
import net.adrianromero.tpv.forms.SentenceContainer;

public class ProductFilterSales extends javax.swing.JPanel implements EditorCreator {
    
    private SentenceList m_sentcat;
    private ComboBoxValModel m_CategoryModel;
    private TableDefinition tcategories;
    
    /** Creates new form ProductFilterSales */
    public ProductFilterSales(AppView app) {
        initComponents();
        
        // El modelo de categorias
        tcategories = app.lookupDataLogic(SentenceContainer.class).getTableCategoriesList();
        m_sentcat = tcategories.getListSentence();
        m_CategoryModel = new ComboBoxValModel(tcategories.getKeyGetterBasic());       
        m_jCategory.setRenderer(new ListCellRendererBasic(tcategories.getRenderStringBasic()));        
        
        m_jCboPriceBuy.setModel(new ListQBFModelNumber());
        m_jPriceBuy.addEditorKeys(m_jKeys);
        
        m_jCboPriceSell.setModel(new ListQBFModelNumber());
        m_jPriceSell.addEditorKeys(m_jKeys);
        
        m_jtxtName.addEditorKeys(m_jKeys);
        
        m_jtxtBarCode.addEditorKeys(m_jKeys);
    }
    
    public void activate() {
        
        m_jtxtBarCode.reset();
        m_jtxtBarCode.setEditModeEnum(JEditorString.MODE_123);
        m_jtxtName.reset();
        m_jPriceBuy.reset();
        m_jPriceSell.reset();
        m_jtxtName.activate();
        
        try {
            List catlist = m_sentcat.list();
            catlist.add(0, null);
            m_CategoryModel = new ComboBoxValModel(catlist, tcategories.getKeyGetterBasic());
            m_jCategory.setModel(m_CategoryModel);
        } catch (BasicException eD) {
            // no hay validacion
        }
    }
    
    public Object createValue() throws BasicException {
        
        Object[] afilter = new Object[10];
        
        // Nombre
        if (m_jtxtName.getText() == null || m_jtxtName.getText().equals("")) {
            afilter[0] = QBFCompareEnum.COMP_NONE;
            afilter[1] = null;
        } else {
            afilter[0] = QBFCompareEnum.COMP_RE;
            afilter[1] = "%" + m_jtxtName.getText() + "%";
        }
        
        // Precio de compra
        try {
            afilter[2] = m_jCboPriceBuy.getSelectedItem();
            afilter[3] =  new Double(m_jPriceBuy.getValue());
        } catch (BasicException e) {
            afilter[2] = QBFCompareEnum.COMP_NONE;
            afilter[3] = null;
        }

        // Precio de venta
        try {
            afilter[4] = m_jCboPriceSell.getSelectedItem();
            afilter[5] =  new Double(m_jPriceSell.getValue());
        } catch (BasicException e) {
            afilter[4] = QBFCompareEnum.COMP_NONE;
            afilter[5] = null;
        }
        
        // Categoria
        if (m_CategoryModel.getSelectedKey() == null) {
            afilter[6] = QBFCompareEnum.COMP_NONE;
            afilter[7] = null;
        } else {
            afilter[6] = QBFCompareEnum.COMP_EQUALS;
            afilter[7] = m_CategoryModel.getSelectedKey();
        }
        
        // el codigo de barras
        if (m_jtxtBarCode.getText() == null || m_jtxtBarCode.getText().equals("")) {
            afilter[8] = QBFCompareEnum.COMP_NONE;
            afilter[9] = null;
        } else{
            afilter[8] = QBFCompareEnum.COMP_RE;
            afilter[9] = "%" + m_jtxtBarCode.getText() + "%";
        }
        
        return afilter;
    } 
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        m_jKeys = new net.adrianromero.editor.JEditorKeys();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        m_jtxtName = new net.adrianromero.editor.JEditorString();
        jLabel2 = new javax.swing.JLabel();
        m_jCategory = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        m_jCboPriceBuy = new javax.swing.JComboBox();
        m_jPriceBuy = new net.adrianromero.editor.JEditorCurrency();
        jLabel3 = new javax.swing.JLabel();
        m_jCboPriceSell = new javax.swing.JComboBox();
        m_jPriceSell = new net.adrianromero.editor.JEditorCurrency();
        m_jtxtBarCode = new net.adrianromero.editor.JEditorString();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        setPreferredSize(new java.awt.Dimension(370, 260));
        add(m_jKeys, java.awt.BorderLayout.EAST);

        jPanel1.setLayout(null);

        jLabel5.setText(AppLocal.getIntString("label.prodname"));
        jPanel1.add(jLabel5);
        jLabel5.setBounds(20, 40, 110, 14);

        jPanel1.add(m_jtxtName);
        m_jtxtName.setBounds(130, 40, 290, 25);

        jLabel2.setText(AppLocal.getIntString("label.prodcategory"));
        jPanel1.add(jLabel2);
        jLabel2.setBounds(20, 70, 110, 14);

        jPanel1.add(m_jCategory);
        m_jCategory.setBounds(130, 70, 260, 25);

        jLabel4.setText(AppLocal.getIntString("label.prodpricebuy"));
        jPanel1.add(jLabel4);
        jLabel4.setBounds(20, 100, 110, 14);

        jPanel1.add(m_jCboPriceBuy);
        m_jCboPriceBuy.setBounds(130, 100, 150, 20);

        jPanel1.add(m_jPriceBuy);
        m_jPriceBuy.setBounds(290, 100, 130, 25);

        jLabel3.setText(AppLocal.getIntString("label.prodpricesell"));
        jPanel1.add(jLabel3);
        jLabel3.setBounds(20, 130, 110, 14);

        jPanel1.add(m_jCboPriceSell);
        m_jCboPriceSell.setBounds(130, 130, 150, 20);

        jPanel1.add(m_jPriceSell);
        m_jPriceSell.setBounds(290, 130, 130, 25);

        jPanel1.add(m_jtxtBarCode);
        m_jtxtBarCode.setBounds(130, 10, 290, 25);

        jLabel1.setText(AppLocal.getIntString("label.prodbarcode"));
        jPanel1.add(jLabel1);
        jLabel1.setBounds(20, 10, 110, 14);

        add(jPanel1, java.awt.BorderLayout.CENTER);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox m_jCategory;
    private javax.swing.JComboBox m_jCboPriceBuy;
    private javax.swing.JComboBox m_jCboPriceSell;
    private net.adrianromero.editor.JEditorKeys m_jKeys;
    private net.adrianromero.editor.JEditorCurrency m_jPriceBuy;
    private net.adrianromero.editor.JEditorCurrency m_jPriceSell;
    private net.adrianromero.editor.JEditorString m_jtxtBarCode;
    private net.adrianromero.editor.JEditorString m_jtxtName;
    // End of variables declaration//GEN-END:variables
    
}
