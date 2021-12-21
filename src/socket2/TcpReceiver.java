package socket2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TcpReceiver {
	
	//serverSocket'i kullanabilmek i�in ServerSocket t�r�nde bir serverSocket olu�turduk.
	private static ServerSocket serverSocket;
	
	//al�c�n�n port numaras�n� belirledik. Final tan�mlad�k ��nk� override edilmesin diye.
	private static final int port = 1240;
	

	public static void main(String[] args) {
		
		System.out.println("Opening port");
		
		try {
			
			serverSocket = new ServerSocket(port);
			
		} catch (IOException i) {
			
			System.out.println("Al�c� ba�lant� noktas�na ba�lanam�yor!");
			
			System.exit(1);
		}
		
		handeRouter();

	}
	
	
	
	private static void handeRouter() {
		
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

}
