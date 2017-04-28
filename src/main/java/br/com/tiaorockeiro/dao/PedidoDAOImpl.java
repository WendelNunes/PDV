/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.Pedido;
import javax.persistence.EntityManager;

/**
 *
 * @author Wendel
 */
public class PedidoDAOImpl extends DAOImpl<Pedido, Long> implements PedidoDAO {

    public PedidoDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
