/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.AberturaCaixa;
import br.com.tiaorockeiro.modelo.ConfiguracaoUsuario;
import br.com.tiaorockeiro.negocio.AberturaCaixaNegocio;
import br.com.tiaorockeiro.negocio.ConfiguracaoUsuarioNegocio;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemInformacao;
import br.com.tiaorockeiro.util.SessaoUtil;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Wendel
 */
public class TelaSelecionarCaixaController implements Initializable {

    @FXML
    private ComboBox<AberturaCaixa> cbCaixa;
    @FXML
    private TextField inputDataAbertura;
    @FXML
    private TextField inputHoraAbertura;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.cbCaixa.getItems().add(null);
            this.cbCaixa.getItems().addAll(FXCollections.observableList(new AberturaCaixaNegocio().listarAbertos()));
            this.cbCaixa.setCellFactory((ListView<AberturaCaixa> param) -> new ListCell<AberturaCaixa>() {
                @Override
                protected void updateItem(AberturaCaixa item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.getCaixa().getCodigo() + " - " + item.getCaixa().getDescricao());
                    } else {
                        setText(null);
                    }
                }
            });
            this.cbCaixa.setConverter(new StringConverter<AberturaCaixa>() {
                @Override
                public String toString(AberturaCaixa aberturaCaixa) {
                    return aberturaCaixa != null ? aberturaCaixa.getCaixa().getCodigo() + " - " + aberturaCaixa.getCaixa().getDescricao() : null;
                }

                @Override
                public AberturaCaixa fromString(String string) {
                    return null;
                }
            });
            this.cbCaixa.valueProperty().addListener((ObservableValue<? extends AberturaCaixa> observable, AberturaCaixa oldValue, AberturaCaixa newValue) -> {
                if (newValue != null) {
                    this.inputDataAbertura.setText(new SimpleDateFormat("dd/MM/yyyy").format(newValue.getDataHora()));
                    this.inputHoraAbertura.setText(new SimpleDateFormat("HH:mm:ss").format(newValue.getDataHora()));
                } else {
                    this.inputDataAbertura.setText(null);
                    this.inputHoraAbertura.setText(null);
                }
            });
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoBotaoSelecionarCaixa(ActionEvent event) {
        try {
            if (this.cbCaixa.getValue() == null) {
                enviarMensagemInformacao("Caixa deve ser informado!");
            } else {
                ConfiguracaoUsuario configuracaoUsuario = new ConfiguracaoUsuario();
                if (SessaoUtil.getUsuario().getConfiguracao() != null) {
                    configuracaoUsuario = SessaoUtil.getUsuario().getConfiguracao();
                }
                configuracaoUsuario.setCaixaSelecionado(this.cbCaixa.getValue().getCaixa());
                configuracaoUsuario.setUsuario(SessaoUtil.getUsuario());
                new ConfiguracaoUsuarioNegocio().salvar(configuracaoUsuario);
                SessaoUtil.getUsuario().setConfiguracao(configuracaoUsuario);
                this.acaoBotaoVoltar(null);
                enviarMensagemInformacao("Caixa selecionado com sucesso!");
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoBotaoVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaMesas.fxml"));
            AnchorPane telaMesas = loader.load();
            TelaPrincipalController.getInstance().mudaTela(telaMesas);
        } catch (IOException | NumberFormatException e) {
            enviarMensagemErro(e.getMessage());
        }
    }
}
