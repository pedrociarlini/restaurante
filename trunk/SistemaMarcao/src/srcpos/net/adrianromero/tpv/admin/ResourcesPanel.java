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

import net.adrianromero.tpv.forms.*;
import net.adrianromero.tpv.panels.*;
import net.adrianromero.tpv.ticket.*;
import net.adrianromero.data.loader.TableDefinition;
import net.adrianromero.data.user.*;

public class ResourcesPanel extends JPanelTable {


    /** Creates a new instance of JPanelResources */
    public ResourcesPanel(AppView oApp) {
        super(oApp);
        
        TableDefinition tresources = m_App.lookupDataLogic(DataLogicAdmin.class).getTableResources();
        
        ListProvider lpr = new ListProviderECreator(tresources);
        SaveProvider spr = new SaveProvider(tresources);        
        
        ResourcesView jeditor = new ResourcesView();    
        BrowsableEditableData bd = new BrowsableEditableData(lpr, spr, new EditorRecordBasic(jeditor));
                
        // el centro
        addEditor(jeditor);
        
        initNavigation(bd, tresources.getVectorerBasic(), tresources.getComparatorCreator(), tresources.getRenderStringBasic());
    }
    public String getTitle() {
        return AppLocal.getIntString("Menu.Resources");
    }        
}
