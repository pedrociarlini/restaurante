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

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import javax.imageio.ImageIO;
import net.adrianromero.tpv.data.*;
import net.adrianromero.tpv.ticket.*;
import net.adrianromero.data.loader.*;
import net.adrianromero.format.Formats;
import net.adrianromero.basic.BasicException;

public class SentenceContainer implements DataLogic {
    
    protected SentenceFind m_nextticketindex;
    protected SentenceFind m_nextreservation;
    protected SentenceFind m_nextstockdiary;
    protected SentenceFind m_nextpayment;
    
    protected SentenceExec m_ticketinsert;
    protected SentenceExec m_ticketlineinsert;
    protected SentenceExec m_paymentinsert;
    protected SentenceExec m_paymentdelete;
    
    protected SentenceFind m_productinfo;
    protected SentenceFind m_productinfo2;
        
    private SentenceExec m_productupdate;
    private SentenceExec m_productinsert;
    private SentenceExec m_productdelete;
//    private SentenceFind m_productunits;
    private SentenceList m_productstock;
    
    private SentenceList m_productcoms;
    
    private SentenceExec m_catalogupdate;
    private SentenceExec m_cataloginsert;
    private SentenceExec m_catalogdelete;
    
    private SentenceExec m_stockdiaryinsert;
    private SentenceExec m_stockdiarydelete;
    
    private SentenceExec m_stockcurrentinsert;
    private SentenceExec m_stockcurrentupdate;
    private SentenceExec m_stockcurrentinsertNEG;
    private SentenceExec m_stockcurrentupdateNEG;
    
    protected Datas[] productcatDatas;
    protected SentenceList m_productcatqbf;
    private SentenceList m_productcatalog;
    private SentenceList m_productcomments;
    private SentenceList m_categorylist;
    private SentenceList m_productlist;
    
    private SentenceFind m_loadticket;
    private SentenceList m_loadticketlines;
    
    private SentenceExec m_catcategoryadd;
    private SentenceExec m_catcategorydel;
    
    private SentenceList m_taxeslist;
    
    private SentenceExec m_ticketdelete;
    
    private SentenceExec m_paymentticketinsert;
    private SentenceExec m_paymentpaymentinsert;
    private SentenceExec m_paymentticketdelete;
    private SentenceExec m_paymentpaymentdelete;
    
    private SentenceExec m_stockupdate;
    private SentenceExec m_stockinsert;
      
    private TableDefinition m_tcategories;
    private TableDefinition m_tcategorieslist;
    private TableDefinition m_ttaxes;
    private TableDefinition m_ttaxeslist;
    private TableDefinition m_tlocations;
     
