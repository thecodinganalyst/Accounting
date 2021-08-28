package com.hevlar.accounting.util;

import com.hevlar.accounting.model.*;
import com.hevlar.accounting.repository.AccountData;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class ModelMappingTest {

    @Test
    void toAccount_IncomeStatementAccount() {
        AccountData accountData = new AccountData("Expense", AccountGroup.EXPENSES.label, false);
        Account account = ModelMapping.toAccount(accountData);
        assertEquals(account.getClass(), IncomeStatementAccount.class);
        assertEquals(account.getName(), "Expense");
        assertEquals(account.getAccountGroup(), AccountGroup.EXPENSES);
    }

    @Test
    void toAccount_BalanceSheetAccount() {
        AccountData accountData = new AccountData("Bank", AccountGroup.CURRENT_ASSETS.label, LocalDate.of(2021,1,1), "SGD", new BigDecimal("100.0"), false);
        Account account = ModelMapping.toAccount(accountData);
        assertEquals(account.getClass(), BalanceSheetAccount.class);
        BalanceSheetAccount balanceSheetAccount = (BalanceSheetAccount) account;
        assertEquals(balanceSheetAccount.getName(), "Bank");
        assertEquals(balanceSheetAccount.getAccountGroup(), AccountGroup.CURRENT_ASSETS);
        assertEquals(balanceSheetAccount.getOpenDate(), LocalDate.of(2021,1,1));
        assertEquals(balanceSheetAccount.getCurrency(), Currency.getInstance("SGD"));
        assertEquals(balanceSheetAccount.getOpenBal(), new BigDecimal("100.0"));
    }

    @Test
    void toAccount_CreditCardAccount() {
        AccountData accountData = new AccountData("Credit Card", AccountGroup.CURRENT_LIABILITIES.label, LocalDate.of(2021,1,1), "SGD", new BigDecimal("100.0"), "Bank A", 1, 12, false);
        Account account = ModelMapping.toAccount(accountData);
        assertEquals(account.getClass(), CreditCardAccount.class);
        CreditCardAccount creditCardAccount = (CreditCardAccount) account;
        assertEquals(creditCardAccount.getName(), "Credit Card");
        assertEquals(creditCardAccount.getAccountGroup(), AccountGroup.CURRENT_LIABILITIES);
        assertEquals(creditCardAccount.getOpenDate(), LocalDate.of(2021,1,1));
        assertEquals(creditCardAccount.getCurrency(), Currency.getInstance("SGD"));
        assertEquals(creditCardAccount.getOpenBal(), new BigDecimal("100.0"));
        assertEquals(creditCardAccount.getBank(), "Bank A");
        assertEquals(creditCardAccount.getStatementDay(), 1);
        assertEquals(creditCardAccount.getDueDay(), 12);
    }
}