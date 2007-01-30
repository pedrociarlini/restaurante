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

import java.util.ResourceBundle;
import java.util.MissingResourceException;
import java.text.MessageFormat;

public class AppLocal {
    
    private static ResourceBundle m_messages = ResourceBundle.getBundle("net/adrianromero/tpv/i18n/messages");     
    
    /** Creates a new instance of AppLocal */
    private AppLocal() {
    }

    public static ResourceBundle getResourceBundle() {
        return m_messages;
    }   
    public static String getIntString(String sKey) {
        
        if (sKey == null) {
            return null;
        } else  {
            try {
                return m_messages.getString(sKey);
            } catch (MissingResourceException e) {
                return sKey;
            }
        }
    }
    public static String getIntString(String sKey, Object ... sValues) {
        
        if (sKey == null) {
            return null;
        } else  {
            try {
                return MessageFormat.format(m_messages.getString(sKey), sValues);
            } catch (MissingResourceException e) {
                return sKey;
            }
        }
    }
}