    /** Creates a new instance of SentenceContainerGeneric */
    public SentenceContainer() {
    }
    public void init(Connection cnt){
        
        m_productinfo = new PreparedSentence(cnt
            , "SELECT P.REFERENCE, P.CODE, P.NAME, P.ISCOM, P.ISSCALE, P.PRICEBUY, P.PRICESELL, P.TAX, T.NAME, T.RATE, P.CATEGORY, P.IMAGE FROM PRODUCTS P LEFT OUTER JOIN TAXES T ON P.TAX = T.ID WHERE CODE = ?"
            , SerializerWriteString.INSTANCE
            , new SerializerReadClass(ProductInfoExt.class));
               
        m_productinfo2 = new PreparedSentence(cnt
            , "SELECT P.REFERENCE, P.CODE, P.NAME, P.ISCOM, P.ISSCALE, P.PRICEBUY, P.PRICESELL, P.TAX, T.NAME, T.RATE, P.CATEGORY, P.IMAGE FROM PRODUCTS P LEFT OUTER JOIN TAXES T ON P.TAX = T.ID WHERE REFERENCE = ?"
            , SerializerWriteString.INSTANCE
            , new SerializerReadClass(ProductInfoExt.class));  
          
        m_categorylist = new PreparedSentence(cnt
              , "SELECT ID, NAME, IMAGE FROM CATEGORIES ORDER BY NAME"
              , null
              , new SerializerReadClass(CategoryInfo.class));        
        m_productcatalog = new PreparedSentence(cnt
            , "SELECT P.REFERENCE, P.CODE, P.NAME, P.ISCOM, P.ISSCALE, P.PRICEBUY, P.PRICESELL, P.TAX, T.NAME, T.RATE, P.CATEGORY, P.IMAGE" +
              " FROM PRODUCTS P LEFT OUTER JOIN TAXES T ON P.TAX = T.ID LEFT OUTER JOIN CATEGORIES C ON P.CATEGORY = C.ID, PRODUCTS_CAT O WHERE P.REFERENCE = O.REFERENCE" +
              " ORDER BY C.NAME, O.CATORDER, P.NAME"
            , null
            , new SerializerReadClass(ProductInfoExt.class));
        m_productcomments = new PreparedSentence(cnt
            , "SELECT P.REFERENCE, P.CODE, P.NAME, P.ISCOM, P.ISSCALE, P.PRICEBUY, P.PRICESELL, P.TAX, T.NAME, T.RATE, P.CATEGORY, P.IMAGE" +
              " FROM PRODUCTS P LEFT OUTER JOIN TAXES T ON P.TAX = T.ID, PRODUCTS_CAT O, PRODUCTS_COM M WHERE P.REFERENCE = O.REFERENCE AND P.REFERENCE = M.REFERENCE2 AND P.REFERENCE = ?" +
              " ORDER BY O.CATORDER, P.NAME"
            , SerializerWriteString.INSTANCE 
            , new SerializerReadClass(ProductInfoExt.class));  
        m_productlist = new StaticSentence(cnt
            , new QBFBuilder("SELECT P.REFERENCE, P.CODE, P.NAME, P.ISCOM, P.ISSCALE, P.PRICEBUY, P.PRICESELL, P.TAX, T.NAME, T.RATE, P.CATEGORY, P.IMAGE FROM PRODUCTS P LEFT OUTER JOIN TAXES T ON P.TAX = T.ID WHERE ?(QBF_FILTER) ORDER BY P.NAME", new String[] {"P.NAME", "P.PRICEBUY", "P.PRICESELL", "P.CATEGORY", "P.CODE"})
            , new SerializerWriteBasic(new Datas[] {Datas.OBJECT, Datas.STRING, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.DOUBLE, Datas.OBJECT, Datas.INT, Datas.OBJECT, Datas.STRING})
            , new SerializerReadClass(ProductInfoExt.class));              
        m_ticketinsert = new PreparedSentence(cnt
            , "INSERT INTO TICKETS (TICKETID, DATENEW, MONEY, PERSON) VALUES (?, ?, ?, ?)"
            , SerializerWriteBuilder.INSTANCE);
        m_ticketlineinsert = new PreparedSentence(cnt
            , "INSERT INTO PRODUCTSOUT (TICKETID, TICKETLINE, PRODUCT, NAME, ISCOM, UNITS, PRICE, TAXID, TAXRATE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            , SerializerWriteBuilder.INSTANCE);  
        
        // Payment data panel
        Datas[] paymenttabledatas = new Datas[] {Datas.INT, Datas.TIMESTAMP, Datas.INT, Datas.STRING, Datas.INT, Datas.STRING, Datas.DOUBLE};
        m_paymentticketinsert = new PreparedSentence(cnt
                , "INSERT INTO TICKETS (TICKETID, DATENEW, MONEY, PERSON) VALUES (?, ?, ?, ?)"
                , new SerializerWriteBasicExt(paymenttabledatas, new int[] {0, 1, 2, 3}));
        m_paymentpaymentinsert = new PreparedSentence(cnt
                , "INSERT INTO PAYMENTS (ID, TICKETID, PAYMENT, TOTAL) VALUES (?, ?, ?, ?)"
                , new SerializerWriteBasicExt(paymenttabledatas, new int[] {4, 0, 5, 6}));
        m_paymentticketdelete = new PreparedSentence(cnt
                , "DELETE FROM TICKETS WHERE TICKETID = ?"
                , new SerializerWriteBasicExt(paymenttabledatas, new int[] {0}));
        m_paymentpaymentdelete = new PreparedSentence(cnt
                , "DELETE FROM PAYMENTS WHERE ID = ?"
                , new SerializerWriteBasicExt(paymenttabledatas, new int[] {4}));

        
        
        Datas[] paymentdatas = new Datas[] {Datas.INT, Datas.INT, Datas.STRING, Datas.DOUBLE};
        m_paymentinsert = new PreparedSentence(cnt
            , "INSERT INTO PAYMENTS (ID, TICKETID, PAYMENT, TOTAL) VALUES (?, ?, ?, ?)"
            , new SerializerWriteBasicExt(paymentdatas, new int[] {0, 1, 2, 3}));
        m_paymentdelete = new PreparedSentence(cnt
            , "DELETE FROM PAYMENTS WHERE ID = ?"
            , new SerializerWriteBasicExt(paymentdatas, new int[] {0}));
        m_loadticket = new PreparedSentence(cnt
                , "SELECT TICKETID, DATENEW, MONEY, PERSON FROM TICKETS WHERE TICKETID = ?"
                , new SerializerWriteBasic(new Datas[] {Datas.INT})
                , new SerializerReadClass(TicketInfo.class));
        m_loadticketlines = new PreparedSentence(cnt
                , "SELECT TICKETID, TICKETLINE, PRODUCT, NAME, ISCOM, UNITS, PRICE, TAXID, TAXRATE FROM PRODUCTSOUT WHERE TICKETID = ?"
                , new SerializerWriteBasic(new Datas[] {Datas.INT})
                , new SerializerReadClass(TicketLineInfo.class));    
        
        m_ticketdelete = new TicketDelete(cnt);
        
        productcatDatas = new Datas[] {Datas.STRING, Datas.STRING, Datas.STRING, Datas.BOOLEAN, Datas.BOOLEAN, Datas.DOUBLE, Datas.DOUBLE, Datas.INT, Datas.INT, Datas.IMAGE, Datas.DOUBLE, Datas.DOUBLE, Datas.BOOLEAN, Datas.INT};
        m_productinsert = new PreparedSentence(cnt
            , "INSERT INTO PRODUCTS (REFERENCE, CODE, NAME, ISCOM, ISSCALE, PRICEBUY, PRICESELL, CATEGORY, TAX, IMAGE, STOCKCOST, STOCKVOLUME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            , new SerializerWriteBasicExt(productcatDatas, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}));
        m_productupdate = new PreparedSentence(cnt
            , "UPDATE PRODUCTS SET REFERENCE = ?, CODE = ?, NAME = ?, ISCOM = ?, ISSCALE = ?, PRICEBUY = ?, PRICESELL = ?, CATEGORY = ?, TAX = ?, IMAGE = ?, STOCKCOST = ?, STOCKVOLUME = ? WHERE REFERENCE = ?"
            , new SerializerWriteBasicExt(productcatDatas, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0}));
        m_productdelete = new PreparedSentence(cnt
            , "DELETE FROM PRODUCTS WHERE REFERENCE = ?"
            , new SerializerWriteBasicExt(productcatDatas, new int[]{0}));
                
        m_cataloginsert = new PreparedSentence(cnt
            , "INSERT INTO PRODUCTS_CAT (REFERENCE, CATORDER) VALUES (?, ?)"
            , new SerializerWriteBasicExt(productcatDatas, new int[] {0, 13}));       
        m_catalogupdate = new PreparedSentence(cnt
            , "UPDATE PRODUCTS_CAT SET CATORDER = ? WHERE REFERENCE = ?"
            , new SerializerWriteBasicExt(productcatDatas, new int[] {13, 0}));       
        m_catalogdelete = new PreparedSentence(cnt
            , "DELETE FROM PRODUCTS_CAT WHERE REFERENCE = ?"
            , new SerializerWriteBasicExt(productcatDatas, new int[] {0}));     
                
        m_productcoms = new PreparedSentence(cnt
                , "SELECT PRODUCTS.REFERENCE, PRODUCTS.NAME FROM PRODUCTS, PRODUCTS_COM WHERE PRODUCTS.REFERENCE = PRODUCTS_COM.REFERENCE2 AND PRODUCTS_COM.REFERENCE = ? ORDER BY PRODUCTS.NAME"
                , SerializerWriteString.INSTANCE
                , new SerializerReadBasic(new Datas[] {Datas.STRING, Datas.STRING}));
        
        Datas[] stockdiaryDatas = new Datas[] {Datas.INT, Datas.TIMESTAMP, Datas.INT, Datas.INT, Datas.STRING, Datas.DOUBLE, Datas.DOUBLE};
        m_stockdiaryinsert = new PreparedSentence(cnt
            , "INSERT INTO STOCKDIARY (ID, DATENEW, REASON, LOCATION, PRODUCT, UNITS, PRICE) VALUES (?, ?, ?, ?, ?, ?, ?)"
            , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {0, 1, 2, 3, 4, 5, 6}));
        m_stockdiarydelete = new PreparedSentence(cnt
            , "DELETE FROM STOCKDIARY WHERE ID = ?"
            , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {0}));
        m_stockcurrentinsert = new PreparedSentence(cnt
                , "INSERT INTO STOCKCURRENT (LOCATION, PRODUCT, UNITS) VALUES (?, ?, ?)"
                , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {3, 4, 5}));
        m_stockcurrentupdate = new PreparedSentence(cnt
                , "UPDATE STOCKCURRENT SET UNITS = (UNITS + ?) WHERE LOCATION = ? AND PRODUCT = ?"
                , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {5, 3, 4}));
        m_stockcurrentinsertNEG = new PreparedSentence(cnt
                , "INSERT INTO STOCKCURRENT (LOCATION, PRODUCT, UNITS) VALUES (?, ?, -(?))"
                , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {3, 4, 5}));
        m_stockcurrentupdateNEG = new PreparedSentence(cnt
                , "UPDATE STOCKCURRENT SET UNITS = (UNITS - ?) WHERE LOCATION = ? AND PRODUCT = ?"
                , new SerializerWriteBasicExt(stockdiaryDatas, new int[] {5, 3, 4}));
                            
