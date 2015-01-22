CREATE TABLE operadora (
    id bigint NOT NULL,
    nome character varying(255) not null
);

CREATE SEQUENCE operadora_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE operadora_id_seq OWNED BY operadora.id;

ALTER TABLE ONLY operadora ALTER COLUMN id SET DEFAULT nextval('operadora_id_seq'::regclass);

ALTER TABLE ONLY operadora
    ADD CONSTRAINT operadora_pkey PRIMARY KEY (id);