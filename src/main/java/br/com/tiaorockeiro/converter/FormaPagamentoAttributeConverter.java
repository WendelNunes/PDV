/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.tiaorockeiro.converter;

import br.com.tiaorockeiro.modelo.FormaPagamento;
import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

/**
 *
 * @author Wendel
 */
@Convert
public class FormaPagamentoAttributeConverter implements AttributeConverter<FormaPagamento, String> {

    @Override
    public String convertToDatabaseColumn(FormaPagamento formaPagamento) {
        if (formaPagamento != null) {
            return formaPagamento.getId();
        }
        return null;
    }

    @Override
    public FormaPagamento convertToEntityAttribute(String id) {
        if (id != null) {
            return FormaPagamento.get(id);
        }
        return null;
    }
}
