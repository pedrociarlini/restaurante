--    Tina POS is a point of sales application designed for touch screens.
--    Copyright (C) 2005 Adrián Romero Corchado.
--    http://sourceforge.net/projects/tinapos
--
--    This program is free software; you can redistribute it and/or modify
--    it under the terms of the GNU General Public License as published by
--    the Free Software Foundation; either version 2 of the License, or
--    (at your option) any later version.
--
--    This program is distributed in the hope that it will be useful,
--    but WITHOUT ANY WARRANTY; without even the implied warranty of
--    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
--    GNU General Public License for more details.
--
--    You should have received a copy of the GNU General Public License
--    along with this program; if not, write to the Free Software
--    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

-- Tina POS Database upgrade script for POSTGRESQL from version 0.0.16 to 0.0.17
-- v0.0.17

UPDATE TINAPOS SET VERSION = '0.0.17';

CREATE INDEX CATEGORIES_NAME_INX ON CATEGORIES(NAME);
CREATE INDEX PRODUCTS_NAME_INX ON PRODUCTS(NAME);

INSERT INTO RESOURCES VALUES('Printer.Start', 0, $FILE{/net/adrianromero/templates/printerstart.xml});
INSERT INTO RESOURCES VALUES('Printer.TicketTotal', 0, $FILE{/net/adrianromero/templates/printertickettotal.xml});
INSERT INTO RESOURCES VALUES('Printer.CloseCash', 0, $FILE{/net/adrianromero/templates/printerclosecash.xml});

CREATE TABLE PRODUCTS_COM (
    REFERENCE VARCHAR NOT NULL,
    REFERENCE2 VARCHAR NOT NULL,
    PRIMARY KEY (REFERENCE, REFERENCE2),
    CONSTRAINT PRODUCTS_COM_FK_1 FOREIGN KEY (REFERENCE) REFERENCES PRODUCTS(REFERENCE),
    CONSTRAINT PRODUCTS_COM_FK_2 FOREIGN KEY (REFERENCE2) REFERENCES PRODUCTS(REFERENCE)
);

ALTER TABLE PRODUCTS ADD COLUMN ISCOM BOOLEAN NOT NULL;

ALTER TABLE PRODUCTSOUT ADD COLUMN NAME VARCHAR;
ALTER TABLE PRODUCTSOUT ADD COLUMN ISCOM BOOLEAN;
