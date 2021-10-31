package utils;

public class SavingAccount extends Account{
    
    private double interestRate = 12;

    public SavingAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    public SavingAccount() {
        super("0", 0);
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(int interestRate) {
        this.interestRate = interestRate;
    }

    @Override
    public void addInterest() {
        this.setBalance(getBalance() + (this.getBalance()*interestRate)/100);
    }
}