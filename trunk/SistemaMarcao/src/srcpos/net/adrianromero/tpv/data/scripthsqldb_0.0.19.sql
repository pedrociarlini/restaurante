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

-- Tina POS Database upgrade script for HSQLDB from version 0.0.19 to 0.0.20
-- v0.0.20

UPDATE TINAPOS SET VERSION = '0.0.20';

CREATE TABLE ROLES (
    NAME VARCHAR NOT NULL,
    PERMISSIONS VARBINARY,
    PRIMARY KEY (NAME)
);
INSERT INTO ROLES VALUES('Administrator', $FILE{/net/adrianromero/templates/permadministrator.xml});
INSERT INTO ROLES VALUES('Manager', $FILE{/net/adrianromero/templates/permmanager.xml});
INSERT INTO ROLES VALUES('Employee', $FILE{/net/adrianromero/templates/permemployee.xml});
INSERT INTO ROLES VALUES('Guest', $FILE{/net/adrianromero/templates/permguest.xml});

ALTER TABLE PEOPLE ADD CONSTRAINT PEOPLE_FK_1 FOREIGN KEY (ROLE) REFERENCES ROLES(NAME);
