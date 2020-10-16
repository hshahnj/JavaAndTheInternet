import java.lang.*; //including Java packages used by this program
import javax.swing.*;

public class LoginControl
{
    private Account Acct;

    public LoginControl(String UName, String PWord) {
		Acct = new Account(UName, PWord);
		String CustomerName = Acct.signIn();
        if (!CustomerName.equals("")) {
            //System.out.println("successful!");
            //JOptionPane.showMessageDialog(null, "Login is successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE)
//            OpenBankAccountBO OpenAcctBO = new OpenBankAccountBO(UName, CustomerName);

            CheckingAccount chkAccount = new CheckingAccount();
            String chkAccountNbr = chkAccount.getCheckingAccountNumber(UName);
            float chkAccountBal = chkAccount.getBalance(chkAccountNbr);

            SavingsAccount svgAccount = new SavingsAccount();
            String svgAccountNbr = svgAccount.getSavingsAccountNumber(UName);
            float svgAccountBal = svgAccount.getBalance(svgAccountNbr);

            String chkAccountBalString = String.valueOf(chkAccountBal);
            String svgAccountBalString = String.valueOf(svgAccountBal);

            MainBO mainBO = new MainBO(UName, CustomerName, chkAccountNbr, svgAccountNbr, chkAccountBalString, svgAccountBalString);


            //LOGIN invokes MAINBO which invokes MAINCONTROL which invokes all the panels.



        } else
            //System.out.println("fail!");
            JOptionPane.showMessageDialog(null, "Login failed because of invalid username or password.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
	}
}