/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.UnidadeProduto;
import javax.persistence.EntityManager;

/**
 *
 * @author INLOC01
 */
public class UnidadeProdutoDAOImpl extends DAOImpl<UnidadeProduto, Long> implements UnidadeProdutoDAO {

    public UnidadeProdutoDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
