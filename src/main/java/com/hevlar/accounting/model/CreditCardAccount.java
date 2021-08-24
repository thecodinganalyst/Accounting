package com.hevlar.accounting.model;

import java.time.LocalDate;

/**
 * Represents a credit card account, which is defaulted as a current liability, with information of bank, statement day
 * and due day, so that the statement reports and payment due alerts can be triggered.
 */
public class CreditCardAccount extends BalanceSheetAccount{

    private String bank;
    private Integer statementDay;
    private Integer dueDay;

    /**
     * Default construct to create a new credit card account
     * @param name name of the credit card
     * @param openDate opening date
     * @param currency currency - the ISO 4217 code of the currency, eg. SGD, USD
     * @param openBal opening balance
     * @param bank bank name
     * @param statementDay day in month when the statement is generated
     * @param dueDay day in month when the statement is due for payment
     */
    public CreditCardAccount(String name, LocalDate openDate, String currency, Double openBal, String bank, Integer statementDay, Integer dueDay) {
        super(name, AccountGroup.CURRENT_LIABILITIES, openDate, currency, openBal);

        if(!validDay(statementDay)) throw new IllegalArgumentException("Statement day must be between 1 and 31");
        if(!validDay(dueDay)) throw new IllegalArgumentException("Due day must be between 1 and 31");

        this.bank = bank;
        this.statementDay = statementDay;
        this.dueDay = dueDay;
    }

    private Boolean validDay(Integer day){
        return day >= 1 && day <= 31;
    }

    /**
     * Gets the name of the bank which issues this credit card
     * @return bank name
     */
    public String getBank() {
        return bank;
    }

    /**
     * Sets the bank name, only allowed if the account is not locked
     * @param bank bank name
     * @return true if successful, false if account is locked
     */
    public Boolean setBank(String bank) {
        if(this.isLocked()) return false;
        this.bank = bank;
        return true;
    }

    /**
     * Get day of month when the statement will be generated
     * @return statement day
     */
    public Integer getStatementDay() {
        return statementDay;
    }

    /**
     * Sets the new statement day, allowed values are between 1 and 31, inclusive
     * This operation is allowed even if the account is locked
     * @param statementDay new statement day
     * @return true, if successful, false if the parameter is invalid
     */
    public Boolean setStatementDay(Integer statementDay) {
        if(!validDay(statementDay)) return false;
        this.statementDay = statementDay;
        return true;
    }

    /**
     * Get day of month when the credit card is due for payment
     * @return due day
     */
    public Integer getDueDay() {
        return dueDay;
    }

    /**
     * Sets the new due day, allowed values are between 1 and 31, inclusive
     * This operation is allowed even if the account is locked
     * @param dueDay new due day
     * @return true, if successful, false if the parameter is invalid
     */
    public Boolean setDueDay(Integer dueDay) {
        if(!validDay(dueDay)) return false;
        this.dueDay = dueDay;
        return true;
    }

}
