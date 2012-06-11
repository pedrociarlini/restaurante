package br.com.marcao.pdv.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the MP_MESA database table.
 * 
 */
@Entity
@Table(name = "MP_MESA")
public class MesaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_MESA")
    private int idMesa;

    @Column(name = "NM_MESA")
    private String nomeMesa;

    // uni-directional many-to-one association to LocalMesaEntity
    @ManyToOne
    @JoinColumn(name = "ID_LOCAL_MESA")
    private LocalMesaEntity localMesa;

    public MesaEntity() {
    }

    public int getIdMesa() {
        return this.idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public String getNomeMesa() {
        return this.nomeMesa;
    }

    public void setNomeMesa(String nmMesa) {
        this.nomeMesa = nmMesa;
    }

    public LocalMesaEntity getLocalMesa() {
        return this.localMesa;
    }

    public void setLocalMesa(LocalMesaEntity mpLocalMesa) {
        this.localMesa = mpLocalMesa;
    }

}