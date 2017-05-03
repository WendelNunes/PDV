/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.ItemPedido;
import br.com.tiaorockeiro.modelo.Pedido;
import br.com.tiaorockeiro.modelo.Venda;
import br.com.tiaorockeiro.negocio.PedidoNegocio;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
import static br.com.tiaorockeiro.util.MoedaUtil.formataMoeda;
import static br.com.tiaorockeiro.util.QuantidadeUtil.formataQuantidade;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author Wendel
 */
public class TelaFinalizarVendaController implements Initializable {

    @FXML
    private Button btnAcaoVoltar;
    @FXML
    private Label titulo;
    @FXML
    private TableView<ItemPedido> tableViewProdutos;
    @FXML
    private TableColumn<ItemPedido, Long> tableColumnSequencia;
    @FXML
    private TableColumn<ItemPedido, String> tableColumnProduto;
    @FXML
    private TableColumn<ItemPedido, BigDecimal> tableColumnQuantidade;
    @FXML
    private TableColumn<ItemPedido, BigDecimal> tableColumnPreco;
    @FXML
    private TableColumn<ItemPedido, BigDecimal> tableColumnTotal;

    private Venda venda;

    public TelaFinalizarVendaController() {
        this.venda = new Venda();
        this.venda.setItens(new ArrayList<>());
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btnAcaoVoltar.setOnAction((ActionEvent event) -> {
            acaoVoltar(event);
        });
    }

    @SuppressWarnings("Convert2Lambda")
    private void ajustaTabelaItens() {
        this.venda.getPedido().getItens().sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
        this.tableViewProdutos.setItems(FXCollections.observableList(this.venda.getPedido().getItens().stream()
                .filter(i -> i.getDataHoraCancelamento() == null).collect(Collectors.toList())));
        this.tableViewProdutos.setFixedCellSize(45);
        this.tableColumnSequencia.setCellValueFactory(i -> new SimpleObjectProperty<>(i.getValue().getId()));
        this.tableColumnSequencia.setCellFactory((TableColumn<ItemPedido, Long> param) -> new TableCell<ItemPedido, Long>() {
            @Override
            protected void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText("");
                } else {
                    setText(String.valueOf(getIndex() + 1));
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_RIGHT);
                }
            }
        });
        this.tableColumnProduto.setCellValueFactory(i -> new SimpleStringProperty(i.getValue().getProduto().getDescricao()));
        this.tableColumnProduto.setCellFactory((TableColumn<ItemPedido, String> param) -> new TableCell<ItemPedido, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText("");
                } else {
                    setText(item);
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_LEFT);
                }
            }
        });
        this.tableColumnQuantidade.setCellValueFactory(i -> new SimpleObjectProperty<>(i.getValue().getQuantidade()));
        this.tableColumnQuantidade.setCellFactory((TableColumn<ItemPedido, BigDecimal> param) -> new TableCell<ItemPedido, BigDecimal>() {
            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText("");
                } else {
                    setText(formataQuantidade(item));
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_RIGHT);
                }
            }
        });
        this.tableColumnPreco.setCellValueFactory(i -> new SimpleObjectProperty<>(i.getValue().getValor()));
        this.tableColumnPreco.setCellFactory((TableColumn<ItemPedido, BigDecimal> param) -> new TableCell<ItemPedido, BigDecimal>() {
            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText("");
                } else {
                    setText(formataMoeda(item));
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_RIGHT);
                }
            }
        });
        this.tableColumnTotal.setCellValueFactory(i -> new SimpleObjectProperty<>(i.getValue().getValorTotal()));
        this.tableColumnTotal.setCellFactory((TableColumn<ItemPedido, BigDecimal> param) -> new TableCell<ItemPedido, BigDecimal>() {
            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText("");
                } else {
                    setText(formataMoeda(item));
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_RIGHT);
                }
            }
        });
    }

    @FXML
    public void acaoVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaPedido.fxml"));
            AnchorPane telaPedido = loader.load();
            TelaPedidoController telaPedidoController = loader.getController();
            telaPedidoController.inicializaDados(this.venda.getMesa());
            TelaPrincipalController.getInstance().mudaTela(telaPedido);
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    public void inicializaDados(Long idPedido) {
        this.venda.setPedido(new PedidoNegocio().obterPorId(Pedido.class, idPedido));
        this.venda.setMesa(this.venda.getPedido().getMesa());
        this.titulo.setText("Mesa " + this.venda.getMesa());
        this.ajustaTabelaItens();
    }
}
