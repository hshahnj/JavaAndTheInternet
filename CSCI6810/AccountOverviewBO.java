/******************************************************************************
 *	Program Author: Dr. Yongming Tang for CSCI 6810 Java and the Internet	  *
 *	Date: February, 2014													  *
 *******************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class AccountOverviewPanel extends JPanel implements ActionListener {

    private JTextField UsernameField, NameField, CheckingAccountBalanceField, SavingsAccountBalanceField;


    private String UName, AccountNumber, Balance, Name, AccountType;

    public AccountOverviewPanel(String UName, String CustomerName, String chkAccountBalance, String svgsAccountBalance) {

        UsernameField = new JTextField(15);
        UsernameField.setText(UName);
        NameField = new JTextField(15);
        NameField.setText(CustomerName);

        CheckingAccountBalanceField = new JTextField(15);
        CheckingAccountBalanceField.setText(chkAccountBalance);
        SavingsAccountBalanceField = new JTextField(15);
        SavingsAccountBalanceField.setText(svgsAccountBalance);


        //JLabel TypeLabel = new JLabel("Choose Account Type: ");
        JLabel NameLabel = new JLabel("Customer Name:");
        JLabel UsernameLabel = new JLabel("Username: ");

        JLabel ChkAccntBalanceLabel = new JLabel("Checking Account Balance:");
        JLabel SvgsAccntBalanceLabel = new JLabel("Savings Account Balance: ");

        JPanel UsernamePanel = new JPanel();
        JPanel NamePanel = new JPanel();

        JPanel ChkAccntBalancePanel = new JPanel();
        JPanel SvgsAccntBalancePanel = new JPanel();

        UsernamePanel.add(UsernameLabel);
        UsernamePanel.add(UsernameField);
        NamePanel.add(NameLabel);
        NamePanel.add(NameField);

        ChkAccntBalancePanel.add(ChkAccntBalanceLabel);
        ChkAccntBalancePanel.add(CheckingAccountBalanceField);
        SvgsAccntBalancePanel.add(SvgsAccntBalanceLabel);
        SvgsAccntBalancePanel.add(SavingsAccountBalanceField);
        JPanel TopPanel = new JPanel();
        JPanel CenterPanel = new JPanel();
        TopPanel.add(NamePanel);
        TopPanel.add(UsernamePanel);
        CenterPanel.add(ChkAccntBalancePanel);
        CenterPanel.add(SvgsAccntBalancePanel);

        setLayout(new BorderLayout());
        add(TopPanel, BorderLayout.NORTH);
        add(CenterPanel, BorderLayout.CENTER);
        //add(OpenButton, BorderLayout.SOUTH);//add the one button on to this panel
    }

    public void actionPerformed(ActionEvent evt) {
    }
}

public class AccountOverviewBO extends JFrame {
    private AccountOverviewPanel AOP_Panel;

    public AccountOverviewBO(String UName, String CustomerName, String chkAccountBalance, String svgsAccountBalance) {
        setTitle("Account Overview");
        setSize(400, 300);

        //get screen size and set the location of the frame
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
        AOP_Panel = new AccountOverviewPanel(UName, CustomerName, chkAccountBalance, svgsAccountBalance);
        contentPane.add(AOP_Panel);
        show();
    }

    public static void main(String[] args) {
        JFrame frame = new AccountOverviewBO("shahharshil", "Harshil Shah", "20", "50"); //initialize a JFrame object
        frame.show(); //display the frame
    }
}

