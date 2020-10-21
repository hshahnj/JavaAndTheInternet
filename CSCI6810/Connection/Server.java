package Connection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
//
//        if (args.length != 1) {
//            System.err.println("Usage: java SocketServer port");
//            return;
//        }

        int port = 2020;
        Socket echoSocket;
        try {
//            ServerSocket s = new ServerSocket(Integer.parseInt(args[0]));
            ServerSocket s = new ServerSocket(port);
            for (; ; ) {
                echoSocket = s.accept();
                System.out.println("Connection from: " + echoSocket.getInetAddress());
                HandleRequestThread NewReq = new HandleRequestThread(echoSocket);
                NewReq.start();
            }
        } catch (Exception e) {
            System.err.println(e);
            return;
        }
    }
}

class HandleRequestThread extends Thread {
    Socket echoSocket;
    InputStream sin = null;
    OutputStream sout = null;
    byte[] b = new byte[1024];
    int i;
    StringBuilder t;
    String s;

    public HandleRequestThread(Socket S) {
        try {
            echoSocket = S;
            sin = echoSocket.getInputStream();
            sout = echoSocket.getOutputStream();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void run() {
        try {

//            BufferedReader rd = new BufferedReader(new InputStreamReader(sin));
//            s = rd.readLine();
//            System.out.println(s);
            int i = sin.read(b);
            System.out.write(b, 0, i);
            System.out.flush();

            while (i  != -1) {
                byte[] temp = new byte[i + 2];
                temp[0] = (byte) '*';
                for (int k = 1; k <= i; k++)
                    temp[k] = b[k - 1];
                temp[i + 1] = (byte) '*';
                sout.write(temp, 0, i + 2);
                sout.flush();
            }
        } catch (Exception e) {
            System.err.println(e);
            return;
        }
    }

//    public void run() {
//        try {
//
//            while ((i = sin.read(b)) != -1) {
//                byte[] temp = new byte[i + 2];
//                temp[0] = (byte) '*';
//                for (int k = 1; k <= i; k++)
//                    temp[k] = b[k - 1];
//                temp[i + 1] = (byte) '*';
//                sout.write(temp, 0, i + 2);
//                sout.flush();
//            }
//        } catch (Exception e) {
//            System.err.println(e);
//            return;
//        }
//    }
}
