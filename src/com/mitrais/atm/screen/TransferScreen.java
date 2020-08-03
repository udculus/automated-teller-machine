package com.mitrais.atm.screen;

import com.mitrais.atm.service.AccountService;
import com.mitrais.atm.service.AccountServiceImpl;
import com.mitrais.atm.service.TransactionService;
import com.mitrais.atm.service.TransactionServiceImpl;
import com.mitrais.atm.exception.InsufficientBalanceException;
import com.mitrais.atm.model.Account;
import com.mitrais.atm.validation.TransferValidation;

import java.security.SecureRandom;
import java.util.Scanner;

public class TransferScreen {

    private AccountService accountService = new AccountServiceImpl();
    private TransactionService transactionService = new TransactionServiceImpl();
    private Account account;

    private String accountNumber, referenceNumber;
    private int transferAmount, balance;

    public TransferScreen(Account account) {
        this.account = account;
    }

    /**
     * Shows input destination number to transfer
     */
    public void show() {
        boolean isValid;
        Scanner scanner = new Scanner(System.in);
        String inputAccountNumber;

        System.out.println("------------------------------------------------");

        do {
            System.out.println("Please enter destination account and press enter to continue or");
            System.out.print("press cancel (Esc) to go back to Transaction: ");
            inputAccountNumber = scanner.nextLine();

            isValid = isValidAccountNumber(inputAccountNumber);
            if (isValid) {
                accountNumber = inputAccountNumber;
                showTransferAmount();
            }

            System.out.println("------------------------------------------------");
        } while (!isValid);
    }

    /**
     * Shows input amount to transfer
     */
    private void showTransferAmount() {
        boolean isValid;
        Scanner scanner = new Scanner(System.in);
        String inputAmount;

        System.out.println("------------------------------------------------");

        do {
            System.out.println("Please enter transfer amount and press enter to continue or");
            System.out.print("press enter to go back to Transaction: ");
            inputAmount = scanner.nextLine();

            isValid = isValidTransferAmount(inputAmount);
            if (isValid) {
                transferAmount = Integer.valueOf(inputAmount);
                showReferenceNumber();
            }

            System.out.println("------------------------------------------------");
        } while (!isValid);
    }

    /**
     * Shows generated reference number
     */
    private void showReferenceNumber() {
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
    private void showTransferConfirmation() {
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
            try {
                Account destinationAccount = accountService.getAccount(accountNumber);
                balance = transactionService.transferFund(account, destinationAccount, referenceNumber, transferAmount);
                showTransferSummary();
            } catch (InsufficientBalanceException e) {
                System.out.println("Insufficient balance $" + e.getAmount());
                show();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                show();
            }
        } else {
            new TransactionScreen(account).show();
        }
    }

    /**
     * Shows summary of completed transaction
     */
    private void showTransferSummary() {
        Scanner scanner = new Scanner(System.in);
        String selectedOption;

        System.out.println("------------------------------------------------");
        System.out.println("Fund Transfer Summary");
        System.out.println("Destination Account         : " + accountNumber);
        System.out.println("Transfer Amount             : $" + transferAmount);
        System.out.println("Reference Number            : " + referenceNumber);
        System.out.println("Balance                     : $" + balance);

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

    /**
     * Shows if inputted account number is valid
     * @param inputAccountNumber
     * @return
     */
    private boolean isValidAccountNumber(String inputAccountNumber) {
        boolean isValid;
        try {
            TransferValidation.validateDestinationAccountField(accountService, inputAccountNumber);
            isValid = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            isValid = false;
        }

        return isValid;
    }

    /**
     * Shows if inputted transfer amount is valid
     * @param inputAmount
     * @return
     */
    private boolean isValidTransferAmount(String inputAmount) {
        boolean isValid;
        try {
            TransferValidation.validateTransferAmountField(this.account, inputAmount);
            isValid = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            isValid = false;
        }

        return isValid;
    }

}
