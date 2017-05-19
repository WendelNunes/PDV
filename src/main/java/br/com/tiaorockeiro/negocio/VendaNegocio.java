/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.negocio;

import br.com.tiaorockeiro.dao.VendaDAO;
import br.com.tiaorockeiro.dao.VendaDAOImpl;
import br.com.tiaorockeiro.modelo.Venda;
import static br.com.tiaorockeiro.util.JpaUtil.criaEntityManager;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author INLOC01
 */
public class VendaNegocio extends NegocioImpl<Venda, Long> {

    public Integer quantidadeRegistroConsultaVenda(Date periodoInicial, Date periodoFinal, Long idUsuario, Long idCaixa, Integer mesa, boolean ativa, boolean cancelada) {
        EntityManager entityManager = criaEntityManager();
        try {
            VendaDAO vendaDAO = new VendaDAOImpl(entityManager);
            return vendaDAO.quantidadeRegistroConsultaVenda(periodoInicial, periodoFinal, idUsuario, idCaixa, mesa, ativa, cancelada);
        } finally {
            entityManager.close();
        }
    }

    public List<Object[]> listaConsultaVenda(Date periodoInicial, Date periodoFinal, Long idUsuario, Long idCaixa, Integer mesa,
            boolean ativa, boolean cancelada, Integer qtdeRegistro, Integer pagina) {
        EntityManager entityManager = criaEntityManager();
        try {
            VendaDAO vendaDAO = new VendaDAOImpl(entityManager);
            return vendaDAO.listaConsultaVenda(periodoInicial, periodoFinal, idUsuario, idCaixa, mesa, ativa, cancelada, qtdeRegistro, pagina);
        } finally {
            entityManager.close();
        }
    }
}
