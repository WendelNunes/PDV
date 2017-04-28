/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.AberturaCaixa;
import br.com.tiaorockeiro.modelo.CategoriaProduto;
import br.com.tiaorockeiro.modelo.ItemPedido;
import br.com.tiaorockeiro.modelo.Pedido;
import br.com.tiaorockeiro.modelo.Produto;
import br.com.tiaorockeiro.negocio.AberturaCaixaNegocio;
import br.com.tiaorockeiro.negocio.CategoriaProdutoNegocio;
import br.com.tiaorockeiro.negocio.PedidoNegocio;
import br.com.tiaorockeiro.negocio.ProdutoNegocio;
import br.com.tiaorockeiro.util.MensagemUtil;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemInformacao;
import static br.com.tiaorockeiro.util.MoedaUtil.formataMoeda;
import static br.com.tiaorockeiro.util.QuantidadeUtil.formataQuantidade;
import br.com.tiaorockeiro.util.SessaoUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
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

/**
 * FXML Controller class
 *
 * @author Wendel
 */
public class TelaPedidoController implements Initializable {

    @FXML
    private AnchorPane anchorPaneTelaPedido;
    @FXML
    private Label titulo;
    @FXML
    private ScrollPane scrollCategorias;
    @FXML
    private GridPane gridCategorias;
    @FXML
    private ScrollPane scrollProdutos;
    @FXML
    private GridPane gridProdutos;
    @FXML
    private TableView<ItemPedido> tableViewItens;
    @FXML
    private TableColumn<ItemPedido, String> tableColumnProduto;
    @FXML
    private TableColumn<ItemPedido, BigDecimal> tableColumnQuantidade;
    @FXML
    private TableColumn<ItemPedido, BigDecimal> tableColumnPreco;
    @FXML
    private TableColumn<ItemPedido, BigDecimal> tableColumnTotal;
    @FXML
    private TextField textFieldQuantidadeItens;
    @FXML
    private TextField textFieldValorTotal;

    private List<CategoriaProduto> categorias;
    private List<Produto> produtos;
    private Pedido pedido;

