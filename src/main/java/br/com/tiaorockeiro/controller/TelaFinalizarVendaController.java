/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.Cupom;
import br.com.tiaorockeiro.modelo.CupomAcerto;
import br.com.tiaorockeiro.modelo.CupomExtrato;
import br.com.tiaorockeiro.modelo.CupomItem;
import br.com.tiaorockeiro.modelo.CupomPagamento;
import br.com.tiaorockeiro.modelo.CupomTotal;
import static br.com.tiaorockeiro.modelo.FormaPagamento.CARTAO_CREDITO;
import static br.com.tiaorockeiro.modelo.FormaPagamento.CARTAO_DEBITO;
import static br.com.tiaorockeiro.modelo.FormaPagamento.DINHEIRO;
import static br.com.tiaorockeiro.modelo.FormaPagamento.OUTROS;
import br.com.tiaorockeiro.modelo.ItemPedido;
import br.com.tiaorockeiro.modelo.ItemVenda;
import br.com.tiaorockeiro.modelo.Pagamento;
import br.com.tiaorockeiro.modelo.Pedido;
import br.com.tiaorockeiro.modelo.Total;
import static br.com.tiaorockeiro.modelo.Total.TOTAL_DINHEIRO;
import static br.com.tiaorockeiro.modelo.Total.TOTAL_CARTAO_CREDITO;
import static br.com.tiaorockeiro.modelo.Total.TOTAL_CARTAO_DEBITO;
import static br.com.tiaorockeiro.modelo.Total.TOTAL_OUTROS;
import static br.com.tiaorockeiro.modelo.Total.TOTAL_DESCONTO;
import static br.com.tiaorockeiro.modelo.Total.TOTAL_COMISSAO;
import static br.com.tiaorockeiro.modelo.Total.TOTAL_GERAL;
import static br.com.tiaorockeiro.modelo.Total.TOTAL_PAGAMENTOS;
import static br.com.tiaorockeiro.modelo.Total.TOTAL_PAGAR;
import static br.com.tiaorockeiro.modelo.Total.TOTAL_TROCO;
import static br.com.tiaorockeiro.modelo.Total.cartaoCredito;
import static br.com.tiaorockeiro.modelo.Total.cartaoDebito;
import static br.com.tiaorockeiro.modelo.Total.comissao;
import static br.com.tiaorockeiro.modelo.Total.desconto;
import static br.com.tiaorockeiro.modelo.Total.dinheiro;
import static br.com.tiaorockeiro.modelo.Total.outros;
import static br.com.tiaorockeiro.modelo.Total.totalGeral;
import static br.com.tiaorockeiro.modelo.Total.totalPagamentos;
import static br.com.tiaorockeiro.modelo.Total.totalPagar;
import static br.com.tiaorockeiro.modelo.Total.troco;
import br.com.tiaorockeiro.modelo.Venda;
import br.com.tiaorockeiro.negocio.AberturaCaixaNegocio;
import br.com.tiaorockeiro.negocio.AdicionalProdutoNegocio;
import br.com.tiaorockeiro.negocio.ItemPedidoNegocio;
import br.com.tiaorockeiro.negocio.ObservacaoProdutoNegocio;
import br.com.tiaorockeiro.negocio.PedidoNegocio;
import br.com.tiaorockeiro.negocio.VendaNegocio;
import static br.com.tiaorockeiro.util.ImpressoraUtil.imprimir;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemConfirmacao;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemInformacao;
import static br.com.tiaorockeiro.util.MoedaUtil.formataMoeda;
import static br.com.tiaorockeiro.util.QuantidadeUtil.formataQuantidade;
import br.com.tiaorockeiro.util.SessaoUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Wendel
 */
public class TelaFinalizarVendaController implements Initializable {

    @FXML
    private Button btnAcaoVoltar;
    @FXML
    private ListView<ItemPedido> listViewItens;
    @FXML
    private ListView<Total> listViewTotais;
    @FXML
    private TextField textFieldQuantidadeItens;
    @FXML
    private TextField textFieldValorTotal;

    private Venda venda;

    public TelaFinalizarVendaController() {
        this.venda = new Venda();
        this.venda.setItens(new ArrayList<>());
        this.venda.setPagamentos(new ArrayList<>());
    }

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

