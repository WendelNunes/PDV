/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.ItemPedido;
import br.com.tiaorockeiro.modelo.Pedido;
import br.com.tiaorockeiro.modelo.Venda;
import br.com.tiaorockeiro.negocio.ItemPedidoNegocio;
import br.com.tiaorockeiro.negocio.PedidoNegocio;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemConfirmacao;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemInformacao;
import static br.com.tiaorockeiro.util.MoedaUtil.formataMoeda;
import static br.com.tiaorockeiro.util.MoedaUtil.parseMoeda;
import static br.com.tiaorockeiro.util.QuantidadeUtil.formataQuantidade;
import br.com.tiaorockeiro.util.SessaoUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author Wendel
 */
public class TelaFinalizarVendaController implements Initializable {

    @FXML
    private Button btnAcaoVoltar;
    @FXML
    private ToggleGroup grupoBotaoTeclado;
    @FXML
    private ToggleButton buttonSelecionarDinheiro;
    @FXML
    private ToggleButton buttonSelecionarCartaoCredito;
    @FXML
    private ToggleButton buttonSelecionarCartaoDebito;
    @FXML
    private ToggleButton buttonSelecionarOutros;
    @FXML
    private ToggleButton buttonSelecionarDesconto;
    @FXML
    private Label titulo;
    @FXML
    private TableView<ItemPedido> tableViewProdutos;
    @FXML
    private TableColumn<ItemPedido, Long> tableColumnSequencia;
    @FXML
    private TableColumn<ItemPedido, String> tableColumnProduto;
    @FXML
    private TableColumn<ItemPedido, BigDecimal> tableColumnQuantidade;
    @FXML
    private TableColumn<ItemPedido, BigDecimal> tableColumnPreco;
    @FXML
    private TableColumn<ItemPedido, BigDecimal> tableColumnTotal;
    @FXML
    private TextField textFieldDinhieiro;
    @FXML
    private TextField textFieldCartaoCredito;
    @FXML
    private TextField textFieldCartaoDebito;
    @FXML
    private TextField textFieldOutros;
    @FXML
    private TextField textFieldDesconto;
    @FXML
    private TextField textFieldComissao;
    @FXML
    private TextField textFieldTotalGeral;
    @FXML
    private TextField textFieldTroco;
    @FXML
    private TextField textFieldQuantidadeItens;
    @FXML
    private TextField textFieldValorTotal;

    private Venda venda;

    public TelaFinalizarVendaController() {
        this.venda = new Venda();
        this.venda.setItens(new ArrayList<>());
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.textFieldDinhieiro.setText(formataMoeda(BigDecimal.ZERO));
        this.textFieldCartaoCredito.setText(formataMoeda(BigDecimal.ZERO));
        this.textFieldCartaoDebito.setText(formataMoeda(BigDecimal.ZERO));
        this.textFieldOutros.setText(formataMoeda(BigDecimal.ZERO));
        this.textFieldDesconto.setText(formataMoeda(BigDecimal.ZERO));
        this.textFieldComissao.setText(formataMoeda(BigDecimal.ZERO));
        this.textFieldTotalGeral.setText(formataMoeda(BigDecimal.ZERO));
        this.textFieldTroco.setText(formataMoeda(BigDecimal.ZERO));

        this.buttonSelecionarDinheiro.setUserData(this.textFieldDinhieiro);
        this.buttonSelecionarCartaoCredito.setUserData(this.textFieldCartaoCredito);
        this.buttonSelecionarCartaoDebito.setUserData(this.textFieldCartaoDebito);
        this.buttonSelecionarOutros.setUserData(this.textFieldOutros);
        this.buttonSelecionarDesconto.setUserData(this.textFieldDesconto);

        this.btnAcaoVoltar.setOnAction((ActionEvent event) -> {
            acaoVoltar(event);
        });
    }

