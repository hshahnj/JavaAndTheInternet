import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class LoginBO extends JFrame implements ActionListener // Implementing ActionListener is for event handling.
{
    private JButton SignUpButton, LoginButton;  //Instance variables
    private JTextField UsernameField;
    private JPasswordField PasswordField;
    Container contentPane = getContentPane();
    JPanel LoginPanel = new JPanel();


    public LoginBO() {
        setTitle("Login");
        setSize(300, 200);

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

        SignUpButton = new JButton("Sign Up"); //initializing two button references
        LoginButton = new JButton("Login");

        UsernameField = new JTextField(15);
        PasswordField = new JPasswordField(15);
        PasswordField.setActionCommand("Login");
        UsernameField.setText("shahharshil77");
        PasswordField.setText("temp1234");

        JLabel FirstTimeUserLabel = new JLabel("First time user? Click Sign Up to register!");
        JLabel UsernameLabel = new JLabel("Username: ");
        JLabel PasswordLabel = new JLabel("Password: ");

        JPanel UsernamePanel = new JPanel();
        JPanel PasswordPanel = new JPanel();

        UsernamePanel.add(UsernameLabel);
        UsernamePanel.add(UsernameField);
        PasswordPanel.add(PasswordLabel);
        PasswordPanel.add(PasswordField);

        LoginPanel.add(UsernamePanel);
        LoginPanel.add(PasswordPanel);

        LoginPanel.add(LoginButton);  //add the two buttons on to this panel
        LoginPanel.add(FirstTimeUserLabel);
        LoginPanel.add(SignUpButton);

        SignUpButton.addActionListener(this);  //event listener registration
        LoginButton.addActionListener(this);
        PasswordField.addActionListener(this);

         //add a panel to a frame
        contentPane.add(LoginPanel);

    }

    public void actionPerformed(ActionEvent evt)  //event handling
    {
        //Object source = evt.getSource(); //get who generates this event
        String arg = evt.getActionCommand();
        Socket client = null;
        InputStream sin = null;
        OutputStream sout = null;

        if (arg.equals("Sign Up")) { //determine which button is clicked
            //System.out.println("Name: "+arg);
            SignUpControl SUC = new SignUpControl(); //take actions
        }

        if (arg.equals("Login")) {
            //System.out.println("Name: "+arg);
            String header = "Login";
            String host = "localhost";
            int port = 2020;
            String Username = UsernameField.getText();
            String Password = PasswordField.getText();
//            LoginControl LoginC = new LoginControl(Username, Password);
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
                sout.write(s.getBytes());
                sout.flush();
                int j = sin.read(b);
//                s = new String(b, StandardCharsets.UTF_8);
//                s = s.replaceAll("\u0000.*", "");
                s = new String(b, 0, j);
                if(s.equals("Login Received")){
                    s = Username + ";" + Password;
                    sout.write(s.getBytes());
                    sout.flush();
                }

                int i = sin.read(b);
                s = new String(b, 0, i);
//                s = s.replaceAll("\u0000.*", "");
                if (s.equals("Harshil Shah")){
                    System.out.println("Login Socket Closing!");
                    client.close();
                    JComponent comp = (JComponent) evt.getSource();
                    Window win = SwingUtilities.getWindowAncestor(comp);
                    win.dispose();
                }
                String CustomerName = s;
                CheckingAccount chkAccount = new CheckingAccount();
                String chkAccountNbr = chkAccount.getCheckingAccountNumber(Username);
                float chkAccountBal = chkAccount.getBalance(chkAccountNbr);

                SavingsAccount svgAccount = new SavingsAccount();
                String svgAccountNbr = svgAccount.getSavingsAccountNumber(Username);
                float svgAccountBal = svgAccount.getBalance(svgAccountNbr);

                String chkAccountBalString = String.valueOf(chkAccountBal);
                String svgAccountBalString = String.valueOf(svgAccountBal);

                MainBO mainBO = new MainBO(Username, CustomerName, chkAccountNbr, svgAccountNbr, chkAccountBalString, svgAccountBalString);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        JFrame frame = new LoginBO(); //initialize a JFrame object
        frame.show(); //display the frame
    }
}

