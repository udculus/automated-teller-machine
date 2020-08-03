package com.mitrais.atm.screen;

import com.mitrais.atm.service.AccountService;
import com.mitrais.atm.service.AccountServiceImpl;
import com.mitrais.atm.validation.FileValidation;

import java.util.Scanner;

public class WelcomeScreen {

    private AccountService accountService = new AccountServiceImpl();

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
                FileValidation.validateSourceFile(inputFilePath);
                accountService.seedAccounts(inputFilePath);
                new LoginScreen().show();
                isValid = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                isValid = false;
            }
        } while (!isValid);
    }
}
