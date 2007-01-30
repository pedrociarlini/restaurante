/*
 * NewJFrame.java
 *
 * Created on 23 de agosto de 2006, 10:28
 */

package net.adrianromero.beans;

import java.awt.Color;
import javax.swing.Icon;

/**
 *
 * @author  adrian
 */
public class NewJFrame extends javax.swing.JFrame {
    
    /** Creates new form NewJFrame */
    public NewJFrame() {
        initComponents();
    }

public class EmptyIcon implements Icon {
    
    private int m_iWidth;
    private int m_iHeight;
    
    /** Creates a new instance of NullIcon */
    public EmptyIcon(int width, int height) {
        m_iWidth = width;
        m_iHeight = height;
    }
    
    public int getIconHeight() {
        return m_iHeight;
    }
    
    public int getIconWidth() {
        return m_iWidth;
    }
    
    public void paintIcon(java.awt.Component c, java.awt.Graphics g, int x, int y) {
        Color color = g.getColor();
        g.setColor(Color.MAGENTA);
        g.fillRoundRect(x, y, m_iWidth, m_iHeight, 8, 8);
        g.setColor(color);
    }
    
}    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();

        getContentPane().setLayout(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jPanel1.setLayout(null);

        jPanel1.setBorder(new RoundedBorder(
            Color.GRAY,
            new Color(255,200,255),
            1f,
            16,
            true,
            true));
    jButton2.setText("jButton2");
    jPanel1.add(jButton2);
    jButton2.setBounds(10, 90, 160, 23);

    jButton3.setText("jButton3");
    jPanel1.add(jButton3);
    jButton3.setBounds(10, 60, 160, 23);

    jLabel3.setText("jLabel3");
    jPanel1.add(jLabel3);
    jLabel3.setBounds(10, 10, 160, 14);

    jTextField3.setText("jTextField3");
    jPanel1.add(jTextField3);
    jTextField3.setBounds(10, 30, 64, 19);

    getContentPane().add(jPanel1);
    jPanel1.setBounds(120, 220, 180, 160);

    jPanel5.setLayout(new java.awt.BorderLayout());

    jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

    jPanel6.setBorder(javax.swing.BorderFactory.createCompoundBorder(new RoundedBorder(
        Color.GRAY,
        new Color(200,255,200),
        1f,
        16,
        true,
        false), javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0))));
jLabel4.setText("jLabel4");
jPanel6.add(jLabel4);

jPanel5.add(jPanel6, java.awt.BorderLayout.NORTH);

jPanel2.setLayout(null);

jPanel2.setBorder(new RoundedBorder(Color.GRAY, 1f, 16, false, false));
jLabel1.setText("jLabel1");
jPanel2.add(jLabel1);
jLabel1.setBounds(14, 10, 60, 14);

jTextField1.setText("jTextField1");
jPanel2.add(jTextField1);
jTextField1.setBounds(70, 10, 130, 19);

jLabel2.setText("jLabel2");
jPanel2.add(jLabel2);
jLabel2.setBounds(14, 40, 60, 14);

jTextField2.setText("jTextField2");
jPanel2.add(jTextField2);
jTextField2.setBounds(70, 40, 130, 19);

jPanel5.add(jPanel2, java.awt.BorderLayout.CENTER);

jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

jPanel3.setBorder(new RoundedBorder(
Color.GRAY,
new Color(200,255,200),
1f,
16,
false,
true));
jPanel4.setLayout(new java.awt.BorderLayout());

jPanel4.setBorder(new RoundedBorder(
    Color.GRAY,
    new Color(200,255,200),
    1f,
    8,
    true,
    true));
    jPanel4.setOpaque(false);
    jLabel6.setText("jLabel6");
    jPanel4.add(jLabel6, java.awt.BorderLayout.CENTER);

    jPanel3.add(jPanel4);

    jPanel11.setLayout(new java.awt.BorderLayout());

    jPanel11.setBorder(new RoundedBorder(
        Color.GRAY,
        new Color(200,255,200),
        1f,
        8,
        true,
        true));
jPanel11.setOpaque(false);
jLabel9.setText("jLabel6");
jPanel11.add(jLabel9, java.awt.BorderLayout.CENTER);

jPanel3.add(jPanel11);

jPanel12.setLayout(new java.awt.BorderLayout());

jPanel12.setBorder(new RoundedBorder(
    Color.GRAY,
    new Color(200,255,200),
    1f,
    8,
    true,
    true));
    jPanel12.setOpaque(false);
    jLabel10.setText("jLabel6");
    jPanel12.add(jLabel10, java.awt.BorderLayout.CENTER);

    jPanel3.add(jPanel12);

    jPanel5.add(jPanel3, java.awt.BorderLayout.SOUTH);

    getContentPane().add(jPanel5);
    jPanel5.setBounds(170, 20, 280, 130);

    jPanel7.setLayout(new java.awt.BorderLayout());

    jPanel7.setBorder(javax.swing.BorderFactory.createCompoundBorder(new RoundedBorder(
        Color.GRAY,
        1f,
        16), javax.swing.BorderFactory.createEmptyBorder(5, 0, 5, 0)));
jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
jPanel8.setLayout(new java.awt.GridLayout(5, 5));

jButton4.setText("jButton4");
jPanel8.add(jButton4);

jButton5.setText("jButton5");
jPanel8.add(jButton5);

jButton6.setText("jButton6");
jPanel8.add(jButton6);

jButton7.setText("jButton7");
jPanel8.add(jButton7);

jButton9.setText("jButton9");
jPanel8.add(jButton9);

jButton14.setText("jButton14");
jPanel8.add(jButton14);

jButton8.setText("jButton8");
jPanel8.add(jButton8);

jButton10.setText("jButton10");
jPanel8.add(jButton10);

jButton11.setText("jButton11");
jPanel8.add(jButton11);

jButton12.setText("jButton12");
jPanel8.add(jButton12);

jButton13.setText("jButton13");
jPanel8.add(jButton13);

jScrollPane1.setViewportView(jPanel8);

jPanel7.add(jScrollPane1, java.awt.BorderLayout.CENTER);

getContentPane().add(jPanel7);
jPanel7.setBounds(320, 180, 180, 200);

java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
setBounds((screenSize.width-526)/2, (screenSize.height-430)/2, 526, 430);
}// </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
    
}
