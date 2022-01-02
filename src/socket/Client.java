package socket;

import java.net.*;
import java.io.*;

public class Client {
	
	private Socket socket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	
	public Client(String address, int port) {
		
		try {
			socket = new Socket(address, port);
			
			System.out.println("Baðlandý");
			
			//clientten bilgiyi aldýk
			dataInputStream = new DataInputStream(System.in);
			
			//servera gönderdik
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			
		} catch (UnknownHostException u) {
			System.out.println(u);
			
		} catch (IOException i) {
			System.out.println(i);
		}
		
		String line = "";
		//there will start
		
		while(!line.equals("over")) {
			try {
				
				line = dataInputStream.readLine();
				dataOutputStream.writeUTF(line);
				
			} catch (IOException i) {
				
				System.out.println(i);
			}
		}
		
		try {
			dataInputStream.close();
			dataOutputStream.close();
			socket.close();
		} catch (IOException i) {
			System.out.println(i);
		}
		
	}

	public static void main(String[] args) {
		
		Client client = new Client("127.0.0.1", 4000);

	}

}
