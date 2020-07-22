package com.mitrais.atm.model;

import java.time.LocalDateTime;

public class Transaction {

    private String accountNumber;
    private String destinationNumber;
    private int amount;
    private String referenceNumber;
    private LocalDateTime time;

    public Transaction(String accountNumber, String destinationNumber, String referenceNumber, int amount, LocalDateTime time) {
        this.accountNumber = accountNumber;
        this.destinationNumber = destinationNumber;
        this.amount = amount;
        this.referenceNumber = referenceNumber;
        this.time = time;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getDestinationNumber() {
        return destinationNumber;
    }

    public void setDestinationNumber(String destinationNumber) {
        this.destinationNumber = destinationNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
