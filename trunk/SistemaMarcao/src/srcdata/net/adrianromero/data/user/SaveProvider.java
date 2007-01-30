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

package net.adrianromero.data.user;
import net.adrianromero.data.loader.TableDefinition;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.loader.SentenceExec;

public class SaveProvider {
    
    protected SentenceExec m_sentupdate;
    protected SentenceExec m_sentinsert;
    protected SentenceExec m_sentdelete;
    
    /** Creates a new instance of SavePrSentence */
    public SaveProvider(SentenceExec sentupdate, SentenceExec sentinsert, SentenceExec sentdelete) {
        m_sentupdate = sentupdate;
        m_sentinsert = sentinsert;
        m_sentdelete = sentdelete;
    }
    public SaveProvider(TableDefinition table) {
        m_sentupdate = table.getUpdateSentence();
        m_sentdelete = table.getDeleteSentence();
        m_sentinsert = table.getInsertSentence();
    }
    
    public int deleteData(Object value) throws BasicException {
        return m_sentdelete.exec(value);
    }

    public int insertData(Object value) throws BasicException {
        return m_sentinsert.exec(value);
    }
    
    public int updateData(Object value) throws BasicException {
        return m_sentupdate.exec(value);
    }
}
