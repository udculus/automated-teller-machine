package com.mitrais.atm.dao;

import com.mitrais.atm.exception.InsufficientBalanceException;
import com.mitrais.atm.model.Account;

import java.util.List;

public interface AccountDao {

    public void seedAccounts();

    public Account getAccount(String accountNumber);

    public Account getAccount(String accountNumber, String pin);

    public int transferFund(Account account, Account receiver, int amount);

    public void withdraw(Account account, int amount) throws InsufficientBalanceException;

}
