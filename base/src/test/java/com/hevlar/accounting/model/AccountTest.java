package com.hevlar.accounting.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void setName_disallowed_for_locked_account() {
        IncomeStatementAccount income = new IncomeStatementAccount("income", AccountGroup.REVENUE);
        income.lock();
        assertFalse(income.setName("Income"));
    }

}