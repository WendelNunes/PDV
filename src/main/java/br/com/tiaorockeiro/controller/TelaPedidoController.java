/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.AberturaCaixa;
import br.com.tiaorockeiro.modelo.AdicionalProduto;
import br.com.tiaorockeiro.modelo.CategoriaProduto;
import br.com.tiaorockeiro.modelo.ItemPedido;
import br.com.tiaorockeiro.modelo.ObservacaoProduto;
import br.com.tiaorockeiro.modelo.Pedido;
import br.com.tiaorockeiro.modelo.Produto;
import br.com.tiaorockeiro.negocio.AberturaCaixaNegocio;
import br.com.tiaorockeiro.negocio.AdicionalProdutoNegocio;
import br.com.tiaorockeiro.negocio.CategoriaProdutoNegocio;
import br.com.tiaorockeiro.negocio.ItemPedidoNegocio;
import br.com.tiaorockeiro.negocio.ObservacaoProdutoNegocio;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Wendel
 */
public class TelaPedidoController implements Initializable {

    @FXML
    private ScrollPane scrollCategorias;
    @FXML
    private ScrollPane scrollProdutos;
    @FXML
    private GridPane gridProdutos;
    @FXML
    private ListView<ItemPedido> listViewItens;
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
            TelaPrincipalController.getInstance().mudaTela(telaMesas, "Mesas");
        } catch (IOException | NumberFormatException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    private void criaGridCategorias() {
        this.categorias = new CategoriaProdutoNegocio().listarTodos(CategoriaProduto.class);
        HBox listaCategorias = new HBox();
        listaCategorias.setSpacing(2);
        if (this.categorias != null && !this.categorias.isEmpty()) {
            this.categorias.stream().forEach((categoria) -> {
                listaCategorias.getChildren().add(this.criaBotaoCategorias(categoria));
            });
        }
        this.scrollCategorias.setContent(listaCategorias);
    }

    private Button criaBotaoCategorias(CategoriaProduto categoriaProduto) {
        Button button = new Button(categoriaProduto.getDescricao());
        button.getStyleClass().add("botao-menu-solto");
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
        this.gridProdutos.setVgap(3);
        this.gridProdutos.setHgap(3);
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
        button.getStyleClass().add("botao-produto");
        button.setId(produto.getId().toString());
        button.setOnAction((ActionEvent event) -> {
            this.acaoAdicionaProduto(event);
        });
        return button;
    }

    private void ajustaTabelaItens() {
        this.listViewItens.setCellFactory(param -> new ListCell<ItemPedido>() {
            @Override
            protected void updateItem(ItemPedido item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    try {
                        ListCellProduto cellProduto = new ListCellProduto(item.getProduto().getDescricao(), item.getValor(), item.getQuantidade(), item.getValorTotalAdicionais(), item.getValorTotal());
                        setGraphic(cellProduto.getCell());
                    } catch (Exception e) {
                        enviarMensagemErro(e.getMessage());
                        setText(null);
                        setGraphic(null);
                    }
                } else {
                    setText(null);
                    setGraphic(null);
                }
            }
        });
    }

    private void atualizaTotalizadores() {
        this.textFieldQuantidadeItens.setText(formataQuantidade(this.listViewItens.getItems().stream().map(i -> i.getQuantidade()).reduce(BigDecimal.ZERO, BigDecimal::add)));
        this.textFieldValorTotal.setText(formataMoeda(this.listViewItens.getItems().stream().map(i -> i.getValorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add)));
    }

    @FXML
    public void acaoMaisProduto() {
        try {
            int index = this.listViewItens.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                ItemPedido item = this.listViewItens.getSelectionModel().getSelectedItem();
                item.setQuantidade(item.getQuantidade().add(BigDecimal.ONE));
                this.listViewItens.getItems().set(index, item);
                this.listViewItens.getSelectionModel().select(index);
            }
            this.atualizaTotalizadores();
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoMenosProduto() {
        try {
            int index = this.listViewItens.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                ItemPedido item = this.listViewItens.getSelectionModel().getSelectedItem();
                if (item.getQuantidade().compareTo(BigDecimal.ONE) > 0) {
                    item.setQuantidade(item.getQuantidade().subtract(BigDecimal.ONE));
                    this.listViewItens.getItems().set(index, item);
                    this.listViewItens.getSelectionModel().select(index);
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
            int index = this.listViewItens.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                this.listViewItens.getItems().remove(index);
            }
            this.atualizaTotalizadores();
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
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
            item.setObservacoes(new ArrayList<>());
            item.setAdicionais(new ArrayList<>());
            this.listViewItens.getItems().add(item);
            this.atualizaTotalizadores();
        } catch (NumberFormatException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoFinalizarPedido(ActionEvent event) {
        try {
            if (!this.listViewItens.getItems().isEmpty()) {
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

    @SuppressWarnings("UnusedAssignment")
    private void salvarPedido() {
        if (this.pedido.getId() == null) {
            this.pedido.setDataHora(new Date());
            this.pedido.setUsuario(SessaoUtil.getUsuario());
            this.pedido = new PedidoNegocio().salvar(this.pedido);
        }
        ItemPedidoNegocio itemPedidoNegocio = new ItemPedidoNegocio();
        ObservacaoProdutoNegocio observacaoProdutoNegocio = new ObservacaoProdutoNegocio();
        AdicionalProdutoNegocio adicionalProdutoNegocio = new AdicionalProdutoNegocio();
        this.listViewItens.getItems().stream().map((item) -> {
            item.setPedido(this.pedido);
            return item;
        }).forEachOrdered((item) -> {
            List<ObservacaoProduto> observacoes = item.getObservacoes();
            List<AdicionalProduto> adicionais = item.getAdicionais();
            item = itemPedidoNegocio.salvar(item);
            for (ObservacaoProduto observacao : observacoes) {
                observacao.setItemPedido(item);
                observacaoProdutoNegocio.salvar(observacao);
            }
            for (AdicionalProduto adicional : adicionais) {
                adicional.setItemPedido(item);
                adicionalProdutoNegocio.salvar(adicional);
            }
        });
    }

    @FXML
    public void acaoFinalizarVenda(ActionEvent event) {
        try {
            if (this.listViewItens.getItems().size() > 0 && enviarMensagemConfirmacao("Deseja salvar o pedido?")) {
                this.salvarPedido();
            }
            if (this.pedido.getId() != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaFinalizarVenda.fxml"));
                AnchorPane telaFinalizarVenda = loader.load();
                TelaFinalizarVendaController telaFinalizarVendaController = loader.getController();
                telaFinalizarVendaController.inicializaDados(this.pedido.getId());
                TelaPrincipalController.getInstance().mudaTela(telaFinalizarVenda, "Fechamento de Venda - Mesa " + this.pedido.getMesa());
            } else {
                enviarMensagemInformacao("Não existe nenhum pedido finalizado para a mesa!");
            }
        } catch (IOException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoCancelarVenda(ActionEvent event) {
        try {
            if (this.pedido.getId() == null) {
                enviarMensagemInformacao("Não existe nenhum pedido finalizado para a mesa!");
            } else if (enviarMensagemConfirmacao("Deseja realmente cancelar a venda?")) {
                this.pedido.setDataHoraCancelamento(new Date());
                this.pedido.setUsuarioCancelamento(SessaoUtil.getUsuario());
                new PedidoNegocio().salvar(this.pedido);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaMesas.fxml"));
                AnchorPane telaMesas = loader.load();
                TelaPrincipalController.getInstance().mudaTela(telaMesas, "Mesas");
                enviarMensagemInformacao("Venda cancelada com sucesso!");
            }
        } catch (IOException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoAbrirAdicionais(ActionEvent event) {
        try {
            int index = this.listViewItens.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaAdicionaisProduto.fxml"));
                AnchorPane tela = loader.load();
                TelaAdicionaisProdutoController controller = loader.getController();
                controller.abrirTela(tela, this, this.listViewItens.getSelectionModel().getSelectedItem());
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    public void updateItemPedidoSelecionado(ItemPedido itemPedido) {
        int index = this.listViewItens.getSelectionModel().getSelectedIndex();
        if (index != -1) {
            this.listViewItens.getItems().set(index, itemPedido);
            this.listViewItens.getSelectionModel().select(index);
        }
    }

    @FXML
    public void acaoAbrirObservacao(ActionEvent event) {
        try {
            int index = this.listViewItens.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaObservacoesProduto.fxml"));
                AnchorPane tela = loader.load();
                TelaObservacoesProdutoController controller = loader.getController();
                controller.abrirTela(tela, this.listViewItens.getSelectionModel().getSelectedItem());
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    public void inicializaDados(Integer mesa) throws Exception {
        this.pedido = new Pedido();
        this.pedido.setMesa(mesa);
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
