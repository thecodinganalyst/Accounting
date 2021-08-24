package com.hevlar.accounting.controller;

import com.hevlar.accounting.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the chart of accounts in accounting, which is a list of all the accounts available.
 */
public class ChartOfAccounts {
    private final Map<String, Account> mapAccount;

    /**
     * Default constructor
     */
    public ChartOfAccounts(){
        mapAccount = new HashMap<>();
    }

    /**
     * Gets the account by name
     * @param name name of account to be retrieved
     * @return Account
     */
    public Account getAccount(String name){
        return mapAccount.get(name);
    }

    /**
     * Gets a hashmap of the credit card accounts available
     * @return hashmap of credit card accounts
     */
    public Map<String, Account> getCreditCardAccounts(){
        Map<String, Account> result = new HashMap<>();
        mapAccount.values()
                .stream()
                .filter(it -> it.getClass().equals(CreditCardAccount.class))
                .forEach(it -> result.put(it.getName(), it));
        return result;
    }

    /**
     * Get hashmap of accounts by the account group
     * @param accountGroup account group - eg. Current Assets, Revenue, etc
     * @return hashmap of accounts
     */
    public Map<String, Account> getAccounts(AccountGroup accountGroup){
        Map<String, Account> result = new HashMap<>();
        mapAccount.values()
                .stream()
                .filter(it -> it.getAccountGroup().equals(accountGroup))
                .forEach(it -> result.put(it.getName(), it));
        return result;
    }

    /**
     * lock all the accounts available
     */
    public void lock(){
        mapAccount.values().forEach(Account::lock);
    }

    /**
     * Delete account by name, only allowed if the account is not locked
     * @param name name of the account to be deleted
     * @return true if successful, false if the operation failed (not found, locked)
     */
    public Boolean deleteAccount(String name){
        if(!mapAccount.containsKey(name) || mapAccount.get(name).isLocked()) {
            return Boolean.FALSE;
        }
        mapAccount.remove(name);
        return Boolean.TRUE;
    }

    private Boolean newAccount(Account account){
        if(mapAccount.containsKey(account.getName())) return Boolean.FALSE;
        mapAccount.put(account.getName(), account);
        return Boolean.TRUE;
    }

    /**
     * Creates a new fixed asset account
     * @param name name of account
     * @param openDate opening date
     * @param currency ISO 4217 currency code - SGD, USD
     * @param openBal opening balance
     * @return true if successful, false otherwise
     */
    public Boolean newFixedAsset(String name, LocalDate openDate, String currency, Double openBal){
        return newAccount(new BalanceSheetAccount(name, AccountGroup.FIXED_ASSETS, openDate, currency, openBal));
    }

    /**
     * Creates a new current asset account
     * @param name name of account
     * @param openDate opening date
     * @param currency ISO 4217 currency code - SGD, USD
     * @param openBal opening balance
     * @return true if successful, false otherwise
     */
    public Boolean newCurrentAsset(String name, LocalDate openDate, String currency, Double openBal){
        return newAccount(new BalanceSheetAccount(name, AccountGroup.CURRENT_ASSETS, openDate, currency, openBal));
    }

    /**
     * Creates a new current liability account
     * @param name name of account
     * @param openDate opening date
     * @param currency ISO 4217 currency code - SGD, USD
     * @param openBal opening balance
     * @return true if successful, false otherwise
     */
    public Boolean newCurrentLiability(String name, LocalDate openDate, String currency, Double openBal){
        return newAccount(new BalanceSheetAccount(name, AccountGroup.CURRENT_LIABILITIES, openDate, currency, openBal));
    }

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
    public Boolean newCreditCard(String name, LocalDate openDate, String currency, Double openBal, String bank, Integer statementDay, Integer dueDay){
        return newAccount(new CreditCardAccount(name, openDate, currency, openBal, bank, statementDay, dueDay));
    }

    /**
     * Creates a new long term liability account
     * @param name name of account
     * @param openDate opening date
     * @param currency ISO 4217 currency code - SGD, USD
     * @param openBal opening balance
     * @return true if successful, false otherwise
     */
    public Boolean newLongTermLiability(String name, LocalDate openDate, String currency, Double openBal){
        return newAccount(new BalanceSheetAccount(name, AccountGroup.LONG_TERM_LIABILITIES, openDate, currency, openBal));
    }

    /**
     * Creates a new equity account
     * @param name name of account
     * @param openDate opening date
     * @param currency ISO 4217 currency code - SGD, USD
     * @param openBal opening balance
     * @return true if successful, false otherwise
     */
    public Boolean newEquity(String name, LocalDate openDate, String currency, Double openBal){
        return newAccount(new BalanceSheetAccount(name, AccountGroup.EQUITIES, openDate, currency, openBal));
    }

    /**
     * Creates a new revenue account
     * @param name name of account
     * @return true if successful, false otherwise
     */
    public Boolean newRevenue(String name){
        return newAccount(new IncomeStatementAccount(name, AccountGroup.REVENUE));
    }

    /**
     * Creates a new expense account
     * @param name name of account
     * @return true if successful, false otherwise
     */
    public Boolean newExpense(String name){
        return newAccount(new IncomeStatementAccount(name, AccountGroup.EXPENSES));
    }

    /**
     * Creates a new gains account
     * @param name name of account
     * @return true if successful, false otherwise
     */
    public Boolean newGain(String name){
        return newAccount(new IncomeStatementAccount(name, AccountGroup.GAINS));
    }

    /**
     * Creates a new losses account
     * @param name name of account
     * @return true if successful, false otherwise
     */
    public Boolean newLoss(String name){
        return newAccount(new IncomeStatementAccount(name, AccountGroup.LOSSES));
    }
}
