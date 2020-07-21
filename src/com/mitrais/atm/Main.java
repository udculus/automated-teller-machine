package com.mitrais.atm;

import com.mitrais.atm.dao.AccountDao;
import com.mitrais.atm.dao.AccountDaoImpl;
import com.mitrais.atm.screen.LoginScreen;

public class Main {

    /**
     * Seed account and show login screen
     * @param args
     */
    public static void main(String[] args) {
        AccountDao accountDao = new AccountDaoImpl();
        LoginScreen loginScreen = new LoginScreen(accountDao);

        accountDao.seedAccounts();
        loginScreen.show();
    }
}
