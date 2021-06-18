package com.wesfalc.planning;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors (fluent = true)
public class ResultRow {
    private int year;
    private double investment;
    private double earnings;
    private double balance;

    public String balanceString() {
        return Utils.moneyFormat(balance);
    }

    public String investmentString() {
        return Utils.moneyFormat(investment);
    }

    public String earningsString() {
        return Utils.moneyFormat(earnings);
    }
}
