/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.AdicionalProduto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private TableView<AdicionalProduto> tableViewItens;
    @FXML
    private TextField textFieldValorTotal;

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

}
