import javax.swing.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class InquireTransactionsThread extends Thread {

    Socket echoSocket;
    InputStream sin = null;
    OutputStream sout = null;
    String s;
    String[] inputArgs;
    byte[] b = new byte[1024];

    public InquireTransactionsThread(Socket S, InputStream is, OutputStream os) {
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

            System.out.println("in run of InquireTransactionThread");

            int i = sin.read(b);
            s = new String(b, 0, i);
            System.out.println("String received:" + s);
            inputArgs = s.split(";");
            if (inputArgs.length != 3) {
                //return error here.
            }
            String customerId = inputArgs[0];
            String startDate = inputArgs[1];
            String endDate = inputArgs[2];

            System.out.println("Got 3 inputs!");
            Transactions transactions = new Transactions();
            ArrayList<Transactions> receivedTnsxs = new ArrayList<>();
            receivedTnsxs = transactions.getTransactions(customerId, startDate, endDate);
            boolean exists = null != receivedTnsxs.get(0).getCustomerID();



            if(!exists){
                JOptionPane.showMessageDialog(null, "Cannot find Transactions for these dates!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            } else {
                TableBO tableBO = new TableBO(receivedTnsxs);
            }
            sout.write("Transactions Finished!".getBytes());
            sout.flush();

        } catch (Exception e) {
            System.err.println(e);
            return;
        }

    }
}
