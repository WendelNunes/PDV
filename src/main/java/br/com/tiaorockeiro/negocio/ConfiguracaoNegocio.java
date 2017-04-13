/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.negocio;

import br.com.tiaorockeiro.dao.ConfiguracaoDAO;
import br.com.tiaorockeiro.dao.ConfiguracaoDAOImpl;
import br.com.tiaorockeiro.modelo.Configuracao;
import br.com.tiaorockeiro.util.JpaUtil;
import javax.persistence.EntityManager;

/**
 *
 * @author Wendel
 */
public class ConfiguracaoNegocio extends NegocioImpl<Configuracao, Long> {

    public Configuracao obterConfiguracao() {
        EntityManager entityManager = JpaUtil.criaEntityManager();
        try {
            ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAOImpl(entityManager);
            return configuracaoDAO.obterConfiguracao();
        } finally {
            entityManager.close();
        }
    }
}
