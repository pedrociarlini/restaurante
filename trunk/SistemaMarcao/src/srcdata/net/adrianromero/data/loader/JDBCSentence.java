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

import java.sql.*;
import javax.sql.DataSource;
import net.adrianromero.basic.BasicException;

public abstract class JDBCSentence extends BaseSentence {
    
    // Conexion
    // protected DataSource m_ds;
    protected Connection m_c;
    
    /** Creates a new instance of BaseSentence */
    public JDBCSentence(Connection c) {
        super();
        m_c = c; 
    }   
    
    protected static final class JDBCDataResultSet implements DataResultSet {
        
        private ResultSet m_rs;
        private SerializerRead m_serread;
//        private int m_iColumnCount;

        public JDBCDataResultSet(ResultSet rs, SerializerRead serread) {
            m_rs = rs;
            m_serread = serread;
//            m_iColumnCount = -1;
        }
        public Integer getInt(int columnIndex) throws BasicException {
            try {
                int iValue = m_rs.getInt(columnIndex);
                return m_rs.wasNull() ? null : new Integer(iValue);
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }
        public String getString(int columnIndex) throws BasicException {
            try {
                return m_rs.getString(columnIndex);
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }    
        public Double getDouble(int columnIndex) throws BasicException {
            try {
                double dValue = m_rs.getDouble(columnIndex);
                return m_rs.wasNull() ? null : new Double(dValue);
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }   
        public Boolean getBoolean(int columnIndex) throws BasicException {
            try {
                boolean bValue = m_rs.getBoolean(columnIndex);
                return m_rs.wasNull() ? null : new Boolean(bValue);
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }
        public java.util.Date getTimestamp(int columnIndex) throws BasicException {        
            try {
                java.sql.Timestamp ts = m_rs.getTimestamp(columnIndex);
                return ts == null ? null : new java.util.Date(ts.getTime());
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }
        public byte[] getBytes(int columnIndex) throws BasicException {
            try {
                return m_rs.getBytes(columnIndex);
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }
        public Object getObject(int columnIndex) throws BasicException {
            try {
                return m_rs.getObject(columnIndex);
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }
        
//        public int getColumnCount() throws DataException {
//            try {
//                if (m_iColumnCount < 0) {
//                    m_iColumnCount = m_rs.getMetaData().getColumnCount();
//                }
//                return m_iColumnCount;
//            } catch (SQLException eSQL) {
//                throw new DataException(eSQL);
//            }
//        }
        
        public DataField[] getDataField() throws BasicException {
            try {
                ResultSetMetaData md = m_rs.getMetaData();
                DataField[] df = new DataField[md.getColumnCount()];
                for (int i = 0; i < df.length; i++) {
                    df[i] = new DataField();
                    df[i].Name = md.getColumnName(i + 1);
                    df[i].Size = md.getColumnDisplaySize(i + 1);
                    df[i].Type = md.getColumnType(i + 1);
                }
                return df;
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }
        
        public Object getCurrent() throws BasicException {
            return m_serread.readValues(this);
        }    
        public boolean next() throws BasicException {
            try {
                return m_rs.next();
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }
        public void close() throws BasicException {
            try {
                m_rs.close();
            } catch (SQLException eSQL) {
                throw new BasicException(eSQL);
            }
        }
        public int updateCount() throws BasicException {
            return -1; // es decir somos datos.
        }        
    }    
}
