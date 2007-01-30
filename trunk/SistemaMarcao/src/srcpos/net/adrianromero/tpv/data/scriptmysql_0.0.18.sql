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

-- Tina POS Database upgrade script for MySQL from version 0.0.18 to 0.0.19
-- v0.0.19

UPDATE TINAPOS SET VERSION = '0.0.19';

INSERT INTO RESOURCES VALUES('Printer.Inventory', 0, $FILE{/net/adrianromero/templates/printerinventory.xml});

CREATE TABLE LOCATIONS (
    ID INTEGER NOT NULL,
    NAME VARCHAR(100) NOT NULL,
    ADDRESS VARCHAR(100),
    PRIMARY KEY (ID)
);
INSERT INTO LOCATIONS VALUES(0, 'General', NULL);

ALTER TABLE STOCKDIARY ADD COLUMN LOCATION INTEGER NOT NULL DEFAULT 0;
ALTER TABLE STOCKDIARY ALTER COLUMN LOCATION DROP DEFAULT;
ALTER TABLE STOCKDIARY ADD CONSTRAINT STOCKDIARY_FK_2 FOREIGN KEY (LOCATION) REFERENCES LOCATIONS(ID);

ALTER TABLE STOCKCURRENT ADD COLUMN LOCATION INTEGER NOT NULL DEFAULT 0;
ALTER TABLE STOCKCURRENT ALTER COLUMN LOCATION DROP DEFAULT;
ALTER TABLE STOCKCURRENT ADD COLUMN STOCKSECURITY DOUBLE;
ALTER TABLE STOCKCURRENT ADD COLUMN STOCKMAXIMUM DOUBLE;

ALTER TABLE STOCKCURRENT ADD CONSTRAINT STOCKCURRENT_FK_2 FOREIGN KEY (LOCATION) REFERENCES LOCATIONS(ID);
ALTER TABLE STOCKCURRENT DROP PRIMARY KEY;
ALTER TABLE STOCKCURRENT ADD PRIMARY KEY(LOCATION, PRODUCT);
