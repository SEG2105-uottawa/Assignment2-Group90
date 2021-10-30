//this class was created for question 50
import java.io.*;

import common.ChatIF;


public class ServerConsole implements ChatIF{
	//instance variables
	EchoServer server;

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
			case "#quit":
				//quit gracefully
				
			case "#stop":
				//only stop listening for new clients, but current clients remain connected
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
			case "#close":
				//stop listening for new connections and disconnect all clients
				if (server.isListening())
				{
					server.stopListening();
					System.out.println("Server stopped listening for connections. Now disconnecting clients");
					try {
						server.close();
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("Unexpected Error while attempting to close");
					}
				}
				break;
			case "#setport":
				//set a port only if server is currently running
				if (server.isListening())
				{
					System.out.println("The Server is already listening for connections");
				}
				else
				{
					this.server.setPort(Integer.parseInt(split[1]));
				}
				break;
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
			}
			
		}
		String msg = "SERVER MSG>"+message;
		System.out.println(msg);
		this.server.sendToAllClients(msg);
		
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
