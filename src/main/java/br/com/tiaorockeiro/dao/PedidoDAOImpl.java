/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.Pedido;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Wendel
 */
public class PedidoDAOImpl extends DAOImpl<Pedido, Long> implements PedidoDAO {

    private final EntityManager entityManager;

    public PedidoDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Pedido obterAbertoPorMesa(Integer mesa) {
        Query query = this.entityManager.createQuery("SELECT p FROM Pedido p LEFT JOIN p.venda v "
                + "WHERE p.dataHoraCancelamento IS NULL AND p.mesa = :mesa AND v IS NULL");
        query.setParameter("mesa", mesa);
        List<Pedido> result = query.getResultList();
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<Pedido> obterAbertos() {
        return this.entityManager.createQuery("SELECT p FROM Pedido p LEFT JOIN p.venda v "
                + "WHERE p.dataHoraCancelamento IS NULL AND v IS NULL").getResultList();
    }
}
