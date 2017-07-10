/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.modelo;

import static br.com.tiaorockeiro.util.MoedaUtil.formataMoeda;
import static br.com.tiaorockeiro.util.QuantidadeUtil.formataQuantidade;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.apache.commons.lang.StringUtils.center;
import static org.apache.commons.lang.StringUtils.leftPad;
import static org.apache.commons.lang.StringUtils.repeat;
import static org.apache.commons.lang.StringUtils.rightPad;

/**
 *
 * @author Wendel
 */
public class CupomExtrato extends Cupom {

    private StringBuilder texto;
    private static final String PULO_LINHA = "\r\n";

    public CupomExtrato(String empresa, String operador, String usuario, Integer mesa, Date dataHora, Impressora impressora) {
        super("Extrato de Pedido", empresa, operador, usuario, mesa, dataHora, impressora);
    }

    private void criaCabecalho() {
        this.texto.append(center(getEmpresa().toUpperCase(), getImpressora().getQuantidadeCaracteres())).append(PULO_LINHA);
        this.texto.append(PULO_LINHA);
        this.texto.append(center(getTituloCupom().toUpperCase(), getImpressora().getQuantidadeCaracteres())).append(PULO_LINHA);
        this.texto.append(PULO_LINHA);
        this.texto.append(rightPad("OPERADOR: " + getOperador().toUpperCase(), getImpressora().getQuantidadeCaracteres())).append(PULO_LINHA);
        this.texto.append(rightPad("MESA: " + getMesa(), getImpressora().getQuantidadeCaracteres())).append(PULO_LINHA);
        this.texto.append(rightPad("DATA ABERTURA: " + new SimpleDateFormat("dd/MM/yyyy").format(getDataHora()), getImpressora().getQuantidadeCaracteres())).append(PULO_LINHA);
        this.texto.append(rightPad("HORA ABERTURA: " + new SimpleDateFormat("HH:mm:ss").format(getDataHora()), getImpressora().getQuantidadeCaracteres())).append(PULO_LINHA);
    }

    private void criaItens() {
        if (!getItens().isEmpty()) {
            this.texto.append(repeat("-", getImpressora().getQuantidadeCaracteres()));
            this.texto.append(rightPad("PRODUTO", getImpressora().getQuantidadeCaracteres())).append(PULO_LINHA);
            this.texto.append(leftPad("QTDE | VALOR | TOTAL", getImpressora().getQuantidadeCaracteres())).append(PULO_LINHA);
            this.texto.append(repeat("-", getImpressora().getQuantidadeCaracteres())).append(PULO_LINHA);
            getItens().forEach(item -> {
                this.texto.append(rightPad(item.getDescricao().toUpperCase(), getImpressora().getQuantidadeCaracteres())).append(PULO_LINHA);
                this.texto.append(leftPad(formataQuantidade(item.getQuantidade()) + " " + formataMoeda(item.getValorUnitario()) + " "
                        + formataMoeda(item.getValorTotal()),
                        getImpressora().getQuantidadeCaracteres())).append(PULO_LINHA);
            });
            this.texto.append(repeat("-", getImpressora().getQuantidadeCaracteres())).append(PULO_LINHA);
        }
    }

    private void criaTotais() {
        if (!getTotais().isEmpty()) {
            getTotais().forEach(total -> {
                this.texto.append(leftPad(total.getDescricao() + " " + formataMoeda(total.getValor()),
                        getImpressora().getQuantidadeCaracteres())).append(PULO_LINHA);
            });
            this.texto.append(repeat("-", getImpressora().getQuantidadeCaracteres()));
        }
    }

    @Override
    public String getCupom() {
        this.texto = new StringBuilder();
        this.texto.append(inicioImpressao());
        this.criaCabecalho();
        this.criaItens();
        this.criaTotais();
        this.texto.append(PULO_LINHA);
        this.texto.append(center(getUsuario() + " - " + new SimpleDateFormat("dd/MM/yyyy").format(new Date())
                + " - " + new SimpleDateFormat("HH:mm:ss").format(new Date()), getImpressora().getQuantidadeCaracteres()));
        this.texto.append(PULO_LINHA);
        this.texto.append(PULO_LINHA);
        this.texto.append(PULO_LINHA);
        this.texto.append(PULO_LINHA);
        this.texto.append(PULO_LINHA);
        this.texto.append(corte());
        return texto.toString();
    }
}
