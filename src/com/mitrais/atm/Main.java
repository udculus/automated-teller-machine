package com.mitrais.atm;

import com.mitrais.atm.screen.LoginScreen;

public class Main {

    /**
     * Seed account and show login screen
     * @param args
     */
    public static void main(String[] args) {
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.show();
    }
}
