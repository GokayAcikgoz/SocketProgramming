package socket3;

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

public class Controllers {

	public static void main(String[] args) throws IOException {
		
		ServerSocket serverSocket = new ServerSocket(5056);
		
		while(true) {
			Socket socket = null;
			
			try {
				socket = serverSocket.accept();
				System.out.println("Client baðlandý " + socket);
				
				DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
				DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
				
				Thread thread = new ClientHandler(dataInputStream, dataOutputStream, socket);
				
				thread.start();
			} catch (Exception e) {
				socket.close();
			}
		}

	}

}


class ClientHandler extends Thread {
	
	private final DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	private Socket socket;
	
	public ClientHandler(DataInputStream dataInputStream, DataOutputStream dataOutputStream, Socket socket) {
		super();
		this.dataInputStream = dataInputStream;
		this.dataOutputStream = dataOutputStream;
		this.socket = socket;
	}
	
	
	@Override
	public void run() {
		
		String received;
		
		while(true) {
			try {
				received = dataInputStream.readUTF();
				if (received.equals("exit")) {
					System.out.println("client " + this.socket + " sends exit");
					System.out.println("Baðlantý Kapanýyor");
					this.socket.close();
					System.out.println("Baðlantý kapandý");
					break;
				}
				
				Date date = new Date();
				
				switch (received) {

					default:
						dataOutputStream.writeUTF("true");
					break;
				}
			} catch (IOException i) {
				i.printStackTrace();
			}
		}
		
		try {
			this.dataInputStream.close();
			this.dataOutputStream.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
		
	}
	
	
}
