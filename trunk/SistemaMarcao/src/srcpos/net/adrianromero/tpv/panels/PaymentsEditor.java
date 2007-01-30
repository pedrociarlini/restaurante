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
import java.util.Date;
import net.adrianromero.beans.DateUtils;
import net.adrianromero.beans.JCalendarDialog;
import net.adrianromero.basic.BasicException;
import net.adrianromero.format.Formats;
import net.adrianromero.data.user.DirtyManager;
import net.adrianromero.data.user.EditorRecord;
import net.adrianromero.tpv.forms.AppLocal;
import net.adrianromero.tpv.forms.AppView;
import net.adrianromero.tpv.forms.SentenceContainer;
import net.adrianromero.tpv.forms.UserView;


public class PaymentsEditor extends javax.swing.JPanel implements EditorRecord {
    
    private DirtyManager m_Dirty = new DirtyManager();
    private ComboBoxModelKey m_ReasonModel;
    
    private Integer m_iTicketID;
    private Integer m_iPaymentID;
   
    private AppView m_App;
    private UserView m_User;
    
    /** Creates new form JPanelPayments */
    public PaymentsEditor(AppView oApp, UserView oUser) {
        
        m_App = oApp;
        m_User = oUser;
        
        initComponents();
       
        m_ReasonModel = new ComboBoxModelKey();
        m_ReasonModel.add("cashin", new PaymentReasonPositive(AppLocal.getIntString("transpayment.cashin")));
        m_ReasonModel.add("cashout", new PaymentReasonNegative(AppLocal.getIntString("transpayment.cashout")));              
        m_jreason.setModel(m_ReasonModel);
        
        m_jdate.getDocument().addDocumentListener(m_Dirty);
        m_jreason.addActionListener(m_Dirty);
        m_jtotal.getDocument().addDocumentListener(m_Dirty);

        writeValueEOF();
    }
    
    public void writeValueEOF() {
        m_iTicketID = null;
        m_iPaymentID = null;
        m_jdate.setText(null);
        setReasonTotal(null, null);
        m_jdate.setEnabled(false);
        m_jbtndate.setEnabled(false);
        m_jreason.setEnabled(false);
        m_jtotal.setEnabled(false);
    }  
    
    public void writeValueInsert() {
        m_iTicketID = null;
        m_iPaymentID = null;
        m_jdate.setText(Formats.TIMESTAMP.formatValue(DateUtils.getTodayMinutes()));
        setReasonTotal(null, null);
        m_jdate.setEnabled(true);
        m_jbtndate.setEnabled(true);
        m_jreason.setEnabled(true);
        m_jtotal.setEnabled(true);
    }
    
    public void writeValueDelete(Object value) {
        Object[] payment = (Object[]) value;
        m_iTicketID = (Integer) payment[0];
        m_iPaymentID = (Integer) payment[4];
        m_jdate.setText(Formats.TIMESTAMP.formatValue(payment[1]));
        setReasonTotal(payment[5], payment[6]);
        m_jdate.setEnabled(false);
        m_jbtndate.setEnabled(false);
        m_jreason.setEnabled(false);
        m_jtotal.setEnabled(false);

    }
    
    public void writeValueEdit(Object value) {
        Object[] payment = (Object[]) value;
        m_iTicketID = (Integer) payment[0];
        m_iPaymentID = (Integer) payment[4];
        m_jdate.setText(Formats.TIMESTAMP.formatValue(payment[1]));
        setReasonTotal(payment[5], payment[6]);
        m_jdate.setEnabled(false);
        m_jbtndate.setEnabled(false);
        m_jreason.setEnabled(false);
        m_jtotal.setEnabled(false);
    }
    
