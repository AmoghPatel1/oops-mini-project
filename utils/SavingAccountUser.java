package utils;

import java.io.FileWriter;
import java.io.IOException;

public class SavingAccountUser {
    private String name;
    private String gender;
    private String username;
    private String password;
    private SavingAccount account = new SavingAccount();

    public SavingAccountUser(String name, String gender, String username, String password, double balance) throws IOException {
        this.name = name;
        this.gender = gender;
        this.username = username;
        this.password = password;
        this.account.setAccountNumber(Double.toString(Math.random()));
        this.account.setBalance(balance);

        addUserToFile();
    }

    private void addUserToFile() throws IOException {
        String file = "data/savingAccountUser.csv";
        String entry = this.getUsername() +"," + this.getPassword() + "," + this.account.getAccountNumber() + "," + this.getName() + "," + this.getGender() + "\n";
        FileWriter writer = new FileWriter(file, true);
        System.out.println(writer);
        writer.write(entry);
        writer.close();
        System.out.println("Account created successfully");
    }

    @Override
    public String toString() {
        return "[Name : " + this.getName() + ", UserName : " + this.getUsername()
                 + ", Account Number : " + this.account.getAccountNumber() + ", Gender : " + this.getGender() + "]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SavingAccount getAccount() {
        return account;
    }

    public void setAccount(SavingAccount account) {
        this.account = account;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}