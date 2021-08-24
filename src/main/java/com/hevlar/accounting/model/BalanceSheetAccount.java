package com.hevlar.accounting.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public class BalanceSheetAccount extends Account{

    private LocalDate openDate;
    private Currency currency;
    private BigDecimal openBal;

    public BalanceSheetAccount(String name, AccountGroup accountGroup, LocalDate openDate, String currency, Double openBal) {
        super(name, accountGroup);
        this.openDate = openDate;
        this.currency = Currency.getInstance(currency);
        this.openBal = new BigDecimal(openBal);
    }

    public BalanceSheetAccount(String name, AccountGroup accountGroup, LocalDate openDate, String currency, BigDecimal openBal) {
        super(name, accountGroup);
        this.openDate = openDate;
        this.currency = Currency.getInstance(currency);
        this.openBal = openBal;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public Boolean setOpenDate(LocalDate openDate) {
        if(this.isLocked()) return Boolean.FALSE;
        this.openDate = openDate;
        return Boolean.TRUE;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Boolean setCurrency(String currency) {
        if(this.isLocked()) return Boolean.FALSE;
        this.currency = Currency.getInstance(currency);
        return Boolean.TRUE;
    }

    public BigDecimal getOpenBal() {
        return openBal;
    }

    public Boolean setOpenBal(double openBal) {
        if(this.isLocked()) return Boolean.FALSE;
        this.openBal = new BigDecimal(openBal);
        return Boolean.TRUE;
    }

}
