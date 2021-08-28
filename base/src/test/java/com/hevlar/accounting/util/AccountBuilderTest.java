package com.hevlar.accounting.util;

import com.hevlar.accounting.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AccountBuilderTest {

    @Test
    void build_IncomeStatementAccount() {
        AccountBuilder builder = new AccountBuilder();
        builder.setName("Food");
        builder.setAccountGroup(AccountGroup.EXPENSES.label);
        builder.setLock(false);
        Account account = builder.build();
        assertEquals(account.getClass(), IncomeStatementAccount.class);
    }

    @Test
    void build_BalanceSheetAccount(){
        AccountBuilder builder = new AccountBuilder();
        builder.setName("Bank");
        builder.setAccountGroup(AccountGroup.CURRENT_ASSETS.label);
        builder.setOpenDate(LocalDate.now());
        builder.setCurrency("SGD");
        builder.setOpenBal(new BigDecimal("100"));
        builder.setLock(false);
        Account account = builder.build();
        assertEquals(account.getClass(), BalanceSheetAccount.class);
    }

    @Test
    void build_CreditCardAccount(){
        AccountBuilder builder = new AccountBuilder();
        builder.setName("Credit Card");
        builder.setAccountGroup(AccountGroup.CURRENT_LIABILITIES.label);
        builder.setOpenDate(LocalDate.now());
        builder.setCurrency("SGD");
        builder.setOpenBal(new BigDecimal("0"));
        builder.setBank("Bank A");
        builder.setStatementDay(1);
        builder.setDueDay(12);
        builder.setLock(false);
        Account account = builder.build();
        assertEquals(account.getClass(), CreditCardAccount.class);
    }
}