package socket2;

import java.io.*;
import java.net.*;
import java.util.*;

public class TcpSender extends Thread{
	
	private static InetAddress host;
	private static final int port = 1241;
	

	public static void main(String[] args) {
		
		try {
			
			//Ip adresimizi yerelde çalýþacak þekilde tanýmladýk.
			host = InetAddress.getLocalHost();
			System.out.println("Router Ip adresi : ");
			
		} catch (Exception e) {
			System.out.println("Ip adresi bulunamadý");
			System.exit(1);
		}
		
		accessServer();

	}
	
	
	private static void accessServer() {
		Socket link = null;
		
		try {
			//socketimize host ve port numarasýný verdik buna göre iþlem yapacak.
			link = new Socket(host, port);
			
			//linki okuttuk yine
			Scanner input = new Scanner(link.getInputStream());
			
			PrintWriter output = new PrintWriter(link.getOutputStream(), true);
			
			for(int i = 0; i < 6; i++) {
				
				System.out.println("Kaç paket gönderilsin?");
				
				//Kullanýcýdan alýyoruz
				Scanner userEntry = new Scanner(System.in);
				
				String message, str2, response;
				int number;
				
				//kullanýcýdan aldýðmýz paket adetini response deðiþkenine atadýk.
				response = userEntry.nextLine();
				
				//bu deðeri integer'a çevirdik ve number deðiþkenine atadýk.
				number = Integer.parseInt(response);
				
				int counter = 0;
				int attempt = 0;
				
				//baþlangýç süremizi tutuyoruz
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
					
					//Eðer paket giderken bir hata meydana gelirse bu blok çalýþýr
					while (!str2.equals("ACK")) {
						
						System.out.println(message + counter + "Resending..");
						output.println(message + counter);
						attempt++;
						
						str = input.nextLine();
						str2 = str.substring(0, 3);
					}
					
					System.out.println(str2 + "alýcýdan baþarýyla alýndý");
					
					counter++;
					
				 // yukarýda paket adetini almýþdýk counter küçük number olduðu sürece çalýþacak
				//yani paketler tam olarak gidene kadar çalýþýr.
				} while(counter < number);
				
				long endTime = System.nanoTime();
				
				System.out.println("Toplam deneme sayýsý : " + attempt);
				System.out.println("Tüm paketler gönderilirken geçen süre : " +
				(endTime - startTime) + "nano saniye");
				
				output.println("CLOSE");
			}
			
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

}
