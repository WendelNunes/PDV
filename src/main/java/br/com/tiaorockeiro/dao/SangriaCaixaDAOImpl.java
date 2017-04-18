/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.SangriaCaixa;
import javax.persistence.EntityManager;

/**
 *
 * @author INLOC01
 */
public class SangriaCaixaDAOImpl extends DAOImpl<SangriaCaixa, Long> implements SangriaCaixaDAO {

    public SangriaCaixaDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
