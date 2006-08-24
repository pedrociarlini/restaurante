package pedrociarlini.marcao.caixa.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import pedrociarlini.model.Entidade;

@Entity
@Table (name="CAIXA_VENDA")
public class Venda extends Entidade {

    private long id;

    private double valorVenda;

    private Date dataVenda;

    private TipoPagamento tipoPagamento;

    @Column (name="VENDA_DATA")
    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    @Transient
    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    @Column (name="VENDA_VALOR")
    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    @Id
    @GeneratedValue
    @Column (name="VENDA_ID")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
