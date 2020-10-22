import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;

class TransferPanel extends JPanel implements ActionListener {

    private JButton TransferButton;
    private JTextField NameField, BalanceField;
    private String UName, FromAccountNumber, ToAccountNumber, Transfer, Name, AccountType;

    private JComboBox FromDropDownBox;
    private JComboBox ToDropDownBox;

    public TransferPanel(String CustomerName, String chkAccountNumber, String svgsAccountNumber) {
        TransferButton = new JButton("Transfer"); //initializing two button references

        FromDropDownBox = new JComboBox();
        FromDropDownBox.addItem("Choose Account");
        FromDropDownBox.addItem("Checking Account: " + chkAccountNumber);
        FromDropDownBox.addItem("Savings Account: " + svgsAccountNumber);

        ToDropDownBox = new JComboBox();
        ToDropDownBox.addItem("Choose Account");
        ToDropDownBox.addItem("Checking Account: " + chkAccountNumber);
        ToDropDownBox.addItem("Savings Account: " + svgsAccountNumber);

        NameField = new JTextField(15);
        NameField.setText(CustomerName);
        BalanceField = new JTextField(15);
        BalanceField.setText("0.0");

        JLabel NameLabel = new JLabel("Customer Name:");
        JLabel FromNumberLabel = new JLabel("From Account Number:");
        JLabel ToNumberLabel = new JLabel("To Account Number:");
        JLabel BalanceLabel = new JLabel("Transfer:");

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
        Socket client = null;
        InputStream sin = null;
        OutputStream sout = null;

        if (arg.equals("Transfer")) { //determine which button is clicked
            String header = "Transfer";
            String host = "localhost";
            int port = 2020;
            FromAccountNumber = (String) FromDropDownBox.getSelectedItem();
            ToAccountNumber = (String) ToDropDownBox.getSelectedItem();
            Transfer = BalanceField.getText();

            if (FromAccountNumber.equals("Choose Account") || ToAccountNumber.equals("Choose Account"))
                JOptionPane.showMessageDialog(null, "Please Choose an Account!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else if (FromAccountNumber.equals(ToAccountNumber))
                JOptionPane.showMessageDialog(null, "Please choose different Accounts!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else if (Double.parseDouble(Transfer) < 0)
                JOptionPane.showMessageDialog(null, "Please enter a positive value to Withdraw!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else {
                //get checking or savings account
                String fromAccnt = extractCheckingSavings((String) FromDropDownBox.getSelectedItem());
                String toAccnt = extractCheckingSavings((String) ToDropDownBox.getSelectedItem());
                String chkAccntNbr = fromAccnt.equals("Checking") ? extractAccountNumber((String) FromDropDownBox.getSelectedItem()) : extractAccountNumber((String) ToDropDownBox.getSelectedItem());
                String svgAccntNbr = fromAccnt.equals("Savings") ? extractAccountNumber((String) FromDropDownBox.getSelectedItem()) : extractAccountNumber((String) ToDropDownBox.getSelectedItem());
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
                    if (s.equals("Transfer Received")) {
                        s = toAccnt + ";" + fromAccnt + ";" + chkAccntNbr + ";" + svgAccntNbr + ";" + Transfer;
                        sout.write(s.getBytes());
                        sout.flush();
                    }

                    int i = sin.read(b);
                    System.out.println("Got message!");
                    s = new String(b, 0, i);
                    if (s.equals("Transfer Finished!")) {
                        System.out.println("Transfer Socket Closing!");
                        client.close();
                    }
                } catch (IOException k) {
                    System.out.println("Error in try catch for TransferBO");
                    k.printStackTrace();
                }

//                TransferControl transferControl = new TransferControl(fromAccnt, toAccnt, chkAccntNbr, svgAccntNbr, Float.parseFloat(Transfer));
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

public class TransferBO extends JFrame {
    private TransferPanel Transfer_Panel;

    public TransferBO() {
        setTitle("Transfer");
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
        Transfer_Panel = new TransferPanel("Harshil Shah", "22222222", "88888888");
        contentPane.add(Transfer_Panel);
        show();
    }

    public static void main(String[] args) {
        JFrame mainFrame = new TransferBO();
        mainFrame.show();
    }
}