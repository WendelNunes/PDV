/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.Venda;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Wendel
 */
public interface VendaDAO extends DAO<Venda, Long> {

    public Integer quantidadeRegistroConsultaVenda(Date periodoInicial, Date periodoFinal, Integer idUsuario, Integer idCaixa, Integer mesa,
            boolean ativa, boolean cancelada);

    public List<Object[]> listaConsultaVenda(Date periodoInicial, Date periodoFinal, Integer idUsuario, Integer idCaixa, Integer mesa,
            boolean ativa, boolean cancelada, Integer qtdeRegistro, Integer pagina);
}
