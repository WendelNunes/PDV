/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.PromocaoProduto;
import javax.persistence.EntityManager;

/**
 *
 * @author INLOC01
 */
public class PromocaoProdutoDAOImpl extends DAOImpl<PromocaoProduto, Long> implements PromocaoProdutoDAO {

    public PromocaoProdutoDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
