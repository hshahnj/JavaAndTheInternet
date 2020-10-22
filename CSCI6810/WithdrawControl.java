import javax.swing.*;

public class WithdrawControl {
    public WithdrawControl(String toAccountType, String accountNbr, float withdrawAmt) {
        int closingScenario = 1;
        if (toAccountType.equals("Checking")) {
            CheckingAccount chkAccnt = new CheckingAccount(accountNbr);
            //closing scenario
            // 1 = false
            // 0 = true
            // 2 = oldBalance < removeBalance
            closingScenario = chkAccnt.withdraw(withdrawAmt, "Withdraw", accountNbr, "External");
            if (closingScenario == 0)
                JOptionPane.showMessageDialog(null, "Withdrawal Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else if (closingScenario == 2)
                JOptionPane.showMessageDialog(null, "Withdrawal Failed! You don't have enough balance!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else if (closingScenario == 1)
                JOptionPane.showMessageDialog(null, "Withdrawal Failed!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        } else if (toAccountType.equals("Savings")) {
            SavingsAccount savingsAccount = new SavingsAccount(accountNbr);
            closingScenario = savingsAccount.withdraw(withdrawAmt, "Withdraw", accountNbr, "External");
            if (closingScenario == 0)
                JOptionPane.showMessageDialog(null, "Withdrawal Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else if (closingScenario == 2)
                JOptionPane.showMessageDialog(null, "Withdrawal Failed! You don't have enough balance!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else if (closingScenario == 1)
                JOptionPane.showMessageDialog(null, "Withdrawal Failed!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

        }
    }
}
