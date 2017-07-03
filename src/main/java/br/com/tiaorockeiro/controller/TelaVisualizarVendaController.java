/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.FormaPagamento;
import br.com.tiaorockeiro.modelo.Informacao;
import br.com.tiaorockeiro.modelo.ItemVenda;
import br.com.tiaorockeiro.modelo.Pagamento;
import br.com.tiaorockeiro.modelo.Total;
import static br.com.tiaorockeiro.modelo.Total.cartaoCredito;
import static br.com.tiaorockeiro.modelo.Total.cartaoDebito;
import static br.com.tiaorockeiro.modelo.Total.comissao;
import static br.com.tiaorockeiro.modelo.Total.desconto;
import static br.com.tiaorockeiro.modelo.Total.dinheiro;
import static br.com.tiaorockeiro.modelo.Total.outros;
import static br.com.tiaorockeiro.modelo.Total.totalGeral;
import static br.com.tiaorockeiro.modelo.Total.totalPagamentos;
import static br.com.tiaorockeiro.modelo.Total.troco;
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
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import static javafx.collections.FXCollections.observableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Wendel
 */
public class TelaVisualizarVendaController implements Initializable {

    private Venda venda;
    private TelaConsultaVendaController telaConsultaVenda;

    @FXML
    private ListView<ItemVenda> listViewItens;
    @FXML
    private TextField quantidadeItens;
    @FXML
    private TextField totalItens;
    @FXML
    private ListView<Total> listViewTotais;
    @FXML
    private ListView<Informacao> listViewInfoAdicionais;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.ajustaTabelaItens();
        this.ajustaTabelaTotais();
        this.ajustaTabelaInformacoesAdicionais();
    }

    private void ajustaTabelaItens() {
        this.listViewItens.setCellFactory(param -> new ListCell<ItemVenda>() {
            @Override
            protected void updateItem(ItemVenda item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    try {
                        ListCellProduto cellProduto = new ListCellProduto(item.getProduto().getDescricao(), item.getValorUnitario(), item.getQuantidade(), null, item.getValorTotal());
                        setGraphic(cellProduto.getCell());
                    } catch (Exception e) {
                        enviarMensagemErro(e.getMessage());
                        setText(null);
                        setGraphic(null);
                    }
                } else {
                    setText(null);
                    setGraphic(null);
                }
            }
        });
    }

    private void ajustaTabelaTotais() {
        this.listViewTotais.setCellFactory(param -> new ListCell<Total>() {
            @Override
            protected void updateItem(Total item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    try {
                        ListCellTotal cellTotal = new ListCellTotal(item.getDescricao(), item.getTotal());
                        setGraphic(cellTotal.getCell());
                    } catch (Exception e) {
                        enviarMensagemErro(e.getMessage());
                        setText(null);
                        setGraphic(null);
                    }
                } else {
                    setText(null);
                    setGraphic(null);
                }
            }
        });
    }

    private void ajustaTabelaInformacoesAdicionais() {
        this.listViewInfoAdicionais.setCellFactory(param -> new ListCell<Informacao>() {
            @Override
            protected void updateItem(Informacao item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    try {
                        ListCellInformacao cellInformacao = new ListCellInformacao(item.getDescricao(), item.getTexto());
                        setGraphic(cellInformacao.getCell());
                    } catch (Exception e) {
                        enviarMensagemErro(e.getMessage());
                        setText(null);
                        setGraphic(null);
                    }
                } else {
                    setText(null);
                    setGraphic(null);
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
        // ITENS
        this.venda.getItens().sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
        this.listViewItens.setItems(observableList(this.venda.getItens()));
        this.quantidadeItens.setText(formataQuantidade(this.listViewItens.getItems().stream().map(i -> i.getQuantidade()).reduce(BigDecimal.ZERO, BigDecimal::add)));
        BigDecimal total = this.listViewItens.getItems().stream().map(i -> i.getValorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.totalItens.setText(formataMoeda(total));
        // TOTAIS
        List<Pagamento> dinheiro = this.venda.getPagamentos().stream().filter(p -> p.getFormaPagamento().equals(FormaPagamento.DINHEIRO)).collect(Collectors.toList());
        this.listViewTotais.getItems().add(dinheiro(dinheiro.isEmpty() ? BigDecimal.ZERO : dinheiro.get(0).getValor()));
        List<Pagamento> cartaoCredito = this.venda.getPagamentos().stream().filter(p -> p.getFormaPagamento().equals(FormaPagamento.CARTAO_CREDITO)).collect(Collectors.toList());
        this.listViewTotais.getItems().add(cartaoCredito(cartaoCredito.isEmpty() ? BigDecimal.ZERO : cartaoCredito.get(0).getValor()));
        List<Pagamento> cartaoDebito = this.venda.getPagamentos().stream().filter(p -> p.getFormaPagamento().equals(FormaPagamento.CARTAO_DEBITO)).collect(Collectors.toList());
        this.listViewTotais.getItems().add(cartaoDebito(cartaoDebito.isEmpty() ? BigDecimal.ZERO : cartaoDebito.get(0).getValor()));
        List<Pagamento> outros = this.venda.getPagamentos().stream().filter(p -> p.getFormaPagamento().equals(FormaPagamento.OUTROS)).collect(Collectors.toList());
        this.listViewTotais.getItems().add(outros(outros.isEmpty() ? BigDecimal.ZERO : outros.get(0).getValor()));
        this.listViewTotais.getItems().add(desconto(this.venda.getValorDesconto() != null ? this.venda.getValorDesconto() : BigDecimal.ZERO));
        this.listViewTotais.getItems().add(comissao(this.venda.getValorComissao() != null ? this.venda.getValorComissao() : BigDecimal.ZERO));
        this.listViewTotais.getItems().add(totalGeral(this.venda.getValorTotal() != null ? this.venda.getValorTotal() : BigDecimal.ZERO));
        BigDecimal vlPago = this.venda.getPagamentos().stream().map(i -> i.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.listViewTotais.getItems().add(totalPagamentos(vlPago));
        BigDecimal vlTroco = vlPago.subtract(this.venda.getValorTotal()).compareTo(BigDecimal.ZERO) > 0 ? vlPago.subtract(this.venda.getValorTotal()) : BigDecimal.ZERO;
        this.listViewTotais.getItems().add(troco(vlTroco));
        // INFORMACOES ADICIONAIS
        this.listViewInfoAdicionais.getItems().add(new Informacao("Realizada por:", this.venda.getUsuario().getDescricao()));
        this.listViewInfoAdicionais.getItems().add(new Informacao("Data da Venda:", new SimpleDateFormat("dd/MM/yyyy").format(this.venda.getDataHora())));
        this.listViewInfoAdicionais.getItems().add(new Informacao("Hora da Venda:", new SimpleDateFormat("HH:mm:ss").format(this.venda.getDataHora())));
        this.listViewInfoAdicionais.getItems().add(new Informacao("Mesa:", this.venda.getMesa().toString()));
        if (this.venda.getDataHoraCancelamento() != null) {
            this.listViewInfoAdicionais.getItems().add(new Informacao("Cancelada por:", this.venda.getUsuarioCancelamento().getDescricao()));
            this.listViewInfoAdicionais.getItems().add(new Informacao("Data do Cancelamento:", new SimpleDateFormat("dd/MM/yyyy").format(this.venda.getDataHoraCancelamento())));
            this.listViewInfoAdicionais.getItems().add(new Informacao("Hora do Cancelamento:", new SimpleDateFormat("HH:mm:ss").format(this.venda.getDataHoraCancelamento())));
        }
    }
}
