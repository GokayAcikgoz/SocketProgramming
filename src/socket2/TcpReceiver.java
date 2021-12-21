package socket2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TcpReceiver {
	
	//serverSocket'i kullanabilmek için ServerSocket türünde bir serverSocket oluþturduk.
	private static ServerSocket serverSocket;
	
	//alýcýnýn port numarasýný belirledik. Final tanýmladýk çünkü override edilmesin diye.
	private static final int port = 1240;
	

	public static void main(String[] args) {
		
		System.out.println("Opening port");
		
		try {
			
			serverSocket = new ServerSocket(port);
			
		} catch (IOException i) {
			
			System.out.println("Alýcý baðlantý noktasýna baðlanamýyor!");
			
			System.exit(1);
		}
		
		handeRouter();

	}
	
	
	
	private static void handeRouter() {
		
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

}
