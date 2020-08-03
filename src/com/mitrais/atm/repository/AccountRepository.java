package com.mitrais.atm.repository;

import com.mitrais.atm.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository {

    private static AccountRepository instance;
    List<Account> accounts = new ArrayList<Account>();

    public static AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepository();
        }
        return instance;
    }

    /**
     * Seed account in memory
     */
    public void seedFromList() {
        accounts.add(new Account("112233", "012108", "John Doe", 100));
        accounts.add(new Account("112244", "932012", "Jane Doe", 30));
    }

    /**
     * Seed account from provided path
     */
    public void seedFromData(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

}
