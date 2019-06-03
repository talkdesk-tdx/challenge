package com.tdx;

import java.time.Duration;
import java.time.Instant;

public class Event {
    private Instant start;
    private Instant end;
    private boolean paid;
    private int priority;
    private long agentID;

    public Event(Instant start, Instant end, boolean paid, int priority, long agentID) {
        this.start = start;
        this.end = end;
        this.paid = paid;
        this.priority = priority;
        this.agentID = agentID;
    }

    public boolean intersects(Event event) {
        return this.start.isBefore(event.end) && this.end.isAfter(event.start);
    }

    public Duration getPaidDuration() {
        return paid ? Duration.between(start, end) : Duration.ZERO;
    }

    public Instant getStart() {
        return start;
    }

    public Instant getEnd() {
        return end;
    }

    public long getAgentID() {
        return agentID;
    }

    public boolean isPaid() {
        return paid;
    }

    public int getPriority() {
        return priority;
    }

}
