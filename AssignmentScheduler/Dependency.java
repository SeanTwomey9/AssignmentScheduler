package AssignmentScheduler;

import java.util.Objects;

/**
 * Represents notion that allows a time point to occur after a specific length of time has passed
 *
 * @author seantwomey
 */
public final class Dependency {

    //the previous time point
    private final TimePoint previous;
    //length of time required to pass from previous time point
    private final long duration;

    /**
     * AssignmentScheduler.Dependency constructor, assigns previous time point and require duration
     *
     * @param previous
     * @param duration Pre-condition: Previous time point cannot be null
     */
    Dependency(TimePoint previous, long duration) {

        assert Objects.nonNull(previous) : "AssignmentScheduler.Dependency cannot be added if previous AssignmentScheduler.TimePoint is null";

        this.previous = previous;
        this.duration = duration;
    }


    /**
     * Additional constructor setting duration to zero
     *
     * @param previous Pre-condition: Previous time point cannot be null
     * @param previous
     */
    Dependency(TimePoint previous) {

        assert Objects.nonNull(previous) : "AssignmentScheduler.Dependency cannot be added if previous AssignmentScheduler.TimePoint is null";

        duration = 0;
        this.previous = previous;
    }

    /**
     * Getter for previous time point
     *
     * @return previous
     */
    public final TimePoint getPrevious() {

        return previous;
    }

    /**
     * Getter for duration
     *
     * @return duration
     */
    public final long getDuration() {

        return duration;
    }

    /**
     * Returns string representation of previous AssignmentScheduler.TimePoint and duration
     *
     * @return
     */

    @Override
    public String toString() {

        return "This dependency has a previous AssignmentScheduler.TimePoint of " + getPrevious() + " and a duration of " + getDuration();

    }

}

