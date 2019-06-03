package com.tdx;

import java.util.Comparator;

public class EndTimeComparator implements Comparator<Event> {
    @Override
    public int compare(Event o1, Event o2) {
        return o1.getEnd().compareTo(o2.getEnd());
    }
}
