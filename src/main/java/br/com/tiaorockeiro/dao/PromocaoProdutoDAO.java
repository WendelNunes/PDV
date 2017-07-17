/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.PromocaoProduto;
import java.util.List;
import java.util.Map;

/**
 *
 * @author INLOC01
 */
public interface PromocaoProdutoDAO extends DAO<PromocaoProduto, Long> {

    public List<Map<String, Object>> procuraPromocaoPorProduto(List<Long> idProdutos);
}
