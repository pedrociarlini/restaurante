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

-- TPV Database upgrade script for MYSQL from version 0.0.7 to 0.0.8
-- v1.0

CREATE TABLE TINAPOS (
    VERSION VARCHAR(100) NOT NULL,
    PRIMARY KEY (VERSION)
);
INSERT INTO TINAPOS VALUES('0.0.8');

CREATE TABLE FLOORS (
    ID INTEGER NOT NULL,
    NAME VARCHAR(100) NOT NULL,
    IMAGE MEDIUMBLOB,
    PRIMARY KEY (ID)
);
INSERT INTO FLOORS VALUES (0, 'Restaurant floor', $FILE{/net/adrianromero/templates/restaurantsample.jpg});

ALTER TABLE PLACES ADD COLUMN FLOOR INTEGER NOT NULL DEFAULT 0;
ALTER TABLE PLACES ALTER COLUMN FLOOR DROP DEFAULT;