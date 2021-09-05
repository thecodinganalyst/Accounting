package com.hevlar.accounting.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BalanceSheetAccountTest {

    @Test
    void balanceSheetAccount_openDate_not_null(){
        assertThrows(InvalidParameterException.class, () ->
                new BalanceSheetAccount("Bank", AccountGroup.CURRENT_LIABILITIES, null, "SGD", "100.0", Boolean.FALSE));
        assertThrows(InvalidParameterException.class, () ->
                new BalanceSheetAccount("Bank", AccountGroup.CURRENT_LIABILITIES, null, "SGD", new BigDecimal("100.0"), Boolean.FALSE));
    }

    @Test
    void balanceSheetAccount_currency_null(){
        assertThrows(InvalidParameterException.class, () ->
                new BalanceSheetAccount("Bank", AccountGroup.CURRENT_LIABILITIES, LocalDate.now(), null, "100.0", Boolean.FALSE));
        assertThrows(InvalidParameterException.class, () ->
                new BalanceSheetAccount("Bank", AccountGroup.CURRENT_LIABILITIES, LocalDate.now(), "", "100.0", Boolean.FALSE));
        assertThrows(InvalidParameterException.class, () ->
                new BalanceSheetAccount("Bank", AccountGroup.CURRENT_LIABILITIES, LocalDate.now(), "  ", "100.0", Boolean.FALSE));

        assertThrows(InvalidParameterException.class, () ->
                new BalanceSheetAccount("Bank", AccountGroup.CURRENT_LIABILITIES, LocalDate.now(), null, new BigDecimal("100.0"), Boolean.FALSE));
        assertThrows(InvalidParameterException.class, () ->
                new BalanceSheetAccount("Bank", AccountGroup.CURRENT_LIABILITIES, LocalDate.now(), "", new BigDecimal("100.0"), Boolean.FALSE));
        assertThrows(InvalidParameterException.class, () ->
                new BalanceSheetAccount("Bank", AccountGroup.CURRENT_LIABILITIES, LocalDate.now(), "  ", new BigDecimal("100.0"), Boolean.FALSE));

    }

    @Test
    void setOpenDate_disallowed_for_locked_account() {
        BalanceSheetAccount bank = new BalanceSheetAccount("bank", AccountGroup.CURRENT_ASSETS, LocalDate.now(), "SGD", "100.0", false);
        assertTrue(bank.setOpenDate(LocalDate.of(2021, 1, 1)));
        bank.lock();
        assertFalse(bank.setOpenDate(LocalDate.now()));
    }

    @Test
    void setCurrency_disallowed_for_locked_account() {
        BalanceSheetAccount bank = new BalanceSheetAccount("bank", AccountGroup.CURRENT_ASSETS, LocalDate.now(), "SGD", "100.0", false);
        assertTrue(bank.setCurrency("GBP"));
        bank.lock();
        assertFalse(bank.setCurrency("USD"));
    }

    @Test
    void setOpenBal_disallowed_for_locked_account() {
        BalanceSheetAccount bank = new BalanceSheetAccount("bank", AccountGroup.CURRENT_ASSETS, LocalDate.now(), "SGD", "100.0", false);
        assertTrue(bank.setOpenBal(0.0));
        bank.lock();
        assertFalse(bank.setOpenBal(1.0));
    }
}