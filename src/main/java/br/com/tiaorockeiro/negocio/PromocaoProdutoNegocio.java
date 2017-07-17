/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.negocio;

import br.com.tiaorockeiro.dao.PromocaoProdutoDAO;
import br.com.tiaorockeiro.dao.PromocaoProdutoDAOImpl;
import br.com.tiaorockeiro.modelo.PromocaoProduto;
import static br.com.tiaorockeiro.util.JpaUtil.criaEntityManager;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author INLOC01
 */
public class PromocaoProdutoNegocio extends NegocioImpl<PromocaoProduto, Long> {

    public List<Map<String, Object>> procuraPromocaoPorProduto(List<Long> idProdutos) {
        EntityManager entityManager = criaEntityManager();
        try {
            PromocaoProdutoDAO produtoDAO = new PromocaoProdutoDAOImpl(entityManager);
            return produtoDAO.procuraPromocaoPorProduto(idProdutos);
        } finally {
            entityManager.close();
        }
    }
}
