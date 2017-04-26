/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.AberturaCaixa;
import br.com.tiaorockeiro.modelo.ConfiguracaoUsuario;
import br.com.tiaorockeiro.negocio.AberturaCaixaNegocio;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
import br.com.tiaorockeiro.util.SessaoUtil;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
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
public class TelaMesasController implements Initializable {

    @FXML
    private AnchorPane anchorPaneMesas;
    @FXML
    private GridPane gridMesas;
    @FXML
    private AnchorPane paneInformacaoCaixa;
    @FXML
    private Button btnSelecionarCaixa;
    private AberturaCaixa aberturaCaixa;

    private static final int QTDE_COLUNAS = 5;

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
            this.gridMesas.setVgap(5);
            this.gridMesas.setHgap(5);

            int coluna = 0;
            int linha = 0;
            for (int i = 1; i <= SessaoUtil.getConfiguracao().getQuantidadeMesas(); i++) {
                AnchorPane anchorPaneMesa = new AnchorPane();
                setAnchor(anchorPaneMesa);
                Button botaoMesa = criaBotao(i);
                setAnchor(botaoMesa);
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
            AnchorPane.setBottomAnchor(this.gridMesas, 0.0);
            this.anchorPaneMesas.getChildren().add(this.gridMesas);
        }
    }

    private void setAnchor(AnchorPane pane) {
        AnchorPane.setLeftAnchor(pane, 0.0);
        AnchorPane.setTopAnchor(pane, 0.0);
        AnchorPane.setRightAnchor(pane, 0.0);
        AnchorPane.setBottomAnchor(pane, 0.0);
    }

    private void setAnchor(Button button) {
        AnchorPane.setLeftAnchor(button, 0.0);
        AnchorPane.setTopAnchor(button, 0.0);
        AnchorPane.setRightAnchor(button, 0.0);
        AnchorPane.setBottomAnchor(button, 0.0);
    }

    private Button criaBotao(int mesa) {
        Image image = new Image("/imagens/icon-table.png");
        Button button = new Button("Mesa " + mesa, new ImageView(image));
        button.setContentDisplay(ContentDisplay.TOP);
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        button.setPrefSize(300, 300);
        button.setStyle("-fx-background-radius: 0");
        button.setId("mesa-" + String.valueOf(mesa));
        button.setOnAction((ActionEvent event) -> {
            this.abreMesa(event);
        });
        return button;
    }

    private void abreMesa(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaPedido.fxml"));
            Integer mesa = Integer.valueOf(((Control) event.getSource()).getId().replace("mesa-", ""));
            AnchorPane telaPedido = loader.load();
            TelaPedidoController telaPedidoController = loader.getController();
            telaPedidoController.setMesa(mesa);
            TelaPrincipalController.getInstance().mudaTela(telaPedido);
        } catch (IOException | NumberFormatException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    private void carregaCaixaSelecionado() throws Exception {
        ConfiguracaoUsuario configuracaoUsuario = SessaoUtil.getUsuario().getConfiguracao();
        GridPane gridPane = new GridPane();
        if (configuracaoUsuario != null && configuracaoUsuario.getCaixaSelecionado() != null) {
            this.aberturaCaixa = new AberturaCaixaNegocio().obterAbertoPorCaixa(configuracaoUsuario.getCaixaSelecionado());
            if (this.aberturaCaixa != null) {
                Font font = new Font("Arial", 14);
                Label labelCaixa = new Label("Caixa:");
                labelCaixa.setFont(font);
                gridPane.add(labelCaixa, 0, 0);
                Label labelDescricaoCaixa = new Label(this.aberturaCaixa.getCaixa().getDescricao());
                labelDescricaoCaixa.setFont(font);
                gridPane.add(labelDescricaoCaixa, 1, 0);
                Label labelData = new Label("Data:");
                labelData.setFont(font);
                gridPane.add(labelData, 0, 1);
                Label labelDataDescricao = new Label(new SimpleDateFormat("dd/MM/yyyy").format(this.aberturaCaixa.getDataHora()));
                labelDataDescricao.setFont(font);
                gridPane.add(labelDataDescricao, 1, 1);
                Label labelHora = new Label("Hora:");
                labelHora.setFont(font);
                gridPane.add(labelHora, 0, 2);
                Label labelHoraDescricao = new Label(new SimpleDateFormat("HH:mm:ss").format(this.aberturaCaixa.getDataHora()));
                labelHoraDescricao.setFont(font);
                gridPane.add(labelHoraDescricao, 1, 2);
                Label labelUsuario = new Label("Usuário:");
                labelUsuario.setFont(font);
                gridPane.add(labelUsuario, 0, 3);
                Label labelUsuarioDescricao = new Label(this.aberturaCaixa.getUsuario().getDescricao());
                labelUsuarioDescricao.setFont(font);
                gridPane.add(labelUsuarioDescricao, 1, 3);
                this.btnSelecionarCaixa.setText("Mudar Caixa");
                this.paneInformacaoCaixa.getChildren().add(gridPane);
                return;
            }
        }
        gridPane.add(new Label("Nenhum caixa selecionado!"), 0, 0);
        this.paneInformacaoCaixa.getChildren().add(gridPane);
    }

    @FXML
    public void acaoSelecionarCaixa(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaSelecionarCaixa.fxml"));
            AnchorPane telaPedido = loader.load();
            TelaPrincipalController.getInstance().mudaTela(telaPedido);
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }
}
