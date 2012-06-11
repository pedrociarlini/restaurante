package br.com.marcao.ui.helpers;

import javax.swing.JOptionPane;

public class SwingHelper {

    /**
     * Exibe uma janela de mensagem de erro.
     * @param string
     */
    public static void showErrorMessage(String string) {
        JOptionPane.showMessageDialog(null, string, "ËRRO", JOptionPane.ERROR_MESSAGE);
    }

}
