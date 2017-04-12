/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.util;

import javafx.scene.control.Alert;

/**
 *
 * @author Wendel
 */
public class MensagemUtil {

    public static void enviarMensagemErro(String mensagem) {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Erro");
        dialog.setHeaderText(mensagem);
        dialog.showAndWait();
    }

    public static void enviarMensagemInformacao(String mensagem) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Informação");
        dialog.setHeaderText(mensagem);
        dialog.showAndWait();
    }

    public static void enviarMensagemConfirmacao(String mensagem) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Confirmação");
        dialog.setHeaderText(mensagem);
        dialog.showAndWait();
    }
}
