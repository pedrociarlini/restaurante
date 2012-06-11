-- drop table seg_usuario

-- create schema marcaopdv AUTHORIZATION marcaopdv

create table SEG_USUARIO (
	ID_USUARIO integer primary key NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	NM_LOGIN varchar(20) not null,
	NM_USUARIO varchar(200),
	TX_SENHA varchar(255)
);

create table MP_LOCAL_MESA (
	ID_LOCAL_MESA integer primary key NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	NM_LOCAL_MESA varchar(30) not null
);

create table MP_MESA (
	ID_MESA integer primary key NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	NM_MESA varchar(30) not null,
	ID_LOCAL_MESA integer
);

alter table MP_MESA add constraint FK_MESA_LOCAL_MESA Foreign Key (ID_LOCAL_MESA)
references MP_LOCAL_MESA(ID_LOCAL_MESA);