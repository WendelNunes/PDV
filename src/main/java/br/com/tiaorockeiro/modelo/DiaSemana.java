/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.modelo;

import static java.util.Arrays.asList;

/**
 *
 * @author INLOC01
 */
public enum DiaSemana {

    DOMINGO(1, "Domingo"),
    SEGUNDA_FEIRA(2, "Segunda-Feira"),
    TERCA_FEIRA(3, "Terça-Feira"),
    QUARTA_FEIRA(4, "Quarta-Feira"),
    QUINTA_FEIRA(5, "Quinta-Feira"),
    SEXTA_FEIRA(6, "Sexta-Feira"),
    SABADO(7, "Sábado");

    private final Integer id;
    private final String descricao;

    private DiaSemana(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static DiaSemana get(Integer id) {
        return asList(DiaSemana.values()).stream().filter(ds -> ds.getId().equals(id)).reduce((a, b) -> {
            throw new IllegalStateException();
        }).get();
    }
}
