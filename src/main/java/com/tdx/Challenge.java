package com.tdx;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Boilerplate challenge class
 * Check the TODO's in the file to check what needs to be changed
 * Make sure not to change anything else so that we can test this against our input.
 */
public class Challenge {

    private static String CSV_FILE_PATH;

    /**
     * Comand line will be used to test the results
     * During execution two prints will be done, one with the elapsed time and other with the resulting paid time
     * The paid time value will be checked against our data set and expected results.
     * The dates have instant iso format yyyy-MM-ddTHH:mm:ssZ
     *
     * @param args the arguments will be a path for the CSV file containing the data set dump, a start date, end date
     *             and the agent id
     */
    public static void main(String[] args) {
        CSV_FILE_PATH = args[0];
        final Instant startInstant = Instant.parse(args[1]);
        final Instant endInstant = Instant.parse(args[2]);
        final long agentID = Long.valueOf(args[3]);
        System.out.println(
                "Time paid in seconds for agent " + agentID + " start = " + startInstant + " end = " + endInstant + " : "
                        + calculatePaidTimeForAgent(startInstant, endInstant, agentID).toSeconds());
    }

    /**
     * There are a few assumptions:
     * - There are only two entities, the agent and the events which are in a ManyToOne relationship with the
     * Employee entity.
     * - For consistency it's assumed that no two events with the same priority for the same agent overlap.
     * As this is a more general case and algorith to solve as such we:
     * - Don't assume that the 0 priority events fully contain the others (the shift scenario)
     * - Don't assume that the same priority events are all either paid or unpaid
     * We do assume that all the events are fully contained in the filter start and end times.
     * Given this we can make a query to fetch the data already sorted for us.
     * We will order it by priority desc, start time asc.
     *
     * @param start no timespan can start before this time
     * @param end   no timespan can end after this time
     * @param agent the agent (here is an id for simplification)
     * @return the amount of time that is paid given the filter criteria
     */
    private static Duration calculatePaidTimeForAgent(final Instant start, final Instant end, final long agent) {
        return calculatePaidTimeMeasured(orderEvents(fetchEventsForAgent(start, end, agent)));
    }

    /**
     * Mocking of database fetching
     * Keep this method as is
     *
     * @param start   no events will start prior to this instant
     * @param end     no event will end past this instant
     * @param agentId the agentId to which the events pertain to
     * @return the list of events filtered accordingly
     */
    private static List<Event> fetchEventsForAgent(final Instant start, final Instant end, final long agentId) {
        return allEvents().stream()
                .filter(event -> !(event.getStart().isBefore(start)
                        || event.getEnd().isAfter(end))
                        && event.getAgentID() == agentId)
                .collect(Collectors.toList());
    }

    /**
     * Some example events that could be in the database.
     *
     * @return the boilerplate list of events for testing purposes
     */
    private static List<Event> allEvents() {
        return CsvUtils.parseEventsFromCsvFile(CSV_FILE_PATH, ",");
    }

    private static Duration calculatePaidTimeMeasured(final List<Event> events) {
        final Instant start = Instant.now();
        final Duration paidTime = calculatePaidTime(events);
        final Instant end = Instant.now();
        System.out.println("Time elapsed on algorithm: " + Duration.between(start, end).toMillis());
        return paidTime;
    }

    /**
     * We'll assume that you queried the database with the sorting you needed but for testing purposes we ask that you
     * implement your required sorting since you don't know the order of the input that this is going to be tested
     * against
     *
     * @param events unordered events
     * @return ordered events
     */
    private static List<Event> orderEvents(final List<Event> events) {
        //TODO implement your order of the events, leave this as is if your algorithm does not care about event order
        return events;
    }

    /**
     * Calculates the amount of paid time taking into account the events priority.
     * <p>
     * Assume you have a list of Event coming in from a database with the query of your choosing
     * Meaning that you have it filtered and ordered as you want, implement the order in Challenge#orderEvents
     * //TODO don't forget to comment and document the code to portray the assumptions that were made
     *
     * @param events events to considered already filtered and ordered
     * @return a duration representing the amount of time that is to be paid for the events
     */
    private static Duration calculatePaidTime(final List<Event> events) {
        return Duration.ZERO;
        //TODO implement algorithm here

    }
}
