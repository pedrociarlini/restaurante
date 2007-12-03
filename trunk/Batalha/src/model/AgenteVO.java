package model;

import java.io.Serializable;

@SuppressWarnings("unchecked")
public class AgenteVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Class classe;

	private String nome;

	public AgenteVO(String string, Class class1) {
		setNome(string);
		setClasse(class1);
	}

	public Class getClasse() {
		return classe;
	}

	public void setClasse(Class classe) {
		this.classe = classe;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return getNome();
	}
}