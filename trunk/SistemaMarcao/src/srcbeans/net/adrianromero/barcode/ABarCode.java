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

package net.adrianromero.barcode;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
//import java.awt.font.LineMetrics;
import java.awt.Dimension;

/**
 *
 * @author  Adrian
 */
public abstract class ABarCode {
    
    protected int m_x;
    protected int m_y;
    
    private Graphics m_CurrentG;
    private int m_iCurrentX;
    private boolean m_bCurrentBlack;
    
    private Font m_FontBase = new Font("Dialog", 0, 10);

    protected int m_iBarHeight = 40;
    protected int m_iBarHeightPlus = 8;
    protected int m_iFontHeight = 10; // fijado
    
    protected int m_iBarWidth = 1;
    
    protected int m_iBorder = 10;
    
    private Color m_Background = Color.white;
    private Color m_Foreground = Color.black;
    private boolean m_bOpaque = true;
    
    
    /** Creates a new instance of ABarCode */
    public ABarCode() {
        m_CurrentG = null;
        m_iCurrentX = 0;
        m_bCurrentBlack = true;
    }
    
    public boolean isOpaque() {
        return m_bOpaque;
    }
    public void setOpaque(boolean value) {
        m_bOpaque = value;
    }
    
    private void paintBarLine(int iWidth, int iTop, int iHeight) {
        
        if (m_bCurrentBlack) {
            m_CurrentG.setColor(m_Foreground);
            m_CurrentG.fillRect(m_x + m_iBorder + m_iCurrentX, m_y + m_iBorder + iTop, iWidth, iHeight);
        }

        m_iCurrentX += iWidth;
        m_bCurrentBlack = !m_bCurrentBlack;
    }
    
    private void paintBarLines(int[] iWidths, int iTop, int iHeight) {        
        for (int i = 0; i < iWidths.length; i++) {
            paintBarLine(m_iBarWidth * iWidths[i], iTop, iHeight);            
        }
    }
    
    private void paintCharsString(String sText, int iTop, int iLeft, int iWidth) {
        
        m_CurrentG.setColor(m_Foreground);
        m_CurrentG.setFont(m_FontBase);
        FontMetrics fm = m_CurrentG.getFontMetrics();
        
        // int iFontHeight = fm.getHeight();
        int iFontWidth = fm.stringWidth(sText);
        
        m_CurrentG.drawString(sText, m_iBorder + m_x + iLeft + ((iWidth - iFontWidth) / 2), m_iBorder + m_y + iTop + m_iFontHeight);
        //g.drawRect(m_iBorder + iLeft + ((iWidth - iFontWidth) / 2), m_iBorder + m_iBarHeight, iFontWidth, m_iFontHeight);
    }
     
    protected void paintWhiteArea(int iWidth) {
        m_bCurrentBlack = true;
        m_iCurrentX += iWidth;
    }
    protected void paintCharsNormal(int[] iWidths) {
        paintBarLines(iWidths, 0,  m_iBarHeight);
    }
         
    protected void paintCharsPlus(int[] iWidths) {
        paintBarLines(iWidths, 0, m_iBarHeight + m_iBarHeightPlus);
    }
    
    protected void paintCharsLow(int[] iWidths) {
        paintBarLines(iWidths, m_iFontHeight, m_iBarHeight + m_iBarHeightPlus - m_iFontHeight);
    }
    protected void paintCharsStringDown(String sText, int iLeft, int iWidth) {
        
        paintCharsString(sText, m_iBarHeight, iLeft, iWidth);
    }
    protected void paintCharsStringTop(String sText, int iLeft, int iWidth) {
        
        paintCharsString(sText, 0, iLeft, iWidth);
    }    
    
    protected int char2index(char c) {
        return (int)c - 0x30;
    }
    protected char index2char(int i) {
        return (char)(i + 0x30);
    }
    
    public void paint(Graphics g) {
        paint(g, 0, 0);
    }
    public void paint(Graphics g, int x, int y) {
        
        if (m_bOpaque) {
            g.setColor(m_Background);
            g.fillRect(x, y, getWidth(), getHeight());
        }
        
        m_x = x;
        m_y = y;
        
        m_iCurrentX = 0;
        m_bCurrentBlack = true;        
        m_CurrentG = g;
        
        paintBars();
        paintCode();
        
        m_CurrentG = null;
    }
    
    public Dimension getBounds() {
        return new Dimension(m_iBorder + getWidth() + m_iBorder, m_iBorder + getHeight() + m_iBorder);
    }
    
    public int getHeight() {
        return m_iBarHeight + m_iFontHeight + 2 * m_iBorder;
    }
    public int getWidth() {
        return getCodeWidth() + 2 * m_iBorder;
    }
    
    public abstract void setCode(String value);    
    public abstract String getCode();
    public abstract int getCodeWidth();       
    
    protected abstract void paintBars();
    protected abstract void paintCode();   
}
