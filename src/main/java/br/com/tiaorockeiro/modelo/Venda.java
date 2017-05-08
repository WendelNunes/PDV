/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Wendel
 */
@Entity
@Table(name = "venda")
public class Venda implements Serializable {

    private Long id;
    private Pedido pedido;
    private Usuario usuario;
    private AberturaCaixa aberturaCaixa;
    private Date dataHora;
    private Usuario usuarioCancelamento;
    private Date dataHoraCancelamento;
    private Integer mesa;
    private List<ItemVenda> itens;
    private List<Pagamento> pagamentos;
    private BigDecimal valorComissao;
    private BigDecimal valorDesconto;
    private BigDecimal valorTotal;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @ManyToOne
    @JoinColumn(name = "id_abertura_caixa", referencedColumnName = "id")
    public AberturaCaixa getAberturaCaixa() {
        return aberturaCaixa;
    }

    public void setAberturaCaixa(AberturaCaixa aberturaCaixa) {
        this.aberturaCaixa = aberturaCaixa;
    }

    @Column(name = "data_hora")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    @ManyToOne
    @JoinColumn(name = "id_usuario_cancelamento", referencedColumnName = "id")
    public Usuario getUsuarioCancelamento() {
        return usuarioCancelamento;
    }

    public void setUsuarioCancelamento(Usuario usuarioCancelamento) {
        this.usuarioCancelamento = usuarioCancelamento;
    }

    @Column(name = "data_hora_cancelamento")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDataHoraCancelamento() {
        return dataHoraCancelamento;
    }

    public void setDataHoraCancelamento(Date dataHoraCancelamento) {
        this.dataHoraCancelamento = dataHoraCancelamento;
    }

    @Column(name = "mesa")
    public Integer getMesa() {
        return mesa;
    }

    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    @OneToOne
    @JoinColumn(name = "id_pedido", referencedColumnName = "id")
    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentos) {
        this.pagamentos = pagamentos;
    }

    @Column(name = "valor_comissao")
    public BigDecimal getValorComissao() {
        return valorComissao;
    }

    public void setValorComissao(BigDecimal valorComissao) {
        this.valorComissao = valorComissao;
    }

    @Column(name = "valor_desconto")
    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    @Transient
    public BigDecimal getValorTotalItens() {
        return this.itens != null ? this.itens.stream().map(i -> i.getValorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add) : BigDecimal.ZERO;
    }

    @Transient
    public BigDecimal getQuantidadeItens() {
        return this.itens != null ? this.itens.stream().map(i -> i.getQuantidade()).reduce(BigDecimal.ZERO, BigDecimal::add) : BigDecimal.ZERO;
    }

    @Column(name = "valor_total")
    public BigDecimal getValorTotal() {
        this.valorTotal = this.getValorTotalItens().add(this.valorComissao != null ? this.valorComissao : BigDecimal.ZERO)
                .subtract(this.valorDesconto != null ? this.valorDesconto : BigDecimal.ZERO);
        return this.valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Venda other = (Venda) obj;
        return Objects.equals(this.id, other.id);
    }
}
