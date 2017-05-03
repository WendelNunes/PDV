/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.AberturaCaixa;
import br.com.tiaorockeiro.modelo.Caixa;
import br.com.tiaorockeiro.negocio.AberturaCaixaNegocio;
import br.com.tiaorockeiro.negocio.CaixaNegocio;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemInformacao;
import static br.com.tiaorockeiro.util.MoedaUtil.formataMoeda;
import static br.com.tiaorockeiro.util.MoedaUtil.parseMoeda;
import br.com.tiaorockeiro.util.SessaoUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Wendel
 */
public class TelaAberturaCaixaController implements Initializable {

    @FXML
    private ComboBox<Caixa> cbCaixa;
    @FXML
    private TextField inputDataAbertura;
    @FXML
    private TextField inputHoraAbertura;
    @FXML
    private TextField inputSaldoInicial;
    private Date dataHoraAberturaCaixa;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.dataHoraAberturaCaixa = new Date();
            this.inputSaldoInicial.setText(formataMoeda(BigDecimal.ZERO));
            this.inputDataAbertura.setText(new SimpleDateFormat("dd/MM/yyyy").format(this.dataHoraAberturaCaixa));
            this.inputHoraAbertura.setText(new SimpleDateFormat("HH:mm:ss").format(this.dataHoraAberturaCaixa));
            this.cbCaixa.getItems().add(null);
            this.cbCaixa.getItems().addAll(FXCollections.observableList(new CaixaNegocio().obterCaixasSemAbertura()));
            this.cbCaixa.setCellFactory((ListView<Caixa> param) -> new ListCell<Caixa>() {
                @Override
                protected void updateItem(Caixa item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.getCodigo() + " - " + item.getDescricao());
                    } else {
                        setText(null);
                    }
                }
            });
            this.cbCaixa.setConverter(new StringConverter<Caixa>() {
                @Override
                public String toString(Caixa caixa) {
                    return caixa != null ? caixa.getCodigo() + " - " + caixa.getDescricao() : null;
                }

                @Override
                public Caixa fromString(String string) {
                    return null;
                }
            });
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoBotao1(ActionEvent event) {
        this.adicionaNumeroSaldoInicial(1);
    }

    @FXML
    public void acaoBotao2(ActionEvent event) {
        this.adicionaNumeroSaldoInicial(2);
    }

    @FXML
    public void acaoBotao3(ActionEvent event) {
        this.adicionaNumeroSaldoInicial(3);
    }

    @FXML
    public void acaoBotao4(ActionEvent event) {
        this.adicionaNumeroSaldoInicial(4);
    }

    @FXML
    public void acaoBotao5(ActionEvent event) {
        this.adicionaNumeroSaldoInicial(5);
    }

    @FXML
    public void acaoBotao6(ActionEvent event) {
        this.adicionaNumeroSaldoInicial(6);
    }

    @FXML
    public void acaoBotao7(ActionEvent event) {
        this.adicionaNumeroSaldoInicial(7);
    }

    @FXML
    public void acaoBotao8(ActionEvent event) {
        this.adicionaNumeroSaldoInicial(8);
    }

    @FXML
    public void acaoBotao9(ActionEvent event) {
        this.adicionaNumeroSaldoInicial(9);
    }

    @FXML
    public void acaoBotao0(ActionEvent event) {
        this.adicionaNumeroSaldoInicial(0);
    }

    @FXML
    public void acaoBotaoLimparSaldoInicial(ActionEvent event) {
        this.inputSaldoInicial.setText(formataMoeda(BigDecimal.ZERO));
    }

    @FXML
    public void acaoBotaoAbrirCaixa(ActionEvent event) {
        try {
            if (this.cbCaixa.getValue() == null) {
                enviarMensagemInformacao("Caixa deve ser informado!");
            } else {
                AberturaCaixa aberturaCaixa = new AberturaCaixa();
                aberturaCaixa.setCaixa(this.cbCaixa.getValue());
                aberturaCaixa.setDataHora(this.dataHoraAberturaCaixa);
                aberturaCaixa.setSaldoInicial(new BigDecimal(this.inputSaldoInicial.getText().replaceAll("\\.", "").replaceAll(",", ".")));
                aberturaCaixa.setUsuario(SessaoUtil.getUsuario());
                new AberturaCaixaNegocio().salvar(aberturaCaixa);
                this.acaoBotaoVoltar(null);
                enviarMensagemInformacao("Caixa aberto com sucesso!");
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoBotaoVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaMesas.fxml"));
            AnchorPane telaMesas = loader.load();
            TelaPrincipalController.getInstance().mudaTela(telaMesas);
        } catch (IOException | NumberFormatException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    private void adicionaNumeroSaldoInicial(Integer numero) {
        try {
            StringBuilder texto = new StringBuilder(parseMoeda(this.inputSaldoInicial.getText()).toString().replaceAll("\\.", ""));
            texto.append(numero);
            texto.insert(texto.length() - 2, ".");
            this.inputSaldoInicial.setText(formataMoeda(new BigDecimal(texto.toString())));
        } catch (NumberFormatException | ParseException e) {
            enviarMensagemErro(e.getMessage());
        }
    }
}
