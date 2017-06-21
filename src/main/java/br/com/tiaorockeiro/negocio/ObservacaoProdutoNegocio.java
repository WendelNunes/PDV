/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.negocio;

import br.com.tiaorockeiro.dao.ObservacaoProdutoDAO;
import br.com.tiaorockeiro.dao.ObservacaoProdutoDAOImpl;
import br.com.tiaorockeiro.modelo.ObservacaoProduto;
import static br.com.tiaorockeiro.util.JpaUtil.criaEntityManager;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author INLOC01
 */
public class ObservacaoProdutoNegocio extends NegocioImpl<ObservacaoProduto, Long> {

    public List<ObservacaoProduto> obterPorIdItemPedido(Long idItemPedido) {
        EntityManager entityManager = criaEntityManager();
        try {
            ObservacaoProdutoDAO observacaoProdutoDAO = new ObservacaoProdutoDAOImpl(entityManager);
            return observacaoProdutoDAO.obterPorIdItemPedido(idItemPedido);
        } finally {
            entityManager.close();
        }
    }
}
