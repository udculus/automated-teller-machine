package com.mitrais.atm.repository;

import com.mitrais.atm.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    private static TransactionRepository instance;
    List<Transaction> transactions = new ArrayList<Transaction>();

    public static TransactionRepository getInstance() {
        if (instance == null) {
            instance = new TransactionRepository();
        }
        return instance;
    }

    public List<Transaction> getTransactions() { return transactions; }

}
