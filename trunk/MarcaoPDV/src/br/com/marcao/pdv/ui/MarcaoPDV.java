package br.com.marcao.pdv.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.marcao.pdv.ui.actions.EntrarNoSistemaAction;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Toolkit;

public class MarcaoPDV {

    private JFrame frmMarcoDasOstras;

    private JTextField txtUsuario;

    private JPasswordField pwdSenha;

    private JButton btnEntrar;
    
    private Action action;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MarcaoPDV window = new MarcaoPDV();
                    window.frmMarcoDasOstras.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MarcaoPDV() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmMarcoDasOstras = new JFrame();
        frmMarcoDasOstras.setIconImage(Toolkit.getDefaultToolkit().getImage(MarcaoPDV.class.getResource("/br/com/marcao/pdv/ui/images/MarcaoIcone.jpeg")));
        frmMarcoDasOstras.setTitle("Marc\u00E3o das Ostras :: PDV 0.1beta");
        frmMarcoDasOstras.setBounds(100, 100, 450, 300);
        frmMarcoDasOstras.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        frmMarcoDasOstras.getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
                new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC, }));

        JLabel lblUsuario = new JLabel("Usu\u00E1rio");
        mainPanel.add(lblUsuario, "12, 12, right, default");

        txtUsuario = new JTextField();
        mainPanel.add(txtUsuario, "16, 12, fill, default");
        txtUsuario.setColumns(10);

        JLabel lblSenha = new JLabel("Senha");
        mainPanel.add(lblSenha, "12, 16, right, default");

        pwdSenha = new JPasswordField();
        mainPanel.add(pwdSenha, "16, 16, fill, default");

        action = new EntrarNoSistemaAction(txtUsuario, pwdSenha, frmMarcoDasOstras);
        
        btnEntrar = new JButton("Entrar");
        btnEntrar.setAction(action);
        mainPanel.add(btnEntrar, "16, 18, left, default");
    }
}
