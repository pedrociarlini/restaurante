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

package net.adrianromero.tpv.thirdparties;
        
import net.adrianromero.tpv.forms.AppLocal;
import net.adrianromero.tpv.forms.AppView;
import net.adrianromero.tpv.panels.*;
import net.adrianromero.data.loader.TableDefinition;
import net.adrianromero.data.user.SaveProvider;
import net.adrianromero.data.user.BrowsableEditableData;
import net.adrianromero.data.user.EditorRecordBasic;
import net.adrianromero.data.user.ListProvider;
import net.adrianromero.data.user.ListProviderECreator;

public class ThirdPartiesPanel extends JPanelTable {
    
    private ThirdPartiesView jeditor;
    
    /** Creates a new instance of JPanelPeople */
    public ThirdPartiesPanel(AppView oApp) {
        super(oApp);
        
        TableDefinition tthirdparties = m_App.lookupDataLogic(DataLogicThirdParties.class).getTableThirdParties();
        
        ListProvider lpr = new ListProviderECreator(tthirdparties);
        SaveProvider spr = new SaveProvider(tthirdparties);        
        
        jeditor = new ThirdPartiesView(m_App);    
        BrowsableEditableData bd = new BrowsableEditableData(lpr, spr, new EditorRecordBasic(jeditor));
                
        // el centro
        addEditor(jeditor);
        
        initNavigation(bd, tthirdparties.getVectorerBasic(), tthirdparties.getComparatorCreator(), tthirdparties.getRenderStringBasic());
    }
    
//    public void activate() {
//        
//        // jeditor.activate(); // primero el editor    
//        super.activate(); // y luego cargamos los datos
//    }      
    
    public String getTitle() {
        return AppLocal.getIntString("Menu.ThirdPartiesManagement");
    }     
}
