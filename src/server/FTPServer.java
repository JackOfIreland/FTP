package server;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


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
            System.out.println("\nRequest received");
            String message = request.getMessage( );
            System.out.println("message received: "+ message);
            String[] messageComponants = message.split("-");



            switch (messageComponants[0]){
               ///////Login
               case "100":
                  currentUser = messageComponants[2].trim();
                  String returnLogInMessage = checkForDirectory(currentUser);
                  mySocket.sendMessage(request.getAddress( ),
                          request.getPort( ), returnLogInMessage);
                  break;

               case "200":
                  //upload to server
                  byte[] receivedBytes = mySocket.receiveFile();
                  String fileName = messageComponants[2].trim();

                  String returnUploadMessage = receiveFile(receivedBytes,currentUser, fileName);

                  mySocket.sendMessage(request.getAddress(),
                          request.getPort(), returnUploadMessage);
                  break;

               case "300":
                  //download to client
                  String nameOfFileToBeDownloaded = messageComponants[2].trim();
                  String filePath = "C:\\FTP Server\\"+currentUser+"\\" + nameOfFileToBeDownloaded;

                  List<String> content = sendFileToClient(filePath, currentUser);

                  mySocket.sendMessage(request.getAddress(),
                          request.getPort(), nameOfFileToBeDownloaded+ " has been downloaded to C:/FTP Client.-" + content.toString());


                  break;


               case "400":

                  String returnLogOutMessage = logOut(currentUser);
                  mySocket.sendMessage(request.getAddress( ),
                          request.getPort( ), returnLogOutMessage);
                  break;
            }


		   } //end while
       } // end try
	    catch (Exception ex) {
          ex.printStackTrace( );
	    } // end catch
   } //end main

   private static List<String> sendFileToClient(String filePath, String currentUser) throws IOException {
      List<String> content = new ArrayList<String>();

      BufferedReader buffer = new BufferedReader(new FileReader(filePath));

      String s;
      while((s = buffer.readLine()) != null){
         content.add(s);
      }
      System.out.println("301-"+currentUser + " is downloading " + filePath);
      return content;
   }

   private static String receiveFile(byte [] receivedBytes, String currentUser,String fileName) throws IOException {

      File fileReceived = new File("C:\\FTP Server\\"+currentUser+"\\" + fileName);
      System.out.println("201-File Received: " + fileName);
      FileOutputStream fos = new FileOutputStream(fileReceived);
      fos.write(receivedBytes);
      fos.close();
      return fileReceived.getName().trim() + " was successfully Uploaded to " + "C:/FTP Server" +"/"+currentUser.trim();
   }



   private static String checkForDirectory(String message) {
      File f = new File("C:/FTP Server/"+ message.trim());
      if (f.exists())
      {
         System.out.println("101-Directory exists for " + message.trim());
         return "Welcome back to the system, " + message.trim();
      }
      else
      {
         System.out.println("102-Creating directory called " + message.trim());
         f.mkdir();
         return "We have detected you are a new user. We hope you enjoy the system,  " + message.trim();
      }
   }

   private static String logOut(String username) {
      System.out.println("401-"+username + " is logging off");
      return "Thanks for using the system, " + username + "\n\n";
   }

} // end class      
