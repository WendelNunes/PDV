/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Wendel
 */
public class Total implements Serializable {

    public static final Integer TOTAL_DINHEIRO = 1;
    public static final Integer TOTAL_CARTAO_CREDITO = 2;
    public static final Integer TOTAL_CARTAO_DEBITO = 3;
    public static final Integer TOTAL_OUTROS = 4;
    public static final Integer TOTAL_DESCONTO = 5;
    public static final Integer TOTAL_COMISSAO = 6;
    public static final Integer TOTAL_GERAL = 7;
    public static final Integer TOTAL_PAGAMENTOS = 8;
    public static final Integer TOTAL_PAGAR = 9;
    public static final Integer TOTAL_TROCO = 10;

    private Integer id;
    private String descricao;
    private BigDecimal total;

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id);
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
        final Total other = (Total) obj;
        return Objects.equals(this.id, other.id);
    }

    public static Total dinheiro(BigDecimal total) {
        Total totalDinheiro = new Total();
        totalDinheiro.id = TOTAL_DINHEIRO;
        totalDinheiro.descricao = "Dinheiro";
        totalDinheiro.total = total;
        return totalDinheiro;
    }

    public static Total cartaoCredito(BigDecimal total) {
        Total totalCartaoCredito = new Total();
        totalCartaoCredito.id = TOTAL_CARTAO_CREDITO;
        totalCartaoCredito.descricao = "Cartão Crédito";
        totalCartaoCredito.total = total;
        return totalCartaoCredito;
    }

    public static Total cartaoDebito(BigDecimal total) {
        Total totalCartaoDebito = new Total();
        totalCartaoDebito.id = TOTAL_CARTAO_DEBITO;
        totalCartaoDebito.descricao = "Cartão Débito";
        totalCartaoDebito.total = total;
        return totalCartaoDebito;
    }

    public static Total outros(BigDecimal total) {
        Total totalOutros = new Total();
        totalOutros.id = TOTAL_OUTROS;
        totalOutros.descricao = "Outros";
        totalOutros.total = total;
        return totalOutros;
    }

    public static Total desconto(BigDecimal total) {
        Total totalDesconto = new Total();
        totalDesconto.id = TOTAL_DESCONTO;
        totalDesconto.descricao = "Desconto";
        totalDesconto.total = total;
        return totalDesconto;
    }

    public static Total comissao(BigDecimal total) {
        Total totalComissao = new Total();
        totalComissao.id = TOTAL_COMISSAO;
        totalComissao.descricao = "Comissão";
        totalComissao.total = total;
        return totalComissao;
    }

    public static Total totalGeral(BigDecimal total) {
        Total totalGeral = new Total();
        totalGeral.id = TOTAL_GERAL;
        totalGeral.descricao = "Total Geral";
        totalGeral.total = total;
        return totalGeral;
    }

    public static Total totalPagamentos(BigDecimal total) {
        Total totalPagamentos = new Total();
        totalPagamentos.id = TOTAL_PAGAMENTOS;
        totalPagamentos.descricao = "Total Pagamentos";
        totalPagamentos.total = total;
        return totalPagamentos;
    }

    public static Total totalPagar(BigDecimal total) {
        Total totalPagar = new Total();
        totalPagar.id = TOTAL_PAGAR;
        totalPagar.descricao = "Total a Pagar";
        totalPagar.total = total;
        return totalPagar;
    }

    public static Total troco(BigDecimal total) {
        Total totalTroco = new Total();
        totalTroco.id = TOTAL_TROCO;
        totalTroco.descricao = "Troco";
        totalTroco.total = total;
        return totalTroco;
    }

    public static Total criaPorId(Integer id, BigDecimal valor) {
        if (id.equals(TOTAL_DINHEIRO)) {
            return dinheiro(valor);
        } else if (id.equals(TOTAL_CARTAO_CREDITO)) {
            return cartaoCredito(valor);
        } else if (id.equals(TOTAL_CARTAO_DEBITO)) {
            return cartaoDebito(valor);
        } else if (id.equals(TOTAL_OUTROS)) {
            return outros(valor);
        } else if (id.equals(TOTAL_DESCONTO)) {
            return desconto(valor);
        } else if (id.equals(TOTAL_COMISSAO)) {
            return comissao(valor);
        } else if (id.equals(TOTAL_GERAL)) {
            return totalGeral(valor);
        } else if (id.equals(TOTAL_PAGAR)) {
            return totalPagar(valor);
        } else if (id.equals(TOTAL_PAGAMENTOS)) {
            return totalPagamentos(valor);
        } else if (id.equals(TOTAL_TROCO)) {
            return troco(valor);
        }
        return null;
    }
}
