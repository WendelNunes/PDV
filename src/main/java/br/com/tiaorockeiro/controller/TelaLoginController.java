/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.MainApp;
import br.com.tiaorockeiro.modelo.Usuario;
import br.com.tiaorockeiro.negocio.UsuarioNegocio;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemInformacao;
import br.com.tiaorockeiro.util.SessaoUtil;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;

/**
 * FXML Controller class
 *
 * @author Wendel
 */
public class TelaLoginController implements Initializable {
    
    @FXML
    private PasswordField senha;
    @FXML
    private ListView<Usuario> listaUsuarios;
    private MainApp application;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.listaUsuarios.setItems(FXCollections.observableList(new UsuarioNegocio().listarTodos(Usuario.class)));
        this.listaUsuarios.setCellFactory(param -> new ListCell<Usuario>() {
            @Override
            protected void updateItem(Usuario item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.getDescricao() == null) {
                    setText(null);
                } else {
                    setText(item.getDescricao());
                }
            }
        });
    }
    
    public void setApplication(MainApp application) {
        this.application = application;
    }
    
    @FXML
    public void acaoLogar(ActionEvent event) throws IOException {
        try {
            Usuario usuarioSelecionado = this.listaUsuarios.getSelectionModel().getSelectedItem();
            if (usuarioSelecionado != null && SessaoUtil.logar(usuarioSelecionado.getDescricao(), this.senha.getText())) {
                if (SessaoUtil.getUsuario().isAdministrador() || SessaoUtil.getUsuario().isGerente() || SessaoUtil.getUsuario().isOperadorCaixa()
                        || SessaoUtil.getUsuario().isVendedor()) {
                    this.application.irParaTelaPrincipal();
                } else {
                    SessaoUtil.fecharSessao();
                    enviarMensagemInformacao("Usuário não tem permissão para acessar o sistema!");
                }
            } else {
                enviarMensagemInformacao("Usuário/Senha inválida!");
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }
    
    @FXML
    public void acaoBotao1(ActionEvent event) {
        this.adicionaNumeroSenha(1);
    }
    
    @FXML
    public void acaoBotao2(ActionEvent event) {
        this.adicionaNumeroSenha(2);
    }
    
    @FXML
    public void acaoBotao3(ActionEvent event) {
        this.adicionaNumeroSenha(3);
    }
    
    @FXML
    public void acaoBotao4(ActionEvent event) {
        this.adicionaNumeroSenha(4);
    }
    
    @FXML
    public void acaoBotao5(ActionEvent event) {
        this.adicionaNumeroSenha(5);
    }
    
    @FXML
    public void acaoBotao6(ActionEvent event) {
        this.adicionaNumeroSenha(6);
    }
    
    @FXML
    public void acaoBotao7(ActionEvent event) {
        this.adicionaNumeroSenha(7);
    }
    
    @FXML
    public void acaoBotao8(ActionEvent event) {
        this.adicionaNumeroSenha(8);
    }
    
    @FXML
    public void acaoBotao9(ActionEvent event) {
        this.adicionaNumeroSenha(9);
    }
    
    @FXML
    public void acaoBotao0(ActionEvent event) {
        this.adicionaNumeroSenha(0);
    }
    
    @FXML
    public void acaoBotaoLimpar(ActionEvent event) {
        this.senha.setText("");
    }
    
    private void adicionaNumeroSenha(int numero) {
        this.senha.setText(this.senha.getText() + numero);
    }
}
