package com.hevlar.accounting.repository;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity object for persistence of Accounts, including IncomeStatementAccount, BalanceSheetAccount and CreditCardAccount
 */
@Entity
public class AccountData {

    @Id
    private String name;
    private String accountGroup;
    private LocalDate openDate;
    private String currency;
    private BigDecimal openBal;
    private String bank;
    private Integer statementDay;
    private Integer dueDay;
    private Boolean lock;

    public AccountData(String name, String accountGroup, Boolean lock) {
        this.name = name;
        this.accountGroup = accountGroup;
        this.lock = lock;
    }

    public AccountData(String name, String accountGroup, LocalDate openDate, String currency, BigDecimal openBal, Boolean lock) {
        this.name = name;
        this.accountGroup = accountGroup;
        this.openDate = openDate;
        this.currency = currency;
        this.openBal = openBal;
        this.lock = lock;
    }

    public AccountData(String name, String accountGroup, LocalDate openDate, String currency, BigDecimal openBal, String bank, Integer statementDay, Integer dueDay, Boolean lock) {
        this.name = name;
        this.accountGroup = accountGroup;
        this.openDate = openDate;
        this.currency = currency;
        this.openBal = openBal;
        this.bank = bank;
        this.statementDay = statementDay;
        this.dueDay = dueDay;
        this.lock = lock;
    }

    public AccountData() {

    }

    public Boolean isLocked() {
        return lock;
    }

    public String getBank() {
        return bank;
    }

    public Integer getStatementDay() {
        return statementDay;
    }

    public Integer getDueDay() {
        return dueDay;
    }

    public String getName(){
        return name;
    }

    public String getAccountGroup() {
        return accountGroup;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getOpenBal() {
        return openBal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccountGroup(String accountGroup) {
        this.accountGroup = accountGroup;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setOpenBal(BigDecimal openBal) {
        this.openBal = openBal;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setStatementDay(Integer statementDay) {
        this.statementDay = statementDay;
    }

    public void setDueDay(Integer dueDay) {
        this.dueDay = dueDay;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }
}
