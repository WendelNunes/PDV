/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

/**
 *
 * @author INLOC01
 */
@Entity
@Table(name = "item_pedido")
public class ItemPedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_pedido", referencedColumnName = "id")
    private Pedido pedido;
    @ManyToOne
    @JoinColumn(name = "id_abertura_caixa", referencedColumnName = "id")
    private AberturaCaixa aberturaCaixa;
    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;
    @Column(name = "data_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    @ManyToOne
    @JoinColumn(name = "id_usuario_cancelamento", referencedColumnName = "id")
    private Usuario usuarioCancelamento;
    @Column(name = "data_hora_cancelamento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraCancelamento;
    @OneToOne
    @JoinColumn(name = "id_produto", referencedColumnName = "id")
    private Produto produto;
    @Column(name = "quantidade")
    private BigDecimal quantidade;
    @Column(name = "valor")
    private BigDecimal valor;
    @OneToMany(mappedBy = "itemPedido")
    private List<ObservacaoProduto> observacoes;
    @OneToMany(mappedBy = "itemPedido")
    private List<AdicionalProduto> adicionais;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public AberturaCaixa getAberturaCaixa() {
        return aberturaCaixa;
    }

    public void setAberturaCaixa(AberturaCaixa aberturaCaixa) {
        this.aberturaCaixa = aberturaCaixa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public Usuario getUsuarioCancelamento() {
        return usuarioCancelamento;
    }

    public void setUsuarioCancelamento(Usuario usuarioCancelamento) {
        this.usuarioCancelamento = usuarioCancelamento;
    }

    public Date getDataHoraCancelamento() {
        return dataHoraCancelamento;
    }

    public void setDataHoraCancelamento(Date dataHoraCancelamento) {
        this.dataHoraCancelamento = dataHoraCancelamento;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorTotalAdicionais() {
        return this.adicionais != null ? this.adicionais.stream().map(i -> i.getValorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add) : BigDecimal.ZERO;
    }

    public BigDecimal getValorTotal() {
        return this.quantidade.multiply(this.valor).setScale(2, RoundingMode.HALF_DOWN).add(this.getValorTotalAdicionais());
    }

    public List<ObservacaoProduto> getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(List<ObservacaoProduto> observacoes) {
        this.observacoes = observacoes;
    }

    public List<AdicionalProduto> getAdicionais() {
        return adicionais;
    }

    public void setAdicionais(List<AdicionalProduto> adicionais) {
        this.adicionais = adicionais;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItemPedido other = (ItemPedido) obj;
        return Objects.equals(this.id, other.id);
    }
}
