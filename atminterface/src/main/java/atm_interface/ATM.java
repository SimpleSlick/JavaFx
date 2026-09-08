package atm_interface;

import java.text.NumberFormat;
import java.util.Locale;

public class ATM {

    private static final double MAX_WITHDRAWAL = 10_000;
    private static final double MAX_DEPOSIT = 50_000;

    private final BankAccount account;
    private final NumberFormat currencyFormat;

    public ATM(BankAccount account) {
        this.account = account;
        currencyFormat = NumberFormat.getCurrencyInstance(
                new Locale("en", "US")
        );
    }

    public String withdraw(double amount) {
        if (amount <= 0) {
            return "❌ Invalid amount!";
        }

        if (amount > account.getBalance()) {
            return "❌ Insufficient balance! Available: "
                    + formatCurrency(account.getBalance());
        }

        if (amount > MAX_WITHDRAWAL) {
            return "❌ Daily withdrawal limit is $10,000!";
        }

        account.setBalance(
                account.getBalance() - amount
        );

        return "✅ Withdrawal of "
                + formatCurrency(amount)
                + " successful!\nNew balance: "
                + formatCurrency(account.getBalance());
    }

    public String deposit(double amount) {
        if (amount <= 0) {
            return "❌ Invalid amount!";
        }

        if (amount > MAX_DEPOSIT) {
            return "❌ Daily deposit limit is $50,000!";
        }

        account.setBalance(
                account.getBalance() + amount
        );

        return "✅ Deposit of "
                + formatCurrency(amount)
                + " successful!\nNew balance: "
                + formatCurrency(account.getBalance());
    }

    public String checkBalance() {
        return "Current balance: "
                + formatCurrency(account.getBalance());
    }

    public double getBalance() {
        return account.getBalance();
    }

    private String formatCurrency(double amount) {
        return currencyFormat.format(amount);
    }
}