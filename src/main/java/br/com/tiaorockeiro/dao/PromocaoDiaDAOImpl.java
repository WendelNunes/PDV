/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import javax.persistence.EntityManager;

/**
 *
 * @author INLOC01
 */
public class PromocaoDiaDAOImpl extends DAOImpl<PromocaoDiaDAO, Long> implements PromocaoDiaDAO {

    public PromocaoDiaDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
