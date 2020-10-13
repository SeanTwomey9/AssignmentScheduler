package AssignmentScheduler;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents error handling for the scheduler
 * @author seantwomey
 */
public final class SchedulerException extends Exception {

    //Unique serial version UID
    private static final long serialVersionUID = 4262;
    //Error code associated with exception
    private final Error error;
    //Primary TimePoint
    private final TimePoint timePoint;
    //Additional TimePoint
    private final TimePoint otherTimePoint;
    //Associated duration
    private final long duration;

    /**
     * Constructor for a SchedulerException
     * Passes instance of the nested Builder class to construct the exception
     * Initializes private variables
     * @param builder
     */
    private SchedulerException(Builder builder) {

        checkNull(builder);
        this.error = builder.getError();
        this.timePoint = builder.getTimePoint();
        this.otherTimePoint = builder.getOtherTimePoint();
        this.duration = builder.getDuration();

    }

    /**
     * Enum storing error codes pertaining to frozen TimePoints, durations, dependencies, etc.
     */
    public enum Error {

        POINT_FROZEN, INVALID_DURATION, TIME_POINT_EXISTS, POINT_NOT_FROZEN, INVALID_DEPENDENCY, UNEXPECTED_NULL, INVALID_COUNTER, NO_SUCH_POINT,
        ERROR_IN_DEPENDENCIES, CIRCULAR_DEPENDENCY

    }

    /**
     * Returns the exception's error code
     * @return error
     */
    public final Error getError() {

        return error;
    }

    /**
     * Returns the TimePoint associated with the exception
     * @return timePoint
     */
    public final TimePoint getTimePoint() {

        return timePoint;
    }

    /**
     * Returns the other TimePoint associated with the exception
     * @return
     */
    public final TimePoint getOtherTimePoint() {

        return otherTimePoint;
    }

    /**
     * Returns the duration of the dependency associated with the exception
     * @return duration
     */
    public final long getDuration() {

        return duration;
    }

    /**
     * Helper method throwing a SchedulerException and setting the appropriate TimePoint to the exception
     * @param error
     * @param timepoint
     * @return exception with TimePoint
     */
    static SchedulerException exceptionwithTimePoint(Error error, TimePoint timepoint) {

        return new SchedulerException.Builder(error).setTimePoint(timepoint).build();
    }

    /**
     * Helper method throwing a SchedulerException with just the error code
     * @param error
     * @return exception with only error code
     */
    static SchedulerException regularException(Error error) {

        return new SchedulerException.Builder(error).build();
    }

    /**
     * Helper method throwing a SchedulerException assigning the TimePoint and duration
     * @param error
     * @param duration
     * @param timepoint
     * @return exception with TimePoint and duration
     */
    static SchedulerException exceptionwithEverything(Error error, long duration, TimePoint timepoint) {

        return new SchedulerException.Builder(error).setDuration(duration).setTimePoint(timepoint).build();
    }

    /**
     * Helper method
     * @param objects
     * @return
     */
    static boolean everythingnonNull(Object... objects) {

        return Arrays.stream(objects).allMatch(Objects::nonNull);
    }

    static void checkNull(Object...objects) {

        assert(everythingnonNull(objects)): regularException(Error.UNEXPECTED_NULL);
    }

    /**
     * Nested class which uses Builder object to construct a SchedulerException
     * @author seantwomey
     */
    final static class Builder {

        //Error code for Builder
        private final Error error;
        //Duration of Builder
        private long duration;
        //Primary TimePoint of Builder
        private TimePoint timePoint;
        //Secondary TimePoint of Builder
        private TimePoint otherTimePoint;

        /**
         * Constructor initializing the error of a Builder object
         * @param error
         */
        Builder(Error error) {

            this.error = error;
        }

        /**
         * Returns the error code of the Builder
         * @return error
         */
        Error getError() {

            return error;
        }

        /**
         * Returns the duration of the Builder
         * @return duration
         */
        long getDuration() {

            return duration;
        }

        /**
         * Sets the Builder's duration to a new value
         * @param duration
         * @return the Builder
         */
        Builder setDuration(long duration) {

            this.duration = duration;
            return this;
        }

        /**
         * Retrieves the Builder's TimePoint
         * @return
         */
        TimePoint getTimePoint() {

            return timePoint;
        }

        /**
         * Sets the Builder's TimePoint to a new TimePoint
         * @param timePoint
         * @return the Builder
         */
        Builder setTimePoint(TimePoint timePoint) {

            this.timePoint = timePoint;
            return this;
        }

        /**
         * Retrieves the secondary TimePoint for the Builder
         * @return otherTimePoint
         */
        TimePoint getOtherTimePoint() {

            return otherTimePoint;
        }

        /**
         * Adjusts the secondary TimePoint of the Builder
         * @param otherTimePoint
         * @return the Builder
         */
        Builder setOtherTimePoint(TimePoint otherTimePoint) {

            this.otherTimePoint = otherTimePoint;
            return this;
        }

        /**
         * Returns a newly instantiated SchedulerException with appropriate parameters
         * @return
         */
        final SchedulerException build() {

            return new SchedulerException(this);
        }

        @Override
        public String toString() {

            StringBuilder sbuild = new StringBuilder();
            assert Objects.isNull(error): sbuild.append("This builder has an error code of " + getError());
            assert Objects.isNull(duration):sbuild.append(", a duration of " + getDuration());
            assert Objects.isNull(timePoint):sbuild.append(", a TimePoint of " + getTimePoint());
            assert Objects.isNull(otherTimePoint):sbuild.append(" and a other TimePoint of " + getOtherTimePoint() + ".");

            return sbuild.toString();
        }
    }

    /**
     * toString representation of a SchedulerException with appropriate data values
     * @return String with error code, timePoint, otherTimePoint, and duration
     */
    @Override
    public String toString() {

        return "This exception's error code is " + error + ", has a TimePoint of " + timePoint + ", other TimePoint of " + otherTimePoint +
                " and a duration of " + duration;
    }

}

