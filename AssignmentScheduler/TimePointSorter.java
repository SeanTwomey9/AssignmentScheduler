package AssignmentScheduler;

import java.util.*;

/**
 * Class representing the sorting algorithm for ordering TimePoints according to their dependencies
 * Each TimePoint can only depend on the previous time points in the list
 * @author seantwomey
 */
final class TimePointSorter {


    static final List<TimePoint> sort(Set<TimePoint> timePoints) {

        SchedulerException.checkNull(timePoints);
        List<TimePoint> sorted = new LinkedList<>();
        TimePointCounterGroup counter = TimePointCounterGroup.create(timePoints);
        TimePointSuccessorGroup s = TimePointSuccessorGroup.create(timePoints);


        while (counter.isAnyIndependent()) {

            TimePoint current;
            current = counter.removeIndependent();
            sorted.add(current);
            counter.decrementCounters(s.getSuccessors(current));
        }

       assert !(sorted.size() < timePoints.size()): SchedulerException.regularException(SchedulerException.Error.ERROR_IN_DEPENDENCIES);

        return sorted;
    }
}
