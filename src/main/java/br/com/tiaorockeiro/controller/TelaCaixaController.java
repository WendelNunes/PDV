/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.Usuario;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
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

/**
 * FXML Controller class
 *
 * @author Wendel
 */
public class TelaCaixaController implements Initializable {

    @FXML
    private AnchorPane panelPrincipal;
    @FXML
    private Button botaoAbrirCaixa;
    @FXML
    private Button botaoFecharCaixa;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.ajustaMenu();
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    private void ajustaMenu() {
        Usuario usuario = SessaoUtil.getUsuario();
        if (!(usuario.isGerente() || usuario.isOperadorCaixa())) {
            this.panelPrincipal.getChildren().remove(this.botaoAbrirCaixa);
            this.panelPrincipal.getChildren().remove(this.botaoFecharCaixa);
        }
    }

    @FXML
    public void acaoBotaoAbrirCaixa(ActionEvent event) {
        try {
            AnchorPane tela = FXMLLoader.load(getClass().getResource("/fxml/TelaAberturaCaixa.fxml"));
            TelaPrincipalController.getInstance().mudaTela(tela, "Abertura de Caixa");
        } catch (IOException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoBotaoFecharCaixa(ActionEvent event) {
        try {
            AnchorPane tela = FXMLLoader.load(getClass().getResource("/fxml/TelaFechamentoCaixa.fxml"));
            TelaPrincipalController.getInstance().mudaTela(tela, "Fechamento de Caixa");
        } catch (IOException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoBotaoSelecionarCaixa(ActionEvent event) {
        try {
            AnchorPane tela = FXMLLoader.load(getClass().getResource("/fxml/TelaSelecionarCaixa.fxml"));
            TelaPrincipalController.getInstance().mudaTela(tela, "Selecionar Caixa");
        } catch (IOException e) {
            enviarMensagemErro(e.getMessage());
        }
    }
}
