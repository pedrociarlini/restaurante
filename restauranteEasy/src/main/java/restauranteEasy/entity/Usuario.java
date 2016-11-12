package restauranteEasy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Usuario extends MainEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "username")
	private String login;

	@Column(name = "password")
	private String senha;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
