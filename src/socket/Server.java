package socket;

import java.net.*;
import java.io.*;


public class Server {
	
	
	private Socket socket;
	private ServerSocket serverSocket;
	private DataInputStream dataInputStream;
	
	public Server(int port) {
		try {
			
			serverSocket = new ServerSocket(port);
			System.out.println("Baðlantý Baþarýlý");
			
			System.out.println("Client bekleniyor...");
			
			//baðlantý iþlemini kabul eder.
			socket = serverSocket.accept();
			
			System.out.println("Client baðlandý");
			
			//clientten bilgi alýr
			dataInputStream = new DataInputStream(
					new BufferedInputStream(socket.getInputStream()));
			
			String line = "";
			
			while(!line.equals("over")) {
				try {
					
					//lineý okumamýzý saðlar.
					line = dataInputStream.readUTF();
					System.out.println(line);
					
				} catch (IOException i) {
					System.out.println(i);
				}
			}
			
			System.out.println("Baðlantý sonlandý");
			
			//baðlantýlarý kapattýk
			socket.close();
			dataInputStream.close();
			
		} catch (IOException i) {
			System.out.println(i);
		}
	}
	
	
	

	public static void main(String[] args) {
		
		Server server = new Server(4000);
		
	}

}
