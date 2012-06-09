package br.com.marcao.pdv.ui;

import java.awt.Dimension;

import javax.swing.JInternalFrame;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class GerenciarPedidosWindow extends JInternalFrame {

    
    private static GerenciarPedidosWindow instance = null;

    public static GerenciarPedidosWindow getInstance() {
        if (instance == null) {
            instance = new GerenciarPedidosWindow();
        }
        return instance;
    }
    
    /**
     * Create the frame.
     */
    public GerenciarPedidosWindow() {
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);
        setClosable(true);
        setFrameIcon(new ImageIcon(GerenciarPedidosWindow.class.getResource("/br/com/marcao/pdv/ui/images/MaoAnotando 64.png")));
        setTitle("Gerenciar Pedidos");
        setBounds(100, 100, 640, 480);
        setMinimumSize(new Dimension(640, 480));
    }
}
