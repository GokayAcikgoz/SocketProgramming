package socket4.Root;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Router extends Root{
    private Socket listenSocket = null;
    private Scanner listenInput;
    private PrintWriter listenOutput;
    
    //Bir elemanli dizi olusturuldu. MRouterda tek bir yoldan gitmesi icin.
    private int SENDPORTS[] = new int[1];
    
    //Constructor olusturduk. Router yollarini belirlemek icin lazým.
    public Router(int LISTENPORT,int[] SENDPORTS) {
        this.openListenPort(LISTENPORT);
        this.SENDPORTS = SENDPORTS;
    }
    
   
    public Router(int LISTENPORT,int SENDPORTT) {
        this.openListenPort(LISTENPORT);
        this.SENDPORTS[0] = SENDPORTT;
    }
    
    //Dinleme servisini baþlatýr 
    private void startListenService() throws IOException{
        do {            
            listenSocket = serverSocket.accept();
            listenInput = new Scanner(listenSocket.getInputStream());
            listenOutput = new PrintWriter(listenSocket.getOutputStream(), true);
            Thread CH = new ClientHandler(listenSocket,listenInput,listenOutput,SENDPORTS);
            CH.start();
        } while (true);
    }
    
    @Override
    public void run() {
        try {
            this.startListenService();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        } finally {
            try {
                System.out.println(
                    "\n* Closing connections (Router side)*");
                listenSocket.close();
               
            } catch (IOException ioEx) {
                System.out.println(
                    "Unable to disconnect!");
                System.exit(1);
            }
        }
    }
    
    
    
    // ClientHandler class
    class ClientHandler extends Thread
    {
        final Scanner dis;
        final PrintWriter dos;
        final Socket s;
        private int SENDPORTS[];
        // Constructor
        public ClientHandler(Socket s, Scanner dis, PrintWriter dos ,int[] SENDPORTS)
        {
            this.s = s;
            this.dis = dis;
            this.dos = dos;
            this.SENDPORTS = SENDPORTS;
        }

        @Override
        public void run()
        {
            try {
                handleClient();
            } catch (IOException ex) {
                Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        private String handleClient() throws IOException {
            String str2 =null ,str3= null;
            String message;
            Rsender sender;
            String PortName ;
            ArrayList<Rsender> list = new ArrayList<>();
            
           
           
            do {
              
                
                message = getMessage();
                //PCK mesaji
                System.out.println("message from sender " + message);
                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(100);
                System.out.println("Generated random number for the packet is: " + randomInt);
                if (randomInt > 19) { //for random probability 20%,each packet has a random number between 0 to 99
                    
                    if(this.SENDPORTS.length != 1){
                        int PORT = this.SENDPORTS[getRoute(this.SENDPORTS.length)];
                        PortName =" "+ getPortName(PORT) +" ";
                        sender = new Rsender(new Socket(host, PORT));
                    }else{
                        PortName =" "+ getPortName(this.SENDPORTS[0])  +" ";
                        sender = new Rsender(new Socket(host, this.SENDPORTS[0]));
                    }
                    sender.sendMessage(PortName+","+message);
                     
                    
                    String str = sender.getRequest();
                    //ACK mesaji
                    System.out.println("message from receiver: " + str);
                    sendRequest(PortName+","+str);
                    sender.closeConn();
                    sender = null;
                    System.gc();
                    Runtime.getRuntime().gc();
                } else {
                	sendRequest(str2);
                    
                }
            } while (!message.equals("***CLOSE***"));
            return null;
        }
        
        private void sendRequest(String message) {
        	dos.println(message);
        }
        
        private String getMessage() {
        	while(true) {
        		if (dis.hasNext()) {
					return dis.nextLine();
				}
        	}
        }
        
        
       
        public boolean getRoute(){
            Random randomGenerator = new Random();
            return randomGenerator.nextBoolean();
        }

        private int getRoute(int length) {
            Random randomgenarator = new Random();
            return randomgenarator.nextInt(length);
        }
        
        //Hangi yoldan gittiðimiz belli olsun diye
        public String getPortName(int PORT){
            return switch(PORT){
            case 1000->"ANKARA";
            case 1001->"CORUM";
            case 1002->"ESKÝSEHÝR";
            case 1003->"BOLU";
            case 1004->"SAMSUN";
            case 1005->"BURSA";
            case 1006->"KARABÜK";
            case 1007->"KASTAMONU";
            case 1008->"ZONGULDAK";
            case 1009->"BARTIN";
            default->"";
            
            };
                
           
        }
    }
    
   
}
