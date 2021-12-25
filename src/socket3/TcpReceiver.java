package socket3;

import java.io.*;
import java.net.*;
import java.util.*;

public class TcpReceiver {
	
	private ServerSocket serverSocket;
	
	private final int port;
	
	
	public TcpReceiver(int port) {
	
		this.port = port;
		this.openPort();
		
		do {
			handeRouter();
		} while (true);
		
	}
	
	synchronized void openPort() {
		
		System.out.println("Receiver portu a��k");
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException i) {
			System.out.println("Receiver porta ba�lanam�yor.");
			System.exit(1);
		}
		
	}
	
	
	private void handeRouter() {
		
		Socket link = null;
		
		try {
			
			//ba�lant�y� ba�latt�k
			link = serverSocket.accept();
			
			//giri� ak��� d�nd�r�r. linkin okunmas�n� sa��ar
			Scanner input = new Scanner(link.getInputStream());
			
			//��k�� ak��� d�nd�r�r. sys.out gibi print i�lemleri gibi dosya yazmak.
			PrintWriter output = new PrintWriter(link.getOutputStream(),true);
			
			//ACK i�in yaz�ld�
			int numMessages = 0;
			
			//kullan�c�dan bir mesaj ald�k.
			String message = input.nextLine();
			
			//while d�nd�s� true oldu�u s�rece �al���r.
			//burada mesaj e�it de�ilse close oldu�u s�rece �al��acak.
			while(!message.equals("close")) {
				
				//g�ndericiden gelen ack mesaj�n� verir.
				output.println("ACK" + numMessages);
				numMessages++;
				
				System.out.println(numMessages + ":" + message);
				message = input.nextLine();
			}
			
			//socket bize IOException f�rlatabilir bu y�zden bunun kontrolu yap�ld�.
		} catch (IOException i) {
			i.printStackTrace();
			
			//Final blo�u her ko�ulda �al���r.
		} finally {
			try {
				System.out.println("Ba�lant� kapat�l�yor.(Receiver)");
				
				//socketimizi kapatt�k
				link.close();
			} catch (IOException i) {
				System.out.println("Ba�lant� kapat�lamad�!!");
				
				//sistemden ��k�� yap�ld�. 1 hata oldu�u durumlarda kullan�l�r.
				System.exit(1);
			}
		}
	}
	



	public static void main(String[] args) {
		
		TcpReceiver tcpReceiver = new TcpReceiver(9000);
		tcpReceiver.handeRouter();

	}

}
