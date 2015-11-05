package client;

import java.io.*;

/**
 * This module contains the presentaton logic of an Echo Client.
 * @author M. L. Liu
 */
public class

        EchoClient1 {
   static final String endMessage = ".";
   public static void main(String[] args) {
      InputStreamReader is = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(is);
      try {
         System.out.println("Welcome to the FTP Program.\n" +
                            "What is the name of the server host?");
         String hostName = br.readLine();
         if (hostName.length() == 0) // if user did not enter a name
            hostName = "localhost";  //   use the default host name
         System.out.println("What is the port number of the server host?");
         String portNum = br.readLine();
         if (portNum.length() == 0)
            portNum = "7";          // default port number
         EchoClientHelper1 helper = 
            new EchoClientHelper1(hostName, portNum);
         boolean done = false;
         String message, echo;

         System.out.println("Please login with your username");
         message = br.readLine( );
         System.out.println(helper.logIn(message));

         while (!done) {
            System.out.println("Please choose an option: \nType A for Upload \nType B for Download \nType C for LogOff");
            message = br.readLine( );

            if ((message.trim().toLowerCase()).equals("a")){
               done = true;
               helper.done( );
            }
            if ((message.trim().toLowerCase()).equals("b")){
               done = true;
               helper.done( );
            }
            if ((message.trim().toLowerCase()).equals("c")){
               done = true;
               helper.done( );
            }
            else {
               echo = helper.getEcho( message);
               System.out.println(echo);
            }
          } // end while
      } // end try  
      catch (Exception ex) {
         ex.printStackTrace( );
      } // end catch
   } //end main
} // end class      
