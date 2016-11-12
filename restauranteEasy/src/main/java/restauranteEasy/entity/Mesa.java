package restauranteEasy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Mesa extends MainEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private String nome;

	private String localizacao;

	public Mesa(String nome2) {
		this();
		setNome(nome2);
	}

	public Mesa() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

}
