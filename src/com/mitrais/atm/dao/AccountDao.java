package com.mitrais.atm.dao;

import com.mitrais.atm.model.Account;

public interface AccountDao {

    public void seedAccounts();

    public Account getAccount(String accountNumber) throws Exception;

    public Account authenticateAccount(String accountNumber, String pin) throws Exception;

}
