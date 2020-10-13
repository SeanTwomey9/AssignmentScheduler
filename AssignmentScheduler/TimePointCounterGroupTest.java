package AssignmentScheduler;

import org.junit.Test;
import org.junit.Rule;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.junit.rules.ExpectedException;
import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

public class TimePointCounterGroupTest {

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Test
    public void createnullSet() {

        expected.expectCause(isA(SchedulerException.class));
        Set<TimePoint> s1 = null;
        TimePointCounterGroup t1 = TimePointCounterGroup.create(s1);
    }

    @Test
    public void create() {

        ActivityGroup ag2 = new ActivityGroup();
        Set<TimePoint> s2 = new HashSet<>();
        TimePoint t2 = new TimePoint(Activity.create(4, ag2, "Implementing new classes"), TimePoint.Side.BEGIN);
        t2.addPrevious(new TimePoint(Activity.create(6, ag2, "Implementing new classes"), TimePoint.Side.END));
        s2.add(t2);
        TimePointCounterGroup tc2 = TimePointCounterGroup.create(s2);
        assertEquals((tc2.getCounters().get(t2).getCounter()), 1); //TimePointCounterGroup should contain a TimePointCounter with
        //a counter of 1 since the TimePoint added has one dependency and its inDegree should have initialized the TimePointCounter properly
    }

    @Test
    public void isAnyIndependent() { //IsIndependent returns if a AssignmentScheduler.TimePoint has any dependencies

        ActivityGroup ag3 = new ActivityGroup();
        Set<TimePoint> s3 = new HashSet<>();
        TimePoint t3 = new TimePoint(Activity.create(9, ag3, "Writing J Unit"), TimePoint.Side.BEGIN);
        s3.add(t3);
        TimePointCounterGroup tc2 = TimePointCounterGroup.create(s3);
        assertTrue(tc2.isAnyIndependent()); //Returns true as the TimePointCounterGroup will contain t3 in the IndependentTimePoint list
    }

    @Test
    public void removeIndependent() {

        ActivityGroup ag4 = new ActivityGroup();
        Set<TimePoint> s4= new HashSet<>();
        TimePoint t4 = new TimePoint(Activity.create(91, ag4, "Errors!"), TimePoint.Side.BEGIN);
        s4.add(t4);
        TimePointCounterGroup tc4 = TimePointCounterGroup.create(s4);
        assertEquals(t4, tc4.removeIndependent()); //Will correctly return t4 as it is the independent time point which is removed from the independent list
    }

    @Test
    public void removeIndependentNoPoint() {

        expected.expectCause(isA(SchedulerException.class));
        ActivityGroup ag5 = new ActivityGroup();
        Set<TimePoint> s5 = new HashSet<>();
        TimePoint t5 = new TimePoint(Activity.create(91, ag5, "Errors!"), TimePoint.Side.BEGIN);
        t5.addPrevious(new TimePoint(Activity.create(6, ag5, "Implementing new classes"), TimePoint.Side.END));
        s5.add(t5);
        TimePointCounterGroup tc5 = TimePointCounterGroup.create(s5);
        tc5.removeIndependent(); //Should print out a SchedulerException as there is not currently an independent TimePoint in the list
    }

    @Test
    public void decrementCounters() {

        ActivityGroup ag6 = new ActivityGroup();
        Set<TimePoint> s6 = new HashSet<>();
        TimePoint t6 = new TimePoint(Activity.create(4, ag6, "Implementing new classes"), TimePoint.Side.BEGIN);
        t6.addPrevious(new TimePoint(Activity.create(6, ag6, "Implementing new classes"), TimePoint.Side.END));
        s6.add(t6);
        TimePointCounterGroup tc6 = TimePointCounterGroup.create(s6);
        assertEquals(1, tc6.getCounters().get(t6).getCounter());
        tc6.decrementCounters(s6);
        assertTrue(tc6.getIndependentTimePoints().contains(t6)); //Returns true as t6 was added to the independent list when its count was decremented from 1 to 0

    }

    @Test
    public void testToString() {
    }
}