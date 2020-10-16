import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class MainPanel extends JPanel implements ActionListener {

    private JTabbedPane tabbedPane;
    private JPanel transferPanel, depositPanel, withdrawPanel, inquireTransactionPanel, accountOverviewPanel, openBankAccountPanel;

    public MainPanel(String UName, String CustomerName, String chkAccntNbr, String svgAccntNbr, String chkAccntBal, String svgAccntBal) {

        tabbedPane = new JTabbedPane();
        transferPanel = new TransferPanel(CustomerName, chkAccntNbr, svgAccntNbr);
        depositPanel = new DepositPanel(CustomerName, chkAccntNbr, svgAccntNbr);
        withdrawPanel = new WithdrawPanel(CustomerName, chkAccntNbr, svgAccntNbr);
        inquireTransactionPanel = new InquireTransactionsPanel(UName);
        accountOverviewPanel = new AccountOverviewPanel(UName, CustomerName, chkAccntBal, svgAccntBal);
        openBankAccountPanel = new OpenBankAccountPanel(UName, CustomerName);


        tabbedPane.addTab("Account Overview", accountOverviewPanel);
        tabbedPane.addTab("Open Bank Account", openBankAccountPanel);
        tabbedPane.addTab("Deposit", depositPanel);
        tabbedPane.addTab("Withdraw", withdrawPanel);
        tabbedPane.addTab("Transfer", transferPanel);
        tabbedPane.addTab("Inquire Transaction", inquireTransactionPanel);
        add(tabbedPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}

public class MainBO extends JFrame{
    private MainPanel Main_Panel;

    public MainBO(String UName, String CustomerName, String chkAccntNbr, String svgAccntNbr, String chkAccntBal, String svgAccntBal){
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
        Main_Panel = new MainPanel(UName, CustomerName, chkAccntNbr, svgAccntNbr, chkAccntBal, svgAccntBal);
        contentPane.add(Main_Panel);
        show();
    }

    public static void main(String[] args) {
//        JFrame frame = new MainBO("Harshil Shah"); //initialize a JFrame object
//        frame.show(); //display the frame
    }
}
