import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;

class InquireTransactionsPanel extends JPanel implements ActionListener {

    private JButton TransactionButton;
    private JTextField NameField, StartDate, EndDate;
    private JComboBox CheckingOrSavingsBox;
    private String Start_Date_Text, End_Date_Text;
    private String CustomerID_Text;

    public InquireTransactionsPanel(String CustomerName) {
        TransactionButton = new JButton("Get Transaction"); //initializing two button references

//        CheckingOrSavingsBox = new JComboBox();
//        CheckingOrSavingsBox.addItem("Choose Account Type");
//        CheckingOrSavingsBox.addItem("Checking");
//        CheckingOrSavingsBox.addItem("Savings");
//        CheckingOrSavingsBox.addItem("Deposit");

        NameField = new JTextField(15);
        NameField.setText(CustomerName);
        StartDate = new JTextField(15);
        StartDate.setText("");
        EndDate = new JTextField(15);
        EndDate.setText("");

        JLabel NameLabel = new JLabel("Customer Name: ");
        JLabel StartLabel = new JLabel("Start Date: ");
        JLabel EndLabel = new JLabel("End Date: ");

        JPanel NamePanel = new JPanel();
        JPanel StartPanel = new JPanel();
        JPanel EndPanel = new JPanel();

        NamePanel.add(NameLabel);
        NamePanel.add(NameField);
        StartPanel.add(StartLabel);
        StartPanel.add(StartDate);
        EndPanel.add(EndLabel);
        EndPanel.add(EndDate);

        TransactionButton.addActionListener(this); //event listener registration

        JPanel TopPanel = new JPanel();
        TopPanel.add(NamePanel);

        JPanel CenterPanel = new JPanel();
        CenterPanel.add(StartPanel);
        CenterPanel.add(EndPanel);

        JPanel BottomPanel = new JPanel();
        BottomPanel.add(TransactionButton);
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

        if (arg.equals("Get Transaction")) {
            String header = "Transactions";
            String host = "localhost";
            int port = 2020;

            Start_Date_Text = StartDate.getText(); //take actions
            End_Date_Text = EndDate.getText();
            CustomerID_Text = NameField.getText();
            if (Start_Date_Text.length() != 10 || End_Date_Text.length() != 10) {
                JOptionPane.showMessageDialog(null, "Please enter 10-digit Date!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            } else {

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
                    if(s.equals("Transactions Received")){
                        s = CustomerID_Text + ";" + Start_Date_Text + ";" + End_Date_Text;
                        sout.write(s.getBytes());
                        sout.flush();
                    }

                    int i = sin.read(b);
                    System.out.println("Got message!");
                    s = new String(b, 0, i);
                    if (s.equals("Transactions Finished!")){
                        System.out.println("Transactions Socket Closing!");
                        client.close();
                    }
                } catch (IOException k) {
                    System.out.println("Error in try catch for TransactionsBO");
                    k.printStackTrace();
                }

//                InquireTransactionsControl control = new InquireTransactionsControl(CustomerID_Text, Start_Date_Text, End_Date_Text);
            }
        }
    }
}

public class InquireTransactionsBO extends JFrame {
    private InquireTransactionsPanel IT_Panel;

    public InquireTransactionsBO(String customerId) {
        setTitle("InquireTransactions");
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
        IT_Panel = new InquireTransactionsPanel(customerId);
        contentPane.add(IT_Panel);
        show();
    }

    public static void main(String[] args) {
        JFrame mainFrame = new InquireTransactionsBO("shahharshil");
        mainFrame.show();
    }
}