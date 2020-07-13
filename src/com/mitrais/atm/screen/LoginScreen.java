package com.mitrais.atm.screen;

import com.mitrais.atm.dao.AccountDaoImpl;
import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Validation;
import com.mitrais.atm.validation.LoginValidation;

import java.util.Scanner;

public class LoginScreen {

    private static LoginScreen instance;

    private LoginScreen() {}

    public static LoginScreen getInstance(){
        if (instance == null) {
            instance = new LoginScreen();
        }
        return instance;
    }

    /**
     * Shows login screen by inputing account and pin number
     */
    public void show() {
        Scanner scanner = new Scanner(System.in);
        String inputAccountNumber, inputPin;
        Validation validateAccountNumber, validatePin;

        System.out.println("\n------------------------------------------------");
        System.out.println("              Welcome to ATM XXX                ");
        System.out.println("------------------------------------------------");

        do {
            System.out.print("Enter Account Number: ");
            inputAccountNumber = scanner.nextLine();

            validateAccountNumber = LoginValidation.validateLoginFields(inputAccountNumber, "Account Number");
            if (!validateAccountNumber.isValid()) {
                System.out.println(validateAccountNumber.getMessage());
            }
        } while (!validateAccountNumber.isValid());

        do {
            System.out.print("Enter PIN: ");
            inputPin = scanner.nextLine();

            validatePin = LoginValidation.validateLoginFields(inputPin, "PIN");
            if (!validatePin.isValid()) {
                System.out.println(validatePin.getMessage());
            }
        } while (!validatePin.isValid());

        Account account = AccountDaoImpl.getInstance().getAccount(inputAccountNumber, inputPin);
        if (account != null) {
            TransactionScreen.getInstance().show(account);
        } else {
            System.out.println("Invalid Account Number/PIN");
            show();
        }
    }
}
