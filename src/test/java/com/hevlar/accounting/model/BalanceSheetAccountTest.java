package com.hevlar.accounting.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BalanceSheetAccountTest {

    @Test
    void setOpenDate_disallowed_for_locked_account() {
        BalanceSheetAccount bank = new BalanceSheetAccount("bank", AccountGroup.CURRENT_ASSETS, LocalDate.now(), "SGD", 100.0);
        bank.lock();
        assertFalse(bank.setOpenDate(LocalDate.now()));
    }

    @Test
    void setCurrency_disallowed_for_locked_account() {
        BalanceSheetAccount bank = new BalanceSheetAccount("bank", AccountGroup.CURRENT_ASSETS, LocalDate.now(), "SGD", 100.0);
        bank.lock();
        assertFalse(bank.setCurrency("USD"));
    }

    @Test
    void setOpenBal_disallowed_for_locked_account() {
        BalanceSheetAccount bank = new BalanceSheetAccount("bank", AccountGroup.CURRENT_ASSETS, LocalDate.now(), "SGD", 100.0);
        bank.lock();
        assertFalse(bank.setOpenBal(1.0));
    }
}