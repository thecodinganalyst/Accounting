package com.hevlar.accounting.model;

/**
 * Represents the fixed account groups - assets, liabilities, revenue, expenses, etc in accounting
 */
public enum AccountGroup {
    /**
     * Fixed Asset (Long term investment, Car, House, Insurance)
     */
    FIXED_ASSETS("Fixed Assets", EntryType.DEBIT, AccountType.BalanceSheet),

    /**
     * Current Asset (Bank Account, Cash)
     */
    CURRENT_ASSETS("Current Assets", EntryType.DEBIT, AccountType.BalanceSheet),

    /**
     * Current liabilities (Credit Cards, Short Term Loans, Utility Accounts)
     */
    CURRENT_LIABILITIES("Current Liabilities", EntryType.CREDIT, AccountType.BalanceSheet),

    /**
     * Long Term Liabilities (Long Term Loans)
     */
    LONG_TERM_LIABILITIES("Long Term Liabilities", EntryType.CREDIT, AccountType.BalanceSheet),

    /**
     * Revenue (Salary, Sale of Goods)
     */
    REVENUE("Revenue", EntryType.CREDIT, AccountType.IncomeStatement),

    /**
     * Expenses (Food, Grocery, Cost of Goods Sold, Utility usage)
     */
    EXPENSES("Expenses", EntryType.DEBIT, AccountType.IncomeStatement),

    /**
     * Gains (Profits from sales of assets)
     */
    GAINS("Gains", EntryType.CREDIT, AccountType.IncomeStatement),

    /**
     * Losses (Loss from sales of assets)
     */
    LOSSES("Losses", EntryType.DEBIT, AccountType.IncomeStatement),

    /**
     * Equities (ShareHolders' investment)
     */
    EQUITIES("Equities", EntryType.CREDIT, AccountType.BalanceSheet);

    /**
     * Display label of this Account Group
     */
    public final String label;

    /**
     * Credit or Debit, when amount is debited/credited to this account, the balance will increase
     */
    public final EntryType entryType;

    /**
     * Account Type - Balance Sheet or Income Statement
     */
    public final AccountType accountType;

    /**
     * Creates a new Account Group
     * @param label display label of this Account Group
     * @param entryType Credit or Debit
     * @param accountType Balance Sheet or Income Statement
     */
    AccountGroup(String label, EntryType entryType, AccountType accountType) {
        this.label = label;
        this.entryType = entryType;
        this.accountType = accountType;
    }
}
