/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author INLOC01
 */
@Entity
@Table(name = "sangria_caixa")
public class SangriaCaixa implements Serializable {

    @Id
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_abertura_caixa")
    private AberturaCaixa aberturaCaixa;
    @Column(name = "data_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    @Column(name = "valor_sangria")
    private BigDecimal valorSangria;
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AberturaCaixa getAberturaCaixa() {
        return aberturaCaixa;
    }

    public void setAberturaCaixa(AberturaCaixa aberturaCaixa) {
        this.aberturaCaixa = aberturaCaixa;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public BigDecimal getValorSangria() {
        return valorSangria;
    }

    public void setValorSangria(BigDecimal valorSangria) {
        this.valorSangria = valorSangria;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
