package com.hevlar.accounting.util;

import com.hevlar.accounting.model.*;
import com.hevlar.accounting.repository.AccountData;

public class ModelMapping {

    public static Account toAccount(AccountData accountData){
        AccountBuilder accountBuilder = new AccountBuilder();
        accountBuilder.setName(accountData.getName());
        accountBuilder.setAccountGroup(accountData.getAccountGroup());
        accountBuilder.setOpenDate(accountData.getOpenDate());
        accountBuilder.setCurrency(accountData.getCurrency());
        accountBuilder.setOpenBal(accountData.getOpenBal());
        accountBuilder.setBank(accountData.getBank());
        accountBuilder.setStatementDay(accountData.getStatementDay());
        accountBuilder.setDueDay(accountData.getDueDay());
        accountBuilder.setLock(accountData.isLocked());
        return accountBuilder.build();
    }

    public static AccountData toAccountData(Account account){
        if(account.getClass() == CreditCardAccount.class){
            return toAccountData((CreditCardAccount)account);
        }else if(account.getClass() == BalanceSheetAccount.class){
            return toAccountData((BalanceSheetAccount) account);
        }else{
            return toAccountData((IncomeStatementAccount) account);
        }
    }

    private static AccountData toAccountData(CreditCardAccount creditCardAccount){
        return new AccountData(
            creditCardAccount.getName(),
            creditCardAccount.getAccountGroup().label,
            creditCardAccount.getOpenDate(),
            creditCardAccount.getCurrency().getCurrencyCode(),
            creditCardAccount.getOpenBal(),
            creditCardAccount.getBank(),
            creditCardAccount.getStatementDay(),
            creditCardAccount.getDueDay(),
            creditCardAccount.isLocked()
        );
    }

    private static AccountData toAccountData(BalanceSheetAccount balanceSheetAccount){
        return new AccountData(
            balanceSheetAccount.getName(),
            balanceSheetAccount.getAccountGroup().label,
            balanceSheetAccount.getOpenDate(),
            balanceSheetAccount.getCurrency().getCurrencyCode(),
            balanceSheetAccount.getOpenBal(),
            balanceSheetAccount.isLocked()
        );
    }

    private static AccountData toAccountData(IncomeStatementAccount incomeStatementAccount){
        return new AccountData(
            incomeStatementAccount.getName(),
            incomeStatementAccount.getAccountGroup().label,
            incomeStatementAccount.isLocked()
        );
    }

}
