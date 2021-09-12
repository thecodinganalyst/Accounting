package com.hevlar.accounting.service;

import com.hevlar.accounting.model.AccountGroup;
import com.hevlar.accounting.model.BalanceSheetAccount;
import com.hevlar.accounting.model.IncomeStatementAccount;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChartOfAccountsIT {

    @Autowired
    private ChartOfAccounts chartOfAccounts;

    @Test
    void getAccount_successful(){
        chartOfAccounts.newExpense("Food");
        IncomeStatementAccount food = (IncomeStatementAccount) chartOfAccounts.getAccount("Food");
        assertNotNull(food);
        assertEquals(food.getName(), "Food");
        assertEquals(food.getAccountGroup(), AccountGroup.EXPENSES);
        assertFalse(food.isLocked());
    }

    @Test
    void getAccount_returns_null_when_not_found(){
        assertNull(chartOfAccounts.getAccount("NonExistent"));
    }

    @Test
    void ensure_account_is_deleted_successfully(){
        chartOfAccounts.newExpense("Grocery");
        assertTrue(chartOfAccounts.deleteAccount("Grocery"));
        assertNull(chartOfAccounts.getAccount("Grocery"));
    }

    @Test
    void ensure_locked_accounts_cannot_be_edited(){
        chartOfAccounts.newCurrentAsset("Bank", LocalDate.of(2021, 1, 1), "SGD", "100.0");
        chartOfAccounts.lock();
        BalanceSheetAccount bank = new BalanceSheetAccount("Bank", AccountGroup.CURRENT_ASSETS, LocalDate.of(2021,1, 2), "SGD", "200.0", false);
        assertNull(chartOfAccounts.updateAccount(bank));
    }

    @Test
    void ensure_locked_accounts_cannot_be_deleted(){
        chartOfAccounts.newCurrentAsset("Bank 2", LocalDate.of(2021, 1, 1), "SGD", "100.0");
        BalanceSheetAccount bank2 = (BalanceSheetAccount) chartOfAccounts.getAccount("Bank 2");
        bank2.lock();
        chartOfAccounts.updateAccount(bank2);
        assertTrue(bank2.isLocked());
        assertFalse(chartOfAccounts.deleteAccount(bank2.getName()));
    }

}
