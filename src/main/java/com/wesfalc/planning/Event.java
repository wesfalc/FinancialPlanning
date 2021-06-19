package com.wesfalc.planning;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors (fluent = true)
public class Event {

    public enum EventType {
        ONE_TIME_CONTRIBUTION, MONTHLY_CONTRIBUTION, ONE_TIME_WITHDRAWAL, MONTHLY_WITHDRAWAL;

        public static EventType fromText(String text) {
            switch (text) {
                case "oneTimeContribution" : return ONE_TIME_CONTRIBUTION;
                case "monthlyContribution" : return MONTHLY_CONTRIBUTION;
                case "oneTimeWithdrawal" :   return ONE_TIME_WITHDRAWAL;
                case "monthlyWithdrawal" :   return MONTHLY_WITHDRAWAL;
            }
            throw new IllegalArgumentException("Cannot recognize event type -  " + text);
        }

        @Override
        public String toString() {
            switch (this) {
                case ONE_TIME_CONTRIBUTION:  return "(One Time Contribution)";
                case MONTHLY_CONTRIBUTION:  return "(Monthly Contribution)";
                case ONE_TIME_WITHDRAWAL:  return "(One Time Withdrawal)";
                case MONTHLY_WITHDRAWAL:  return "(Monthly Withdrawal)";
            }
            return "unknown";
        }
    }

    private EventType type;
    private double amount;
    private String description;
    private int year;

    public String amountString() {
        return Utils.moneyFormat(amount);
    }

    public static Event parse(String eventData) {
        Event event = new Event();

        String[] fields = eventData.split("::");
        for (String field : fields) {
            String[] keyValue = field.split("==");

            String key = keyValue[0];
            String value = keyValue[1];

            switch (key) {
                case "eventType"   : event.type = EventType.fromText(value); break;
                case "eventAmount" : event.amount = Double.parseDouble(value); break;
                case "eventYear"   : event.year = Integer.parseInt(value); break;
                case "eventDescription" : event.description = value;
            }
        }
        return event;
    }
}