    public Object createValue() throws BasicException {
        Object[] payment = new Object[7];
        payment[0] = m_iTicketID == null ? m_App.lookupDataLogic(SentenceContainer.class).getNextTicketIndex().find() : m_iTicketID;
        payment[4] = m_iPaymentID == null ? m_App.lookupDataLogic(SentenceContainer.class).getNextPayment().find() : m_iPaymentID;
        payment[1] = Formats.TIMESTAMP.parseValue(m_jdate.getText());
        payment[2] = m_App.getActiveCashIndex();
        payment[3] = m_User.getUser().getName();
        payment[5] = m_ReasonModel.getSelectedKey();
        PaymentReason reason = (PaymentReason) m_ReasonModel.getSelectedValue();
        Double dtotal = (Double) Formats.DOUBLE.parseValue(m_jtotal.getText());
        payment[6] = reason == null ? dtotal : reason.addSignum(dtotal);
        return payment;
    }
    
    public DirtyManager getDirtyManager() {
        return m_Dirty;
    }
    
    private void setReasonTotal(Object reasonfield, Object totalfield) {
        
        m_ReasonModel.setSelectedKey(reasonfield);
        
        PaymentReason reason = (PaymentReason) m_ReasonModel.getSelectedValue();     
        
        Double dtotal;
        if (reason == null) {
            dtotal = (Double) totalfield;
        } else {
            dtotal = reason.positivize((Double) totalfield);
        }

        m_jtotal.setText(Formats.CURRENCY.formatValue(dtotal));  
    }
    
    private static abstract class PaymentReason {
        private String m_sText;
        
        public PaymentReason(String text) {
            m_sText = text;
        }
        public abstract Double positivize(Double d);
        public abstract Double addSignum(Double d);
        
        public String toString() {
            return m_sText;
        }
    }
    private static class PaymentReasonPositive extends PaymentReason {
        public PaymentReasonPositive(String text) {
            super(text);
        }
        public Double positivize(Double d) {
            return d;
        }
        public Double addSignum(Double d) {
            if (d == null) {
                return null;
            } else if (d.doubleValue() < 0.0) {
                return new Double(-d.doubleValue());
            } else {
                return d;
            }
        }
    }
    private static class PaymentReasonNegative extends PaymentReason {
        public PaymentReasonNegative(String text) {
            super(text);
        }
        public Double positivize(Double d) {
            return d == null ? null : new Double(-d.doubleValue());
        }
        public Double addSignum(Double d) {
            if (d == null) {
                return null;
            } else if (d.doubleValue() > 0.0) {
                return new Double(-d.doubleValue());
            } else {
                return d;
            }
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        m_jreason = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        m_jdate = new javax.swing.JTextField();
        m_jbtndate = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        m_jtotal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setLayout(null);

        add(m_jreason);
        m_jreason.setBounds(160, 60, 200, 20);

        jLabel1.setText(AppLocal.getIntString("label.paymentdate"));
        add(jLabel1);
        jLabel1.setBounds(10, 30, 150, 14);

        add(m_jdate);
        m_jdate.setBounds(160, 30, 200, 19);

        m_jbtndate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/adrianromero/images/date.png")));
        m_jbtndate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                m_jbtndateActionPerformed(evt);
            }
        });

        add(m_jbtndate);
        m_jbtndate.setBounds(370, 30, 40, 25);

        jLabel2.setText(AppLocal.getIntString("label.paymentreason"));
        add(jLabel2);
        jLabel2.setBounds(10, 60, 150, 14);

        m_jtotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        add(m_jtotal);
        m_jtotal.setBounds(160, 90, 70, 19);

        jLabel3.setText(AppLocal.getIntString("label.paymenttotal"));
        add(jLabel3);
        jLabel3.setBounds(10, 90, 150, 14);

    }
    // </editor-fold>//GEN-END:initComponents

    private void m_jbtndateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_m_jbtndateActionPerformed

        Date date;
        try {
            date = (Date) Formats.TIMESTAMP.parseValue(m_jdate.getText());
        } catch (BasicException e) {
            date = null;
        }        
        date = JCalendarDialog.showCalendarTime(this, date);
        if (date != null) {
            m_jdate.setText(Formats.TIMESTAMP.formatValue(date));
        }

    }//GEN-LAST:event_m_jbtndateActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton m_jbtndate;
    private javax.swing.JTextField m_jdate;
    private javax.swing.JComboBox m_jreason;
    private javax.swing.JTextField m_jtotal;
    // End of variables declaration//GEN-END:variables
    
}
