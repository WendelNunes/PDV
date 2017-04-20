/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.AberturaCaixa;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author INLOC01
 */
public class AberturaCaixaDAOImpl extends DAOImpl<AberturaCaixa, Long> implements AberturaCaixaDAO {

    private final EntityManager entityManager;

    public AberturaCaixaDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<AberturaCaixa> listarAbertos() {
        return this.entityManager.createQuery("SELECT a FROM AberturaCaixa a LEFT JOIN a.fechamentoCaixa f WHERE f IS NULL").getResultList();
    }
}
