// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
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
	//**** changed for E49
	if (msg.equals("") || msg.equals(null)) {}
	else {
	  System.out.println("Message received: " + msg + " (from " + client + ")");
	  this.sendToAllClients(msg); 
	}
  }
    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
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
	//****changed for E49
    System.out.println
      ("Server has stopped listening for connections.");
    this.sendToAllClients("The server has closed");
  }
  
  //**** made for E50
  public void handleMessageFromServerConsole(String message) {
      if (message.charAt(0)) {
          String[] split = message.split(" ");
          switch (split[0]) {
              case "#quit":
                  try {
                      this.close(); } 
                  catch (IOException e) {
                      System.exit(1); }
                  System.exit(0);
                  break;
              case "#stop":
                  this.stopListening();
                  break;
              case "#close":
                  try {
                      this.close(); } 
                  catch (IOException e) {}
                  break;
              case "#setport":
                  if (!this.isListening() && this.getNumberOfClients() < 1) {
                      super.setPort(Integer.parseInt(split[1]));
                      System.out.println("Port was set to " + Integer.parseInt(split[1]));} 
                  else {
                      System.out.println("Server is connected.");}
                  break;
              case "#start":
                  if (!this.isListening()) {
                      try {
                          this.listen(); } 
                      catch (IOException e) {}
                  } 
                  else {
                      System.out.println("Already started and listening for clients"); }
                  break;
              case "#getport":
                  System.out.println("Current port is " + this.getPort());
                  break;
              default:
                  System.out.println("Invalid command: '" + command+ "'");
                  break;
          }
      } 
      else {
          this.sendToAllClients("SERVER> "+message); }
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on

    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
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
