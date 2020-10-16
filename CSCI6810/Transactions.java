

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class Transactions {
    private String TransactionNumber, TransactionType, FromAccount, ToAccount, CustomerID, TransactionTime, TransactionDate;
    private Float TransactionAmount;


    public Transactions(Float TA, String TT, String FromAcc, String ToAcc, String CustID) {
        TransactionNumber = generateTransactionNumber();
        TransactionAmount = TA;
        TransactionType = TT;
        //Adjust FromAccount and ToAccount based on Transaction Type
        FromAccount = FromAcc;
        ToAccount = ToAcc;
        CustomerID = CustID;
        TransactionTime = null;
        TransactionDate = null;
    }

    public Transactions(String TN, String TA, String TT, String FromAcc, String ToAcc, String CustID, String time, String date) {
        TransactionNumber = TN;
        TransactionAmount = Float.parseFloat(TA);
        TransactionType = TT;
        //Adjust FromAccount and ToAccount based on Transaction Type
        FromAccount = FromAcc;
        ToAccount = ToAcc;
        CustomerID = CustID;
        TransactionTime = time;
        TransactionDate = date;
    }

    public void setTransactionTime(String transactionTime) {
        TransactionTime = transactionTime;
    }

    public void setTransactionDate(String transactionDate) {
        TransactionDate = transactionDate;
    }

    public void setTransactionNumber(String transactionNumber) {
        TransactionNumber = transactionNumber;
    }

    public Transactions(String TN) {
        TransactionNumber = TN;
    }

    public Transactions() {
    }

    public boolean doTransaction() {
        boolean done = false;
        try {
            if (!done) {
                DBConnection ToDB = new DBConnection(); //Have a connection to the DB
                Connection DBConn = ToDB.openConn();
                Statement Stmt = DBConn.createStatement();
                String SQL_Command = "SELECT TransactionNumber FROM Transactions WHERE TransactionNumber ='" + TransactionNumber + "'"; //SQL query command
                ResultSet Rslt = Stmt.executeQuery(SQL_Command); //Inquire if the username exsits.
                done = !Rslt.next();
                if (done) {
                    SQL_Command = "INSERT INTO Transactions(TransactionNumber, TransactionAmount, TransactionType, TransactionTime, TransactionDate, FromAccount, ToAccount, CustomerID)" +
                            " VALUES ('" + TransactionNumber + "'," + TransactionAmount + ",'" + TransactionType + "', CURTIME() , CURDATE() ,'" + FromAccount + "','" + ToAccount + "','" + CustomerID + "')"; //Save the username, password and Name
                    Stmt.executeUpdate(SQL_Command);
                }
                Stmt.close();
                ToDB.closeConn();
            }
        } catch (java.sql.SQLException e) {
            done = false;
            System.out.println("SQLException: " + e);
            while (e != null) {
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("Message: " + e.getMessage());
                System.out.println("Vendor: " + e.getErrorCode());
                e = e.getNextException();
                System.out.println("");
            }
        } catch (java.lang.Exception e) {
            done = false;
            System.out.println("Exception: " + e);
            e.printStackTrace();
        }
        return done;
    }

    public String generateTransactionNumber() {
        Random rnd = new Random();
        int n = 10000000 + rnd.nextInt(90000000);
        return Integer.toString(n);
    }

    public ArrayList<Transactions> getTransactions(String customerID, String startDate, String endDate) {
        boolean exists = false;
        ArrayList<Transactions> transactionList = new ArrayList<>();
        try {
            DBConnection ToDB = new DBConnection();
            Connection DBConn = ToDB.openConn();
            Statement stmt = DBConn.createStatement();
            String SQL_Command = "Select * FROM Transactions WHERE customerId = '" + customerID + "';";
            ResultSet rs = stmt.executeQuery(SQL_Command);
            exists = rs.next();
            if (exists) {
                SQL_Command = "Select * FROM Transactions WHERE customerId = '" + customerID + "' and TransactionDate between '" + startDate + "' and '" + endDate + "';";
                ResultSet rs2 = stmt.executeQuery(SQL_Command);
                while (rs2.next()) {
                    Transactions transaction = new Transactions(rs2.getFloat("TransactionAmount"),
                            rs2.getString("TransactionType"), rs2.getString("FromAccount"),
                            rs2.getString("ToAccount"), rs2.getString("CustomerID"));
                    transaction.setTransactionDate(rs2.getString("TransactionDate"));
                    transaction.setTransactionTime(rs2.getString("TransactionTime"));
                    transaction.setTransactionNumber(rs2.getString("TransactionNumber"));
                    transactionList.add(transaction);
                }
            }
            stmt.close();
            ToDB.closeConn();

        } catch (java.sql.SQLException e) {
            exists = false;
            System.out.println("SQLException: " + e);
            while (e != null) {
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("Message: " + e.getMessage());
                System.out.println("Vendor: " + e.getErrorCode());
                e = e.getNextException();
                System.out.println("");
            }
        } catch (
                java.lang.Exception e) {
            exists = false;
            System.out.println("Exception: " + e);
            e.printStackTrace();
        }

        return transactionList;
    }

    public String getTransactionNumber() {
        return TransactionNumber;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public String getFromAccount() {
        return FromAccount;
    }

    public String getToAccount() {
        return ToAccount;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public String getTransactionTime() {
        return TransactionTime;
    }

    public String getTransactionDate() {
        return TransactionDate;
    }

    public String getTransactionAmount() {
        return String.valueOf(TransactionAmount);
    }


}