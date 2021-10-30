import java.io.*;

import common.ChatIF;


public class ServerConsole implements ChatIF{
	//instance variables
	EchoServer server;

	String message;

	private int port;
	
	public ServerConsole(int port)
	{
		//super(port);
		this.port = port;	
	}

	@Override
	public void display(String message) {
		String msg = "SERVERMSG>"+message;
		System.out.println(msg);
		server = new EchoServer(port);
		server.sendToAllClients(msg);
		
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
