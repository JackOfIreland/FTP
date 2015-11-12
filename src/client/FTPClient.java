package client;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.*;

/**
 * This module contains the presentaton logic of an Echo Client.
 * @author M. L. Liu
 */
public class FTPClient {
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
         boolean done = false, exit = false;
         String userName ="", message, choice,  echo;

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
         while (!exit) {
            System.out.println("Please login with your username or type exit");
            userName = br.readLine();

            if(userName.toLowerCase().equals("exit")){
               System.out.println("System shutting down...");
               System.exit(0);
            }

            else{
               done=false;
               System.out.println(helper.logIn(userName));

               while (!done) {
                  System.out.println("\n" + userName + ", please choose an option: \nType A for Upload \nType B for Download \nType C for LogOff");
                  choice = br.readLine().toLowerCase();

                  switch (choice) {
                     //////Upload///////
                     case "a":
                        File fileToSend = helper.chooseFile();
                        String fileName = fileToSend.getName();
                        System.out.println(helper.uploadFile(fileToSend, fileName));
                        break;

                     case "b":

                        System.out.println("Please enter the name of the file you want to download"); //use testDownload.txt for testing
                        String fileToDownload = br.readLine().toLowerCase();
                        String s = helper.downloadFile(fileToDownload);
                        File fileDownloaded = new File("C:\\FTP Client\\" + fileToDownload);
                        PrintWriter prW = new PrintWriter(fileDownloaded);
                        prW.write(s);
                        prW.close();



                        break;

                     case "c":
                        System.out.println(helper.logOut(userName));
                        done = true;
                        break;

                     default:
                        System.out.println("Invalid option; please retry");

                  }

               }
            } // end inner while
         }
      } // end try  
      catch (Exception ex) {
         ex.printStackTrace( );
      } // end catch
   } //end main
} // end class      
