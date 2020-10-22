import javax.swing.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TransferThread extends Thread {

    Socket echoSocket;
    InputStream sin = null;
    OutputStream sout = null;
    String s;
    String[] inputArgs;
    byte[] b = new byte[1024];

    public TransferThread(Socket S, InputStream is, OutputStream os) {
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

            System.out.println("in run of TransferThread");

            int i = sin.read(b);
            s = new String(b, 0, i);
            System.out.println("String received:" + s);
            inputArgs = s.split(";");
            if (inputArgs.length != 5) {
                //return error here.
            }
            String fromAccount = inputArgs[0];
            String toAccount = inputArgs[1];
            String chkAccntNbr = inputArgs[2];
            String svgAccntNbr = inputArgs[3];
            float transfer = Float.parseFloat(inputArgs[4]);

//            TransferControl transferControl = new TransferControl(fromAccnt, toAccnt, chkAccntNbr, svgAccntNbr, Float.parseFloat(Transfer));
            System.out.println("Got 5 inputs!");
            CheckingAccount chkAccnt = new CheckingAccount(chkAccntNbr);
            SavingsAccount svgAccnt = new SavingsAccount(svgAccntNbr);
            boolean depositCheck = false;
            int withdrawalCheck = 1;

            //closing scenario
            // 1 = false
            // 0 = true
            // 2 = oldBalance < removeBalance

            if (toAccount.equals("Checking") && fromAccount.equals("Savings")) {

                withdrawalCheck = svgAccnt.withdraw(transfer, "Withdraw", svgAccntNbr, chkAccntNbr);
                if (withdrawalCheck == 0) {
                    depositCheck = chkAccnt.deposit(transfer, "Deposit", svgAccntNbr, chkAccntNbr);
                    if (depositCheck)
                        JOptionPane.showMessageDialog(null, "Transfer Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(null, "Transfer Failed!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

                } else if (withdrawalCheck == 2)

                    JOptionPane.showMessageDialog(null, "Transfer Failed! Not enough balance!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                else if (withdrawalCheck == 1)
                    JOptionPane.showMessageDialog(null, "Transfer Failed! Issue on Withdrawal Side!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

            } else if (toAccount.equals("Savings") && fromAccount.equals("Checking")) {


                withdrawalCheck = chkAccnt.withdraw(transfer, "Withdraw", svgAccntNbr, chkAccntNbr);

                if (withdrawalCheck == 0) {
                    depositCheck = svgAccnt.deposit(transfer, "Deposit", svgAccntNbr, chkAccntNbr);

                    if (depositCheck)
                        JOptionPane.showMessageDialog(null, "Transfer Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

                    else
                        JOptionPane.showMessageDialog(null, "Transfer Failed!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

                } else if (withdrawalCheck == 2)

                    JOptionPane.showMessageDialog(null, "Transfer Failed! Not enough balance!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

                else if (withdrawalCheck == 1)

                    JOptionPane.showMessageDialog(null, "Transfer Failed! Issue on Withdrawal Side!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);


            }

            sout.write("Transfer Finished!".getBytes());
            sout.flush();

        } catch (Exception e) {
            System.err.println(e);
            return;
        }

    }
}
