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

import java.io.*;
import java.sql.Connection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.adrianromero.basic.BasicException;

public class BatchSentence extends BaseSentence {
    
    // Conexion
    protected Connection m_c;
    protected String m_sResScript;
    
    /** Creates a new instance of BatchSentence */
    public BatchSentence(Connection cnt, String resscript) {
        m_c = cnt;
        m_sResScript = resscript;
    }
    
    public class ExceptionsResultSet implements DataResultSet {
        
        List l;
        int m_iIndex;
        
        public ExceptionsResultSet(List l) {
            this.l = l;
            m_iIndex = -1;
        }
        
        public Integer getInt(int columnIndex) throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }
        public String getString(int columnIndex) throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }
        public Double getDouble(int columnIndex) throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }
        public Boolean getBoolean(int columnIndex) throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }
        public java.util.Date getTimestamp(int columnIndex) throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }

        //public java.io.InputStream getBinaryStream(int columnIndex) throws DataException;
        public byte[] getBytes(int columnIndex) throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }
        public Object getObject(int columnIndex) throws BasicException  {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }

    //    public int getColumnCount() throws DataException;
        public DataField[] getDataField() throws BasicException {
            throw new BasicException(LocalRes.getIntString("exception.nodataset"));
        }        
        
        
        public Object getCurrent() throws BasicException {
            if (m_iIndex < 0 || m_iIndex >= l.size()) {
                throw new BasicException(LocalRes.getIntString("exception.outofbounds"));
            } else {
                return l.get(m_iIndex);
            }
        }
        
        public boolean next() throws BasicException {
            return ++m_iIndex < l.size();
        }
        public void close() throws BasicException {
        }
        public int updateCount() {
            return 0;
        }
    }
    
    public final void closeExec() throws BasicException {
    }
    
    public final DataResultSet moreResults() throws BasicException {
        return null;
    }
    
    public DataResultSet openExec(Object params) throws BasicException {
        
        InputStream in = BatchSentence.class.getResourceAsStream(m_sResScript);
        
        if (in == null) {
            throw new BasicException(LocalRes.getIntString("exception.nosentencesfile"));
        } else {
            BufferedReader br = new BufferedReader(new InputStreamReader(in)); // se toma el charset del sistema.

            String sLine;
            StringBuffer sSentence = new StringBuffer();
            List aExceptions = new ArrayList();
                
            try {
                while ((sLine = br.readLine()) != null) {
                    sLine = sLine.trim();
                    if (!sLine.equals("") && !sLine.startsWith("--")) {
                        // No es un comentario ni linea vacia
                        if (sLine.endsWith(";")) {
                            // ha terminado la sentencia
                            sSentence.append(sLine.substring(0, sLine.length() - 1));                            
                            
                            // Leo los parametros
                            Pattern pattern = Pattern.compile("\\$FILE\\{([^}]+)\\}");
                            Matcher matcher = pattern.matcher(sSentence.toString());
                            List paramlist = new ArrayList();

                            // Replace all occurrences of pattern in input
                            StringBuffer buf = new StringBuffer();
                            while (matcher.find()) {
                                paramlist.add(getBytesFromResource(matcher.group(1)));
                                matcher.appendReplacement(buf, "?");
                            }
                            matcher.appendTail(buf); 

                            // La disparo
                            try {
                                BaseSentence sent;
                                if (paramlist.size() == 0) {
                                    sent = new StaticSentence(m_c, buf.toString());
                                    sent.exec();
                                } else {
                                    sent = new PreparedSentence(m_c, buf.toString(), SerializerWriteBuilder.INSTANCE);
                                    sent.exec(new BlobsParams(paramlist));
                                }
                            } catch (BasicException eD) {
                                aExceptions.add(eD);
                            }
                            sSentence = new StringBuffer();

                        } else {
                            // la sentencia continua en la linea siguiente
                            sSentence.append(sLine);
                        }
                    }
                }
                
                br.close();

            } catch (IOException eIO) {
                throw new BasicException(LocalRes.getIntString("exception.noreadfile"), eIO);
            }

            if (sSentence.length() > 0) {
                // ha quedado una sentencia inacabada
                aExceptions.add(new BasicException(LocalRes.getIntString("exception.nofinishedfile")));
            }   
            
            return new ExceptionsResultSet(aExceptions);
        }
    }    
    
    public static byte[] getBytesFromResource(String sFile) {
        
        InputStream in = BatchSentence.class.getResourceAsStream(sFile);
        
        if (in == null) {
            return null;
        } else {        
            byte[] buffer = new byte[1024];
            byte[] resource = new byte[0];             
            int n;

            try {
                while ((n = in.read(buffer)) != -1) {
                    byte[] b = new byte[resource.length + n];
                    System.arraycopy(resource, 0, b, 0, resource.length);
                    System.arraycopy(buffer, 0, b, resource.length, n);
                    resource = b;
                }
                in.close();
                return resource;
            } catch (IOException e) {
                return new byte[0];
           }
        }
    }
    
    private static class BlobsParams implements SerializableWrite {
        
        private List l;
        
        public BlobsParams(List l) {
            this.l = l;
        }
        
        public void writeValues(DataWrite dp) throws BasicException {
            for (int i = 0; i < l.size(); i++) {
                dp.setBytes(i + 1, (byte[]) l.get(i));
            }
        }
    }
}
