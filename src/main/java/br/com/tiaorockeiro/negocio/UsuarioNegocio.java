/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.negocio;

import br.com.tiaorockeiro.dao.UsuarioDAO;
import br.com.tiaorockeiro.dao.UsuarioDAOImpl;
import br.com.tiaorockeiro.modelo.Usuario;
import static br.com.tiaorockeiro.util.JpaUtil.criaEntityManager;
import javax.persistence.EntityManager;

/**
 *
 * @author Wendel
 */
public class UsuarioNegocio extends NegocioImpl<Usuario, Long> {
    
    public Usuario procurarPorDescricao(String descricao) throws Exception {
        EntityManager entityManager = criaEntityManager();
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAOImpl(entityManager);
            return usuarioDAO.procurarPorDescricao(descricao);
        } finally {
            entityManager.close();
        }
    }
}
