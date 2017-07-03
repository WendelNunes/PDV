/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.modelo;

/**
 *
 * @author Wendel
 */
public class Informacao {

    private final String descricao;
    private final String texto;

    public Informacao(String descricao, String texto) {
        this.descricao = descricao;
        this.texto = texto;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTexto() {
        return texto;
    }
}
