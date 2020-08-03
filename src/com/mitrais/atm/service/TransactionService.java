package com.mitrais.atm.service;

import com.mitrais.atm.exception.InsufficientBalanceException;
import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;

import java.util.List;

public interface TransactionService {

    public int transferFund(Account account, Account receiver, String referenceNumber, int amount) throws InsufficientBalanceException;

    public void withdraw(Account account, int amount) throws InsufficientBalanceException;

    public List<Transaction> getHistory() throws Exception;

}
