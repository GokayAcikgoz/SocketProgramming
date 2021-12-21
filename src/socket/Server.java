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
			System.out.println("Ba�lant� Ba�ar�l�");
			
			System.out.println("Client bekleniyor...");
			
			//ba�lant� i�lemini kabul eder.
			socket = serverSocket.accept();
			
			System.out.println("Client ba�land�");
			
			//clientten bilgi al�r
			dataInputStream = new DataInputStream(
					new BufferedInputStream(socket.getInputStream()));
			
			String line = "";
			
			while(!line.equals("over")) {
				try {
					
					//line� okumam�z� sa�lar.
					line = dataInputStream.readUTF();
					System.out.println(line);
					
				} catch (IOException i) {
					System.out.println(i);
				}
			}
			
			System.out.println("Ba�lant� sonland�");
			
			//ba�lant�lar� kapatt�k
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
