
import java.io.*;
import java.net.*;
import java.util.*;

public class BeatBoxServer 
{
	ArrayList<ObjectOutputStream> clientOutputStreams;
	
	public static void main(String[] args) 
	{
		new BeatBoxServer().go();
	}
	
	public class ClientHandler implements Runnable
	{
		ObjectInputStream in;
		Socket clientSocket;
		
		public ClientHandler(Socket socket)
		{
			try
			{
				clientSocket = socket;
				in = new ObjectInputStream(socket.getInputStream());
			}
			catch(Exception x)
			{
				
			}
		}
		
		
		
		public void run()
		{
			Object o2 = null;
			Object o1 = null;
			
			try
			{
				while((o1 = in.readObject()) != null)
				{
					o2 = in.readObject();
					
					tellEveryone(o1, o2);
				}
			}
			catch(Exception x)
			{
				
			}
		}
	}
	
	public void go()
	{
		clientOutputStreams = new ArrayList<ObjectOutputStream>();
		
		try 
		{
			ServerSocket serverSock = new ServerSocket(4422);
			
			while(true)
			{
				Socket clientSocket = serverSock.accept();
				ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
				clientOutputStreams.add(out);
				
				Thread t = new Thread(new ClientHandler(clientSocket));
				
				t.start();
				
			}
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void tellEveryone(Object one, Object two)
	{
		Iterator it = clientOutputStreams.iterator();
		
		while(it.hasNext())
		{
			try
			{
				ObjectOutputStream out = (ObjectOutputStream) it.next();
				out.writeObject(one);
				out.writeObject(two);
			}
			catch(Exception x)
			{
				
			}
		}
	}
}
