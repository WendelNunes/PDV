CREATE TABLE configuracao (
    id bigserial NOT NULL,
    qtde_mesas INTEGER NOT NULL DEFAULT 0,
    percentual_comissao numeric(14,4) DEFAULT 0,
    PRIMARY KEY (id)
);

CREATE TABLE pessoa (
    id bigserial NOT NULL,
    descricao character varying NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE usuario(
    id bigserial NOT NULL,
    descricao character varying NOT NULL,
    senha character varying NOT NULL,
    id_pessoa bigint NOT NULL,
    administrador boolean NOT NULL DEFAULT false,
    gerente boolean NOT NULL DEFAULT false,
    operador_caixa boolean NOT NULL DEFAULT false,
    vendedor boolean NOT NULL DEFAULT false,
    PRIMARY KEY (id),
    FOREIGN KEY (id_pessoa) REFERENCES pessoa (id) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE caixa (
    id bigserial NOT NULL,
    codigo character varying NOT NULL,
    descricao character varying NOT NULL,
    PRIMARY KEY(id),
    UNIQUE (codigo)
);

CREATE TABLE abertura_caixa (
   id bigserial NOT NULL, 
   id_caixa bigint NOT NULL, 
   data_hora timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP, 
   saldo_inicial numeric(14,4) NOT NULL DEFAULT 0, 
   id_usuario bigint NOT NULL, 
   PRIMARY KEY (id), 
   FOREIGN KEY (id_caixa) REFERENCES caixa (id) ON UPDATE NO ACTION ON DELETE NO ACTION, 
   FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE fechamento_caixa (
   id bigserial NOT NULL, 
   id_abertura_caixa bigint NOT NULL, 
   data_hora timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP, 
   saldo_final numeric(14,4) NOT NULL DEFAULT 0, 
   id_usuario bigint NOT NULL, 
   PRIMARY KEY (id), 
   FOREIGN KEY (id_abertura_caixa) REFERENCES abertura_caixa (id) ON UPDATE NO ACTION ON DELETE NO ACTION, 
   FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE sangria_caixa (
   id bigserial NOT NULL, 
   id_abertura_caixa bigint NOT NULL, 
   data_hora timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP, 
   valor_sangria numeric(14,4) NOT NULL DEFAULT 0, 
   id_usuario bigint NOT NULL, 
   PRIMARY KEY (id), 
   FOREIGN KEY (id_abertura_caixa) REFERENCES abertura_caixa (id) ON UPDATE NO ACTION ON DELETE NO ACTION, 
   FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE suprimento_caixa (
   id bigserial NOT NULL, 
   id_abertura_caixa bigint NOT NULL, 
   data_hora timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP, 
   valor_suprimento numeric(14,4) NOT NULL DEFAULT 0, 
   id_usuario bigint NOT NULL, 
   PRIMARY KEY (id), 
   FOREIGN KEY (id_abertura_caixa) REFERENCES abertura_caixa (id) ON UPDATE NO ACTION ON DELETE NO ACTION, 
   FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE configuracao_usuario (
   id bigserial NOT NULL,
   id_usuario bigint NOT NULL,
   id_caixa_selecionado bigint,
   PRIMARY KEY (id),
   FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
   FOREIGN KEY (id_caixa_selecionado) REFERENCES caixa (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE impressora (
    id bigserial NOT NULL,
    descricao character varying NOT NULL,
    url character varying NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE unidade_produto (
    id bigserial NOT NULL,
    codigo character varying NOT NULL,
    descricao character varying NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE categoria_produto (
    id bigserial NOT NULL,
    descricao character varying NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE produto (
    id bigserial NOT NULL,
    codigo character varying NOT NULL,
    descricao character varying NOT NULL,
    id_categoria_produto bigint NOT NULL,
    id_unidade_produto bigint NOT NULL,
    valor numeric(14,4) NOT NULL DEFAULT 0,
    id_impressora_comanda bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (id_categoria_produto) REFERENCES categoria_produto (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (id_unidade_produto) REFERENCES unidade_produto (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (id_impressora_comanda) REFERENCES impressora (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE pedido (
    id bigserial NOT NULL,
    id_usuario bigint NOT NULL,
    data_hora timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_usuario_cancelamento bigint,
    data_hora_cancelamento timestamp without time zone,
    mesa integer NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (id_usuario_cancelamento) REFERENCES usuario (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE item_pedido (
    id bigserial NOT NULL,
    id_pedido bigint NOT NULL,
    id_abertura_caixa bigint NOT NULL,
    id_usuario bigint NOT NULL,
    data_hora timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_usuario_cancelamento bigint,
    data_hora_cancelamento timestamp without time zone,
    id_produto bigint NOT NULL,
    quantidade numeric(14,4) NOT NULL DEFAULT 0,
    valor numeric(14,4) NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (id_pedido) REFERENCES pedido (id) ON UPDATE NO ACTION ON DELETE CASCADE,
    FOREIGN KEY (id_abertura_caixa) REFERENCES abertura_caixa (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (id_usuario_cancelamento) REFERENCES usuario (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (id_produto) REFERENCES produto (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE venda (
    id bigserial NOT NULL,
    id_pedido bigint NOT NULL,
    id_usuario bigint NOT NULL,
    id_abertura_caixa bigint NOT NULL,
    data_hora timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mesa integer NOT NULL,
    valor_comissao numeric(14,4) DEFAULT 0,
    valor_desconto numeric(14,4) DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (id_pedido) REFERENCES pedido (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (id_usuario) REFERENCES usuario (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (id_abertura_caixa) REFERENCES abertura_caixa (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE item_venda (
    id bigserial NOT NULL,
    id_venda bigint NOT NULL,
    sequencia integer NOT NULL,
    id_produto bigint NOT NULL,
    quantidade numeric(14,4) NOT NULL DEFAULT 0,
    valor numeric(14,4) NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    FOREIGN KEY (id_venda) REFERENCES venda (id) ON UPDATE NO ACTION ON DELETE CASCADE,
    FOREIGN KEY (id_produto) REFERENCES produto (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE pagamento (
    id bigserial NOT NULL,
    id_venda bigint NOT NULL,
    id_forma_pagamento character varying NOT NULL,
    valor numeric(14,4) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_venda) REFERENCES venda (id) ON UPDATE NO ACTION ON DELETE CASCADE
);