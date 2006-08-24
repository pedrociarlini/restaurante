CREATE DATABASE `marcao` /*!40100 DEFAULT CHARACTER SET latin1 */;

DROP TABLE IF EXISTS `marcao`.`caixa_tipo_venda`;
CREATE TABLE  `marcao`.`caixa_tipo_venda` (
  `TIPO_VENDA_ID` int(10) unsigned NOT NULL auto_increment,
  `TIPO_VENDA_DESCRICAO` varchar(50) NOT NULL,
  `TIPO_VENDA_A_VISTA` tinyint(1) default NULL,
  PRIMARY KEY  (`TIPO_VENDA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `marcao`.`caixa_venda`;
CREATE TABLE  `marcao`.`caixa_venda` (
  `VENDA_ID` int(10) unsigned NOT NULL auto_increment,
  `VENDA_VALOR` double NOT NULL,
  `VENDA_DATA` datetime NOT NULL,
  `TIPO_VENDA_ID` int(10) unsigned default NULL,
  PRIMARY KEY  (`VENDA_ID`),
  KEY `FK_caixa_venda-caixa_tipo_venda` (`TIPO_VENDA_ID`),
  CONSTRAINT `FK_caixa_venda-caixa_tipo_venda` FOREIGN KEY (`TIPO_VENDA_ID`) REFERENCES `caixa_tipo_venda` (`TIPO_VENDA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;