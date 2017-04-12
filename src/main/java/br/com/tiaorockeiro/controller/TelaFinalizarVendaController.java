/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Wendel
 */
public class TelaFinalizarVendaController implements Initializable {

    private TelaPedidoController telaPedidoController;
    @FXML
    private Button btnAcaoVoltar;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.btnAcaoVoltar.setOnAction((ActionEvent event) -> {
            acaoVoltar(event);
        });
    }

    @FXML
    public void acaoVoltar(ActionEvent event) {
        TelaPrincipalController.getInstance().mudaTela(this.telaPedidoController.getAnchorPaneTelaPedido());
    }

    public void setTelaPedidoController(TelaPedidoController telaPedidoController) {
        this.telaPedidoController = telaPedidoController;
    }
}
