package com.hevlar.accounting.controller;

import com.hevlar.accounting.model.Account;
import com.hevlar.accounting.model.AccountGroup;

import java.time.LocalDate;
import java.util.Map;

public interface IChartOfAccounts {

    /**
     * Gets the account by name
     * @param name name of account to be retrieved
     * @return Account
     */
    Account getAccount(String name);

    /**
     * Gets a hashmap of the credit card accounts available
     * @return hashmap of credit card accounts
     */
    Map<String, Account> getCreditCardAccounts();

    /**
     * Get hashmap of accounts by the account group
     * @param accountGroup account group - eg. Current Assets, Revenue, etc
     * @return hashmap of accounts
     */
    Map<String, Account> getAccounts(AccountGroup accountGroup);

    /**
     * lock all the accounts available
     */
    void lock();

    /**
     * Delete account by name, only allowed if the account is not locked
     * @param name name of the account to be deleted
     * @return true if successful, false if the operation failed (not found, locked)
     */
    Boolean deleteAccount(String name);

    /**
     * Creates a new fixed asset account
     * @param name name of account
     * @param openDate opening date
     * @param currency ISO 4217 currency code - SGD, USD
     * @param openBal opening balance
     * @return true if successful, false otherwise
     */
    Boolean newFixedAsset(String name, LocalDate openDate, String currency, Double openBal);

    /**
     * Creates a new current asset account
     * @param name name of account
     * @param openDate opening date
     * @param currency ISO 4217 currency code - SGD, USD
     * @param openBal opening balance
     * @return true if successful, false otherwise
     */
    Boolean newCurrentAsset(String name, LocalDate openDate, String currency, Double openBal);

    /**
     * Creates a new current liability account
     * @param name name of account
     * @param openDate opening date
     * @param currency ISO 4217 currency code - SGD, USD
     * @param openBal opening balance
     * @return true if successful, false otherwise
     */
    Boolean newCurrentLiability(String name, LocalDate openDate, String currency, Double openBal);

    /**
     * Creates a new credit card account
     * @param name name of account
     * @param openDate opening date
     * @param currency ISO 4217 currency code - SGD, USD
     * @param openBal opening balance
     * @param bank name of issuing bank
     * @param statementDay day of month when statement is generated
     * @param dueDay day of month when statement is due for payment
     * @return true if successful, false otherwise
     */
    Boolean newCreditCard(String name, LocalDate openDate, String currency, Double openBal, String bank, Integer statementDay, Integer dueDay);

    /**
     * Creates a new long term liability account
     * @param name name of account
     * @param openDate opening date
     * @param currency ISO 4217 currency code - SGD, USD
     * @param openBal opening balance
     * @return true if successful, false otherwise
     */
    Boolean newLongTermLiability(String name, LocalDate openDate, String currency, Double openBal);

    /**
     * Creates a new equity account
     * @param name name of account
     * @param openDate opening date
     * @param currency ISO 4217 currency code - SGD, USD
     * @param openBal opening balance
     * @return true if successful, false otherwise
     */
    Boolean newEquity(String name, LocalDate openDate, String currency, Double openBal);

    /**
     * Creates a new revenue account
     * @param name name of account
     * @return true if successful, false otherwise
     */
    Boolean newRevenue(String name);

    /**
     * Creates a new expense account
     * @param name name of account
     * @return true if successful, false otherwise
     */
    Boolean newExpense(String name);

    /**
     * Creates a new gains account
     * @param name name of account
     * @return true if successful, false otherwise
     */
    Boolean newGain(String name);

    /**
     * Creates a new losses account
     * @param name name of account
     * @return true if successful, false otherwise
     */
    Boolean newLoss(String name);
}
