/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Wendel
 */
@Entity
@Table(name = "impressora")
public class Impressora implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "url")
    private String url;
    @Column(name = "codigo_inicio_impressao")
    private String codigoInicioImpressao;
    @Column(name = "codigo_corte")
    private String codigoCorte;
    @Column(name = "quantidade_caracteres")
    private Integer quantidadeCaracteres;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCodigoInicioImpressao() {
        return codigoInicioImpressao;
    }

    public void setCodigoInicioImpressao(String codigoInicioImpressao) {
        this.codigoInicioImpressao = codigoInicioImpressao;
    }

    public String getCodigoCorte() {
        return codigoCorte;
    }

    public void setCodigoCorte(String codigoCorte) {
        this.codigoCorte = codigoCorte;
    }

    public Integer getQuantidadeCaracteres() {
        return quantidadeCaracteres;
    }

    public void setQuantidadeCaracteres(Integer quantidadeCaracteres) {
        this.quantidadeCaracteres = quantidadeCaracteres;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Impressora other = (Impressora) obj;
        return Objects.equals(this.id, other.id);
    }
}
