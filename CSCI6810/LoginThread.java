import javax.swing.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class LoginThread extends Thread {
    Socket echoSocket;
    InputStream sin = null;
    OutputStream sout = null;
    String s;
    String[] inputArgs;
    byte[] b = new byte[1024];

    public LoginThread(Socket S, InputStream is, OutputStream os) {
        try {
            echoSocket = S;
            sin = is;
            sout = os;
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    public void run(){
        try {

            System.out.println("in run of LoginThread");

            int i = sin.read(b);
//            s = new String(b, StandardCharsets.UTF_8);
//            s = s.replaceAll("\u0000.*", "");
            s = new String(b, 0, i);
            System.out.println("String received:" + s);
            inputArgs = s.split(";");
            if (inputArgs.length != 2) {
                //return error here.
            }
            String Username = inputArgs[0];
            String Password = inputArgs[1];
            System.out.println("Got 2 inputs!");
            Account Accnt = new Account(Username, Password);
            String CustomerName = Accnt.signIn();

            if (!CustomerName.equals("")) {
                System.out.println("Signed In!");
                JOptionPane.showMessageDialog(null, "Login is successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                sout.write(CustomerName.getBytes());
                sout.flush();

            }
        } catch (Exception e) {
            System.err.println(e);
            return;
        }
    }
}
