//this class was created for question 50
import java.io.*;

import client.ChatClient;
import common.ChatIF;
import ocsf.client.AbstractClient;


public class ServerConsole implements ChatIF{
	//instance variables
	EchoServer server;
	Thread client;

	String message;

	int port;
	
	public ServerConsole(int port, EchoServer server)
	{
		this.port = port;	
		this.server = server;
	}

	@Override
	public void display(String message) {
		if (message.charAt(0) == '#')
		{
			String command = message;
			String[] split = message.split(" ");
			
			switch (split[0])
			{
			//quit command: if quit is typed then the EchoServer server terminates
			case "#quit":
				System.out.println("Terminating Server");
				System.exit(0);
				break;	
			//stop command: only stop listening for new clients, but current clients remain connected
			case "#stop":
				
				if (server.isListening())
				{
					server.stopListening();
					System.out.println("Server stopped listening for connections");
				}
				else
				{
					System.out.println("Server is not currently listening for connections");
				}
				break;
			//close command: stop listening for connections first, then disconnect clients, then close the server
			case "#close":
				if (server.isListening())
				{
					server.stopListening();
					System.out.println("Server has stopped listening for connections. Now disconnecting clients");
					try {
						server.close();
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("Unexpected Error while attempting to close");
					}
				}
				break;
			//setport command: if server is currently set to a port and listening, a port can be set; #setport XXXX,
				//where XXXX represents integers for the port number
			case "#setport":
				if (server.isListening())
				{
					System.out.println("The Server is already listening for connections");
				}
				else
				{
					this.server.setPort(Integer.parseInt(split[1]));
				}
				break;
			//start command: if server isn't listening for connections, command makes it start listening
			case "#start":
				if (server.isListening())
				{
					System.out.println("The Server is already listening for connections");
				}
				else
				{
					try {
						server.listen();
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("Unexpected Error, server can't listen for clients");
					}
				}
				break;
			//getport command: displays the port number to the end-user of the server 
			case "#getport":
				if (server.isListening())
				{
					System.out.println("The Server is listening for clients on port "+server.getPort());
				}
				else
				{
					System.out.println("The Server is currently not listening for connections");
				}
				break;
			default:
				System.out.println("Invalid command: '" + command+ "'");
		        break;
			}
			
		}
		else
		{
			String msg = "SERVER MSG>"+message;
			System.out.println(msg);
			this.server.sendToAllClients(msg);
		}
		
		
	}
	
	public void run()
	{
		while(true)
		{
			try
			{
				BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
				message = fromConsole.readLine();
				display(message);
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("Error Reading from Console.");
			}
		}
	}
	
	
}
