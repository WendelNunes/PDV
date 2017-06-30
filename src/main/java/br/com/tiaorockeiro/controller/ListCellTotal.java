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

/**
 *
 * @author INLOC01
 */
public class ListCellTotal {

    private final String descPagamento;
    private final BigDecimal vlrPagamento;

    public ListCellTotal(String descPagamento, BigDecimal vlrPagamento) {
        this.descPagamento = descPagamento;
        this.vlrPagamento = vlrPagamento;
    }

    public AnchorPane getCell() throws Exception {
        AnchorPane pane = new AnchorPane();
        pane.setPrefHeight(50.0);
        Label lbDescricao = new Label(this.descPagamento);
        lbDescricao.setPrefHeight(30.0);
        lbDescricao.getStyleClass().add("labelListNegrito");
        AnchorPane.setLeftAnchor(lbDescricao, 5.0);
        AnchorPane.setTopAnchor(lbDescricao, 5.0);
//        AnchorPane.setRightAnchor(lbDescricao, 170.0);
        pane.getChildren().add(lbDescricao);
        Label lbVlrPagamento = new Label(formataQuantidade(this.vlrPagamento));
        lbVlrPagamento.setPrefHeight(30.0);
        lbVlrPagamento.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setTopAnchor(lbVlrPagamento, 5.0);
        AnchorPane.setRightAnchor(lbVlrPagamento, 5.0);
        pane.getChildren().add(lbVlrPagamento);
        return pane;
    }
}
