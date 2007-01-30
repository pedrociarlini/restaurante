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

package net.adrianromero.tpv.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.adrianromero.basic.BasicException;
import net.adrianromero.tpv.forms.AppLocal;

import net.adrianromero.tpv.forms.DataLogicSystem;
import net.adrianromero.tpv.forms.SentenceContainer;
import net.adrianromero.tpv.admin.DataLogicAdmin;
import net.adrianromero.tpv.forms.DataLogicSystemHSQLDB;
import net.adrianromero.tpv.forms.DataLogicSystemMySQL;
import net.adrianromero.tpv.forms.DataLogicSystemOracle;
import net.adrianromero.tpv.forms.DataLogicSystemPostgreSQL;
import net.adrianromero.tpv.forms.SentenceContainerHSQLDB;
import net.adrianromero.tpv.forms.SentenceContainerMySQL;
import net.adrianromero.tpv.forms.SentenceContainerOracle;
import net.adrianromero.tpv.forms.SentenceContainerPostgreSQL;
import net.adrianromero.tpv.thirdparties.DataLogicThirdParties;

public class DataLogicProvider {
    
    private Connection m_cnt = null;
    
    private Map<Class<?>, Object> m_loaded;    
    private Map<Class<?>, Class<? extends DataLogic>> m_Configuration;
    
    /** Creates a new instance of DataLogicProvider */
    public DataLogicProvider(Connection cnt) throws BasicException {
        
        m_loaded = new HashMap<Class<?>, Object>();
        m_Configuration = new HashMap<Class<?>, Class<? extends DataLogic>>();

        
        try {
            m_cnt = cnt;
            String m_sdbmanager = cnt.getMetaData().getDatabaseProductName();
            
            // DAOs independientes de la base de datos
            m_Configuration.put(DataLogicAdmin.class, DataLogicAdmin.class);
            m_Configuration.put(DataLogicThirdParties.class, DataLogicThirdParties.class);               

            if ("HSQL Database Engine".equals(m_sdbmanager)) {
                m_Configuration.put(DataLogicSystem.class, DataLogicSystemHSQLDB.class);
                m_Configuration.put(SentenceContainer.class, SentenceContainerHSQLDB.class);
            } else if ("MySQL".equals(m_sdbmanager)) {
                m_Configuration.put(DataLogicSystem.class, DataLogicSystemMySQL.class);
                m_Configuration.put(SentenceContainer.class, SentenceContainerMySQL.class);
            } else if ("PostgreSQL".equals(m_sdbmanager)) {
                m_Configuration.put(DataLogicSystem.class, DataLogicSystemPostgreSQL.class);
                m_Configuration.put(SentenceContainer.class, SentenceContainerPostgreSQL.class);
            } else if ("Oracle".equals(m_sdbmanager)) {
                m_Configuration.put(DataLogicSystem.class, DataLogicSystemOracle.class);
                m_Configuration.put(SentenceContainer.class, SentenceContainerOracle.class);
            } else {
                throw new BasicException(AppLocal.getIntString("message.databasenotsupported", m_sdbmanager));
            }   
        } catch (SQLException eSQL) {
            throw new BasicException(eSQL);
        }
        
    }
    
    public <T extends DataLogic> T lookup(Class<T> clazz){        
        
        Object datalogic = m_loaded.get(clazz);
        
        if (datalogic == null) {
            
            Class<? extends DataLogic> c = m_Configuration.get(clazz);
            if ( c == null) {
                // No hemos encontrado la clase a cargar
                return null;
            } else {
                try {
                    DataLogic d = c.newInstance();
                    d.init(m_cnt);
                    m_loaded.put(clazz, datalogic);   
                    datalogic = d;
                } catch (Exception e) {
                    return null;
                }
            }
        }        
        return (T) datalogic;
    }    
 
}
