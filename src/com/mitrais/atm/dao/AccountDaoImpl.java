package com.mitrais.atm.dao;

import com.mitrais.atm.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {

    List<Account> accounts;

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
    public Account getAccount(String accountNumber) throws Exception {
        return accounts.stream()
                .filter(account -> accountNumber.equals(account.getAccountNumber()))
                .findAny()
                .orElseThrow(() -> new Exception("Invalid Account"));
    }

    /**
     * Get account by account number for authentication only
     * @param accountNumber
     * @param pin
     * @return
     */
    @Override
    public Account authenticateAccount(String accountNumber, String pin) throws Exception {
        return accounts.stream()
                .filter(account -> accountNumber.equals(account.getAccountNumber()) && pin.equals(account.getPin()))
                .findAny()
                .orElseThrow(() -> new Exception("Invalid Account Number/PIN"));
    }
}
