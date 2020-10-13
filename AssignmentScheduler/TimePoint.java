package AssignmentScheduler;


import java.util.Set;
import java.util.*;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.lang.reflect.Constructor;

/**
 * Represents a point in time, specifically the beginning and end of an activity
 *
 * @author seantwomey
 */
public final class TimePoint {

    //Set of all previous time points which preceded the current time point
    private final Set<Dependency> dependencies = new HashSet<Dependency>();
    //indicates if dependencies can still be added to the AssignmentScheduler.TimePoint
    private boolean frozen = false;
    private boolean hasDependency = false;
    private final Activity activity;
    private final Side side;
    private final String description;

    TimePoint(Activity activity, Side side) {

        SchedulerException.checkNull();
        this.activity = activity;
        this.side = side;
        description = activity.getDescription() + ":" + side;

    }

    public final Activity getActivity() {

        return activity;
    }

    public final Side getSide() {

        return side;
    }

    public final String getDescription() {

        return description;
    }

    public enum Side {

        BEGIN, END
    }
    /**
     * Adds dependency to time point
     *
     * @param previousTimePoint
     * @param duration
     * @return true or false for addition of dependency
     * Pre condition: AssignmentScheduler.TimePoint is not frozen, previous AssignmentScheduler.TimePoint is not null, and duration is not negative
     */
    final boolean addPrevious(TimePoint previousTimePoint, long duration) {


        //assert statements to verify pre conditions
        assert !frozen: (new SchedulerException.Builder(SchedulerException.Error.POINT_FROZEN).setTimePoint(this).setDuration(duration).build());
        assert !(duration < 0): new SchedulerException.Builder(SchedulerException.Error.INVALID_DURATION).setTimePoint(this).setDuration(duration).build();
        assert Objects.nonNull(previousTimePoint) : new SchedulerException.Builder(SchedulerException.Error.UNEXPECTED_NULL).build();

        hasDependency = true;
        return dependencies.add(new Dependency(previousTimePoint, duration));

    }

    /**
     * Adds dependency with zero duration
     *
     * @param previousTimePoint
     * @return Success or failure of addition
     * Pre condition: AssignmentScheduler.TimePoint is not frozen, previous AssignmentScheduler.TimePoint is not null
     */
    final boolean addPrevious(TimePoint previousTimePoint) {

        //assert statements to verify pre conditions
        assert !frozen: new SchedulerException.Builder(SchedulerException.Error.POINT_FROZEN).setTimePoint(this).build();
        assert Objects.nonNull(previousTimePoint) : new SchedulerException.Builder(SchedulerException.Error.UNEXPECTED_NULL).setTimePoint(this).build();

        hasDependency = true;
        return dependencies.add(new Dependency(previousTimePoint));

    }

    /**
     * Freezes the AssignmentScheduler.TimePoint
     */
    public final void freeze() {

        frozen = true;
    }

    /**
     * Returns whether or not the AssignmentScheduler.TimePoint is frozen
     *
     * @return True/False
     */
    public final boolean isFrozen() {

        return frozen;
    }

    /**
     * Returns a copy of the dependencies
     *
     * @return duplicateSet- copied set of dependencies
     */
    public final Set<Dependency> getDependencies() {

        HashSet<Dependency> duplicateSet = new HashSet<Dependency>();
        duplicateSet.addAll(dependencies);
        return duplicateSet;
    }

    /**
     * Returns a set with the previous points in all the dependencies
     *
     * @return set with previous points in all dependencies
     */
    public final Set<TimePoint> previousTimePoints() {

        return dependencies.stream().map(Dependency::getPrevious).collect(Collectors.toSet());

    }

    /**
     * Returns the number of dependencies
     *
     * @return number of dependencies
     */
    public final int inDegree() {

        return dependencies.size();
    }

    /**
     * Returns if a AssignmentScheduler.TimePoint has any dependencies
     *
     * @return
     */
    public final boolean isIndependent() {

        return !hasDependency;

    }

    /**
     * Returns a string representation of the AssignmentScheduler.TimePoint, omitting depedendencies
     *
     * @return string representing AssignmentScheduler.TimePoint
     */
    public final String toSimpleString() {

        if (isFrozen()) {

            return "The AssignmentScheduler.TimePoint is currently frozen, no new dependencies can be added at this time.";
        } else {

            return "The AssignmentScheduler.TimePoint is not currently frozen, new dependencies can be added at this time.";
        }
    }

    //@Override
    /*public String toString() {

        StringBuilder sbuild = new StringBuilder();
        sbuild.append("This TimePoint has dependencies of " + getDependencies());
        sbuild.append(", a degree of " + inDegree());
        sbuild.append(", an activity of " + getActivity());
        sbuild.append(" and a side of " + getSide() + ".");

        return sbuild.toString();

    }*/

}
