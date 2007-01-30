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

import net.adrianromero.tpv.ticket.*;
import net.adrianromero.tpv.util.ThumbNailBuilder;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;
import javax.imageio.ImageIO;
import net.adrianromero.tpv.forms.AppView;
import net.adrianromero.tpv.forms.AppLocal;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.loader.SentenceList;
import net.adrianromero.data.loader.SentenceFind;
import net.adrianromero.tpv.forms.SentenceContainer;
import net.adrianromero.tpv.ticket.ThumbNailBuilderProduct;

public class JCatalog extends JPanel implements ListSelectionListener {
    
    protected EventListenerList listeners = new EventListenerList();
    private AppView m_App;    
    
    // Listado de paneles de productos modificadores...
    private Set m_productcom = new HashSet();
    private Set m_productcomEmpty = new HashSet();
    
    // Listado de productos en memoria...
    private Map m_productcache = new HashMap();
    
    private ThumbNailBuilderProduct tnbprodtext;
    
    /** Creates new form JCatalog */
    public JCatalog(AppView oApp) {
        this(oApp, ThumbNailBuilderProduct.PRICE_NONE);
    }
    public JCatalog(AppView oApp, int iPriceMode) {
        m_App = oApp;
        initComponents();
        
        Image defimg;
        try {
            defimg = ImageIO.read(getClass().getClassLoader().getResourceAsStream("net/adrianromero/images/colorize.png"));               
        } catch (Exception fnfe) {
            defimg = null;
        }           
        tnbprodtext = new ThumbNailBuilderProduct(64, 32, defimg, iPriceMode);
        
        m_jListCategories.addListSelectionListener(this);                
        m_jscrollcat.getVerticalScrollBar().setPreferredSize(new Dimension(35, 35));
    }
    
    private ProductInfoExt getProduct(String reference) {
        
        if (m_productcache.containsKey(reference)) {
            return (ProductInfoExt) m_productcache.get(reference);
        } else {
            ProductInfoExt p;
            try {
                SentenceFind sf = m_App.lookupDataLogic(SentenceContainer.class).getProductInfo2();
                p = (ProductInfoExt) sf.find(reference);
            } catch (BasicException eb) {
                // fallo la ejecucion de la lista de productos.
                // nunca deberia ocurrir.
                eb.printStackTrace();
                p = null;
            }
            
            m_productcache.put(reference, p);
            return p;
        }
    }
    
    public void loadProduct(String reference) {
        
        
        if (reference == null) {
            showCatalogPanel();
        } else {
            String smypanel = "." + reference;

            // Si ya existia y estaba vacio, mostramos el catalogo general
            if (m_productcomEmpty.contains(reference)) {
                showCatalogPanel();
            } else if (m_productcom.contains(reference)) {
                // existia, y tenia productos, lo muestro.
                showCommentPanel(smypanel);
            } else {
                SentenceList sent = m_App.lookupDataLogic(SentenceContainer.class).getProductComments();
                try {
                    java.util.List products = sent.list(reference);
                    if (products.size() == 0) {
                        // no hay productos por tanto lo anado a la de vacios y muestro el panel principal.
                        m_productcomEmpty.add(reference);
                        showCatalogPanel();
                    } else {

                        // creo un nuevo panel de productos...
                        JCatalogCom jprodcom = new JCatalogCom(getProduct(reference), new BackAction());         

                        for (int i = 0; i < products.size(); i++) {
                            ProductInfoExt prod = (ProductInfoExt) products.get(i);
                            jprodcom.addProduct(tnbprodtext.getThumbNail(prod), prod.getName(), new SelectedAction(prod));                                
                        }
                        m_productcom.add(smypanel);
                        m_jProductSingle.add(smypanel, jprodcom);
                        showCommentPanel(smypanel);
                    }
                } catch (BasicException eb) {
                    // fallo la ejecucion de la lista de productos.
                    // nunca deberia ocurrir.
                    eb.printStackTrace();
                    m_productcomEmpty.add(reference);
                    showCatalogPanel();
                }
            }
        }
    }
    
