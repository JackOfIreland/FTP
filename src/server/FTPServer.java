package server;

import com.sun.org.apache.bcel.internal.generic.Select;
import javafx.stage.FileChooser;

import java.io.*;

/**
 * This module contains the application logic of an echo server
 * which uses a connectionless datagram socket for interprocess 
 * communication.
 * A command-line argument is required to specify the server port.
 * @author M. L. Liu
 */

public class FTPServer {
   public static void main(String[] args) {
      int serverPort = 7;    // default port
      String currentUser=null;
      String downloadDirectory = "C:/FTP Server";
      File d = new File(downloadDirectory);
      if (!d.exists())
      { d.mkdir();}

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
                  String returnMessage = checkForDirectory(currentUser);
                  mySocket.sendMessage(request.getAddress( ),
                          request.getPort( ), returnMessage);
                  break;
            }

            switch (messageComponants[0]){
               case "200":

                  File s = new File(downloadDirectory);
                  s.mkdir();
                  s.setWritable(true);
                  if (s.exists() && s.isDirectory())
                  {
                     String fileReceived = messageComponants[2];

                     PrintWriter out = new PrintWriter("filename.txt");
                  }
                  break;
            }

            // Now send the echo to the requester
            mySocket.sendMessage(request.getAddress( ),
               request.getPort( ), message);
		   } //end while
       } // end try
	    catch (Exception ex) {
          ex.printStackTrace( );
	    } // end catch
   } //end main

   private static String checkForDirectory(String message) {
      File f = new File("C:/FTP Server/"+ message.trim());
      if (f.exists())
      {
         System.out.println("Directory exists for " + message.trim());
         return "Welcome back to the system, " + message.trim();
      }
      else
      {
         System.out.println("Creating directory called " + message.trim());
         f.mkdir();
         return "We have detected you are a new user. We hope you enjoy the system,  " + message.trim();
      }
   }

} // end class      
