/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

/**
 *
 * @author INLOC01
 */
public class MoedaUtil {

    private static final DecimalFormat formatador;

    static {
        formatador = new DecimalFormat("#,##0.00", DecimalFormatSymbols.getInstance(Locale.getDefault()));
        formatador.setParseBigDecimal(true);
    }

    public static String formataMoeda(BigDecimal valor) {
        return formatador.format(valor);
    }

    public static BigDecimal parseMoeda(String valor) throws ParseException {
        return (BigDecimal) formatador.parse(valor);
    }
}
