/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.AdicionalProduto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Wendel
 */
public class AdicionalProdutoDAOImpl extends DAOImpl<AdicionalProduto, Long> implements AdicionalProdutoDAO {

    private final EntityManager entityManager;

    public AdicionalProdutoDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<AdicionalProduto> obterPorIdItemPedido(Long idItemPedido) {
        Query query = this.entityManager.createQuery("FROM AdicionalProduto ap WHERE ap.itemPedido.id = :idItemPedido");
        query.setParameter("idItemPedido", idItemPedido);
        return query.getResultList();
    }
}
