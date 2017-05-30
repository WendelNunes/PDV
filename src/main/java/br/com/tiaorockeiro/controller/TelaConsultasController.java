/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.util.MensagemUtil;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Wendel
 */
public class TelaConsultasController implements Initializable {

    @FXML
    private Button botaoVendas;

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
    public void acaoBotaoVendas(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaConsultaVenda.fxml"));
            loader.setController(new TelaConsultaVendaController());
            AnchorPane tela = loader.load();
            TelaPrincipalController.getInstance().mudaTela(tela, "Consulta de Vendas");
        } catch (IOException e) {
            enviarMensagemErro(e.getMessage());
        }
    }
}
