import javax.swing.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class DepositThread extends Thread {
    Socket echoSocket;
    InputStream sin = null;
    OutputStream sout = null;
    String s;
    String[] inputArgs;
    byte[] b = new byte[1024];

    public DepositThread(Socket S, InputStream is, OutputStream os) {
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

            System.out.println("in run of DepositThread");

            int i = sin.read(b);
//            s = new String(b, StandardCharsets.UTF_8);
//            s = s.replaceAll("\u0000.*", "");
            s = new String(b, 0, i);
            System.out.println("String received:" + s);
            inputArgs = s.split(";");
            if (inputArgs.length != 3) {
                //return error here.
            }
            String toAccountType = inputArgs[0];
            String accountNbr = inputArgs[1];
            float depositAmt = Float.parseFloat(inputArgs[2]);

            System.out.println("Got 3 inputs!");
            if (toAccountType.equals("Checking")) {
                CheckingAccount chkAccnt = new CheckingAccount(accountNbr);
                if(chkAccnt.deposit(depositAmt, "Deposit", "External", accountNbr))
                    JOptionPane.showMessageDialog(null, "Deposit Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, "Deposit Failed!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            } else if (toAccountType.equals("Savings")) {
                SavingsAccount savingsAccount = new SavingsAccount(accountNbr);
                if(savingsAccount.deposit(depositAmt, "Deposit", "External", accountNbr))
                    JOptionPane.showMessageDialog(null, "Deposit Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, "Deposit Failed!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            }

            sout.write("Deposit Finished!".getBytes());
            sout.flush();

        } catch (Exception e) {
            System.err.println(e);
            return;
        }

    }
}
