/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Wendel
 */
public class TelaPrincipalController implements Initializable {

    @FXML
    private AnchorPane content;
    private static TelaPrincipalController instance;

    public TelaPrincipalController() {
        instance = this;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            AnchorPane telaMesa = FXMLLoader.load(getClass().getResource("/fxml/TelaMesas.fxml"));
            this.mudaTela(telaMesa);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @FXML
    public void acaoEntrarTelaMesa(ActionEvent event) {
        try {
            AnchorPane telaMesa = FXMLLoader.load(getClass().getResource("/fxml/TelaMesas.fxml"));
            this.mudaTela(telaMesa);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void setAnchor(AnchorPane pane) {
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
    }

    public void mudaTela(AnchorPane pane) {
        this.setAnchor(pane);
        this.content.getChildren().clear();
        this.content.getChildren().add(pane);
    }

    public static TelaPrincipalController getInstance() {
        return instance;
    }
}
