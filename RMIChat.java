//Copyright (c) 2018, Borja Fernández Merchán

/**
 * @author Borja Fern�ndez Merch�n
 * @version 1.0
 * @since 2018-01-11
 */

package rmichat;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.util.Vector;


public class RMIChat extends UnicastRemoteObject implements IRMIChat {


    Vector<ICliente> clientes = new Vector<ICliente>();

    /**
     * @throws RemoteException
     * Constructor vac�o para avisar de la posible excepci�n remota.
     */
    RMIChat() throws RemoteException{

    }

    /**
     * Código de inicialización del servidor.
     * @param args El primer parámetro pasado al ejecutar la clase representa la dirección a utilizar por el servidor.
     */
    public static void main(String[] args) throws Exception{

        RMIChat chat;
        
        chat = new RMIChat();
        
        Naming.rebind("//" + args[0] + "/chat", chat);
        System.out.println("Chat preparado.");
    }

    @Override
    /**
     * Envía el mensaje recibido por un cliente a todos los clientes registrados.
     * @throws RemoteException
     * @param mensaje La cadena de texto que el servidor debe enviar a todos los clientes registrados.
     */
    public void enviarMensaje(String mensaje) throws RemoteException{

        int i;

        for(i = 0; i < clientes.size(); i++){
            clientes.get(i).recibirMensaje(mensaje);
        }

    }

    @Override
    /**
     * Añade un cliente al vector contenedor de clientes del objeto Servidor.
     * @throws RemoteException
     * @param cliente Representa a un cliente remoto que se quiere registrar en el servidor.
     */
    public void registrar(ICliente cliente) throws RemoteException{

        clientes.add(cliente);
    }
}