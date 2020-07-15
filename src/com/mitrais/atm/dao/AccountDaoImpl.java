package com.mitrais.atm.dao;

import com.mitrais.atm.exception.InsufficientBalanceException;
import com.mitrais.atm.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {

    private static AccountDaoImpl instance;
    List<Account> accounts;

    private AccountDaoImpl() {
    }

    public static AccountDaoImpl getInstance() {
        if (instance == null) {
            instance = new AccountDaoImpl();
        }
        return instance;
    }

    /**
     * Seed account to list
     */
    @Override
    public void seedAccounts() {
        accounts = new ArrayList<Account>();
        accounts.add(new Account("112233", "012108", "John Doe", 100));
        accounts.add(new Account("112244", "932012", "Jane Doe", 30));
    }

    /**
     * Get account by account number
     * @param accountNumber
     * @return
     */
    @Override
    public Account getAccount(String accountNumber) {
        return accounts.stream()
                .filter(account -> accountNumber.equals(account.getAccountNumber()))
                .findAny()
                .orElse(null);
    }

    /**
     * Get account by account number for authentication only
     * @param accountNumber
     * @param pin
     * @return
     */
    @Override
    public Account getAccount(String accountNumber, String pin) {
        return accounts.stream()
                .filter(account -> accountNumber.equals(account.getAccountNumber()) && pin.equals(account.getPin()))
                .findAny()
                .orElse(null);
    }

    /**
     * Transfer fund from logged in account to destination account with inputted amount of value
     * @param account
     * @param receiver
     * @param amount
     * @return
     */
    @Override
    public int transferFund(Account account, Account receiver, int amount) {
        int accountBalance = account.getBalance();
        int receiverBalance = receiver.getBalance();

        accountBalance -= amount;
        receiverBalance += amount;

        account.setBalance(accountBalance);
        receiver.setBalance(receiverBalance);

        return account.getBalance();
    }

    /**
     * Deduct balance of the logged in account
     * @param account
     * @param amount
     */
    @Override
    public void withdraw(Account account, int amount) throws InsufficientBalanceException {
        int accountBalance = account.getBalance();

        if (accountBalance < amount) {
            throw new InsufficientBalanceException(amount);
        } else {
            accountBalance -= amount;
            account.setBalance(accountBalance);
        }
    }
}
