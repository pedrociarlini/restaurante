package pedrociarlini.marcao.caixa.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.JComboBox;

public class PanelVendasDoDia extends JPanel {

    private JComboBox comboBoxData = null;
    private JScrollPane jScrollPane = null;
    private JTable tableVendas = null;

    /**
     * This is the default constructor
     */
    public PanelVendasDoDia() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setLayout(new BorderLayout());
        this.setSize(436, 120);
        this.add(getComboBoxData(), java.awt.BorderLayout.NORTH);
        this.add(getJScrollPane(), java.awt.BorderLayout.CENTER);
    }

    /**
     * This method initializes comboBoxData	
     * 	
     * @return javax.swing.JComboBox	
     */
    private JComboBox getComboBoxData() {
        if (comboBoxData == null) {
            comboBoxData = new JComboBox();
            comboBoxData.setMaximumRowCount(5);
        }
        return comboBoxData;
    }

    /**
     * This method initializes jScrollPane	
     * 	
     * @return javax.swing.JScrollPane	
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getTableVendas());
        }
        return jScrollPane;
    }

    /**
     * This method initializes tableVendas	
     * 	
     * @return javax.swing.JTable	
     */
    private JTable getTableVendas() {
        if (tableVendas == null) {
            tableVendas = new JTable();
        }
        return tableVendas;
    }

}  //  @jve:decl-index=0:visual-constraint="10,10"
