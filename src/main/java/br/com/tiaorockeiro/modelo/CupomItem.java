/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.modelo;

import java.math.BigDecimal;

/**
 *
 * @author Wendel
 */
public class CupomItem {

    private final String descricao;
    private final BigDecimal quantidade;
    private final BigDecimal valorUnitario;
    private final BigDecimal valorTotal;

    public CupomItem(String descricao, BigDecimal quantidade, BigDecimal valorUnitario, BigDecimal valorTotal) {
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
