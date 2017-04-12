/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Wendel
 */
public class UsuarioDAOImpl extends DAOImpl<Usuario, Long> implements UsuarioDAO {

    private final EntityManager entityManager;

    public UsuarioDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Usuario procurarPorDescricao(String descricao) {
        Query query = this.entityManager.createQuery("FROM Usuario u WHERE u.descricao = :descricao");
        query.setParameter("descricao", descricao);
        return (Usuario) query.getSingleResult();
    }
}
