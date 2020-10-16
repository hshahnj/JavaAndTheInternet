import javax.swing.*;

public class TransferControl {
    public TransferControl(String fromAccountType, String toAccountType, String chkAccountNbr, String svgAccountNbr, float transferAmt) {

        CheckingAccount chkAccnt = new CheckingAccount(chkAccountNbr);
        SavingsAccount svgAccnt = new SavingsAccount(svgAccountNbr);
        boolean depositCheck = false;
        int withdrawalCheck = 1;

        //closing scenario
        // 1 = false
        // 0 = true
        // 2 = oldBalance < removeBalance

        if (toAccountType.equals("Checking") && fromAccountType.equals("Savings")) {

            withdrawalCheck = svgAccnt.withdraw(transferAmt, "Withdraw", svgAccountNbr, chkAccountNbr);
            if (withdrawalCheck == 0) {
                depositCheck = chkAccnt.deposit(transferAmt, "Deposit", svgAccountNbr, chkAccountNbr);
                if (depositCheck)
                    JOptionPane.showMessageDialog(null, "Transfer Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, "Transfer Failed!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

            } else if (withdrawalCheck == 2)

                JOptionPane.showMessageDialog(null, "Transfer Failed! Not enough balance!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            else if (withdrawalCheck == 1)
                JOptionPane.showMessageDialog(null, "Transfer Failed! Issue on Withdrawal Side!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

        } else if (toAccountType.equals("Savings") && fromAccountType.equals("Checking")) {


            withdrawalCheck = chkAccnt.withdraw(transferAmt, "Withdraw", svgAccountNbr, chkAccountNbr);

            if (withdrawalCheck == 0) {
                depositCheck = svgAccnt.deposit(transferAmt, "Deposit", svgAccountNbr, chkAccountNbr);

                if (depositCheck)
                    JOptionPane.showMessageDialog(null, "Transfer Successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

                else
                    JOptionPane.showMessageDialog(null, "Transfer Failed!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

            } else if (withdrawalCheck == 2)

                JOptionPane.showMessageDialog(null, "Transfer Failed! Not enough balance!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

            else if (withdrawalCheck == 1)

                JOptionPane.showMessageDialog(null, "Transfer Failed! Issue on Withdrawal Side!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);


        }
    }
}
