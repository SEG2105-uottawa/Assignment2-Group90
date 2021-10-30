package common;

public class ServerConsole implements ChatIF{
	//instance variables
	EchoServer server;


	@Override
	public void display(String message) {
		System.out.println("SERVERMSG>"+message);
		
	}
	public static void main(String args[0])
	{
		
	}
	
}
