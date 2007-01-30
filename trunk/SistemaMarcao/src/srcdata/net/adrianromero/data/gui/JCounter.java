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

package net.adrianromero.data.gui;

import java.util.*;
import javax.swing.*;
import net.adrianromero.format.Formats;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.user.BrowsableData;
import net.adrianromero.data.user.BrowseListener;
import net.adrianromero.data.user.BrowsableEditableData;
import net.adrianromero.data.user.StateListener;

/**
 *
 * @author  adrian
 */
public class JCounter extends JPanel implements BrowseListener, StateListener  {
    
    // protected BrowsableData m_bd;
    
    /** Creates new form JCounter */
    public JCounter(BrowsableEditableData bd) {
        initComponents();
        bd.addBrowseListener(this);
        bd.addStateListener(this);
    }
//    public JCounter(BrowsableData bd) {
//        
//        initComponents();
//        bd.addBrowseListener(this);
//    }
    
    public void updateState(int iState) {
        if (iState == BrowsableEditableData.ST_INSERT) {
             // Insert
            jlblIndex.setText("*");
        // BrowsableEditableData.ST_DELETE ya gestionado en updateIndex
        // BrowsableEditableData.ST_NORECORD ya gestionado en updateIndex
        // BrowsableEditableData.ST_UPDATE ya gestionado en updateIndex
        }
    }  

    public void updateIndex(int iIndex, int iCounter) {

        if (iIndex >= 0 && iIndex < iCounter) {
            jlblIndex.setText(Formats.INT.formatValue(new Integer(iIndex + 1)));
        } else {
            jlblIndex.setText("-");
        }
        jlblCounter.setText(Formats.INT.formatValue(new Integer(iCounter)));
    }    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jlblIndex = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jlblCounter = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(60, 20));
        setMinimumSize(new java.awt.Dimension(60, 20));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(60, 20));
        jlblIndex.setText("XX");
        add(jlblIndex);

        jLabel2.setText("/");
        add(jLabel2);

        jlblCounter.setText("XX");
        add(jlblCounter);

    }
    // </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jlblCounter;
    private javax.swing.JLabel jlblIndex;
    // End of variables declaration//GEN-END:variables
    
}
