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

/*
 * ABarCode128.java
 *
 * Created on 2,  de agosto de 2, 003, , 2, 3, :53, 
 */

package net.adrianromero.barcode;

import java.util.ArrayList;

/**
 *
 * @author  Adrian
 */
public class ABarCode128 extends ABarCode {
    
    private final static int[][] Code128Table = {
        {2, 1, 2, 2, 2, 2}, {2, 2, 2, 1, 2, 2}, {2, 2, 2, 2, 2, 1}, {1, 2, 1, 2, 2, 3}, {1, 2, 1, 3, 2, 2}, {1, 3, 1, 2, 2, 2},
        {1, 2, 2, 2, 1, 3}, {1, 2, 2, 3, 1, 2}, {1, 3, 2, 2, 1, 2}, {2, 2, 1, 2, 1, 3}, {2, 2, 1, 3, 1, 2}, {2, 3, 1, 2, 1, 2},
        {1, 1, 2, 2, 3, 2}, {1, 2, 2, 1, 3, 2}, {1, 2, 2, 2, 3, 1}, {1, 1, 3, 2, 2, 2}, {1, 2, 3, 1, 2, 2}, {1, 2, 3, 2, 2, 1},
        {2, 2, 3, 2, 1, 1}, {2, 2, 1, 1, 3, 2}, {2, 2, 1, 2, 3, 1}, {2, 1, 3, 2, 1, 2}, {2, 2, 3, 1, 1, 2}, {3, 1, 2, 1, 3, 1},
        {3, 1, 1, 2, 2, 2}, {3, 2, 1, 1, 2, 2}, {3, 2, 1, 2, 2, 1}, {3, 1, 2, 2, 1, 2}, {3, 2, 2, 1, 1, 2}, {3, 2, 2, 2, 1, 1},
        {2, 1, 2, 1, 2, 3}, {2, 1, 2, 3, 2, 1}, {2, 3, 2, 1, 2, 1}, {1, 1, 1, 3, 2, 3}, {1, 3, 1, 1, 2, 3}, {1, 3, 1, 3, 2, 1},
        {1, 1, 2, 3, 1, 3}, {1, 3, 2, 1, 1, 3}, {1, 3, 2, 3, 1, 1}, {2, 1, 1, 3, 1, 3}, {2, 3, 1, 1, 1, 3}, {2, 3, 1, 3, 1, 1},
        {1, 1, 2, 1, 3, 3}, {1, 1, 2, 3, 3, 1}, {1, 3, 2, 1, 3, 1}, {1, 1, 3, 1, 2, 3}, {1, 1, 3, 3, 2, 1}, {1, 3, 3, 1, 2, 1},
        {3, 1, 3, 1, 2, 1}, {2, 1, 1, 3, 3, 1}, {2, 3, 1, 1, 3, 1}, {2, 1, 3, 1, 1, 3}, {2, 1, 3, 3, 1, 1}, {2, 1, 3, 1, 3, 1},
        {3, 1, 1, 1, 2, 3}, {3, 1, 1, 3, 2, 1}, {3, 3, 1, 1, 2, 1}, {3, 1, 2, 1, 1, 3}, {3, 1, 2, 3, 1, 1}, {3, 3, 2, 1, 1, 1},
        {3, 1, 4, 1, 1, 1}, {2, 2, 1, 4, 1, 1}, {4, 3, 1, 1, 1, 1}, {1, 1, 1, 2, 2, 4}, {1, 1, 1, 4, 2, 2}, {1, 2, 1, 1, 2, 4},
        {1, 2, 1, 4, 2, 1}, {1, 4, 1, 1, 2, 2}, {1, 4, 1, 2, 2, 1}, {1, 1, 2, 2, 1, 4}, {1, 1, 2, 4, 1, 2}, {1, 2, 2, 1, 1, 4},
        {1, 2, 2, 4, 1, 1}, {1, 4, 2, 1, 1, 2}, {1, 4, 2, 2, 1, 1}, {2, 4, 1, 2, 1, 1}, {2, 2, 1, 1, 1, 4}, {4, 1, 3, 1, 1, 1},
        {2, 4, 1, 1, 1, 2}, {1, 3, 4, 1, 1, 1}, {1, 1, 1, 2, 4, 2}, {1, 2, 1, 1, 4, 2}, {1, 2, 1, 2, 4, 1}, {1, 1, 4, 2, 1, 2},
        {1, 2, 4, 1, 1, 2}, {1, 2, 4, 2, 1, 1}, {4, 1, 1, 2, 1, 2}, {4, 2, 1, 1, 1, 2}, {4, 2, 1, 2, 1, 1}, {2, 1, 2, 1, 4, 1},
        {2, 1, 4, 1, 2, 1}, {4, 1, 2, 1, 2, 1}, {1, 1, 1, 1, 4, 3}, {1, 1, 1, 3, 4, 1}, {1, 3, 1, 1, 4, 1}, {1, 1, 4, 1, 1, 3},
        {1, 1, 4, 3, 1, 1}, {4, 1, 1, 1, 1, 3}, {4, 1, 1, 3, 1, 1}, {1, 1, 3, 1, 4, 1}, {1, 1, 4, 1, 3, 1}, {3, 1, 1, 1, 4, 1},
        {4, 1, 1, 1, 3, 1}, {2, 1, 1, 4, 1, 2}, {2, 1, 1, 2, 1, 4}, {2, 1, 1, 2, 3, 2}
    };
    
