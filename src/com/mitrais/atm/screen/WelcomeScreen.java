package com.mitrais.atm.screen;

import com.mitrais.atm.dao.AccountDao;
import com.mitrais.atm.dao.AccountDaoImpl;
import com.mitrais.atm.validation.AccountValidation;

import java.util.Scanner;

public class WelcomeScreen {

    private AccountDao accountDao = new AccountDaoImpl();

    /**
     * Prompt user to provide account csv file
     */
    public void show() {
        Scanner scanner = new Scanner(System.in);
        String inputFilePath;
        boolean isValid = true;

        do {
            System.out.println("Please provide csv file path for the accounts or");
            System.out.print("leave it empty to use from app's data: ");

            inputFilePath = scanner.nextLine();

            try {
                AccountValidation.validateSourceFile(inputFilePath);
                accountDao.seedAccounts(inputFilePath);
                new LoginScreen().show();
                isValid = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                isValid = false;
            }
        } while (!isValid);
    }
}
