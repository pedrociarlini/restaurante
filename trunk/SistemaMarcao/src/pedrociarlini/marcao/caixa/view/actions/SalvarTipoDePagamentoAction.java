package pedrociarlini.marcao.caixa.view.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import pedrociarlini.marcao.caixa.controller.CaixaFacade;
import pedrociarlini.marcao.caixa.exception.CaixaException;
import pedrociarlini.marcao.caixa.model.TipoPagamento;

public class SalvarTipoDePagamentoAction extends AbstractAction {
	
	public static final String KEY_TIPO_DE_PAGAMENTO = "KEY_TIPO_DE_PAGAMENTO";
	private CaixaFacade caixa = CaixaFacade.createInstance();
	public void actionPerformed(ActionEvent ev) {

		TipoPagamento tipo = (TipoPagamento) getValue(KEY_TIPO_DE_PAGAMENTO);
		try {
			caixa.inserirTipoDePagamento(tipo);
		} catch (CaixaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
