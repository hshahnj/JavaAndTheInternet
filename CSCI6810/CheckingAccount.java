import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class CheckingAccount {   //Instance Variables
    private String CheckingAccountNumber, CustomerName, CustomerID;
    private float Balance = -1;

    public CheckingAccount(String CA_Num, String Cust_Name, String Cust_ID, String Bal) { //Constructor One with three parameters
        CheckingAccountNumber = CA_Num;
        CustomerName = Cust_Name;
        CustomerID = Cust_ID;
        Balance = Float.parseFloat(Bal);
    }

    public CheckingAccount(String CA_Num) { //Constructor Two with one parameter
        CheckingAccountNumber = CA_Num;
        CustomerID = getCustomerID();
    }

    public CheckingAccount() {    }

    public String getCheckingAccountNumber(String customerID) {
        String chkAccountNbr = "";
        boolean done = false;
        try {
            DBConnection ToDB = new DBConnection(); //Have a connection to the DB
            Connection DBConn = ToDB.openConn();
            Statement stmt = DBConn.createStatement();
            String SQL_Command = "SELECT checkingAccountNumber FROM CheckingAccount WHERE CustomerID ='" + customerID + "'"; //SQL query command for Balance
            ResultSet Rslt = stmt.executeQuery(SQL_Command);
            done = Rslt.next();
            if (done) {
                SQL_Command = "SELECT checkingAccountNumber FROM CheckingAccount WHERE CustomerID ='" + customerID + "'"; //SQL query command for Balance
                ResultSet rs2 = stmt.executeQuery(SQL_Command);
                rs2.next();
                chkAccountNbr = rs2.getString(1);
            }
            stmt.close();
            ToDB.closeConn();
        } catch (java.sql.SQLException e) {
            System.out.println("SQLException: " + e);
            while (e != null) {
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("Message: " + e.getMessage());
                System.out.println("Vendor: " + e.getErrorCode());
                e = e.getNextException();
                System.out.println("");
            }
        } catch (java.lang.Exception e) {
            System.out.println("Exception: " + e);
            e.printStackTrace();
        }

        return chkAccountNbr;
    }

    public String getCustomerID() {
        String custID = "";
        boolean done = false;
        try {
            DBConnection ToDB = new DBConnection(); //Have a connection to the DB
            Connection DBConn = ToDB.openConn();
            Statement stmt = DBConn.createStatement();
            String SQL_Command = "SELECT customerID FROM CheckingAccount WHERE CheckingAccountNumber ='" + CheckingAccountNumber + "'"; //SQL query command for Balance
            ResultSet Rslt = stmt.executeQuery(SQL_Command);
            done = Rslt.next();
            if (done) {
                SQL_Command = "SELECT customerID FROM CheckingAccount WHERE CheckingAccountNumber ='" + CheckingAccountNumber + "'"; //SQL query command for Balance
                ResultSet rs2 = stmt.executeQuery(SQL_Command);
                rs2.next();
                custID = rs2.getString(1);
            }
            stmt.close();
            ToDB.closeConn();
        } catch (java.sql.SQLException e) {
            System.out.println("SQLException: " + e);
            while (e != null) {
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("Message: " + e.getMessage());
                System.out.println("Vendor: " + e.getErrorCode());
                e = e.getNextException();
                System.out.println("");
            }
        } catch (java.lang.Exception e) {
            System.out.println("Exception: " + e);
            e.printStackTrace();
        }

        return custID;
    }

    public boolean openAcct() {
        boolean done = false;
        try {
            if (!done) {
                DBConnection ToDB = new DBConnection(); //Have a connection to the DB
                Connection DBConn = ToDB.openConn();
                Statement Stmt = DBConn.createStatement();
                String SQL_Command = "SELECT CheckingAccountNumber FROM CheckingAccount WHERE CheckingAccountNumber ='" + CheckingAccountNumber + "'"; //SQL query command
                ResultSet Rslt = Stmt.executeQuery(SQL_Command); //Inquire if the username exsits.
                done = !Rslt.next();
                if (done) {
                    SQL_Command = "INSERT INTO CheckingAccount(CheckingAccountNumber, CustomerName, Balance, CustomerID)" +
                            " VALUES ('" + CheckingAccountNumber + "','" + CustomerName + "'," + Balance + ", '" + CustomerID + "')"; //Save the username, password and Name
                    Stmt.executeUpdate(SQL_Command);

                    Transactions transactions = new Transactions(Balance, "Deposit", "External", CheckingAccountNumber, CustomerID);
                    transactions.doTransaction();
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

    public boolean deposit(float addBalance, String TransactionType, String FromAccount, String ToAccount) {
        boolean done = false;
        try {
            if (!done) {
                DBConnection ToDB = new DBConnection();
                Connection DBConn = ToDB.openConn();
                Statement stmt = DBConn.createStatement();
                String SQL_Command = "Select CheckingAccountNumber FROM CheckingAccount WHERE CheckingAccountNumber = '" + ToAccount + "';";
                ResultSet rs = stmt.executeQuery(SQL_Command);
                done = rs.next();
                if (done) {
                    SQL_Command = "Select Balance from checkingAccount where CheckingAccountNumber = '" + ToAccount + "';";
                    ResultSet rs2 = stmt.executeQuery(SQL_Command);
                    rs2.next();
                    float oldBalance = rs2.getFloat("Balance");
                    float newBalance = oldBalance + addBalance;
                    SQL_Command = "update checkingaccount set balance = " + newBalance + " where checkingaccountnumber = '" + ToAccount + "';";
                    stmt.executeUpdate(SQL_Command);

                    Transactions transactions = new Transactions(addBalance, TransactionType, FromAccount, ToAccount, CustomerID);
                    transactions.doTransaction();
                }
                stmt.close();
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

    public int withdraw(float removeBalance, String TransactionType, String FromAccount, String ToAccount) {
        //closing scenario
        // 1 = false
        // 0 = true
        // 2 = oldBalance < removeBalance
        int closingScenario = 1;
        boolean exists;
        try {
            DBConnection ToDB = new DBConnection();
            Connection DBConn = ToDB.openConn();
            Statement stmt = DBConn.createStatement();
            String SQL_Command = "Select CheckingAccountNumber FROM CheckingAccount WHERE CheckingAccountNumber = '" + FromAccount + "';";
            ResultSet rs = stmt.executeQuery(SQL_Command);
            exists = rs.next();
            if (exists) {
                SQL_Command = "Select Balance from checkingAccount where CheckingAccountNumber = '" + FromAccount + "';";
                ResultSet rs2 = stmt.executeQuery(SQL_Command);
                rs2.next();
                float oldBalance = rs2.getFloat("Balance");
                if (oldBalance < removeBalance) {
                    closingScenario = 2;
                    return closingScenario;
                }
                float newBalance = oldBalance - removeBalance;
                SQL_Command = "update checkingaccount set balance = " + newBalance + " where checkingaccountnumber = '" + FromAccount + "';";
                stmt.executeUpdate(SQL_Command);

                Transactions transactions = new Transactions(removeBalance, TransactionType, FromAccount, ToAccount, CustomerID);
                transactions.doTransaction();
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
        } catch (java.lang.Exception e) {
            exists = false;
            System.out.println("Exception: " + e);
            e.printStackTrace();
        }
        closingScenario = exists ? 0 : 1;

        return closingScenario;
    }

    public float getBalance() {  //Method to return a CheckingAccount balance
        try {
            DBConnection ToDB = new DBConnection(); //Have a connection to the DB
            Connection DBConn = ToDB.openConn();
            Statement Stmt = DBConn.createStatement();
            String SQL_Command = "SELECT Balance FROM CheckingAccount WHERE CheckingAccountNumber ='" + CheckingAccountNumber + "'"; //SQL query command for Balance
            ResultSet Rslt = Stmt.executeQuery(SQL_Command);

            while (Rslt.next()) {
                Balance = Rslt.getFloat(1);
            }
            Stmt.close();
            ToDB.closeConn();
        } catch (java.sql.SQLException e) {
            System.out.println("SQLException: " + e);
            while (e != null) {
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("Message: " + e.getMessage());
                System.out.println("Vendor: " + e.getErrorCode());
                e = e.getNextException();
                System.out.println("");
            }
        } catch (java.lang.Exception e) {
            System.out.println("Exception: " + e);
            e.printStackTrace();
        }
        return Balance;
    }

    public float getBalance(String ChkAcctNumber) {
        try {
            DBConnection ToDB = new DBConnection(); //Have a connection to the DB
            Connection DBConn = ToDB.openConn();
            Statement Stmt = DBConn.createStatement();
            String SQL_Command = "SELECT Balance FROM CheckingAccount WHERE CheckingAccountNumber ='" + ChkAcctNumber + "'"; //SQL query command for Balance
            ResultSet Rslt = Stmt.executeQuery(SQL_Command);

            while (Rslt.next()) {
                Balance = Rslt.getFloat(1);
            }
            Stmt.close();
            ToDB.closeConn();
        } catch (java.sql.SQLException e) {
            System.out.println("SQLException: " + e);
            while (e != null) {
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("Message: " + e.getMessage());
                System.out.println("Vendor: " + e.getErrorCode());
                e = e.getNextException();
                System.out.println("");
            }
        } catch (java.lang.Exception e) {
            System.out.println("Exception: " + e);
            e.printStackTrace();
        }
        return Balance;
    }
}