/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.AdicionalProduto;
import java.util.List;

/**
 *
 * @author INLOC01
 */
public interface AdicionalProdutoDAO extends DAO<AdicionalProduto, Long> {

    public List<AdicionalProduto> obterPorIdItemPedido(Long idItemPedido);

}
