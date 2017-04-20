/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.Caixa;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author INLOC01
 */
public class CaixaDAOImpl extends DAOImpl<Caixa, Long> implements CaixaDAO {

    private final EntityManager entityManager;

    public CaixaDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<Caixa> obterCaixasSemAbertura() {
        return this.entityManager.createQuery("SELECT c "
                + "                              FROM Caixa c "
                + "                             WHERE NOT EXISTS(SELECT c "
                + "                                                FROM AberturaCaixa a "
                + "                                           LEFT JOIN a.fechamentoCaixa f "
                + "                                               WHERE a.caixa = c"
                + "                                                 AND f IS NULL) ").getResultList();
    }
}
