/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.modelo;

import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author INLOC01
 */
public class Comanda {

    private final Integer mesa;
    private final Date dataHora;
    private final String operador;
    private final Impressora impressora;
    private final List<ComandaItem> itens;

    public Comanda(Integer mesa, Date dataHora, String operador, Impressora impressora) {
        this.mesa = mesa;
        this.dataHora = dataHora;
        this.operador = operador;
        this.impressora = impressora;
        this.itens = new ArrayList<>();
    }

    public Integer getMesa() {
        return mesa;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public String getOperador() {
        return operador;
    }

    public Impressora getImpressora() {
        return impressora;
    }

    public void addItem(ComandaItem item) {
        this.itens.add(item);
    }

    public List<ComandaItem> getItens() {
        return unmodifiableList(this.itens);
    }

    public String getComanda() {
        return "";
    }
}
