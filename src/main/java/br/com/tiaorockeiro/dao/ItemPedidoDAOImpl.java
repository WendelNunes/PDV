/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.ItemPedido;
import javax.persistence.EntityManager;

/**
 *
 * @author Wendel
 */
public class ItemPedidoDAOImpl extends DAOImpl<ItemPedido, Long> implements ItemPedidoDAO {

    public ItemPedidoDAOImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
