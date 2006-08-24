package pedrociarlini.marcao.caixa.exception;

public class CaixaException extends Exception {

	public CaixaException() {
	}
	public CaixaException(String message, Throwable ex) {
		super(message, ex);
	}

	public CaixaException(String message) {
		super(message);
	}

	public CaixaException(Throwable ex) {
		super(ex);
	}
}
