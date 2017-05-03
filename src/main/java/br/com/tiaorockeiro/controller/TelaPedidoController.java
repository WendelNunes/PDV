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
import br.com.tiaorockeiro.negocio.ItemPedidoNegocio;
import br.com.tiaorockeiro.negocio.PedidoNegocio;
import br.com.tiaorockeiro.negocio.ProdutoNegocio;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemConfirmacao;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemInformacao;
import static br.com.tiaorockeiro.util.MoedaUtil.formataMoeda;
import static br.com.tiaorockeiro.util.QuantidadeUtil.formataQuantidade;
import br.com.tiaorockeiro.util.SessaoUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
    private TableColumn<ItemPedido, Long> tableColumnSequencia;
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
    private final AberturaCaixa aberturaCaixa;

    private static final int QTDE_COLUNAS_PRODUTOS = 5;

    public TelaPedidoController() throws Exception {
        this.aberturaCaixa = new AberturaCaixaNegocio()
                .obterAbertoPorCaixa(SessaoUtil.getUsuario().getConfiguracao().getCaixaSelecionado());
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
        this.tableViewItens.setFixedCellSize(45);
        this.tableColumnSequencia.setCellValueFactory(i -> new SimpleObjectProperty<>(i.getValue().getProduto().getId()));
        this.tableColumnSequencia.setCellFactory((TableColumn<ItemPedido, Long> param) -> new TableCell<ItemPedido, Long>() {
            @Override
            protected void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(String.valueOf(getIndex() + 1));
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_RIGHT);
                } else {
                    setText("");
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

    private void atualizaTotalizadores() {
        this.textFieldQuantidadeItens.setText(formataQuantidade(this.tableViewItens.getItems().stream().map(i -> i.getQuantidade()).reduce(BigDecimal.ZERO, BigDecimal::add)));
        this.textFieldValorTotal.setText(formataMoeda(this.tableViewItens.getItems().stream().map(i -> i.getValorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add)));
    }

    @FXML
    public void acaoMaisProduto() {
        int index = this.tableViewItens.getSelectionModel().getSelectedIndex();
        if (index != -1) {
            ItemPedido item = this.tableViewItens.getSelectionModel().getSelectedItem();
            item.setQuantidade(item.getQuantidade().add(BigDecimal.ONE));
            this.tableViewItens.getItems().set(index, item);
            this.tableViewItens.getSelectionModel().select(index);
        }
        this.atualizaTotalizadores();
    }

    @FXML
    public void acaoMenosProduto() {
        int index = this.tableViewItens.getSelectionModel().getSelectedIndex();
        if (index != -1) {
            ItemPedido item = this.tableViewItens.getSelectionModel().getSelectedItem();
            if (item.getQuantidade().compareTo(BigDecimal.ONE) > 0) {
                item.setQuantidade(item.getQuantidade().subtract(BigDecimal.ONE));
                this.tableViewItens.getItems().set(index, item);
                this.tableViewItens.getSelectionModel().select(index);
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
        try {
            Produto produto = new ProdutoNegocio().obterPorId(Produto.class, Long.valueOf(((Control) event.getSource()).getId()));
            ItemPedido item = new ItemPedido();
            item.setPedido(this.pedido);
            item.setAberturaCaixa(this.aberturaCaixa);
            item.setUsuario(SessaoUtil.getUsuario());
            item.setDataHora(new Date());
            item.setProduto(produto);
            item.setQuantidade(BigDecimal.ONE);
            item.setValor(produto.getValor());
            this.tableViewItens.getItems().add(item);
            this.atualizaTotalizadores();
        } catch (NumberFormatException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoFinalizarPedido(ActionEvent event) {
        try {
            if (!this.tableViewItens.getItems().isEmpty()) {
                this.salvarPedido();
                this.acaoVoltar(null);
                enviarMensagemInformacao("Pedido realizado com sucesso!");
            } else {
                enviarMensagemInformacao("Deve ser adicionado um ou mais item!");
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    private void salvarPedido() {
        if (this.pedido.getId() == null) {
            this.pedido.setDataHora(new Date());
            this.pedido.setUsuario(SessaoUtil.getUsuario());
            this.pedido = new PedidoNegocio().salvar(this.pedido);
        }
        this.tableViewItens.getItems().forEach((ItemPedido i) -> {
            i.setPedido(this.pedido);
        });
        new ItemPedidoNegocio().salvar(this.tableViewItens.getItems());
    }

    @FXML
    public void acaoFinalizarVenda(ActionEvent event) {
        try {
            if (this.tableViewItens.getItems().size() > 0 && enviarMensagemConfirmacao("Deseja salvar o pedido?")) {
                this.salvarPedido();
            }
            if (this.pedido.getId() != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaFinalizarVenda.fxml"));
                AnchorPane telaFinalizarVenda = loader.load();
                TelaFinalizarVendaController telaFinalizarVendaController = loader.getController();
                telaFinalizarVendaController.inicializaDados(this.pedido.getId());
                TelaPrincipalController.getInstance().mudaTela(telaFinalizarVenda);
            } else {
                enviarMensagemInformacao("Não existe nenhum pedido finalizado para a mesa!");
            }
        } catch (IOException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    public void inicializaDados(Integer mesa) throws Exception {
        this.pedido = new Pedido();
        this.pedido.setMesa(mesa);
        this.titulo.setText("Mesa " + this.pedido.getMesa());
        Pedido pedidoEmAberto = new PedidoNegocio().obterAbertoPorMesa(this.pedido.getMesa());
        if (pedidoEmAberto != null) {
            this.pedido = pedidoEmAberto;
        }
        this.ajustaTabelaItens();
        this.criaGridCategorias();
        if (this.categorias != null && !this.categorias.isEmpty()) {
            this.criaGridProdutos(this.categorias.get(0).getId());
        }
        this.atualizaTotalizadores();
    }

    public Pedido getPedido() {
        return pedido;
    }
}
