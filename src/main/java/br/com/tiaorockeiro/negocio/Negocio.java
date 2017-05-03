/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.negocio;

import java.util.List;

/**
 *
 * @author Wendel
 * @param <T>
 * @param <Long>
 */
public interface Negocio<T, Long> {

    public T salvar(T entidade);

    public void salvar(List<T> entidade);

    public void remover(T entidade);

    public void remover(List<T> entidade);

    public T obterPorId(Class<T> classe, Long id);

    public List<T> listarTodos(Class<T> classe);
}
