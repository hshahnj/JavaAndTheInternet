/******************************************************************************
*	Program Author: Dr. Yongming Tang for CSCI 6810 Java and the Internet	  *
*	Date: September, 2012													  *
*******************************************************************************/

import java.io.*;
import java.net.*;

public class SocketServer_V2
{
   public static void main(String[] args) throws IOException {

      if (args.length != 1) {
	  		System.err.println("Usage: java SocketServer port");
	 		return;
      }
      Socket echoSocket;
      try {
	 		ServerSocket s = new ServerSocket(Integer.parseInt(args[0]));
	 		for(;;) {
	 			echoSocket = s.accept();
	 			System.out.println("Connection from: " + echoSocket.getInetAddress());
	        	HandleRequestThread NewReq = new HandleRequestThread(echoSocket);
	        	NewReq.start();
			}
      }
      catch (Exception e) {
	  	System.err.println(e);
	 	return;
      }
   }
}

class HandleRequestThread extends Thread
{     Socket echoSocket;
      InputStream sin = null;
      OutputStream sout  = null;
      byte[] b = new byte[1024];
      int i;
	public HandleRequestThread(Socket S) {
		try {
			echoSocket=S;
			sin = echoSocket.getInputStream();
       	 	sout= echoSocket.getOutputStream();
		}
		catch (Exception e) {
			 System.err.println(e);
         }
	}

	public void run() {
		try {

			 while ((i = sin.read(b)) != -1) {
		            byte[] temp = new byte[i+2];
		            temp[0] = (byte) '*';
		            for (int k=1; k<=i; k++)
		               temp[k] = b[k-1];
		            temp[i+1] = (byte)'*';
			    sout.write(temp, 0, i+2);
			    sout.flush();
			 }
		 }
		 catch (Exception e) {
			 System.err.println(e);
			 return;
         }
	}
}
