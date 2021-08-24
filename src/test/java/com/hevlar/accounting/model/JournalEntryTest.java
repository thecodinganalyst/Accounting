package com.hevlar.accounting.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
        je = new JournalEntry(new AtomicLong(1), LocalDate.now(), "Lunch", Recurrence.N, null, "SGD", 10.0, expense, cash, null, null, null);
    }


}