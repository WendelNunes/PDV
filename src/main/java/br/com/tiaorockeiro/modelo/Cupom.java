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
 * @author Wendel
 */
public abstract class Cupom {

    private final String tituloCupom;
    private final String empresa;
    private final String operador;
    private final String usuario;
    private final Integer mesa;
    private final Date dataHora;
    private final List<CupomItem> itens;
    private final List<CupomPagamento> pagamentos;
    private final List<CupomTotal> totais;
    private final Impressora impressora;

    public Cupom(String tituloCupom, String empresa, String operador, String usuario, Integer mesa, Date dataHora, Impressora impressora) {
        this.tituloCupom = tituloCupom;
        this.empresa = empresa;
        this.operador = operador;
        this.usuario = usuario;
        this.mesa = mesa;
        this.dataHora = dataHora;
        this.itens = new ArrayList<>();
        this.pagamentos = new ArrayList<>();
        this.totais = new ArrayList<>();
        this.impressora = impressora;
    }

    public void addItem(CupomItem item) {
        this.itens.add(item);
    }

    public void addPagamento(CupomPagamento pagamento) {
        this.pagamentos.add(pagamento);
    }

    public void addTotal(CupomTotal cupomTotal) {
        this.totais.add(cupomTotal);
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getOperador() {
        return operador;
    }

    public String getUsuario() {
        return usuario;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public List<CupomItem> getItens() {
        return unmodifiableList(this.itens);
    }

    public List<CupomPagamento> getPagamentos() {
        return unmodifiableList(pagamentos);
    }

    public List<CupomTotal> getTotais() {
        return unmodifiableList(totais);
    }

    public Impressora getImpressora() {
        return impressora;
    }

    public String getTituloCupom() {
        return tituloCupom;
    }

    public Integer getMesa() {
        return mesa;
    }

    public String inicioImpressao() {
        String codigo = "";
        if (this.impressora != null && this.impressora.getCodigoInicioImpressao() != null) {
            String[] codigos = this.impressora.getCodigoInicioImpressao().split(",");
            for (String c : codigos) {
                codigo += Character.toString((char) Integer.parseInt(c));
            }
        }
        return codigo;
    }

    public String corte() {
        String codigo = "";
        if (this.impressora != null && this.impressora.getCodigoCorte() != null) {
            String[] codigos = this.impressora.getCodigoCorte().split(",");
            for (String c : codigos) {
                codigo += Character.toString((char) Integer.parseInt(c));
            }
        }
        return codigo;
    }

    public abstract String getCupom();
}
