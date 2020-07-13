package com.mitrais.atm.validation;

import com.mitrais.atm.model.Validation;

public class LoginValidation {

    /**
     * Validate login account number and pin number
     * @param value
     * @param label
     * @return
     */
    public static Validation validateLoginFields(String value, String label) {
        Validation validation = new Validation();

        if (value.length() != 6) {
            validation.setMessage(label + " should have 6 digits length");
            validation.setValid(false);
        } else if (!value.matches("[0-9]+")) {
            validation.setMessage(label + " should only contains numbers");
            validation.setValid(false);
        }

        return validation;
    }

}
