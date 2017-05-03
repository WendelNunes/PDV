/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.negocio;

import br.com.tiaorockeiro.dao.PedidoDAO;
import br.com.tiaorockeiro.dao.PedidoDAOImpl;
import br.com.tiaorockeiro.modelo.Pedido;
import static br.com.tiaorockeiro.util.JpaUtil.criaEntityManager;
import javax.persistence.EntityManager;

/**
 *
 * @author Wendel
 */
public class PedidoNegocio extends NegocioImpl<Pedido, Long> {

    public Pedido obterAbertoPorMesa(Integer mesa) {
        EntityManager entityManager = criaEntityManager();
        try {
            PedidoDAO pedidoDAO = new PedidoDAOImpl(entityManager);
            return pedidoDAO.obterAbertoPorMesa(mesa);
        } finally {
            entityManager.close();
        }
    }
}
