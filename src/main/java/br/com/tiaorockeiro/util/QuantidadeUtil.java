/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 *
 * @author INLOC01
 */
public class QuantidadeUtil {

    private static final DecimalFormat formatador;

    static {
        formatador = new DecimalFormat("#,##0.00");
        formatador.setParseBigDecimal(true);
    }

    public static String formataQuantidade(BigDecimal quantidade) {
        return formatador.format(quantidade);
    }

    public static BigDecimal parseQuantidade(String quantidade) throws ParseException {
        return (BigDecimal) formatador.parse(quantidade);
    }
}