    private final static int CODENONE = 0;
    private final static int CODEA = 1;
    private final static int CODEB = 2;
            
    private int[] m_iCode;
    private String m_sCodeCode;
    
    /** Creates a new instance of ABarCode128 */
    public ABarCode128() {
        super();
        m_iCode = new int[] {103};
        m_sCodeCode = "";
    }
    
    public String getCode() {
        return m_sCodeCode;
    }
    
    public void setCode(String sCode) {
        
        ArrayList l = new ArrayList();
        StringBuffer sb = new StringBuffer();
        int iCurrent = CODEA;   
        Check128 check = new Check128();
        
        for (int i= 0; i < sCode.length(); i++) {
            char c = sCode.charAt(i);
            
            if (c < '\u0020') {
                if (iCurrent != CODEA) {
                    l.add(new Integer(101));
                    iCurrent = CODEA;
                    check.add(101);
                }
               l.add(new Integer((int) c + 0x0040));
               check.add((int) c + 0x0040);
               sb.append(' ');
            } else if (c < '\u0040') {
                if (iCurrent != CODEA && iCurrent != CODEB) {
                    l.add(new Integer(100));
                    iCurrent = CODEB;
                    check.add(100);
                }
               l.add(new Integer((int) c - 0x0020));
               check.add((int) c - 0x0020);
               sb.append(c);                
            } else if (c < '\u0080') {
                if (iCurrent != CODEB) {
                    l.add(new Integer(100));
                    iCurrent = CODEB;
                    check.add(100);
                }
               l.add(new Integer((int) c - 0x0020));
               check.add((int) c - 0x0020);
               sb.append(c);                
            }
        }       
        
        l.add(new Integer(check.getCheck()));
        
        m_iCode = new int[l.size()];
        for (int i = 0; i < l.size(); i++) {
            m_iCode[i] = ((Integer) l.get(i)).intValue();
        }
        m_sCodeCode = sb.toString();
    }
   
    protected void paintBars() {
             
        paintWhiteArea(10);
        
        paintCharsNormal(new int[] {2, 1, 1, 4, 1, 2}); // START-A
        for (int i = 0 ; i < m_iCode.length; i++) {
            paintCharsNormal(Code128Table[m_iCode[i]]);
        }
        paintCharsNormal(new int[] {2, 3, 3, 1, 1, 1}); // STOP Character
        paintCharsNormal(new int[] {2}); // Termination Bar
               
        paintWhiteArea(10);
    }    
    
    protected void paintCode() {
        paintCharsStringDown(m_sCodeCode, 0, getWidth()); 
    }

    public int getCodeWidth() {
        return 11 * m_iCode.length + 43;
    }
    
    private static class Check128 {
        private int m_iCounter = 0;
        private int m_iCheck = 0;
        
        public void add(int i) {
            m_iCounter++;
            m_iCheck += i * m_iCounter;
        }
        
        public int getCheck() {
            return m_iCheck % 103;
        }    
    }
}