    public void loadCatalog() {
        
        // borro lo que ya habiamos creado...
        m_jProducts.removeAll();
        m_productcache = new HashMap();
        
        m_productcom = new HashSet();
        m_productcomEmpty = new HashSet();
        m_jProductSingle.removeAll();        
        
        SentenceList sent = m_App.lookupDataLogic(SentenceContainer.class).getProductCatalog();         
        SentenceList catlist = m_App.lookupDataLogic(SentenceContainer.class).getCategoryList();

        try {
            java.util.List products = sent.list();
            java.util.List categories = catlist.list();
            int iIndexCat = 0;
            
            Image defimg;
            try {
                defimg = ImageIO.read(getClass().getClassLoader().getResourceAsStream("net/adrianromero/images/colorize.png"));               
            } catch (Exception fnfe) {
                defimg = null;
            }            

            ThumbNailBuilder tnbcat = new ThumbNailBuilder(32, 32, defimg);
            
            Integer categoryID = null;
            JCatalogTab jcurrTab = null;
            JCatalogTab jnullTab = null;
            
            java.util.List aCatList = new ArrayList();

            for (int i = 0; i < products.size(); i++) {
                ProductInfoExt prod = (ProductInfoExt) products.get(i);
                if (jcurrTab == null 
                || ((categoryID == null && prod.getCategoryID() != null) || !categoryID.equals(prod.getCategoryID()))) {
                    
                    // Buscamos la categoria del producto
                    CategoryInfo newcat = null;
                    if (prod.getCategoryID() != null) {
                        CategoryInfo searchcat = null;
                        while (iIndexCat < categories.size()) {
                            searchcat = (CategoryInfo) categories.get(iIndexCat++);
                            if (searchcat.getID().equals(prod.getCategoryID())) {
                                newcat = searchcat;
                                break;
                            }
                        }
                    }
                    
                    if (newcat == null) {
                        if (jnullTab == null) {
                            jnullTab = new JCatalogTab();
                        }
                        jcurrTab = jnullTab;
                        categoryID = prod.getCategoryID();
                    } else {
                        jcurrTab = new JCatalogTab();      
                        categoryID = newcat.getID();
                        m_jProducts.add(jcurrTab, newcat.getID().toString());
                        // m_jTabCategories.addTab(newcat.getName(), new ImageIcon(tnbcat.getThumbNail(newcat.getImage())), jcurrTab);
                        aCatList.add(new Object[] {newcat.getID(), newcat.getName(), new ImageIcon(tnbcat.getThumbNail(newcat.getImage()))});
                    }
                }

                jcurrTab.addProduct(tnbprodtext.getThumbNail(prod), prod.getName(), new SelectedAction(prod));
            }
            
            // Si ha habido productos del catalogo sin categoria.
            if (jnullTab != null) {
                m_jProducts.add(jnullTab, Integer.toString(Integer.MIN_VALUE));
                // m_jTabCategories.addTab(AppLocal.getIntString("label.nullcategory"), new ImageIcon(tnbcat.getThumbNail(null)), jnullTab);
                aCatList.add(new Object[] {new Integer(Integer.MIN_VALUE), AppLocal.getIntString("label.nullcategory"), new ImageIcon(tnbcat.getThumbNail(null))});
            }
            
            m_jListCategories.setCellRenderer(new SmallCategoryRenderer());
            m_jListCategories.setModel(new CategoriesListModel(aCatList));
            if (m_jListCategories.getModel().getSize() > 0) {
                m_jListCategories.setSelectedIndex(0);
            }
            
        } catch (BasicException ee) {
            ee.printStackTrace();
        }
        
        showCatalogPanel();
    }
    
    public void setEnabled(boolean value) {
        
        m_jListCategories.setEnabled(value);
        m_jscrollcat.setEnabled(value);
        m_jUp.setEnabled(value);
        m_jDown.setEnabled(value);
        m_jProducts.setEnabled(value); 
        synchronized (m_jProducts.getTreeLock()) {
            int compCount = m_jProducts.getComponentCount();
            for (int i = 0 ; i < compCount ; i++) {
                m_jProducts.getComponent(i).setEnabled(value);
            }
        }
     
        super.setEnabled(value);
    }
    
    public void addActionListener(ActionListener l) {
        listeners.add(ActionListener.class, l);
    }
    public void removeActionListener(ActionListener l) {
        listeners.add(ActionListener.class, l);
    }

    public void valueChanged(ListSelectionEvent evt) {
        
        if (!evt.getValueIsAdjusting()) {
            int i = m_jListCategories.getSelectedIndex();
            if (i >= 0) {
                // Lo hago visible...
                Rectangle oRect = m_jListCategories.getCellBounds(i, i);
                m_jListCategories.scrollRectToVisible(oRect);       
            }
        }
    }  
    
    protected void fireSelectedProduct(ProductInfoExt prod) {
        EventListener[] l = listeners.getListeners(ActionListener.class);
        ActionEvent e = null;
        for (int i = 0; i < l.length; i++) {
            if (e == null) {
                e = new ActionEvent(prod, ActionEvent.ACTION_PERFORMED, prod.getReference());
            }
            ((ActionListener) l[i]).actionPerformed(e);	       
        }
    }   
    
    private void showCommentPanel(String view) {
        CardLayout cl = (CardLayout)(this.getLayout());
        cl.show(this, "comment");  
        
        cl = (CardLayout)(this.getLayout());
        cl.show(this, view);  
    }
    
    private void showCatalogPanel() {
        CardLayout cl = (CardLayout)(this.getLayout());
        cl.show(this, "catalog");  
    }
    
    private void showProductsPanel(String view) {
        CardLayout cl = (CardLayout)(m_jProducts.getLayout());
        cl.show(m_jProducts, view);  
    }
    
    private class SelectedAction implements ActionListener {
        private ProductInfoExt prod;
        public SelectedAction(ProductInfoExt prod) {
            this.prod = prod;
        }
        public void actionPerformed(ActionEvent e) {
            fireSelectedProduct(prod);
        }
    }
    
