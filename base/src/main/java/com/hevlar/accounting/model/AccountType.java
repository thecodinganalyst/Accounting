package com.hevlar.accounting.model;

/**
 * Represents the 2 general account types in accounting. IncomeStatement account type are for the revenue and expense
 * as found in the income statement, whereas BalanceSheet account type are for the accounts that is used in balance sheets
 */
public enum AccountType {
    /**
     * Balance Sheet account
     */
    BalanceSheet,

    /**
     * Income Statement account
     */
    IncomeStatement;
}
