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

package net.adrianromero.tpv.config;

import java.awt.CardLayout;
import javax.swing.*;
import java.io.IOException;

import net.adrianromero.tpv.forms.*;
import net.adrianromero.tpv.util.DirectoryEvent;
import net.adrianromero.tpv.util.StringParser;
import net.adrianromero.data.gui.MessageInf;
import net.adrianromero.data.gui.JMessageDialog;
import net.adrianromero.tpv.util.AltEncrypter;

public class JPanelConfiguration extends JPanel implements JPanelView {
        
    /** Creates new form JPanelConfiguration */
    public JPanelConfiguration(AppView oApp) {
             
        initComponents();
        
        jbtnDbDriverLib.addActionListener(new DirectoryEvent(jtxtDbDriverLib));
        
        jcboMachineScreenmode.addItem("fullscreen");
        jcboMachineScreenmode.addItem("window");
        
        jcboTicketsBag.addItem("simple");
        jcboTicketsBag.addItem("standard");
        jcboTicketsBag.addItem("restaurant");
        
        // Printer 1
        jcboMachinePrinter.addItem("screen");
        jcboMachinePrinter.addItem("printer");
        jcboMachinePrinter.addItem("epson");
        jcboMachinePrinter.addItem("tmu220");
        jcboMachinePrinter.addItem("star");
        jcboMachinePrinter.addItem("ithaca");
        jcboMachinePrinter.addItem("javapos");
        jcboMachinePrinter.addItem("Not defined");
        
        jcboConnPrinter.addItem("serial");
        jcboConnPrinter.addItem("rxtx");
        jcboConnPrinter.addItem("file");        
        
        jcboSerialPrinter.addItem("COM1");
        jcboSerialPrinter.addItem("COM2");
        jcboSerialPrinter.addItem("COM3");
        jcboSerialPrinter.addItem("COM4");
        jcboSerialPrinter.addItem("LPT1");
        jcboSerialPrinter.addItem("/dev/ttyS0");
        jcboSerialPrinter.addItem("/dev/ttyS1");
        jcboSerialPrinter.addItem("/dev/ttyS2");
        jcboSerialPrinter.addItem("/dev/ttyS3");
        
        // Printer 2        
        jcboMachinePrinter2.addItem("screen");
        jcboMachinePrinter2.addItem("printer");
        jcboMachinePrinter2.addItem("epson");
        jcboMachinePrinter2.addItem("tmu220");
        jcboMachinePrinter2.addItem("star");
        jcboMachinePrinter2.addItem("ithaca");
        jcboMachinePrinter2.addItem("javapos");
        jcboMachinePrinter2.addItem("Not defined");
        
        jcboConnPrinter2.addItem("serial");
        jcboConnPrinter2.addItem("rxtx");
        jcboConnPrinter2.addItem("file");   
        
        jcboSerialPrinter2.addItem("COM1");
        jcboSerialPrinter2.addItem("COM2");
        jcboSerialPrinter2.addItem("COM3");
        jcboSerialPrinter2.addItem("COM4");
        jcboSerialPrinter2.addItem("LPT1");
        jcboSerialPrinter2.addItem("/dev/ttyS0");
        jcboSerialPrinter2.addItem("/dev/ttyS1");
        jcboSerialPrinter2.addItem("/dev/ttyS2");
        jcboSerialPrinter2.addItem("/dev/ttyS3");
        
        // Printer 3
        jcboMachinePrinter3.addItem("screen");
        jcboMachinePrinter3.addItem("printer");
        jcboMachinePrinter3.addItem("epson");
        jcboMachinePrinter3.addItem("tmu220");
        jcboMachinePrinter3.addItem("star");
        jcboMachinePrinter3.addItem("ithaca");
        jcboMachinePrinter3.addItem("javapos");
        jcboMachinePrinter3.addItem("Not defined");
        
        jcboConnPrinter3.addItem("serial");
        jcboConnPrinter3.addItem("rxtx");
        jcboConnPrinter3.addItem("file");   
        
        jcboSerialPrinter3.addItem("COM1");
        jcboSerialPrinter3.addItem("COM2");
        jcboSerialPrinter3.addItem("COM3");
        jcboSerialPrinter3.addItem("COM4");
        jcboSerialPrinter3.addItem("LPT1");
        jcboSerialPrinter3.addItem("/dev/ttyS0");
        jcboSerialPrinter3.addItem("/dev/ttyS1");
        jcboSerialPrinter3.addItem("/dev/ttyS2");
        jcboSerialPrinter3.addItem("/dev/ttyS3");
        
        // Display
        jcboMachineDisplay.addItem("screen");
        jcboMachineDisplay.addItem("window");
        jcboMachineDisplay.addItem("javapos");
        jcboMachineDisplay.addItem("epson");
        jcboMachineDisplay.addItem("ld200");
        jcboMachineDisplay.addItem("Not defined");
        
        jcboConnDisplay.addItem("serial");
        jcboConnDisplay.addItem("rxtx");
        jcboConnDisplay.addItem("file");
        
        jcboSerialDisplay.addItem("COM1");
        jcboSerialDisplay.addItem("COM2");
        jcboSerialDisplay.addItem("COM3");
        jcboSerialDisplay.addItem("COM4");
        jcboSerialDisplay.addItem("LPT1");
        jcboSerialDisplay.addItem("/dev/ttyS0");
        jcboSerialDisplay.addItem("/dev/ttyS1");
        jcboSerialDisplay.addItem("/dev/ttyS2");
        jcboSerialDisplay.addItem("/dev/ttyS3");
        
        // Scale
        jcboMachineScale.addItem("dialog1");
        jcboMachineScale.addItem("samsungesp");
        jcboMachineScale.addItem("Not defined");
        
        jcboSerialScale.addItem("COM1");
        jcboSerialScale.addItem("COM2");
        jcboSerialScale.addItem("COM3");
        jcboSerialScale.addItem("COM4");
        jcboSerialScale.addItem("LPT1");
        jcboSerialScale.addItem("/dev/ttyS0");
        jcboSerialScale.addItem("/dev/ttyS1");
        jcboSerialScale.addItem("/dev/ttyS2");
        jcboSerialScale.addItem("/dev/ttyS3");
        
        // Scanner
        jcboMachineScanner.addItem("scanpal2");
        jcboMachineScanner.addItem("Not defined");
        
        jcboSerialScanner.addItem("COM1");
        jcboSerialScanner.addItem("COM2");
        jcboSerialScanner.addItem("COM3");
        jcboSerialScanner.addItem("COM4");
        jcboSerialScanner.addItem("LPT1");
        jcboSerialScanner.addItem("/dev/ttyS0");
        jcboSerialScanner.addItem("/dev/ttyS1");
        jcboSerialScanner.addItem("/dev/ttyS2");
        jcboSerialScanner.addItem("/dev/ttyS3");
        
        // Lector de tarjetas.
        jcboCardReader.addItem("Not defined");
        jcboCardReader.addItem("Generic");
        jcboCardReader.addItem("Intelligent");
        
        jcboPaymentGateway.addItem("Not defined");
        jcboPaymentGateway.addItem("external");
        jcboPaymentGateway.addItem("SECPay");
        jcboPaymentGateway.addItem("AuthorizeNet");
    }
    
