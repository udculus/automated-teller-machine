package com.mitrais.atm.screen;

import com.mitrais.atm.dao.AccountDaoImpl;
import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Validation;
import com.mitrais.atm.validation.TransferValidation;

import java.security.SecureRandom;
import java.util.Scanner;

public class TransferScreen {

    private static TransferScreen instance;
    private Account account;
    private String accountNumber, referenceNumber;
    private int transferAmount, balance;

    private TransferScreen() {}

    public static TransferScreen getInstance(){
        if (instance == null) {
            instance = new TransferScreen();
        }
        return instance;
    }

    /**
     * Shows input destination number to transfer
     * @param account
     */
    public void show(Account account) {
        Validation validateAccountNumber;
        Scanner scanner = new Scanner(System.in);
        String inputAccountNumber;

        this.account = account;

        System.out.println("------------------------------------------------");

        do {
            System.out.println("Please enter destination account and press enter to continue or");
            System.out.print("press cancel (Esc) to go back to Transaction: ");
            inputAccountNumber = scanner.nextLine();

            validateAccountNumber = TransferValidation.validateDestinationAccountField(inputAccountNumber);
            if (!validateAccountNumber.isValid()) {
                System.out.println(validateAccountNumber.getMessage());
            } else {
                accountNumber = inputAccountNumber;
                showTransferAmount();
            }
            System.out.println("------------------------------------------------");
        } while (!validateAccountNumber.isValid());
    }

    /**
     * Shows input amount to transfer
     */
    private void showTransferAmount() {
        Validation validateTransferAmount;
        Scanner scanner = new Scanner(System.in);
        String inputAmount;

        System.out.println("------------------------------------------------");

        do {
            System.out.println("Please enter transfer amount and press enter to continue or");
            System.out.print("press enter to go back to Transaction: ");
            inputAmount = scanner.nextLine();

            validateTransferAmount = TransferValidation.validateTransferAmountField(this.account, inputAmount);
            if (!validateTransferAmount.isValid()) {
                System.out.println(validateTransferAmount.getMessage());
            } else {
                transferAmount = Integer.valueOf(inputAmount);
                showReferenceNumber();
            }
            System.out.println("------------------------------------------------");
        } while (!validateTransferAmount.isValid());
    }

    /**
     * Shows generated reference number
     */
    public void showReferenceNumber() {
        Scanner scanner = new Scanner(System.in);

        referenceNumber = generateReferenceNumber();
        System.out.println("------------------------------------------------");
        System.out.println("Reference Number: " + referenceNumber);
        System.out.print("press enter to continue or press enter to go back to Transaction: ");

        scanner.nextLine();
        showTransferConfirmation();
    }

    /**
     * Shows transfer confirmation
     */
    public void showTransferConfirmation() {
        Scanner scanner = new Scanner(System.in);
        String selectedOption;

        System.out.println("------------------------------------------------");
        System.out.println("Transfer Confirmation");
        System.out.println("Destination Account         : " + accountNumber);
        System.out.println("Transfer Amount             : $" + transferAmount);
        System.out.println("Reference Number            : " + referenceNumber);

        System.out.println("\n1. Confirm Trx");
        System.out.println("2. Cancel Trx");
        System.out.print("Choose Option[2]: ");
        selectedOption = scanner.nextLine();

        if (selectedOption.equals("1")) {
            balance = AccountDaoImpl.getInstance().transferFund(account, AccountDaoImpl.getInstance().getAccount(accountNumber), transferAmount);
            showTransferSummary();
        } else {
            show(account);
        }
    }

    /**
     * Shows summary of completed transaction
     */
    public void showTransferSummary() {
        Scanner scanner = new Scanner(System.in);
        String selectedOption;

        System.out.println("------------------------------------------------");
        System.out.println("Fund Transfer Summary");
        System.out.println("Destination Account         : " + accountNumber);
        System.out.println("Transfer Amount             : $" + transferAmount);
        System.out.println("Reference Number            : " + referenceNumber);
        System.out.println("Balance                     : " + balance);

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

    /**
     * Generated six digits random string
     * @return
     */
    private String generateReferenceNumber() {
        SecureRandom random = new SecureRandom();

        String DATA_FOR_RANDOM_STRING = "0123456789";

        StringBuilder sb = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
            sb.append(rndChar);
        }

        return sb.toString();
    }
}
