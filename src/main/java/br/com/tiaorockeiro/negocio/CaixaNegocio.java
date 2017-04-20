/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.negocio;

import br.com.tiaorockeiro.dao.CaixaDAO;
import br.com.tiaorockeiro.dao.CaixaDAOImpl;
import br.com.tiaorockeiro.modelo.Caixa;
import static br.com.tiaorockeiro.util.JpaUtil.criaEntityManager;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author INLOC01
 */
public class CaixaNegocio extends NegocioImpl<Caixa, Long> {

    public List<Caixa> obterCaixasSemAbertura() throws Exception {
        EntityManager entityManager = criaEntityManager();
        try {
            CaixaDAO caixaDAO = new CaixaDAOImpl(entityManager);
            return caixaDAO.obterCaixasSemAbertura();
        } finally {
            entityManager.close();
        }
    }
}
