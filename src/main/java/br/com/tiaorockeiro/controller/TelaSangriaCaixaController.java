/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import br.com.tiaorockeiro.modelo.AberturaCaixa;
import br.com.tiaorockeiro.modelo.SangriaCaixa;
import br.com.tiaorockeiro.negocio.AberturaCaixaNegocio;
import br.com.tiaorockeiro.negocio.SangriaCaixaNegocio;
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
public class TelaSangriaCaixaController implements Initializable {

    @FXML
    private TextField inputSaldoSangria;
    @FXML
    private TextField inputDataSangria;
    @FXML
    private TextField inputHoraSangria;
    @FXML
    private ComboBox<AberturaCaixa> cbCaixa;
    private Date dataHoraSangria;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.dataHoraSangria = new Date();
            this.inputSaldoSangria.setText(formataMoeda(BigDecimal.ZERO));
            this.inputDataSangria.setText(new SimpleDateFormat("dd/MM/yyyy").format(this.dataHoraSangria));
            this.inputHoraSangria.setText(new SimpleDateFormat("HH:mm:ss").format(this.dataHoraSangria));
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
                    this.inputDataSangria.setText(new SimpleDateFormat("dd/MM/yyyy").format(newValue.getDataHora()));
                    this.inputHoraSangria.setText(new SimpleDateFormat("HH:mm:ss").format(newValue.getDataHora()));
                } else {
                    this.inputDataSangria.setText(null);
                    this.inputHoraSangria.setText(null);
                }
            }
            );
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoBotao1(ActionEvent event) {
        this.adicionaNumeroSaldoSangria(1);
    }

    @FXML
    public void acaoBotao2(ActionEvent event) {
        this.adicionaNumeroSaldoSangria(2);
    }

    @FXML
    public void acaoBotao3(ActionEvent event) {
        this.adicionaNumeroSaldoSangria(3);
    }

    @FXML
    public void acaoBotao4(ActionEvent event) {
        this.adicionaNumeroSaldoSangria(4);
    }

    @FXML
    public void acaoBotao5(ActionEvent event) {
        this.adicionaNumeroSaldoSangria(5);
    }

    @FXML
    public void acaoBotao6(ActionEvent event) {
        this.adicionaNumeroSaldoSangria(6);
    }

    @FXML
    public void acaoBotao7(ActionEvent event) {
        this.adicionaNumeroSaldoSangria(7);
    }

    @FXML
    public void acaoBotao8(ActionEvent event) {
        this.adicionaNumeroSaldoSangria(8);
    }

    @FXML
    public void acaoBotao9(ActionEvent event) {
        this.adicionaNumeroSaldoSangria(9);
    }

    @FXML
    public void acaoBotao0(ActionEvent event) {
        this.adicionaNumeroSaldoSangria(0);
    }

    @FXML
    public void acaoBotaoLimpar(ActionEvent event) {
        this.inputSaldoSangria.setText(formataMoeda(BigDecimal.ZERO));
    }

    private void adicionaNumeroSaldoSangria(Integer numero) {
        try {
            StringBuilder texto = new StringBuilder(parseMoeda(this.inputSaldoSangria.getText()).toString().replaceAll("\\.", ""));
            texto.append(numero);
            texto.insert(texto.length() - 2, ".");
            this.inputSaldoSangria.setText(formataMoeda(new BigDecimal(texto.toString())));
        } catch (NumberFormatException | ParseException e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoFinalizarSangria(ActionEvent event) {
        try {
            if (this.cbCaixa.getValue() == null) {
                enviarMensagemInformacao("Caixa deve ser informado!");
            } else {
                SangriaCaixa sangriaCaixa = new SangriaCaixa();
                sangriaCaixa.setAberturaCaixa(this.cbCaixa.getValue());
                sangriaCaixa.setDataHora(this.dataHoraSangria);
                sangriaCaixa.setValorSangria(new BigDecimal(this.inputSaldoSangria.getText().replaceAll("\\.", "").replaceAll(",", ".")));
                sangriaCaixa.setUsuario(SessaoUtil.getUsuario());
                new SangriaCaixaNegocio().salvar(sangriaCaixa);
                this.acaoBotaoVoltar(null);
                enviarMensagemInformacao("Caixa sangrado com sucesso!");
            }
        } catch (Exception e) {
            enviarMensagemErro(e.getMessage());
        }
    }

    @FXML
    public void acaoBotaoVoltar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TelaCaixa.fxml"));
            AnchorPane tela = loader.load();
            TelaPrincipalController.getInstance().mudaTela(tela, "Caixa");
        } catch (IOException | NumberFormatException e) {
            enviarMensagemErro(e.getMessage());
        }
    }
}
