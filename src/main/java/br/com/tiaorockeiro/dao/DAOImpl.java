/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Wendel
 * @param <T>
 * @param <Long>
 */
public class DAOImpl<T, Long> implements DAO<T, Long> {

    private final EntityManager entityManager;

    public DAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public T salvar(T entidade) {
        return this.entityManager.merge(entidade);
    }

    @Override
    public void remover(T entidade) {
        this.entityManager.remove(entidade);
    }

    @Override
    public T obterPorId(Class<T> classe, Long id) {
        return this.entityManager.find(classe, id);
    }

    @Override
    public List<T> listarTodos(Class<T> classe) {
        return this.entityManager.createQuery("SELECT o FROM " + classe.getSimpleName() + " o").getResultList();
    }
}
