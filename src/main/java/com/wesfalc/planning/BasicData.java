package com.wesfalc.planning;

import lombok.Data;
import lombok.experimental.Accessors;

// very hard to find a good name for this class!
@Data
@Accessors (fluent = true)
public class BasicData {
    private int startingAmount;
    private int years;
    private int monthlyContribution;
    private double rateOfReturn;

    public BasicData(int startingAmount, int years, int monthlyContribution, double rateOfReturn) {
        this.startingAmount = startingAmount;
        this.years = years;
        this.monthlyContribution = monthlyContribution;
        this.rateOfReturn = rateOfReturn;
    }
}
