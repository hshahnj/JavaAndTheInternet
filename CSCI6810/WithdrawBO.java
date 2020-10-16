import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class WithdrawPanel extends JPanel implements ActionListener {

    private JButton TransferButton;
    private JTextField NameField, FromAccountNumberField, ToAccountNumberField, BalanceField;
    private JComboBox FromDropDownBox;
    private JComboBox ToDropDownBox;

    private String UName, FromAccountNumber, ToAccountNumber, Withdraw, Name, AccountType;

    public WithdrawPanel(String CustomerName, String chkAccntNbr, String svgAccntNbr) {
        TransferButton = new JButton("Withdraw"); //initializing two button references

        FromDropDownBox = new JComboBox();
        FromDropDownBox.addItem("Choose Account");
        FromDropDownBox.addItem("Checking Account: " + chkAccntNbr);
        FromDropDownBox.addItem("Savings Account: " + svgAccntNbr);
        FromDropDownBox.addItem("External");

        ToDropDownBox = new JComboBox();
        ToDropDownBox.addItem("Choose Account");
        ToDropDownBox.addItem("Checking Account: " + chkAccntNbr);
        ToDropDownBox.addItem("Savings Account: " + svgAccntNbr);
        ToDropDownBox.addItem("External");


        NameField = new JTextField(15);
        NameField.setText(CustomerName);
        BalanceField = new JTextField(15);
        BalanceField.setText("0.0");

        JLabel NameLabel = new JLabel("Customer Name:");
        JLabel FromNumberLabel = new JLabel("From Account Number:");
        JLabel ToNumberLabel = new JLabel("To Account Number:");
        JLabel BalanceLabel = new JLabel("Withdraw Amount:");

//        JPanel TypePanel = new JPanel();
//        JPanel UsernamePanel = new JPanel();
        JPanel NamePanel = new JPanel();
        JPanel FromNumberPanel = new JPanel();
        JPanel ToNumberPanel = new JPanel();
        JPanel BalancePanel = new JPanel();

//        TypePanel.add(CheckingOrSavingsBox);
        NamePanel.add(NameLabel);
        NamePanel.add(NameField);
        FromNumberPanel.add(FromNumberLabel);
        FromNumberPanel.add(FromDropDownBox);
        ToNumberPanel.add(ToNumberLabel);
        ToNumberPanel.add(ToDropDownBox);
        BalancePanel.add(BalanceLabel);
        BalancePanel.add(BalanceField);

        TransferButton.addActionListener(this); //event listener registration

        JPanel TopPanel = new JPanel();
//        TopPanel.add(TypePanel);
//        TopPanel.add(UsernamePanel);
        TopPanel.add(NamePanel);

        JPanel CenterPanel = new JPanel();
//        CenterPanel.add(NamePanel);
        CenterPanel.add(FromNumberPanel);
        CenterPanel.add(ToNumberPanel);
        CenterPanel.add(BalancePanel);

        JPanel BottomPanel = new JPanel();
        BottomPanel.add(TransferButton);
        setLayout(new BorderLayout());
        add(TopPanel, BorderLayout.NORTH);
        add(CenterPanel, BorderLayout.CENTER);
        add(BottomPanel, BorderLayout.SOUTH);//add the one button on to this panel
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Object source = evt.getSource(); //get who generates this event
        String arg = e.getActionCommand();
        if (arg.equals("Withdraw")) { //determine which button is clicked
//            UName = UsernameField.getText(); //take actions
//            Name = NameField.getText();
            FromAccountNumber = (String) FromDropDownBox.getSelectedItem();
            ToAccountNumber = (String) ToDropDownBox.getSelectedItem();
            Withdraw = BalanceField.getText();

            if (FromAccountNumber.equals("Choose Account") || ToAccountNumber.equals("Choose Account"))
                JOptionPane.showMessageDialog(null, "Please Choose an Account!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else if (FromAccountNumber.equals(ToAccountNumber))
                JOptionPane.showMessageDialog(null, "Please choose different Accounts!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else if (Double.parseDouble(Withdraw) < 0)
                JOptionPane.showMessageDialog(null, "Please enter a positive value to Withdraw!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else if (!ToAccountNumber.equals("External"))
                JOptionPane.showMessageDialog(null, "Please enter External Account for To Account!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else {
                //get checking or savings account
                WithdrawControl withControl = new WithdrawControl(extractCheckingSavings((String) FromDropDownBox.getSelectedItem()), extractAccountNumber((String) FromDropDownBox.getSelectedItem()), Float.parseFloat(Withdraw));
            }
        }

    }

    private String extractAccountNumber(String fullText) {
        return fullText.length() > 8 ? fullText.substring(fullText.length() - 8) : "External";
    }

    private String extractCheckingSavings(String fullText) {
        if (fullText.length() > 8) {
            String arr[] = fullText.split(" ");
            return arr[0];
        } else {
            return "External";
        }
    }
}

public class WithdrawBO extends JFrame {
    private WithdrawPanel withdrawPanel;

    public WithdrawBO() {
        setTitle("Withdraw");
        setSize(450, 250);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension dim = tk.getScreenSize();
        int screenHeight = dim.height;
        int screenWidth = dim.width;
        setLocation(screenWidth / 3, screenHeight / 4);

        addWindowListener(new WindowAdapter()  //handle window event
        {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        Container contentPane = getContentPane();
//        withdrawPanel = new WithdrawPanel("Harshil Shah");
//        contentPane.add(withdrawPanel);
        show();
    }

    public static void main(String[] args) {
        JFrame mainFrame = new WithdrawBO();
        mainFrame.show();
    }
}

