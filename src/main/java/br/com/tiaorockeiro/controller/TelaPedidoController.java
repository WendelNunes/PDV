/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.CategoriaProduto;
import br.com.tiaorockeiro.modelo.ItemPedido;
import br.com.tiaorockeiro.modelo.Pedido;
import br.com.tiaorockeiro.modelo.Produto;
import br.com.tiaorockeiro.negocio.CategoriaProdutoNegocio;
import br.com.tiaorockeiro.negocio.ProdutoNegocio;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
import static br.com.tiaorockeiro.util.MoedaUtil.formataMoeda;
import static br.com.tiaorockeiro.util.QuantidadeUtil.formataQuantidade;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

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
    private TableColumn<ItemPedido, String> tableColumnQuantidade;
    @FXML
    private TableColumn<ItemPedido, String> tableColumnPreco;
    @FXML
    private TableColumn<ItemPedido, String> tableColumnTotal;
    @FXML
    private TableColumn<ItemPedido, String> tableColumnMenu;
    
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
    
    private void ajustaTabelaItens() {
        this.tableColumnProduto.setCellValueFactory(i -> new SimpleStringProperty(i.getValue().getProduto().getDescricao()));
        this.tableColumnQuantidade.setCellValueFactory(i -> new SimpleStringProperty(formataQuantidade(i.getValue().getQuantidade())));
        this.tableColumnPreco.setCellValueFactory(i -> new SimpleStringProperty(formataMoeda(i.getValue().getValor())));
        this.tableColumnTotal.setCellValueFactory(i -> new SimpleStringProperty(formataMoeda(i.getValue().getValorTotal())));
        this.tableColumnMenu.setCellFactory((TableColumn<ItemPedido, String> param) -> new TableCell<ItemPedido, String>() {
            Button btnDeletar = new Button("Deletar");
            
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    btnDeletar.setOnAction(event -> {
                        acaoDeletarProduto(getIndex());
                    });
                    setGraphic(btnDeletar);
                    setText(null);
                }
            }
            
        });
    }
    
    private void acaoDeletarProduto(int index) {
        this.tableViewItens.getItems().remove(index);
        this.pedido.getItens().remove(index);
    }
    
    private void acaoAdicionaProduto(ActionEvent event) {
        Produto produto = new ProdutoNegocio().obterPorId(Produto.class, Long.valueOf(((Control) event.getSource()).getId()));
        ItemPedido item = new ItemPedido();
        item.setPedido(this.pedido);
        item.setProduto(produto);
        item.setQuantidade(BigDecimal.ONE);
        item.setValor(produto.getValor());
        this.pedido.getItens().add(item);
        this.tableViewItens.setItems(FXCollections.observableList(this.pedido.getItens()));
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
            System.err.println(e);
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
