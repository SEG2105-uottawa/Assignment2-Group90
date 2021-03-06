// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.net.InetAddress;

import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
	  System.out.println("Message received: " + msg + " from " + client);
	  this.sendToAllClients(msg);

    
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  
  //Edited for E49c*************************************************
  
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  
  //Edited for E49 part c********************************************
  @Override
  protected void clientConnected(ConnectionToClient client)
  {
	  System.out.println("Client has connected to Server with port " + getPort());
	  
  }
  //Edited for E49 part c********************************************
  @Override
  protected void clientDisconnected(ConnectionToClient client)
  {
	  System.out.println("Client has disconnected from Server with port " + getPort());
  }
  //Edited for E49 part c********************************************
  @Override
  protected void clientException(ConnectionToClient client, Throwable exception)
  {
	  System.out.println("Error establishing connection to client. Disconnecting Client from Server.");
	  clientDisconnected(client);
  }
  
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
      System.out.println(port);

    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
    //modified for E49b
    //Server port can be modified from the console, if proper input, client can only connect to this port and not default
    try {
    	System.out.print("Enter a port number: ");
    	BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
    	port = Integer.parseInt(fromConsole.readLine());
    	System.out.println("Now using port: "+port);
    }
    catch (Exception e) {
        port = DEFAULT_PORT;
        System.out.println("Bad input, using default port: "+ DEFAULT_PORT);
    }
    EchoServer sv = new EchoServer(port);
    //before listening for connections, must create a Thread for listening for input on console
    Thread serverConsoleThread = new Thread(new ServerConsole(port, sv)); 
    serverConsoleThread.start();
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }

 
  }
}
//End of EchoServer class