    private static final int QTDE_COLUNAS_PRODUTOS = 5;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.pedido = new Pedido();
            this.pedido.setItens(new ArrayList<>());
            this.ajustaTabelaItens();
            this.criaGridCategorias();
            if (this.categorias != null && !this.categorias.isEmpty()) {
                this.criaGridProdutos(this.categorias.get(0).getId());
            }
            this.atualizaTotalizadores();
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaMesas.fxml"));
            AnchorPane telaMesas = loader.load();
            TelaPrincipalController.getInstance().mudaTela(telaMesas);
        } catch (IOException | NumberFormatException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    private void criaGridCategorias() {
        this.categorias = new CategoriaProdutoNegocio().listarTodos(CategoriaProduto.class);
        this.gridCategorias = new GridPane();
        this.gridCategorias.setVgap(10);
        this.gridCategorias.setHgap(10);
        if (this.categorias != null && !this.categorias.isEmpty()) {
            int posicao = 0;
            for (CategoriaProduto categoria : this.categorias) {
                this.gridCategorias.add(this.criaBotaoCategorias(categoria), ++posicao, 0);
            }
        }
        this.scrollCategorias.setContent(this.gridCategorias);
    }

    private Button criaBotaoCategorias(CategoriaProduto categoriaProduto) {
        Image image = new Image("/imagens/icon-table.png");
        Button button = new Button(categoriaProduto.getDescricao(), new ImageView(image));
        button.setContentDisplay(ContentDisplay.TOP);
        button.setPrefSize(123, 107);
        button.setStyle("-fx-background-radius: 0");
        button.setId(categoriaProduto.getId().toString());
        button.setOnAction((ActionEvent event) -> {
            this.selecionaCategoria(event);
        });
        return button;
    }

    private void selecionaCategoria(ActionEvent event) {
        try {
            Long idCategoria = Long.valueOf(((Control) event.getSource()).getId());
            this.criaGridProdutos(idCategoria);
            this.scrollProdutos.setContent(this.gridProdutos);
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    private void criaGridProdutos(Long idCategoria) throws Exception {
        this.produtos = new ProdutoNegocio().listarPorCategoria(idCategoria);
        this.gridProdutos = new GridPane();
        this.gridProdutos.setVgap(10);
        this.gridProdutos.setHgap(10);
        if (this.produtos != null && !this.produtos.isEmpty()) {
            int coluna = 0;
            int linha = 0;
            for (Produto produto : this.produtos) {
                this.gridProdutos.add(this.criaBotaoProdutos(produto), coluna, linha);
                ++coluna;
                if (coluna == QTDE_COLUNAS_PRODUTOS) {
                    coluna = 0;
                    ++linha;
                }
            }
        }
        this.scrollProdutos.setContent(this.gridProdutos);
    }

    private Button criaBotaoProdutos(Produto produto) {
        Image image = new Image("/imagens/icon-table.png");
        Button button = new Button(produto.getDescricao(), new ImageView(image));
        button.setContentDisplay(ContentDisplay.TOP);
        button.setPrefSize(107, 107);
        button.setStyle("-fx-background-radius: 0");
        button.setId(produto.getId().toString());
        button.setOnAction((ActionEvent event) -> {
            this.acaoAdicionaProduto(event);
        });
        return button;
    }

    @SuppressWarnings("Convert2Lambda")
    private void ajustaTabelaItens() {
        this.tableViewItens.setItems(FXCollections.observableList(this.pedido.getItens()));
        this.tableViewItens.setFixedCellSize(45);
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

    private void atualizaTotalizadores() {
        this.textFieldQuantidadeItens.setText(formataQuantidade(this.pedido.getQuantidadeItens()));
        this.textFieldValorTotal.setText(formataMoeda(this.pedido.getValorTotal()));
    }

    @FXML
    public void acaoMaisProduto() {
        int index = this.tableViewItens.getSelectionModel().getSelectedIndex();
        if (index != -1) {
            ItemPedido item = this.pedido.getItens().get(index);
            item.setQuantidade(item.getQuantidade().add(BigDecimal.ONE));
            this.tableViewItens.getItems().set(index, item);
        }
        this.atualizaTotalizadores();
    }

    @FXML
    public void acaoMenosProduto() {
        int index = this.tableViewItens.getSelectionModel().getSelectedIndex();
        if (index != -1) {
            ItemPedido item = this.pedido.getItens().get(index);
            if (item.getQuantidade().compareTo(BigDecimal.ONE) > 0) {
                item.setQuantidade(item.getQuantidade().subtract(BigDecimal.ONE));
                this.tableViewItens.getItems().set(index, item);
            }
        }
        this.atualizaTotalizadores();
    }

    @FXML
    public void acaoExcluirProduto() {
        int index = this.tableViewItens.getSelectionModel().getSelectedIndex();
        if (index != -1) {
            this.tableViewItens.getItems().remove(index);
        }
        this.atualizaTotalizadores();
    }

    private void acaoAdicionaProduto(ActionEvent event) {
        Produto produto = new ProdutoNegocio().obterPorId(Produto.class, Long.valueOf(((Control) event.getSource()).getId()));
        ItemPedido item = new ItemPedido();
        item.setPedido(this.pedido);
        item.setProduto(produto);
        item.setQuantidade(BigDecimal.ONE);
        item.setValor(produto.getValor());
        this.tableViewItens.getItems().add(item);
        this.atualizaTotalizadores();
    }

    @FXML
    public void acaoFinalizarPedido(ActionEvent event) {
        try {
            if (this.pedido.getItens().isEmpty()) {
                enviarMensagemInformacao("Deve ser adicionado um ou mais item(ns)!");
            } else {
                this.pedido.setDataHora(new Date());
                this.pedido.setUsuario(SessaoUtil.getUsuario());
                AberturaCaixa aberturaCaixa = new AberturaCaixaNegocio()
                        .obterAbertoPorCaixa(SessaoUtil.getUsuario().getConfiguracao().getCaixaSelecionado());
                this.pedido.setAberturaCaixa(aberturaCaixa);
                this.pedido = new PedidoNegocio().salvar(this.pedido);
                if (this.pedido.getId() != null && this.pedido.getId() > 0) {
                    this.acaoVoltar(null);
                    enviarMensagemInformacao("Pedido finalizado com sucesso!");
                } else {
                    throw new Exception("Erro ao finalizar pedido, tente novamente!");
                }
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoFinalizarVenda(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaFinalizarVenda.fxml"));
            TelaFinalizarVendaController telaFinalizarVendaController = new TelaFinalizarVendaController();
            telaFinalizarVendaController.setTelaPedidoController(this);
            loader.setController(telaFinalizarVendaController);
            AnchorPane telaFinalizarVenda = loader.load();
            TelaPrincipalController.getInstance().mudaTela(telaFinalizarVenda);
        } catch (IOException | NumberFormatException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    public void setMesa(Integer mesa) {
        this.pedido.setMesa(mesa);
        this.titulo.setText("Mesa " + this.pedido.getMesa());
    }

    public AnchorPane getAnchorPaneTelaPedido() {
        return anchorPaneTelaPedido;
    }
}
