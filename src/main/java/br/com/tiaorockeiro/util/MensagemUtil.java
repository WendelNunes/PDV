/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Wendel
 */
public class MensagemUtil {

    private static final int BUTTON_HEIGHT = 52;
    private static final String BUTTON_STYLE = "-fx-background-radius: 0;";

    public static void enviarMensagemErro(String mensagem) {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        Button botaoOK = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        botaoOK.setPrefHeight(BUTTON_HEIGHT);
        botaoOK.setStyle(BUTTON_STYLE);
        dialog.setTitle("Erro");
        dialog.setHeaderText(mensagem);
        dialog.showAndWait();
    }

    public static void enviarMensagemInformacao(String mensagem) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        Button botaoOK = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        botaoOK.setPrefHeight(BUTTON_HEIGHT);
        botaoOK.setStyle(BUTTON_STYLE);
        dialog.setTitle("Informação");
        dialog.setHeaderText(mensagem);
        dialog.showAndWait();
    }

    public static boolean enviarMensagemConfirmacao(String mensagem) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        Button botaoYES = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        botaoYES.setPrefHeight(BUTTON_HEIGHT);
        botaoYES.setStyle(BUTTON_STYLE);
        Button botaoNO = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
        botaoNO.setPrefHeight(BUTTON_HEIGHT);
        botaoNO.setStyle(BUTTON_STYLE);
        dialog.setTitle("Confirmação");
        dialog.setHeaderText(mensagem);
        Optional<ButtonType> result = dialog.showAndWait();
        return result.get() == ButtonType.OK;
    }
}
