package utils.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;  
import java.sql.SQLException;

import individuals.Varun;
import utils.CurrentAccountUser;
import utils.SavingAccountUser;
import utils.helpers.Transaction;

public class SearchDataBase {
    
    /**
     * This function searchUser will search for a user by their username and create appropriate object from that details
     * The Object can be of two types - currentAccountUser and savingAccountUser
     * that's why the return type of function is Object as it is a superclass of every class so we can return that way
     */
    public static Object searchUser(String username) {
        Connection con = ConnectionFactory.getConnection();
        String query = "SELECT * FROM account";
        try(PreparedStatement stmt = con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                if(rs.getString("username").equals(username)) {
                    if(rs.getString("account_type").equalsIgnoreCase("CA")) {
                        CurrentAccountUser user = new CurrentAccountUser(rs.getString("account_holder_name"),
                                                                        rs.getString("gender"),
                                                                        rs.getString("username"),
                                                                        rs.getString("password"),
                                                                        Double.valueOf(rs.getString("account_balance")));
                        user.getAccount().setAccountNumber(rs.getString("account_number"));
                        return user;
                    } else if(rs.getString("account_type").equalsIgnoreCase("SA")) {
                        SavingAccountUser user = new SavingAccountUser(rs.getString("account_holder_name"),
                                                                        rs.getString("gender"),
                                                                        rs.getString("username"),
                                                                        rs.getString("password"),
                                                                        Double.valueOf(rs.getString("account_balance")));
                        user.getAccount().setAccountNumber(rs.getString("account_number"));
                        return user;
                    }
                }
            }
            System.out.println("Record not found");
        } catch(Exception e) {
            if(Varun.inProduction) {
                e.printStackTrace();
            } else {
                System.out.println("Some error occurred while accessing the database");
            }
        }
        try {
            con.close();
        } catch (SQLException e) {
            // e.printStackTrace();
        }
        return null;
    }

    /**
     * This method searchUserByAccountNumber will simply search for a user by their account number
     * This function prints the details of the user by using the toString method of that class which we have overriden
     */
    public static void searchUserByAccountNumber(String number) {
        Connection con = ConnectionFactory.getConnection();
        String query = "SELECT * FROM account";
        try(PreparedStatement stmt = con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                if(rs.getString("account_number").equals(number)) {
                    if(rs.getString("account_type").equalsIgnoreCase("CA")) {
                        CurrentAccountUser user = new CurrentAccountUser(rs.getString("account_holder_name"),
                                                                        rs.getString("gender"),
                                                                        rs.getString("username"),
                                                                        rs.getString("password"),
                                                                        Double.valueOf(rs.getString("account_balance")));
                        user.getAccount().setAccountNumber(rs.getString("account_number"));
                        System.out.println(user);
                        return;
                    } else if(rs.getString("account_type").equalsIgnoreCase("SA")) {
                        SavingAccountUser user = new SavingAccountUser(rs.getString("account_holder_name"),
                                                                        rs.getString("gender"),
                                                                        rs.getString("username"),
                                                                        rs.getString("password"),
                                                                        Double.valueOf(rs.getString("account_balance")));
                        user.getAccount().setAccountNumber(rs.getString("account_number"));
                        System.out.println(user);
                        return;
                    }
                }
            }
            System.out.println("Record not found");
        } catch(Exception e) {
            if(Varun.inProduction) {
                e.printStackTrace();
            } else {
                System.out.println("Some error occurred while accessing the database");
            }
        }
        try {
            con.close();
        } catch (SQLException e) {
            // e.printStackTrace();
        }
    }
    
    /**
     * This method will search user by their name and returns all the users with that name
     * Name can be typed in any format (Varun, VARUN, varun, varuN, VaRuN) all are considered as same
     */
    public static void searchUserByName(String name) {
        Connection con = ConnectionFactory.getConnection();
        String query = "SELECT * FROM account";
        boolean isFound = false;
        try(PreparedStatement stmt = con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                if(rs.getString("account_holder_name").contains(name.toLowerCase())) {
                    isFound = true;
                    if(rs.getString("account_type").equalsIgnoreCase("CA")) {
                        CurrentAccountUser user = new CurrentAccountUser(rs.getString("account_holder_name"),
                                                                        rs.getString("gender"),
                                                                        rs.getString("username"),
                                                                        rs.getString("password"),
                                                                        Double.valueOf(rs.getString("account_balance")));
                        user.getAccount().setAccountNumber(rs.getString("account_number"));
                        System.out.println(user);
                    } else if(rs.getString("account_type").equalsIgnoreCase("SA")) {
                        SavingAccountUser user = new SavingAccountUser(rs.getString("account_holder_name"),
                                                                        rs.getString("gender"),
                                                                        rs.getString("username"),
                                                                        rs.getString("password"),
                                                                        Double.valueOf(rs.getString("account_balance")));
                        user.getAccount().setAccountNumber(rs.getString("account_number"));
                        System.out.println(user);
                    }
                }
            }
            if(!isFound) {
                System.out.println("Record not found");
            }
        } catch(Exception e) {
            if(Varun.inProduction) {
                e.printStackTrace();
            } else {
                System.out.println("Some error occurred while accessing the database");
            }
        }
        try {
            con.close();
        } catch (SQLException e) {
            // e.printStackTrace();
        }
    }

    /**
     * This method will simply search for a transaction using its transaction id and create a Transaction object from it
     * Then it prints the object by using its own toString method which is overridden
     */
    public static void searchTransaction(String string) {
        Connection con = ConnectionFactory.getConnection();
        String query = "SELECT * FROM transaction";
        boolean isFound = false;
        try(PreparedStatement stmt = con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                if(Integer.toString(rs.getInt("transaction_id")).equals(string)) {
                    String id = Integer.toString(rs.getInt("transaction_id"));
                    String dateTime = rs.getString("transaction_date");
                    String sender = rs.getString("sender");
                    String receiver = rs.getString("receiver");
                    int amount = rs.getInt("amount");
                    String type = rs.getString("type");
                    Transaction transaction = new Transaction(id, dateTime, amount, sender, receiver, type);
                    System.out.println(transaction);
                    isFound = true;
                    break;
                }
            }
            if(!isFound) {
                System.out.println("No record found for this transaction ID");
            }
        } catch (Exception e) {
            if(Varun.inProduction) {
                e.printStackTrace();
            } else {
                System.out.println("Some internal error occurred");
            }
        }
        try {
            con.close();
        } catch (SQLException e) {
            // e.printStackTrace();
        }
    }

    /**
     * This method will print the name and account number of the customers which are sorted according to their names
     */
    public static void printSortedUserList() {
        Connection con = ConnectionFactory.getConnection();
        String query = "SELECT account_holder_name, account_number FROM account ORDER BY account_holder_name";
        try(PreparedStatement stmt = con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            System.out.println("+-----------------------+---------------+");
            System.out.println("|\t   NAME   \t|    Acc. No.\t|");
            System.out.println("|-----------------------+---------------|");
            while(rs.next()) {
                System.out.printf("|\t%10s\t|\t%s\t|\n", rs.getString("account_holder_name"),rs.getString("account_number"));
            }
            System.out.println("+-----------------------+---------------+");
        } catch (Exception e) {
            if(Varun.inProduction) {
                e.printStackTrace();
            } else {
                System.out.println("Some internal error occurred");
            }
        }
        try {
            con.close();
        } catch (SQLException e) {
            // e.printStackTrace();
        }
    }

    /**
     * This method is used to print the transaction done by a particular user
     * either it is a withdrawal, deposit or transfer
     * This method is called from Varun.java file whenever user is asking for detailed description of the user
     */
    public static void printTransactions(String string) {
        Connection con = ConnectionFactory.getConnection();
        String query = "SELECT * FROM transaction";
        try(PreparedStatement stmt = con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            System.out.println("+---------------------------------------------------------------------------------------+");
            System.out.println("|                                 Transactions                                          |");
            System.out.println("|---------------------------------------------------------------------------------------|");
            System.out.println("|\tID\t|\t Date and Time \t\t|  Amount\t| \t Remark \t|");
            System.out.println("|---------------+---------------------------------+---------------+---------------------|");
            while(rs.next()) {
                if(rs.getString("sender").equals(string)) {
                    String result = "|\t" + rs.getInt("transaction_id")
                                    + "\t|\t" + rs.getString("transaction_date")
                                    + "\t|\t" + rs.getInt("amount");

                    if(rs.getString("type").equals("W") || rs.getString("type").equals("T")) {
                        result = result + "\t|\tWithdraw\t|";
                    } else if(rs.getString("type").equals("D")) {
                        result = result + "\t|\tDeposit \t|";
                    }
                    System.out.println(result);
                } else if(rs.getString("receiver").equals(string)) {
                    String result = "|\t" + rs.getInt("transaction_id")
                                    + "\t|\t" + rs.getString("transaction_date")
                                    + "\t|\t" + rs.getInt("amount");

                    if(rs.getString("type").equals("W")) {
                        result = result + "\t|\tWithdraw\t|";
                    } else if(rs.getString("type").equals("D") || rs.getString("type").equals("T")) {
                        result = result + "\t|\tDeposit \t|";
                    }
                    System.out.println(result);
                }
            }
            System.out.println("+---------------------------------------------------------------------------------------+");
        } catch (Exception e) {
            if(Varun.inProduction) {
                e.printStackTrace();
            } else {
                System.out.println("Some error occurred");
            }
        }
    }
}
