package com.wesfalc.planning;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors (fluent = true)
public class Result {

    private double startingAmount;
    private double rateOfReturn;

    private List<ResultRow> rows = new ArrayList<>();

    public void addRow(ResultRow row) {
        rows.add(row);
    }

    public String startingAmountString() {
        return Utils.moneyFormat(startingAmount);
    }

}
