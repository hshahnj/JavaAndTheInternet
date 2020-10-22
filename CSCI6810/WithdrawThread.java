import javax.swing.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class WithdrawThread extends Thread {
    Socket echoSocket;
    InputStream sin = null;
    OutputStream sout = null;
    String s;
    String[] inputArgs;
    byte[] b = new byte[1024];

    public WithdrawThread(Socket S, InputStream is, OutputStream os) {
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

            System.out.println("in run of WithdrawThread");

            int i = sin.read(b);
            s = new String(b, 0, i);
            System.out.println("String received:" + s);
            inputArgs = s.split(";");
            if (inputArgs.length != 3) {
                //return error here.
            }
            String toAccountType = inputArgs[0];
            String accountNbr = inputArgs[1];
            float withdrawAmt = Float.parseFloat(inputArgs[2]);

            System.out.println("Got 3 inputs!");
            int closingScenario = 1;
            if (toAccountType.equals("Checking")) {
                CheckingAccount chkAccnt = new CheckingAccount(accountNbr);
                //closing scenario
                // 1 = false
                // 0 = true
                // 2 = oldBalance < removeBalance
                closingScenario = chkAccnt.withdraw(withdrawAmt, "Withdraw", accountNbr, "External");
                if (closingScenario == 0)
                    JOptionPane.showMessageDialog(null, "Withdrawal Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                else if (closingScenario == 2)
                    JOptionPane.showMessageDialog(null, "Withdrawal Failed! You don't have enough balance!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                else if (closingScenario == 1)
                    JOptionPane.showMessageDialog(null, "Withdrawal Failed!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            } else if (toAccountType.equals("Savings")) {
                SavingsAccount savingsAccount = new SavingsAccount(accountNbr);
                closingScenario = savingsAccount.withdraw(withdrawAmt, "Withdraw", accountNbr, "External");
                if (closingScenario == 0)
                    JOptionPane.showMessageDialog(null, "Withdrawal Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                else if (closingScenario == 2)
                    JOptionPane.showMessageDialog(null, "Withdrawal Failed! You don't have enough balance!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                else if (closingScenario == 1)
                    JOptionPane.showMessageDialog(null, "Withdrawal Failed!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

            }

            sout.write("Withdraw Finished!".getBytes());
            sout.flush();

        } catch (Exception e) {
            System.err.println(e);
            return;
        }

    }
}

