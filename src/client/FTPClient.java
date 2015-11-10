package client;

import java.io.*;

/**
 * This module contains the presentaton logic of an Echo Client.
 * @author M. L. Liu
 */
public class

        FTPClient {
   static final String endMessage = ".";
   public static void main(String[] args) throws IOException {
      InputStreamReader is = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(is);
      try {
         /////////////////////Initialisation////////////////////////////////

         System.out.println("Welcome to the FTP Program.\n" +
                            "What is the name of the server host?");
         String hostName = br.readLine();
         if (hostName.length() == 0) // if user did not enter a name
            hostName = "localhost";  //   use the default host name
         System.out.println("What is the port number of the server host?");
         String portNum = br.readLine();
         if (portNum.length() == 0)
            portNum = "7";          // default port number
         FTPClientHelper helper =
            new FTPClientHelper(hostName, portNum);
         boolean done = false;
         String message, echo;

         //Create a test folder that has a file in it that we can use for upload
         File d = new File("C:/FTP Client");
         if (!d.exists())
         { d.mkdir();}

         File testFile = new File("C:/FTP Client/test file.txt");
         PrintWriter pW = new PrintWriter(testFile);
         pW.println("Hello there!");
         pW.println("This is a test File");
         pW.close();


         /////////////////////Login/////////////////////
         System.out.println("Please login with your username");
         message = br.readLine( );
         System.out.println(helper.logIn(message));

         while (!done) {
            System.out.println("\nPlease choose an option: \nType A for Upload \nType B for Download \nType C for LogOff\n");
            message = br.readLine( );

            //////Upload///////
            if ((message.trim().toLowerCase()).equals("a")){
               File fileToSend = helper.chooseFile();
               String fileName = fileToSend.getName();
               System.out.println(helper.uploadFile(fileToSend, fileName));

            }

            if ((message.trim().toLowerCase()).equals("b")){

            }
            if ((message.trim().toLowerCase()).equals("c")){
               done = true;
               helper.done( );
            }
            else {
               echo = helper.getEcho(message);
               System.out.println(echo);
            }
          } // end while
      } // end try  
      catch (Exception ex) {
         ex.printStackTrace( );
      } // end catch
   } //end main
} // end class      
