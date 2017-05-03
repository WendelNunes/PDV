/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.Venda;
import javax.persistence.EntityManager;

/**
 *
 * @author Wendel
 */
public class VendaDAOImpl extends DAOImpl<Venda, Long> implements VendaDAO {

    public VendaDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
