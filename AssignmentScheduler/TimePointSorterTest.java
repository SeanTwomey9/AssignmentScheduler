package AssignmentScheduler;

import org.junit.rules.ExpectedException;
import org.junit.Test;
import org.junit.Rule;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.junit.rules.ExpectedException;
import static org.hamcrest.CoreMatchers.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TimePointSorterTest {

    @Rule
    public ExpectedException expected = ExpectedException.none();
    @Test
    public void sortNull() {

        expected.expectCause(isA(SchedulerException.class));
        ActivityGroup ag1 = new ActivityGroup();
        Set<TimePoint> s1 = new HashSet<>();
        TimePoint t1 = new TimePoint(Activity.create(4, ag1, "Implementing new classes"), TimePoint.Side.BEGIN);
        t1.addPrevious(new TimePoint(Activity.create(6, ag1, "Implementing new classes"), TimePoint.Side.END));
        s1 = null;
        //TimePointSuccessorGroup ts1 = TimePointSuccessorGroup.create(s1);
        //TimePointCounterGroup tc1 = TimePointCounterGroup.create(s1);

        System.out.println(TimePointSorter.sort(s1)); //prints out a SchedulerException due to null set
    }

    @Test
    public void sort() {

        expected.expectCause(isA(SchedulerException.class));
        ActivityGroup ag2 = new ActivityGroup();
        Set<TimePoint> s2 = new HashSet<>();
        TimePoint t2 = new TimePoint(Activity.create(4, ag2, "Implementing new classes"), TimePoint.Side.BEGIN);
        t2.addPrevious(new TimePoint(Activity.create(6, ag2, "Implementing new classes"), TimePoint.Side.END));
        TimePoint t3 = new TimePoint(Activity.create(4, ag2, "Not working"), TimePoint.Side.BEGIN);
        s2.add(t2);
        s2.add(t3);
        TimePointSuccessorGroup ts1 = TimePointSuccessorGroup.create(s2);
        TimePointCounterGroup tc1 = TimePointCounterGroup.create(s2);

        System.out.println(TimePointSorter.sort(s2));
    }
}