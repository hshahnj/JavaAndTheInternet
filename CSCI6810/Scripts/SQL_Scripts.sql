--Assignment 1
--Checking Account Creation
CREATE TABLE CheckingAccount(
    CheckingAccountNumber VARCHAR(50),
    CustomerName VARCHAR(50),
    Balance FLOAT,
    CustomerID VARCHAR(50)
);

--Savings Account Creation
CREATE TABLE SavingsAccount(
    SavingsAccountNumber VARCHAR(50),
    CustomerName VARCHAR(50),
    Balance FLOAT,
    InterestRate FLOAT,
    CustomerID VARCHAR(50)
);

--Transactions Creation
CREATE TABLE Transactions(
    TransactionNumber VARCHAR(50),
    TransactionAmount FLOAT,
    TransactionType VARCHAR(50),
    TransactionTime TIME,
    TransactionDate DATE,
    FromAccount VARCHAR(50),
    ToAccount VARCHAR(50),
    CustomerID VARCHAR(50)
);

COMMIT;