    private void ajustaTabelaItens() {
        this.venda.getPedido().getItens().sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
        this.listViewItens.setItems(FXCollections.observableList(this.venda.getPedido().getItens().stream()
                .filter(i -> i.getDataHoraCancelamento() == null).collect(Collectors.toList())));
        this.listViewItens.setCellFactory(param -> new ListCell<ItemPedido>() {
            @Override
            protected void updateItem(ItemPedido item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    try {
                        ListCellProduto cellProduto = new ListCellProduto(item.getProduto().getDescricao(), item.getValor(), item.getQuantidade(), item.getValorTotalAdicionais(), item.getValorTotal());
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
        ObservableList<Total> totais = FXCollections.observableArrayList();
        totais.add(dinheiro(BigDecimal.ZERO));
        totais.add(cartaoCredito(BigDecimal.ZERO));
        totais.add(cartaoDebito(BigDecimal.ZERO));
        totais.add(outros(BigDecimal.ZERO));
        totais.add(desconto(BigDecimal.ZERO));
        totais.add(comissao(BigDecimal.ZERO));
        totais.add(totalGeral(BigDecimal.ZERO));
        totais.add(totalPagamentos(BigDecimal.ZERO));
        totais.add(totalPagar(BigDecimal.ZERO));
        totais.add(troco(BigDecimal.ZERO));
        this.listViewTotais.setItems(totais);
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

    private BigDecimal getTotal(Integer id) {
        return this.listViewTotais.getItems().stream().filter(t -> t.getId().equals(id)).collect(Collectors.toList()).get(0).getTotal();
    }

    private void setTotal(Integer id, BigDecimal valor) {
        Total total = this.listViewTotais.getItems().stream().filter(t -> t.getId().equals(id)).collect(Collectors.toList()).get(0);
        this.listViewTotais.getItems().set(this.listViewTotais.getItems().indexOf(total), Total.criaPorId(id, valor));
    }

    private void calculaComissao() {
        if (SessaoUtil.getConfiguracao() != null
                && SessaoUtil.getConfiguracao().getPercentualComissao() != null
                && SessaoUtil.getConfiguracao().getPercentualComissao().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal total = this.listViewItens.getItems().stream().map(i -> i.getValorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal comissao = total.multiply(SessaoUtil.getConfiguracao().getPercentualComissao()
                    .divide(BigDecimal.valueOf(100), RoundingMode.HALF_DOWN)).setScale(2, RoundingMode.HALF_DOWN);
            this.setTotal(TOTAL_COMISSAO, comissao);
        }
    }

    private void atualizaTotalizadores() {
        this.textFieldQuantidadeItens.setText(formataQuantidade(this.listViewItens.getItems().stream().map(i -> i.getQuantidade()).reduce(BigDecimal.ZERO, BigDecimal::add)));
        BigDecimal total = this.listViewItens.getItems().stream().map(i -> i.getValorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.textFieldValorTotal.setText(formataMoeda(total));
        BigDecimal desconto = this.getTotal(TOTAL_DESCONTO);
        BigDecimal comissao = this.getTotal(TOTAL_COMISSAO);
        BigDecimal totalGeral = total.add(comissao).subtract(desconto);
        this.setTotal(TOTAL_GERAL, totalGeral);
        BigDecimal dinheiro = this.getTotal(TOTAL_DINHEIRO);
        BigDecimal cartaoCredito = this.getTotal(TOTAL_CARTAO_CREDITO);
        BigDecimal cartaoDebito = this.getTotal(TOTAL_CARTAO_DEBITO);
        BigDecimal outros = this.getTotal(TOTAL_OUTROS);
        BigDecimal totalPagamento = dinheiro.add(cartaoCredito).add(cartaoDebito).add(outros);
        this.setTotal(TOTAL_PAGAMENTOS, totalPagamento);
        this.setTotal(TOTAL_PAGAR, totalGeral.subtract(totalPagamento).compareTo(BigDecimal.ZERO) > 0 ? totalGeral.subtract(totalPagamento) : BigDecimal.ZERO);
        this.setTotal(TOTAL_TROCO, totalPagamento.compareTo(totalGeral) > 0 ? totalPagamento.subtract(totalGeral) : BigDecimal.ZERO);
    }

    @FXML
    public void acaoCancelarProduto(ActionEvent event) {
        try {
            int index = this.listViewItens.getSelectionModel().getSelectedIndex();
            if (index != -1 && enviarMensagemConfirmacao("Deseja realmente cancelar o item selecionado?")) {
                ItemPedido item = this.listViewItens.getItems().get(index);
                item.setDataHoraCancelamento(new Date());
                item.setUsuarioCancelamento(SessaoUtil.getUsuario());
                new ItemPedidoNegocio().salvar(item);
                this.listViewItens.getItems().remove(index);
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
            int index = this.listViewTotais.getSelectionModel().getSelectedIndex();
            if (index != -1 && asList(TOTAL_DINHEIRO, TOTAL_CARTAO_CREDITO, TOTAL_CARTAO_DEBITO, TOTAL_OUTROS, TOTAL_DESCONTO, TOTAL_COMISSAO).contains(this.listViewTotais.getItems().get(index).getId())) {
                Total totalAntigo = this.listViewTotais.getItems().get(index);
                this.setTotal(totalAntigo.getId(), BigDecimal.ZERO);
            }
            this.atualizaTotalizadores();
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoBotaoCalcular(ActionEvent event) {
        try {
            int index = this.listViewTotais.getSelectionModel().getSelectedIndex();
            if (index != -1 && this.listViewTotais.getItems().get(index).getId().equals(TOTAL_COMISSAO)) {
                this.calculaComissao();
                this.atualizaTotalizadores();
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    private void adicionaNumero(Integer numero) {
        try {
            int index = this.listViewTotais.getSelectionModel().getSelectedIndex();
            if (index != -1 && asList(TOTAL_DINHEIRO, TOTAL_CARTAO_CREDITO, TOTAL_CARTAO_DEBITO, TOTAL_OUTROS, TOTAL_DESCONTO).contains(this.listViewTotais.getItems().get(index).getId())) {
                Total totalAntigo = this.listViewTotais.getItems().get(index);
                StringBuilder texto = new StringBuilder(totalAntigo.getTotal().toString().replaceAll("\\.", ""));
                texto.append(numero);
                texto.insert(texto.length() - 2, ".");
                this.setTotal(totalAntigo.getId(), new BigDecimal(texto.toString()));
                this.atualizaTotalizadores();
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoFinalizarVenda(ActionEvent event) {
        try {
            BigDecimal dinheiro = this.getTotal(TOTAL_DINHEIRO);
            BigDecimal cartaoCredito = this.getTotal(TOTAL_CARTAO_CREDITO);
            BigDecimal cartaoDebito = this.getTotal(TOTAL_CARTAO_DEBITO);
            BigDecimal outros = this.getTotal(TOTAL_OUTROS);
            BigDecimal pagamentos = dinheiro.add(cartaoCredito).add(cartaoDebito).add(outros);
            BigDecimal desconto = this.getTotal(TOTAL_DESCONTO);
            BigDecimal comissao = this.getTotal(TOTAL_COMISSAO);
            BigDecimal totalItens = this.listViewItens.getItems().stream().map(i -> i.getValorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal totalGeral = totalItens.add(comissao).subtract(desconto);
            // VERIFICA SE TOTAL DE PAGAMENTOS E MAIOR DO QUE O TOTAL GERAL
            if (pagamentos.compareTo(totalGeral) < 0) {
                enviarMensagemInformacao("O valor total dos pagamentos é menor que o valor total a pagar!");
            } else {
                this.venda.setUsuario(SessaoUtil.getUsuario());
                this.venda.setDataHora(new Date());
                this.venda.setValorComissao(comissao);
                this.venda.setValorDesconto(desconto);
                // ITENS
                this.venda.getPedido().getItens().forEach(i -> {
                    ItemVenda item = new ItemVenda();
                    item.setVenda(this.venda);
                    item.setProduto(i.getProduto());
                    item.setQuantidade(i.getQuantidade());
                    item.setValorUnitario(i.getValor());
                    this.venda.getItens().add(item);
                    i.getAdicionais().forEach(a -> {
                        ItemVenda itemAdicional = new ItemVenda();
                        itemAdicional.setVenda(this.venda);
                        itemAdicional.setProduto(a.getProduto());
                        itemAdicional.setQuantidade(a.getQuantidade());
                        itemAdicional.setValorUnitario(a.getValor());
                        this.venda.getItens().add(itemAdicional);
                    });
                });
                // FORMA PAGAMENTO
                if (dinheiro.compareTo(BigDecimal.ZERO) > 0) {
                    Pagamento pagamento = new Pagamento();
                    pagamento.setVenda(this.venda);
                    pagamento.setFormaPagamento(DINHEIRO);
                    pagamento.setValor(dinheiro);
                    this.venda.getPagamentos().add(pagamento);
                }
                if (cartaoCredito.compareTo(BigDecimal.ZERO) > 0) {
                    Pagamento pagamento = new Pagamento();
                    pagamento.setVenda(this.venda);
                    pagamento.setFormaPagamento(CARTAO_CREDITO);
                    pagamento.setValor(cartaoCredito);
                    this.venda.getPagamentos().add(pagamento);
                }
                if (cartaoDebito.compareTo(BigDecimal.ZERO) > 0) {
                    Pagamento pagamento = new Pagamento();
                    pagamento.setVenda(this.venda);
                    pagamento.setFormaPagamento(CARTAO_DEBITO);
                    pagamento.setValor(cartaoDebito);
                    this.venda.getPagamentos().add(pagamento);
                }
                if (outros.compareTo(BigDecimal.ZERO) > 0) {
                    Pagamento pagamento = new Pagamento();
                    pagamento.setVenda(this.venda);
                    pagamento.setFormaPagamento(OUTROS);
                    pagamento.setValor(outros);
                    this.venda.getPagamentos().add(pagamento);
                }
                this.venda = new VendaNegocio().salvar(this.venda);
                if (this.venda.getId() == null) {
                    throw new Exception("Erro ao finalizar a venda, tente novamente!");
                }
                
                Cupom cupom = new CupomAcerto("TIAO ROCKEIRO", this.venda.getPedido().getUsuario().getPessoa().getId()
                        + " - " + this.venda.getPedido().getUsuario().getPessoa().getDescricao(),
                        this.venda.getPedido().getUsuario().getDescricao(), this.venda.getPedido().getMesa(),
                        this.venda.getPedido().getDataHora(), this.venda.getAberturaCaixa().getCaixa().getImpressora());
                this.venda.getItens().forEach(i -> {
                    cupom.addItem(new CupomItem(i.getProduto().getDescricao(), i.getQuantidade(), i.getValorUnitario(), i.getValorTotal()));
                });
                this.venda.getPagamentos().forEach(p -> {
                    cupom.addPagamento(new CupomPagamento(p.getFormaPagamento().getDescricao(), p.getValor()));
                });
                cupom.addTotal(new CupomTotal("TOTAL ITENS", this.venda.getValorTotalItens()));
                cupom.addTotal(new CupomTotal("COMISSAO", this.venda.getValorComissao()));
                cupom.addTotal(new CupomTotal("DESCONTO", this.venda.getValorDesconto()));
                cupom.addTotal(new CupomTotal("TOTAL", this.venda.getValorTotal()));
                cupom.addTotal(new CupomTotal("TOTAL PAGMENTO", this.venda.getPagamentos().stream().map(p -> p.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add)));
                imprimir(cupom.getCupom(), this.venda.getAberturaCaixa().getCaixa().getImpressora().getUrl());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaMesas.fxml"));
                AnchorPane telaMesas = loader.load();
                TelaPrincipalController.getInstance().mudaTela(telaMesas, "Mesas");
                enviarMensagemInformacao("Venda finalizada com sucesso!");
            }
        } catch (Exception e) {
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
                TelaPrincipalController.getInstance().mudaTela(telaMesas, "Mesas");
                enviarMensagemInformacao("Venda cancelada com sucesso!");
            }
        } catch (IOException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoEmitirExtrato(ActionEvent event) {
        try {
            Cupom cupom = new CupomExtrato("TIAO ROCKEIRO", this.venda.getPedido().getUsuario().getPessoa().getId()
                    + " - " + this.venda.getPedido().getUsuario().getPessoa().getDescricao(),
                    this.venda.getPedido().getUsuario().getDescricao(), this.venda.getPedido().getMesa(),
                    this.venda.getPedido().getDataHora(), this.venda.getAberturaCaixa().getCaixa().getImpressora());
            this.venda.getPedido().getItens().forEach(i -> {
                cupom.addItem(new CupomItem(i.getProduto().getDescricao(), i.getQuantidade(), i.getValor(), i.getValorTotal()));
                i.getAdicionais().forEach(a -> {
                    cupom.addItem(new CupomItem(a.getProduto().getDescricao(), a.getQuantidade(), a.getValor(), a.getValorTotal()));
                });
            });
            cupom.addTotal(new CupomTotal("TOTAL", this.listViewItens.getItems().stream().map(i -> i.getValorTotal()).reduce(BigDecimal.ZERO, BigDecimal::add)));
            imprimir(cupom.getCupom(), this.venda.getAberturaCaixa().getCaixa().getImpressora().getUrl());
        } catch (Exception e) {
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
            TelaPrincipalController.getInstance().mudaTela(telaPedido, "Pedido - Mesa " + this.venda.getMesa());
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    public void inicializaDados(Long idPedido) throws Exception {
        this.venda.setPedido(new PedidoNegocio().obterPorId(Pedido.class, idPedido));
        this.venda.setAberturaCaixa(new AberturaCaixaNegocio()
                .obterAbertoPorCaixa(SessaoUtil.getUsuario().getConfiguracao().getCaixaSelecionado()));
        ObservacaoProdutoNegocio observacaoProdutoNegocio = new ObservacaoProdutoNegocio();
        AdicionalProdutoNegocio adicionalProdutoNegocio = new AdicionalProdutoNegocio();
        this.venda.getPedido().getItens().stream().map((itemPedido) -> {
            return itemPedido;
        }).forEachOrdered((itemPedido) -> {
            itemPedido.setObservacoes(observacaoProdutoNegocio.obterPorIdItemPedido(itemPedido.getId()));
            itemPedido.setAdicionais(adicionalProdutoNegocio.obterPorIdItemPedido(itemPedido.getId()));
        });
        this.venda.setMesa(this.venda.getPedido().getMesa());
        this.ajustaTabelaItens();
        this.ajustaTabelaTotais();
        this.calculaComissao();
        this.atualizaTotalizadores();
    }
}
