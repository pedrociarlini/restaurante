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

package net.adrianromero.tpv.printer.escpos;

import java.io.*;
import net.adrianromero.tpv.printer.*;

public class PrinterWritterFile extends PrinterWritter {
    
    private String m_sFilePrinter;
    private OutputStream m_out;
     
//    /** Creates a new instance of PrinterWritterFile */
//    public PrinterWritterFile(String sFilePrinter) throws TicketPrinterException {
//        try {  
//            OutputStream oSerialOutPrinter = new FileOutputStream(sFilePrinter); 
//          
//            init(oSerialOutPrinter);
//            write(ESCPOS.INIT); // Arrancamos la impresora
//        } catch (IOException e) {
//            throw new TicketPrinterException("IO Exception: (" + sFilePrinter + ") : " + e.getMessage());
//        }    
//    }
    
    public PrinterWritterFile(String sFilePrinter) {
        m_sFilePrinter = sFilePrinter;
        m_out = null;
        
        write(ESCPOS.INIT); // Arrancamos la impresora
        flush();
    }  
    
    public void daemonWrite(byte[] data) {
        try {  
            if (m_out == null) {
                m_out = new FileOutputStream(m_sFilePrinter);  // No poner append = true.
            }
            m_out.write(data);
        } catch (IOException e) {
        }    
    }
    
    public void daemonFlush() {
        try {  
            if (m_out != null) {
                m_out.flush();
                m_out.close();
                m_out = null;
            }
        } catch (IOException e) {
        }    
    }
    
    public void daemonClose() {
        try {  
            if (m_out != null) {
                m_out.flush();
                m_out.close();
                m_out = null;
            }
        } catch (IOException e) {
        }    
    }    
}
