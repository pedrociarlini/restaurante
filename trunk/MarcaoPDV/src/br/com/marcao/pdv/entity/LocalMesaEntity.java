package br.com.marcao.pdv.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the MP_LOCAL_MESA database table.
 * 
 */
@Entity
@Table(name="MP_LOCAL_MESA")
public class LocalMesaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_LOCAL_MESA")
	private int idLocalMesa;

	@Column(name="NM_LOCAL_MESA")
	private String nomeLocalMesa;

    public LocalMesaEntity() {
    }

	public int getIdLocalMesa() {
		return this.idLocalMesa;
	}

	public void setIdLocalMesa(int idLocalMesa) {
		this.idLocalMesa = idLocalMesa;
	}

	public String getNomeLocalMesa() {
		return this.nomeLocalMesa;
	}

	public void setNomeLocalMesa(String nmLocalMesa) {
		this.nomeLocalMesa = nmLocalMesa;
	}

}