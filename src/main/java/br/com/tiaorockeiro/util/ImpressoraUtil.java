/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.stream.Collectors;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

/**
 *
 * @author Wendel
 */
public class ImpressoraUtil {

    @SuppressWarnings("ConvertToTryWithResources")
    public static void imprimir(String texto, String urlImpressora) throws Exception {
        PrintService printService = localizarImpressora(urlImpressora);
        if (printService == null) {
            throw new Exception("Impressora não encontrada!");
        } else {
            DocPrintJob printJob = printService.createPrintJob();
            InputStream stream = new ByteArrayInputStream(texto.getBytes());
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc doc = new SimpleDoc(stream, flavor, null);
            printJob.print(doc, null);
            stream.close();
        }
    }

    public static PrintService localizarImpressora(String urlImpressora) {
        List<PrintService> impressoras = asList(PrintServiceLookup.lookupPrintServices(DocFlavor.SERVICE_FORMATTED.PRINTABLE, null)).stream()
                .filter(ps -> ps.getName().equals(urlImpressora)).collect(Collectors.toList());
        return impressoras.isEmpty() ? null : impressoras.get(0);
    }
}
