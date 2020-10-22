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
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

class OpenBankAccountPanel extends JPanel implements ActionListener {
    private JButton OpenButton;
    private JTextField UsernameField, NameField, AccountNumberField, BalanceField, InterestRateField;
    private JComboBox CheckingOrSavingsBox;
    private String UName, AccountNumber, Balance, Name, AccountType;

    public OpenBankAccountPanel(String UName, String CustomerName) {
        OpenButton = new JButton("Open"); //initializing two button references

        CheckingOrSavingsBox = new JComboBox();
        CheckingOrSavingsBox.addItem("Choose Account Type");
        CheckingOrSavingsBox.addItem("Checking");
        CheckingOrSavingsBox.addItem("Savings");
        CheckingOrSavingsBox.setSelectedItem(1);

        UsernameField = new JTextField(15);
        UsernameField.setText(UName);
        NameField = new JTextField(CustomerName);
        AccountNumberField = new JTextField(15);
        BalanceField = new JTextField(15);
        BalanceField.setText("7777");


        //JLabel TypeLabel = new JLabel("Choose Account Type: ");
        JLabel NameLabel = new JLabel("Customer Name:");
        JLabel UsernameLabel = new JLabel("Username: ");
        JLabel NumberLabel = new JLabel("Account Number:");
        JLabel BalanceLabel = new JLabel("Opening Deposit:");

        JPanel TypePanel = new JPanel();
        JPanel UsernamePanel = new JPanel();
        JPanel NamePanel = new JPanel();
        JPanel NumberPanel = new JPanel();
        JPanel BalancePanel = new JPanel();

        TypePanel.add(CheckingOrSavingsBox);
        UsernamePanel.add(UsernameLabel);
        UsernamePanel.add(UsernameField);
        NamePanel.add(NameLabel);
        NamePanel.add(NameField);
        NumberPanel.add(NumberLabel);
        NumberPanel.add(AccountNumberField);
        BalancePanel.add(BalanceLabel);
        BalancePanel.add(BalanceField);

        OpenButton.addActionListener(this); //event listener registration

        JPanel TopPanel = new JPanel();
        TopPanel.add(TypePanel);
        TopPanel.add(UsernamePanel);

        JPanel BottomPanel = new JPanel();

        JPanel CenterPanel = new JPanel();
        CenterPanel.add(NamePanel);
        CenterPanel.add(NumberPanel);
        CenterPanel.add(BalancePanel);
        BottomPanel.add(OpenButton);
        setLayout(new BorderLayout());
        add(TopPanel, BorderLayout.NORTH);
        add(CenterPanel, BorderLayout.CENTER);
        add(BottomPanel, BorderLayout.SOUTH);//add the one button on to this panel

    }

    public void actionPerformed(ActionEvent evt)  //event handling
    {
        //Object source = evt.getSource(); //get who generates this event
        String arg = evt.getActionCommand();
        Socket client = null;
        InputStream sin = null;
        OutputStream sout = null;

        if (arg.equals("Open")) { //determine which button is clicked
            String header = "Open";
            String host = "localhost";
            int port = 2020;
            UName = UsernameField.getText(); //take actions
            Name = NameField.getText();
            AccountNumber = AccountNumberField.getText();
            Balance = BalanceField.getText();
            AccountType = (String) CheckingOrSavingsBox.getSelectedItem();
            if (AccountType.equals("Choose Account Type"))
                JOptionPane.showMessageDialog(null, "Please Choose an Account Type!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else if (AccountNumber.length() != 8)
                JOptionPane.showMessageDialog(null, "Please Enter an Account Number with Exactly 8 Characters!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else {
//                    OpenBankAccountControl OBAcct_Ctrl = new OpenBankAccountControl(AccountType, AccountNumber, Name, UName, Balance);
                try {
                    client = new Socket(host, port);
                    sin = client.getInputStream();
                    sout = client.getOutputStream();
                } catch (IOException e) {
                    System.out.println("Error in try catch for LoginBO");
                    e.printStackTrace();
                }
                String s;
                byte[] b = new byte[1024];
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Connection Established!");
                try {
                    s = header;
                    System.out.println("Client S: " + s);
                    sout.write(s.getBytes());
                    sout.flush();
                    int j = sin.read(b);
                    s = new String(b, 0, j);
//                    s = new String(b, StandardCharsets.UTF_8);
//                    s = s.replaceAll("\u0000.*", "");
                    if(s.equals("Open Received")){
                        s = AccountType + ";" + AccountNumber + ";" + Name + ";" + UName + ";" + Balance;
                        sout.write(s.getBytes());
                        sout.flush();
                    }

                    int i = sin.read(b);
                    System.out.println("Got message!");
                    System.out.write(b, 0, i);
                    System.out.println();
                    s = new String(b, 0, i);
//                    s = new String(b, StandardCharsets.UTF_8);
//                    s = s.replaceAll("\u0000.*", "");
                    if (s.equals("Open Finished!")){
                        System.out.println("Socket Closing!");
                        client.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            //Acct = new Account(UName, PsWord, PsWord1, Name);
            //if (Acct.signUp())
            //JOptionPane.showMessageDialog(null, "Account has been open!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            //else
            //JOptionPane.showMessageDialog(null, "Account creation failed due to an invalid email address or unmatched passwords or the email address exists.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        }
    }
/*
    public String getUsername() {
		return UName;
	};

	public String getPassword() {
	    return PsWord;
	}

	public String getPassword1() {
	    return PsWord1;
	}*/
}

public class OpenBankAccountBO extends JFrame {
    private OpenBankAccountPanel OBA_Panel;

    public OpenBankAccountBO(String UName, String CustomerName) {
        setTitle("Open a Bank Account");
        setSize(450, 200);

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
        OBA_Panel = new OpenBankAccountPanel(UName, CustomerName);
        contentPane.add(OBA_Panel);
        show();
    }

    public static void main(String[] args) {
        JFrame frame = new OpenBankAccountBO("shahharshil", "Harshil Shah"); //initialize a JFrame object
        frame.show(); //display the frame
    }
}

