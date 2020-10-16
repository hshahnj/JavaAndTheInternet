import javax.swing.*;

public class DepositControl {

    public DepositControl(String toAccountType, String accountNbr, float depositAmt) {
        if (toAccountType.equals("Checking")) {
            CheckingAccount chkAccnt = new CheckingAccount(accountNbr);
            if(chkAccnt.deposit(depositAmt, "Deposit", "External", accountNbr))
                JOptionPane.showMessageDialog(null, "Deposit Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, "Deposit Failed!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        } else if (toAccountType.equals("Savings")) {
            SavingsAccount savingsAccount = new SavingsAccount(accountNbr);
            if(savingsAccount.deposit(depositAmt, "Deposit", "External", accountNbr))
                JOptionPane.showMessageDialog(null, "Deposit Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, "Deposit Failed!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}