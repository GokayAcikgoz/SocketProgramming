package socket4.Root;

import java.io.*;
import java.net.*;
import java.util.*;

public class Sender extends Thread {
	
    private InetAddress host;
    private final int PORT;
    private Socket link = null;
    Scanner input;
    PrintWriter output;
    
    
    //Parametreli constructor
    //MSenderi cagirdigimiz zaman bizden Port numarasý ister.
    public Sender(int PORT) {
        this.PORT = PORT;
        this.serviceStart(PORT);
    }
    

    //Override islemi Thread classýndan gelmekte. Threadde Runnabledan implemente ettiði
    //için runnabledan gelmekte.
    //Yani runnable interfaceimiz thread ise manager sýnýfýmýz.
    @Override
    public void run() {
        try {
            while (true) {                
                this.accessServer();
            }
            
        } 
        catch (IOException ioEx) 
        {
            ioEx.printStackTrace();
        } 
        finally 
        {
            try 
            {
                System.out.println("\n* Closing connections (Sender side)*");
                output.println("***CLOSE***");
                link.close();
            } 
            catch (IOException ioEx) 
            {
                System.out.println("Baglanti Kesilemedi!");
                System.exit(1);
            }
        }
    }
    
    
    public void accessServer() throws IOException {
            
            
        System.out.println("Kaç paket gonderilsin? ");
        
        //Kullanicidan aliyoruz.
        Scanner userEntry = new Scanner(System.in);
        
        String message, str2, response;
        int number;

        response = userEntry.nextLine();
        number = Integer.parseInt(response);
        
        int counter = 0, attempt = 0;
        long startTime = System.nanoTime();
        do {

            message = "PCK";
            this.sendMessage(message + counter);
            attempt++;
            
            

            String request = this.getRequest();
            
            String[] split = request.split(",");
            
            str2 = split[split.length-1].substring(0, 3);
            
            while (!str2.equals("ACK")) {
                System.out.println(message + counter + " Resending...");
                output.println(message + counter);
                attempt++;
                split = this.getRequest().split(",");
                if(split != null){
                    str2 = split[split.length-1].substring(0, 3);
                    System.out.println(str2);
                }
                
                
            }
            System.out.println(request + " Alicidan basariyla alindi");
            counter++;
        } while (counter < number);
        
        long endTime = System.nanoTime();
        
        System.out.println("Top. Deneme sayisi : " + attempt);

        System.out.println("Paketler gidene kadar gecen süre : " +(endTime - startTime) + " nano seconds.");
        
    }
    
    public void sendMessage(String message) {
    	output.println(message);
    }
    
    public String getRequest() {
    	if (input.hasNext()) {
			return input.nextLine();
		}else {
			return "null";
		}
    }
    
    
    public void serviceStart(int PORT) {
    	try {
			host = InetAddress.getLocalHost();
			link = new Socket(host, PORT);
			input = new Scanner(link.getInputStream());
			output = new PrintWriter(link.getOutputStream(), true);
		} catch (Exception e) {
			System.exit(1);
		}
    }
    
    
    
    
       

    
    
}
