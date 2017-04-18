/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.SuprimentoCaixa;
import javax.persistence.EntityManager;

/**
 *
 * @author INLOC01
 */
public class SuprimentoCaixaDAOImpl extends DAOImpl<SuprimentoCaixa, Long> implements SuprimentoCaixaDAO {

    public SuprimentoCaixaDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
