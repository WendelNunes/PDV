/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author INLOC01
 */
@Entity
@Table(name = "observacao_produto")
public class ObservacaoProduto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_item_pedido", referencedColumnName = "id")
    private ItemPedido itemPedido;
    @ManyToOne
    @JoinColumn(name = "id_prefixo_observacao")
    private PrefixoObservacao prefixo;
    @ManyToOne
    @JoinColumn(name = "id_observacao")
    private Observacao observacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemPedido getItemPedido() {
        return itemPedido;
    }

    public void setItemPedido(ItemPedido itemPedido) {
        this.itemPedido = itemPedido;
    }

    public PrefixoObservacao getPrefixo() {
        return prefixo;
    }

    public void setPrefixo(PrefixoObservacao prefixo) {
        this.prefixo = prefixo;
    }

    public Observacao getObservacao() {
        return observacao;
    }

    public void setObservacao(Observacao observacao) {
        this.observacao = observacao;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final ObservacaoProduto other = (ObservacaoProduto) obj;
        return Objects.equals(this.id, other.id);
    }
}
