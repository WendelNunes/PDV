/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.ItemVenda;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Wendel
 */
public class ItemVendaDAOImpl extends DAOImpl<ItemVenda, Long> implements ItemVendaDAO {

    private final EntityManager entityManager;

    public ItemVendaDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<ItemVenda> listarPorIdVenda(Long idVenda) {
        Query query = this.entityManager.createQuery("FROM ItemVenda i WHERE i.venda.id = :idVenda");
        query.setParameter("idVenda", idVenda);
        return query.getResultList();
    }
}
