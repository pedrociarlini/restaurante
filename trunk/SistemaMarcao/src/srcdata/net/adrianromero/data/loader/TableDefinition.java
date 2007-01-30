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

package net.adrianromero.data.loader;

import java.sql.Connection;
import net.adrianromero.format.Formats;

public class TableDefinition {
    
    private Connection m_c;
    private String m_sTableName;
    private int[] m_aiIDs;
    
    private String[] m_asFields;
    private Datas[] m_aDatas;
    
    private Formats[] m_aFmtVector; // los nulos son para los huecos en el vectorizer
    private Formats[] m_aFmtString; // los nulos son para los huecos en el stringnizer
    
    private int[] m_iOrderIndexes;
    
    /** Creates a new instance of TableDefinition */
    public TableDefinition(
            Connection c,
            String sTableName, int[] aiIDs, String[] asFields,
            Datas[] aDatas, Formats[] aFmtVector, Formats[] aFmtString,
            int[] iOrderIndexes) {
        m_c = c;
        m_sTableName = sTableName;
        m_aiIDs = aiIDs;
        m_asFields = asFields;
        m_aDatas = aDatas;
        m_aFmtVector = aFmtVector;
        m_aFmtString = aFmtString;   
        m_iOrderIndexes = iOrderIndexes;
    }
    public TableDefinition(
            Connection c,
            String sTableName, int[] aiIDs, String[] asFields,
            Datas[] aDatas, Formats[] aFmtVector, Formats[] aFmtString) {
        this(c, sTableName, aiIDs, asFields, aDatas, aFmtVector, aFmtString, null);
    }
    
    /** Creates a new instance of TableDefinition */
    public TableDefinition(
            Connection c,
            String sTableName, int[] aiIDs, String[] asFields) {
        this(c, sTableName, aiIDs, asFields, null, null, null, null);
    }
    
    public String getTableName() {
        return m_sTableName;
    }
    
    public String[] getFields() {
        return m_asFields;
    }
    
    public Vectorer getVectorerBasic() {
        return new VectorerBasic(m_asFields, m_aFmtVector);
    }
    
    public IRenderString getRenderStringBasic() {
        return new RenderStringBasic(m_aFmtString);
    }
    
    public IKeyGetter getKeyGetterBasic() {
        if (m_aiIDs.length == 1) {
            return new KeyGetterFirst(m_aiIDs);
        } else {
            return new KeyGetterBasic(m_aiIDs);     
        }
    }    
    
    public SerializerRead getSerializerReadBasic() {
        return new SerializerReadBasic(m_aDatas);
    }
    public SerializerWrite getSerializerInsertBasic() {
        return new SerializerWriteBasic(m_aDatas);
    }
    public SerializerWrite getSerializerDeleteBasic() {     
        return new SerializerWriteBasicExt(m_aDatas, m_aiIDs);
    }
    public SerializerWrite getSerializerUpdateBasic() {
        
        int[] aindex = new int[m_asFields.length + m_aiIDs.length];

        for (int i = 0; i < m_asFields.length; i++) {
            aindex[i] = i;
        } 
        for (int i = 0; i < m_aiIDs.length; i++) {
            aindex[i + m_asFields.length] = m_aiIDs[i];
        }       
 
        return new SerializerWriteBasicExt(m_aDatas, aindex);
    }
    
    public SentenceFind getFindSentence(SerializerWrite sw) {
        return getFindSentence(sw, getSerializerReadBasic());
    }
    
    public SentenceFind getFindSentence(SerializerWrite sw, SerializerRead sr) {
        return new PreparedSentence(m_c, getFindSQL(), sw, sr);
    }
    
    public String getFindSQL() {
        String[] sIDs = new String[m_aiIDs.length];
        for (int i = 0; i < m_aiIDs.length; i++) {
            sIDs[i] = m_asFields[m_aiIDs[i]];
        }
        return getListSQL(sIDs);
    }
    
    public SentenceList getListSentence() {
        return getListSentence(null, null, getSerializerReadBasic());
    }
    
