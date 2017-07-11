/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.modelo;

import static br.com.tiaorockeiro.util.QuantidadeUtil.formataQuantidade;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.Date;
import java.util.List;
import static org.apache.commons.lang.StringUtils.center;
import static org.apache.commons.lang.StringUtils.repeat;
import static org.apache.commons.lang.StringUtils.rightPad;

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
    private StringBuilder texto;
    private static final String PULO_LINHA = "\r\n";

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

    private void criaCabecalho() {
        this.texto.append(center("COMANDA MESA " + this.mesa, this.impressora.getQuantidadeCaracteres())).append(PULO_LINHA);
        this.texto.append(PULO_LINHA);
        this.texto.append(rightPad("OPERADOR: " + this.operador, this.impressora.getQuantidadeCaracteres())).append(PULO_LINHA);
        this.texto.append(rightPad("DATA: " + new SimpleDateFormat("dd/MM/yyyy").format(this.dataHora), this.impressora.getQuantidadeCaracteres())).append(PULO_LINHA);
        this.texto.append(rightPad("HORA: " + new SimpleDateFormat("HH:mm:ss").format(this.dataHora), this.impressora.getQuantidadeCaracteres())).append(PULO_LINHA);
    }

    private void criaItens() {
        if (!this.itens.isEmpty()) {
            this.texto.append(repeat("-", this.impressora.getQuantidadeCaracteres()));
            this.texto.append(rightPad("QTDE | PRODUTO", getImpressora().getQuantidadeCaracteres())).append(PULO_LINHA);
            this.texto.append(repeat("-", getImpressora().getQuantidadeCaracteres())).append(PULO_LINHA);
            this.itens.forEach(i -> {
                this.texto.append(rightPad(formataQuantidade(i.getQuantidade()) + " " + i.getDescricao().toUpperCase(), this.impressora.getQuantidadeCaracteres())).append(PULO_LINHA);
                if (!i.getAdicionais().isEmpty()) {
                    this.texto.append(rightPad("  ADICIONAIS:", getImpressora().getQuantidadeCaracteres())).append(PULO_LINHA);
                    i.getAdicionais().forEach(a -> {
                        this.texto.append(rightPad("    " + formataQuantidade(a.getQuantidade()) + " " + a.getDescricao().toUpperCase(), this.impressora.getQuantidadeCaracteres())).append(PULO_LINHA);
                    });
                }
                if (!i.getInformacoes().isEmpty()) {
                    this.texto.append(rightPad("  OBSERVACOES:", getImpressora().getQuantidadeCaracteres())).append(PULO_LINHA);
                    i.getInformacoes().forEach(o -> {
                        this.texto.append(rightPad("    " + o.toUpperCase(), this.impressora.getQuantidadeCaracteres())).append(PULO_LINHA);
                    });
                }
            });
            this.texto.append(repeat("-", this.impressora.getQuantidadeCaracteres()));
        }
    }

    public String getComanda() {
        this.texto = new StringBuilder();
        this.criaCabecalho();
        this.criaItens();
        this.texto.append(PULO_LINHA);
        this.texto.append(PULO_LINHA);
        this.texto.append(PULO_LINHA);
        this.texto.append(PULO_LINHA);
        this.texto.append(PULO_LINHA);
        this.texto.append(corte());
        return texto.toString();
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
}
