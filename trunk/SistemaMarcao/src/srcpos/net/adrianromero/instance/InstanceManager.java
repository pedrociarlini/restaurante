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

package net.adrianromero.instance;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class InstanceManager {
    
    private Registry m_registry;
    private TinaMessage m_message;
    
    /** Creates a new instance of InstanceManager */
    public InstanceManager(TinaMessage message) throws RemoteException, AlreadyBoundException {

        m_registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

        m_message = message;

        TinaMessage stub = (TinaMessage) UnicastRemoteObject.exportObject(m_message, 0);
        m_registry.bind("TinaMessage", stub); 

        // jLabel1.setText("Server ready");
    }    
}