    public SentenceList getListSentence(SerializerRead sr) {
        return getListSentence(null, null, sr);
    }
   
    public SentenceList getListSentence(String[] asFindFields, SerializerWrite sw) {
        return getListSentence(asFindFields, sw, getSerializerReadBasic());
    }
     
    public SentenceList getListSentence(String[] asFindFields, SerializerWrite sw, SerializerRead sr) {
        return new PreparedSentence(m_c, getListSQL(asFindFields), sw,  sr);
    }
    
    public String getListSQL() {
        return getListSQL(null);
    }
    
    public String getListSQL(String[] asFindFields) {
        
        StringBuffer sent = new StringBuffer();
        sent.append("select ");

        for (int i = 0; i < m_asFields.length; i ++) {
            if (i > 0) {
                sent.append(", ");
            }
            sent.append(m_asFields[i]);
        }        
        
        sent.append(" from ");        
        sent.append(m_sTableName);
        
        if (asFindFields != null) {
            for (int i = 0; i < asFindFields.length; i ++) {
                sent.append((i == 0) ? " where " : " and ");
                sent.append(asFindFields[i]);
                sent.append(" = ?");
            }
        }
        
        return sent.toString();    
    }
   
    public SentenceExec getDeleteSentence() {
        return getDeleteSentence(getSerializerDeleteBasic());
    }
    
    public SentenceExec getDeleteSentence(SerializerWrite sw) {
        return new PreparedSentence(m_c, getDeleteSQL(), sw, null);
    }
    
    public String getDeleteSQL() {
        
        StringBuffer sent = new StringBuffer();
        sent.append("delete from ");
        sent.append(m_sTableName);
        
        for (int i = 0; i < m_aiIDs.length; i ++) {
            sent.append((i == 0) ? " where " : " and ");
            sent.append(m_asFields[m_aiIDs[i]]);
            sent.append(" = ?");
        }
        
        return sent.toString();     
    }
   
    public SentenceExec getInsertSentence() {
        return getInsertSentence(getSerializerInsertBasic());
    }
    
    public SentenceExec getInsertSentence(SerializerWrite sw) {
        return new PreparedSentence(m_c, getInsertSQL(), sw, null);
    }
    
    public String getInsertSQL() {
        
        StringBuffer sent = new StringBuffer();
        StringBuffer values = new StringBuffer();
        
        sent.append("insert into ");
        sent.append(m_sTableName);
        sent.append(" (");        
        
        for (int i = 0; i < m_asFields.length; i ++) {
            if (i > 0) {
                sent.append(", ");
                values.append(", ");
            }
            sent.append(m_asFields[i]);
            values.append("?");
        }
        
        sent.append(") values (");
        sent.append(values.toString());
        sent.append(")");

        return sent.toString();       
    }
   
    public SentenceExec getUpdateSentence() {
        return getUpdateSentence(getSerializerUpdateBasic());
    }
    
    public SentenceExec getUpdateSentence(SerializerWrite sw) {
        return new PreparedSentence(m_c, getUpdateSQL(), sw, null);
    }
    
    public String getUpdateSQL() {
        
        StringBuffer sent = new StringBuffer();
        
        sent.append("update ");
        sent.append(m_sTableName);
        sent.append(" set ");
        
        for (int i = 0; i < m_asFields.length; i ++) {
            if (i > 0) {
                sent.append(", ");
            }
            sent.append(m_asFields[i]);
            sent.append(" = ?");
        }
        
        for (int i = 0; i < m_aiIDs.length; i ++) {
            sent.append((i == 0) ? " where " : " and ");
            sent.append(m_asFields[m_aiIDs[i]]);
            sent.append(" = ?");
        }
        
        return sent.toString();               
    }
    
    public ComparatorCreator getComparatorCreator() {
        if (m_iOrderIndexes == null) {
            return null;
        } else {
            return new ComparatorCreatorBasic(m_asFields, m_aDatas, m_iOrderIndexes);
        }
    }
}
