package com.hevlar.accounting.controller;

import com.hevlar.accounting.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ChartOfAccounts {
    private final Map<String, Account> mapAccount;

    public ChartOfAccounts(){
        mapAccount = new HashMap<>();
    }

    public Account getAccount(String name){
        return mapAccount.get(name);
    }

    public Map<String, Account> getCreditCardAccounts(){
        Map<String, Account> result = new HashMap<>();
        mapAccount.values()
                .stream()
                .filter(it -> it.getClass().equals(CreditCardAccount.class))
                .forEach(it -> result.put(it.getName(), it));
        return result;
    }

    public Map<String, Account> getAccounts(AccountGroup accountGroup){
        Map<String, Account> result = new HashMap<>();
        mapAccount.values()
                .stream()
                .filter(it -> it.getAccountGroup().equals(accountGroup))
                .forEach(it -> result.put(it.getName(), it));
        return result;
    }

    public void lock(){
        mapAccount.values().forEach(Account::lock);
    }

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

    public Boolean newFixedAsset(String name, LocalDate openDate, String currency, Double openBal){
        return newAccount(new BalanceSheetAccount(name, AccountGroup.FIXED_ASSETS, openDate, currency, openBal));
    }

    public Boolean newCurrentAsset(String name, LocalDate openDate, String currency, Double openBal){
        return newAccount(new BalanceSheetAccount(name, AccountGroup.CURRENT_ASSETS, openDate, currency, openBal));
    }

    public Boolean newCurrentLiability(String name, LocalDate openDate, String currency, Double openBal){
        return newAccount(new BalanceSheetAccount(name, AccountGroup.CURRENT_LIABILITIES, openDate, currency, openBal));
    }

    public Boolean newCreditCard(String name, LocalDate openDate, String currency, Double openBal, String bank, Integer statementDay, Integer dueDay){
        return newAccount(new CreditCardAccount(name, openDate, currency, openBal, bank, statementDay, dueDay));
    }

    public Boolean newLongTermLiability(String name, LocalDate openDate, String currency, Double openBal){
        return newAccount(new BalanceSheetAccount(name, AccountGroup.LONG_TERM_LIABILITIES, openDate, currency, openBal));
    }

    public Boolean newEquity(String name, LocalDate openDate, String currency, Double openBal){
        return newAccount(new BalanceSheetAccount(name, AccountGroup.EQUITIES, openDate, currency, openBal));
    }

    public Boolean newRevenue(String name){
        return newAccount(new IncomeStatementAccount(name, AccountGroup.REVENUE));
    }

    public Boolean newExpense(String name){
        return newAccount(new IncomeStatementAccount(name, AccountGroup.EXPENSES));
    }

    public Boolean newGain(String name){
        return newAccount(new IncomeStatementAccount(name, AccountGroup.GAINS));
    }

    public Boolean newLoss(String name){
        return newAccount(new IncomeStatementAccount(name, AccountGroup.LOSSES));
    }
}
