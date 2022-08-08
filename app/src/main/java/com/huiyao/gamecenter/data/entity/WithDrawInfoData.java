package com.huiyao.gamecenter.data.entity;

import java.util.List;

public class WithDrawInfoData {


    /**
     * balance : 10
     * amount : [1,5,10,50,100,200]
     */

    private String balance;
    private List<String> amount;
    private String instructions;

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public List<String> getAmount() {
        return amount;
    }

    public void setAmount(List<String> amount) {
        this.amount = amount;
    }
}
