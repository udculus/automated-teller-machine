package com.mitrais.atm.screen;

import com.mitrais.atm.dao.TransactionDao;
import com.mitrais.atm.dao.TransactionDaoImpl;
import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class HistoryScreen {

    private Account account;
    private TransactionDao transactionDao = new TransactionDaoImpl();;

    public HistoryScreen(Account account) { this.account = account; }

    /**
     * Shows all transaction log history
     */
    public void show() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");

        System.out.println("------------------------------------------------");
        System.out.println("Transaction History");

        try {
            List<Transaction> transactions = transactionDao.getHistory();
            System.out.println("| Account Number\t| Destination Number\t| Reference Number\t| Amount\t| Time\t\t\t\t\t|");
            transactions.forEach( row -> {
                System.out.println("| " + row.getAccountNumber() + "\t\t\t| " + row.getDestinationNumber() + "\t\t\t\t| " + row.getReferenceNumber() + "\t\t\t| " + row.getAmount() + "\t\t| " + row.getTime().format(formatter) + "\t|");
            });

            System.out.print("\nPress enter to go back to transaction menu: ");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        new TransactionScreen(account).show();
    }
}
