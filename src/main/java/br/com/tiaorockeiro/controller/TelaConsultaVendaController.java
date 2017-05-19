/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.Caixa;
import br.com.tiaorockeiro.modelo.Usuario;
import br.com.tiaorockeiro.negocio.CaixaNegocio;
import br.com.tiaorockeiro.negocio.UsuarioNegocio;
import br.com.tiaorockeiro.negocio.VendaNegocio;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
import br.com.tiaorockeiro.util.SessaoUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import static javafx.collections.FXCollections.observableList;
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
        try {
            this.dataInicial.setValue(LocalDate.now());
            this.dataFinal.setValue(LocalDate.now());
            this.usuarios.getItems().add(null);
            this.usuarios.getItems().addAll(observableList(new UsuarioNegocio().listarTodos(Usuario.class)));
            this.caixas.getItems().add(null);
            this.caixas.getItems().addAll(observableList(new CaixaNegocio().listarTodos(Caixa.class)));
            this.mesas.getItems().add(null);
            if (SessaoUtil.getConfiguracao() != null && SessaoUtil.getConfiguracao().getQuantidadeMesas() != null) {
                for (int i = 1; i <= SessaoUtil.getConfiguracao().getQuantidadeMesas(); i++) {
                    this.mesas.getItems().add(i);
                }
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoPesquisar(ActionEvent event) {
        try {
            this.filtroDataInicial = new Date(this.dataInicial.getValue().toEpochDay());
            this.filtroDataFinal = new Date(this.dataFinal.getValue().toEpochDay());
            this.filtroUsuario = this.usuarios.getValue();
            this.filtroCaixa = this.caixas.getValue();
            this.filtroMesa = this.mesas.getValue();
            this.filtroAtiva = this.ativa.isSelected();
            this.filtroCancelada = this.cancelada.isSelected();
            Integer qtdeRegistrosConsulta = new VendaNegocio().quantidadeRegistroConsultaVenda(this.filtroDataInicial, this.filtroDataFinal, this.filtroUsuario.getId(),
                    this.filtroCaixa.getId(), this.filtroMesa, this.filtroAtiva, this.filtroCancelada);
            this.paginas.getItems().clear();
            if (qtdeRegistrosConsulta > 0) {
                BigDecimal qtdePaginas = new BigDecimal(qtdeRegistrosConsulta).divide(new BigDecimal(qtdeRegistrosConsulta), RoundingMode.UP);
                for (int i = 1; i <= qtdePaginas.intValue(); i++) {
                    this.paginas.getItems().add(i);
                }
                this.paginas.getSelectionModel().select(0);
                this.preencheListaVendas();
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    private void preencheListaVendas() {
        this.listaVendas.setItems(observableList(new VendaNegocio().listaConsultaVenda(this.filtroDataInicial, this.filtroDataFinal,
                this.filtroUsuario.getId(), this.filtroCaixa.getId(), this.filtroMesa, this.filtroAtiva, this.filtroCancelada, QTDE_REGISTROS,
                this.paginas.getValue())));
    }

    @FXML
    public void acaoVoltar(ActionEvent event) {
    }

    @FXML
    public void acaoVisualizar(ActionEvent event) {

    }

    @FXML
    public void acaoVoltarPagina(ActionEvent event) {
        try {
            if (this.paginas.getSelectionModel().getSelectedItem() > 0) {
                this.paginas.getSelectionModel().selectNext();
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoAvancarPagina(ActionEvent event) {
        try {
            if (!Objects.equals(this.paginas.getSelectionModel().getSelectedItem(), this.paginas.getItems().stream().max((m1, m2) -> m1.compareTo(m2)).get())) {
                this.paginas.getSelectionModel().selectFirst();
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }
}
