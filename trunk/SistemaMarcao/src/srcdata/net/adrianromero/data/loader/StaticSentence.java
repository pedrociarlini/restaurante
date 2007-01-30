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

/*
 * StaticSentence.java
 *
 * Created on 27 de abril de 2004, 20:25
 */

package net.adrianromero.data.loader;

import java.sql.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import net.adrianromero.basic.BasicException;

/**
 *
 * @author  adrian
 */
public class StaticSentence extends JDBCSentence {
    
    private ISQLBuilderStatic m_sentence;
    protected SerializerWrite m_SerWrite = null;
    protected SerializerRead m_SerRead = null;

    // Estado
    private Statement m_Stmt;
    
    /** Creates a new instance of StaticSentence */
    public StaticSentence(Connection c, ISQLBuilderStatic sentence, SerializerWrite serwrite, SerializerRead serread) {
        super(c);
        m_sentence = sentence;
        m_SerWrite = serwrite;
        m_SerRead = serread;
        m_Stmt = null;
    }    
    /** Creates a new instance of StaticSentence */
    public StaticSentence(Connection c, ISQLBuilderStatic sentence) {
        this(c, sentence, null, null);
    }     
    /** Creates a new instance of StaticSentence */
    public StaticSentence(Connection c, ISQLBuilderStatic sentence, SerializerWrite serwrite) {
        this(c, sentence, serwrite, null);
    }     
    /** Creates a new instance of StaticSentence */
    public StaticSentence(Connection c, String sentence, SerializerWrite serwrite, SerializerRead serread) {
        this(c, new NormalBuilder(sentence), serwrite, serread);
    }
    /** Creates a new instance of StaticSentence */
    public StaticSentence(Connection c, String sentence, SerializerWrite serwrite) {
        this(c, new NormalBuilder(sentence), serwrite, null);
    }
    /** Creates a new instance of StaticSentence */
    public StaticSentence(Connection c, String sentence) {
        this(c, new NormalBuilder(sentence), null, null);
    }
    
    public DataResultSet openExec(Object params) throws BasicException {
        // true -> un resultset
        // false -> un updatecount (si -1 entonces se acabo)
        
        closeExec();
            
        try {
            m_Stmt = m_c.createStatement();
            if (m_Stmt.execute(m_sentence.getSQL(m_SerWrite, params))) {            
                return new JDBCDataResultSet(m_Stmt.getResultSet(), m_SerRead);
            } else { 
                int iUC = m_Stmt.getUpdateCount();
                if (iUC < 0) {
                    return null;
                } else {
                    return new SentenceUpdateResultSet(iUC);
                }
            }
        } catch (SQLException eSQL) {
            throw new BasicException(eSQL);
        }
    }
    
    public void closeExec() throws BasicException {
        
        try {
            if (m_Stmt != null) {
                m_Stmt.close();
                m_Stmt = null;
            }
        } catch (SQLException eSQL) {
            throw new BasicException(eSQL);
        }
    }
    
    public DataResultSet moreResults() throws BasicException {

        try {
            if (m_Stmt.getMoreResults()){
                // tenemos resultset
                return new JDBCDataResultSet(m_Stmt.getResultSet(), m_SerRead);
            } else {
                // tenemos updatecount o si devuelve -1 ya no hay mas
                int iUC = m_Stmt.getUpdateCount();
                if (iUC < 0) {
                    return null;
                } else {
                    return new SentenceUpdateResultSet(iUC);
                }
            }
        } catch (SQLException eSQL) {
            throw new BasicException(eSQL);
        }
    }    
    
}
