/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.negocio;

import br.com.tiaorockeiro.dao.ProdutoDAO;
import br.com.tiaorockeiro.dao.ProdutoDAOImpl;
import br.com.tiaorockeiro.modelo.Produto;
import static br.com.tiaorockeiro.util.JpaUtil.criaEntityManager;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author INLOC01
 */
public class ProdutoNegocio extends NegocioImpl<Produto, Long> {

    public List<Produto> listarPorCategoria(Long idCategoria) throws Exception {
        EntityManager entityManager = criaEntityManager();
        try {
            ProdutoDAO produtoDAO = new ProdutoDAOImpl(entityManager);
            return produtoDAO.listarPorCategoria(idCategoria);
        } finally {
            entityManager.close();
        }
    }

    public List<Produto> listarAdicionais() throws Exception {
        EntityManager entityManager = criaEntityManager();
        try {
            ProdutoDAO produtoDAO = new ProdutoDAOImpl(entityManager);
            return produtoDAO.listarAdicionais();
        } finally {
            entityManager.close();
        }
    }
}
