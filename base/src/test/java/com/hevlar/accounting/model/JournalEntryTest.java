package com.hevlar.accounting.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JournalEntryTest {

    JournalEntry je;

    @Mock
    IncomeStatementAccount expense;

    @Mock
    BalanceSheetAccount cash;

    @BeforeEach
    void setUp() {
        je = new JournalEntry(new AtomicLong(1), LocalDate.now(), "Lunch", Recurrence.N, null, "SGD", "10.0", expense, cash, null, null, null);
    }

    @Test
    void lock() {
        assertFalse(je.isLocked());
        je.lock();
        assertTrue(je.isLocked());
    }

    @Test
    void setTxDate() {
        assertTrue(je.setTxDate(LocalDate.of(2021, 12, 12)));
        assertEquals(je.getTxDate(), LocalDate.of(2021, 12, 12));
        je.lock();
        assertFalse(je.setTxDate(LocalDate.of(2021, 6, 6)));
        assertEquals(je.getTxDate(), LocalDate.of(2021, 12, 12));
    }

    @Test
    void setItem() {
        assertTrue(je.setItem("Item"));
        assertEquals(je.getItem(), "Item");
        je.lock();
        assertFalse(je.setItem("Test"));
        assertEquals(je.getItem(), "Item");
    }

    @Test
    void setRecurrence() {
        assertTrue(je.setRecurrence(Recurrence.D));
        assertEquals(je.getRecurrence(), Recurrence.D);
        je.lock();
        assertFalse(je.setRecurrence(Recurrence.M));
        assertEquals(je.getRecurrence(), Recurrence.D);
    }

    @Test
    void setTags() {
        assertTrue(je.setTags(new String[]{"#food", "#lunch"}));
        assertEquals(je.getTags().length, 2);
        assertArrayEquals(je.getTags(), new String[]{"#food", "#lunch"});
        je.lock();
        assertFalse(je.setTags(new String[]{"#test"}));
        assertEquals(je.getTags().length, 2);
    }

    @Test
    void setCurrency_by_code() {
        assertTrue(je.setCurrency("CNY"));
        assertEquals(je.getCurrency().getCurrencyCode(), "CNY");
        je.lock();
        assertFalse(je.setCurrency("USD"));
        assertEquals(je.getCurrency().getCurrencyCode(), "CNY");
    }

    @Test
    void setCurrency() {
        assertTrue(je.setCurrency(Currency.getInstance("MYR")));
        assertEquals(je.getCurrency().getCurrencyCode(), "MYR");
        je.lock();
        assertFalse(je.setCurrency("USD"));
        assertEquals(je.getCurrency().getCurrencyCode(), "MYR");
    }

    @Test
    void setAmount() {
        assertTrue(je.setAmount(new BigDecimal("3.2")));
        assertEquals(je.getAmount(), new BigDecimal("3.2"));
        je.lock();
        assertFalse(je.setAmount(new BigDecimal("2.1")));
        assertEquals(je.getAmount(), new BigDecimal("3.2"));
    }

    @Test
    void setAmount_by_string() {
        assertTrue(je.setAmount("4.1"));
        assertEquals(je.getAmount(), new BigDecimal("4.1"));
        je.lock();
        assertFalse(je.setAmount(new BigDecimal("2.1")));
        assertEquals(je.getAmount(), new BigDecimal("4.1"));
    }

    @Test
    void setDebit() {
        IncomeStatementAccount food = Mockito.mock(IncomeStatementAccount.class);
        Mockito.when(food.getName()).thenReturn("food");
        assertTrue(je.setDebit(food));
        assertEquals(je.getDebit().getName(), "food");
        je.lock();
        assertFalse(je.setDebit(food));
    }

    @Test
    void setCredit() {
        BalanceSheetAccount bank = Mockito.mock(BalanceSheetAccount.class);
        Mockito.when(bank.getName()).thenReturn("Bank");
        assertTrue(je.setCredit(bank));
        assertEquals(je.getCredit().getName(), "Bank");
        je.lock();
        assertFalse(je.setCredit(bank));
    }

    @Test
    void setPostDate() {
        assertTrue(je.setPostDate(LocalDate.of(2021, 12, 12)));
        assertEquals(je.getPostDate(), LocalDate.of(2021, 12, 12));
        je.lock();
        assertFalse(je.setPostDate(LocalDate.of(2021, 6, 6)));
        assertEquals(je.getPostDate(), LocalDate.of(2021, 12, 12));
    }

    @Test
    void setDebitStatementDate() {
        assertTrue(je.setDebitStatementDate(LocalDate.of(2021, 12, 12)));
        assertEquals(je.getDebitStatementDate(), LocalDate.of(2021, 12, 12));
        je.lock();
        assertFalse(je.setDebitStatementDate(LocalDate.of(2021, 6, 6)));
        assertEquals(je.getDebitStatementDate(), LocalDate.of(2021, 12, 12));
    }

    @Test
    void setCreditStatementDate() {
        assertTrue(je.setCreditStatementDate(LocalDate.of(2021, 12, 12)));
        assertEquals(je.getCreditStatementDate(), LocalDate.of(2021, 12, 12));
        je.lock();
        assertFalse(je.setCreditStatementDate(LocalDate.of(2021, 6, 6)));
        assertEquals(je.getCreditStatementDate(), LocalDate.of(2021, 12, 12));
    }
}