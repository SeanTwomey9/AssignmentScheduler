package AssignmentScheduler;

import static org.junit.jupiter.api.Assertions.*;

class DependencyTest {

    @org.junit.jupiter.api.Test
    public void testgetPrevious() {

        ActivityGroup ag1 = new ActivityGroup();
        TimePoint test1 = new TimePoint(Activity.create(4, ag1, "Lunch break"), TimePoint.Side.BEGIN);
        Dependency deptTest1 = new Dependency(test1, 10);
        assertEquals(test1, deptTest1.getPrevious());

    }

    @org.junit.jupiter.api.Test
    public void testgetDuration() {

        ActivityGroup ag2 = new ActivityGroup();
        TimePoint test2 = new TimePoint(Activity.create(2, ag2, "Planning stories"), TimePoint.Side.BEGIN);
        Dependency depTest2 = new Dependency(test2, 2);
        assertEquals(depTest2.getDuration(), 2);
    }

    @org.junit.jupiter.api.Test
    public void testToString() {

        ActivityGroup ag3 = new ActivityGroup();
        TimePoint test3 = new TimePoint(Activity.create(4, ag3, "Meeting with investors"), TimePoint.Side.BEGIN);
        Dependency depTest3 = new Dependency(test3, 4);
        //Comparing the expected output of the dependency's toString method to the actual result, it's a match!
        assertEquals("This dependency has a previous AssignmentScheduler.TimePoint of " + depTest3.getPrevious() + " and a duration " +
                "of " + depTest3.getDuration(), depTest3.toString());

    }
}