/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.Pessoa;
import javax.persistence.EntityManager;

/**
 *
 * @author Wendel
 */
public class PessoaDAOImpl extends DAOImpl<Pessoa, Long> implements PessoaDAO {

    public PessoaDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
