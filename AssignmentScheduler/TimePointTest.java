package AssignmentScheduler;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

class TimePointTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void badTimePoint() {
        
        expectedException.expectCause(isA(SchedulerException.class));
        ActivityGroup ag0 = new ActivityGroup();
        TimePoint test= new TimePoint(Activity.create(1, ag0, "NULL!"), null);
    }
    @Test
    public void testfreeze() {

        ActivityGroup ag1 = new ActivityGroup();
        TimePoint test1 = new TimePoint(Activity.create(4, ag1, "Testing"), TimePoint.Side.BEGIN);
        test1.freeze();
        assertTrue(test1.isFrozen());
    }

    @Test
    public void testisFrozen() {

        ActivityGroup ag2 = new ActivityGroup();
        TimePoint test2 = new TimePoint(Activity.create(6, ag2, "Building the server"), TimePoint.Side.BEGIN);
        test2.freeze();
        assertTrue(test2.isFrozen());
    }

    @Test
    public void testgetDependencies() {

        ActivityGroup ag3 = new ActivityGroup();
        TimePoint test3 = new TimePoint(Activity.create(4, ag3, "Testing"), TimePoint.Side.BEGIN);
        ActivityGroup ag4 = new ActivityGroup();
        TimePoint test4 = new TimePoint(Activity.create(8, ag4, "Compiling"), TimePoint.Side.BEGIN);
        ActivityGroup ag5 = new ActivityGroup();
        TimePoint test5 = new TimePoint(Activity.create(2, ag5, "Executing"), TimePoint.Side.BEGIN);
        ActivityGroup ag6 = new ActivityGroup();
        TimePoint testAnother = new TimePoint(Activity.create(4, ag6, "Producing"), TimePoint.Side.BEGIN);
        test3.addPrevious(test4, 8);
        test3.addPrevious(test5, 16);
        test3.addPrevious(testAnother, 24);
        System.out.println(test3.getDependencies()); /*Prints out the sequence of dependencies with their respective
        TimePoints and durations in a list separated by commas*/
    }

    @Test
    public void testpreviousTimePoints() {
    }

    @Test
    public void testinDegree() {

        ActivityGroup ag7 = new ActivityGroup();
        TimePoint test6 = new TimePoint(Activity.create(10, ag7, "Writing scripts"), TimePoint.Side.BEGIN);
        test6.addPrevious(test6, 6);
        assertEquals(test6.inDegree(), 1);
    }

    @Test
    public void testtoSimpleString() {

        //Case where the AssignmentScheduler.TimePoint is already frozen
        ActivityGroup ag8 = new ActivityGroup();
        TimePoint test7 = new TimePoint(Activity.create(4, ag8, "Meeting with the team"), TimePoint.Side.BEGIN);
        test7.freeze();
        String frozenMessage = "The AssignmentScheduler.TimePoint is currently frozen, no new dependencies can be added at this time.";
        assertEquals(test7.toSimpleString(), frozenMessage);

        //Case where the AssignmentScheduler.TimePoint is not currently frozen and can accept new dependencies
        ActivityGroup ag9 = new ActivityGroup();
        TimePoint test8 = new TimePoint(Activity.create(1, ag9, "Coffee break"), TimePoint.Side.BEGIN);
        String nonFrozen = "The AssignmentScheduler.TimePoint is not currently frozen, new dependencies can be added at this time.";
        assertEquals(test8.toSimpleString(), nonFrozen);
    }


    @Test
    public void testisIndependent() {

        //Case where no dependencies have been added yet, returns true since none have been
        ActivityGroup ag10 = new ActivityGroup();
        TimePoint test10 = new TimePoint(Activity.create(4, ag10, "Scrum meeting"), TimePoint.Side.BEGIN);
        assertTrue(test10.isIndependent());

        //Case where dependencies have been added, and thus will return false
        ActivityGroup ag11 = new ActivityGroup();
        TimePoint test11 = new TimePoint(Activity.create(4, ag11, "Testing"), TimePoint.Side.BEGIN);
        TimePoint test12 = new TimePoint(Activity.create(4, ag11, "Testing"), TimePoint.Side.END);
        test11.addPrevious(test12, 99);
        assertFalse(test11.isIndependent());
    }
}