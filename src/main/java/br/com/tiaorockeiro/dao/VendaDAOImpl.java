/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.Venda;
import java.util.Date;
import java.util.List;
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
            sql.append("       AND v.data_hora_cancelamento ").append(ativa ? "ISNULL" : "NOTNULL");
        }
        Query query = this.entityManager.createNativeQuery(sql.toString());
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
    public List<Object[]> listaConsultaVenda(Date periodoInicial, Date periodoFinal, Integer idUsuario, Integer idCaixa, Integer mesa,
            boolean ativa, boolean cancelada, Integer qtdeRegistro, Integer pagina) {
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT v.id_venda,\n");
        sql.append("           v.mesa,\n");
        sql.append("           v.data_hora,\n");
        sql.append("           u.descricao,\n");
        sql.append("           c.descricao,\n");
        sql.append("           v.data_hora_cancelamento,\n");
        sql.append("      FROM venda v\n");
        sql.append("INNER JOIN usuario u ON u.id = v.id_usuario\n");
        sql.append("INNER JOIN abertura_caixa ac ON ac.id = v.id_abertura_caixa\n");
        sql.append("INNER JOIN caixa c ON c.id = ac.id_caixa\n");
        sql.append("     WHERE v.data_hora BETWEEN :periodoInicial AND :periodoFinal\n");
        if (idUsuario != null) {
            sql.append("       AND u.id_usuario = :idUsuario");
        }
        if (idCaixa != null) {
            sql.append("       AND c.id_caixa = :idCaixa");
        }
        if (mesa != null) {
            sql.append("       AND v.mesa = :mesa");
        }
        if (!(ativa && cancelada) || !(!ativa && !cancelada)) {
            sql.append("       AND v.data_hora_cancelamento ").append(ativa ? "ISNULL" : "NOTNULL");
        }
        sql.append("  ORDER BY v.data_hora\n");
        sql.append("     LIMIT :qtdeRegistro");
        sql.append("    OFFSET (:pagina-1) * :qtdeRegistro");
        Query query = this.entityManager.createNativeQuery(sql.toString());
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
        query.setParameter(":qtdeRegistro", pagina);
        query.setParameter(":pagina", pagina);
        return query.getResultList();
    }
}
