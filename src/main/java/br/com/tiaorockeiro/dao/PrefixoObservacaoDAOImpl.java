/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.PrefixoObservacao;
import javax.persistence.EntityManager;

/**
 *
 * @author INLOC01
 */
public class PrefixoObservacaoDAOImpl extends DAOImpl<PrefixoObservacao, Long> implements PrefixoObservacaoDAO {

    public PrefixoObservacaoDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
