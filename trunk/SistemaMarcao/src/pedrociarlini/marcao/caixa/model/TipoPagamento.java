package pedrociarlini.marcao.caixa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import pedrociarlini.model.Entidade;

@Entity
public class TipoPagamento extends Entidade {
	
	private int id;
	
	private String descricao;
	
	private boolean aVista;
	
	public TipoPagamento() {
		
	}

	public TipoPagamento(String descricao) {
		setDescricao(descricao);
	}

	@Column (name="TIPO_VENDA_DESCRICAO")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	@Column (name="TIPO_VENDA_ID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column (name ="TIPO_VENDA_AVISTA")
	public boolean isAVista() {
		return aVista;
	}

	public void setAVista(boolean vista) {
		aVista = vista;
	}
}