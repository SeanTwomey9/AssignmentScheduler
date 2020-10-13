package AssignmentScheduler;

import org.junit.Rule;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;

import static org.junit.Assert.*;

public class TimePointSuccessorGroupTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void create() {

        ActivityGroup ag1 = new ActivityGroup();
        Set<TimePoint> s1 = new HashSet<>();
        TimePoint t1 = new TimePoint(Activity.create(4, ag1, "Implementing new classes"), TimePoint.Side.BEGIN);
        t1.addPrevious(new TimePoint(Activity.create(6, ag1, "Implementing new classes"), TimePoint.Side.END));
        s1.add(t1);
        TimePointSuccessorGroup ts1 = TimePointSuccessorGroup.create(s1);
    }

    @Test
    public void getSuccessors() {

        ActivityGroup ag2 = new ActivityGroup();
        Set<TimePoint> s2 = new HashSet<>();
        TimePoint t2 = new TimePoint(Activity.create(4, ag2, "Implementing new classes"), TimePoint.Side.BEGIN);
        TimePoint othert2 = new TimePoint(Activity.create(6, ag2, "Implementing new classes"), TimePoint.Side.END);
        t2.addPrevious(othert2);
        s2.add(t2);
        TimePointSuccessorGroup ts2 = TimePointSuccessorGroup.create(s2);
        assertEquals("[" + othert2 + "]", ts2.getSuccessors(t2).toString()); //Correctly assigns othert2 as a successor of T2 since othert2 was added as a dependency on T2
    }

    @Test
    public void testToString() {

        ActivityGroup ag3 = new ActivityGroup();
        Set<TimePoint> s3 = new HashSet<>();
        TimePoint t3 = new TimePoint(Activity.create(4, ag3, "Implementing new classes"), TimePoint.Side.BEGIN);
        TimePoint othert3 = new TimePoint(Activity.create(6, ag3, "Implementing new classes"), TimePoint.Side.END);
        t3.addPrevious(othert3);
        s3.add(t3);
        TimePointSuccessorGroup ts3 = TimePointSuccessorGroup.create(s3);
        System.out.println(ts3.toString());
    }

    @Test
    public void getsuccessorsNull() {

        expectedException.expectCause(isA(SchedulerException.class));
        ActivityGroup ag4 = new ActivityGroup();
        Set<TimePoint> s4 = new HashSet<>();
        TimePoint t4 = new TimePoint(Activity.create(4, ag4, "Implementing new classes"), TimePoint.Side.BEGIN);
        TimePoint othert4 = new TimePoint(Activity.create(6, ag4, "Implementing new classes"), TimePoint.Side.END);
        t4.addPrevious(othert4);
        s4.add(t4);
        TimePointSuccessorGroup ts4 = TimePointSuccessorGroup.create(s4);
        ts4.getSuccessors(null);
    }

    @Test
    public void createNull() {

        expectedException.expectCause(isA(SchedulerException.class));
        ActivityGroup ag5 = new ActivityGroup();
        Set<TimePoint> s5 = null;
        TimePoint t5 = new TimePoint(Activity.create(4, ag5, "Implementing new classes"), TimePoint.Side.BEGIN);
        t5.addPrevious(new TimePoint(Activity.create(6, ag5, "Implementing new classes"), TimePoint.Side.END));
        TimePointSuccessorGroup ts1 = TimePointSuccessorGroup.create(s5);
    }
}