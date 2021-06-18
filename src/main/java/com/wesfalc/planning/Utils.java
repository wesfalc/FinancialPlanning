package com.wesfalc.planning;

import java.text.NumberFormat;

public class Utils {

    public static String moneyFormat(double finalAmount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(finalAmount);
        return moneyString;
    }
}
