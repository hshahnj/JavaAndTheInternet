import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
//65.19.96.252
public class SocketClient {
    public static void main(String[] args) throws IOException {
        Socket client;
        InputStream sin = null;
        OutputStream sout = null;

        if (args.length != 2) {
            System.err.println("Usage: java SocketClient server port");
            return;
        }
        try {
            client = new Socket(args[0], Integer.parseInt(args[1]));
            sin = client.getInputStream();
            sout = client.getOutputStream();
        } catch (UnknownHostException e) {
            System.err.println("can't locate server: " + args[0]);
            return;
        }
        String s;
        byte[] b = new byte[1024];
        //BufferedReader in = new BufferedReader(new InputStreamReader(sin));
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("connection to " + args[0] + " established");
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
