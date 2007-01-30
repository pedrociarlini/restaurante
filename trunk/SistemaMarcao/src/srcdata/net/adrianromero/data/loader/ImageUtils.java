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

package net.adrianromero.data.loader;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.*;

public class ImageUtils {
    
    private static char[] HEXCHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    
    /** Creates a new instance of ImageUtils */
    private ImageUtils() {
    }
    
    public static BufferedImage readImage(byte[] b) {
        if (b == null) {
            return null;
        } else {
            try {
                return ImageIO.read(new ByteArrayInputStream(b));
            } catch(IOException e) {
                return null;
            }
        }
    }
    
    public static byte[] writeImage(BufferedImage img) {
        if (img == null) {
            return null;
        } else {        
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                ImageIO.write(img, "png", b);
                b.flush();
                b.close();
                return b.toByteArray();
            } catch(IOException e) {
                return null;
            }
        }
    }
    
    public static Object readSerializable(byte[] b) {
        if (b == null) {
            return null;
        } else {        
            try {
                ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b));
                Object obj = in.readObject();
                in.close();
                return obj;
            } catch (ClassNotFoundException eCNF) {
                //logger.error("Cannot create lists object", eCNF);    
                return null;
            } catch (IOException eIO) {
                //logger.error("Cannot load lists file", eIO);
                return null;
            }
        }
    }
    
    public static byte[] writeSerializable(Object o) {
        
        if (o == null) {
            return null;
        } else {            
            try {
                ByteArrayOutputStream b = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(b);
                out.writeObject(o);
                out.flush();
                out.close();
                return b.toByteArray();
            } catch (IOException eIO) {
                eIO.printStackTrace();
                return null;
            }
        }
    }
    
//    public static Object readFromFile(String sfilename) {
//        try {
//            ObjectInputStream in = new ObjectInputStream(new FileInputStream(
//                new File(new File(System.getProperty("user.home")), sfilename)));
//            Object obj = in.readObject();
//            in.close();
//            return obj;
//        } catch (ClassNotFoundException eCNF) {
//            return null;
//        } catch (IOException eIO) {
//            return null;
//        }
//    }
//    
//    public static void writeToFile(String sfilename, Object o) {
//        try {
//            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(
//                    new File(new File(System.getProperty("user.home")), sfilename)));
//                out.writeObject(o);
//                out.flush();
//                out.close();
//        } catch (IOException eIO) {
//        }
//    }
//       
    public static String bytes2hex(byte[] binput) {
        
        StringBuffer s = new StringBuffer(binput.length *2);
        for (int i = 0; i < binput.length; i++) {
            byte b = binput[i];
            s.append(HEXCHARS[(b & 0xF0) >> 4]);
            s.append(HEXCHARS[b & 0x0F]);            
        }
        return s.toString();
    }    
}
