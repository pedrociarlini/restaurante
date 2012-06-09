-- drop table seg_usuario

-- create schema marcaopdv AUTHORIZATION marcaopdv

create table SEG_USUARIO (
	ID_USUARIO integer primary key NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	NM_LOGIN varchar(20) not null,
	NM_USUARIO varchar(200),
	TX_SENHA varchar(255)
)