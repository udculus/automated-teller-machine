package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;

public interface AccountService {

    public Account getAccount(String accountNumber) throws Exception;

    public Account authenticateAccount(String accountNumber, String pin) throws Exception;

    public void seedAccounts(String path) throws Exception;

}
