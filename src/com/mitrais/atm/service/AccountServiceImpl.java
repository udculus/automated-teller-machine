package com.mitrais.atm.service;

import com.mitrais.atm.model.Account;
import com.mitrais.atm.repository.AccountRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;
    List<Account> accounts;

    public AccountServiceImpl() {
        accountRepository = AccountRepository.getInstance();
        accounts = accountRepository.getAccounts();
    }

    /**
     * Get account by account number
     * @param accountNumber
     * @return
     */
    @Override
    public Account getAccount(String accountNumber) throws Exception {
        return accounts.stream()
                .filter(account -> accountNumber.equals(account.getAccountNumber()))
                .findAny()
                .orElseThrow(() -> new Exception("Invalid Account"));
    }

    /**
     * Get account by account number for authentication only
     * @param accountNumber
     * @param pin
     * @return
     */
    @Override
    public Account authenticateAccount(String accountNumber, String pin) throws Exception {
        return accounts.stream()
                .filter(account -> accountNumber.equals(account.getAccountNumber()) && pin.equals(account.getPin()))
                .findAny()
                .orElseThrow(() -> new Exception("Invalid Account Number/PIN"));
    }

    /**
     * Seed accounts data by provided path or list
     * @param path
     */
    @Override
    public void seedAccounts(String path) throws Exception {
        if (!path.equals("")) {
            Set<String> uniqueAccounts = new HashSet<String>();

            try (Stream<String> streamData = Files.lines(Paths.get(path))) {
                List<String> datas = streamData.filter(line -> !line.startsWith("Name")).collect(Collectors.toList());
                for(int i=0; i < datas.size(); i++) {
                    String cells[] = datas.get(i).split(",");
                    if (uniqueAccounts.contains(cells[3])) {
                        System.out.println("Duplicated account number: " + cells[3]);
                    } else {
                        uniqueAccounts.add(cells[3]);
                        accounts.add(new Account(cells[3], cells[1], cells[0], Integer.valueOf(cells[2])));
                    }
                }
            } catch (IOException e) {
                throw new Exception("Invalid csv content");
            }

            accountRepository.seedFromData(accounts);
        } else {
            accountRepository.seedFromList();
        }
    }
}
