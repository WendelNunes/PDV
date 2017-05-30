/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.Pagamento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Wendel
 */
public class PagamentoDAOImpl extends DAOImpl<Pagamento, Long> implements PagamentoDAO {

    private final EntityManager entityManager;

    public PagamentoDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<Pagamento> listarPorIdVenda(Long idVenda) {
        Query query = this.entityManager.createQuery("FROM Pagamento i WHERE i.venda.id = :idVenda");
        query.setParameter("idVenda", idVenda);
        return query.getResultList();
    }
}
