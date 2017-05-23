/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.AberturaCaixa;
import br.com.tiaorockeiro.modelo.Caixa;
import br.com.tiaorockeiro.modelo.Usuario;
import br.com.tiaorockeiro.negocio.CaixaNegocio;
import br.com.tiaorockeiro.negocio.UsuarioNegocio;
import br.com.tiaorockeiro.negocio.VendaNegocio;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
import br.com.tiaorockeiro.util.SessaoUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import static javafx.collections.FXCollections.observableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.util.StringConverter;

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
    private ComboBox<Map<String, String>> status;

    @FXML
    private TableView<Object[]> listaVendas;
    @FXML
    private TableColumn<Object[], Long> colunaCodigo;
    @FXML
    private TableColumn<Object[], Integer> colunaMesa;
    @FXML
    private TableColumn<Object[], Date> colunaData;
    @FXML
    private TableColumn<Object[], Date> colunaHora;
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
    private String filtroStatus;

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
            this.status.getItems().add(null);
            Map<String, String> ativa = new HashMap<>();
            ativa.put("STATUS", "1");
            ativa.put("LABEL", "Ativa");
            this.status.getItems().add(ativa);
            Map<String, String> cancelada = new HashMap<>();
            cancelada.put("STATUS", "0");
            cancelada.put("LABEL", "Cancelada");
            this.status.getItems().add(cancelada);
            this.status.setCellFactory((ListView<Map<String, String>> param) -> new ListCell<Map<String, String>>() {
                @Override
                protected void updateItem(Map<String, String> item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.get("LABEL"));
                    } else {
                        setText(null);
                    }
                }
            });
            this.status.setConverter(new StringConverter<Map<String, String>>() {
                @Override
                public String toString(Map<String, String> item) {
                    return item != null ? item.get("LABEL") : null;
                }

                @Override
                public Map<String, String> fromString(String string) {
                    return null;
                }
            });
            this.paginas.valueProperty()
                    .addListener((observable) -> {
                        this.preencheListaVendas();
                    }
                    );

            this.ajustaTabela();
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    private void ajustaTabela() {
        this.listaVendas.setFixedCellSize(45);
        this.colunaCodigo.setCellValueFactory(i -> new SimpleObjectProperty<>(Long.valueOf(i.getValue()[0].toString())));
        this.colunaCodigo.setCellFactory((TableColumn<Object[], Long> param) -> new TableCell<Object[], Long>() {
            @Override
            protected void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.toString());
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_RIGHT);
                } else {
                    setText("");
                }
            }
        });
        this.colunaMesa.setCellValueFactory(i -> new SimpleObjectProperty<>(Integer.valueOf(i.getValue()[1].toString())));
        this.colunaMesa.setCellFactory((TableColumn<Object[], Integer> param) -> new TableCell<Object[], Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.toString());
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_RIGHT);
                } else {
                    setText("");
                }
            }
        });
        this.colunaData.setCellValueFactory(i -> new SimpleObjectProperty<>((Date) i.getValue()[2]));
        this.colunaData.setCellFactory((TableColumn<Object[], Date> param) -> new TableCell<Object[], Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(new SimpleDateFormat("dd/MM/yyyy").format(item));
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_LEFT);
                } else {
                    setText("");
                }
            }
        });
        this.colunaHora.setCellValueFactory(i -> new SimpleObjectProperty<>((Date) i.getValue()[2]));
        this.colunaHora.setCellFactory((TableColumn<Object[], Date> param) -> new TableCell<Object[], Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(new SimpleDateFormat("HH:mm:ss").format(item));
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_LEFT);
                } else {
                    setText("");
                }
            }
        });
        this.colunaUsuario.setCellValueFactory(i -> new SimpleObjectProperty<>(i.getValue()[3].toString()));
        this.colunaUsuario.setCellFactory((TableColumn<Object[], String> param) -> new TableCell<Object[], String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item);
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_LEFT);
                } else {
                    setText("");
                }
            }
        });
        this.colunaCaixa.setCellValueFactory(i -> new SimpleObjectProperty<>(i.getValue()[4].toString()));
        this.colunaCaixa.setCellFactory((TableColumn<Object[], String> param) -> new TableCell<Object[], String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item);
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_LEFT);
                } else {
                    setText("");
                }
            }
        });
        this.colunaStatus.setCellValueFactory(i -> new SimpleObjectProperty<>(i.getValue()[5] != null ? "0" : "1"));
        this.colunaStatus.setCellFactory((TableColumn<Object[], String> param) -> new TableCell<Object[], String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.equals("1") ? "Ativa" : "Cancelada");
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_LEFT);
                } else {
                    setText("");
                }
            }
        });
    }

    @FXML
    public void acaoPesquisar(ActionEvent event) {
        try {
            this.filtroDataInicial = Date.from(this.dataInicial.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            this.filtroDataFinal = Date.from(this.dataFinal.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            this.filtroUsuario = this.usuarios.getValue();
            this.filtroCaixa = this.caixas.getValue();
            this.filtroMesa = this.mesas.getValue();
            this.filtroStatus = this.status.getValue() != null ? this.status.getValue().get("STATUS") : null;
            Integer qtdeRegistrosConsulta = new VendaNegocio().quantidadeRegistroConsultaVenda(this.filtroDataInicial, this.filtroDataFinal, this.filtroUsuario != null ? this.filtroUsuario.getId() : null,
                    this.filtroCaixa != null ? this.filtroCaixa.getId() : null, this.filtroMesa != null ? this.filtroMesa : null, this.filtroStatus);
            this.paginas.getItems().clear();
            this.listaVendas.getItems().clear();
            if (qtdeRegistrosConsulta > 0) {
                BigDecimal qtdePaginas = new BigDecimal(qtdeRegistrosConsulta).divide(new BigDecimal(QTDE_REGISTROS), RoundingMode.UP);
                for (int i = 1; i <= qtdePaginas.intValue(); i++) {
                    this.paginas.getItems().add(i);
                }
                this.paginas.getSelectionModel().select(0);
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    private void preencheListaVendas() {
        this.listaVendas.setItems(observableList(new VendaNegocio().listaConsultaVenda(this.filtroDataInicial, this.filtroDataFinal,
                this.filtroUsuario != null ? this.filtroUsuario.getId() : null, this.filtroCaixa != null ? this.filtroCaixa.getId() : null,
                this.filtroMesa != null ? this.filtroMesa : null, this.filtroStatus, QTDE_REGISTROS, this.paginas.getValue())));
    }

    @FXML
    public void acaoVoltar(ActionEvent event) {
        try {
            AnchorPane tela = FXMLLoader.load(getClass().getResource("/fxml/TelaConsultas.fxml"));
            TelaPrincipalController.getInstance().mudaTela(tela, "Consultas");
        } catch (IOException | NumberFormatException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoVisualizar(ActionEvent event) {

    }

    @FXML
    public void acaoVoltarPagina(ActionEvent event) {
        try {
            if (this.paginas.getSelectionModel().getSelectedItem() > 1) {
                this.paginas.getSelectionModel().selectFirst();
                this.preencheListaVendas();
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoAvancarPagina(ActionEvent event) {
        try {
            if (!Objects.equals(this.paginas.getSelectionModel().getSelectedItem(), this.paginas.getItems().stream().max((m1, m2) -> m1.compareTo(m2)).get())) {
                this.paginas.getSelectionModel().selectNext();
                this.preencheListaVendas();
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }
}
