package AssignmentScheduler;

import java.util.*;

/**
 * Represents a set of activities
 *
 * @author seantwomey
 */
public final class ActivityGroup {

    //Collection of TimePoints
    private static Set<TimePoint> activitytimePoints = new HashSet<>();

    /**
     * Adds all TimePoints to the group
     *
     * @param timePoints Pre-condition: timePoint is not null
     *                   Pre-condition: All TimePoints are non-null and frozen
     *                   Pre-condition: No TimePoint is already in the ActivityGroup
     */
    final void addTimePoints(Collection<TimePoint> timePoints) {

        if (Objects.nonNull(timePoints) || !timePoints.contains(null)) {

            SchedulerException.regularException(SchedulerException.Error.UNEXPECTED_NULL);
        }

        for (TimePoint timepoint : timePoints) { //checks to make sure all incoming timePoints are frozen, if this is not the case an appropriate exception is returned

            assert timepoint.isFrozen() : SchedulerException.exceptionwithTimePoint(SchedulerException.Error.POINT_NOT_FROZEN, timepoint);
            assert !activitytimePoints.contains(timepoint) : SchedulerException.exceptionwithTimePoint(SchedulerException.Error.TIME_POINT_EXISTS, timepoint);  //checks if a TimePoint being added is a duplicate, if so returns appropriate exception
            activitytimePoints.add(timepoint); //adds TimePoints to the set if they meet the preconditions
        }
    }

    /**
     * Orders all TimePoints in the group using sorting algorithm
     * Each TimePoint can only depend on the previous TimePoints in the list
     *
     * @return list of sorted TimePoints
     */
    public final List<TimePoint> sort() {

        return TimePointSorter.sort(activitytimePoints);
    }

    /**
     * Takes in a sorted list of TimePoints and assigns them to to their timeline
     *
     * @param sortedPoints
     * @return
     */
    public static final LinkedHashMap<TimePoint, Long> timeline(List<TimePoint> sortedPoints) {

        SchedulerException.checkNull(sortedPoints); //ensures sorted list of TimePoints is non-null
        LinkedHashMap<TimePoint, Long> timelineMap = new LinkedHashMap<>();

        for (TimePoint timepoint : sortedPoints) {
            Long zerotimeLine = 0L; //Local Long representing an activity TimePoint with no timeline
            if (timepoint.isIndependent()) {

                placeinMap(timelineMap, timepoint, zerotimeLine);
            }

            for (Dependency dependency : timepoint.getDependencies()) { //need a helper method to have 3 cases for dependencies of 0,1, or multiple

                TimePoint previousPoint = dependency.getPrevious();
                Dependency lastDependency = findlastPoint(sortedPoints, timepoint);
                assert (timelineMap.containsKey(lastDependency.getPrevious())) : new IllegalArgumentException("Current timepoint cannot find predecessor's completion time.", SchedulerException.exceptionwithEverything(SchedulerException.Error.CIRCULAR_DEPENDENCY, lastDependency.getDuration(), timepoint));
                Long lastpointDuration = lastDependency.getDuration();
                placeinMap(timelineMap, timepoint, zerotimeLine + timelineMap.get(lastDependency.getPrevious()) + lastpointDuration);
        }
    }

        return timelineMap;
}

    /**
     * Helper method which iterates through the sorted points and then scans through those points dependencies
     * If it finds the previous dependency of the given point is equal to the predecessor it will return that dependency
     *
     * @param sortedPoints
     * @param timePoint
     * @return
     */
    static Dependency findlastPoint(List<TimePoint> sortedPoints, TimePoint timePoint) {

        SchedulerException.checkNull(sortedPoints, timePoint);
        Iterator<TimePoint> sortedIterator = sortedPoints.iterator();
        Dependency priorpredecessor = null;
        while (sortedIterator.hasNext()) {

            TimePoint previousPoint = sortedIterator.next();
            for (Dependency dependency : timePoint.getDependencies()) {

                if (dependency.getPrevious().equals(previousPoint)) {

                    priorpredecessor = dependency;
                }
            }
        }

        return priorpredecessor;
    }

    static void placeinMap(LinkedHashMap<TimePoint, Long> map, TimePoint timepoint, Long duration) {

        SchedulerException.checkNull(map, timepoint, duration);
        map.put(timepoint, duration);
    }

    @Override
    public String toString() {

        return "This activity group has TimePoints of " + activitytimePoints;
    }
}
