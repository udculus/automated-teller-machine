package com.mitrais.atm.service;

import com.mitrais.atm.exception.InsufficientBalanceException;
import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Transaction;
import com.mitrais.atm.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionServiceImpl implements TransactionService {

    TransactionRepository transactionRepository;
    List<Transaction> transactions;

    public TransactionServiceImpl() {
        transactionRepository = TransactionRepository.getInstance();
        transactions = transactionRepository.getTransactions();
    }

    /**
     * Transfer fund from logged in account to destination account with inputted amount of value
     * @param account
     * @param receiver
     * @param amount
     * @return
     */
    @Override
    public int transferFund(Account account, Account receiver, String referenceNumber, int amount) throws InsufficientBalanceException {
        int accountBalance = account.getBalance();
        int receiverBalance = receiver.getBalance();

        if (accountBalance < amount) {
            throw new InsufficientBalanceException(amount);
        } else {
            accountBalance -= amount;
            receiverBalance += amount;

            account.setBalance(accountBalance);
            receiver.setBalance(receiverBalance);

            transactions.add(new Transaction(account.getAccountNumber(), receiver.getAccountNumber(), referenceNumber, amount, LocalDateTime.now(), Transaction.Type.TRANSFER ));
        }

        return accountBalance;
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

            transactions.add(new Transaction(account.getAccountNumber(), null, null, amount, LocalDateTime.now(), Transaction.Type.WITHDRAW ));
        }
    }

    @Override
    public List<Transaction> getHistory(Transaction.Type type) throws Exception {
        switch (type) {
            case TRANSFER:
                return transactions.stream().filter(c -> c.getType().equals(Transaction.Type.TRANSFER)).limit(10).collect(Collectors.toList());
            case WITHDRAW:
                return transactions.stream().filter(c -> c.getType().equals(Transaction.Type.WITHDRAW)).limit(10).collect(Collectors.toList());
            default:
                throw new Exception("No transactions has been recorded");
        }
    }

}
