package action;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;

import pedrociarlini.marcao.caixa.view.actions.SalvarTipoDePagamentoAction;

/**
 * Implementa o design pattern Front controller.
 * @author pedro
 *
 */
public class ActionsFrontController extends AbstractAction {
	private static Map<String, Action> actions = new HashMap<String, Action>(10);

	public ActionsFrontController() {
        loadConfiguration();
	}

	private void loadConfiguration() {
        actions.put("SalvarTipoPagamento", new SalvarTipoDePagamentoAction());
        org.apache.commons.betwixt
        
    }

    public void actionPerformed(ActionEvent ev) {
		String actionCommand = ev.getActionCommand();
		Action action = actions.get(actionCommand);
		synchronized (action) {
			action.actionPerformed(ev);
		}
	}
}