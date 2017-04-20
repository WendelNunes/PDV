/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.AberturaCaixa;
import br.com.tiaorockeiro.modelo.FechamentoCaixa;
import br.com.tiaorockeiro.negocio.AberturaCaixaNegocio;
import br.com.tiaorockeiro.negocio.FechamentoCaixaNegocio;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemConfirmacao;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemErro;
import static br.com.tiaorockeiro.util.MensagemUtil.enviarMensagemInformacao;
import br.com.tiaorockeiro.util.SessaoUtil;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
public class TelaFechamentoCaixaController implements Initializable {

    @FXML
    private ComboBox<AberturaCaixa> cbCaixa;
    @FXML
    private TextField inputDataFechamento;
    @FXML
    private TextField inputHoraFechamento;
    @FXML
    private TextField inputDataAbertura;
    @FXML
    private TextField inputHoraAbertura;
    @FXML
    private TextField inputSaldoFinal;
    private DecimalFormat formatadorValor;
    private Date dataHoraFechamentoCaixa;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.dataHoraFechamentoCaixa = new Date();
            this.formatadorValor = new DecimalFormat("#,##0.00");
            this.inputSaldoFinal.setText(this.formatadorValor.format(0.0));
            this.inputDataFechamento.setText(new SimpleDateFormat("dd/MM/yyyy").format(this.dataHoraFechamentoCaixa));
            this.inputHoraFechamento.setText(new SimpleDateFormat("HH:mm:ss").format(this.dataHoraFechamentoCaixa));
            this.cbCaixa.getItems().add(null);
            this.cbCaixa.getItems().addAll(FXCollections.observableList(new AberturaCaixaNegocio().listarAbertos()));
            this.cbCaixa.setCellFactory((ListView<AberturaCaixa> param) -> new ListCell<AberturaCaixa>() {
                @Override
                protected void updateItem(AberturaCaixa item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.getCaixa().getCodigo() + " - " + item.getCaixa().getDescricao());
                    } else {
                        setText(null);
                    }
                }
            });
            this.cbCaixa.setConverter(new StringConverter<AberturaCaixa>() {
                @Override
                public String toString(AberturaCaixa aberturaCaixa) {
                    return aberturaCaixa != null ? aberturaCaixa.getCaixa().getCodigo() + " - " + aberturaCaixa.getCaixa().getDescricao() : null;
                }

                @Override
                public AberturaCaixa fromString(String string) {
                    return null;
                }
            });
            this.cbCaixa.valueProperty().addListener((ObservableValue<? extends AberturaCaixa> observable, AberturaCaixa oldValue, AberturaCaixa newValue) -> {
                if (newValue != null) {
                    this.inputDataAbertura.setText(new SimpleDateFormat("dd/MM/yyyy").format(newValue.getDataHora()));
                    this.inputHoraAbertura.setText(new SimpleDateFormat("HH:mm:ss").format(newValue.getDataHora()));
                } else {
                    this.inputDataAbertura.setText(null);
                    this.inputHoraAbertura.setText(null);
                }
            }
            );
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoBotao1(ActionEvent event
    ) {
        this.adicionaNumeroSaldoFinal(1);
    }

    @FXML
    public void acaoBotao2(ActionEvent event
    ) {
        this.adicionaNumeroSaldoFinal(2);
    }

    @FXML
    public void acaoBotao3(ActionEvent event
    ) {
        this.adicionaNumeroSaldoFinal(3);
    }

    @FXML
    public void acaoBotao4(ActionEvent event
    ) {
        this.adicionaNumeroSaldoFinal(4);
    }

    @FXML
    public void acaoBotao5(ActionEvent event
    ) {
        this.adicionaNumeroSaldoFinal(5);
    }

    @FXML
    public void acaoBotao6(ActionEvent event
    ) {
        this.adicionaNumeroSaldoFinal(6);
    }

    @FXML
    public void acaoBotao7(ActionEvent event
    ) {
        this.adicionaNumeroSaldoFinal(7);
    }

    @FXML
    public void acaoBotao8(ActionEvent event
    ) {
        this.adicionaNumeroSaldoFinal(8);
    }

    @FXML
    public void acaoBotao9(ActionEvent event
    ) {
        this.adicionaNumeroSaldoFinal(9);
    }

    @FXML
    public void acaoBotao0(ActionEvent event
    ) {
        this.adicionaNumeroSaldoFinal(0);
    }

    @FXML
    public void acaoBotaoLimparSaldoFinal(ActionEvent event
    ) {
        this.inputSaldoFinal.setText(this.formatadorValor.format(0.0));
    }

    @FXML
    public void acaoBotaoFecharCaixa(ActionEvent event
    ) {
        try {
            if (this.cbCaixa.getValue() == null) {
                enviarMensagemInformacao("Caixa deve ser informado!");
            } else {
                FechamentoCaixa fechamentoCaixa = new FechamentoCaixa();
                fechamentoCaixa.setAberturaCaixa(this.cbCaixa.getValue());
                fechamentoCaixa.setDataHora(this.dataHoraFechamentoCaixa);
                fechamentoCaixa.setSaldoFinal(new BigDecimal(this.inputSaldoFinal.getText().replaceAll("\\.", "").replaceAll(",", ".")));
                fechamentoCaixa.setUsuario(SessaoUtil.getUsuario());
                new FechamentoCaixaNegocio().salvar(fechamentoCaixa);
                this.acaoBotaoVoltar(null);
                enviarMensagemConfirmacao("Caixa fechado com sucesso!");
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoBotaoVoltar(ActionEvent event
    ) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaMesas.fxml"));
            AnchorPane telaMesas = loader.load();
            TelaPrincipalController.getInstance().mudaTela(telaMesas);
        } catch (IOException | NumberFormatException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    private void adicionaNumeroSaldoFinal(Integer numero) {
        try {
            StringBuilder texto = new StringBuilder(this.inputSaldoFinal.getText().replaceAll(",", "")
                    .replaceAll("\\.", ""));
            texto.append(numero);
            texto.insert(texto.length() - 2, ".");
            this.inputSaldoFinal.setText(this.formatadorValor.format(new BigDecimal(texto.toString())));
        } catch (NumberFormatException e) {
            enviarMensagemErro(e.getMessage());
        }
    }
}
