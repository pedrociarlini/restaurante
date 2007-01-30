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
 * ABarCodeEAN13.java
 *
 * Created on 31 de julio de 2003, 19:08
 */

package net.adrianromero.barcode;

/**
 *
 * @author  Adrian
 */
public class ABarCodeEAN13 extends ABarCode {

    private final static int[][] EANTableOdd = {
        {3, 2, 1, 1}, {2, 2, 2, 1}, {2, 1, 2, 2}, {1, 4, 1, 1}, {1, 1, 3, 2}, {1, 2, 3, 1}, {1, 1, 1, 4}, {1, 3, 1, 2}, {1, 2, 1, 3}, {3, 1, 1, 2}
    };
    private final static int[][] EANTableEven = {
        {1, 1, 2, 3}, {1, 2, 2, 2}, {2, 2, 1, 2}, {1, 1, 4, 1}, {2, 3, 1, 1}, {1, 3, 2, 1}, {4, 1, 1, 1}, {2, 1, 3, 1}, {3, 1, 2, 1}, {2, 1, 1, 3}
    };
    private final static int[][] EANTableCode = {
        {0, 0, 0, 0, 0}, {0, 1, 0, 1, 1}, {0, 1, 1, 0, 1}, {0, 1, 1, 1, 0}, {1, 0, 0, 1, 1}, {1, 1, 0, 0, 1}, {1, 1, 1, 0, 0}, {1, 0, 1, 0, 1}, {1, 0, 1, 1, 0}, {1, 1, 0, 1, 0}
    };
    private final static int[][] EANTableSup2 = {
        {0, 0}, {0, 1}, {1, 0}, {0, 0}
    };
    private final static int[][] EANTableSup5 = {
        {1, 1, 0, 0, 0}, {1, 0, 1, 0, 0}, {1, 0, 0, 1, 0}, {1, 0, 0, 0, 1}, {0, 1, 1, 0, 0}, {0, 0, 1, 1, 0}, {0, 0, 0, 1, 1}, {0, 1, 0, 1, 0}, {0, 1, 0, 0, 1}, {0, 0, 1, 0, 1}
    };
    
    private String m_sCodeCode;
    private String m_sCodeSup;
    
    /** Creates a new instance of ABarCodeEAN13 */
    public ABarCodeEAN13() {
        super();
    }

    public int getCodeWidth() {
        
        int iWidth = 0;
        
        if (m_sCodeCode != null && m_sCodeCode.length() == 13) {
            iWidth += 109; // 7 (espacio de atras) + 13 * 7 (barras) + 11 (fijos)
        }
        
        if (m_sCodeSup != null && m_sCodeSup.length() == 2) { // 2 suplement
            iWidth += 27; // 20 suplemento 7 espacio final
        }
        
        if (m_sCodeSup != null && m_sCodeSup.length() == 5) { // 5 suplement
            iWidth += 54; // 47 suplemento 7 espacio final
        }
        
        return iWidth; 
    }
    
    public String getCode() {
        return m_sCodeCode + m_sCodeSup;
    }
    
    public void setCode(String sCode) {
        
        switch (sCode.length()) {
            case 12:
                m_sCodeSup = "";
                m_sCodeCode = UPCEANAdd(sCode);
                break;
            case 13:
                m_sCodeSup = "";
                m_sCodeCode = sCode;
                break;
            case 14:
                m_sCodeSup = sCode.substring(12,14);
                m_sCodeCode = UPCEANAdd(sCode.substring(0, 12));
                break;
            case 15:
                m_sCodeSup = sCode.substring(13,15);
                m_sCodeCode = sCode.substring(0, 13);
                break;
            case 17:
                m_sCodeSup = sCode.substring(12,17);
                m_sCodeCode = UPCEANAdd(sCode.substring(0, 12));
                break;
            case 18:
                m_sCodeSup = sCode.substring(13,18);
                m_sCodeCode = sCode.substring(0, 13);
                break;
        }
    }
    