    private void restoreProperties() {
        
        AppConfig config = new AppConfig();
        if (config.delete()) {
            loadProperties();
        } else {
            JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotdeleteconfig")));            
        }
    }
    
    private void loadProperties() {
        
        AppConfig config = new AppConfig();
        config.load();
        
        jtxtDbDriverLib.setText(config.getProperty("db.driverlib"));
        jtxtDbDriver.setText(config.getProperty("db.driver"));
        jtxtDbURL.setText(config.getProperty("db.URL"));
        
        String sDBUser = config.getProperty("db.user");
        String sDBPassword = config.getProperty("db.password");        
        if (sDBUser != null && sDBPassword != null && sDBPassword.startsWith("crypt:")) {
            // La clave esta encriptada.
            AltEncrypter cypher = new AltEncrypter("tinapos" + sDBUser);
            sDBPassword = cypher.decrypt(sDBPassword.substring(6));
        }        
        jtxtDbUser.setText(sDBUser);
        jtxtDbPassword.setText(sDBPassword);       
        
        jtxtMachineHostname.setText(config.getProperty("machine.hostname"));
        jcboMachineScreenmode.setSelectedItem(config.getProperty("machine.screenmode"));
        jcboTicketsBag.setSelectedItem(config.getProperty("machine.ticketsbag"));
        
        StringParser p = new StringParser(config.getProperty("machine.printer"));
        String sparam = p.nextToken(':');
        if ("serial".equals(sparam) || "rxtx".equals(sparam) || "file".equals(sparam)) {
            jcboMachinePrinter.setSelectedItem("epson");
            jcboConnPrinter.setSelectedItem(sparam);
            jcboSerialPrinter.setSelectedItem(p.nextToken(','));
        } else if("javapos".equals(sparam)) {
            jcboMachinePrinter.setSelectedItem(sparam);
            m_jtxtJPOSPrinter.setText(p.nextToken(','));
            m_jtxtJPOSDrawer.setText(p.nextToken(','));
        } else {
            jcboMachinePrinter.setSelectedItem(sparam);
            jcboConnPrinter.setSelectedItem(p.nextToken(','));
            jcboSerialPrinter.setSelectedItem(p.nextToken(','));
        }

        p = new StringParser(config.getProperty("machine.printer.2"));
        sparam = p.nextToken(':');
        if ("serial".equals(sparam) || "rxtx".equals(sparam) || "file".equals(sparam)) {
            jcboMachinePrinter2.setSelectedItem("epson");
            jcboConnPrinter2.setSelectedItem(sparam);
            jcboSerialPrinter2.setSelectedItem(p.nextToken(','));
        } else if("javapos".equals(sparam)) {
            jcboMachinePrinter2.setSelectedItem(sparam);
            m_jtxtJPOSPrinter2.setText(p.nextToken(','));
            m_jtxtJPOSDrawer2.setText(p.nextToken(','));
        } else {
            jcboMachinePrinter2.setSelectedItem(sparam);
            jcboConnPrinter2.setSelectedItem(p.nextToken(','));
            jcboSerialPrinter2.setSelectedItem(p.nextToken(','));
        }

        p = new StringParser(config.getProperty("machine.printer.3"));
        sparam = p.nextToken(':');
        if ("serial".equals(sparam) || "rxtx".equals(sparam) || "file".equals(sparam)) {
            jcboMachinePrinter3.setSelectedItem("epson");
            jcboConnPrinter3.setSelectedItem(sparam);
            jcboSerialPrinter3.setSelectedItem(p.nextToken(','));
        } else if("javapos".equals(sparam)) {
            jcboMachinePrinter3.setSelectedItem(sparam);
            m_jtxtJPOSPrinter3.setText(p.nextToken(','));
            m_jtxtJPOSDrawer3.setText(p.nextToken(','));
        } else {
            jcboMachinePrinter3.setSelectedItem(sparam);
            jcboConnPrinter3.setSelectedItem(p.nextToken(','));
            jcboSerialPrinter3.setSelectedItem(p.nextToken(','));
        }
        
        p = new StringParser(config.getProperty("machine.display"));
        sparam = p.nextToken(':');
        if ("serial".equals(sparam) || "rxtx".equals(sparam) || "file".equals(sparam)) {
            jcboMachineDisplay.setSelectedItem("epson");
            jcboConnDisplay.setSelectedItem(sparam);
            jcboSerialDisplay.setSelectedItem(p.nextToken(','));
        } else {
            jcboMachineDisplay.setSelectedItem(sparam);
            if ("epson".equals(sparam) || "ld200".equals(sparam)) {
                jcboConnDisplay.setSelectedItem(p.nextToken(','));
                jcboSerialDisplay.setSelectedItem(p.nextToken(','));
            } else if ("javapos".equals(sparam)) {
                m_jtxtJPOSName.setText(p.nextToken(','));
            }
        }   
        
        p = new StringParser(config.getProperty("machine.scale"));
        sparam = p.nextToken(':');
        jcboMachineScale.setSelectedItem(sparam);
        if ("dialog1".equals(sparam) || "samsungesp".equals(sparam)) {
            jcboSerialScale.setSelectedItem(p.nextToken(','));
        }
        
        p = new StringParser(config.getProperty("machine.scanner"));
        sparam = p.nextToken(':');
        jcboMachineScanner.setSelectedItem(sparam);
        if ("scanpal2".equals(sparam)) {
            jcboSerialScanner.setSelectedItem(p.nextToken(','));
        }
        
        jcboCardReader.setSelectedItem(config.getProperty("payment.magcardreader"));
        jcboPaymentGateway.setSelectedItem(config.getProperty("payment.gateway"));
        jchkPaymentTest.setSelected(Boolean.valueOf(config.getProperty("payment.testmode")).booleanValue());
        jtxtCommerceID.setText(config.getProperty("payment.commerceid"));        
        jtxtCommercePwd.setText(config.getProperty("payment.commercepassword"));        
    }
    
