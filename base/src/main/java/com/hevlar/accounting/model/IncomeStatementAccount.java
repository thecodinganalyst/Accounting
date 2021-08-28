package com.hevlar.accounting.model;

/**
 * Represents an account used in the income statement, like Revenue, Expense, etc
 */
public class IncomeStatementAccount extends Account{

    /**
     * Default constructor to create an income statement account
     * @param name name of the account
     * @param accountGroup account group - like Revenue, Expense, etc
     */
    public IncomeStatementAccount(String name, AccountGroup accountGroup, Boolean lock) {
        super(name, accountGroup, lock);
    }
}