    protected void paintBars() {
       
        if (m_sCodeCode != null && m_sCodeCode.length() == 13) {
            paintWhiteArea(7);

            paintCharsPlus(new int[]{1, 1, 1}); 

            int[] iDecoder = EANTableCode[char2index(m_sCodeCode.charAt(0))];

            paintCharsNormal(EANTableOdd[char2index(m_sCodeCode.charAt(1))]);

            for (int i = 2; i < 7; i++) {
                if (iDecoder[i - 2] == 0) {
                    paintCharsNormal(EANTableOdd[char2index(m_sCodeCode.charAt(i))]);
                } else {
                    paintCharsNormal(EANTableEven[char2index(m_sCodeCode.charAt(i))]);
                }
            }       

            paintCharsPlus(new int[]{1, 1, 1, 1, 1});   

            for (int i = 7; i < 13; i++){
                paintCharsNormal(EANTableOdd[char2index(m_sCodeCode.charAt(i))]);
            }

            paintCharsPlus(new int[]{1, 1, 1});
            
            paintWhiteArea(7);
        }
        
        if (m_sCodeSup != null && m_sCodeSup.length() == 2) { // 2 suplement
            paintCharsLow(new int[] {1, 1, 2});
            
            int iRemainder = (10 * char2index(m_sCodeSup.charAt(0)) + char2index(m_sCodeSup.charAt(1))) % 4;
            int[] iDecoder = EANTableSup2[iRemainder];
            for (int i = 0; i < 2; i++) {
                if (iDecoder[i] == 0) {
                    paintCharsLow(EANTableOdd[char2index(m_sCodeSup.charAt(i))]);
                } else {
                    paintCharsLow(EANTableEven[char2index(m_sCodeSup.charAt(i))]);
                }
                if (i < 1) { // el ultimo no
                    paintCharsLow(new int[] {1, 1});
                }
            }
            
            paintWhiteArea(7);
        }
                
        if (m_sCodeSup != null && m_sCodeSup.length() == 5) { // 5 suplement
            paintCharsLow(new int[] {1, 1, 2});

            int iRemainder = UPCEANSup(m_sCodeSup);
            
            System.out.println(iRemainder);
            
            int[] iDecoder = EANTableSup5[iRemainder];
            for (int i = 0; i < 5; i++) {
                if (iDecoder[i] == 0) {
                    paintCharsLow(EANTableOdd[char2index(m_sCodeSup.charAt(i))]);
                } else {
                    paintCharsLow(EANTableEven[char2index(m_sCodeSup.charAt(i))]);
                }
                if (i < 4) { // el ultimo no
                    paintCharsLow(new int[] {1, 1});
                }
            }
            
            paintWhiteArea(7);
        }
    }
    
    protected void paintCode() {
        
        if (m_sCodeCode != null && m_sCodeCode.length() == 13) {
            // El Texto
            paintCharsStringDown(m_sCodeCode.substring(0, 1), 0, 7); 
            paintCharsStringDown(m_sCodeCode.substring(1, 7), 10, 42); // 42 = 6 * 7
            paintCharsStringDown(m_sCodeCode.substring(7, 13), 57, 42); 
        }
        if (m_sCodeSup != null && m_sCodeSup.length() == 2) { // 2 suplement
            paintCharsStringTop(m_sCodeSup, 109, 20); 
        }
        if (m_sCodeSup != null && m_sCodeSup.length() == 5) { // 5 suplement
            paintCharsStringTop(m_sCodeSup, 109, 47); 
        }
    }
    
    private int UPCEANSup(String value) {
        
        int iCheckSum = 0;
        int iDigit;
        
        for (int i = 0; i < value.length(); i++){
            iDigit = char2index(value.charAt(i));
            iCheckSum += (i % 2 == 0) ? iDigit * 3 : iDigit * 9;
        }
        
        return iCheckSum % 10;
    }  
    
    private String UPCEANAdd(String value) {
        
        int iCheckSum = 0;
        int iDigit;
        
        for (int i = 0; i < value.length(); i++){
            iDigit = char2index(value.charAt(i));
            iCheckSum += (i % 2 == 0) ? 10 - iDigit : 3 * ((9- iDigit) % 3) + ((iDigit + 2) / 3);
        }
        
        return value + String.valueOf(index2char(iCheckSum % 10));
    }   
}
