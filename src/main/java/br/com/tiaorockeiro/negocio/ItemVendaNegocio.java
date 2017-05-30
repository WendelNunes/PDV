/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.negocio;

import br.com.tiaorockeiro.dao.ItemVendaDAO;
import br.com.tiaorockeiro.dao.ItemVendaDAOImpl;
import br.com.tiaorockeiro.modelo.ItemVenda;
import static br.com.tiaorockeiro.util.JpaUtil.criaEntityManager;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Wendel
 */
public class ItemVendaNegocio extends NegocioImpl<ItemVenda, Long> {

    public List<ItemVenda> listarPorIdVenda(Long idVenda) {
        EntityManager entityManager = criaEntityManager();
        try {
            ItemVendaDAO itemVendaDAO = new ItemVendaDAOImpl(entityManager);
            return itemVendaDAO.listarPorIdVenda(idVenda);
        } finally {
            entityManager.close();
        }
    }
}
