package AssignmentScheduler;

import java.util.*;

/**
 * Represents a group of TimePointCounters
 * @author seantwomey
 * */
final class TimePointCounterGroup {

    //Map containing keys of TimePoints and values of TimePointCounters
    private Map<TimePoint, TimePointCounter> counters;
    //List holding all currently independent TimePoints
    private List<TimePoint> independentTimePoints;

    /**
     * Constructor for a TimePointCounterGroup
     *
     * @param counters
     * @param independentTimePoints
     */
    private TimePointCounterGroup(Map<TimePoint, TimePointCounter> counters, List<TimePoint> independentTimePoints) {

        SchedulerException.checkNull(counters, independentTimePoints);
        this.counters = counters;
        this.independentTimePoints = independentTimePoints;
    }

    /**
     * Method used to instantiate a TimePointCounterGroup
     *
     * @param timePoints
     * @return new instance of TimePointCounterGroup
     */
    static final TimePointCounterGroup create(Set<TimePoint> timePoints) {

        SchedulerException.checkNull(timePoints);
        Map<TimePoint, TimePointCounter> counts = new HashMap<>();
        List<TimePoint> independentPoints = new LinkedList<>();
        for (TimePoint timepoint : timePoints) {

            assert Objects.nonNull(timepoint);
            if (timepoint.isIndependent()) {

                independentPoints.add(timepoint);
            }

            counts.put(timepoint, new TimePointCounter(timepoint.inDegree())); //sets counters of TimePoints to the inDegree of the TimePoints
        }

        return new TimePointCounterGroup(counts, independentPoints);
    }

    /**
     * Helper method used to access the map containing the TimePoints with their respective TimePointCounters
     * @return counters
     */
    Map<TimePoint, TimePointCounter> getCounters() {

        return counters;
    }

    /**
     * Helper method used to access the list of independent TimePoints
     * @return independentTimePoints
     */
    List<TimePoint> getIndependentTimePoints () {

        return independentTimePoints;
    }

    /**
     * Returns whether or not there exists an independent TimePoint
     *
     * @return T/F
     */
    final boolean isAnyIndependent() {

        return !independentTimePoints.isEmpty(); //Will return true if there are independent time points in the list, otherwise false if it is empty
    }

    /**
     * Returns any independent TimePoint after removing from independentTimePoints list
     * Pre-condition: There is at least one point in independentTimePoints
     *
     * @return TimePoint which was removed
     */
    final TimePoint removeIndependent() {

        assert !independentTimePoints.isEmpty() : SchedulerException.regularException(SchedulerException.Error.NO_SUCH_POINT);
        return independentTimePoints.remove(0); //remove method will return the TimePoint which was removed
    }

    /**
     * Decrements all counters corresponding to given TimePoints
     * If any counter reaches 0, the TimePoint will be inserted into independentTimePoints
     * Pre-condition: All timePoints are known to the TimePointCounterGroup and none is already independent
     *
     * @param timePoints
     */
    final void decrementCounters(Set<TimePoint> timePoints) {

        SchedulerException.checkNull(timePoints);
        assert !isAnyIndependent(): "No TimePoints can be independent."; //checks to ensure no TimePoints are currently independent
        for (TimePoint timepoint : timePoints) {

            assert counters.containsKey(timepoint); //checks to ensure the TimePoint is known to the TimePointCounterGroup
            TimePointCounter targetCounter = counters.get(timepoint);
            targetCounter.decrement();

            if (targetCounter.isIndependent()) {

                independentTimePoints.add(timepoint);
            }
        }

    }

    @Override
    public String toString() {

        StringBuilder sbuild = new StringBuilder();
        sbuild.append("This TimePointCounterGroup has counters of " + counters);
        sbuild.append(" and there are " + independentTimePoints.size() + " independent TimePoints currently.");
        return sbuild.toString();
    }
}
