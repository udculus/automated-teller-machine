package com.mitrais.atm.screen;

import com.mitrais.atm.dao.AccountDao;
import com.mitrais.atm.dao.AccountDaoImpl;
import com.mitrais.atm.model.Account;
import com.mitrais.atm.validation.LoginValidation;

import java.util.Scanner;

public class LoginScreen {

    private AccountDao accountDao = new AccountDaoImpl();
    private TransactionScreen transactionScreen;

    /**
     * Shows login screen by inputting account and pin number
     */
    public void show() {
        Scanner scanner = new Scanner(System.in);
        String inputAccountNumber, inputPin;
        boolean isValid;

        System.out.println("\n------------------------------------------------");
        System.out.println("              Welcome to ATM XXX                ");
        System.out.println("------------------------------------------------");

        do {
            System.out.print("Enter Account Number: ");
            inputAccountNumber = scanner.nextLine();
            isValid = isValidField(inputAccountNumber, "Account Number");
        } while (!isValid);

        do {
            System.out.print("Enter PIN: ");
            inputPin = scanner.nextLine();
            isValid = isValidField(inputPin, "PIN");
        } while (!isValid);

        authenticateAccount(inputAccountNumber, inputPin);
    }

    /**
     * Authenticate inputted account and pin field
     * @param inputAccountNumber
     * @param inputPin
     */
    private void authenticateAccount(String inputAccountNumber, String inputPin) {
        try {
            Account account = accountDao.authenticateAccount(inputAccountNumber, inputPin);
            transactionScreen = new TransactionScreen(account);
            transactionScreen.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            show();
        }
    }

    /**
     * Validate inputted field on login screen
     * @param inputField
     * @param s
     * @return
     */
    private boolean isValidField(String inputField, String s) {
        boolean isValid;
        try {
            LoginValidation.validateLoginFields(inputField, s);
            isValid = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            isValid = false;
        }

        return isValid;
    }
}
