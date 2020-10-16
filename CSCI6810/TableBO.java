import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

class TablePanel extends JPanel {
    private final JTable table;

    public TablePanel(ArrayList<Transactions> transactionsArrayList) {
//        Object[] columnNames = {"Swing", "Awt"}; //prepare two arrays for a table
        Object[] columnNames = {"Transaction Number", "Transaction Amount", "Transaction Type", "Transaction Time", "Transaction Date", "From Account", "To Account", "CustomerID"};
        Object[][] transactionComponents = new Object[transactionsArrayList.size()][];
        for(int i = 0; i < transactionsArrayList.size(); i++){
            Object[] temp = {transactionsArrayList.get(i).getTransactionNumber(), transactionsArrayList.get(i).getTransactionAmount(), transactionsArrayList.get(i).getTransactionType(),
            transactionsArrayList.get(i).getTransactionTime(), transactionsArrayList.get(i).getTransactionDate(), transactionsArrayList.get(i).getFromAccount(), transactionsArrayList.get(i).getToAccount(), transactionsArrayList.get(i).getCustomerID()};
            transactionComponents[i] = temp;
        }

        table = new JTable(transactionComponents, columnNames); //initialize a Table object
        table.setPreferredScrollableViewportSize(new Dimension(1200, 250));

        //Create the scroll pane and add it to the table.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this window.
        add(scrollPane);
        JOptionPane.showMessageDialog(table, scrollPane);
    }
}

public class TableBO extends JFrame{
    private TablePanel tablePanel;


    public TableBO(ArrayList<Transactions> transactionsArrayList) {
        setTitle("Main Window");
        setSize(1200, 250);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        int screenHeight = d.height;
        int screenWidth = d.width;
        setLocation(screenWidth / 3, screenHeight / 4);

        addWindowListener(new WindowAdapter()  //handle window event
        {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        Container contentPane = getContentPane(); //add a panel to a frame
        tablePanel = new TablePanel(transactionsArrayList);
    }

    public static void main(String[] args) {
        ArrayList<Transactions> transactionsList = new ArrayList<>();
        Transactions transaction = new Transactions("a", "2.0", "c", "d", "e", "f", "g", "h");
        Transactions transaction2 = new Transactions("aa", "3.0", "cc", "dd", "ee", "ff", "gg", "hh");
        Transactions transaction3 = new Transactions("aaa", "4.0", "ccc", "ddd", "eee", "fff", "ggg", "hhh");

        transactionsList.add(transaction);
        transactionsList.add(transaction2);
        transactionsList.add(transaction3);

        JFrame frame = new TableBO(transactionsList); //initialize a JFrame object
        frame.show(); //display the frame
    }
}

