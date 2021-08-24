package com.hevlar.accounting.model;

public enum AccountGroup {
    FIXED_ASSETS("Fixed Assets", EntryType.DEBIT, AccountType.BalanceSheet),
    CURRENT_ASSETS("Current Assets", EntryType.DEBIT, AccountType.BalanceSheet),
    CURRENT_LIABILITIES("Current Liabilities", EntryType.CREDIT, AccountType.BalanceSheet),
    LONG_TERM_LIABILITIES("Long Term Liabilities", EntryType.CREDIT, AccountType.BalanceSheet),
    REVENUE("Revenue", EntryType.CREDIT, AccountType.IncomeStatement),
    EXPENSES("Expenses", EntryType.DEBIT, AccountType.IncomeStatement),
    GAINS("Gains", EntryType.CREDIT, AccountType.IncomeStatement),
    LOSSES("Losses", EntryType.DEBIT, AccountType.IncomeStatement),
    EQUITIES("Equities", EntryType.CREDIT, AccountType.BalanceSheet);

    public final String label;
    public final EntryType entryType;
    public final AccountType accountType;

    AccountGroup(String label, EntryType entryType, AccountType accountType) {
        this.label = label;
        this.entryType = entryType;
        this.accountType = accountType;
    }
}
