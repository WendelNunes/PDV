/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.AberturaCaixa;
import java.util.List;

/**
 *
 * @author INLOC01
 */
public interface AberturaCaixaDAO extends DAO<AberturaCaixa, Long> {

    public List<AberturaCaixa> listarAbertos();
}
