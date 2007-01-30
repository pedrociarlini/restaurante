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

package net.adrianromero.tpv.admin;

import java.sql.Connection;
import net.adrianromero.data.loader.Datas;
import net.adrianromero.data.loader.TableDefinition;
import net.adrianromero.format.Formats;
import net.adrianromero.tpv.data.DataLogic;

public class DataLogicAdmin implements DataLogic {
    
    private TableDefinition m_tpeople;
    private TableDefinition m_troles;
    private TableDefinition m_tresources;    
    
    /** Creates a new instance of DataLogicAdmin */
    public DataLogicAdmin() {
    }
    
    public void init(Connection cnt){

        m_tpeople = new TableDefinition(cnt,
            "PEOPLE"
            , new int[] {0}
            , new String[] {"NAME", "APPPASSWORD", "ROLE", "VISIBLE", "IMAGE"}
            , new Datas[] {Datas.STRING, Datas.STRING, Datas.STRING, Datas.BOOLEAN, Datas.IMAGE}
            , new Formats[] {Formats.STRING}
            , new Formats[] {Formats.STRING}
            , new int[] {0, 2}
        );   
        
        m_troles = new TableDefinition(cnt,
            "ROLES"
            , new int[] {0}
            , new String[] {"NAME", "PERMISSIONS"}
            , new Datas[] {Datas.STRING, Datas.BYTES}
            , new Formats[] {Formats.STRING}
            , new Formats[] {Formats.STRING}
            , new int[] {0}
        );  
        
        m_tresources = new TableDefinition(cnt,
            "RESOURCES"
            , new int[] {0}
            , new String[] {"NAME", "RESTYPE", "CONTENT"}
            , new Datas[] {Datas.STRING, Datas.INT, Datas.BYTES}
            , new Formats[] {Formats.STRING}
            , new Formats[] {Formats.STRING}
            , new int[] {0, 1}
        );           
    }
       
    public final TableDefinition getTablePeople() {
        return m_tpeople;
    }    
    public final TableDefinition getTableRoles() {
        return m_troles;
    }
    public final TableDefinition getTableResources() {
        return m_tresources;
    }     
}
