/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.AdicionalProduto;
import javax.persistence.EntityManager;

/**
 *
 * @author Wendel
 */
public class AdicionalProdutoDAOImpl extends DAOImpl<AdicionalProduto, Long> implements AdicionalProdutoDAO {

    public AdicionalProdutoDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
