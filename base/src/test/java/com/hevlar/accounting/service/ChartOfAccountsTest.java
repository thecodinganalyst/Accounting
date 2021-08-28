package com.hevlar.accounting.service;

import com.hevlar.accounting.model.*;
import com.hevlar.accounting.repository.AccountData;
import com.hevlar.accounting.repository.AccountDataRepository;
import com.hevlar.accounting.util.ModelMapping;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.util.Streamable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ChartOfAccountsTest {

    ChartOfAccounts chartOfAccounts;
    AutoCloseable closeable;

    @MockBean
    AccountDataRepository accountDataRepository;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this.getClass());
        chartOfAccounts = new ChartOfAccounts(accountDataRepository);
    }

    @Test
    void ensure_AccountData_map_to_Account_correctly(){
        AccountData accountData = new AccountData(
                "Bank",
                AccountGroup.CURRENT_ASSETS.label,
                LocalDate.of(2021, 1, 1),
                "SGD", new BigDecimal("10000"),
                null, null, null, false
        );
        BalanceSheetAccount account = (BalanceSheetAccount) ModelMapping.toAccount(accountData);
        assertEquals(account.getName(), accountData.getName());
        assertEquals(account.getAccountGroup(), AccountGroup.CURRENT_ASSETS);
        assertEquals(account.getOpenDate(), accountData.getOpenDate());
        assertEquals(account.getCurrency(), Currency.getInstance("SGD"));
        assertEquals(account.getOpenBal(), accountData.getOpenBal());
    }

    @Test
    void ensure_no_duplicate_account_name(){
        Mockito.when(accountDataRepository.findByName("Food")).thenReturn(new AccountData("Food", AccountGroup.EXPENSES.label, false));

        assertFalse(chartOfAccounts.newRevenue("Food"));
    }

    @Test
    void getAccounts_return_correctly(){

        Mockito.when(accountDataRepository.findByAccountGroup(AccountGroup.CURRENT_ASSETS.label)).thenReturn(
                Streamable.of(List.of(
                        new AccountData("Bank A", AccountGroup.CURRENT_ASSETS.label, LocalDate.now(), "SGD", new BigDecimal("100.0"), false),
                        new AccountData("Bank B", AccountGroup.CURRENT_ASSETS.label, LocalDate.now(), "SGD", new BigDecimal("100.0"), false)
                ))
        );

        Streamable<Account> currentAssets = chartOfAccounts.getAccounts(AccountGroup.CURRENT_ASSETS);
        assertEquals(currentAssets.toList().size(), 2);
        assertFalse(currentAssets.filter(account -> account.getName().equals("Bank A")).isEmpty());
        assertFalse(currentAssets.filter(account -> account.getName().equals("Bank B")).isEmpty());
    }

    @Test
    void lock_success() {
        Mockito.when(accountDataRepository.findAll()).thenReturn(
                List.of(
                        new AccountData("Food", AccountGroup.EXPENSES.label, false),
                        new AccountData("Bank", AccountGroup.CURRENT_ASSETS.label, LocalDate.now(), "SGD", new BigDecimal("100.00"), false)
                )
        );
        chartOfAccounts.lock();
        accountDataRepository.findAll().forEach(
                accountData -> assertTrue(accountData.isLocked())
        );
    }

    @Test
    void deleteAccount_not_allowed_for_locked_account() {
        Mockito.when(accountDataRepository.findAll()).thenReturn(
                List.of(
                        new AccountData("Food", AccountGroup.EXPENSES.label, false)
                )
        );
        Mockito.when(accountDataRepository.findByName("Account")).thenReturn(
                new AccountData("Food", AccountGroup.EXPENSES.label, false));
        assertFalse(chartOfAccounts.deleteAccount("Food"));
    }

    @Test
    void getAccount() {
        Mockito.when(accountDataRepository.findByName("Bank A")).thenReturn(
                new AccountData("Bank A", AccountGroup.CURRENT_ASSETS.label, LocalDate.now(), "SGD", new BigDecimal("100.0"), false)
        );
        BalanceSheetAccount bank = (BalanceSheetAccount) chartOfAccounts.getAccount("Bank A");
        assertEquals(bank.getOpenBal(), new BigDecimal("100.0"));
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }
}