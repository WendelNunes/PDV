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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import static javafx.collections.FXCollections.observableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

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
    private ListView<AdicionalProduto> listViewItens;
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
        this.listViewItens.setCellFactory(param -> new ListCell<AdicionalProduto>() {
            @Override
            protected void updateItem(AdicionalProduto item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    try {
                        ListCellProduto cellProduto = new ListCellProduto(item.getProduto().getDescricao(), item.getValor(), item.getQuantidade(), null, item.getValorTotal());
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

    private void acaoAdicionaProduto(ActionEvent event) {
        try {
            Produto produto = new ProdutoNegocio().obterPorId(Produto.class, Long.valueOf(((Control) event.getSource()).getId()));
            AdicionalProduto item = new AdicionalProduto();
            item.setItemPedido(this.itemPedido);
            item.setProduto(produto);
            item.setQuantidade(BigDecimal.ONE);
            item.setValor(produto.getValor());
            this.listViewItens.getItems().add(item);
            this.telaPedido.updateItemPedidoSelecionado(this.itemPedido);
            this.atualizaTotalizadores();
        } catch (NumberFormatException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoMaisProduto(ActionEvent event) {
        try {
            int index = this.listViewItens.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                AdicionalProduto item = this.listViewItens.getSelectionModel().getSelectedItem();
                item.setQuantidade(item.getQuantidade().add(BigDecimal.ONE));
                this.listViewItens.getItems().set(index, item);
                this.telaPedido.updateItemPedidoSelecionado(this.itemPedido);
                this.listViewItens.getSelectionModel().select(index);
            }
            this.atualizaTotalizadores();
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoMenosProduto(ActionEvent event) {
        try {
            int index = this.listViewItens.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                AdicionalProduto item = this.listViewItens.getSelectionModel().getSelectedItem();
                if (item.getQuantidade().compareTo(BigDecimal.ONE) > 0) {
                    item.setQuantidade(item.getQuantidade().subtract(BigDecimal.ONE));
                    this.listViewItens.getItems().set(index, item);
                    this.telaPedido.updateItemPedidoSelecionado(this.itemPedido);
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
                if (coluna == 3) {
                    coluna = 0;
                    ++linha;
                }
            }
        }
        this.scrollProdutos.setContent(gridProdutos);
    }

    private Button criaBotaoProdutos(Produto produto) throws IOException {
        Button button = new Button(produto.getDescricao());
        button.setTooltip(new Tooltip(produto.getDescricao()));
        if (produto.getImagem() != null) {
            ByteArrayInputStream in = new ByteArrayInputStream(produto.getImagem());
            BufferedImage read = ImageIO.read(in);
            ImageView imagem = new ImageView();
            imagem.setFitWidth(50);
            imagem.setFitHeight(50);
            imagem.setImage(SwingFXUtils.toFXImage(read, null));
            button.setGraphic(imagem);
        }
        button.setContentDisplay(ContentDisplay.TOP);
        button.getStyleClass().add("botao-produto");
        button.setId(produto.getId().toString());
        button.setOnAction((ActionEvent event) -> {
            this.acaoAdicionaProduto(event);
        });
        return button;
    }

    private void atualizaTotalizadores() {
        this.textFieldQuantidadeItens.setText(formataQuantidade(this.listViewItens.getItems().stream().map(i -> i.getQuantidade()).reduce(BigDecimal.ZERO, BigDecimal::add)));
        this.textFieldValorTotal.setText(formataMoeda(this.listViewItens.getItems().stream().map(i -> i.getValorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add)));
    }

    public void abrirTela(AnchorPane tela, TelaPedidoController telaPedido, ItemPedido itemPedido) throws Exception {
        this.itemPedido = itemPedido;
        this.telaPedido = telaPedido;
        this.criaGridProdutos();
        this.ajustaTabelaItens();
        this.listViewItens.setItems(observableList(this.itemPedido.getAdicionais()));
        this.atualizaTotalizadores();
        this.stage = MainApp.getInstance().popup(tela, true);
    }
}
