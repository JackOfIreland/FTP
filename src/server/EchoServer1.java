package server;

import com.sun.org.apache.bcel.internal.generic.Select;

import java.io.*;

/**
 * This module contains the application logic of an echo server
 * which uses a connectionless datagram socket for interprocess 
 * communication.
 * A command-line argument is required to specify the server port.
 * @author M. L. Liu
 */

public class EchoServer1 {
   public static void main(String[] args) {
      int serverPort = 7;    // default port
      String currentUser=null;
      String downloadDirectory = "C:/Users/jack";
      if (args.length == 1 )
         serverPort = Integer.parseInt(args[0]);       
      try {
         // instantiates a datagram socket for both sending
         // and receiving data
   	   MyServerDatagramSocket mySocket = new MyServerDatagramSocket(serverPort); 
         System.out.println("File Transfer server ready.");
         while (true) {  // forever loop
            DatagramMessage request = 
               mySocket.receiveMessageAndSender();
            System.out.println("Request received");
            String message = request.getMessage( );
            System.out.println("message received: "+ message);
            String[] messageComponants = message.split("-");


            switch (messageComponants[0]){
               case "100":
                  currentUser = messageComponants[2];
                  checkForDirectory(currentUser);
                  mySocket.sendMessage(request.getAddress( ),
                          request.getPort( ), "101 ok;test response");
                  break;
            }

            switch (messageComponants[0]){
               case "200":

                  File s = new File(downloadDirectory);
                  if (s.exists() && s.isDirectory())
                  {
                  FileOutputStream fos = new FileOutputStream(downloadDirectory);
                  fos.write(Integer.parseInt(messageComponants[2]));
                  fos.close();}
                  break;
            }

            // Now send the echo to the requestor
            mySocket.sendMessage(request.getAddress( ),
               request.getPort( ), message);
		   } //end while
       } // end try
	    catch (Exception ex) {
          ex.printStackTrace( );
	    } // end catch
   } //end main

   private static void checkForDirectory(String message) {
      File f = new File("C:\\Users\\t00168584\\Desktop\\"+ message.trim());
      if (f.exists())
      {
         System.out.println("Directory exists for " + message.trim());
      }
      else
      {
         System.out.println("Creating directory called " + message.trim());
         f.mkdir();

      }
   }

} // end class      
