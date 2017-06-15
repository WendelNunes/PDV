/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.negocio;

import br.com.tiaorockeiro.dao.DAO;
import br.com.tiaorockeiro.dao.DAOImpl;
import static br.com.tiaorockeiro.util.JpaUtil.criaEntityManager;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Wendel
 * @param <T>
 * @param <Long>
 */
public class NegocioImpl<T, Long> implements Negocio<T, Long> {

    @Override
    public T salvar(T entidade) {
        EntityManager entityManager = null;
        try {
            entityManager = criaEntityManager();
            entityManager.getTransaction().begin();
            entidade = new DAOImpl<T, Long>(entityManager).salvar(entidade);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return entidade;
    }

    @Override
    @SuppressWarnings("UnusedAssignment")
    public void salvar(List<T> entidades) {
        EntityManager entityManager = null;
        try {
            entityManager = criaEntityManager();
            entityManager.getTransaction().begin();
            DAO<T, Long> dao = new DAOImpl<>(entityManager);
            entidades.stream().forEach(entidade -> {
                entidade = dao.salvar(entidade);
            });
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public void remover(T entidade) {
        EntityManager entityManager = null;
        try {
            entityManager = criaEntityManager();
            entityManager.getTransaction().begin();
            new DAOImpl<>(entityManager).remover(entidade);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public void remover(List<T> entidades) {
        EntityManager entityManager = null;
        try {
            entityManager = criaEntityManager();
            entityManager.getTransaction().begin();
            DAO<T, Long> dao = new DAOImpl<>(entityManager);
            entidades.stream().forEach((entidade) -> {
                dao.remover(entidade);
            });
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            if (entityManager != null) {
                entityManager.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public T obterPorId(Class<T> classe, Long id) {
        EntityManager entityManager = null;
        try {
            entityManager = criaEntityManager();
            return new DAOImpl<T, Long>(entityManager).obterPorId(classe, id);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public List<T> listarTodos(Class<T> classe) {
        EntityManager entityManager = null;
        try {
            entityManager = criaEntityManager();
            return new DAOImpl<T, Long>(entityManager).listarTodos(classe);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
