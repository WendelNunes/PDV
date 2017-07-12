/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.converter;

import br.com.tiaorockeiro.modelo.DiaSemana;
import javax.persistence.AttributeConverter;

/**
 *
 * @author INLOC01
 */
public class DiaSemanaAttributeConverter implements AttributeConverter<DiaSemana, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DiaSemana diaSemana) {
        if (diaSemana != null) {
            return diaSemana.getId();
        }
        return null;
    }

    @Override
    public DiaSemana convertToEntityAttribute(Integer id) {
        if (id != null) {
            return DiaSemana.get(id);
        }
        return null;
    }

}
