package com.mitrais.atm.screen;

import com.mitrais.atm.dao.AccountDao;
import com.mitrais.atm.model.Account;

import java.util.Scanner;

public class TransactionScreen {

    AccountDao accountDao;
    Account account;
    LoginScreen loginScreen;
    TransferScreen transferScreen;
    WithdrawScreen withdrawScreen;

    public TransactionScreen(AccountDao accountDao, Account account) {
        this.accountDao = accountDao;
        this.account = account;
    }

    /**
     * Shows transaction options
     */
    public void show() {
        withdrawScreen = new WithdrawScreen(accountDao, account);
        transferScreen = new TransferScreen(accountDao, account);
        loginScreen = new LoginScreen(accountDao);

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
            withdrawScreen.show();
        } else if (selectedOption.equals("2")) {
            transferScreen.show();
        } else if (selectedOption.equals("3") || selectedOption.equals("")) {
            loginScreen.show();
        } else {
            show();
        }
    }

}
