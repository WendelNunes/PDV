/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.negocio;

import br.com.tiaorockeiro.dao.PagamentoDAO;
import br.com.tiaorockeiro.dao.PagamentoDAOImpl;
import br.com.tiaorockeiro.modelo.Pagamento;
import static br.com.tiaorockeiro.util.JpaUtil.criaEntityManager;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Wendel
 */
public class PagamentoNegocio extends NegocioImpl<Pagamento, Long> {

    public List<Pagamento> listarPorIdVenda(Long idVenda) {
        EntityManager entityManager = criaEntityManager();
        try {
            PagamentoDAO pagamentoDAO = new PagamentoDAOImpl(entityManager);
            return pagamentoDAO.listarPorIdVenda(idVenda);
        } finally {
            entityManager.close();
        }
    }
}
