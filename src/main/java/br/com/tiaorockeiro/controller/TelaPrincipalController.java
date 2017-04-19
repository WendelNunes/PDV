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

    private static TelaPrincipalController instance;

    public TelaPrincipalController() {
        instance = this;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.ajustaMenu();
            AnchorPane telaMesa = FXMLLoader.load(getClass().getResource("/fxml/TelaMesas.fxml"));
            this.mudaTela(telaMesa);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void ajustaMenu() {
        Usuario usuario = SessaoUtil.getUsuario();
        if (usuario.isGerente() || usuario.isVendedor()) {
            Button btnEntrarTelaMesa = new Button("Mesas");
            btnEntrarTelaMesa.setOnAction(this::acaoEntrarTelaMesa);
            this.aplicaEstiloBotoesMenu(btnEntrarTelaMesa);
            this.menuPrincipal.getChildren().add(btnEntrarTelaMesa);
        }

        if (usuario.isGerente() || usuario.isOperadorCaixa()) {
            Button btnEntrarTelaAbrirCaixa = new Button("Abrir Caixa");
            btnEntrarTelaAbrirCaixa.setOnAction(this::acaoEntrarTelaAbrirCaixa);
            this.aplicaEstiloBotoesMenu(btnEntrarTelaAbrirCaixa);
            this.menuPrincipal.getChildren().add(btnEntrarTelaAbrirCaixa);

            Button btnEntrarTelaFecharCaixa = new Button("Fechar Caixa");
            btnEntrarTelaFecharCaixa.setOnAction(this::acaoEntrarTelaFecharCaixa);
            this.aplicaEstiloBotoesMenu(btnEntrarTelaFecharCaixa);
            this.menuPrincipal.getChildren().add(btnEntrarTelaFecharCaixa);

            Button btnEntrarTelaRelatorios = new Button("Relatórios");
            btnEntrarTelaRelatorios.setOnAction(this::acaoEntrarTelaRelatorios);
            this.aplicaEstiloBotoesMenu(btnEntrarTelaRelatorios);
            this.menuPrincipal.getChildren().add(btnEntrarTelaRelatorios);
        }
    }

    private void aplicaEstiloBotoesMenu(Button botao) {
        botao.setPrefWidth(86);
        botao.setPrefHeight(60);
        botao.setStyle("-fx-background-radius: 0");
    }

    private void acaoEntrarTelaMesa(ActionEvent event) {
        try {
            AnchorPane tela = FXMLLoader.load(getClass().getResource("/fxml/TelaMesas.fxml"));
            this.mudaTela(tela);
        } catch (IOException e) {
            MensagemUtil.enviarMensagemErro(e.getMessage());
        }
    }

    private void acaoEntrarTelaAbrirCaixa(ActionEvent event) {
        try {
            AnchorPane tela = FXMLLoader.load(getClass().getResource("/fxml/TelaAberturaCaixa.fxml"));
            this.mudaTela(tela);
        } catch (IOException e) {
            MensagemUtil.enviarMensagemErro(e.getMessage());
        }
    }

    private void acaoEntrarTelaFecharCaixa(ActionEvent event) {
        try {
            AnchorPane tela = FXMLLoader.load(getClass().getResource("/fxml/TelaFechamentoCaixa.fxml"));
            this.mudaTela(tela);
        } catch (IOException e) {
            MensagemUtil.enviarMensagemErro(e.getMessage());
        }
    }

    private void acaoEntrarTelaRelatorios(ActionEvent event) {

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
