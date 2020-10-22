import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

class DepositPanel extends JPanel implements ActionListener {

    private JButton TransferButton;
    private JTextField NameField, DepositField;
    private JComboBox FromDropDownBox;
    private JComboBox ToDropDownBox;

    private String UName, FromAccountNumber, ToAccountNumber, Deposit, Name, AccountType;

    public DepositPanel(String CustomerID, String chkAccountNumber, String svgsAccountNumber) {
        TransferButton = new JButton("Deposit"); //initializing two button references

        FromDropDownBox = new JComboBox();
        FromDropDownBox.addItem("Choose Account");
        FromDropDownBox.addItem("Checking Account: " + chkAccountNumber);
        FromDropDownBox.addItem("Savings Account: " + svgsAccountNumber);
        FromDropDownBox.addItem("External");

        ToDropDownBox = new JComboBox();
        ToDropDownBox.addItem("Choose Account");
        ToDropDownBox.addItem("Checking Account: " + chkAccountNumber);
        ToDropDownBox.addItem("Savings Account: " + svgsAccountNumber);
        ToDropDownBox.addItem("External");

        NameField = new JTextField(15);
        NameField.setText(CustomerID);
        DepositField = new JTextField(15);
        DepositField.setText("0.0");

        JLabel NameLabel = new JLabel("Customer Name:");
        JLabel FromNumberLabel = new JLabel("From Account Number:");
        JLabel ToNumberLabel = new JLabel("To Account Number:");
        JLabel BalanceLabel = new JLabel("Deposit Amount:");

        JPanel NamePanel = new JPanel();
        JPanel FromNumberPanel = new JPanel();
        JPanel ToNumberPanel = new JPanel();
        JPanel DepositAmountPanel = new JPanel();

        NamePanel.add(NameLabel);
        NamePanel.add(NameField);
        FromNumberPanel.add(FromNumberLabel);
        FromNumberPanel.add(FromDropDownBox);
        ToNumberPanel.add(ToNumberLabel);
        ToNumberPanel.add(ToDropDownBox);
        DepositAmountPanel.add(BalanceLabel);
        DepositAmountPanel.add(DepositField);

        TransferButton.addActionListener(this); //event listener registration

        JPanel TopPanel = new JPanel();
        TopPanel.add(NamePanel);

        JPanel CenterPanel = new JPanel();
        CenterPanel.add(FromNumberPanel);
        CenterPanel.add(ToNumberPanel);
        CenterPanel.add(DepositAmountPanel);

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
        Socket client = null;
        InputStream sin = null;
        OutputStream sout = null;

        if (arg.equals("Deposit")) { //determine which button is clicked
            String header = "Deposit";
            String host = "localhost";
            int port = 2020;
            FromAccountNumber = (String) FromDropDownBox.getSelectedItem();
            ToAccountNumber = (String) ToDropDownBox.getSelectedItem();
            Deposit = DepositField.getText();

            if (FromAccountNumber.equals("Choose Account") || ToAccountNumber.equals("Choose Account"))
                JOptionPane.showMessageDialog(null, "Please Choose an Account!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else if (FromAccountNumber.equals(ToAccountNumber))
                JOptionPane.showMessageDialog(null, "Please choose different Accounts!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else if (Double.parseDouble(Deposit) < 0)
                JOptionPane.showMessageDialog(null, "Please enter a positive value to Deposit!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else if (!FromAccountNumber.equals("External"))
                JOptionPane.showMessageDialog(null, "Please enter External Account for From Account!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else {

                //get checking or savings account
                String toAccountType = extractCheckingSavings((String) ToDropDownBox.getSelectedItem());
                String accountNbr = extractAccountNumber((String) ToDropDownBox.getSelectedItem());
                String deposit = Deposit;
                String s;
                byte[] b = new byte[1024];
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

                try {
                    client = new Socket(host, port);
                    sin = client.getInputStream();
                    sout = client.getOutputStream();
                    System.out.println("Connection Established!");
                    s = header;
                    System.out.println("Client S: " + s);
                    sout.write(s.getBytes());
                    sout.flush();
                    int j = sin.read(b);
                    s = new String(b, 0, j);
                    if(s.equals("Deposit Received")){
                        s = toAccountType + ";" + accountNbr + ";" + deposit;
                        sout.write(s.getBytes());
                        sout.flush();
                    }

                    int i = sin.read(b);
                    System.out.println("Got message!");
                    s = new String(b, 0, i);
                    if (s.equals("Deposit Finished!")){
                        System.out.println("Deposit Socket Closing!");
                        client.close();
                    }
                } catch (IOException k) {
                    System.out.println("Error in try catch for DepositBO");
                    k.printStackTrace();
                }

//                DepositControl depControl = new DepositControl(extractCheckingSavings((String) ToDropDownBox.getSelectedItem()), extractAccountNumber((String) ToDropDownBox.getSelectedItem()), Float.parseFloat(Deposit));
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
//For Debugging

public class DepositBO extends JFrame {
    private DepositPanel depositPanel;

    public DepositBO() {
        setTitle("Deposit");
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
        depositPanel = new DepositPanel("shahharshil77", "12345678", "12345679");
        contentPane.add(depositPanel);
        show();
    }

    public static void main(String[] args) {
        JFrame mainFrame = new DepositBO();
        mainFrame.show();
    }
}

