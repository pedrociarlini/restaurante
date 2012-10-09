package br.com.marcao.pdv.ui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import br.com.marcao.pdv.entity.MesaEntity;
import br.com.marcao.pdv.ui.helpers.IconesConstants;

public class MesaLabel extends JLabel implements MouseListener {

    public MesaLabel(MesaEntity mesa) {
        super(mesa.getNomeMesa());
        setIcon(IconesConstants.MESA_VAGA);
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.BOTTOM);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(true);
        setBackground(Color.WHITE);
        setName("MESA: " + mesa.getIdMesa());
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

}
