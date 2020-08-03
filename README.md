# ATM - Automated Teller Machine v2.0
> A project built with basic java operation and 'List' as its data simulation

## Requirements
JDK 1.8

## Run Application
```java -jar dist/automated-teller-machine.jar```

## Features
1. Authentication
2. Withdrawal
3. Transfer Funds
4. Transaction History

## Application Demo
Authenticate by using account and pin number below

| Account Number | PIN    | Name        | Balance |
| -------------- | ------ | ----------- | ------- |
| 112233         | 012108 | John Doe    | $100    |
| 112244         | 932012 | Jane Doe    | $30     |

or

Authenticate from provided csv file in the `./file/accounts.csv`. You can input your own account by editing the csv file with the following format:

```name,pin,balance,account number```
