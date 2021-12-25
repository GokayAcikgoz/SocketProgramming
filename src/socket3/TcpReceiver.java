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
		
		System.out.println("Receiver portu açýk");
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException i) {
			System.out.println("Receiver porta baðlanamýyor.");
			System.exit(1);
		}
		
	}
	
	
	private void handeRouter() {
		
		Socket link = null;
		
		try {
			
			//baðlantýyý baþlattýk
			link = serverSocket.accept();
			
			//giriþ akýþý döndürür. linkin okunmasýný saðþar
			Scanner input = new Scanner(link.getInputStream());
			
			//çýkýþ akýþý döndürür. sys.out gibi print iþlemleri gibi dosya yazmak.
			PrintWriter output = new PrintWriter(link.getOutputStream(),true);
			
			//ACK için yazýldý
			int numMessages = 0;
			
			//kullanýcýdan bir mesaj aldýk.
			String message = input.nextLine();
			
			//while döndüsü true olduðu sürece çalýþýr.
			//burada mesaj eþit deðilse close olduðu sürece çalýþacak.
			while(!message.equals("close")) {
				
				//göndericiden gelen ack mesajýný verir.
				output.println("ACK" + numMessages);
				numMessages++;
				
				System.out.println(numMessages + ":" + message);
				message = input.nextLine();
			}
			
			//socket bize IOException fýrlatabilir bu yüzden bunun kontrolu yapýldý.
		} catch (IOException i) {
			i.printStackTrace();
			
			//Final bloðu her koþulda çalýþýr.
		} finally {
			try {
				System.out.println("Baðlantý kapatýlýyor.(Receiver)");
				
				//socketimizi kapattýk
				link.close();
			} catch (IOException i) {
				System.out.println("Baðlantý kapatýlamadý!!");
				
				//sistemden çýkýþ yapýldý. 1 hata olduðu durumlarda kullanýlýr.
				System.exit(1);
			}
		}
	}
	



	public static void main(String[] args) {
		
		TcpReceiver tcpReceiver = new TcpReceiver(9000);
		tcpReceiver.handeRouter();

	}

}
