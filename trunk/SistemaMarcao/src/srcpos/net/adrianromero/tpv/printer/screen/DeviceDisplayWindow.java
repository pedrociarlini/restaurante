/*
 * DeviceDisplayWindow.java
 *
 * Created on June 4, 2005, 11:12 PM
 */

package net.adrianromero.tpv.printer.screen;

import javax.swing.JComponent;
import net.adrianromero.tpv.forms.AppLocal;
import net.adrianromero.tpv.printer.DeviceDisplay;

/**
 *
 * @author  Adrian
 */
public class DeviceDisplayWindow extends javax.swing.JFrame implements DeviceDisplay {
    
    private String m_sName;
    private DeviceDisplayPanel m_display;
    
    /** Creates new form DeviceDisplayWindow */
    public DeviceDisplayWindow() {
        initComponents();
        
        m_sName = AppLocal.getIntString("Display.Window");
        m_display = new DeviceDisplayPanel(3.0);
        
        m_jContainer.add(m_display.getDisplayComponent());
        
        setVisible(true);
    }
    
    public String getDisplayName() {
        return m_sName;
    }    
    public String getDisplayDescription() {
        return null;
    }        
    public JComponent getDisplayComponent() {
        return null;
    }
    
    public void writeVisor(String sLine1, String sLine2) {
        m_display.writeVisor(sLine1, sLine2);
    }
    public void writeTimeVisor(String sLine2) {
        m_display.writeTimeVisor(sLine2);
    }
    public void clearVisor() {
        m_display.clearVisor();
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        m_jContainer = new javax.swing.JPanel();

        setTitle(AppLocal.getIntString("Display.Window"));
        m_jContainer.setLayout(new java.awt.BorderLayout());

        m_jContainer.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 5, 5, 5)));
        getContentPane().add(m_jContainer, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(767, 245));
    }
    // </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel m_jContainer;
    // End of variables declaration//GEN-END:variables
    
}
