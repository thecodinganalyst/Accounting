package com.hevlar.accounting;

import com.hevlar.accounting.controller.ChartOfAccounts;

public class App{

    public static void main(String[] args){
        App app = new App();
    }

    ChartOfAccounts chartOfAccounts;

    public App(){
        chartOfAccounts = new ChartOfAccounts();
    }
}