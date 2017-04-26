/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.Impressora;
import javax.persistence.EntityManager;

/**
 *
 * @author INLOC01
 */
public class ImpressoraDAOImpl extends DAOImpl<Impressora, Long> implements ImpressoraDAO {

    public ImpressoraDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
