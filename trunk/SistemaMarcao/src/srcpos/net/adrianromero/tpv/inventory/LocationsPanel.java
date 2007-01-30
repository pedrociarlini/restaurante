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

package net.adrianromero.tpv.inventory;

import net.adrianromero.data.loader.TableDefinition;
import net.adrianromero.data.user.BrowsableEditableData;
import net.adrianromero.data.user.EditorRecordBasic;
import net.adrianromero.data.user.EditorView;
import net.adrianromero.data.user.ListProvider;
import net.adrianromero.data.user.ListProviderECreator;
import net.adrianromero.data.user.SaveProvider;
import net.adrianromero.tpv.forms.AppLocal;
import net.adrianromero.tpv.forms.AppView;
import net.adrianromero.tpv.forms.SentenceContainer;
import net.adrianromero.tpv.panels.JPanelTable;

public class LocationsPanel extends JPanelTable {
    
    /** Creates a new instance of LocationsPanel */
    public LocationsPanel(AppView oApp) {
        super(oApp);
        
        TableDefinition tlocations = m_App.lookupDataLogic(SentenceContainer.class).getTableLocations();
        
        ListProvider lpr = new ListProviderECreator(tlocations);
        SaveProvider spr = new SaveProvider(tlocations);        
        
        EditorView view = new LocationsView();
        BrowsableEditableData bd = new BrowsableEditableData(lpr, spr, new EditorRecordBasic(view));
                
        // el centro
        addEditor(view.getComponent());
        
        initNavigation(bd, tlocations.getVectorerBasic(), tlocations.getComparatorCreator(), tlocations.getRenderStringBasic());
    }
    
    public String getTitle() {
        return AppLocal.getIntString("Menu.Locations");
    }  
    
}
