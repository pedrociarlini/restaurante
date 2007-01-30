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

-- TPV Database upgrade script for HSQLDB from version 0.0.8 to 0.0.9
-- v1.0

UPDATE TINAPOS SET VERSION = '0.0.9';

CREATE TABLE RESERVATIONS (
    ID INTEGER NOT NULL,
    DATENEW TIMESTAMP NOT NULL,
    TITLE VARCHAR NOT NULL,
    CHAIRS INTEGER NOT NULL,
    ISDONE BOOLEAN NOT NULL,
    DESCRIPTION VARCHAR,
    PRIMARY KEY (ID)
);
CREATE INDEX RESERVATIONS_INX_1 ON RESERVATIONS(DATENEW);
CREATE SEQUENCE RESERVATIONSNUM;

INSERT INTO RESOURCES VALUES('Printer.OpenDrawer', 0, $FILE{/net/adrianromero/templates/printeropendrawer.xml});
INSERT INTO RESOURCES VALUES('Ticket.Buttons', 0, $FILE{/net/adrianromero/templates/ticketbuttons.xml});
INSERT INTO RESOURCES VALUES('Ticket.Line', 0, $FILE{/net/adrianromero/templates/ticketline_taxesincluded.xml});

ALTER TABLE PRODUCTS ADD COLUMN STOCKSECURITY DOUBLE PRECISION;
ALTER TABLE PRODUCTS ADD COLUMN STOCKCOST DOUBLE PRECISION;
ALTER TABLE PRODUCTS ADD COLUMN STOCKVOLUME DOUBLE PRECISION;

CREATE TABLE STOCKDIARY (
    ID INTEGER NOT NULL,
    DATENEW TIMESTAMP NOT NULL,
    REASON INTEGER NOT NULL,
    PRODUCT VARCHAR NOT NULL,
    UNITS DOUBLE PRECISION NOT NULL,
    PRICE DOUBLE PRECISION NOT NULL,
    PRIMARY KEY (ID),
    CONSTRAINT STOCKDIARY_FK_1 FOREIGN KEY (PRODUCT) REFERENCES PRODUCTS(REFERENCE)
);
CREATE INDEX STOCKDIARY_INX_1 ON STOCKDIARY(DATENEW);
CREATE SEQUENCE STOCKDIARYNUM;

CREATE TABLE STOCKCURRENT (
    PRODUCT VARCHAR NOT NULL,
    UNITS DOUBLE PRECISION NOT NULL,
    PRIMARY KEY (PRODUCT),
    CONSTRAINT STOCKCURRENT_FK_1 FOREIGN KEY (PRODUCT) REFERENCES PRODUCTS(REFERENCE)
);

ALTER TABLE PRODUCTSOUT ADD COLUMN TAXRATE DOUBLE PRECISION;

-- Update of the taxes management
UPDATE PRODUCTSOUT SET TAXRATE = COALESCE((SELECT TAXES.RATE FROM PRODUCTS LEFT OUTER JOIN TAXES ON PRODUCTS.TAX = TAXES.ID WHERE PRODUCTS.REFERENCE = PRODUCTSOUT.PRODUCT), 0);
UPDATE PRODUCTSOUT SET PRICE = PRICE * (1 / (1 + COALESCE(TAXRATE, 0)));
UPDATE PRODUCTS SET PRICESELL =  PRICESELL * (1 / (1 + COALESCE( ( SELECT TAXES.RATE FROM TAXES WHERE PRODUCTS.TAX = TAXES.ID), 0)));
