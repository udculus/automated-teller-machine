package com.mitrais.atm.validation;

import com.mitrais.atm.model.Validation;

public class WithdrawValidation {

    /**
     * Validate amount to withdraw field
     * @param amount
     * @return
     */
    public static void validateWithdrawalField(String amount) throws Exception {
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

        if (!validation.isValid()) throw new Exception(validation.getMessage());
    }
}
