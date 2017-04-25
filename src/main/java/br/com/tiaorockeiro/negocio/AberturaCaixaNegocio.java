/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.negocio;

import br.com.tiaorockeiro.dao.AberturaCaixaDAO;
import br.com.tiaorockeiro.dao.AberturaCaixaDAOImpl;
import br.com.tiaorockeiro.modelo.AberturaCaixa;
import br.com.tiaorockeiro.modelo.Caixa;
import static br.com.tiaorockeiro.util.JpaUtil.criaEntityManager;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;

/**
 *
 * @author INLOC01
 */
public class AberturaCaixaNegocio extends NegocioImpl<AberturaCaixa, Long> {

    public List<AberturaCaixa> listarAbertos() throws Exception {
        EntityManager entityManager = criaEntityManager();
        try {
            AberturaCaixaDAO aberturaCaixaDAO = new AberturaCaixaDAOImpl(entityManager);
            return aberturaCaixaDAO.listarAbertos();
        } finally {
            entityManager.close();
        }
    }

    public AberturaCaixa obterAbertoPorCaixa(Caixa caixa) throws Exception {
        List<AberturaCaixa> caixasAbertos = this.listarAbertos();
        List<AberturaCaixa> abertura = caixasAbertos.stream().filter(ac -> ac.getCaixa().equals(caixa)).collect(Collectors.toList());
        return abertura.isEmpty() ? null : abertura.get(0);
    }
}
