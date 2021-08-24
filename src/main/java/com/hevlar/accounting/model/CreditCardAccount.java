package com.hevlar.accounting.model;

import java.time.LocalDate;

public class CreditCardAccount extends BalanceSheetAccount{

    private String bank;
    private Integer statementDay;
    private Integer dueDay;

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

    public String getBank() {
        return bank;
    }

    public Boolean setBank(String bank) {
        if(this.isLocked()) return false;
        this.bank = bank;
        return true;
    }

    public Integer getStatementDay() {
        return statementDay;
    }

    public Boolean setStatementDay(Integer statementDay) {
        if(this.isLocked()) return false;
        if(!validDay(statementDay)) return false;
        this.statementDay = statementDay;
        return true;
    }

    public Integer getDueDay() {
        return dueDay;
    }

    public Boolean setDueDay(Integer dueDay) {
        if(this.isLocked()) return false;
        if(!validDay(dueDay)) return false;
        this.dueDay = dueDay;
        return true;
    }

}
