package com.mitrais.atm.dao;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.repository.AccountRepository;

import java.util.List;

public class AccountDaoImpl implements AccountDao {

    List<Account> accounts;

    public AccountDaoImpl() {
        accounts = AccountRepository.getInstance().getAccounts();
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
