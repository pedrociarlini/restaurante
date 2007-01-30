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

import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;
import javax.imageio.ImageIO;
import net.adrianromero.basic.BasicException;
import net.adrianromero.data.loader.*;
import net.adrianromero.tpv.data.DataLogic;

public class DataLogicSystem implements DataLogic {
    
    protected String m_sInitScript;
    private SentenceFind m_version;    
    private SentenceExec m_dummy;
    
    protected SentenceList m_peoplevisible;  
    private SentenceFind m_rolepermissions; 
    private SentenceExec m_changepassword;    
    private SentenceFind m_locationfind;
    
    private SentenceFind m_resourcebytes;
    private SentenceExec m_resourcebytesinsert;
    private SentenceExec m_resourcebytesupdate;
    
    protected SentenceFind m_nextclosedcash;
    protected SentenceFind m_activecash;
    protected SentenceExec m_insertcash;
    
    /** Creates a new instance of DataLogicSystem */
    public DataLogicSystem() {            
    }
    
    public void init(Connection cnt){
        
        m_version = new StaticSentence(cnt, "SELECT VERSION FROM TINAPOS", null, SerializerReadString.INSTANCE);
        m_dummy = new StaticSentence(cnt, "SELECT * FROM PEOPLE WHERE 1 = 0");
         
        m_resourcebytes = new PreparedSentence(cnt
            , "SELECT CONTENT FROM RESOURCES WHERE NAME = ?"
            , SerializerWriteString.INSTANCE
            , SerializerReadBytes.INSTANCE);
        
        Datas[] resourcedata = new Datas[] {Datas.STRING, Datas.INT, Datas.BYTES};
        m_resourcebytesinsert = new PreparedSentence(cnt
                , "INSERT INTO RESOURCES(NAME, RESTYPE, CONTENT) VALUES (?, ?, ?)"
                , new SerializerWriteBasic(resourcedata));
        m_resourcebytesupdate = new PreparedSentence(cnt
                , "UPDATE RESOURCES SET RESTYPE = ?, CONTENT = ? WHERE NAME = ?"
                , new SerializerWriteBasicExt(resourcedata, new int[] {1, 2, 0}));
        
        m_rolepermissions = new PreparedSentence(cnt
                , "SELECT PERMISSIONS FROM ROLES WHERE NAME = ?"
            , SerializerWriteString.INSTANCE
            , SerializerReadBytes.INSTANCE);     
        
        m_changepassword = new StaticSentence(cnt
                , "UPDATE PEOPLE SET APPPASSWORD = ? WHERE NAME = ?"
                ,new SerializerWriteBasic(new Datas[] {Datas.STRING, Datas.STRING}));
        
        m_activecash = new StaticSentence(cnt
            , "SELECT HOST, DATESTART, DATEEND FROM CLOSEDCASH WHERE MONEY = ?"
            , SerializerWriteInteger.INSTANCE
            , new SerializerReadBasic(new Datas[] {Datas.STRING, Datas.TIMESTAMP, Datas.TIMESTAMP}));            
        m_insertcash = new StaticSentence(cnt
                , "INSERT INTO CLOSEDCASH(MONEY, HOST, DATESTART, DATEEND) VALUES (?, ?, ?, ?)"
                , new SerializerWriteBasic(new Datas[] {Datas.INT, Datas.STRING, Datas.TIMESTAMP, Datas.TIMESTAMP}));
            
        m_locationfind = new StaticSentence(cnt
                , "SELECT NAME FROM LOCATIONS WHERE ID = ?"
                , SerializerWriteInteger.INSTANCE
                , SerializerReadString.INSTANCE);        
        
    }


    public String getInitScript() {
        return m_sInitScript;
    }
    
//    public abstract BaseSentence getShutdown();
    
    public final String findVersion() throws BasicException {
        return (String) m_version.find();
    }
    public final void execDummy() throws BasicException {
        m_dummy.exec();
    }
    public final List listPeopleVisible() throws BasicException {
        return m_peoplevisible.list();
    }        
    
    
    public final String findRolePermissions(String sRole) {
        
        byte[] str;
        
        // Primero trato de obtenerlo de la tabla de roles
        try {
            str = (byte[]) m_rolepermissions.find(sRole);        
        } catch (BasicException e) {
            str = null;                    
        }       
        
        // Segundo de los recursos por defecto...
        if (str == null || str.length == 0) {
            str = BatchSentence.getBytesFromResource("/net/adrianromero/templates/Role." + sRole + ".xml"); 
        }      
        
        // Al final transformo
        try {
            return str == null ? null : new String(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
    
    public final void execChangePassword(Object[] userdata) throws BasicException {
        m_changepassword.exec(userdata);
    }
    
    private final byte[] getResource(String sName, String sExt) {

        byte[] resource;
        
        // Primero trato de obtenerlo de la tabla de recursos
        try {
            resource = (byte[]) m_resourcebytes.find(sName);
        } catch (BasicException e) {
            resource = null;
        }
        
        // Segundo de los recursos por defecto...
        if (resource == null && sExt != null) {
            resource = BatchSentence.getBytesFromResource("/net/adrianromero/templates/" + sName + sExt); 
        }
        
        return resource;
    }
    
    public final void setResource(String sName, int iType, byte[] data) {
        
        Object[] value = new Object[] {sName, new Integer(iType), data}; // 2 binary
        try {
            if (m_resourcebytesupdate.exec(value) == 0) {
                m_resourcebytesinsert.exec(value);
            }
        } catch (BasicException e) {
        }
    }
    
    public final void setResourceAsBinary(String sName, byte[] data) {
        setResource(sName, 2, data);
    }
    
    public final byte[] getResourceAsBinary(String sName) {
        return getResource(sName, null);
    }
    
    public final String getResourceAsText(String sName) {
        try {
            byte[] str = getResource(sName, ".txt");
            return str == null ? null : new String(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
    
    public final String getResourceAsXML(String sName) {
        try {
            byte[] str = getResource(sName, ".xml");
            return str == null ? null : new String(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }    
    public final BufferedImage getResourceAsImage(String sName) {
        try {
            byte[] img = getResource(sName, ".png");
            return img == null ? null : ImageIO.read(new ByteArrayInputStream(img));
        } catch (IOException e) {
            return null;
        }
    }
    
    public final void setResourceAsProperties(String sName, Properties p) {
        if (p == null) {
            setResource(sName, 0, null); // texto
        } else {
            try {
                ByteArrayOutputStream o = new ByteArrayOutputStream();
                p.storeToXML(o, "Tina POS", "UTF8");
                setResource(sName, 0, o.toByteArray()); // El texto de las propiedades   
            } catch (IOException e) { // no deberia pasar nunca
            }            
        }
    }
    
    public final Properties getResourceAsProperties(String sName) {
        try {
            byte[] img = getResourceAsBinary(sName);
            if (img == null) {
                return null;
            } else {
                Properties p = new Properties();
                p.loadFromXML(new ByteArrayInputStream(img));
                return p;
            }
        } catch (IOException e) {
            return null;
        }
    }    

    public final Object[] findActiveCash(Integer iActiveCashIndex) throws BasicException {
        return (Object[]) m_activecash.find(iActiveCashIndex);
    }
    public final void execInsertCash(Object[] cash) throws BasicException {
        m_insertcash.exec(cash);
    }
    public final Integer getNextClosedCash() throws BasicException {
        return new Integer(((Integer) m_nextclosedcash.find()).intValue() + 1);       
    }   
    
    public final String findLocationName(Integer iLocation) throws BasicException {
        return (String) m_locationfind.find(iLocation);
    }    
}
