package com.mitrais.atm.screen;

import com.mitrais.atm.dao.AccountDaoImpl;
import com.mitrais.atm.model.Account;

import java.util.Scanner;

public class TransactionScreen {

    private static TransactionScreen instance;

    private TransactionScreen() {}

    public static TransactionScreen getInstance(){
        if (instance == null) {
            instance = new TransactionScreen();
        }
        return instance;
    }

    /**
     * Shows transaction options
     * @param account
     */
    public void show(Account account) {
        Scanner scanner = new Scanner(System.in);
        String selectedOption;

        System.out.println("------------------------------------------------");
        System.out.println("Transaction");
        System.out.println("1. Withdraw");
        System.out.println("2. Fund Transfer");
        System.out.println("3. Exit");
        System.out.print("Please choose option[3]: ");
        selectedOption = scanner.nextLine();

        if (selectedOption.equals("1")) {
            WithdrawScreen.getInstance().show(account);
        } else if (selectedOption.equals("2")) {
            TransferScreen.getInstance().show(account);
        } else if (selectedOption.equals("3") || selectedOption.equals("")) {
            LoginScreen.getInstance().show();
        } else {
            show(account);
        }
    }

}
