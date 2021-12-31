package socket4.SDN;

import socket4.Root.Receiver;

public class MReciever {

	public static void main(String[] args) {
		
		Receiver rc = new Receiver(1009);
		rc.start();
	}

}
