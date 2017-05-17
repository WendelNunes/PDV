/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.Venda;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Wendel
 */
public class VendaDAOImpl extends DAOImpl<Venda, Long> implements VendaDAO {

    private final EntityManager entityManager;

    public VendaDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Integer quantidadeRegistroConsultaVenda(Date periodoInicial, Date periodoFinal, Integer idUsuario, Integer idCaixa, Integer mesa, boolean ativa, boolean cancelada) {
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT COUNT(v.id_venda)\n");
        sql.append("      FROM venda v\n");
        sql.append("INNER JOIN abertura_caixa ac ON ac.id = v.id_abertura_caixa\n");
        sql.append("     WHERE v.data_hora BETWEEN :periodoInicial AND :periodoFinal\n");
        if (idUsuario != null) {
            sql.append("       AND v.id_usuario = :idUsuario");
        }
        if (idCaixa != null) {
            sql.append("       AND ac.id_caixa = :idCaixa");
        }
        if (mesa != null) {
            sql.append("       AND v.mesa = :mesa");
        }
        if (!(ativa && cancelada) || !(!ativa && !cancelada)) {
            sql.append("       AND data_hora_cancelamento ").append(ativa ? "ISNULL" : "NOTNULL");
        }
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("periodoInicial", periodoInicial);
        query.setParameter("periodoFinal", periodoFinal);
        if (idUsuario != null) {
            query.setParameter("idUsuario", idUsuario);
        }
        if (idCaixa != null) {
            query.setParameter("idCaixa", idCaixa);
        }
        if (mesa != null) {
            query.setParameter("mesa", mesa);
        }
        return ((Integer[]) query.getSingleResult())[0];
    }

    @Override
    public List<Map<String, Object>> listaConsultaVenda(Date periodoInicial, Date periodoFinal, Integer idUsuario, Integer idCaixa, Integer mesa, boolean ativa, boolean cancelada, Integer qtdeRegistro, Integer pagina) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
