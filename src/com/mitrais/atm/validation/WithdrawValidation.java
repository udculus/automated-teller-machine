package com.mitrais.atm.validation;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Validation;

public class WithdrawValidation {

    /**
     * Validate amount to withdraw field
     * @param amount
     * @return
     */
    public static Validation validateWithdrawalField(String amount) {
        Validation validation = new Validation();

        if (amount.matches("[0-9]+")) {
            int amountValue = Integer.valueOf(amount);
            if (amountValue % 10 != 0) {
                validation.setMessage("Invalid amount");
                validation.setValid(false);
            }
        } else {
            validation.setMessage("Invalid amount");
            validation.setValid(false);
        }
        return validation;
    }

    /**
     * Validate account balance before withdrawal
     * @param account
     * @param amount
     * @return
     */
    public static Validation validateBalance(Account account, int amount) {
        Validation validation = new Validation();
        int currentBalance = account.getBalance();

        if (currentBalance < amount) {
            validation.setMessage("Insufficient balance $" + amount);
            validation.setValid(false);
        }
        return validation;
    }
}
