/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import static br.com.tiaorockeiro.util.QuantidadeUtil.formataQuantidade;
import java.math.BigDecimal;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Wendel
 */
public class ListCellProduto {

    private final String descProduto;
    private final BigDecimal vlrUnitarioProduto;
    private final BigDecimal qtdeProduto;
    private final BigDecimal vlrAdicionais;
    private final BigDecimal vlrTotal;

    public ListCellProduto(String descProduto, BigDecimal vlrUnitarioProduto, BigDecimal qtdeProduto, BigDecimal vlrAdicionais, BigDecimal vlrTotal) {
        this.descProduto = descProduto;
        this.vlrUnitarioProduto = vlrUnitarioProduto;
        this.qtdeProduto = qtdeProduto;
        this.vlrAdicionais = vlrAdicionais;
        this.vlrTotal = vlrTotal;
    }

    public VBox getCell() throws Exception {
        VBox vBox = new VBox();
        vBox.setFillWidth(true);

        AnchorPane paneProduto = new AnchorPane();
        Label lbDescricaoProduto = new Label(this.descProduto);
        AnchorPane.setLeftAnchor(lbDescricaoProduto, 5.0);
        AnchorPane.setTopAnchor(lbDescricaoProduto, 5.0);
        AnchorPane.setRightAnchor(lbDescricaoProduto, 170.0);
        paneProduto.getChildren().add(lbDescricaoProduto);
        Label lbQtdeProduto = new Label(formataQuantidade(this.qtdeProduto) + " x");
        lbQtdeProduto.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setTopAnchor(lbQtdeProduto, 5.0);
        AnchorPane.setRightAnchor(lbQtdeProduto, 90.0);
        paneProduto.getChildren().add(lbQtdeProduto);
        Label lbVlrUnitarioProduto = new Label(formataQuantidade(this.vlrUnitarioProduto));
        lbVlrUnitarioProduto.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setTopAnchor(lbVlrUnitarioProduto, 5.0);
        AnchorPane.setRightAnchor(lbVlrUnitarioProduto, 10.0);
        paneProduto.getChildren().add(lbVlrUnitarioProduto);
        vBox.getChildren().add(paneProduto);

        if (this.vlrAdicionais != null) {
            AnchorPane paneAdicionais = new AnchorPane();
            Label lbAdicionais = new Label("Adicionais");
            lbAdicionais.setAlignment(Pos.CENTER_RIGHT);
            lbAdicionais.getStyleClass().add("labelListNegrito");
            AnchorPane.setLeftAnchor(lbAdicionais, 10.0);
            AnchorPane.setTopAnchor(lbAdicionais, 5.0);
            AnchorPane.setRightAnchor(lbAdicionais, 170.0);
            paneAdicionais.getChildren().add(lbAdicionais);
            Label lbVlrAdiconais = new Label(formataQuantidade(this.vlrAdicionais));
            lbVlrAdiconais.setAlignment(Pos.CENTER_RIGHT);
            AnchorPane.setTopAnchor(lbVlrAdiconais, 5.0);
            AnchorPane.setRightAnchor(lbVlrAdiconais, 10.0);
            paneAdicionais.getChildren().add(lbVlrAdiconais);
            vBox.getChildren().add(paneAdicionais);
        }

        AnchorPane paneTotal = new AnchorPane();
        Label lbTotal = new Label("Total");
        lbTotal.setAlignment(Pos.CENTER_RIGHT);
        lbTotal.getStyleClass().add("labelAdicional");
        AnchorPane.setLeftAnchor(lbTotal, 10.0);
        AnchorPane.setTopAnchor(lbTotal, 5.0);
        AnchorPane.setRightAnchor(lbTotal, 170.0);
        paneTotal.getChildren().add(lbTotal);
        Label lbVlrTotal = new Label(formataQuantidade(this.vlrTotal));
        lbVlrTotal.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setTopAnchor(lbVlrTotal, 5.0);
        AnchorPane.setRightAnchor(lbVlrTotal, 10.0);
        paneTotal.getChildren().add(lbVlrTotal);
        vBox.getChildren().add(paneTotal);
        return vBox;
    }
}
