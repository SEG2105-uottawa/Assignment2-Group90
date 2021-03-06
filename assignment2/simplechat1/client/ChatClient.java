// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
	//**** changed for E50
    if (message.charAt(0) == '#') {
    	String command = message;
    	String[] split = message.split(" ");
    	switch (split[0]) {
        case "#exit":
            quit();
            break;
        case "#logoff":
            try {
                closeConnection(); }
            catch (IOException e) {
                System.out.println("Error closing connection"); }
            break;
        case "#sethost":
            if (this.isConnected()) {
                System.out.println("Already connected"); }
            else {
                this.setHost(split[1]); }
            break;
        case "#setport":
            if (this.isConnected()) {
                System.out.println("Already connected"); }
            else {
                this.setPort(Integer.parseInt(split[1])); }
            break;
        case "#login":
            if (this.isConnected()) {
                System.out.println("Already connected"); } 
            else {
                try {
                    this.openConnection(); } 
                catch (IOException e) {
                    System.out.println("Error server not open"); }
    		}
            break;
        case "#gethost":
            System.out.println("The host is " + this.getHost());
            break;
        case "#getport":
            System.out.println("The Port is " + this.getPort());
            break;
        default:
            System.out.println("Invalid command: '" + command+ "'");
            break;
    	}
    }
	else{
	  try {
		  sendToServer(message);}
	  catch(IOException e){
		  clientUI.display ("Could not send message to server.  Terminating client.");
		  quit(); }
  	}
  }
  
  //**** made for E49
  public void connectionClosed() {
      System.out.println("Connection to server closed"); }
  
  public void connectionException(Exception exception) {
      System.out.println("The server has stopped listening for connections");
      quit();
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class
