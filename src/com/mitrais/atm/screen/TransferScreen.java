package com.mitrais.atm.screen;

import com.mitrais.atm.dao.AccountDao;
import com.mitrais.atm.dao.AccountDaoImpl;
import com.mitrais.atm.dao.TransactionDao;
import com.mitrais.atm.dao.TransactionDaoImpl;
import com.mitrais.atm.exception.InsufficientBalanceException;
import com.mitrais.atm.model.Account;
import com.mitrais.atm.validation.TransferValidation;

import java.security.SecureRandom;
import java.util.Scanner;

public class TransferScreen {

    private AccountDao accountDao = new AccountDaoImpl();
    private Account account;
    private TransactionDao transactionDao;
    private LoginScreen loginScreen;
    private TransactionScreen transactionScreen;

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

        loginScreen = new LoginScreen();
        transactionScreen = new TransactionScreen(account);
        transactionDao = new TransactionDaoImpl();

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
                Account destinationAccount = accountDao.getAccount(accountNumber);
                balance = transactionDao.transferFund(account, destinationAccount, referenceNumber, transferAmount);
                showTransferSummary();
            } catch (InsufficientBalanceException e) {
                System.out.println("Insufficient balance $" + e.getAmount());
                show();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                show();
            }
        } else {
            transactionScreen.show();
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
            transactionScreen.show();
        } else {
            loginScreen.show();
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
            TransferValidation.validateDestinationAccountField(accountDao, inputAccountNumber);
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
