package com.mitrais.atm.screen;

import com.mitrais.atm.service.TransactionService;
import com.mitrais.atm.service.TransactionServiceImpl;
import com.mitrais.atm.exception.InsufficientBalanceException;
import com.mitrais.atm.model.Account;
import com.mitrais.atm.validation.WithdrawValidation;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WithdrawScreen {

    private TransactionService transactionService = new TransactionServiceImpl();
    private Account account;

    public WithdrawScreen(Account account) {
        this.account = account;
    }

    /**
     * Shows withdrawal options
     */
    public void show() {
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
                new TransactionScreen(account).show();
                break;
            default:
                show();
        }
    }

    /**
     * Proceed withdrawal by validating the balance account
     * @param account
     * @param amount
     */
    private void withdraw(Account account, int amount) {
        try {
            transactionService.withdraw(account, amount);
            showSummaryWithdrawal(account, amount);
        } catch (InsufficientBalanceException e) {
            System.out.println("Insufficient balance $" + e.getAmount());
            show();
        }
    }

    /**
     * Custom withdrawal by inputted amount of value
     * @param account
     */
    private void showCustomWithdrawal(Account account) {
        Scanner scanner = new Scanner(System.in);
        String inputAmount;
        boolean isValid;

        System.out.println("------------------------------------------------");
        System.out.println("Other Withdraw");

        do {
            System.out.print("Enter amount to withdraw: ");
            inputAmount = scanner.next();

            isValid = isValidWithdrawalField(inputAmount);
        } while (!isValid);

        withdraw(account, Integer.valueOf(inputAmount));
    }

    /**
     * Validate withdrawal field
     * @param inputAmount
     * @return
     */
    private boolean isValidWithdrawalField(String inputAmount) {
        boolean isValid;
        try {
            WithdrawValidation.validateWithdrawalField(inputAmount);
            isValid = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            isValid = false;
        }
        return isValid;
    }

    /**
     * Shows summary of withdrawal
     * @param account
     * @param amount
     */
    private void showSummaryWithdrawal(Account account, int amount) {
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
            new TransactionScreen(account).show();
        } else {
            new LoginScreen().show();
        }
    }
}
