package com.mitrais.atm.validation;

import com.mitrais.atm.model.Validation;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileValidation {

    public static void validateSourceFile(String path) throws Exception {
        Validation validation = new Validation();

        if (path.equals("")){
            return;
        }

        if (!Files.exists(Paths.get(path))) {
            validation.setMessage("Invalid specified file path");
            validation.setValid(false);
        }

        if (!validation.isValid()) throw new Exception(validation.getMessage());
    }
}
