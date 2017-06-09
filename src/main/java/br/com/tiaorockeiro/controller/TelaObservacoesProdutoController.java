/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.MainApp;
import br.com.tiaorockeiro.modelo.ItemPedido;
import br.com.tiaorockeiro.modelo.Observacao;
import br.com.tiaorockeiro.modelo.ObservacaoProduto;
import br.com.tiaorockeiro.modelo.PrefixoObservacao;
import br.com.tiaorockeiro.negocio.ObservacaoNegocio;
import br.com.tiaorockeiro.negocio.PrefixoObservacaoNegocio;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemInformacao;
import java.net.URL;
import java.util.ResourceBundle;
import static javafx.collections.FXCollections.observableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author INLOC01
 */
public class TelaObservacoesProdutoController implements Initializable {

    @FXML
    private ComboBox<PrefixoObservacao> prefixoObservacao;
    @FXML
    private ComboBox<Observacao> observacao;
    @FXML
    private ListView<ObservacaoProduto> observacoes;
    private ItemPedido itemPedido;
    private Stage stage;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.prefixoObservacao.getItems().add(null);
        this.prefixoObservacao.getItems().addAll(new PrefixoObservacaoNegocio().listarTodos(PrefixoObservacao.class));
        this.prefixoObservacao.setCellFactory((ListView<PrefixoObservacao> param) -> new ListCell<PrefixoObservacao>() {
            @Override
            protected void updateItem(PrefixoObservacao item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getDescricao());
                } else {
                    setText(null);
                }
            }
        });
        this.prefixoObservacao.setConverter(new StringConverter<PrefixoObservacao>() {
            @Override
            public String toString(PrefixoObservacao item) {
                return item != null ? item.getDescricao() : null;
            }

            @Override
            public PrefixoObservacao fromString(String string) {
                return null;
            }
        });
        this.observacao.setCellFactory((ListView<Observacao> param) -> new ListCell<Observacao>() {
            @Override
            protected void updateItem(Observacao item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getDescricao());
                } else {
                    setText(null);
                }
            }
        });
        this.observacao.setConverter(new StringConverter<Observacao>() {
            @Override
            public String toString(Observacao item) {
                return item != null ? item.getDescricao() : null;
            }

            @Override
            public Observacao fromString(String string) {
                return null;
            }
        });
        this.observacao.getItems().add(null);
        this.observacao.getItems().addAll(new ObservacaoNegocio().listarTodos(Observacao.class));
        this.observacoes.setCellFactory(param -> new ListCell<ObservacaoProduto>() {
            @Override
            protected void updateItem(ObservacaoProduto item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText((item.getPrefixo() != null ? item.getPrefixo().getDescricao() + " " : "") + item.getObservacao().getDescricao());
                } else {
                    setText(null);
                }
            }
        });
    }

    @FXML
    public void acaoAdicionarObservacao(ActionEvent event) {
        try {
            if (this.observacao.getSelectionModel().getSelectedItem() == null) {
                enviarMensagemInformacao("Deve ser informado a observação!");
            } else {
                ObservacaoProduto observacaoProduto = new ObservacaoProduto();
                observacaoProduto.setItemPedido(this.itemPedido);
                observacaoProduto.setPrefixo(this.prefixoObservacao.getSelectionModel().getSelectedItem());
                observacaoProduto.setObservacao(this.observacao.getSelectionModel().getSelectedItem());
                this.observacoes.getItems().add(observacaoProduto);
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoFechar(ActionEvent event) {
        try {
            this.stage.close();
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    public void abrirTela(AnchorPane tela, ItemPedido itemPedido) throws Exception {
        this.itemPedido = itemPedido;
        this.observacoes.setItems(observableList(this.itemPedido.getObservacoes()));
        this.stage = MainApp.getInstance().popup(tela, true);
    }
}
