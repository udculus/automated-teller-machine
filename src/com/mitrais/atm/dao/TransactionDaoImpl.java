package com.mitrais.atm.dao;

import com.mitrais.atm.exception.InsufficientBalanceException;
import com.mitrais.atm.model.Account;

public class TransactionDaoImpl implements TransactionDao {

    /**
     * Transfer fund from logged in account to destination account with inputted amount of value
     * @param account
     * @param receiver
     * @param amount
     * @return
     */
    @Override
    public int transferFund(Account account, Account receiver, int amount) throws InsufficientBalanceException {
        int accountBalance = account.getBalance();
        int receiverBalance = receiver.getBalance();

        if (accountBalance < amount) {
            throw new InsufficientBalanceException(amount);
        } else {
            accountBalance -= amount;
            receiverBalance += amount;

            account.setBalance(accountBalance);
            receiver.setBalance(receiverBalance);
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
        }
    }

}
