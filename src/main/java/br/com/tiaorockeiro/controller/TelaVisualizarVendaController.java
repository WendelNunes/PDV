/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.ItemVenda;
import br.com.tiaorockeiro.modelo.Pagamento;
import br.com.tiaorockeiro.modelo.Venda;
import br.com.tiaorockeiro.negocio.ItemVendaNegocio;
import br.com.tiaorockeiro.negocio.PagamentoNegocio;
import br.com.tiaorockeiro.negocio.VendaNegocio;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemConfirmacao;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemInformacao;
import static br.com.tiaorockeiro.util.MoedaUtil.formataMoeda;
import static br.com.tiaorockeiro.util.QuantidadeUtil.formataQuantidade;
import br.com.tiaorockeiro.util.SessaoUtil;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import static javafx.collections.FXCollections.observableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author Wendel
 */
public class TelaVisualizarVendaController implements Initializable {

    private Venda venda;
    private TelaConsultaVendaController telaConsultaVenda;

    @FXML
    private TableView<ItemVenda> tableViewProdutos;
    @FXML
    private TableColumn<ItemVenda, Long> tableColumnSequencia;
    @FXML
    private TableColumn<ItemVenda, String> tableColumnProduto;
    @FXML
    private TableColumn<ItemVenda, BigDecimal> tableColumnQuantidade;
    @FXML
    private TableColumn<ItemVenda, BigDecimal> tableColumnPreco;
    @FXML
    private TableColumn<ItemVenda, BigDecimal> tableColumnTotal;
    @FXML
    private TableView<Pagamento> tableViewValores;
    @FXML
    private TableColumn<Pagamento, String> tableColumnDescricaoValor;
    @FXML
    private TableColumn<Pagamento, BigDecimal> tableColumnValor;
    @FXML
    private TextField quantidadeItens;
    @FXML
    private TextField totalItens;
    @FXML
    private Label desconto;
    @FXML
    private Label comissao;
    @FXML
    private TextField pago;
    @FXML
    private Label troco;
    @FXML
    private Label data;
    @FXML
    private Label hora;
    @FXML
    private Label mesa;
    @FXML
    private Label usuario;
    @FXML
    private Label usuarioCancelamento;
    @FXML
    private Label lbUsuarioCancelamento;
    @FXML
    private Label dataCancelamento;
    @FXML
    private Label lbDataCancelamento;
    @FXML
    private Label horaCancelamento;
    @FXML
    private Label lbHoraCancelamento;
    @FXML
    private Label valor;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.ajustaTabelaItens();
    }

    @SuppressWarnings("Convert2Lambda")
    private void ajustaTabelaItens() {
        this.tableViewProdutos.setFixedCellSize(45);
        this.tableColumnSequencia.setCellValueFactory(i -> new SimpleObjectProperty<>(i.getValue().getId()));
        this.tableColumnSequencia.setCellFactory((TableColumn<ItemVenda, Long> param) -> new TableCell<ItemVenda, Long>() {
            @Override
            protected void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setText("");
                } else {
                    setText(String.valueOf(getIndex() + 1));
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_RIGHT);
                }
            }
        });
        this.tableColumnProduto.setCellValueFactory(i -> new SimpleStringProperty(i.getValue().getProduto().getDescricao()));
        this.tableColumnProduto.setCellFactory((TableColumn<ItemVenda, String> param) -> new TableCell<ItemVenda, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText("");
                } else {
                    setText(item);
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_LEFT);
                }
            }
        });
        this.tableColumnQuantidade.setCellValueFactory(i -> new SimpleObjectProperty<>(i.getValue().getQuantidade()));
        this.tableColumnQuantidade.setCellFactory((TableColumn<ItemVenda, BigDecimal> param) -> new TableCell<ItemVenda, BigDecimal>() {
            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText("");
                } else {
                    setText(formataQuantidade(item));
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_RIGHT);
                }
            }
        });
        this.tableColumnPreco.setCellValueFactory(i -> new SimpleObjectProperty<>(i.getValue().getValorUnitario()));
        this.tableColumnPreco.setCellFactory((TableColumn<ItemVenda, BigDecimal> param) -> new TableCell<ItemVenda, BigDecimal>() {
            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText("");
                } else {
                    setText(formataMoeda(item));
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_RIGHT);
                }
            }
        });
        this.tableColumnTotal.setCellValueFactory(i -> new SimpleObjectProperty<>(i.getValue().getValorTotal()));
        this.tableColumnTotal.setCellFactory((TableColumn<ItemVenda, BigDecimal> param) -> new TableCell<ItemVenda, BigDecimal>() {
            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText("");
                } else {
                    setText(formataMoeda(item));
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_RIGHT);
                }
            }
        });
        this.tableViewValores.setFixedCellSize(45);
        this.tableColumnDescricaoValor.setCellValueFactory(i -> new SimpleStringProperty(i.getValue().getFormaPagamento().getDescricao()));
        this.tableColumnDescricaoValor.setCellFactory((TableColumn<Pagamento, String> param) -> new TableCell<Pagamento, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText("");
                } else {
                    setText(item);
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_LEFT);
                }
            }
        });
        this.tableColumnValor.setCellValueFactory(i -> new SimpleObjectProperty<>((BigDecimal) i.getValue().getValor()));
        this.tableColumnValor.setCellFactory((TableColumn<Pagamento, BigDecimal> param) -> new TableCell<Pagamento, BigDecimal>() {
            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText("");
                } else {
                    setText(formataMoeda(item));
                    setFont(Font.font("Arial", 14));
                    setAlignment(Pos.CENTER_RIGHT);
                }
            }
        });
    }

    @FXML
    public void acaoCancelarVenda(ActionEvent event) {
        try {
            if (enviarMensagemConfirmacao("Deseja realmente cancelar a venda?")) {
                this.venda.setDataHoraCancelamento(new Date());
                this.venda.setUsuarioCancelamento(SessaoUtil.getUsuario());
                new VendaNegocio().salvar(this.venda);
                this.telaConsultaVenda.preencheListaVendas();
                this.acaoVoltar(null);
                enviarMensagemInformacao("Venda cancelada com sucesso!");
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoEmitirExtrato(ActionEvent event) {

    }

    @FXML
    public void acaoVoltar(ActionEvent event) {
        try {
            TelaPrincipalController.getInstance().mudaTela(this.telaConsultaVenda.getTelaConsultaVenda(), "Consulta de Vendas");
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    public void inicializaDados(Long idVenda, TelaConsultaVendaController consultaVenda) {
        this.telaConsultaVenda = consultaVenda;
        this.venda = new VendaNegocio().obterPorId(Venda.class, idVenda);
        this.venda.setItens(new ItemVendaNegocio().listarPorIdVenda(idVenda));
        this.venda.setPagamentos(new PagamentoNegocio().listarPorIdVenda(idVenda));
        BigDecimal vlPago = this.venda.getPagamentos().stream().map(i -> i.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);

        this.data.setText(new SimpleDateFormat("dd/MM/yyyy").format(this.venda.getDataHora()));
        this.hora.setText(new SimpleDateFormat("HH:mm:ss").format(this.venda.getDataHora()));
        this.mesa.setText(this.venda.getMesa().toString());
        this.usuario.setText(this.venda.getUsuario().getDescricao());
        this.valor.setText(formataMoeda(this.venda.getValorTotal()));
        this.desconto.setText(formataMoeda(this.venda.getValorDesconto()));
        this.comissao.setText(formataMoeda(this.venda.getValorComissao()));
        BigDecimal vlTroco = vlPago.subtract(this.venda.getValorTotal()).compareTo(BigDecimal.ZERO) > 0 ? vlPago.subtract(this.venda.getValorTotal()) : BigDecimal.ZERO;
        this.troco.setText(formataMoeda(vlTroco));

        if (this.venda.getDataHoraCancelamento() != null) {
            this.usuarioCancelamento.setText(this.venda.getUsuarioCancelamento().getDescricao());
            this.dataCancelamento.setText(new SimpleDateFormat("dd/MM/yyyy").format(this.venda.getDataHoraCancelamento()));
            this.horaCancelamento.setText(new SimpleDateFormat("HH:mm:ss").format(this.venda.getDataHoraCancelamento()));
        } else {
            this.usuarioCancelamento.setVisible(false);
            this.dataCancelamento.setVisible(false);
            this.horaCancelamento.setVisible(false);
            this.lbUsuarioCancelamento.setVisible(false);
            this.lbHoraCancelamento.setVisible(false);
            this.lbDataCancelamento.setVisible(false);
        }

        this.venda.getItens().sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
        this.tableViewProdutos.setItems(observableList(this.venda.getItens()));
        this.quantidadeItens.setText(formataQuantidade(this.tableViewProdutos.getItems().stream().map(i -> i.getQuantidade()).reduce(BigDecimal.ZERO, BigDecimal::add)));
        BigDecimal total = this.tableViewProdutos.getItems().stream().map(i -> i.getValorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.totalItens.setText(formataMoeda(total));

        this.tableViewValores.setItems(observableList(this.venda.getPagamentos()));
        this.pago.setText(formataMoeda(vlPago));
    }
}
