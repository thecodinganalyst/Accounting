package com.hevlar.accounting.service;

import com.hevlar.accounting.model.*;
import com.hevlar.accounting.repository.AccountData;
import com.hevlar.accounting.repository.AccountDataRepository;
import com.hevlar.accounting.util.ModelMapping;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
class ChartOfAccountsTest {

    private ChartOfAccounts chartOfAccounts;
    private AutoCloseable closeable;

    @MockBean
    private AccountDataRepository accountDataRepository;

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
        assertEquals(account.getAccountGroup(),
                AccountGroup.CURRENT_ASSETS);
        assertEquals(account.getOpenDate(), accountData.getOpenDate());
        assertEquals(account.getCurrency(), Currency.getInstance("SGD"));
        assertEquals(account.getOpenBal(), accountData.getOpenBal());
    }

    @Test
    void ensure_no_duplicate_account_name(){
        Mockito.when(accountDataRepository.findByName("Food")).thenReturn(new AccountData("Food", AccountGroup.EXPENSES.label, false));

        assertNull(chartOfAccounts.newRevenue("Food"));
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
    void updateAccount_successful(){
        AccountData accountData = new AccountData("Food", AccountGroup.EXPENSES.label, false);
        Mockito.when(accountDataRepository.findByName("Food")).thenReturn(accountData);
        Mockito.when(accountDataRepository.save(any(AccountData.class))).thenReturn(accountData);
        IncomeStatementAccount food = new IncomeStatementAccount("Food", AccountGroup.EXPENSES, false);
        IncomeStatementAccount account = (IncomeStatementAccount) chartOfAccounts.updateAccount(food);
        assertNotNull(account);
        assertFalse(account.isLocked());
        assertEquals(account.getName(), "Food");
        assertEquals(account.getAccountGroup(), AccountGroup.EXPENSES);
    }

    @Test
    void updateAccount_not_allowed_for_locked_account(){
        AccountData accountData = new AccountData("Food", AccountGroup.EXPENSES.label, true);
        Mockito.when(accountDataRepository.findByName("Food")).thenReturn(accountData);
        Mockito.when(accountDataRepository.save(any(AccountData.class))).thenReturn(accountData);
        IncomeStatementAccount food = new IncomeStatementAccount("Food", AccountGroup.EXPENSES, false);
        IncomeStatementAccount account = (IncomeStatementAccount) chartOfAccounts.updateAccount(food);
        assertNull(account);
    }

    @Test
    void updateAccount_not_allowed_non_existent_accounts(){
        Mockito.when(accountDataRepository.findByName("Food")).thenReturn(null);
        IncomeStatementAccount food = new IncomeStatementAccount("Food", AccountGroup.EXPENSES, false);
        IncomeStatementAccount account = (IncomeStatementAccount) chartOfAccounts.updateAccount(food);
        assertNull(account);
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
    void getCreditCardAccounts_correctly(){
        Mockito.when(accountDataRepository.findAllByBankNotNull()).thenReturn(
                Streamable.of(
                        new AccountData("Credit Card", AccountGroup.CURRENT_LIABILITIES.label, LocalDate.of(2021,1,1), "SGD", new BigDecimal("100"), "Bank A", 1, 12, false)
                )
        );
        List<Account> accountList = chartOfAccounts.getCreditCardAccounts().toList();
        assertEquals(accountList.size(), 1);
        CreditCardAccount account = (CreditCardAccount) accountList.get(0);
        assertEquals(account.getName(), "Credit Card");
        assertEquals(account.getAccountGroup(), AccountGroup.CURRENT_LIABILITIES);
        assertEquals(account.getOpenDate(), LocalDate.of(2021, 1, 1));
        assertEquals(account.getCurrency().getCurrencyCode(), "SGD");
        assertEquals(account.getOpenBal(), new BigDecimal("100"));
        assertEquals(account.getBank(), "Bank A");
        assertEquals(account.getStatementDay(), 1);
        assertEquals(account.getDueDay(), 12);
        assertFalse(account.isLocked());
    }

    @Test
    void deleteAccount_not_allowed_for_locked_account() {
        Mockito.when(accountDataRepository.findByName("Food")).thenReturn(
                new AccountData("Food", AccountGroup.EXPENSES.label, true));
        assertFalse(chartOfAccounts.deleteAccount("Food"));
    }

    @Test
    void deleteAccount_return_false_when_account_not_found() {
        Mockito.when(accountDataRepository.findByName("Food")).thenReturn(null);
        assertFalse(chartOfAccounts.deleteAccount("Food"));
    }

    @Test
    void deleteAccount_successful() {
        Mockito.when(accountDataRepository.findByName("Food")).thenReturn(
                new AccountData("Food", AccountGroup.EXPENSES.label, false));
        assertTrue(chartOfAccounts.deleteAccount("Food"));
    }

    @Test
    void newFixedAsset_successful(){
        Mockito.when(accountDataRepository.findByName("Investment")).thenReturn(null);
        AccountData accountData = new AccountData("Investment", AccountGroup.FIXED_ASSETS.label, LocalDate.of(2021,1, 1), "SGD", new BigDecimal("100"), false);
        Mockito.when(accountDataRepository.save(any(AccountData.class))).thenReturn(accountData);
        Account account = chartOfAccounts.newFixedAsset("Investment", LocalDate.of(2021,1, 1), "SGD", "100");
        assertNotNull(account);
    }

    @Test
    void newCurrentAsset_successful(){
        Mockito.when(accountDataRepository.findByName("Cash")).thenReturn(null);
        AccountData accountData = new AccountData("Cash", AccountGroup.CURRENT_ASSETS.label, LocalDate.of(2021, 1,1), "SGD", new BigDecimal("100"), false);
        Mockito.when(accountDataRepository.save(any(AccountData.class))).thenReturn(accountData);
        Account account = chartOfAccounts.newCurrentAsset("Cash", LocalDate.of(2021, 1, 1), "SGD", "100");
        assertNotNull(account);
    }

    @Test
    void newCurrentLiability_successful(){
        Mockito.when(accountDataRepository.findByName("Loan")).thenReturn(null);
        AccountData accountData = new AccountData("Loan", AccountGroup.CURRENT_LIABILITIES.label, LocalDate.of(2021,1, 1), "SGD", new BigDecimal("100"), false);
        Mockito.when(accountDataRepository.save(any(AccountData.class))).thenReturn(accountData);
        Account account = chartOfAccounts.newCurrentLiability("Loan", LocalDate.of(2021, 1, 1), "SGD", "100");
        assertNotNull(account);
    }

    @Test
    void newCreditCard_successful(){
        Mockito.when(accountDataRepository.findByName("Credit Card")).thenReturn(null);
        AccountData accountData = new AccountData("Credit Card", AccountGroup.CURRENT_LIABILITIES.label, LocalDate.of(2021, 1,1), "SGD", new BigDecimal("100"), "Bank A", 1, 12, false);
        Mockito.when(accountDataRepository.save(any(AccountData.class))).thenReturn(accountData);
        Account account = chartOfAccounts.newCreditCard("Credit Card", LocalDate.now(), "SGD", "100", "Bank A", 1, 12);
        assertNotNull(account);
    }

    @Test
    void newLongTermLiability_successful(){
        Mockito.when(accountDataRepository.findByName("Loan")).thenReturn(null);
        AccountData accountData = new AccountData("Loan", AccountGroup.LONG_TERM_LIABILITIES.label, LocalDate.of(2021, 1, 1), "SGD", new BigDecimal("100"), false);
        Mockito.when(accountDataRepository.save(any(AccountData.class))).thenReturn(accountData);
        assertNotNull(chartOfAccounts.newLongTermLiability("Loan", LocalDate.of(2021, 1, 1), "SGD", "100"));
    }

    @Test
    void newEquity_successful(){
        Mockito.when(accountDataRepository.findByName("Shareholder investment")).thenReturn(null);
        AccountData accountData = new AccountData("Shareholder investment", AccountGroup.EQUITIES.label, LocalDate.of(2021, 1, 1), "SGD", new BigDecimal("100"), false);
        Mockito.when(accountDataRepository.save(any(AccountData.class))).thenReturn(accountData);
        assertNotNull(chartOfAccounts.newEquity("Shareholder investment", LocalDate.of(2021, 1,1), "SGD", "100"));
    }

    @Test
    void newGain_successful(){
        Mockito.when(accountDataRepository.findByName("Profit from exchange rates")).thenReturn(null);
        AccountData accountData = new AccountData("Profit from exchange rates", AccountGroup.GAINS.label,false);
        Mockito.when(accountDataRepository.save(any(AccountData.class))).thenReturn(accountData);
        assertNotNull(chartOfAccounts.newGain("Profit from exchange rates"));
    }

    @Test
    void newLoss_successful(){
        Mockito.when(accountDataRepository.findByName("Loss from exchange rates")).thenReturn(null);
        AccountData accountData = new AccountData("Loss from exchange rates", AccountGroup.LOSSES.label, false);
        Mockito.when(accountDataRepository.save(any(AccountData.class))).thenReturn(accountData);
        assertNotNull(chartOfAccounts.newLoss("Loss from exchange rates"));
    }

    @Test
    void newExpense_successful(){
        Mockito.when(accountDataRepository.findByName("Food")).thenReturn(null);
        AccountData accountData = new AccountData("Food", AccountGroup.EXPENSES.label, false);
        Mockito.when(accountDataRepository.save(any(AccountData.class))).thenReturn(accountData);
        assertNotNull(chartOfAccounts.newExpense("Food"));
    }

    @Test
    void newRevenue_successful(){
        Mockito.when(accountDataRepository.findByName("Salary")).thenReturn(null);
        AccountData accountData = new AccountData("Salary", AccountGroup.REVENUE.label, false);
        Mockito.when(accountDataRepository.save(any(AccountData.class))).thenReturn(accountData);
        assertNotNull(chartOfAccounts.newRevenue("Salary"));
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