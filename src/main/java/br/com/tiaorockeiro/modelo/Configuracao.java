/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Wendel
 */
@Entity
@Table(name = "configuracao")
public class Configuracao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "qtde_mesas")
    private Integer quantidadeMesas;
    @Column(name = "percentual_comissao")
    private BigDecimal percentualComissao;
    @Column(name = "hora_maxima_virada_dia_caixa")
    @Temporal(TemporalType.TIME)
    private Date horaMaximaViradaDiaCaixa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidadeMesas() {
        return quantidadeMesas;
    }

    public void setQuantidadeMesas(Integer quantidadeMesas) {
        this.quantidadeMesas = quantidadeMesas;
    }

    public BigDecimal getPercentualComissao() {
        return percentualComissao;
    }

    public void setPercentualComissao(BigDecimal percentualComissao) {
        this.percentualComissao = percentualComissao;
    }

    public Date getHoraMaximaViradaDiaCaixa() {
        return horaMaximaViradaDiaCaixa;
    }

    public void setHoraMaximaViradaDiaCaixa(Date horaMaximaViradaDiaCaixa) {
        this.horaMaximaViradaDiaCaixa = horaMaximaViradaDiaCaixa;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Configuracao other = (Configuracao) obj;
        return Objects.equals(this.id, other.id);
    }
}
