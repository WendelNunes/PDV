/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.Caixa;
import br.com.tiaorockeiro.modelo.Usuario;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Wendel
 */
public class TelaConsultaVendaController implements Initializable {

    @FXML
    private DatePicker dataInicial;
    @FXML
    private DatePicker dataFinal;
    @FXML
    private ComboBox<Usuario> usuarios;
    @FXML
    private ComboBox<Caixa> caixas;
    @FXML
    private ComboBox<Integer> mesas;
    @FXML
    private CheckBox ativa;
    @FXML
    private CheckBox cancelada;

    @FXML
    private TableView<Object[]> listaVendas;
    @FXML
    private TableColumn<Object[], Integer> colunaCodigo;
    @FXML
    private TableColumn<Object[], Integer> colunaMesa;
    @FXML
    private TableColumn<Object[], String> colunaData;
    @FXML
    private TableColumn<Object[], String> colunaHora;
    @FXML
    private TableColumn<Object[], String> colunaUsuario;
    @FXML
    private TableColumn<Object[], String> colunaCaixa;
    @FXML
    private TableColumn<Object[], String> colunaStatus;

    @FXML
    private ComboBox<Integer> paginas;

    // FILTROS
    private Date filtroDataInicial, filtroDataFinal;
    private Usuario filtroUsuario;
    private Caixa filtroCaixa;
    private Integer filtroMesa;
    private boolean filtroAtiva;
    private boolean filtroCancelada;

    // PAGINACAO
    private static final Integer QTDE_REGISTROS = 10;

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

    @FXML
    public void acaoPesquisar(ActionEvent event) {

    }

    @FXML
    public void acaoVoltar(ActionEvent event) {

    }

    @FXML
    public void acaoVisualizar(ActionEvent event) {

    }

    @FXML
    public void acaoVoltarPagina(ActionEvent event) {

    }

    @FXML
    public void acaoAvancarPagina(ActionEvent event) {

    }
}
