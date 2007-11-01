package algoritmo;

public class Agentes {

	private Arquitetura arquitetura;
	private Programa programa;


	public Agentes(Arquitetura arquitetura, Programa programa) {
		// TODO Auto-generated constructor stub
		this.arquitetura = arquitetura;
		this.programa = programa;
	
	}

	/**
	 * @return Returns the arquitetura.
	 */
	public Arquitetura getArquitetura() {
		return arquitetura;
	}

	/**
	 * @param arquitetura
	 *            The arquitetura to set.
	 */
	public void setArquitetura(Arquitetura arquitetura) {
		this.arquitetura = arquitetura;
	}

	/**
	 * @return Returns the programa.
	 */
	public Programa getPrograma() {
		return programa;
	}

	/**
	 * @param programa
	 *            The programa to set.
	 */
	public void setPrograma(Programa programa) {
		this.programa = programa;
	}
}
