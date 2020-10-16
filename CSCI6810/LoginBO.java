import java.awt.*;     //including Java packages used by this program
import java.awt.event.*;
import javax.swing.*;

public class LoginBO extends JFrame implements ActionListener // Implementing ActionListener is for event handling.
{
    private JButton SignUpButton, LoginButton;  //Instance variables
    private JTextField UsernameField;
    private JPasswordField PasswordField;


    public LoginBO()
    {
        setTitle("Login");
        setSize(300, 200);

         //get screen size and set the location of the frame
         Toolkit tk = Toolkit.getDefaultToolkit();
         Dimension d = tk.getScreenSize();
         int screenHeight = d.height;
         int screenWidth = d.width;
         setLocation( screenWidth / 3, screenHeight / 4);

         addWindowListener (new WindowAdapter()  //handle window event
            {
		       public void windowClosing (WindowEvent e)
			                  { System.exit(0);
               }
            });

         SignUpButton = new JButton("Sign Up"); //initializing two button references
         LoginButton = new JButton("Login");

         UsernameField = new JTextField(15);
         PasswordField = new JPasswordField(15);
         PasswordField.setActionCommand("Login");

         JLabel FirstTimeUserLabel = new JLabel("First time user? Click Sign Up to register!");
         JLabel UsernameLabel = new JLabel("Username: ");
         JLabel PasswordLabel = new JLabel("Password: ");

         JPanel UsernamePanel = new JPanel();
         JPanel PasswordPanel = new JPanel();

         UsernamePanel.add(UsernameLabel);
         UsernamePanel.add(UsernameField);
         PasswordPanel.add(PasswordLabel);
         PasswordPanel.add(PasswordField);

         JPanel LoginPanel = new JPanel();
         LoginPanel.add(UsernamePanel);
         LoginPanel.add(PasswordPanel);

         LoginPanel.add(LoginButton);  //add the two buttons on to this panel
         LoginPanel.add(FirstTimeUserLabel);
         LoginPanel.add(SignUpButton);

         SignUpButton.addActionListener(this);  //event listener registration
         LoginButton.addActionListener(this);
         PasswordField.addActionListener(this);

         Container contentPane = getContentPane(); //add a panel to a frame
         contentPane.add(LoginPanel);

	}

    public void actionPerformed(ActionEvent evt)  //event handling
    {
        //Object source = evt.getSource(); //get who generates this event
        String arg = evt.getActionCommand();

        if (arg.equals("Sign Up")) { //determine which button is clicked
            //System.out.println("Name: "+arg);
            SignUpControl SUC = new SignUpControl(); //take actions
		}

		if (arg.equals("Login")) {
			//System.out.println("Name: "+arg);
			String Username = UsernameField.getText();
			String Password = PasswordField.getText();
            LoginControl LoginC = new LoginControl(Username, Password);
		}
    }

    public static void main(String [] args)
    { JFrame frame = new LoginBO(); //initialize a JFrame object
      frame.show(); //display the frame
    }
}

