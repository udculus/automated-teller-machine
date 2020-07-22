package com.mitrais.atm.validation;

import com.mitrais.atm.model.Validation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountValidation {

    public static void validateSourceFile(String path) throws Exception {
        Validation validation = new Validation();

        if (path.equals(""))
            return;

        if (!Files.exists(Paths.get(path))) {
            validation.setMessage("Invalid specified file path");
            validation.setValid(false);
        } else {
            Set<String> uniqueAccounts = new HashSet<String>();

            try (Stream<String> streamData = Files.lines(Paths.get(path))) {
                List<String> datas = streamData.filter(line -> !line.startsWith("Name")).collect(Collectors.toList());
                for(int i=0; i < datas.size(); i++) {
                    String cells[] = datas.get(i).split(",");
                    if (uniqueAccounts.contains(cells[3])) {
                        validation.setMessage("Duplicated account number: " + cells[3]);
                        validation.setValid(false);
                        break;
                    } else {
                        uniqueAccounts.add(cells[3]);
                    }
                }
            } catch (IOException e) {
                validation.setMessage("Invalid csv content");
                validation.setValid(false);
            }
        }

        if (!validation.isValid()) throw new Exception(validation.getMessage());
    }
}
