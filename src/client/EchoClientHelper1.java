package client;

import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import java.net.*;
import java.io.*;

/**
 * This class is a module which provides the application logic
 * for an Echo client using connectionless datagram socket.
 * @author M. L. Liu
 */
public class EchoClientHelper1 {
   private MyClientDatagramSocket mySocket;
   private InetAddress serverHost;
   private int serverPort;

   EchoClientHelper1(String hostName, String portNum) 
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

      mySocket.sendMessage(serverHost, serverPort, "200-UPLOAD-" + fileToSend);



      String receiveMessage = mySocket.receiveMessage();
      return receiveMessage;
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
