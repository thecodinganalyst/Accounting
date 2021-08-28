package com.hevlar.accounting.util;

import com.hevlar.accounting.model.*;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDate;

public class AccountBuilder {

    private String name;
    private String accountGroup;
    private LocalDate openDate;
    private String currency;
    private BigDecimal openBal;
    private String bank;
    private Integer statementDay;
    private Integer dueDay;
    private Boolean lock;

    public AccountBuilder(){
    }

    public Account build() throws InvalidParameterException {
        AccountType accountType = AccountGroup.getAccountTypeFromLabel(accountGroup);
        if(accountType == AccountType.BalanceSheet){
            return buildBalanceSheetAccount();
        }else if(accountType == AccountType.IncomeStatement){
            return buildIncomeStatementAccount();
        }
        throw new InvalidParameterException("Unable to determine account group for " + accountGroup);
    }

    private IncomeStatementAccount buildIncomeStatementAccount(){
        return new IncomeStatementAccount(name, AccountGroup.fromLabel(accountGroup), lock);
    }

    private CreditCardAccount buildCreditCardAccount(){
        return new CreditCardAccount(
                name,
                openDate,
                currency,
                openBal,
                bank,
                statementDay,
                dueDay,
                lock
        );
    }

    private BalanceSheetAccount buildBalanceSheetAccount(){
        return bank != null ? buildCreditCardAccount() :
                new BalanceSheetAccount(
                    name,
                    AccountGroup.fromLabel(accountGroup),
                    openDate,
                    currency,
                    openBal,
                    lock
                );
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
