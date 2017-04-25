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
    private Label labelCaixaSelecionado;
    @FXML
    private Label labelDataCaixaSelecionado;
    @FXML
    private Label labelHoraCaixaSelecionado;
    @FXML
    private Label labelUsuarioCaixaSelecionado;
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
        if (SessaoUtil.getConfiguracao() != null && SessaoUtil.getConfiguracao().getQuantidadeMesas() > 0) {
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
            System.err.println(e);
        }
    }

    private void carregaCaixaSelecionado() throws Exception {
        ConfiguracaoUsuario configuracaoUsuario = SessaoUtil.getUsuario().getConfiguracao();
        if (configuracaoUsuario != null && configuracaoUsuario.getCaixaSelecionado() != null) {
            this.aberturaCaixa = new AberturaCaixaNegocio().obterAbertoPorCaixa(configuracaoUsuario.getCaixaSelecionado());
            if (this.aberturaCaixa != null) {
                this.labelCaixaSelecionado.setText("Caixa: " + this.aberturaCaixa.getCaixa().getDescricao());
                this.labelDataCaixaSelecionado.setText("Data: " + new SimpleDateFormat("dd/MM/yyyy").format(this.aberturaCaixa.getDataHora()));
                this.labelHoraCaixaSelecionado.setText("Hora: " + new SimpleDateFormat("HH:mm:ss").format(this.aberturaCaixa.getDataHora()));
                this.labelUsuarioCaixaSelecionado.setText("Usuário: " + this.aberturaCaixa.getUsuario().getDescricao());
                this.btnSelecionarCaixa.setText("Mudar Caixa");
                return;
            }
        }
        this.labelCaixaSelecionado.setText("Nenhum caixa selecionado!");
    }
}
