/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.controller;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Wendel
 */
public class ListCellInformacao {

    private final String descInformacao;
    private final String texto;

    public ListCellInformacao(String descInformacao, String texto) {
        this.descInformacao = descInformacao;
        this.texto = texto;
    }

    public AnchorPane getCell() throws Exception {
        AnchorPane pane = new AnchorPane();
        Label lbDescricao = new Label(this.descInformacao);
        lbDescricao.getStyleClass().add("labelListNegrito");
        AnchorPane.setLeftAnchor(lbDescricao, 5.0);
        AnchorPane.setTopAnchor(lbDescricao, 5.0);
        pane.getChildren().add(lbDescricao);
        Label lbTexto = new Label(this.texto);
        lbTexto.setAlignment(Pos.CENTER_RIGHT);
        AnchorPane.setTopAnchor(lbTexto, 5.0);
        AnchorPane.setRightAnchor(lbTexto, 5.0);
        pane.getChildren().add(lbTexto);
        return pane;
    }

}
