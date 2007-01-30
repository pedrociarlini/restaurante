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

import java.util.Iterator;
import java.util.LinkedList;

public class PaymentInfoList {
    
    private LinkedList m_apayment;
    
    /** Creates a new instance of PaymentInfoComposed */
    public PaymentInfoList() {
        m_apayment = new LinkedList();
    }
        
    public double getTotal() {
        
        double dTotal = 0.0;
        Iterator i = m_apayment.iterator();
        while (i.hasNext()) {
            PaymentInfo p = (PaymentInfo) i.next();
            dTotal += p.getTotal();
        }
        
        return dTotal;
    }     
    
    public boolean isEmpty() {
        return m_apayment.isEmpty();
    }
    
    public void add(PaymentInfo p) {
        m_apayment.addLast(p);
    }
    
    public void removeLast() {
        m_apayment.removeLast();
    }
    
    public PaymentInfo[] getPaymentList() {
        return (PaymentInfo[]) m_apayment.toArray(new PaymentInfo[m_apayment.size()]);
    }
}
