package socket2;

import java.io.*;  
import java.net.*;  
import java.util.*; 

public class TcpRouter {
	
	private static ServerSocket serverSocket;
	private static InetAddress host;
	private static final int port = 1241;
	private static final int port2 = 1240;
	
	private static Socket link2 = null;

	public static void main(String[] args) {
		
		System.out.println("Port Açýk");
		
		try {
			
			host = InetAddress.getLocalHost();
			System.out.println("Receiver Ip adresi");
			
		} catch (Exception e) {
			System.out.println("Host bilgisi bulunamadý!");
			System.exit(1);
		}
		
		
		try {
			
			serverSocket = new ServerSocket(port);
			
			//göndericiden alýcýya bir iþlem yapýlacaðý için bu kýsma port2 yi verdik
			link2 = new Socket(host, port2);
			
		} catch (IOException i) {
			System.out.println("Port routera baðlanamýyor");
			System.exit(1);
		}
		
		
		handleClient();

	}
	
	
	private static String handleClient() {
		
		Socket link = null;
		String str2 = null;
		
		try {
			//Sockete yapýlacak baðlantý için kabul iþlemi.
			link = serverSocket.accept();
			
			Scanner input = new Scanner(link.getInputStream());
			
			PrintWriter output = new PrintWriter(link.getOutputStream(), true);
			
			String message = input.nextLine();
			
			Scanner input2 = new Scanner(link2.getInputStream());
			
			PrintWriter output2 = new PrintWriter(link2.getOutputStream(), true);
			
			while(!message.equals("CLOSE")) {
				
				System.out.println("Göndericiden mesaj " + message);
				
				Random randomGenerator = new Random();
				
				//0-100 arasý rastgele sayý üretmek için
				int randomInt = randomGenerator.nextInt(100);
				
				System.out.println("Paket için oluþturulan rastgele sayi : "+randomInt);
				
				if (randomInt>19) {
					output2.println(message);
					
					String str = input2.nextLine();
					System.out.println("Alýcýdan gelen mesaj : "+str);
					output.println(str);
				}else {
					output.println(str2);
				}
				
				message = input.nextLine();
			}
			
		} catch (IOException i) {
			i.printStackTrace();
		} finally {
			try {
				System.out.println("Kapatma baðlantýlarý(router)");
				link.close();
				link2.close();
			} catch (IOException i) {
				System.out.println("Baðlantý kapandý");
				System.exit(1);
			}
			
		}
		
		return null;
	}

}
