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

-- Tina POS Database upgrade script for MySQL from version 0.0.17 to 0.0.18
-- v0.0.18

UPDATE TINAPOS SET VERSION = '0.0.18';

ALTER TABLE PRODUCTS ADD COLUMN ISSCALE BIT NOT NULL DEFAULT 0;
ALTER TABLE PRODUCTSOUT ADD COLUMN TAXID INTEGER;
ALTER TABLE PRODUCTSOUT ADD CONSTRAINT PRODUCTSOUT_FK_3 FOREIGN KEY (TAXID) REFERENCES TAXES(ID);

ALTER TABLE PAYMENTS DROP COLUMN DATENEW;
ALTER TABLE PAYMENTS DROP FOREIGN KEY PAYMENTS_FK_1;
ALTER TABLE PAYMENTS DROP COLUMN MONEY;
ALTER TABLE PAYMENTS ADD COLUMN TICKETID INTEGER;
ALTER TABLE PAYMENTS ADD CONSTRAINT PAYMENTS_FK_2 FOREIGN KEY (TICKETID) REFERENCES TICKETS(TICKETID);
