package Connection;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket client;
        InputStream sin = null;
        OutputStream sout = null;

//        if (args.length != 2) {
//            System.err.println("Usage: java SocketClient server port");
//            return;
//        }
        String host = "localhost";
        int port = 2020;

        try {
//            client = new Socket(args[0], Integer.parseInt(args[1]));
            client = new Socket(host, port);
            sin = client.getInputStream();
            sout = client.getOutputStream();
        } catch (UnknownHostException e) {
//            System.err.println("can't locate server: " + args[0]);
            System.err.println("can't locate server: " + host);
            return;
        }
        String s;
        byte[] b = new byte[1024];
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

//        System.out.println("connection to " + args[0] + " established");
        System.out.println("connection to " + host + " established");
        do {
            System.out.print("> ");
            System.out.flush();
            s = in.readLine();
            if (s.length() == 0) continue;  // make sure it doesn�t hang
            sout.write(s.getBytes());
            sout.flush();
            int i = sin.read(b);
            System.out.write(b, 0, i);
            System.out.println();
        } while (!s.equals("exit"));
    }
}


