package br.com.marcao.pdv.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import pedrociarlini.reuse.ui.helpers.SwingHelper;
import br.com.marcao.pdv.buzz.BuzzFactory;
import br.com.marcao.pdv.buzz.MesaBuzz;
import br.com.marcao.pdv.entity.MesaEntity;
import br.com.marcao.pdv.ui.components.MesaLabel;
import br.com.marcao.pdv.ui.helpers.ColorConstants;

@SuppressWarnings("serial")
public class GerenciarPedidosWindow extends JInternalFrame implements MouseListener {

    private static GerenciarPedidosWindow instance = null;

    private JTable tableMesasAbertas;

    private MesaBuzz mesaBuzz;

    private JPanel panelMesas;

    public static GerenciarPedidosWindow getInstance() throws Exception {
        if (instance == null) {
            instance = new GerenciarPedidosWindow();
        }
        return instance;
    }

    private MesaBuzz getMesasBuzz() {
        if (mesaBuzz == null) {
            mesaBuzz = BuzzFactory.getInstance().retrieveBuzzInstance(BuzzFactory.BUZZ_MESA);
        }
        return mesaBuzz;
    }

    /**
     * Create the frame.
     * 
     * @throws Exception
     */
    public GerenciarPedidosWindow() throws Exception {
        setSize(new Dimension(700, 500));
        setResizable(true);
        setMaximizable(true);
        setIconifiable(true);
        setClosable(true);
        setFrameIcon(new ImageIcon(
                GerenciarPedidosWindow.class.getResource("/br/com/marcao/pdv/ui/images/MaoAnotando 64.png")));
        setTitle("Gerenciar Pedidos");
        setBounds(0, 0, 640, 481);
        setMinimumSize(new Dimension(640, 480));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        setContentPane(splitPane);

        JScrollPane scrollPaneMesasAbertas = new JScrollPane();
        scrollPaneMesasAbertas.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        scrollPaneMesasAbertas.setToolTipText("Mesas abertas.");
        scrollPaneMesasAbertas.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneMesasAbertas.setMinimumSize(new Dimension(200, 100));
        getContentPane().add(scrollPaneMesasAbertas, JSplitPane.LEFT);

        tableMesasAbertas = new JTable();
        tableMesasAbertas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableMesasAbertas.setFillsViewportHeight(true);
        tableMesasAbertas.setMinimumSize(new Dimension(200, 100));
        scrollPaneMesasAbertas.setViewportView(tableMesasAbertas);

        JPanel panelMainMesas = new JPanel();
        getContentPane().add(panelMainMesas, JSplitPane.RIGHT);
        panelMainMesas.setLayout(new BorderLayout(0, 0));

        JLabel lblMesas = new JLabel("Mesas");
        lblMesas.setHorizontalAlignment(SwingConstants.CENTER);
        panelMainMesas.add(lblMesas, BorderLayout.NORTH);

        // JScrollPane paneScrollMesas = new JScrollPane();
        // paneScrollMesas.setViewportBorder(new
        // BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        // panelMainMesas.add(paneScrollMesas, BorderLayout.CENTER);

        panelMesas = new JPanel();
        panelMesas.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panelMesas.setBackground(new Color(255, 255, 255));
        panelMesas.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
        // paneScrollMesas.setViewportView(panelMesas);
        panelMainMesas.add(panelMesas, BorderLayout.CENTER);

        JPanel paneOperacoesMesas = new JPanel();
        paneOperacoesMesas.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panelMainMesas.add(paneOperacoesMesas, BorderLayout.SOUTH);
        paneOperacoesMesas.setLayout(new GridLayout(2, 4, 4, 4));

        JButton btnAbrirMesa = new JButton("Abrir Mesa");
        paneOperacoesMesas.add(btnAbrirMesa);

        JButton btnFazerPedido = new JButton("Fazer Pedido");
        paneOperacoesMesas.add(btnFazerPedido);

        JButton btnIniciarFechamento = new JButton("Iniciar Fechamento");
        paneOperacoesMesas.add(btnIniciarFechamento);

        JButton btnFecharMesa = new JButton("Fechar Mesa");
        paneOperacoesMesas.add(btnFecharMesa);
        try {
            carregarDados();
        } catch (Exception ex) {
            SwingHelper.showErrorMessage("Erro durante o carregamento dos dados: " + ex.getMessage());
            dispose();
            instance = null;
            throw ex;
        }
    }

    private void carregarDados() {
        List<MesaEntity> mesas = getMesasBuzz().retrieveAll();
        MesaLabel newLblMesa;
        for (MesaEntity mesa : mesas) {
            newLblMesa = new MesaLabel(mesa);
            newLblMesa.addMouseListener(this);
            panelMesas.add(newLblMesa);
        }
    }

    @Override
    public void mouseClicked(MouseEvent ev) {
        if (ev.getSource() instanceof MesaLabel) {
            MesaLabel mesaLabel = (MesaLabel) ev.getSource();
            mesaLabel.setForeground(Color.WHITE);
            mesaLabel.setBackground(ColorConstants.MESA_SELECIONADA);

            for (int i = 0; i < panelMesas.getComponentCount(); i++) {
                if (mesaLabel != panelMesas.getComponent(i)) {
                    panelMesas.getComponent(i).setForeground(Color.BLACK);
                    panelMesas.getComponent(i).setBackground(Color.WHITE);
                }
            }
        }
        
        updateUI();
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }

}
