package com.wesfalc.planning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YearToEventMap {

    private Map<Integer, List<Event>> yearToEventMap = new HashMap<>();

    public List<Event> getEventsForYear(int year) {
        if (yearToEventMap.containsKey(year)) {
            return yearToEventMap.get(year);
        }
        else {
            return new ArrayList<>();
        }
    }

    public void putEvent(int year, Event event) {
        if (!yearToEventMap.containsKey(year)) {
            yearToEventMap.put(year, new ArrayList<Event>());
        }

        List<Event> events = yearToEventMap.get(year);
        events.add(event);
    }
}
