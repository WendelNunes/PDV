CREATE TABLE configuracao (
    id bigserial NOT NULL,
    qtde_mesas INTEGER NOT NULL DEFAULT 0,
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