package com.mitrais.atm.screen;

import com.mitrais.atm.model.Account;

import java.util.Scanner;

public class TransactionScreen {

    private Account account;

    public TransactionScreen(Account account) {
        this.account = account;
    }

    /**
     * Shows transaction options
     */
    public void show() {
        Scanner scanner = new Scanner(System.in);
        String selectedOption;

        System.out.println("------------------------------------------------");
        System.out.println("Transaction");
        System.out.println("1. Withdraw");
        System.out.println("2. Fund Transfer");
        System.out.println("3. Transaction History");
        System.out.println("4. Exit");
        System.out.print("Please choose option[3]: ");
        selectedOption = scanner.nextLine();

        if (selectedOption.equals("1")) {
            new WithdrawScreen(account).show();
        } else if (selectedOption.equals("2")) {
            new TransferScreen(account).show();
        } else if (selectedOption.equals("3")) {
            new HistoryScreen(account).show();
        } else if (selectedOption.equals("4") || selectedOption.equals("")) {
            new LoginScreen().show();
        } else {
            show();
        }
    }

}
