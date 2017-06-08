/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.ObservacaoProduto;
import javax.persistence.EntityManager;

/**
 *
 * @author INLOC01
 */
public class ObservacaoProdutoDAOImpl extends DAOImpl<ObservacaoProduto, Long> implements ObservacaoProdutoDAO {

    public ObservacaoProdutoDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
