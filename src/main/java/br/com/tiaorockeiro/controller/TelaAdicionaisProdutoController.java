/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.MainApp;
import br.com.tiaorockeiro.modelo.AdicionalProduto;
import br.com.tiaorockeiro.modelo.ItemPedido;
import br.com.tiaorockeiro.modelo.Produto;
import br.com.tiaorockeiro.negocio.ProdutoNegocio;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
import static br.com.tiaorockeiro.util.MoedaUtil.formataMoeda;
import static br.com.tiaorockeiro.util.QuantidadeUtil.formataQuantidade;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import static javafx.collections.FXCollections.observableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author INLOC01
 */
public class TelaAdicionaisProdutoController implements Initializable {

    @FXML
    private TextField textFieldQuantidadeItens;
    @FXML
    private ScrollPane scrollProdutos;
    @FXML
    private TableView<AdicionalProduto> tableViewItens;
    @FXML
    private TableColumn<AdicionalProduto, String> tableColumnProduto;
    @FXML
    private TableColumn<AdicionalProduto, BigDecimal> tableColumnQuantidade;
    @FXML
    private TableColumn<AdicionalProduto, BigDecimal> tableColumnPreco;
    @FXML
    private TableColumn<AdicionalProduto, BigDecimal> tableColumnTotal;
    @FXML
    private TextField textFieldValorTotal;
    private ItemPedido itemPedido;
    private TelaPedidoController telaPedido;
    private Stage stage;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void ajustaTabelaItens() {
        this.tableViewItens.setFixedCellSize(45);
        this.tableColumnProduto.setCellValueFactory(i -> new SimpleStringProperty(i.getValue().getProduto().getDescricao()));
        this.tableColumnProduto.setCellFactory((TableColumn<AdicionalProduto, String> param) -> new TableCell<AdicionalProduto, String>() {
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
        this.tableColumnQuantidade.setCellFactory((TableColumn<AdicionalProduto, BigDecimal> param) -> new TableCell<AdicionalProduto, BigDecimal>() {
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
        this.tableColumnPreco.setCellFactory((TableColumn<AdicionalProduto, BigDecimal> param) -> new TableCell<AdicionalProduto, BigDecimal>() {
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
        this.tableColumnTotal.setCellFactory((TableColumn<AdicionalProduto, BigDecimal> param) -> new TableCell<AdicionalProduto, BigDecimal>() {
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

    private void acaoAdicionaProduto(ActionEvent event) {
        try {
            Produto produto = new ProdutoNegocio().obterPorId(Produto.class, Long.valueOf(((Control) event.getSource()).getId()));
            AdicionalProduto item = new AdicionalProduto();
            item.setItemPedido(this.itemPedido);
            item.setProduto(produto);
            item.setQuantidade(BigDecimal.ONE);
            item.setValor(produto.getValor());
            this.tableViewItens.getItems().add(item);
            this.telaPedido.updateItemPedidoSelecionado(this.itemPedido);
            this.atualizaTotalizadores();
        } catch (NumberFormatException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoMaisProduto(ActionEvent event) {
        try {
            int index = this.tableViewItens.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                AdicionalProduto item = this.tableViewItens.getSelectionModel().getSelectedItem();
                item.setQuantidade(item.getQuantidade().add(BigDecimal.ONE));
                this.tableViewItens.getItems().set(index, item);
                this.telaPedido.updateItemPedidoSelecionado(this.itemPedido);
                this.tableViewItens.getSelectionModel().select(index);
            }
            this.atualizaTotalizadores();
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoMenosProduto(ActionEvent event) {
        try {
            int index = this.tableViewItens.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                AdicionalProduto item = this.tableViewItens.getSelectionModel().getSelectedItem();
                if (item.getQuantidade().compareTo(BigDecimal.ONE) > 0) {
                    item.setQuantidade(item.getQuantidade().subtract(BigDecimal.ONE));
                    this.tableViewItens.getItems().set(index, item);
                    this.telaPedido.updateItemPedidoSelecionado(this.itemPedido);
                    this.tableViewItens.getSelectionModel().select(index);
                }
            }
            this.atualizaTotalizadores();
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoExcluirProduto() {
        try {
            int index = this.tableViewItens.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                this.tableViewItens.getItems().remove(index);
                this.telaPedido.updateItemPedidoSelecionado(this.itemPedido);
            }
            this.atualizaTotalizadores();
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

    private void criaGridProdutos() throws Exception {
        List<Produto> produtos = new ProdutoNegocio().listarAdicionais();
        GridPane gridProdutos = new GridPane();
        gridProdutos.setVgap(3);
        gridProdutos.setHgap(3);
        if (produtos != null && !produtos.isEmpty()) {
            int coluna = 0;
            int linha = 0;
            for (Produto produto : produtos) {
                gridProdutos.add(this.criaBotaoProdutos(produto), coluna, linha);
                ++coluna;
                if (coluna == 5) {
                    coluna = 0;
                    ++linha;
                }
            }
        }
        this.scrollProdutos.setContent(gridProdutos);
    }

    private Button criaBotaoProdutos(Produto produto) {
        Image image = new Image("/imagens/icon-table.png");
        Button button = new Button(produto.getDescricao(), new ImageView(image));
        button.setContentDisplay(ContentDisplay.TOP);
        button.getStyleClass().add("botao-produto");
        button.setId(produto.getId().toString());
        button.setOnAction((ActionEvent event) -> {
            this.acaoAdicionaProduto(event);
        });
        return button;
    }

    private void atualizaTotalizadores() {
        this.textFieldQuantidadeItens.setText(formataQuantidade(this.tableViewItens.getItems().stream().map(i -> i.getQuantidade()).reduce(BigDecimal.ZERO, BigDecimal::add)));
        this.textFieldValorTotal.setText(formataMoeda(this.tableViewItens.getItems().stream().map(i -> i.getValorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add)));
    }

    public void abrirTela(AnchorPane tela, TelaPedidoController telaPedido, ItemPedido itemPedido) throws Exception {
        this.itemPedido = itemPedido;
        this.telaPedido = telaPedido;
        this.criaGridProdutos();
        this.ajustaTabelaItens();
        this.tableViewItens.setItems(observableList(this.itemPedido.getAdicionais()));
        this.atualizaTotalizadores();
        this.stage = MainApp.getInstance().popup(tela, true);
    }
}
