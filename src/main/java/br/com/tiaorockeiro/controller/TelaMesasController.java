/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.AberturaCaixa;
import br.com.tiaorockeiro.modelo.ConfiguracaoUsuario;
import br.com.tiaorockeiro.modelo.Pedido;
import br.com.tiaorockeiro.negocio.AberturaCaixaNegocio;
import br.com.tiaorockeiro.negocio.PedidoNegocio;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
import br.com.tiaorockeiro.util.SessaoUtil;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Wendel
 */
public class TelaMesasController implements Initializable {

    @FXML
    private AnchorPane anchorPaneMesas;
    @FXML
    private GridPane gridMesas;
    private AberturaCaixa aberturaCaixa;
    List<Pedido> pedidosAbertos = new ArrayList<>();

    private static final int QTDE_COLUNAS = 10;

    public TelaMesasController() {
        this.pedidosAbertos = new PedidoNegocio().obterAbertos();
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.carregaCaixaSelecionado();
            this.criaGridMesas();
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    private void criaGridMesas() {
        if (SessaoUtil.getConfiguracao() != null && SessaoUtil.getConfiguracao().getQuantidadeMesas() > 0
                && this.aberturaCaixa != null) {
            this.gridMesas = new GridPane();
            this.gridMesas.setHgap(3);
            this.gridMesas.setVgap(3);
            int coluna = 0;
            int linha = 0;
            for (int i = 1; i <= SessaoUtil.getConfiguracao().getQuantidadeMesas(); i++) {
                AnchorPane anchorPaneMesa = new AnchorPane();
                AnchorPane.setLeftAnchor(anchorPaneMesa, 0.0);
                AnchorPane.setTopAnchor(anchorPaneMesa, 0.0);
                AnchorPane.setRightAnchor(anchorPaneMesa, 0.0);
                Button botaoMesa = criaBotao(i);
                anchorPaneMesa.getChildren().add(botaoMesa);
                this.gridMesas.add(anchorPaneMesa, coluna, linha);
                ++coluna;
                if (coluna == QTDE_COLUNAS) {
                    coluna = 0;
                    ++linha;
                }
            }
            AnchorPane.setLeftAnchor(this.gridMesas, 0.0);
            AnchorPane.setTopAnchor(this.gridMesas, 0.0);
            AnchorPane.setRightAnchor(this.gridMesas, 0.0);
            this.anchorPaneMesas.getChildren().add(this.gridMesas);
        }
    }

    private Button criaBotao(int mesa) {
        Image image = new Image("/imagens/icon-table.png");
        Button button = new Button("Mesa " + mesa, new ImageView(image));
        button.setContentDisplay(ContentDisplay.TOP);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button.setPrefSize(300, 125);
        button.getStyleClass().add("botao");
        button.getStyleClass().add(this.pedidosAbertos.stream().anyMatch(i -> i.getMesa() == mesa) ? "botao-mesa-aberta" : "botao-mesa-fechada");
        button.setId("mesa-" + String.valueOf(mesa));
        button.setOnAction((ActionEvent event) -> {
            this.abreMesa(event);
        });
        AnchorPane.setLeftAnchor(button, 0.0);
        AnchorPane.setRightAnchor(button, 0.0);
        AnchorPane.setTopAnchor(button, 0.0);
        return button;
    }

    private void abreMesa(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaPedido.fxml"));
            Integer mesa = Integer.valueOf(((Control) event.getSource()).getId().replace("mesa-", ""));
            AnchorPane telaPedido = loader.load();
            TelaPedidoController telaPedidoController = loader.getController();
            telaPedidoController.inicializaDados(mesa);
            TelaPrincipalController.getInstance().mudaTela(telaPedido, "Pedido - Mesa " + mesa);
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    private void carregaCaixaSelecionado() throws Exception {
        ConfiguracaoUsuario configuracaoUsuario = SessaoUtil.getUsuario().getConfiguracao();
        if (configuracaoUsuario != null && configuracaoUsuario.getCaixaSelecionado() != null) {
            this.aberturaCaixa = new AberturaCaixaNegocio().obterAbertoPorCaixa(configuracaoUsuario.getCaixaSelecionado());

        }
    }
}
