/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.MainApp;
import br.com.tiaorockeiro.modelo.AberturaCaixa;
import br.com.tiaorockeiro.modelo.ConfiguracaoUsuario;
import br.com.tiaorockeiro.modelo.Usuario;
import br.com.tiaorockeiro.negocio.AberturaCaixaNegocio;
import br.com.tiaorockeiro.util.JpaUtil;
import br.com.tiaorockeiro.util.MensagemUtil;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemConfirmacao;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
import br.com.tiaorockeiro.util.SessaoUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Button botaoRelatorios;
    @FXML
    private Label titulo;
    @FXML
    private Label labelCaixaSelecionado;
    @FXML
    private Label labelUsuario;

    private static TelaPrincipalController instance;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            instance = this;
            this.ajustaMenu();
            this.ajustaRodape();
            AnchorPane telaMesa = FXMLLoader.load(getClass().getResource("/fxml/TelaMesas.fxml"));
            this.mudaTela(telaMesa, "Mesas");
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    private void ajustaMenu() {
        Usuario usuario = SessaoUtil.getUsuario();
        if (!(usuario.isGerente() || usuario.isVendedor())) {
            this.menuPrincipal.getChildren().remove(this.botaoMesas);
        }
        if (!(usuario.isGerente() || usuario.isOperadorCaixa())) {
            this.menuPrincipal.getChildren().remove(this.botaoRelatorios);
        }
    }

    private void ajustaRodape() throws Exception {
        Usuario usuario = SessaoUtil.getUsuario();
        this.setLabelUsuario("Usuário: " + usuario.getDescricao());
        ConfiguracaoUsuario configuracaoUsuario = usuario.getConfiguracao();
        if (configuracaoUsuario != null && configuracaoUsuario.getCaixaSelecionado() != null) {
            AberturaCaixa aberturaCaixa = new AberturaCaixaNegocio().obterAbertoPorCaixa(configuracaoUsuario.getCaixaSelecionado());
            if (aberturaCaixa != null) {
                this.setLabelCaixaSelecionado("Caixa: " + aberturaCaixa.getCaixa().getDescricao());
            } else {
                this.setLabelCaixaSelecionado("Nenhum caixa selecionado!");
            }
        } else {
            this.setLabelCaixaSelecionado("Nenhum caixa selecionado!");
        }
    }

    @FXML
    public void acaoEntrarTelaMesa(ActionEvent event) {
        try {
            AnchorPane tela = FXMLLoader.load(getClass().getResource("/fxml/TelaMesas.fxml"));
            this.mudaTela(tela, "Mesas");
        } catch (IOException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoEntrarTelaCaixa(ActionEvent event) {
        try {
            AnchorPane tela = FXMLLoader.load(getClass().getResource("/fxml/TelaCaixa.fxml"));
            this.mudaTela(tela, "Caixa");
        } catch (IOException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoBotaoConsultas(ActionEvent event) {
        try {
            AnchorPane tela = FXMLLoader.load(getClass().getResource("/fxml/TelaConsultas.fxml"));
            this.mudaTela(tela, "Consultas");
        } catch (IOException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoSair(ActionEvent event) {
        try {
            MainApp.sair();
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    private void setAnchor(AnchorPane pane) {
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
    }

    public void mudaTela(AnchorPane pane, String titulo) {
        this.setAnchor(pane);
        this.content.getChildren().clear();
        this.content.getChildren().add(pane);
        this.titulo.setText(titulo);
    }

    public static TelaPrincipalController getInstance() {
        return instance;
    }

    public void setLabelCaixaSelecionado(String texto) {
        this.labelCaixaSelecionado.setText(texto);
    }

    public void setLabelUsuario(String usuario) {
        this.labelUsuario.setText(usuario);
    }
}
