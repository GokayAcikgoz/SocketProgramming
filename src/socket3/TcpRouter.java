package socket3;

import java.io.*;
import java.net.*;
import java.util.*;

public class TcpRouter extends Thread{
	
	private ServerSocket serverSocket;
	public static InetAddress host;
	private final int listenPort;
	private final int sendPort1;
	private final int sendPort2;
	private final int controllerPort = 5056;
	
	private Socket sendLink1 = null;
	private Socket sendLink2 = null;
	private Socket controllerLink = null;
	
	
	public TcpRouter(int listenPort, int sendPort1) {
		this.listenPort = listenPort;
		this.sendPort1 = sendPort1;
		this.sendPort2 = 0;
		this.openPort();
	}
	
	
	public TcpRouter(int listenPort, int sendPort1, int sendPort2) {
		this.listenPort = listenPort;
		this.sendPort1 = sendPort1;
		this.sendPort2 = sendPort2;
		this.openPort();
	}
	
	
	private synchronized void openPort() {
		System.out.println("Dinleme portu a��k : " + listenPort + getName());
		
		try {
			serverSocket = new ServerSocket(listenPort);
			sendLink1 = new Socket(host, sendPort1);
			if (sendPort2 != 0) {
				sendLink2 = new Socket(host, sendPort2);
				controllerLink = new Socket(host, controllerPort);
			}
			
			
			
		} catch (IOException i) {
			System.out.println("Router ba�lanam�yor.");
			System.exit(1);
		}
	}
	
	@Override
	public void run() {
		
		Socket link = null;
		String str2 = null;
		
		try {
			//Sockete yap�lacak ba�lant� i�in kabul i�lemi.
			link = serverSocket.accept();
			
			Scanner input = new Scanner(link.getInputStream());
			
			PrintWriter output = new PrintWriter(link.getOutputStream(), true);
			
			String message = input.nextLine();
			
			Scanner input2 = new Scanner(sendLink1.getInputStream());
			
			PrintWriter output2 = new PrintWriter(sendLink1.getOutputStream(), true);
			
			Scanner input3 = new Scanner(sendLink2.getInputStream());
			
			PrintWriter output3 = new PrintWriter(sendLink2.getOutputStream(), true);
			
			Scanner input4 = new Scanner(controllerLink.getInputStream());
			
			PrintWriter output4 = new PrintWriter(controllerLink.getOutputStream(), true);
			
			while(!message.equals("CLOSE")) {
				
				System.out.println("G�ndericiden mesaj " + message);
				output4.println(listenPort);
				
				Random randomGenerator = new Random();
				
				//0-100 aras� rastgele say� �retmek i�in
				int randomInt = randomGenerator.nextInt(100);
				
				System.out.println("Paket i�in olu�turulan rastgele sayi : "+randomInt);
				
				if (randomInt>19) {
					output2.println(message);
					
					String str = input2.nextLine();
					System.out.println("Al�c�dan gelen mesaj : "+str);
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
				System.out.println("Kapatma ba�lant�lar�(router)");
				link.close();
				sendLink1.close();
			} catch (IOException i) {
				System.out.println("Ba�lant� kapand�");
				System.exit(1);
			}
			
		}
		
	}
	
	

}
