package AssignmentScheduler;

import java.sql.Time;

import static org.junit.Assert.*;

public class SchedulerExceptionTest {

    @org.junit.Test
    public void testgetError() {

        SchedulerException test1 = ((new SchedulerException.Builder(SchedulerException.Error.INVALID_DURATION).build())); //test passes as correct error code of POINT_FROZEN is returned
        assertEquals(SchedulerException.Error.INVALID_DURATION, test1.getError());
    }

    @org.junit.Test
    public void testgetTimePoint() {

        SchedulerException.Builder test2 = new SchedulerException.Builder(SchedulerException.Error.POINT_FROZEN);
        ActivityGroup ag2 = new ActivityGroup();
        Activity a2 = Activity.create(4, ag2, "Fixing errors");
        TimePoint t2 = new TimePoint(a2, TimePoint.Side.BEGIN);
        test2.setTimePoint(t2);
        assertEquals(t2, test2.getTimePoint());
    }

    @org.junit.Test
    public void testgetOtherTimePoint() {

        SchedulerException.Builder test3 = new SchedulerException.Builder(SchedulerException.Error.POINT_FROZEN);
        ActivityGroup ag3 = new ActivityGroup();
        Activity a3 = Activity.create(4, ag3, "Making new methods");
        TimePoint t3 = new TimePoint(a3, TimePoint.Side.BEGIN);
        test3.setTimePoint(t3);
        assertEquals(t3, test3.getTimePoint());

    }

    @org.junit.Test
    public void testgetDuration() {

        SchedulerException.Builder test5 = new SchedulerException.Builder(SchedulerException.Error.TIME_POINT_EXISTS);
        test5.setDuration(4);
        SchedulerException exception5 = test5.build();
        assertEquals(4, exception5.getDuration());


    }

    @org.junit.Test
    public void testToString() { //compares the expected output of the exception's toString call to the actual resulting String, it's a match!

        SchedulerException test5 = (new SchedulerException.Builder(SchedulerException.Error.INVALID_DEPENDENCY).build());
        assertEquals("This exception's error code is " + test5.getError() + ", has a TimePoint of " + test5.getTimePoint() + ", other TimePoint of " + test5.getOtherTimePoint() + " and a duration of " + test5.getDuration(), test5.toString());
    }
}