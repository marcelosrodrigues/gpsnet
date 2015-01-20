SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;


CREATE TABLE cliente (
    cpf character varying(255),
    cep character varying(255),
    bairro character varying(255),
    cidade character varying(255),
    logradouro character varying(255),
    id bigint NOT NULL,
    uf character varying(255)
);

CREATE TABLE cobranca (
    id bigint NOT NULL,
    digitoagencia character varying(255),
    numeroagencia integer,
    banco integer,
    carteira integer,
    digitocontacorrente character varying(255),
    numerocontacorrente bigint,
    dataemissao timestamp without time zone,
    dataprocessamento timestamp without time zone,
    datavencimento timestamp without time zone,
    instrucoes character varying(255),
    digitonossonumero character varying(255),
    nossonumero bigint,
    valorboleto numeric(19,2),
    cliente_id bigint NOT NULL,
    empresa_id bigint NOT NULL,
    numerododocumento character varying(255) NOT NULL,
    status integer
);

CREATE SEQUENCE cobranca_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



ALTER SEQUENCE cobranca_id_seq OWNED BY cobranca.id;


--
-- Name: empresa; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE empresa (
    id bigint NOT NULL,
    bloqueado boolean,
    cep character varying(255),
    bairro character varying(255),
    cidade character varying(255),
    logradouro character varying(255),
    nome character varying(255) NOT NULL,
    uf character varying(255)
);


CREATE SEQUENCE empresa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: empresa_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE empresa_id_seq OWNED BY empresa.id;


--
-- Name: estado; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE estado (
    uf character VARYING (2) NOT NULL,
    nome character varying(255) NOT NULL
);

--
-- Name: grupo; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE grupo (
    id bigint NOT NULL,
    nome character varying(255) NOT NULL
);

CREATE SEQUENCE grupo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE grupo_id_seq OWNED BY grupo.id;


--
-- Name: membro; Type: TABLE; Schema: public; Owner: postgres; Tablespace:
--

CREATE TABLE membro (
    membro_id bigint NOT NULL,
    grupo_id bigint NOT NULL
);


CREATE TABLE usuario (
    id bigint NOT NULL,
    bloqueado boolean,
    email character varying(200) NOT NULL,
    password character varying(255) NOT NULL,
    tentativas bigint,
    empresa_id bigint,
    nome character varying(255) NOT NULL
);

CREATE SEQUENCE usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE usuario_id_seq OWNED BY usuario.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cobranca ALTER COLUMN id SET DEFAULT nextval('cobranca_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empresa ALTER COLUMN id SET DEFAULT nextval('empresa_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY grupo ALTER COLUMN id SET DEFAULT nextval('grupo_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario ALTER COLUMN id SET DEFAULT nextval('usuario_id_seq'::regclass);


ALTER TABLE ONLY cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id);


ALTER TABLE ONLY cobranca
    ADD CONSTRAINT cobranca_pkey PRIMARY KEY (id);


ALTER TABLE ONLY empresa
    ADD CONSTRAINT empresa_pkey PRIMARY KEY (id);


ALTER TABLE ONLY estado
    ADD CONSTRAINT estado_pkey PRIMARY KEY (uf);

ALTER TABLE ONLY grupo
    ADD CONSTRAINT grupo_pkey PRIMARY KEY (id);

ALTER TABLE ONLY membro
    ADD CONSTRAINT membro_pkey PRIMARY KEY (grupo_id, membro_id);


ALTER TABLE ONLY usuario
    ADD CONSTRAINT uk_email UNIQUE (email);

ALTER TABLE ONLY grupo
    ADD CONSTRAINT uk_nome_grupo UNIQUE (nome);

ALTER TABLE ONLY cobranca
    ADD CONSTRAINT uk_cobranca UNIQUE (numerododocumento, banco);

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


ALTER TABLE ONLY cobranca
    ADD CONSTRAINT fk_empresa_cobranca FOREIGN KEY (empresa_id) REFERENCES empresa(id);


ALTER TABLE ONLY membro
    ADD CONSTRAINT fk_grupo_membro FOREIGN KEY (grupo_id) REFERENCES grupo(id);


ALTER TABLE ONLY cobranca
    ADD CONSTRAINT fk_cliente_cobranca FOREIGN KEY (cliente_id) REFERENCES cliente(id);


ALTER TABLE ONLY cliente
    ADD CONSTRAINT fk_estado_cliente FOREIGN KEY (uf) REFERENCES estado(uf);

ALTER TABLE ONLY membro
    ADD CONSTRAINT fk_membros_grupo FOREIGN KEY (membro_id) REFERENCES usuario(id);

ALTER TABLE ONLY cliente
    ADD CONSTRAINT fk_cliente_usuario FOREIGN KEY (id) REFERENCES usuario(id);


ALTER TABLE ONLY empresa
    ADD CONSTRAINT fk_estado_empresa FOREIGN KEY (uf) REFERENCES estado(uf);

ALTER TABLE ONLY usuario
    ADD CONSTRAINT fk_usuario_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id);


REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;

INSERT INTO ESTADO (UF,NOME) VALUES ('AC','Acre'),
('AL','Alagoas'),
('AP','Amapá'),
('AM','Amazonas'),
('BA','Bahia'),
('CE','Ceará'),
('ES','Espírito Santo'),
('GO','Goiás'),
('MA','Maranhão'),
('MT','Mato Grosso'),
('MS','Mato Grosso do Sul'),
('MG','Minas Gerais'),
('PA','Pará'),
('PB','Paraíba'),
('PR','Paraná'),
('PE','Pernambuco'),
('PI','Piauí'),
('RJ','Rio de Janeiro'),
('RN','Rio Grande do Norte'),
('RS','Rio Grande do Sul'),
('RO','Rondônia'),
('RR','Roraima'),
('SC','Santa Catarina'),
('SP','São Paulo'),
('SE','Sergipe'),
('TO','Tocantins'),
('DF','Distrito Federal');

insert into usuario (bloqueado, email, password, tentativas, nome) values ( false , 'marcelosrodrigues@globo.com' , md5('2pk0#3ty?') , 0 , 'MARCELO DA SILVA RODRIGUES');

insert into grupo (  nome ) values ( 'Administrador' );
insert into membro ( membro_id, grupo_id ) values ( 1 , 1 );

insert into grupo ( nome ) values ('Corretor' );
insert into grupo ( nome ) values ( 'Financeiro' );



