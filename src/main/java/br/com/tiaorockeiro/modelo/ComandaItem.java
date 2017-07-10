/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.modelo;

import java.math.BigDecimal;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.List;

/**
 *
 * @author INLOC01
 */
public class ComandaItem {

    private final String descricao;
    private final BigDecimal quantidade;
    private final List<ComandaAdicional> adicionais;
    private final List<String> informacoes;

    public ComandaItem(String descricao, BigDecimal quantidade) {
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.adicionais = new ArrayList<>();
        this.informacoes = new ArrayList<>();
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void addAdicional(ComandaAdicional comandaAdicional) {
        this.adicionais.add(comandaAdicional);
    }

    public void addInformacao(String informacao) {
        this.informacoes.add(informacao);
    }

    public List<ComandaAdicional> getAdicionais() {
        return unmodifiableList(this.adicionais);
    }

    public List<String> getInformacoes() {
        return unmodifiableList(this.informacoes);
    }
}
