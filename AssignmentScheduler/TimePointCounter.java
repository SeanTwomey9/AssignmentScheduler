package AssignmentScheduler;

/**
 * Represents the counter of a TimePoint
 * @author seantwomey
 */
final class TimePointCounter {

    private int counter;
    /**
     * Constructs an instance of a TimePointCounter
     * Pre-condition: Counter cannot be negative
     * @param counter
     */
    TimePointCounter(int counter) {

        assert !(counter < 0): new SchedulerException.Builder(SchedulerException.Error.INVALID_COUNTER).build();
        this.counter = counter;
    }

    /**
     * Helper method to return the counter associated with a TimePoint counter
     * @return counter
     */
    int getCounter() {

        return counter;
    }

    /**
     * Helper method to set the counter of a TimePointCounter
     * @param counter
     */
    void setCounter(int counter) {

        this.counter = counter;
    }

    /**
     * Decrements the counter value
     * Pre-condition: Counter must be positive
     * @return
     */
    final int decrement() {

        assert !(counter < 0): new SchedulerException.Builder(SchedulerException.Error.INVALID_COUNTER).build();
        return --counter;
    }

    /**
     * Returns whether or not the counter is currently 0
     * @return T/F
     */
    final boolean isIndependent() {

        return counter == 0;
    }

    @Override
    public String toString() {

        StringBuilder sbuild = new StringBuilder();
        sbuild.append("This TimePointCounter has a counter of " + getCounter());

        if(this.isIndependent()) {

            sbuild.append(" and is independent.");
        }

        else {

            sbuild.append(" and is not independent.");
        }

        return sbuild.toString();
    }

}
