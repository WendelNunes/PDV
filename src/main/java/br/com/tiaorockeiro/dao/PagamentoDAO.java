/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.Pagamento;
import java.util.List;

/**
 *
 * @author Wendel
 */
public interface PagamentoDAO {

    public List<Pagamento> listarPorIdVenda(Long idVenda);
}
