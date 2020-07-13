package com.mitrais.atm.validation;

import com.mitrais.atm.dao.AccountDaoImpl;
import com.mitrais.atm.model.Account;
import com.mitrais.atm.model.Validation;

public class TransferValidation {

    /**
     * Validate account field and availability of the account
     * @param accountNumber
     * @return
     */
    public static Validation validateDestinationAccountField(String accountNumber) {
        Validation validation = new Validation();

        if (accountNumber.matches("[0-9]+")) {
            if (AccountDaoImpl.getInstance().getAccount(accountNumber) == null) {
                validation.setMessage("Invalid account");
                validation.setValid(false);
            }
        } else {
            validation.setMessage("Invalid account");
            validation.setValid(false);
        }
        return validation;
    }

    /**
     * Validate amount to transfer field and current account balance
     * @param account
     * @param amount
     * @return
     */
    public static Validation validateTransferAmountField(Account account, String amount) {
        Validation validation = new Validation();

        if (amount.matches("[0-9]+")) {
            int amountValue = Integer.valueOf(amount);

            if (amountValue < 1) {
                validation.setMessage("Minimum amount to transfer is $1");
                validation.setValid(false);
            } else if (amountValue > 1000) {
                validation.setMessage("Maximum amount to transfer is $1000");
                validation.setValid(false);
            } else if (account.getBalance() < amountValue) {
                validation.setMessage("Insufficient balance $" + amount);
                validation.setValid(false);
            }
        } else {
            validation.setMessage("Invalid amount");
            validation.setValid(false);
        }
        return validation;
    }
}
