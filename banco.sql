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
