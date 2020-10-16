/******************************************************************************
 *	Program Author: Dr. Yongming Tang for CSCI 6810 Java and the Internet	  *
 *	Date: February, 2014													  *
 *******************************************************************************/

import javax.swing.*;

public class OpenBankAccountControl {

    public OpenBankAccountControl(String AccountType, String AccountNumber, String Name, String UName, String Balance) {
        //Use CheckingAccount object to invoke method openAcct()
        if (AccountType.equals("Checking")) {
            CheckingAccount CA = new CheckingAccount(AccountNumber, Name, UName, Balance);
            if (CA.openAcct()) {
                //System.out.println("successful!");
                JOptionPane.showMessageDialog(null, "Opening a Checking Account is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            } else
                //System.out.println("fail!");
                JOptionPane.showMessageDialog(null, "Opening a Checking Account failed.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        }

        if (AccountType.equals("Savings")) {
            SavingsAccount SA = new SavingsAccount(AccountNumber, Name, UName, Balance);
            if (SA.openAcct()) {
                //System.out.println("successful!");
                JOptionPane.showMessageDialog(null, "Opening a Savings Account is Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            } else
                //System.out.println("fail!");
                JOptionPane.showMessageDialog(null, "Opening a Savings Account failed.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}



