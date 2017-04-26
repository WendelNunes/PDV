/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.Produto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author INLOC01
 */
public class ProdutoDAOImpl extends DAOImpl<Produto, Long> implements ProdutoDAO {

    private final EntityManager entityManager;

    public ProdutoDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<Produto> listarPorCategoria(Long idCategoria) {
        Query query = this.entityManager.createQuery("FROM Produto p WHERE p.categoriaProduto.id = :idCategoriaProduto");
        query.setParameter("idCategoriaProduto", idCategoria);
        return query.getResultList();
    }
}
