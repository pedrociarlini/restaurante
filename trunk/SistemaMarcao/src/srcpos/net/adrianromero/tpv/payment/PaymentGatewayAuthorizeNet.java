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

package net.adrianromero.tpv.payment;

import java.util.*;
import java.io.*;
import java.net.*;
import java.security.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;

import net.adrianromero.tpv.forms.*;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import net.adrianromero.tpv.ticket.TicketInfo;

public class PaymentGatewayAuthorizeNet implements PaymentGateway {
    
    private static final boolean DBG = false; // move this somewhere globally
    private static final String ENDPOINTADDRESS = "https://secure.authorize.net/gateway/transact.dll";
    private static final String OPERATIONVALIDATE = "AUTH_CAPTURE";
    private static final String OPERATIONREFUND = "CREDIT";
    
    private String m_sCommerceID;
    private String m_sCommercePassword;
    private String m_sCurrency;
    private boolean m_bTestMode;

    private String m_sMagCardReader;

    /** Creates a new instance of PaymentGatewayAuthorizeNet */
    public PaymentGatewayAuthorizeNet(AppProperties app) {
        m_sMagCardReader = app.getProperty("payment.magcardreader");
        // Grab some security variables
        m_sCommerceID = app.getProperty("payment.commerceid");
        m_sCommercePassword = app.getProperty("payment.commercepassword");
        m_bTestMode = Boolean.valueOf(app.getProperty("payment.testmode")).booleanValue();
        m_sCurrency = Currency.getInstance(Locale.getDefault()).getCurrencyCode();
    }

    public PaymentPanel getInfoMagcardFactory(JPaymentNotifier notifier) {
        return new PaymentPanelMagCard(m_sMagCardReader, notifier);
    }    

    public void execute(PaymentInfoMagcard payinfo) {
        
        // dependiendo del total payinfo debe ser un pago o una devolucion...
        // por ahora solo se realizan pagos...
        
        // Se podria comprobar la instancia de payinfo,
        // PaymentInfoMagcard o PaymentInfoMagcardRefund        
        if (payinfo.getTotal() > 0.0) {
            try {
                StringBuffer sb = new StringBuffer();

                // mandatory name/value pairs for all AIM CC transactions
                // as well as some "good to have" values
                sb.append("x_login=");        
                sb.append(URLEncoder.encode(m_sCommerceID, "UTF-8"));
                
                sb.append("&x_password=");
                sb.append(URLEncoder.encode(m_sCommercePassword, "UTF-8"));
                
                //sb.append("x_tran_key=eoXaDm2LUnz2OiyQ&");     // replace with your own
                
                sb.append("&x_version=3.1");
                
                sb.append("&x_test_request=");             // for testing
                sb.append(m_bTestMode);
                
                sb.append("&x_method=CC");
                
                sb.append("&x_type=");
                sb.append(OPERATIONVALIDATE);
                
                sb.append("&x_amount=");
                NumberFormat formatter = new DecimalFormat("000.00");
                String amount = formatter.format(payinfo.getTotal());
                sb.append(URLEncoder.encode((String)amount, "UTF-8"));

                sb.append("&x_delim_data=TRUE");
                sb.append("&x_delim_char=|");
                sb.append("&x_relay_response=FALSE");

                // CC information
                // Reverse month and year order in getExpirationDate()
                sb.append("&x_exp_date=");
                String tmp = payinfo.getExpirationDate();
                sb.append(tmp.charAt(2));
                sb.append(tmp.charAt(3));
                sb.append(tmp.charAt(0));
                sb.append(tmp.charAt(1));
                
                sb.append("&x_card_num=");
                sb.append(URLEncoder.encode(payinfo.getCardNumber(), "UTF-8"));

                // not required
                sb.append("&x_description=Cafe+Transaction");
                
                Vector cc_name = split(" ", payinfo.getHolderName());
                sb.append("&x_first_name=");
                if (cc_name.size() > 0) {
                    sb.append(URLEncoder.encode((String)cc_name.elementAt(0), "UTF-8"));
                }
                sb.append("&x_last_name=");
                if (cc_name.size() > 1) {
                    sb.append(URLEncoder.encode((String)cc_name.elementAt(1), "UTF-8"));
                }
                
if (DBG) {
   System.err.println(sb);
}

                // open secure connection
                URL url = new URL(ENDPOINTADDRESS);

                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                connection.setUseCaches(false);

                // not necessarily required but fixes a bug with some servers
                connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                // POST the data in the string buffer
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.write(sb.toString().getBytes());
                out.flush();
                out.close();

                // process and read the gateway response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                line = in.readLine();
                in.close();	                     // no more data
if (DBG) {
   System.err.println(line);
}



                // ONLY FOR THOSE WHO WANT TO CAPTURE GATEWAY RESPONSE INFORMATION
                // make the reply readable (be sure to use the x_delim_char for the split operation)
                Vector ccRep = split("|", line);
                
if (DBG) {
   System.err.print("Response Code: ");
   System.err.println(ccRep.elementAt(0));
   System.err.print("Human Readable Response Code: ");
   System.err.println(ccRep.elementAt(3));
   System.err.print("Approval Code: ");
   System.err.println(ccRep.elementAt(4));
   System.err.print("Trans ID: ");
   System.err.println(ccRep.elementAt(6));
   System.err.print("MD5 Hash Server: ");
   System.err.println(ccRep.elementAt(37));
}
                
                if ("1".equals(ccRep.elementAt(0))) {
                    payinfo.paymentOK((String) ccRep.elementAt(4)); 
                } else { 
                    payinfo.paymentError(AppLocal.getIntString("message.paymenterror") + "\n" + ccRep.elementAt(0) + " -- " + ccRep.elementAt(3));
                }

            } catch (UnsupportedEncodingException eUE) {
                // no pasa nunca
                payinfo.paymentError(AppLocal.getIntString("message.paymentexceptionservice") + "\n" + eUE.getMessage());
            } catch (MalformedURLException eMURL) {
                // no pasa nunca    
                payinfo.paymentError(AppLocal.getIntString("message.paymentexceptionservice") + "\n" + eMURL.getMessage());
            } catch(IOException e){
                payinfo.paymentError(AppLocal.getIntString("message.paymenterror") + "\n" + e.getMessage());
            }
        } else {
            // devoluciones no soportadas actualmente
            payinfo.paymentError(AppLocal.getIntString("message.paymentrefundsnotsupported"));
        }
    }

    // utility functions
    public static Vector split(String pattern, String in){
          int s1=0, s2=-1;
          Vector out = new Vector(30);
          while(true){
              s2 = in.indexOf(pattern, s1);
              if(s2 != -1){
                      out.addElement(in.substring(s1, s2));
              }else{
                      //the end part of the string (string not pattern terminated)
                      String _ = in.substring(s1);
                      if(_ != null && !_.equals("")){
                              out.addElement(_);
                      }
                      break;
              }
              s1 = s2;
              s1 += pattern.length();
          }
          return out;
    }
}