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

package net.adrianromero.editor;

import net.adrianromero.basic.BasicException;

public class JEditorPassword extends JEditorString {
    
    private static final char ECHO_CHAR = '*';
    
    /** Creates a new instance of JEditorPassword */
    public JEditorPassword() {
    }
    
    protected String getTextEdit() {
        
        StringBuffer s = new StringBuffer();
        s.append("<html>");
        s.append(getEcho(m_svalue));
        if (m_cLastChar != '\u0000') {
//            char cnew = getKeyChar();
//            switch (cnew) {
//                case '\u0010': // set mode Abc1
//                    break;
//            
//            default:
                s.append("<font color=\"#a0a0a0\">");
                s.append(ECHO_CHAR);
                s.append("</font>");
//                break;
//            }
        }
        s.append("<font color=\"#a0a0a0\">_</font>");

        return s.toString(); 
    }
    
    public final String getPassword() {
        
        // como clave nunca devolvemos null
        String sPassword = getText();
        return sPassword == null ? "" : sPassword;     
    }    
    
    protected String getTextFormat() throws BasicException {
        return "<html>" + getEcho(m_svalue);
    }    
    
    private String getEcho(String sValue) {
        
        if (sValue == null) {
            return "";
        } else {
            char[] c = new char[sValue.length()];
            for(int i = 0; i < sValue.length(); i++) {
                c[i] = ECHO_CHAR;
            }
            return new String(c);
        }
    }
}
