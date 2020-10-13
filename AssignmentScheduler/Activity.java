package AssignmentScheduler;

import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a single activity
 *
 * @author seantwomey
 */
public final class Activity {

    //Represents a set of activities
    private final ActivityGroup activities;
    //Description of the activity
    private String description;
    //Duration of the activity
    private long duration;
    //Indicates if the activity is frozen
    private boolean frozen;
    //EnumMap storing TimePoints at both sides of an activity
    private final EnumMap<TimePoint.Side, TimePoint> map = new EnumMap<TimePoint.Side, TimePoint>(TimePoint.Side.class);
    private TimePoint beginPoint;
    private TimePoint endPoint;

    /**
     * Constructor for an Activity, initializes activity with private variables
     * Creates two new TimePoints for the beginning and end of an activity
     * Adds dependency on initial TimePoint with the activity's duration
     *
     * @param activities
     * @param description
     * @param duration
     */
    private Activity(ActivityGroup activities, String description, long duration) {

        this.activities = activities;
        this.description = description;
        this.duration = duration;
        beginPoint = new TimePoint(this, TimePoint.Side.BEGIN);
        endPoint = new TimePoint(this, TimePoint.Side.END);
        endPoint.addPrevious(beginPoint, duration);
        map.put(TimePoint.Side.BEGIN, beginPoint);
        map.put(TimePoint.Side.END, endPoint);
    }

    /**
     * Simple getter for the Activity's begin TimePoint
     *
     * @return beginPoint
     */
    TimePoint getBeginPoint() {

        return beginPoint;
    }

    /**
     * Getter for Activity's end TimePoint
     *
     * @return
     */
    TimePoint getEndPoint() {

        return endPoint;
    }

    /**
     * Getter for the Activity's duration
     *
     * @return duration
     */
    long getDuration() {

        return duration;
    }

    /**
     * Getter for the Activity's description
     *
     * @return
     */
    String getDescription() {

        return description;
    }

    /**
     * Getter for the Activity's ActivityGroup
     *
     * @return
     */
    ActivityGroup getActivities() {

        return activities;
    }

    /**
     * Boolean checking if the Activity is frozen
     *
     * @return T/F
     */
    boolean activityFrozen() {

        return frozen;
    }


    /**
     * Returns specific TimePoint at specified side
     *
     * @param side
     * @return TimePoint at side of the TimePoint.Side object from EnumMap
     */
    final TimePoint extremePoint(TimePoint.Side side) {

       SchedulerException.checkNull(side);
        return map.get(side);

    }

    /**
     * Returns dependencies for respective side of the TimePoint
     *
     * @param side
     * @return set of dependencies for respective TimePoint.Side
     */
    public final Set<Dependency> dependencies(TimePoint.Side side) {

        SchedulerException.checkNull(side);
        return extremePoint(side).getDependencies();
    }

    /**
     * Instantiates an activity with appropriate duration, ActivityGroup, and description after checking preconditions
     *
     * @param duration
     * @param activities
     * @param description
     * @return
     */
    public static final Activity create(long duration, ActivityGroup activities, String description) {

        if (duration < 0) {

            throw new IllegalArgumentException("Duration cannot be negative.", SchedulerException.regularException(SchedulerException.Error.INVALID_DURATION)); //Invalid duration SchedulerException passed into IllegalArgumentException
        }
        if (!Objects.nonNull(activities)) {

            throw new IllegalArgumentException("ActivityGroup cannot be null.", SchedulerException.regularException(SchedulerException.Error.UNEXPECTED_NULL)); //Unexpected null SchedulerException passed into IllegalArgumentException
        }
        if (!Objects.nonNull(description)) {

            description = ""; //If description is null it is assigned to an empty string
        }

        return new Activity(activities, description, duration); //New activity instantiated after clearing preconditions
    }

    /**
     * Freezes activity and associated TimePoints, then adds TimePoints to ActivityGroup
     */
    public final void freeze() {

        Collection<TimePoint> collectPoints = map.values();
        frozen = true;
        for (TimePoint t : collectPoints) {

            t.freeze();
        }
        activities.addTimePoints(collectPoints);
    }

    /**
     * Adds dependency with duration of 0 from other TimePoint to provided side
     *
     * @param side
     * @param other
     * @return Whether or not dependency was properly added to side
     */
    public final boolean addDependency(TimePoint.Side side, TimePoint other) {

        if (!Objects.nonNull(side)) {

            throw new IllegalArgumentException("TimePoint side cannot be null.", SchedulerException.regularException(SchedulerException.Error.UNEXPECTED_NULL)); //appropriate exception thrown for unexpected null
        }
        if (!Objects.nonNull(other)) {

            throw new IllegalArgumentException("Other TimePoint cannot be null.", SchedulerException.regularException(SchedulerException.Error.UNEXPECTED_NULL)); //appropriate exception thrown for unexpected null
        }
        if (frozen) {

            throw new IllegalArgumentException("Activity cannot be frozen.", SchedulerException.exceptionwithEverything(SchedulerException.Error.INVALID_DEPENDENCY, duration, other));
        }

        return map.get(side).addPrevious(other);
    }

    /**
     * Returns a String representation of an activity
     *
     * @return String with duration, ActivityGroup, and description
     */

    @Override
    public String toString() {

        return "This activity has a duration of " + getDuration() + ", ActivityGroup of " + getActivities() + " and a description of: \"" + getDescription() + "\".";
    }
}
