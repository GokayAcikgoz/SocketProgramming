package socket3;

import java.net.InetAddress;

public class Router {

	public static void main(String[] args) {
		
		try {
			TcpRouter.host = InetAddress.getLocalHost();
		} catch (Exception e) {
			System.out.println("Host Id bulunamadý");
			System.exit(1);
		}
		
		TcpRouter tcpRouter8 = new TcpRouter(8000, 9000);
		TcpRouter tcpRouter7 = new TcpRouter(7000, 9000);
		TcpRouter tcpRouter6 = new TcpRouter(6000, 7000, 8000);
		TcpRouter tcpRouter5 = new TcpRouter(5000, 6000, 8000);
		TcpRouter tcpRouter4 = new TcpRouter(4000, 6000, 7000);
		TcpRouter tcpRouter3 = new TcpRouter(3000, 4000, 5000);
		TcpRouter tcpRouter2 = new TcpRouter(2000, 3000, 5000);
		TcpRouter tcpRouter1 = new TcpRouter(1000, 3000, 4000);
		
		
		tcpRouter1.start();
		tcpRouter2.start();
		tcpRouter3.start();
		tcpRouter4.start();
		tcpRouter5.start();
		tcpRouter6.start();

	}

}
