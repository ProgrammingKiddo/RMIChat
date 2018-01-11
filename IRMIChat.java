//Copyright (c) 2018, Borja Fernández Merchán

/**
 * @author Borja Fern�ndez Merch�n
 * @version 1.0
 * @since 2018-01-11
 */

package rmichat;

import java.rmi.Remote;
import java.rmi.RemoteException;

interface IRMIChat extends Remote{


    void enviarMensaje(String mensaje) throws RemoteException;


    void registrar(ICliente cliente) throws RemoteException;


}