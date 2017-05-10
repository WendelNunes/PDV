/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.Usuario;
import br.com.tiaorockeiro.util.MensagemUtil;
import br.com.tiaorockeiro.util.SessaoUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Wendel
 */
public class TelaPrincipalController implements Initializable {

    @FXML
    private AnchorPane content;
    @FXML
    private HBox menuPrincipal;
    @FXML
    private Button botaoMesas;
    @FXML
    private Button botaoAbrirCaixa;
    @FXML
    private Button botaoFecharCaixa;
    @FXML
    private Button botaoConsultas;
    @FXML
    private Button botaoRelatorios;

    private static TelaPrincipalController instance;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            instance = this;
            this.ajustaMenu();
            AnchorPane telaMesa = FXMLLoader.load(getClass().getResource("/fxml/TelaMesas.fxml"));
            this.mudaTela(telaMesa);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void ajustaMenu() {
        Usuario usuario = SessaoUtil.getUsuario();
        this.menuPrincipal.getChildren().remove(this.botaoConsultas);
        if (!(usuario.isGerente() || usuario.isVendedor())) {
            this.menuPrincipal.getChildren().remove(this.botaoMesas);
        }
        if (!(usuario.isGerente() || usuario.isOperadorCaixa())) {
            this.menuPrincipal.getChildren().remove(this.botaoAbrirCaixa);
            this.menuPrincipal.getChildren().remove(this.botaoFecharCaixa);
            this.menuPrincipal.getChildren().remove(this.botaoRelatorios);
        }
    }

    @FXML
    public void acaoEntrarTelaMesa(ActionEvent event) {
        try {
            AnchorPane tela = FXMLLoader.load(getClass().getResource("/fxml/TelaMesas.fxml"));
            this.mudaTela(tela);
        } catch (IOException e) {
            MensagemUtil.enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoEntrarTelaAbrirCaixa(ActionEvent event) {
        try {
            AnchorPane tela = FXMLLoader.load(getClass().getResource("/fxml/TelaAberturaCaixa.fxml"));
            this.mudaTela(tela);
        } catch (IOException e) {
            MensagemUtil.enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoEntrarTelaFecharCaixa(ActionEvent event) {
        try {
            AnchorPane tela = FXMLLoader.load(getClass().getResource("/fxml/TelaFechamentoCaixa.fxml"));
            this.mudaTela(tela);
        } catch (IOException e) {
            MensagemUtil.enviarMensagemErro(e.getMessage());
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
