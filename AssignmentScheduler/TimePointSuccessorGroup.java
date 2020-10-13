package AssignmentScheduler;
import java.util.*;
import java.util.Map;
import java.util.Set;

/**
 * Successor group of a TimePoint
 * @author seantwomey
 */
final class TimePointSuccessorGroup {

    //Map containing successors
    private final Map<TimePoint, Set<TimePoint>> successors;

    /**
     * Constructor initializing the map of successors
     * @param successors
     */
    private TimePointSuccessorGroup(Map<TimePoint, Set<TimePoint>> successors) {

        this.successors = successors;
    }


    /**
     * Returns the successors of the given TimePoint by consulting the successors map
     * @param timePoint
     * @return set of successors
     */
    final Set<TimePoint> getSuccessors(TimePoint timePoint) {

        SchedulerException.checkNull(timePoint);
       return successors.get(timePoint);

    }


    /**
     * Create method to initialize a successor group
     * @param timePoints
     * @return
     */
    static final TimePointSuccessorGroup create(Set<TimePoint> timePoints) {

        SchedulerException.checkNull(timePoints);
        Map<TimePoint, Set<TimePoint>> successors = new HashMap<>();
        for(TimePoint timepoint: timePoints) {

            for(TimePoint prevtimepoint: timepoint.previousTimePoints()) {

                if(!successors.containsKey(prevtimepoint)) {

                    Set<TimePoint> newPoints = new HashSet<>();
                    newPoints.add(timepoint);
                    successors.put(prevtimepoint,newPoints); //adds new mapping of successors if it didn't already exist
                }

                else {
                    successors.get(prevtimepoint).add(timepoint); //if it already exists, adds timepoint to that pre-existing map
                }
            }
        }
        return new TimePointSuccessorGroup(successors);
    }



    @Override
    public String toString() {

        StringBuilder sbuild = new StringBuilder();
        sbuild.append("This TimePointSuccessorGroup has successors of " + successors);
        return sbuild.toString();
    }
}
