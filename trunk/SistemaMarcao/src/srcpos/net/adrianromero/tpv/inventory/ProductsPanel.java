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

import java.awt.*;
import javax.swing.*;
import net.adrianromero.tpv.forms.JPanelView;
import net.adrianromero.tpv.forms.AppView;
import net.adrianromero.tpv.forms.AppLocal;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.gui.*;
import net.adrianromero.data.loader.*;
import net.adrianromero.data.user.*;
import net.adrianromero.format.Formats;
import net.adrianromero.tpv.forms.SentenceContainer;
import net.adrianromero.tpv.ticket.ProductFilter;

public class ProductsPanel extends javax.swing.JPanel implements JPanelView, EditorListener {
    
    private BrowsableEditableData m_bd;    
    
    private SentenceList liststock;
    private BrowsableData m_bdstock;
    
    protected AppView m_App;
    
    private ProductsEditor jeditor;
    private ProductFilter jproductfilter;    
    private StockEditor jeditorstock;
    
    /** Creates new form ProductsPanel */
    public ProductsPanel(AppView oApp) {
        m_App = oApp;
        initComponents();
        
        // El scanpal
        btnScanPal.setEnabled(m_App.getDeviceScanner() != null);
        
        // el panel del filtro
        jproductfilter = new ProductFilter(m_App);
        add(jproductfilter, BorderLayout.NORTH);

        // el panel del editor
        jeditor = new ProductsEditor(m_App);    
        m_jContEditor.add(jeditor, BorderLayout.CENTER);
        
        // El editable data de productos
        ListProvider lpr = new ListProviderECreator(m_App.lookupDataLogic(SentenceContainer.class).getProductCatQBF(), jproductfilter);
        SaveProvider spr = new SaveProvider(
            m_App.lookupDataLogic(SentenceContainer.class).getProductCatUpdate(), 
            m_App.lookupDataLogic(SentenceContainer.class).getProductCatInsert(), 
            m_App.lookupDataLogic(SentenceContainer.class).getProductCatDelete());
                
        m_bd = new BrowsableEditableData(lpr, spr, jeditor);
        m_bd.addEditorListener(this);
        
        // El editable data del stock
        //Listprovider lpr =  new ListProvider
        liststock = m_App.lookupDataLogic(SentenceContainer.class).getProductStock();
        //m_bdstock = new BrowsableEditableData
        // El editable data del stock
        m_bdstock = new BrowsableData(null, new SaveProvider(
                m_App.lookupDataLogic(SentenceContainer.class).getStockUpdate(),
                null,
                null));
        
        jeditorstock = new StockEditor();
        jeditor.setStockEditor(jeditorstock);
        
        JListNavigator nl = new JListNavigator(m_bd);
        nl.setCellRenderer(new ListCellRendererBasic(new RenderStringBasic(new Formats[] {Formats.STRING, null, Formats.STRING})));
        m_jContEditor.add(nl, java.awt.BorderLayout.WEST);     

        // La Toolbar
        m_jToolbar.add(new JLabelDirty(m_bd));
        m_jToolbar.add(new JCounter(m_bd));
        m_jToolbar.add(new JNavigator(m_bd, 
                new VectorerBasic(
                new String[] {"REFERENCE", "CODE", "NAME", "PRICEBUY", "PRICESELL"},
                new Formats[] {Formats.STRING, Formats.STRING, Formats.STRING, Formats.CURRENCY, Formats.CURRENCY},
                new int[] {0, 1, 2, 5, 6}),
                new ComparatorCreatorBasic(
                new String[]{"REFERENCE", "CODE", "NAME", "ISCOM", "ISSCALE", "PRICEBUY", "PRICESELL", "CATEGORY", "TAX", "IMAGE", "STOCKCOST", "STOCKVOLUME"},
                // El productCatDatas del SentenceContainer, igualito
                new Datas[]{Datas.STRING, Datas.STRING, Datas.STRING, Datas.BOOLEAN, Datas.BOOLEAN, Datas.DOUBLE, Datas.DOUBLE, Datas.INT, Datas.INT, Datas.IMAGE, Datas.DOUBLE, Datas.DOUBLE, Datas.BOOLEAN, Datas.INT}, 
                new int[]{0, 1, 2, 5, 6, 7, 8}
                )));
        m_jToolbar.add(new JSaver(m_bd));        
    }
    
    public String getTitle() {
        return AppLocal.getIntString("Menu.Products");
    } 
    
    public JComponent getComponent() {
        return this;
    }

    public void activate() {
        
        jeditor.activate(); 
        jproductfilter.activate();
        
        try {
            m_bd.actionLoad();
        } catch (BasicException e) {
        }
    }    
    
    public boolean deactivate() {
    // se me debe permitir cancelar el deactivate   
        try {
            return m_bd.actionClosingForm(this);
        } catch (BasicException eD) {
            JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_NOTICE, AppLocal.getIntString("message.CannotMove"), eD));
            return false;
        }
    }  
    
    public void updateValue(Object value) {
        
        // recargo 
        try {
            m_bdstock.loadList(liststock.list(value));
        } catch (BasicException e) {
            m_bdstock.loadList(null);
        }
        
        jeditorstock.writeValueStock(m_bdstock);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        m_jBasePanel = new javax.swing.JPanel();
        m_jContEditor = new javax.swing.JPanel();
        m_jToolbar = new javax.swing.JPanel();
        btnScanPal = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        m_jBasePanel.setLayout(new java.awt.BorderLayout());

        m_jContEditor.setLayout(new java.awt.BorderLayout());

        btnScanPal.setText("ScanPal");
        btnScanPal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScanPalActionPerformed(evt);
            }
        });

        m_jToolbar.add(btnScanPal);

        m_jContEditor.add(m_jToolbar, java.awt.BorderLayout.NORTH);

        m_jBasePanel.add(m_jContEditor, java.awt.BorderLayout.CENTER);

        add(m_jBasePanel, java.awt.BorderLayout.CENTER);

    }// </editor-fold>//GEN-END:initComponents

    private void btnScanPalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScanPalActionPerformed
  
        JDlgUploadProducts.showMessage(this, m_App.getDeviceScanner(), m_bd);

    }//GEN-LAST:event_btnScanPalActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnScanPal;
    private javax.swing.JPanel m_jBasePanel;
    private javax.swing.JPanel m_jContEditor;
    private javax.swing.JPanel m_jToolbar;
    // End of variables declaration//GEN-END:variables
    
}
