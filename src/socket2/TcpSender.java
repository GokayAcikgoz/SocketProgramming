package socket2;

import java.io.*;
import java.net.*;
import java.util.*;

public class TcpSender extends Thread{
	
	private static InetAddress host;
	private static final int port = 1241;
	

	public static void main(String[] args) {
		
		try {
			
			//Ip adresimizi yerelde �al��acak �ekilde tan�mlad�k.
			host = InetAddress.getLocalHost();
			System.out.println("Router Ip adresi : ");
			
		} catch (Exception e) {
			System.out.println("Ip adresi bulunamad�");
			System.exit(1);
		}
		
		accessServer();

	}
	
	
	private static void accessServer() {
		Socket link = null;
		
		try {
			//socketimize host ve port numaras�n� verdik buna g�re i�lem yapacak.
			link = new Socket(host, port);
			
			//linki okuttuk yine
			Scanner input = new Scanner(link.getInputStream());
			
			PrintWriter output = new PrintWriter(link.getOutputStream(), true);
			
			for(int i = 0; i < 6; i++) {
				
				System.out.println("Ka� paket g�nderilsin?");
				
				//Kullan�c�dan al�yoruz
				Scanner userEntry = new Scanner(System.in);
				
				String message, str2, response;
				int number;
				
				//kullan�c�dan ald��m�z paket adetini response de�i�kenine atad�k.
				response = userEntry.nextLine();
				
				//bu de�eri integer'a �evirdik ve number de�i�kenine atad�k.
				number = Integer.parseInt(response);
				
				int counter = 0;
				int attempt = 0;
				
				//ba�lang�� s�remizi tutuyoruz
				long startTime = System.nanoTime();
				
				do {
					
					message = "PCK";
					
					//PCK 0, PCK 1....
					output.println(message + counter);
					attempt++;
					
					//4000 milisaniye bekler ve tekrar yollar.
					link.setSoTimeout(4000);
					
					String str = input.nextLine();
					str2 = str.substring(0, 3);
					
					//E�er paket giderken bir hata meydana gelirse bu blok �al���r
					while (!str2.equals("ACK")) {
						
						System.out.println(message + counter + "Resending..");
						output.println(message + counter);
						attempt++;
						
						str = input.nextLine();
						str2 = str.substring(0, 3);
					}
					
					System.out.println(str2 + "al�c�dan ba�ar�yla al�nd�");
					
					counter++;
					
				 // yukar�da paket adetini alm��d�k counter k���k number oldu�u s�rece �al��acak
				//yani paketler tam olarak gidene kadar �al���r.
				} while(counter < number);
				
				long endTime = System.nanoTime();
				
				System.out.println("Toplam deneme say�s� : " + attempt);
				System.out.println("T�m paketler g�nderilirken ge�en s�re : " +
				(endTime - startTime) + "nano saniye");
				
				output.println("CLOSE");
			}
			
		} catch (IOException i) {
			i.printStackTrace();
		} finally {
			try {
				System.out.println("Sender Kapat�l�yor");
				link.close();
			} catch (IOException i) {
				i.printStackTrace();
				System.exit(1);
			}
		}
	}

}
