package com.tdx;

import java.util.Comparator;

public class StartTimeComparator implements Comparator<Event> {
    @Override
    public int compare(Event o1, Event o2) {
        return o1.getStart().compareTo(o2.getStart());
    }
}
