import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InquireTransactionsControl {
    public InquireTransactionsControl(String customerId, String startDate, String endDate){
        Transactions transactions = new Transactions();
        ArrayList<Transactions> receivedTnsxs = new ArrayList<>();

        receivedTnsxs = transactions.getTransactions(customerId, startDate, endDate);

        boolean exists = null != receivedTnsxs.get(0).getCustomerID();

        if(!exists){
            JOptionPane.showMessageDialog(null, "Cannot find Transactions for these dates!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        } else {
            TableBO tableBO = new TableBO(receivedTnsxs);
        }

    }
}