//        m_productunits = new PreparedSentence(cnt
//                , "SELECT STOCKCURRENT.UNITS FROM STOCKCURRENT WHERE STOCKCURRENT.PRODUCT = ?"
//                , SerializerWriteString.INSTANCE
//                , SerializerReadDouble.INSTANCE);   
        Datas[] stockdatas = new Datas[] {Datas.INT, Datas.STRING, Datas.STRING, Datas.DOUBLE, Datas.DOUBLE, Datas.DOUBLE};
        m_productstock = new PreparedSentence (cnt
                , "SELECT L.ID, L.NAME, ?, COALESCE(S.UNITS, 0.0), S.STOCKSECURITY, S.STOCKMAXIMUM " +
                "FROM LOCATIONS L LEFT OUTER JOIN (" +
                "SELECT PRODUCT, LOCATION, STOCKSECURITY, STOCKMAXIMUM, UNITS FROM STOCKCURRENT WHERE PRODUCT = ?) S " +
                "ON L.ID = S.LOCATION"
                , new SerializerWriteBasicExt(productcatDatas, new int[]{0, 0})
                , new SerializerReadBasic(stockdatas));        
        m_stockupdate = new PreparedSentence(cnt
                , "UPDATE STOCKCURRENT SET STOCKSECURITY = ?, STOCKMAXIMUM = ? WHERE LOCATION = ? AND PRODUCT = ?"
                , new SerializerWriteBasicExt(stockdatas, new int[] {4, 5, 0, 2}));
        m_stockinsert = new PreparedSentence(cnt
                , "INSERT INTO STOCKCURRENT(LOCATION, PRODUCT, UNITS, STOCKSECURITY, STOCKMAXIMUM) VALUES (?, ?, 0.0, ?, ?)"
                , new SerializerWriteBasicExt(stockdatas, new int[] {0, 2, 4, 5}));
                
        m_taxeslist = new StaticSentence(cnt
            , "SELECT ID, NAME, RATE FROM TAXES"
            , null
            , new SerializerReadClass(TaxInfo.class));            
            
       
        m_tcategories = new TableDefinition(cnt,
            "CATEGORIES"
            , new int[] {0}
            , new String[] {"ID", "NAME", "IMAGE"}
            , new Datas[] {Datas.INT, Datas.STRING, Datas.IMAGE}
            , new Formats[] {Formats.INT, Formats.STRING}
            , new Formats[] {Formats.INT, Formats.STRING}
            , new int[] {0, 1}
        );   
        
        m_tcategorieslist = new TableDefinition(cnt,
            "CATEGORIES"
            , new int[] {0}
            , new String[] {"ID", "NAME"}
            , new Datas[] {Datas.INT, Datas.STRING}
            , new Formats[] {Formats.INT, Formats.STRING}
            , new Formats[] {Formats.INT, Formats.STRING}
        );               
        
         m_ttaxes = new TableDefinition(cnt,
            "TAXES"
            , new int[] {0}
            , new String[] {"ID", "NAME", "RATE"}
            , new Datas[] {Datas.INT, Datas.STRING, Datas.DOUBLE}
            , new Formats[] {Formats.INT, Formats.STRING, Formats.PERCENT}
            , new Formats[] {Formats.INT, Formats.STRING}
            , new int[] {0, 1, 2}
        );
        
        m_ttaxeslist = new TableDefinition(cnt,
            "TAXES"
            , new int[] {0}
            , new String[] {"ID", "NAME", "RATE"}
            , new Datas[] {Datas.INT, Datas.STRING, Datas.DOUBLE}
            , new Formats[] {Formats.INT, Formats.STRING}
            , new Formats[] {Formats.INT, Formats.STRING}
        );    
        
         m_tlocations = new TableDefinition(cnt,
            "LOCATIONS"
            , new int[] {0}
            , new String[] {"ID", "NAME", "ADDRESS"}
            , new Datas[] {Datas.INT, Datas.STRING, Datas.STRING}
            , new Formats[] {Formats.INT, Formats.STRING, Formats.STRING}
            , new Formats[] {Formats.INT, Formats.STRING}
            , new int[] {0, 1, 2}
        );       
         
        m_catcategoryadd = new StaticSentence(cnt
                , "INSERT INTO PRODUCTS_CAT(REFERENCE, CATORDER) SELECT REFERENCE, NULL FROM PRODUCTS WHERE CATEGORY=?"
                , SerializerWriteInteger.INSTANCE);
        m_catcategorydel = new StaticSentence(cnt
                , "DELETE FROM PRODUCTS_CAT WHERE REFERENCE = ANY (SELECT REFERENCE FROM PRODUCTS WHERE CATEGORY=?)"
                , SerializerWriteInteger.INSTANCE);                
    }        
    
    public final SentenceExec getTicketInsert() {
        return m_ticketinsert;
    }
    public final SentenceExec getTicketLineInsert() {
        return m_ticketlineinsert;
    }
    public final SentenceExec getPaymentInsert() {
        return m_paymentinsert;
    }
    public final SentenceExec getPaymentDelete() {
        return m_paymentdelete;
    }

    public final SentenceFind getNextTicketIndex() {
        return m_nextticketindex;
    }

    public final SentenceFind getNextReservation() {
        return m_nextreservation;
    }
    public final SentenceFind getNextStockDiary() {
        return m_nextstockdiary;
    }
    public final SentenceFind getNextPayment() {
        return m_nextpayment;
    }
    public final SentenceFind getProductInfo() {
        return m_productinfo;
    }
    public final SentenceFind getProductInfo2() {
        return m_productinfo2;
    }
    
    public final SentenceList getProductComments2() {
        return m_productcoms;
    }
    
    public final SentenceList getProductCatQBF() {
        return m_productcatqbf;
    }
    public final SentenceList getProductList() {
        return m_productlist;
    }
    public final SentenceList getProductCatalog() {
        return m_productcatalog;
    }
    public final SentenceList getProductComments() {
        return m_productcomments;
    }
    public final SentenceList getCategoryList() {
        return m_categorylist;
    }
    public final SentenceList getProductStock() {
        return m_productstock;
    }    
    public final SentenceFind getLoadTicket() {
        return m_loadticket;
    }
    public final SentenceList getLoadTicketLines() {
        return m_loadticketlines;
    }
    
    public final SentenceExec getTicketDelete() {                
        return m_ticketdelete;
    }   
    
    private static final class TicketDelete implements SentenceExec {
        
        private SentenceExec m_ticketlinesdelete;
        private SentenceExec m_ticketiddelete;
        private SentenceExec m_ticketpaymentdelete;
        
        public TicketDelete(Connection cnt) {
            m_ticketpaymentdelete = new StaticSentence(cnt
                , "DELETE FROM PAYMENTS WHERE TICKETID = ?"
                , SerializerWriteInteger.INSTANCE); 
            m_ticketlinesdelete = new StaticSentence(cnt
                , "DELETE FROM PRODUCTSOUT WHERE TICKETID = ?"
                , SerializerWriteInteger.INSTANCE); 
            m_ticketiddelete =  new StaticSentence(cnt
                , "DELETE FROM TICKETS WHERE TICKETID = ?"
                , SerializerWriteInteger.INSTANCE); 
        }
        public int exec() throws BasicException {
            return exec(null);
        }
        public int exec(Object params) throws BasicException {
            m_ticketpaymentdelete.exec(params);
            m_ticketlinesdelete.exec(params);
            return m_ticketiddelete.exec(params);
        } 
    }
    
    public final SentenceExec getProductCatInsert() {
        return new SentenceExecAdapter() {
            public int exec(Object params) throws BasicException {
                Object[] values = (Object[]) params;            
                int i = m_productinsert.exec(params);
                if (i > 0 && ((Boolean)values[12]).booleanValue()) {
                    return m_cataloginsert.exec(params);
                } else {
                    return i;
                }
            }
        };
    }
    
    public final SentenceExec getProductCatUpdate() {
        return new SentenceExecAdapter() {
            public int exec(Object params) throws BasicException {
                Object[] values = (Object[]) params;            
                int i = m_productupdate.exec(params);
                if (i > 0) {
                    if (((Boolean)values[12]).booleanValue()) {
                        if (m_catalogupdate.exec(params) == 0) {
                            m_cataloginsert.exec(params);
                        }
                    } else {
                        m_catalogdelete.exec(params);
                    }
                }
                return i;
            }        
        };
    }
   
    public final SentenceExec getProductCatDelete() {
        return new SentenceExecAdapter() {
            public int exec(Object params) throws BasicException {
                m_catalogdelete.exec(params);
                return m_productdelete.exec(params);
            } 
        };
    }
    
    public final SentenceExec getStockDiaryInsert() {
        return new SentenceExecAdapter() {
            public int exec(Object params) throws BasicException {
                if (m_stockcurrentupdate.exec(params) == 0) {
                    m_stockcurrentinsert.exec(params);
                }
                return m_stockdiaryinsert.exec(params);
            }
        };
    }
    
    public final SentenceExec getStockDiaryDelete() {
        return new SentenceExecAdapter() {
            public int exec(Object params) throws BasicException {
                if (m_stockcurrentupdateNEG.exec(params) == 0) {
                    m_stockcurrentinsertNEG.exec(params);
                }
                return m_stockdiarydelete.exec(params);
            }
        };
    }
    
    public final SentenceExec getPaymentMovementInsert() {
        return new SentenceExecAdapter() {
            public int exec(Object params) throws BasicException {
                m_paymentticketinsert.exec(params);
                return m_paymentpaymentinsert.exec(params);
            }
        };
    }
    
    public final SentenceExec getPaymentMovementDelete() {
        return new SentenceExecAdapter() {
            public int exec(Object params) throws BasicException {
                m_paymentpaymentdelete.exec(params);
                return m_paymentticketdelete.exec(params);
            }
        };
    }
    
    public final SentenceExec getStockUpdate() {
        return new SentenceExecAdapter() {
            public int exec(Object params) throws BasicException {
                if (m_stockupdate.exec(params) == 0) {
                    return m_stockinsert.exec(params);
                } else {
                    return 1;
                }     
            }
        };
    }
    
    public final SentenceExec getCatalogCategoryAdd() {
        return m_catcategoryadd;
    }
    public final SentenceExec getCatalogCategoryDel() {
        return m_catcategorydel;
    }    
    
    public final SentenceList getTaxList() {
        return m_taxeslist;
    }
   
    public final TableDefinition getTableCategories() {
        return m_tcategories;
    }    
    public final TableDefinition getTableCategoriesList() {
        return m_tcategorieslist;
    }    
    public final TableDefinition getTableTaxes() {
        return m_ttaxes;
    }    
    public final TableDefinition getTableTaxesList() {
        return m_ttaxeslist;
    }    
   
    public final TableDefinition getTableLocations() {
        return m_tlocations;
    }
}
