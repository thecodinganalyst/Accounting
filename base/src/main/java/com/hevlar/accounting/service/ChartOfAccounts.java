package com.hevlar.accounting.service;

import com.hevlar.accounting.model.*;
import com.hevlar.accounting.repository.AccountData;
import com.hevlar.accounting.repository.AccountDataRepository;
import com.hevlar.accounting.util.ModelMapping;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


/**
 * Represents the chart of accounts in accounting, which is a list of all the accounts available.
 */
@Service
@Transactional
public class ChartOfAccounts {

    private final AccountDataRepository accountDataRepository;

    /**
     * Default constructor
     * @param accountDataRepository repository
     */
    public ChartOfAccounts(AccountDataRepository accountDataRepository){
        this.accountDataRepository = accountDataRepository;
    }

    /**
     * Gets the account by name
     * @param name name of account to be retrieved
     * @return Account
     */
    public Account getAccount(String name) {
        AccountData data = accountDataRepository.findByName(name);
        return ModelMapping.toAccount(data);
    }

    /**
     * Gets a hashmap of the credit card accounts available
     * @return hashmap of credit card accounts
     */
    public Streamable<Account> getCreditCardAccounts(){
        return accountDataRepository
                .findAllByBankNotNull()
                .map(ModelMapping::toAccount);
    }

    /**
     * Get hashmap of accounts by the account group
     * @param accountGroup account group - eg. Current Assets, Revenue, etc
     * @return hashmap of accounts
     */
    public Streamable<Account> getAccounts(AccountGroup accountGroup){
        return accountDataRepository
                .findByAccountGroup(accountGroup.label)
                .map(ModelMapping::toAccount);
    }

    /**
     * lock all the accounts available
     */
    public void lock(){
        Iterable<AccountData> allAccounts = accountDataRepository.findAll();
        allAccounts.forEach(accountData -> accountData.setLock(true));
        accountDataRepository.saveAll(allAccounts);
    }

    /**
     * Delete account by name, only allowed if the account is not locked
     * @param name name of the account to be deleted
     * @return true if successful, false if the operation failed (not found, locked)
     */
    public Boolean deleteAccount(String name){
        AccountData accountData = accountDataRepository.findByName(name);
        if(accountData == null || accountData.isLocked()) return Boolean.FALSE;
        accountDataRepository.delete(accountData);
        return true;
    }

    private Boolean newAccount(Account account){
        if(accountDataRepository.findByName(account.getName()) != null) return false;
        accountDataRepository.save(ModelMapping.toAccountData(account));
        return true;
    }

    /**
     * Creates a new fixed asset account
     * @param name name of account
     * @param openDate opening date
     * @param currency ISO 4217 currency code - SGD, USD
     * @param openBal opening balance
     * @return true if successful, false otherwise
     */
    public Boolean newFixedAsset(String name, LocalDate openDate, String currency, String openBal){
        return newAccount(new BalanceSheetAccount(name, AccountGroup.FIXED_ASSETS, openDate, currency, openBal, false));
    }

    /**
     * Creates a new current asset account
     * @param name name of account
     * @param openDate opening date
     * @param currency ISO 4217 currency code - SGD, USD
     * @param openBal opening balance
     * @return true if successful, false otherwise
     */
    public Boolean newCurrentAsset(String name, LocalDate openDate, String currency, String openBal){
        return newAccount(new BalanceSheetAccount(name, AccountGroup.CURRENT_ASSETS, openDate, currency, openBal, false));
    }

    /**
     * Creates a new current liability account
     * @param name name of account
     * @param openDate opening date
     * @param currency ISO 4217 currency code - SGD, USD
     * @param openBal opening balance
     * @return true if successful, false otherwise
     */
    public Boolean newCurrentLiability(String name, LocalDate openDate, String currency, String openBal){
        return newAccount(new BalanceSheetAccount(name, AccountGroup.CURRENT_LIABILITIES, openDate, currency, openBal, false));
    }

    /**
     * Creates a new credit card account
     * @param name name of account
     * @param openDate opening date
     * @param currency ISO 4217 currency code - SGD, USD
     * @param openBal opening balance
     * @param bank name of issuing bank
     * @param statementDay day of month when statement is generated
     * @param dueDay day of month when statement is due for payment
     * @return true if successful, false otherwise
     */
    public Boolean newCreditCard(String name, LocalDate openDate, String currency, String openBal, String bank, Integer statementDay, Integer dueDay){
        return newAccount(new CreditCardAccount(name, openDate, currency, openBal, bank, statementDay, dueDay, false));
    }

    /**
     * Creates a new long term liability account
     * @param name name of account
     * @param openDate opening date
     * @param currency ISO 4217 currency code - SGD, USD
     * @param openBal opening balance
     * @return true if successful, false otherwise
     */
    public Boolean newLongTermLiability(String name, LocalDate openDate, String currency, String openBal){
        return newAccount(new BalanceSheetAccount(name, AccountGroup.LONG_TERM_LIABILITIES, openDate, currency, openBal, false));
    }

    /**
     * Creates a new equity account
     * @param name name of account
     * @param openDate opening date
     * @param currency ISO 4217 currency code - SGD, USD
     * @param openBal opening balance
     * @return true if successful, false otherwise
     */
    public Boolean newEquity(String name, LocalDate openDate, String currency, String openBal){
        return newAccount(new BalanceSheetAccount(name, AccountGroup.EQUITIES, openDate, currency, openBal, false));
    }

    /**
     * Creates a new revenue account
     * @param name name of account
     * @return true if successful, false otherwise
     */
    public Boolean newRevenue(String name){
        return newAccount(new IncomeStatementAccount(name, AccountGroup.REVENUE, false));
    }

    /**
     * Creates a new expense account
     * @param name name of account
     * @return true if successful, false otherwise
     */
    public Boolean newExpense(String name){
        return newAccount(new IncomeStatementAccount(name, AccountGroup.EXPENSES, false));
    }

    /**
     * Creates a new gains account
     * @param name name of account
     * @return true if successful, false otherwise
     */
    public Boolean newGain(String name){
        return newAccount(new IncomeStatementAccount(name, AccountGroup.GAINS, false));
    }

    /**
     * Creates a new losses account
     * @param name name of account
     * @return true if successful, false otherwise
     */
    public Boolean newLoss(String name){
        return newAccount(new IncomeStatementAccount(name, AccountGroup.LOSSES, false));
    }
}
