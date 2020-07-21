package com.mitrais.atm.repository;

import com.mitrais.atm.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository {

    private static AccountRepository instance;
    List<Account> accounts;

    /**
     * Seed account to list
     */
    private AccountRepository() {
        accounts = new ArrayList<Account>();
        accounts.add(new Account("112233", "012108", "John Doe", 100));
        accounts.add(new Account("112244", "932012", "Jane Doe", 30));
    }

    public static AccountRepository getInstance() {
        if (instance == null) {
            instance = new AccountRepository();
        }
        return instance;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

}
