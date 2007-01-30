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

import java.sql.Connection;
import java.util.Date;
import net.adrianromero.tpv.data.DataLogic;
import net.adrianromero.tpv.printer.*;
import net.adrianromero.tpv.scale.DeviceScale;
import net.adrianromero.tpv.scanpal2.DeviceScanner;
import org.apache.velocity.context.Context;

public interface AppView extends AppProperties {
    
    public DeviceScale getDeviceScale();
    public DeviceTicket getDeviceTicket();
    public DeviceScanner getDeviceScanner();
      
    public Connection getConnection();
    public <T extends DataLogic> T lookupDataLogic(Class<T> clazz);
    public String evaluateTemplate(String sTemplateValue, Context c);
    public String evaluateResTemplate(String sTemplate, Context c);
    
    public void setActiveCash(Integer value, Date dStart, Date dEnd);
    public Integer getActiveCashIndex();
    public Date getActiveCashDateStart();
    public Date getActiveCashDateEnd();
    
    public Integer getInventoryLocation();
    
    public void waitCursorBegin();
    public void waitCursorEnd();   
}