    private class BackAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            showCatalogPanel();
        }
    }
    
    private class CategoriesListModel extends AbstractListModel {
        private java.util.List m_aCategories;
        public CategoriesListModel(java.util.List aCategories) {
            m_aCategories = aCategories;
        }
        public int getSize() { 
            return m_aCategories.size(); 
        }
        public Object getElementAt(int i) {
            return m_aCategories.get(i);
        }    
    }
    
    private class SmallCategoryRenderer extends DefaultListCellRenderer {

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, null, index, isSelected, cellHasFocus);
            Object[] cat = (Object[]) value;
            setText((String) cat[1]);
            setIcon((Icon) cat[2]);
            return this;
        }      
    }            
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        m_jCatalogAll = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        m_jscrollcat = new javax.swing.JScrollPane();
        m_jListCategories = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        m_jUp = new javax.swing.JButton();
        m_jDown = new javax.swing.JButton();
        m_jProducts = new javax.swing.JPanel();
        m_jProductSingle = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        m_jCatalogAll.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        m_jscrollcat.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        m_jscrollcat.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        m_jscrollcat.setPreferredSize(new java.awt.Dimension(235, 0));
        m_jListCategories.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        m_jListCategories.setFocusable(false);
        m_jListCategories.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                m_jListCategoriesValueChanged(evt);
            }
        });

        m_jscrollcat.setViewportView(m_jListCategories);

        jPanel1.add(m_jscrollcat, java.awt.BorderLayout.WEST);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.GridLayout(0, 1, 0, 5));

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 5));
        m_jUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/adrianromero/images/1uparrow.png")));
        m_jUp.setFocusPainted(false);
        m_jUp.setFocusable(false);
        m_jUp.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jUp.setRequestFocusEnabled(false);
        m_jUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jUpActionPerformed(evt);
            }
        });

        jPanel3.add(m_jUp);

        m_jDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/adrianromero/images/1downarrow.png")));
        m_jDown.setFocusPainted(false);
        m_jDown.setFocusable(false);
        m_jDown.setMargin(new java.awt.Insets(8, 14, 8, 14));
        m_jDown.setRequestFocusEnabled(false);
        m_jDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jDownActionPerformed(evt);
            }
        });

        jPanel3.add(m_jDown);

        jPanel2.add(jPanel3, java.awt.BorderLayout.NORTH);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        m_jCatalogAll.add(jPanel1, java.awt.BorderLayout.WEST);

        m_jProducts.setLayout(new java.awt.CardLayout());

        m_jProducts.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        m_jCatalogAll.add(m_jProducts, java.awt.BorderLayout.CENTER);

        add(m_jCatalogAll, "catalog");

        m_jProductSingle.setLayout(new java.awt.CardLayout());

        add(m_jProductSingle, "comment");

    }// </editor-fold>//GEN-END:initComponents

    private void m_jDownActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jDownActionPerformed

        int i = m_jListCategories.getSelectionModel().getMaxSelectionIndex();
        if (i < 0){
            i =  0; // No hay ninguna seleccionada
        } else {
            i ++;
            if (i >= m_jListCategories.getModel().getSize() ) {
                i = m_jListCategories.getModel().getSize() - 1;
            }
        }

        if ((i >= 0) && (i < m_jListCategories.getModel().getSize())) {
            // Solo seleccionamos si podemos.
            m_jListCategories.getSelectionModel().setSelectionInterval(i, i);
        }        
        
    }//GEN-LAST:event_m_jDownActionPerformed

    private void m_jUpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jUpActionPerformed

        int i = m_jListCategories.getSelectionModel().getMinSelectionIndex();
        if (i < 0){
            i = m_jListCategories.getModel().getSize() - 1; // No hay ninguna seleccionada
        } else {
            i --;
            if (i < 0) {
                i = 0;
            }
        }

        if ((i >= 0) && (i < m_jListCategories.getModel().getSize())) {
            // Solo seleccionamos si podemos.
            m_jListCategories.getSelectionModel().setSelectionInterval(i, i);
        }        
        
        
    }//GEN-LAST:event_m_jUpActionPerformed

    private void m_jListCategoriesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_m_jListCategoriesValueChanged

        if (!evt.getValueIsAdjusting()) {
            Object[] i = (Object[]) m_jListCategories.getSelectedValue();
            if (i != null) {
                showProductsPanel(((Integer) i[0]).toString());
            }
        }
        
    }//GEN-LAST:event_m_jListCategoriesValueChanged
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel m_jCatalogAll;
    private javax.swing.JButton m_jDown;
    private javax.swing.JList m_jListCategories;
    private javax.swing.JPanel m_jProductSingle;
    private javax.swing.JPanel m_jProducts;
    private javax.swing.JButton m_jUp;
    private javax.swing.JScrollPane m_jscrollcat;
    // End of variables declaration//GEN-END:variables
    
}
