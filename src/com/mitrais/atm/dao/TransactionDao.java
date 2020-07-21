package com.mitrais.atm.dao;

import com.mitrais.atm.exception.InsufficientBalanceException;
import com.mitrais.atm.model.Account;

public interface TransactionDao {

    public int transferFund(Account account, Account receiver, int amount) throws InsufficientBalanceException;

    public void withdraw(Account account, int amount) throws InsufficientBalanceException;

}
