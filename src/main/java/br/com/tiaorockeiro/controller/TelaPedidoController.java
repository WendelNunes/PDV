/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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
    private Integer mesa;

    private static final int QTDE_CATEGORIAS = 10;
    private static final int QTDE_PRODUTOS = 20;
    private static final int QTDE_COLUNAS_PRODUTOS = 5;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.criaGridCategorias();
        this.scrollCategorias.setContent(this.gridCategorias);
        this.criaGridProdutos(1);
        this.scrollProdutos.setContent(this.gridProdutos);
    }

    @FXML
    public void acaoVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaMesas.fxml"));
            AnchorPane telaMesas = loader.load();
            TelaPrincipalController.getInstance().mudaTela(telaMesas);
        } catch (IOException | NumberFormatException e) {
            System.err.println(e);
        }
    }

    private void criaGridCategorias() {
        this.gridCategorias = new GridPane();
        this.gridCategorias.setVgap(10);
        this.gridCategorias.setHgap(10);
        for (int i = 1; i <= QTDE_CATEGORIAS; i++) {
            this.gridCategorias.add(this.criaBotaoCategorias(i), i, 0);
        }
    }

    private Button criaBotaoCategorias(int mesa) {
        Image image = new Image("/imagens/icon-table.png");
        Button button = new Button("Categoria " + mesa, new ImageView(image));
        button.setContentDisplay(ContentDisplay.TOP);
        button.setPrefSize(123, 107);
        button.setStyle("-fx-background-radius: 0");
        button.setId("categoria-" + String.valueOf(mesa));
        button.setOnAction((ActionEvent event) -> {
            this.selecionaCategoria(event);
        });
        return button;
    }

    private void selecionaCategoria(ActionEvent event) {
        Integer categoria = Integer.valueOf(((Control) event.getSource()).getId().replace("categoria-", ""));
        this.criaGridProdutos(categoria);
        this.scrollProdutos.setContent(this.gridProdutos);
    }

    private void criaGridProdutos(Integer categoria) {
        this.gridProdutos = new GridPane();
        this.gridProdutos.setVgap(10);
        this.gridProdutos.setHgap(10);

        int coluna = 0;
        int linha = 0;
        for (int i = 1; i <= QTDE_PRODUTOS; i++) {
            this.gridProdutos.add(criaBotaoProdutos(categoria, i), coluna, linha);
            ++coluna;
            if (coluna == QTDE_COLUNAS_PRODUTOS) {
                coluna = 0;
                ++linha;
            }
        }
    }

    private Button criaBotaoProdutos(Integer categoria, Integer produto) {
        Image image = new Image("/imagens/icon-table.png");
        Button button = new Button("C: " + categoria + " P: " + produto, new ImageView(image));
        button.setContentDisplay(ContentDisplay.TOP);
        button.setPrefSize(107, 107);
        button.setStyle("-fx-background-radius: 0");
        button.setId("C-" + categoria + "-P-" + produto);
        button.setOnAction((ActionEvent event) -> {
            this.acaoAdicionaProduto(event);
        });
        return button;
    }

    private void acaoAdicionaProduto(ActionEvent event) {

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
        this.mesa = mesa;
        this.titulo.setText("Mesa " + this.mesa);
    }

    public AnchorPane getAnchorPaneTelaPedido() {
        return anchorPaneTelaPedido;
    }
}
