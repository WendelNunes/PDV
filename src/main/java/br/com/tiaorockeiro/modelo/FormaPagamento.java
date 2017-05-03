/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.modelo;

import static java.util.Arrays.asList;

/**
 *
 * @author Wendel
 */
public enum FormaPagamento {

    DINHEIRO("1", "Dinheiro"),
    CARTAO_CREDITO("2", "Cartão de Crédito"),
    CARTAO_DEBITO("3", "Cartão de Débito"),
    OUTROS("4", "Outros");

    private final String id;
    private final String descricao;

    private FormaPagamento(String id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static FormaPagamento get(String id) {
        return asList(FormaPagamento.values()).stream().filter(fp -> fp.getId().equals(id)).reduce((a, b) -> {
            throw new IllegalStateException();
        }).get();
    }
}
