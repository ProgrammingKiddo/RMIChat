//Copyright (c) 2018, Borja Fernández Merchán

/**
 * @author Borja Fernández Merchán
 * @version 1.0
 * @since 2018-01-11
 */

package rmichat;

//Muchos de estos imports se pueden simplificar en "java.rmi.*", pero prefiero dejarlo así
//  para ver de un vistazo los componentes que se utilizan.
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.util.Scanner;


public class Cliente extends UnicastRemoteObject implements ICliente{

    /**
     * @throws RemoteException
     * Constructor vacío para avisar de la posible excepción remota.
     */
    Cliente() throws RemoteException{

    }

    /**
     * @param args El primer parámetro pasado al ejecutar la clase representa la dirección del servidor al que se pretende conectar.
     */
    public static void main(String[] args){

        Scanner teclado;
        IRMIChat servidor;
        Cliente cliente;
        String entrada;

        cliente = null;
        servidor = null;

        //Intentamos inicializar al cliente y recogemos la excepción.
        try{
            cliente = new Cliente();
        } catch(RemoteException ex){
            System.err.println(ex.toString());
        }

        //Intentamos buscar el servidor en la dirección dada y nos aseguramos
        //  de tener en cuenta todas las posibles excepciones.
        try{
            servidor = (IRMIChat) Naming.lookup("//" + args[0] + "/chat");
        } catch(NotBoundException ex){
            System.err.println(ex.toString());
        } catch(MalformedURLException ex){
            System.err.println(ex.toString());
        } catch(RemoteException ex){
            System.err.println(ex.toString());
        }

        //Para evitar posibles fallos, si no hemos inicializado correctamente el cliente y/o el servidor
        //  no seguimos ejecutando el cuerpo del cliente.
        if(!servidor.equals(null) && !cliente.equals(null)){
            
            try{
                servidor.registrar(cliente);
            } catch(RemoteException ex){
                System.err.println(ex.toString());
            }
            
            teclado = new Scanner(System.in);
            entrada = "";
    
            while(!entrada.equalsIgnoreCase("salir")){
                entrada = teclado.nextLine();

                //Hacemos esta comprobación para evitar mandar el mensaje "salir" al salir.
                if(!entrada.equalsIgnoreCase("salir")){    
                    try{
                        servidor.enviarMensaje(entrada);
                    } catch(RemoteException ex){
                        System.err.println(ex.toString());
                    }
                }
            }

            System.out.println("\nGracias por usar nuestro sistema de chat via RMI\n�Que tenga un buen d�a!");

            teclado.close();    //Cerramos los recursos abiertos al terminar.
        }

    }

    @Override
    /**
     * @throws RemoteException
     * @param texto La cadena de texto enviada por otro cliente.
     */
    public void recibirMensaje(String texto) throws RemoteException{

        System.out.println(texto);
    }

}