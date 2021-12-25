package socket3;

import java.io.*;
import java.net.*;
import java.util.*;

public class TcpSender {
	
	private InetAddress host;
	private final int port;
	

	public TcpSender(int port) {
		this.port = port;
		this.openPort();
		this.accessServer();
	}
	
	synchronized void openPort() {
		try {
			host = InetAddress.getLocalHost();
		} catch (UnknownHostException u) {
			System.out.println("Host bilgisi bulunamadý");
			System.exit(1);
		}
	}
	
	
	private void accessServer() {
		Socket link = null;
		
		try {
		 link = new Socket(host, port);
		 
		 Scanner input = new Scanner(link.getInputStream());
		 
		 PrintWriter output = new PrintWriter(link.getOutputStream(), true);
		 
		 for(int i = 0; i < 6; i++) {
			 
			 System.out.println("Kaç paket gönderilsin : ");
			 
			 Scanner userEntry = new Scanner(System.in);
			 
			 String message, response, str2;
			 int number;
			 
			 response = userEntry.nextLine();
			 
			 number = Integer.parseInt(response);
			 
			 int counter = 0, attempt = 0;
			 
			 long startTime = System.nanoTime();
			 
			 do {
				 
				 message = "PCK";
				 output.print(message + counter);
				 attempt++;
				 
				 link.setSoTimeout(4000);
				 
				 String str = input.nextLine();
				 str2 = str.substring(0, 3);
				 
				 while (!str2.equals("ACK")) {
						
						System.out.println(message + counter + "Resending..");
						output.println(message + counter);
						attempt++;
						
						str = input.nextLine();
						str2 = str.substring(0, 3);
					}
				 
				 System.out.println(str2 + "alýcýdan baþarýyla alýndý");
				 counter++;
				
			} while (counter < number);
			 
			 long endTime = System.nanoTime();
				
				System.out.println("Toplam deneme sayýsý : " + attempt);
				System.out.println("Tüm paketler gönderilirken geçen süre : " +
				(endTime - startTime) + "nano saniye");
		 }
		 
		 output.println("CLOSE");
			
		} catch (IOException i) {
			i.printStackTrace();
		} finally {
			try {
				System.out.println("Sender Kapatýlýyor");
				link.close();
			} catch (IOException i) {
				i.printStackTrace();
				System.exit(1);
			}
		}
	}




	public static void main(String[] args) {
		
		TcpSender tcpSender = new TcpSender(1000);
		tcpSender.accessServer();

	}

}
