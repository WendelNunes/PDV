/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author INLOC01
 */
@Entity
@Table(name = "abertura_caixa")
public class AberturaCaixa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "id_caixa")
    private Caixa caixa;
    @Column(name = "data_hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHora;
    @Column(name = "saldo_inicial")
    private BigDecimal saldoInicial;
    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @OneToOne(mappedBy = "aberturaCaixa")
    private FechamentoCaixa fechamentoCaixa;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aberturaCaixa")
    private List<SangriaCaixa> sangrias;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aberturaCaixa")
    private List<SuprimentoCaixa> suprimentos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public FechamentoCaixa getFechamentoCaixa() {
        return fechamentoCaixa;
    }

    public void setFechamentoCaixa(FechamentoCaixa fechamentoCaixa) {
        this.fechamentoCaixa = fechamentoCaixa;
    }

    public List<SangriaCaixa> getSangrias() {
        return sangrias;
    }

    public void setSangrias(List<SangriaCaixa> sangrias) {
        this.sangrias = sangrias;
    }

    public List<SuprimentoCaixa> getSuprimentos() {
        return suprimentos;
    }

    public void setSuprimentos(List<SuprimentoCaixa> suprimentos) {
        this.suprimentos = suprimentos;
    }
}
