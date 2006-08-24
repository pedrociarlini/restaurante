CREATE DATABASE `marcao` /*!40100 DEFAULT CHARACTER SET latin1 */;

DROP TABLE IF EXISTS `marcao`.`caixa_venda`;
CREATE TABLE  `marcao`.`caixa_venda` (
  `VENDA_ID` int(10) unsigned NOT NULL auto_increment,
  `VENDA_VALOR` double NOT NULL,
  `VENDA_DATA` datetime NOT NULL,
  `VENDA_TIPO_VENDA_ID` int(10) unsigned default NULL,
  PRIMARY KEY  (`VENDA_ID`)
) ENGINE=InnoDB;



