/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.dao;

import br.com.tiaorockeiro.modelo.PromocaoProduto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import static org.apache.commons.lang.StringUtils.join;

/**
 *
 * @author INLOC01
 */
public class PromocaoProdutoDAOImpl extends DAOImpl<PromocaoProduto, Long> implements PromocaoProdutoDAO {

    private final EntityManager entityManager;

    public PromocaoProdutoDAOImpl(EntityManager entityManager) {
        super(entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<Map<String, Object>> procuraPromocaoPorProduto(List<Long> idProdutos) {
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT p.id AS id_promocao,\n");
        sql.append("           pp.id AS id_promocao_produto,\n");
        sql.append("           pp.id_produto,\n");
        sql.append("           pp.valor_produto\n");
        sql.append("      FROM promocao_produto pp\n");
        sql.append("INNER JOIN promocao p ON p.id = pp.id_promocao\n");
        sql.append(" LEFT JOIN promocao_dia pd ON p.id = pd.id_promocao\n");
        sql.append("     WHERE pp.id_produto = ANY(STRING_TO_ARRAY(:idProdutos,';')\\:\\:BIGINT[])\n");
        sql.append("       AND ((p.data_final ISNULL AND p.data_inicial <= CURRENT_DATE) OR\n");
        sql.append("            (CURRENT_DATE BETWEEN p.data_inicial AND p.data_final))\n");
        sql.append("       AND (pd.id ISNULL OR pd.dia = (EXTRACT(DOW FROM CURRENT_DATE)+1))\n");
        sql.append("       AND ((pd.hora_inicial ISNULL AND pd.hora_final ISNULL) OR\n");
        sql.append("            (pd.hora_final ISNULL AND pd.hora_inicial <= CURRENT_TIME) OR\n");
        sql.append("            (pd.hora_inicial ISNULL AND pd.hora_final >= CURRENT_TIME) OR\n");
        sql.append("            (CURRENT_TIME BETWEEN pd.hora_inicial AND pd.hora_final))\n");
        Query query = this.entityManager.createNativeQuery(sql.toString());
        query.setParameter("idProdutos", join(idProdutos, ";"));
        List<Object[]> result = query.getResultList();
        List<Map<String, Object>> lista = new ArrayList<>();
        result.stream().map((object) -> {
            Map<String, Object> item = new HashMap<>();
            item.put("ID_PROMOCAO", object[0]);
            item.put("ID_PROMOCAO_PRODUTO", object[1]);
            item.put("ID_PRODUTO", object[2]);
            item.put("VALOR_PRODUTO", object[3]);
            return item;
        }).forEachOrdered((item) -> {
            lista.add(item);
        });
        return lista;
    }
}
