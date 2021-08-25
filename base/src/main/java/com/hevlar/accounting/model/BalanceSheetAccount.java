package com.hevlar.accounting.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

/**
 * Represents an account that is used in the balance sheet report.
 */
public class BalanceSheetAccount extends Account{

    private LocalDate openDate;
    private Currency currency;
    private BigDecimal openBal;

    /**
     * Constructor, using Double for opening balance
     * @param name name of the account
     * @param accountGroup account group - Current Assets, Current Liabilities, Revenue, Expenses, etc
     * @param openDate date when this account is opened
     * @param currency ISO 4217 code of the currency to be associated with this account
     * @param openBal opening balance
     */
    public BalanceSheetAccount(String name, AccountGroup accountGroup, LocalDate openDate, String currency, Double openBal) {
        super(name, accountGroup);
        this.openDate = openDate;
        this.currency = Currency.getInstance(currency);
        this.openBal = new BigDecimal(openBal);
    }

    /**
     * Constructor, using the original BigDecimal for opening balance
     * @param name name of the account
     * @param accountGroup account group - Current Assets, Current Liabilities, Revenue, Expenses, etc
     * @param openDate date when this account is opened
     * @param currency ISO 4217 code of the currency to be associated with this account
     * @param openBal opening balance
     */
    public BalanceSheetAccount(String name, AccountGroup accountGroup, LocalDate openDate, String currency, BigDecimal openBal) {
        super(name, accountGroup);
        this.openDate = openDate;
        this.currency = Currency.getInstance(currency);
        this.openBal = openBal;
    }

    /**
     * Gets the opening date of this account
     * @return opening date
     */
    public LocalDate getOpenDate() {
        return openDate;
    }

    /**
     * Edit the opening date of this account, only permissible if the account is not locked
     * @param openDate new opening date
     * @return true, if successful, false if the account is already locked
     */
    public Boolean setOpenDate(LocalDate openDate) {
        if(this.isLocked()) return Boolean.FALSE;
        this.openDate = openDate;
        return Boolean.TRUE;
    }

    /**
     * Gets the currency of this account
     * @return currency
     */
    public Currency getCurrency() {
        return currency;
    }

    /**
     * Sets the currency of this account
     * @param currency the ISO 4217 code of the currency, eg. SGD, USD
     * @return true, if successful, false if the account is already locked
     */
    public Boolean setCurrency(String currency) {
        if(this.isLocked()) return Boolean.FALSE;
        this.currency = Currency.getInstance(currency);
        return Boolean.TRUE;
    }

    /**
     * Gets the opening balance of the account
     * @return opening balance
     */
    public BigDecimal getOpenBal() {
        return openBal;
    }

    /**
     * Sets the opening balance of the account, only permissible if the account is not locked
     * @param openBal new opening balance
     * @return true if successful, false if account is already locked
     */
    public Boolean setOpenBal(double openBal) {
        if(this.isLocked()) return Boolean.FALSE;
        this.openBal = new BigDecimal(openBal);
        return Boolean.TRUE;
    }

}
