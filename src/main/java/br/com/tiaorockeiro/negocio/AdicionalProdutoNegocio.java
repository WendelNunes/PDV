/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.negocio;

import br.com.tiaorockeiro.dao.AdicionalProdutoDAO;
import br.com.tiaorockeiro.dao.AdicionalProdutoDAOImpl;
import br.com.tiaorockeiro.modelo.AdicionalProduto;
import static br.com.tiaorockeiro.util.JpaUtil.criaEntityManager;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Wendel
 */
public class AdicionalProdutoNegocio extends NegocioImpl<AdicionalProduto, Long> {

    public List<AdicionalProduto> obterPorIdItemPedido(Long idItemPedido) {
        EntityManager entityManager = criaEntityManager();
        try {
            AdicionalProdutoDAO adicionalProdutoDAO = new AdicionalProdutoDAOImpl(entityManager);
            return adicionalProdutoDAO.obterPorIdItemPedido(idItemPedido);
        } finally {
            entityManager.close();
        }
    }
}
