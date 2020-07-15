package com.mitrais.atm.screen;

import com.mitrais.atm.dao.AccountDaoImpl;
import com.mitrais.atm.exception.InsufficientBalanceException;
import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Validation;
import com.mitrais.atm.validation.WithdrawValidation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WithdrawScreen {

    private static WithdrawScreen instance;

    private WithdrawScreen() {}

    public static WithdrawScreen getInstance(){
        if (instance == null) {
            instance = new WithdrawScreen();
        }
        return instance;
    }

    /**
     * Shows withdrawal options
     * @param account
     */
    public void show(Account account) {
        Scanner scanner = new Scanner(System.in);
        String selectedOption;

        System.out.println("------------------------------------------------");
        System.out.println("Withdrawal");
        System.out.println("1. $10");
        System.out.println("2. $50");
        System.out.println("3. $100");
        System.out.println("4. Other");
        System.out.println("5. Back");
        System.out.print("Please choose option[5]: ");
        selectedOption = scanner.nextLine();

        switch (selectedOption) {
            case "1":
                withdraw(account, 10);
                break;
            case "2":
                withdraw(account, 50);
                break;
            case "3":
                withdraw(account, 100);
                break;
            case "4":
                showCustomWithdrawal(account);
                break;
            case "5":
                TransferScreen.getInstance().show(account);
                break;
            default:
                show(account);
        }
    }

    /**
     * Proceed withdrawal by validating the balance account
     * @param account
     * @param amount
     */
    public void withdraw(Account account, int amount) {
        try {
            AccountDaoImpl.getInstance().withdraw(account, amount);
            showSummaryWithdrawal(account, amount);
        } catch (InsufficientBalanceException e) {
            System.out.println("Insufficient balance $" + e.getAmount());
            show(account);
        }
    }

    /**
     * Custom withdrawal by inputted amount of value
     * @param account
     */
    public void showCustomWithdrawal(Account account) {
        Validation validateWithdrawal;
        Scanner scanner = new Scanner(System.in);
        String inputAmount;

        System.out.println("------------------------------------------------");
        System.out.println("Other Withdraw");

        do {
            System.out.print("Enter amount to withdraw: ");

            inputAmount = scanner.next();
            validateWithdrawal = WithdrawValidation.validateWithdrawalField(inputAmount);

            if (!validateWithdrawal.isValid()) {
                System.out.println(validateWithdrawal.getMessage());
            }
        } while (!validateWithdrawal.isValid());

        withdraw(account, Integer.valueOf(inputAmount));
    }

    /**
     * Shows summary of withdrawal
     * @param account
     * @param amount
     */
    public void showSummaryWithdrawal(Account account, int amount) {
        Scanner scanner = new Scanner(System.in);
        String selectedOption;
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");

        String formatDateTime = date.format(formatter);

        System.out.println("------------------------------------------------");
        System.out.println("Summary");
        System.out.println("Date : " + formatDateTime);
        System.out.println("Withdraw : $" + amount);
        System.out.println("Balance : $" + account.getBalance());

        System.out.println("\n1. Transaction");
        System.out.println("2. Exit");
        System.out.print("Choose Option[2]: ");
        selectedOption = scanner.nextLine();

        if (selectedOption.equals("1")) {
            TransactionScreen.getInstance().show(account);
        } else {
            LoginScreen.getInstance().show();
        }
    }
}
