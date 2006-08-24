package pedrociarlini.marcao.caixa.controller;

import org.hibernate.Session;

import pedrociarlini.marcao.caixa.exception.CaixaException;
import pedrociarlini.marcao.caixa.model.TipoPagamento;
import pedrociarlini.marcao.caixa.util.CaixaMessages;
import pedrociarlini.util.hibernate.HibernateUtil;

public class CaixaFacade {

	private CaixaFacade() {
	}

	public static CaixaFacade createInstance() {
		return new CaixaFacade();
	}

	public void inserirTipoDePagamento(TipoPagamento tipo)
			throws CaixaException {
		Session session = HibernateUtil.getSession();
		try {
			session.save(tipo);
		} catch (Throwable ex) {
			new CaixaException(CaixaMessages.MSG_ERROR_INSERIR_TIPO_PAGAMENTO, ex);
		}
	}

}
