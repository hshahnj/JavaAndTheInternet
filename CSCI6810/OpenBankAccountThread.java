import javax.swing.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class OpenBankAccountThread extends Thread {
    Socket echoSocket;
    InputStream sin = null;
    OutputStream sout = null;
    String s;
    String[] inputArgs;
    byte[] b = new byte[1024];

    public OpenBankAccountThread(Socket S, InputStream is, OutputStream os) {
        try {
            echoSocket = S;
            sin = is;
            sout = os;
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void run() {
        try {

            System.out.println("in run of OpenBankAccountThread");

            int i = sin.read(b);
//            s = new String(b, StandardCharsets.UTF_8);
//            s = s.replaceAll("\u0000.*", "");
            s = new String(b, 0, i);
            System.out.println("String received:" + s);
            inputArgs = s.split(";");
            if (inputArgs.length != 5) {
                //return error here.
            }
            String AccountType = inputArgs[0];
            String AccountNumber = inputArgs[1];
            String Name = inputArgs[2];
            String UName = inputArgs[3];
            String Balance = inputArgs[4];

//            String AccountType, String AccountNumber, String Name, String UName, String Balance
            System.out.println("Got 5 inputs!");
            if (AccountType.equals("Checking")) {
                CheckingAccount CA = new CheckingAccount(AccountNumber, Name, UName, Balance);
                if (CA.openAcct()) {
                    //System.out.println("successful!");
                    JOptionPane.showMessageDialog(null, "Opening a Checking Account is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                } else
                    //System.out.println("fail!");
                    JOptionPane.showMessageDialog(null, "Opening a Checking Account failed.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            }

            if (AccountType.equals("Savings")) {
                SavingsAccount SA = new SavingsAccount(AccountNumber, Name, UName, Balance);
                if (SA.openAcct()) {
                    //System.out.println("successful!");
                    JOptionPane.showMessageDialog(null, "Opening a Savings Account is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                } else
                    //System.out.println("fail!");
                    JOptionPane.showMessageDialog(null, "Opening a Savings Account failed.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            }

            sout.write("Open Finished!".getBytes());
            sout.flush();

        } catch (Exception e) {
            System.err.println(e);
            return;
        }

    }
}