    @SuppressWarnings("Convert2Lambda")
    private void ajustaTabelaItens() {
        this.venda.getPedido().getItens().sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
        this.tableViewProdutos.setItems(FXCollections.observableList(this.venda.getPedido().getItens().stream()
                .filter(i -> i.getDataHoraCancelamento() == null).collect(Collectors.toList())));
        this.tableViewProdutos.setFixedCellSize(45);
        this.tableColumnSequencia.setCellValueFactory(i -> new SimpleObjectProperty<>(i.getValue().getId()));
        this.tableColumnSequencia.setCellFactory((TableColumn<ItemPedido, Long> param) -> new TableCell<ItemPedido, Long>() {
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
        this.tableColumnProduto.setCellFactory((TableColumn<ItemPedido, String> param) -> new TableCell<ItemPedido, String>() {
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
        this.tableColumnQuantidade.setCellFactory((TableColumn<ItemPedido, BigDecimal> param) -> new TableCell<ItemPedido, BigDecimal>() {
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
        this.tableColumnPreco.setCellValueFactory(i -> new SimpleObjectProperty<>(i.getValue().getValor()));
        this.tableColumnPreco.setCellFactory((TableColumn<ItemPedido, BigDecimal> param) -> new TableCell<ItemPedido, BigDecimal>() {
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
        this.tableColumnTotal.setCellFactory((TableColumn<ItemPedido, BigDecimal> param) -> new TableCell<ItemPedido, BigDecimal>() {
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

    private void atualizaTotalizadores() {
        this.textFieldQuantidadeItens.setText(formataQuantidade(this.tableViewProdutos.getItems().stream().map(i -> i.getQuantidade()).reduce(BigDecimal.ZERO, BigDecimal::add)));
        BigDecimal total = this.tableViewProdutos.getItems().stream().map(i -> i.getValorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.textFieldValorTotal.setText(formataMoeda(total));
        BigDecimal desconto = new BigDecimal(this.textFieldDesconto.getText().replaceAll("\\.", "").replaceAll(",", "."));
        BigDecimal comissao = new BigDecimal(this.textFieldComissao.getText().replaceAll("\\.", "").replaceAll(",", "."));
        BigDecimal totalGeral = total.add(comissao).subtract(desconto);
        this.textFieldTotalGeral.setText(formataMoeda(totalGeral));
        BigDecimal dinheiro = new BigDecimal(this.textFieldDinhieiro.getText().replaceAll("\\.", "").replaceAll(",", "."));
        BigDecimal cartaoCredito = new BigDecimal(this.textFieldCartaoCredito.getText().replaceAll("\\.", "").replaceAll(",", "."));
        BigDecimal cartaoDebito = new BigDecimal(this.textFieldCartaoDebito.getText().replaceAll("\\.", "").replaceAll(",", "."));
        BigDecimal outros = new BigDecimal(this.textFieldOutros.getText().replaceAll("\\.", "").replaceAll(",", "."));
        BigDecimal totalPagamento = dinheiro.add(cartaoCredito).add(cartaoDebito).add(outros);
        this.textFieldTroco.setText(formataMoeda(totalPagamento.compareTo(totalGeral) > 0
                ? totalPagamento.subtract(totalGeral) : BigDecimal.ZERO));
    }

    @FXML
    public void acaoCancelarProduto(ActionEvent event) {
        try {
            int index = this.tableViewProdutos.getSelectionModel().getSelectedIndex();
            if (index != -1 && enviarMensagemConfirmacao("Deseja realmente cancelar o item selecionado?")) {
                ItemPedido item = this.tableViewProdutos.getItems().get(index);
                item.setDataHoraCancelamento(new Date());
                item.setUsuarioCancelamento(SessaoUtil.getUsuario());
                new ItemPedidoNegocio().salvar(item);
                this.tableViewProdutos.getItems().remove(index);
                this.atualizaTotalizadores();
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoBotao1(ActionEvent event) {
        this.adicionaNumero(1);
    }

    @FXML
    public void acaoBotao2(ActionEvent event) {
        this.adicionaNumero(2);
    }

    @FXML
    public void acaoBotao3(ActionEvent event) {
        this.adicionaNumero(3);
    }

    @FXML
    public void acaoBotao4(ActionEvent event) {
        this.adicionaNumero(4);
    }

    @FXML
    public void acaoBotao5(ActionEvent event) {
        this.adicionaNumero(5);
    }

    @FXML
    public void acaoBotao6(ActionEvent event) {
        this.adicionaNumero(6);
    }

    @FXML
    public void acaoBotao7(ActionEvent event) {
        this.adicionaNumero(7);
    }

    @FXML
    public void acaoBotao8(ActionEvent event) {
        this.adicionaNumero(8);
    }

    @FXML
    public void acaoBotao9(ActionEvent event) {
        this.adicionaNumero(9);
    }

    @FXML
    public void acaoBotao0(ActionEvent event) {
        this.adicionaNumero(0);
    }

    @FXML
    public void acaoBotaoLimpar(ActionEvent event) {
        try {
            if (this.grupoBotaoTeclado.getSelectedToggle() != null) {
                ((TextField) this.grupoBotaoTeclado.getSelectedToggle().getUserData()).setText(formataMoeda(BigDecimal.ZERO));
            }
            this.atualizaTotalizadores();
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    private void adicionaNumero(Integer numero) {
        try {
            if (this.grupoBotaoTeclado.getSelectedToggle() != null) {
                TextField textField = (TextField) this.grupoBotaoTeclado.getSelectedToggle().getUserData();
                StringBuilder texto = new StringBuilder(parseMoeda(textField.getText()).toString().replaceAll("\\.", ""));
                texto.append(numero);
                texto.insert(texto.length() - 2, ".");
                textField.setText(formataMoeda(new BigDecimal(texto.toString())));
                this.atualizaTotalizadores();
            }
        } catch (NumberFormatException | ParseException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoCancelarVenda(ActionEvent event) {
        try {
            if (enviarMensagemConfirmacao("Deseja realmente cancelar a venda?")) {
                this.venda.getPedido().setDataHoraCancelamento(new Date());
                this.venda.getPedido().setUsuarioCancelamento(SessaoUtil.getUsuario());
                new PedidoNegocio().salvar(this.venda.getPedido());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaMesas.fxml"));
                AnchorPane telaMesas = loader.load();
                TelaPrincipalController.getInstance().mudaTela(telaMesas);
                enviarMensagemInformacao("Venda cancelada com sucesso!");
            }
        } catch (IOException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaPedido.fxml"));
            AnchorPane telaPedido = loader.load();
            TelaPedidoController telaPedidoController = loader.getController();
            telaPedidoController.inicializaDados(this.venda.getMesa());
            TelaPrincipalController.getInstance().mudaTela(telaPedido);
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    public void inicializaDados(Long idPedido) {
        this.venda.setPedido(new PedidoNegocio().obterPorId(Pedido.class, idPedido));
        this.venda.setMesa(this.venda.getPedido().getMesa());
        this.titulo.setText("Mesa " + this.venda.getMesa());
        this.ajustaTabelaItens();
        this.atualizaTotalizadores();
    }
}
