/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.Configuracao;
import javax.persistence.EntityManager;

/**
 *
 * @author Wendel
 */
public class ConfiguracaoDAOImpl extends DAOImpl<Configuracao, Long> implements ConfiguracaoDAO {

    private final EntityManager entityManager;

    public ConfiguracaoDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Configuracao obterConfiguracao() {
        return (Configuracao) this.entityManager.createQuery("FROM Configuracao c").getSingleResult();
    }
}
