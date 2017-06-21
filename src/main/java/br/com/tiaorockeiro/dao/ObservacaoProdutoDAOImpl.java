/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.ObservacaoProduto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author INLOC01
 */
public class ObservacaoProdutoDAOImpl extends DAOImpl<ObservacaoProduto, Long> implements ObservacaoProdutoDAO {

    private final EntityManager entityManager;

    public ObservacaoProdutoDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<ObservacaoProduto> obterPorIdItemPedido(Long idItemPedido) {
        Query query = this.entityManager.createQuery("FROM ObservacaoProduto op WHERE op.itemPedido.id = :idItemPedido");
        query.setParameter("idItemPedido", idItemPedido);
        return query.getResultList();
    }
}
