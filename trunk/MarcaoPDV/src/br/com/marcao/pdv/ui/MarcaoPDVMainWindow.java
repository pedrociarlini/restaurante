package br.com.marcao.pdv.ui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import br.com.marcao.pdv.ui.actions.AbrirGerenciarPedidosAction;

public class MarcaoPDVMainWindow extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JDesktopPane contentPane;

    private Action pedidosAction;

    /**
     * Create the frame.
     */
    public MarcaoPDVMainWindow() {
        setTitle("MarcaoPDV 0.1beta");
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                MarcaoPDVMainWindow.class.getResource("/br/com/marcao/pdv/ui/images/MarcaoIcone.jpeg")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 500);
        

        setContentPane(getContentPane());

        pedidosAction = new AbrirGerenciarPedidosAction(getContentPane());

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnMesas = new JMenu("Mesas");
        mnMesas.setMnemonic('m');
        menuBar.add(mnMesas);

        JMenuItem mntmGerenciarPedidos = new JMenuItem("Gerenciar Pedidos");
        mntmGerenciarPedidos.setAction(pedidosAction);
        mntmGerenciarPedidos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK
                | InputEvent.SHIFT_MASK));
        mntmGerenciarPedidos.setIcon(new ImageIcon(MarcaoPDVMainWindow.class
                .getResource("/br/com/marcao/pdv/ui/images/MaoAnotando 32.png")));
        mnMesas.add(mntmGerenciarPedidos);
    }

    public JDesktopPane getContentPane() {
        if (contentPane == null) {
            contentPane = new JDesktopPane();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            contentPane.setLayout(new BorderLayout(0, 0));
        }
        return contentPane;
    }
}