    private void saveProperties() {
        
        AppConfig config = new AppConfig();
        
        config.setProperty("db.driverlib", jtxtDbDriverLib.getText());
        config.setProperty("db.driver", jtxtDbDriver.getText());
        config.setProperty("db.URL", jtxtDbURL.getText());
        config.setProperty("db.user", jtxtDbUser.getText());
        AltEncrypter cypher = new AltEncrypter("tinapos" + jtxtDbUser.getText());       
        config.setProperty("db.password", "crypt:" + cypher.encrypt(new String(jtxtDbPassword.getPassword())));

        config.setProperty("machine.hostname", jtxtMachineHostname.getText());
        config.setProperty("machine.screenmode", comboValue(jcboMachineScreenmode.getSelectedItem()));
        config.setProperty("machine.ticketsbag", comboValue(jcboTicketsBag.getSelectedItem()));
        
        String sMachinePrinter = comboValue(jcboMachinePrinter.getSelectedItem());
        if ("epson".equals(sMachinePrinter) || "tmu220".equals(sMachinePrinter) || "star".equals(sMachinePrinter) || "ithaca".equals(sMachinePrinter)) {
            config.setProperty("machine.printer", sMachinePrinter + ":" + comboValue(jcboConnPrinter.getSelectedItem()) + "," + comboValue(jcboSerialPrinter.getSelectedItem()));
        } else if ("javapos".equals(sMachinePrinter)) {
            config.setProperty("machine.printer", sMachinePrinter + ":" + m_jtxtJPOSPrinter.getText() + "," + m_jtxtJPOSDrawer.getText());
        } else {
            config.setProperty("machine.printer", sMachinePrinter);
        }
        
        String sMachinePrinter2 = comboValue(jcboMachinePrinter2.getSelectedItem());
        if ("epson".equals(sMachinePrinter2) || "tmu220".equals(sMachinePrinter2) || "star".equals(sMachinePrinter2) || "ithaca".equals(sMachinePrinter2)) {
            config.setProperty("machine.printer.2", sMachinePrinter2 + ":" + comboValue(jcboConnPrinter2.getSelectedItem()) + "," + comboValue(jcboSerialPrinter2.getSelectedItem()));
        } else if ("javapos".equals(sMachinePrinter2)) {
            config.setProperty("machine.printer.2", sMachinePrinter2 + ":" + m_jtxtJPOSPrinter2.getText() + "," + m_jtxtJPOSDrawer2.getText());
        } else {
            config.setProperty("machine.printer.2", sMachinePrinter2);
        }
        
        
        String sMachinePrinter3 = comboValue(jcboMachinePrinter3.getSelectedItem());
        if ("epson".equals(sMachinePrinter3) || "tmu220".equals(sMachinePrinter3) || "star".equals(sMachinePrinter3) || "ithaca".equals(sMachinePrinter3)) {
            config.setProperty("machine.printer.3", sMachinePrinter3 + ":" + comboValue(jcboConnPrinter3.getSelectedItem()) + "," + comboValue(jcboSerialPrinter3.getSelectedItem()));
        } else if ("javapos".equals(sMachinePrinter3)) {
            config.setProperty("machine.printer.3", sMachinePrinter3 + ":" + m_jtxtJPOSPrinter3.getText() + "," + m_jtxtJPOSDrawer3.getText());
        } else {
            config.setProperty("machine.printer.3", sMachinePrinter3);
        }
        
        String sMachineDisplay = comboValue(jcboMachineDisplay.getSelectedItem());
        if ("epson".equals(sMachineDisplay) || "ld200".equals(sMachineDisplay)) {
            config.setProperty("machine.display", sMachineDisplay + ":" + comboValue(jcboConnDisplay.getSelectedItem()) + "," + comboValue(jcboSerialDisplay.getSelectedItem()));
        } else if ("javapos".equals(sMachineDisplay)) {
            config.setProperty("machine.display", sMachineDisplay + ":" + m_jtxtJPOSName.getText());
        } else {
            config.setProperty("machine.display", sMachineDisplay);
        }
        
        // La bascula
        String sMachineScale = comboValue(jcboMachineScale.getSelectedItem());
        if ("dialog1".equals(sMachineScale) || "samsungesp".equals(sMachineScale)) {
            config.setProperty("machine.scale", sMachineScale + ":" + comboValue(jcboSerialScale.getSelectedItem()));
        } else {
            config.setProperty("machine.scale", sMachineScale);
        }
        
        // El scanner
        String sMachineScanner = comboValue(jcboMachineScanner.getSelectedItem());
        if ("scanpal2".equals(sMachineScale)) {
            config.setProperty("machine.scanner", sMachineScanner + ":" + comboValue(jcboSerialScanner.getSelectedItem()));
        } else {
            config.setProperty("machine.scanner", sMachineScanner);
        }
        
        config.setProperty("payment.magcardreader", comboValue(jcboCardReader.getSelectedItem()));
        config.setProperty("payment.gateway", comboValue(jcboPaymentGateway.getSelectedItem()));
        config.setProperty("payment.testmode", Boolean.toString(jchkPaymentTest.isSelected()));
        config.setProperty("payment.commerceid", jtxtCommerceID.getText());
        config.setProperty("payment.commercepassword", new String(jtxtCommercePwd.getPassword()));
        
        try {
            config.save();
            JOptionPane.showMessageDialog(this, AppLocal.getIntString("message.restartchanges"), AppLocal.getIntString("message.title"), JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JMessageDialog.showMessage(this, new MessageInf(MessageInf.SGN_WARNING, AppLocal.getIntString("message.cannotsaveconfig"), e));
        }
    }

    public JComponent getComponent() {
        return this;
    }
    
    public String getTitle() {
        return AppLocal.getIntString("Menu.Properties");
    } 
    
    public void activate() {
        loadProperties();        
    }
    
    public boolean deactivate() {
        return true;
    }      
    
    private String comboValue(Object value) {
        return value == null ? "" : value.toString();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel14 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel13 = new javax.swing.JPanel();
        jtxtMachineHostname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jcboMachineScreenmode = new javax.swing.JComboBox();
        jcboMachinePrinter = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jtxtCommerceID = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jtxtCommercePwd = new javax.swing.JPasswordField();
        jcboCardReader = new javax.swing.JComboBox();
        jchkPaymentTest = new javax.swing.JCheckBox();
        jcboPaymentGateway = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        jcboMachineDisplay = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        jcboTicketsBag = new javax.swing.JComboBox();
        jLabel18 = new javax.swing.JLabel();
        jcboMachinePrinter2 = new javax.swing.JComboBox();
        jLabel19 = new javax.swing.JLabel();
        jcboMachinePrinter3 = new javax.swing.JComboBox();
        m_jDisplayParams = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jlblConnDisplay = new javax.swing.JLabel();
        jcboConnDisplay = new javax.swing.JComboBox();
        jlblDisplayPort = new javax.swing.JLabel();
        jcboSerialDisplay = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        m_jtxtJPOSName = new javax.swing.JTextField();
        m_jPrinterParams1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jlblConnPrinter = new javax.swing.JLabel();
        jcboConnPrinter = new javax.swing.JComboBox();
        jlblPrinterPort = new javax.swing.JLabel();
        jcboSerialPrinter = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        m_jtxtJPOSPrinter = new javax.swing.JTextField();
        m_jtxtJPOSDrawer = new javax.swing.JTextField();
        m_jPrinterParams2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jlblConnPrinter2 = new javax.swing.JLabel();
        jcboConnPrinter2 = new javax.swing.JComboBox();
        jlblPrinterPort2 = new javax.swing.JLabel();
        jcboSerialPrinter2 = new javax.swing.JComboBox();
        jPanel11 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        m_jtxtJPOSPrinter2 = new javax.swing.JTextField();
        m_jtxtJPOSDrawer2 = new javax.swing.JTextField();
        m_jPrinterParams3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jlblConnPrinter3 = new javax.swing.JLabel();
        jcboConnPrinter3 = new javax.swing.JComboBox();
        jlblPrinterPort3 = new javax.swing.JLabel();
        jcboSerialPrinter3 = new javax.swing.JComboBox();
        jPanel12 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        m_jtxtJPOSPrinter3 = new javax.swing.JTextField();
        m_jtxtJPOSDrawer3 = new javax.swing.JTextField();
        jtxtDbPassword = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jtxtDbDriverLib = new javax.swing.JTextField();
        jbtnDbDriverLib = new javax.swing.JButton();
        jtxtDbDriver = new javax.swing.JTextField();
        jtxtDbURL = new javax.swing.JTextField();
        jtxtDbUser = new javax.swing.JTextField();
        m_jScaleParams = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jlblPrinterPort4 = new javax.swing.JLabel();
        jcboSerialScale = new javax.swing.JComboBox();
        jLabel25 = new javax.swing.JLabel();
        jcboMachineScale = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        jcboMachineScanner = new javax.swing.JComboBox();
        m_jScannerParams = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jlblPrinterPort5 = new javax.swing.JLabel();
        jcboSerialScanner = new javax.swing.JComboBox();
        jPanel15 = new javax.swing.JPanel();
        jbtnSave = new javax.swing.JButton();
        jbtnRestore = new javax.swing.JButton();
        jbtnCancel = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jPanel14.setLayout(new java.awt.BorderLayout());

        jPanel14.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        jPanel13.setLayout(null);

        jPanel13.setPreferredSize(new java.awt.Dimension(680, 680));
        jPanel13.add(jtxtMachineHostname);
        jtxtMachineHostname.setBounds(150, 230, 180, 19);

        jLabel5.setText(AppLocal.getIntString("Label.MachineName"));
        jPanel13.add(jLabel5);
        jLabel5.setBounds(20, 230, 130, 14);

        jLabel6.setText(AppLocal.getIntString("Label.MachineScreen"));
        jPanel13.add(jLabel6);
        jLabel6.setBounds(20, 260, 130, 14);

        jLabel7.setText(AppLocal.getIntString("Label.MachinePrinter"));
        jPanel13.add(jLabel7);
        jLabel7.setBounds(20, 350, 130, 14);

        jPanel13.add(jcboMachineScreenmode);
        jcboMachineScreenmode.setBounds(150, 260, 180, 20);

        jcboMachinePrinter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachinePrinterActionPerformed(evt);
            }
        });

        jPanel13.add(jcboMachinePrinter);
        jcboMachinePrinter.setBounds(150, 350, 180, 20);

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel9.setText(AppLocal.getIntString("Label.CashMachine"));
        jLabel9.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel13.add(jLabel9);
        jLabel9.setBounds(20, 200, 660, 17);

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel10.setText(AppLocal.getIntString("Label.Payment"));
        jLabel10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel13.add(jLabel10);
        jLabel10.setBounds(20, 510, 660, 17);

        jLabel11.setText(AppLocal.getIntString("label.magcardreader"));
        jPanel13.add(jLabel11);
        jLabel11.setBounds(20, 630, 130, 14);

        jLabel12.setText(AppLocal.getIntString("label.commerceid"));
        jPanel13.add(jLabel12);
        jLabel12.setBounds(20, 540, 130, 14);

        jPanel13.add(jtxtCommerceID);
        jtxtCommerceID.setBounds(150, 540, 180, 19);

        jLabel13.setText(AppLocal.getIntString("label.paymentgateway"));
        jPanel13.add(jLabel13);
        jLabel13.setBounds(20, 600, 130, 14);

        jLabel14.setText(AppLocal.getIntString("label.commercepwd"));
        jPanel13.add(jLabel14);
        jLabel14.setBounds(20, 570, 130, 14);

        jPanel13.add(jtxtCommercePwd);
        jtxtCommercePwd.setBounds(150, 570, 180, 20);

        jPanel13.add(jcboCardReader);
        jcboCardReader.setBounds(150, 630, 240, 20);

        jchkPaymentTest.setText(AppLocal.getIntString("label.paymenttestmode"));
        jPanel13.add(jchkPaymentTest);
        jchkPaymentTest.setBounds(400, 600, 130, 20);

        jPanel13.add(jcboPaymentGateway);
        jcboPaymentGateway.setBounds(150, 600, 240, 20);

        jLabel15.setText(AppLocal.getIntString("Label.MachineDisplay"));
        jPanel13.add(jLabel15);
        jLabel15.setBounds(20, 320, 130, 14);

        jcboMachineDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachineDisplayActionPerformed(evt);
            }
        });

        jPanel13.add(jcboMachineDisplay);
        jcboMachineDisplay.setBounds(150, 320, 180, 20);

        jLabel16.setText(AppLocal.getIntString("Label.Ticketsbag"));
        jPanel13.add(jLabel16);
        jLabel16.setBounds(20, 290, 130, 14);

        jPanel13.add(jcboTicketsBag);
        jcboTicketsBag.setBounds(150, 290, 180, 20);

        jLabel18.setText(AppLocal.getIntString("Label.MachinePrinter2"));
        jPanel13.add(jLabel18);
        jLabel18.setBounds(20, 380, 130, 14);

        jcboMachinePrinter2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachinePrinter2ActionPerformed(evt);
            }
        });

        jPanel13.add(jcboMachinePrinter2);
        jcboMachinePrinter2.setBounds(150, 380, 180, 20);

        jLabel19.setText(AppLocal.getIntString("Label.MachinePrinter3"));
        jPanel13.add(jLabel19);
        jLabel19.setBounds(20, 410, 130, 14);

        jcboMachinePrinter3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachinePrinter3ActionPerformed(evt);
            }
        });

        jPanel13.add(jcboMachinePrinter3);
        jcboMachinePrinter3.setBounds(150, 410, 180, 20);

        m_jDisplayParams.setLayout(new java.awt.CardLayout());

        jPanel2.setLayout(null);

        m_jDisplayParams.add(jPanel2, "empty");

        jPanel1.setLayout(null);

        jlblConnDisplay.setText(AppLocal.getIntString("label.machinedisplayconn"));
        jPanel1.add(jlblConnDisplay);
        jlblConnDisplay.setBounds(10, 0, 50, 14);

        jPanel1.add(jcboConnDisplay);
        jcboConnDisplay.setBounds(60, 0, 80, 20);

        jlblDisplayPort.setText(AppLocal.getIntString("label.machinedisplayport"));
        jPanel1.add(jlblDisplayPort);
        jlblDisplayPort.setBounds(160, 0, 50, 14);

        jcboSerialDisplay.setEditable(true);
        jPanel1.add(jcboSerialDisplay);
        jcboSerialDisplay.setBounds(210, 0, 130, 20);

        m_jDisplayParams.add(jPanel1, "comm");

        jPanel3.setLayout(null);

        jLabel20.setText(AppLocal.getIntString("Label.Name"));
        jPanel3.add(jLabel20);
        jLabel20.setBounds(10, 0, 50, 14);

        jPanel3.add(m_jtxtJPOSName);
        m_jtxtJPOSName.setBounds(60, 0, 150, 19);

        m_jDisplayParams.add(jPanel3, "javapos");

        jPanel13.add(m_jDisplayParams);
        m_jDisplayParams.setBounds(340, 320, 350, 20);

        m_jPrinterParams1.setLayout(new java.awt.CardLayout());

        jPanel5.setLayout(null);

        m_jPrinterParams1.add(jPanel5, "empty");

        jPanel6.setLayout(null);

        jlblConnPrinter.setText(AppLocal.getIntString("label.machinedisplayconn"));
        jPanel6.add(jlblConnPrinter);
        jlblConnPrinter.setBounds(10, 0, 50, 14);

        jPanel6.add(jcboConnPrinter);
        jcboConnPrinter.setBounds(60, 0, 80, 20);

        jlblPrinterPort.setText(AppLocal.getIntString("label.machineprinterport"));
        jPanel6.add(jlblPrinterPort);
        jlblPrinterPort.setBounds(160, 0, 50, 14);

        jcboSerialPrinter.setEditable(true);
        jPanel6.add(jcboSerialPrinter);
        jcboSerialPrinter.setBounds(210, 0, 130, 20);

        m_jPrinterParams1.add(jPanel6, "comm");

        jPanel4.setLayout(null);

        jLabel21.setText(AppLocal.getIntString("Label.Name"));
        jPanel4.add(jLabel21);
        jLabel21.setBounds(10, 0, 50, 14);

        jPanel4.add(m_jtxtJPOSPrinter);
        m_jtxtJPOSPrinter.setBounds(60, 0, 110, 19);

        jPanel4.add(m_jtxtJPOSDrawer);
        m_jtxtJPOSDrawer.setBounds(180, 0, 110, 19);

        m_jPrinterParams1.add(jPanel4, "javapos");

        jPanel13.add(m_jPrinterParams1);
        m_jPrinterParams1.setBounds(340, 350, 350, 20);

        m_jPrinterParams2.setLayout(new java.awt.CardLayout());

        jPanel7.setLayout(null);

        m_jPrinterParams2.add(jPanel7, "empty");

        jPanel8.setLayout(null);

        jlblConnPrinter2.setText(AppLocal.getIntString("label.machinedisplayconn"));
        jPanel8.add(jlblConnPrinter2);
        jlblConnPrinter2.setBounds(10, 0, 50, 14);

        jPanel8.add(jcboConnPrinter2);
        jcboConnPrinter2.setBounds(60, 0, 80, 20);

        jlblPrinterPort2.setText(AppLocal.getIntString("label.machineprinterport"));
        jPanel8.add(jlblPrinterPort2);
        jlblPrinterPort2.setBounds(160, 0, 50, 14);

        jcboSerialPrinter2.setEditable(true);
        jPanel8.add(jcboSerialPrinter2);
        jcboSerialPrinter2.setBounds(210, 0, 130, 20);

        m_jPrinterParams2.add(jPanel8, "comm");

        jPanel11.setLayout(null);

        jLabel22.setText(AppLocal.getIntString("Label.Name"));
        jPanel11.add(jLabel22);
        jLabel22.setBounds(10, 0, 50, 14);

        jPanel11.add(m_jtxtJPOSPrinter2);
        m_jtxtJPOSPrinter2.setBounds(60, 0, 110, 19);

        jPanel11.add(m_jtxtJPOSDrawer2);
        m_jtxtJPOSDrawer2.setBounds(180, 0, 110, 19);

        m_jPrinterParams2.add(jPanel11, "javapos");

        jPanel13.add(m_jPrinterParams2);
        m_jPrinterParams2.setBounds(340, 380, 350, 20);

        m_jPrinterParams3.setLayout(new java.awt.CardLayout());

        jPanel9.setLayout(null);

        m_jPrinterParams3.add(jPanel9, "empty");

        jPanel10.setLayout(null);

        jlblConnPrinter3.setText(AppLocal.getIntString("label.machinedisplayconn"));
        jPanel10.add(jlblConnPrinter3);
        jlblConnPrinter3.setBounds(10, 0, 50, 14);

        jPanel10.add(jcboConnPrinter3);
        jcboConnPrinter3.setBounds(60, 0, 80, 20);

        jlblPrinterPort3.setText(AppLocal.getIntString("label.machineprinterport"));
        jPanel10.add(jlblPrinterPort3);
        jlblPrinterPort3.setBounds(160, 0, 50, 14);

        jcboSerialPrinter3.setEditable(true);
        jPanel10.add(jcboSerialPrinter3);
        jcboSerialPrinter3.setBounds(210, 0, 130, 20);

        m_jPrinterParams3.add(jPanel10, "comm");

        jPanel12.setLayout(null);

        jLabel23.setText(AppLocal.getIntString("Label.Name"));
        jPanel12.add(jLabel23);
        jLabel23.setBounds(10, 0, 50, 14);

        jPanel12.add(m_jtxtJPOSPrinter3);
        m_jtxtJPOSPrinter3.setBounds(60, 0, 110, 19);

        jPanel12.add(m_jtxtJPOSDrawer3);
        m_jtxtJPOSDrawer3.setBounds(180, 0, 110, 19);

        m_jPrinterParams3.add(jPanel12, "javapos");

        jPanel13.add(m_jPrinterParams3);
        m_jPrinterParams3.setBounds(340, 410, 350, 20);

        jPanel13.add(jtxtDbPassword);
        jtxtDbPassword.setBounds(150, 160, 180, 20);

        jLabel4.setText(AppLocal.getIntString("Label.DbPassword"));
        jPanel13.add(jLabel4);
        jLabel4.setBounds(20, 160, 130, 14);

        jLabel3.setText(AppLocal.getIntString("Label.DbUser"));
        jPanel13.add(jLabel3);
        jLabel3.setBounds(20, 130, 130, 14);

        jLabel2.setText(AppLocal.getIntString("Label.DbURL"));
        jPanel13.add(jLabel2);
        jLabel2.setBounds(20, 100, 130, 14);

        jLabel1.setText(AppLocal.getIntString("Label.DbDriver"));
        jPanel13.add(jLabel1);
        jLabel1.setBounds(20, 70, 130, 14);

        jLabel17.setText(AppLocal.getIntString("label.dbdriverlib"));
        jPanel13.add(jLabel17);
        jLabel17.setBounds(20, 40, 130, 14);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 12));
        jLabel8.setText(AppLocal.getIntString("Label.Database"));
        jLabel8.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        jPanel13.add(jLabel8);
        jLabel8.setBounds(20, 10, 660, 17);

        jPanel13.add(jtxtDbDriverLib);
        jtxtDbDriverLib.setBounds(150, 40, 340, 19);

        jbtnDbDriverLib.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/adrianromero/images/fileopen.png")));
        jPanel13.add(jbtnDbDriverLib);
        jbtnDbDriverLib.setBounds(500, 40, 30, 25);

        jPanel13.add(jtxtDbDriver);
        jtxtDbDriver.setBounds(150, 70, 180, 19);

        jPanel13.add(jtxtDbURL);
        jtxtDbURL.setBounds(150, 100, 340, 19);

        jPanel13.add(jtxtDbUser);
        jtxtDbUser.setBounds(150, 130, 180, 19);

        m_jScaleParams.setLayout(new java.awt.CardLayout());

        jPanel16.setLayout(null);

        m_jScaleParams.add(jPanel16, "empty");

        jPanel17.setLayout(null);

        jlblPrinterPort4.setText(AppLocal.getIntString("label.machineprinterport"));
        jPanel17.add(jlblPrinterPort4);
        jlblPrinterPort4.setBounds(10, 0, 50, 14);

        jcboSerialScale.setEditable(true);
        jPanel17.add(jcboSerialScale);
        jcboSerialScale.setBounds(60, 0, 130, 20);

        m_jScaleParams.add(jPanel17, "comm");

        jPanel13.add(m_jScaleParams);
        m_jScaleParams.setBounds(340, 440, 350, 20);

        jLabel25.setText(AppLocal.getIntString("label.scale"));
        jPanel13.add(jLabel25);
        jLabel25.setBounds(20, 440, 130, 14);

        jcboMachineScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachineScaleActionPerformed(evt);
            }
        });

        jPanel13.add(jcboMachineScale);
        jcboMachineScale.setBounds(150, 440, 180, 20);

        jLabel26.setText(AppLocal.getIntString("label.scanner"));
        jPanel13.add(jLabel26);
        jLabel26.setBounds(20, 470, 130, 14);

        jcboMachineScanner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcboMachineScannerActionPerformed(evt);
            }
        });

        jPanel13.add(jcboMachineScanner);
        jcboMachineScanner.setBounds(150, 470, 180, 20);

        m_jScannerParams.setLayout(new java.awt.CardLayout());

        jPanel18.setLayout(null);

        m_jScannerParams.add(jPanel18, "empty");

        jPanel19.setLayout(null);

        jlblPrinterPort5.setText(AppLocal.getIntString("label.machineprinterport"));
        jPanel19.add(jlblPrinterPort5);
        jlblPrinterPort5.setBounds(10, 0, 50, 14);

        jcboSerialScanner.setEditable(true);
        jPanel19.add(jcboSerialScanner);
        jcboSerialScanner.setBounds(60, 0, 130, 20);

        m_jScannerParams.add(jPanel19, "comm");

        jPanel13.add(m_jScannerParams);
        m_jScannerParams.setBounds(340, 470, 350, 20);

        jScrollPane1.setViewportView(jPanel13);

        jPanel14.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(jPanel14, java.awt.BorderLayout.CENTER);

        jPanel15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jbtnSave.setText(AppLocal.getIntString("Button.Save"));
        jbtnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSaveActionPerformed(evt);
            }
        });

        jPanel15.add(jbtnSave);

        jbtnRestore.setText(AppLocal.getIntString("Button.Factory"));
        jbtnRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRestoreActionPerformed(evt);
            }
        });

        jPanel15.add(jbtnRestore);

        jbtnCancel.setText(AppLocal.getIntString("Button.Restore"));
        jbtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelActionPerformed(evt);
            }
        });

        jPanel15.add(jbtnCancel);

        add(jPanel15, java.awt.BorderLayout.SOUTH);

    }// </editor-fold>//GEN-END:initComponents

    private void jcboMachineScannerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachineScannerActionPerformed

        CardLayout cl = (CardLayout)(m_jScannerParams.getLayout());
        
        if ("scanpal2".equals(jcboMachineScanner.getSelectedItem())) {
            cl.show(m_jScannerParams, "comm");            
        } else {
            cl.show(m_jScannerParams, "empty");            
        } 
        
    }//GEN-LAST:event_jcboMachineScannerActionPerformed

    private void jcboMachineScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachineScaleActionPerformed

        CardLayout cl = (CardLayout)(m_jScaleParams.getLayout());
        
        if ("dialog1".equals(jcboMachineScale.getSelectedItem()) || "samsungesp".equals(jcboMachineScale.getSelectedItem())) {
            cl.show(m_jScaleParams, "comm");            
        } else {
            cl.show(m_jScaleParams, "empty");            
        }        
        
    }//GEN-LAST:event_jcboMachineScaleActionPerformed

    private void jcboMachinePrinter3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachinePrinter3ActionPerformed

        CardLayout cl = (CardLayout)(m_jPrinterParams3.getLayout());

        if ("epson".equals(jcboMachinePrinter3.getSelectedItem()) || "tmu220".equals(jcboMachinePrinter3.getSelectedItem()) || "star".equals(jcboMachinePrinter3.getSelectedItem()) || "ithaca".equals(jcboMachinePrinter3.getSelectedItem())) {
            cl.show(m_jPrinterParams3, "comm");   
        } else if ("javapos".equals(jcboMachinePrinter3.getSelectedItem())) {
            cl.show(m_jPrinterParams3, "javapos"); 
        } else {
            cl.show(m_jPrinterParams3, "empty");   
        }

    }//GEN-LAST:event_jcboMachinePrinter3ActionPerformed

    private void jcboMachinePrinter2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachinePrinter2ActionPerformed

        CardLayout cl = (CardLayout)(m_jPrinterParams2.getLayout());

        if ("epson".equals(jcboMachinePrinter2.getSelectedItem()) || "tmu220".equals(jcboMachinePrinter2.getSelectedItem()) || "star".equals(jcboMachinePrinter2.getSelectedItem()) || "ithaca".equals(jcboMachinePrinter2.getSelectedItem())) {
            cl.show(m_jPrinterParams2, "comm");   
        } else if ("javapos".equals(jcboMachinePrinter2.getSelectedItem())) {
            cl.show(m_jPrinterParams2, "javapos"); 
        } else {
            cl.show(m_jPrinterParams2, "empty");   
        }

    }//GEN-LAST:event_jcboMachinePrinter2ActionPerformed

    private void jcboMachineDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachineDisplayActionPerformed

        CardLayout cl = (CardLayout)(m_jDisplayParams.getLayout());
        
        if ("epson".equals(jcboMachineDisplay.getSelectedItem()) || "ld200".equals(jcboMachineDisplay.getSelectedItem())) {
            cl.show(m_jDisplayParams, "comm");   
        } else if ("javapos".equals(jcboMachineDisplay.getSelectedItem())) {
            cl.show(m_jDisplayParams, "javapos"); 
        } else {
            cl.show(m_jDisplayParams, "empty"); 
        }
        
    }//GEN-LAST:event_jcboMachineDisplayActionPerformed

    private void jcboMachinePrinterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcboMachinePrinterActionPerformed

        CardLayout cl = (CardLayout)(m_jPrinterParams1.getLayout());

        if ("epson".equals(jcboMachinePrinter.getSelectedItem()) || "tmu220".equals(jcboMachinePrinter.getSelectedItem()) || "star".equals(jcboMachinePrinter.getSelectedItem()) || "ithaca".equals(jcboMachinePrinter.getSelectedItem())) {
            cl.show(m_jPrinterParams1, "comm");   
        } else if ("javapos".equals(jcboMachinePrinter.getSelectedItem())) {
            cl.show(m_jPrinterParams1, "javapos"); 
        } else {
            cl.show(m_jPrinterParams1, "empty");   
        }
        
    }//GEN-LAST:event_jcboMachinePrinterActionPerformed

    private void jbtnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelActionPerformed

        if (JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.configrestore"), AppLocal.getIntString("message.title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {          
            loadProperties();
        }
        
    }//GEN-LAST:event_jbtnCancelActionPerformed

    private void jbtnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRestoreActionPerformed

        if (JOptionPane.showConfirmDialog(this, AppLocal.getIntString("message.configfactory"), AppLocal.getIntString("message.title"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {          
            restoreProperties();
        }
        
    }//GEN-LAST:event_jbtnRestoreActionPerformed

    private void jbtnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSaveActionPerformed

        saveProperties();
        
    }//GEN-LAST:event_jbtnSaveActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnCancel;
    private javax.swing.JButton jbtnDbDriverLib;
    private javax.swing.JButton jbtnRestore;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JComboBox jcboCardReader;
    private javax.swing.JComboBox jcboConnDisplay;
    private javax.swing.JComboBox jcboConnPrinter;
    private javax.swing.JComboBox jcboConnPrinter2;
    private javax.swing.JComboBox jcboConnPrinter3;
    private javax.swing.JComboBox jcboMachineDisplay;
    private javax.swing.JComboBox jcboMachinePrinter;
    private javax.swing.JComboBox jcboMachinePrinter2;
    private javax.swing.JComboBox jcboMachinePrinter3;
    private javax.swing.JComboBox jcboMachineScale;
    private javax.swing.JComboBox jcboMachineScanner;
    private javax.swing.JComboBox jcboMachineScreenmode;
    private javax.swing.JComboBox jcboPaymentGateway;
    private javax.swing.JComboBox jcboSerialDisplay;
    private javax.swing.JComboBox jcboSerialPrinter;
    private javax.swing.JComboBox jcboSerialPrinter2;
    private javax.swing.JComboBox jcboSerialPrinter3;
    private javax.swing.JComboBox jcboSerialScale;
    private javax.swing.JComboBox jcboSerialScanner;
    private javax.swing.JComboBox jcboTicketsBag;
    private javax.swing.JCheckBox jchkPaymentTest;
    private javax.swing.JLabel jlblConnDisplay;
    private javax.swing.JLabel jlblConnPrinter;
    private javax.swing.JLabel jlblConnPrinter2;
    private javax.swing.JLabel jlblConnPrinter3;
    private javax.swing.JLabel jlblDisplayPort;
    private javax.swing.JLabel jlblPrinterPort;
    private javax.swing.JLabel jlblPrinterPort2;
    private javax.swing.JLabel jlblPrinterPort3;
    private javax.swing.JLabel jlblPrinterPort4;
    private javax.swing.JLabel jlblPrinterPort5;
    private javax.swing.JTextField jtxtCommerceID;
    private javax.swing.JPasswordField jtxtCommercePwd;
    private javax.swing.JTextField jtxtDbDriver;
    private javax.swing.JTextField jtxtDbDriverLib;
    private javax.swing.JPasswordField jtxtDbPassword;
    private javax.swing.JTextField jtxtDbURL;
    private javax.swing.JTextField jtxtDbUser;
    private javax.swing.JTextField jtxtMachineHostname;
    private javax.swing.JPanel m_jDisplayParams;
    private javax.swing.JPanel m_jPrinterParams1;
    private javax.swing.JPanel m_jPrinterParams2;
    private javax.swing.JPanel m_jPrinterParams3;
    private javax.swing.JPanel m_jScaleParams;
    private javax.swing.JPanel m_jScannerParams;
    private javax.swing.JTextField m_jtxtJPOSDrawer;
    private javax.swing.JTextField m_jtxtJPOSDrawer2;
    private javax.swing.JTextField m_jtxtJPOSDrawer3;
    private javax.swing.JTextField m_jtxtJPOSName;
    private javax.swing.JTextField m_jtxtJPOSPrinter;
    private javax.swing.JTextField m_jtxtJPOSPrinter2;
    private javax.swing.JTextField m_jtxtJPOSPrinter3;
    // End of variables declaration//GEN-END:variables
    
}
