//    Tina POS is a point of sales application designed for touch screens.
//    Copyright (C) 2005 Adrian Romero Corchado.
//    http://sourceforge.net/projects/tinapos
//
//    This program is free software; you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation; either version 2 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program; if not, write to the Free Software
//    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

package net.adrianromero.tpv.forms;

import java.sql.Connection;
import net.adrianromero.data.loader.Datas;
import net.adrianromero.data.loader.QBFBuilder;
import net.adrianromero.data.loader.SequenceForMySQL;
import net.adrianromero.data.loader.SerializerReadBasic;
import net.adrianromero.data.loader.SerializerReadInteger;
import net.adrianromero.data.loader.SerializerWriteBasic;
import net.adrianromero.data.loader.StaticSentence;

public class SentenceContainerMySQL extends SentenceContainer {
    
    /** Creates a new instance of SentenceContainerMySQL */
    public SentenceContainerMySQL() {
    }
    
    public void init(Connection cnt) {
        super.init(cnt);
        
        m_productcatqbf = new StaticSentence(cnt
            , new QBFBuilder("SELECT P.REFERENCE, P.CODE, P.NAME, P.ISCOM, P.ISSCALE, P.PRICEBUY, P.PRICESELL, P.CATEGORY, P.TAX, P.IMAGE, P.STOCKCOST, P.STOCKVOLUME, NOT (C.REFERENCE IS NULL), C.CATORDER FROM PRODUCTS P LEFT OUTER JOIN PRODUCTS_CAT C ON P.REFERENCE = C.REFERENCE WHERE ?(QBF_FILTER) ORDER BY P.NAME", new String[] {"P.NAME", "P.PRICEBUY", "P.PRICESELL", "P.CATEGORY", "P.CODE"})
            , new SerializerWriteBasic(new Datas[] {Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.INT, Datas.OBJECT, Datas.STRING})
            , new SerializerReadBasic(productcatDatas));

        m_nextticketindex = new SequenceForMySQL(cnt, "TICKETSNUM", SerializerReadInteger.INSTANCE);                             
        m_nextreservation = new SequenceForMySQL(cnt, "RESERVATIONSNUM", SerializerReadInteger.INSTANCE);                       
        m_nextstockdiary = new SequenceForMySQL(cnt, "STOCKDIARYNUM", SerializerReadInteger.INSTANCE);           
        m_nextpayment = new SequenceForMySQL(cnt, "PAYMENTSNUM", SerializerReadInteger.INSTANCE);         
    }    
}