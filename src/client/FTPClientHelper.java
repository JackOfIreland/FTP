package client;

import javafx.stage.FileChooser;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import javax.swing.*;
import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * This class is a module which provides the application logic
 * for an Echo client using connectionless datagram socket.
 * @author M. L. Liu
 */
public class FTPClientHelper {
   private MyClientDatagramSocket mySocket;
   private InetAddress serverHost;
   private int serverPort;

   FTPClientHelper(String hostName, String portNum)
      throws SocketException, UnknownHostException { 
  	   this.serverHost = InetAddress.getByName(hostName);
  		this.serverPort = Integer.parseInt(portNum);
      // instantiates a datagram socket for both sending
      // and receiving data
   	this.mySocket = new MyClientDatagramSocket(); 
   } 
	
   public String getEcho( String message) 
      throws SocketException, IOException {                                                                                 
      String echo = "";    
      mySocket.sendMessage( serverHost, serverPort, message);
	   // now receive the echo
      echo = mySocket.receiveMessage();
      return echo;
   }

   public String logIn(String username)
           throws SocketException, IOException {

      mySocket.sendMessage(serverHost, serverPort, "100-LOGIN-" + username);
      String receiveMessage = mySocket.receiveMessage();
      return receiveMessage;
   }

   public String uploadFile(File fileToSend)
           throws SocketException, IOException {
      Path p = Paths.get(fileToSend.getPath());
      List<String> lines = Files.readAllLines(p);
      mySocket.sendMessage(serverHost, serverPort, "200-UPLOAD-" + lines);
      String receiveMessage = mySocket.receiveMessage();
      return receiveMessage;
   }

   public File chooseFile()
   {
      JFrame frame = new JFrame();
      frame.setVisible(true);
      frame.setExtendedState(JFrame.ICONIFIED);
      frame.setExtendedState(JFrame.NORMAL);

      JFileChooser fc = new JFileChooser();
      if(JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)){
         frame.setVisible(false);
         return fc.getSelectedFile();
      }else {
         System.out.println("Please ensure you choose a file");
         System.exit(1);
      }
      return null;
      // adapted from http://stackoverflow.com/questions/7494478/jfilechooser-from-a-command-line-program-and-popping-up-underneath-all-windows
   }


   public String logOut()
           throws SocketException, IOException {

      mySocket.sendMessage( serverHost, serverPort, "400:LOGOUT:");
      String receiveMessage = mySocket.receiveMessage();
      return receiveMessage;
   }

   public void done( ) throws SocketException {
      mySocket.close( );
   }  //end done

} //end class
