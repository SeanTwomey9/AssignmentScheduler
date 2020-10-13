package AssignmentScheduler;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ActivityGroupTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void addTimePointsnullTest1()  {

        expectedException.expectCause(isA(SchedulerException.class));
        HashSet<TimePoint> timePoints = new HashSet<>();
        ActivityGroup a1 = new ActivityGroup();
        Activity activity1 = Activity.create(49, a1, "Writing the last tester method!");
        timePoints = null;
        a1.addTimePoints(timePoints); //returns appropriate SchedulerException for a null set of TimePoints
    }

    @Test
    public void addTimePointsnullTest2() {

        expectedException.expectCause(isA(SchedulerException.class));
        HashSet<TimePoint> timePoints = new HashSet<>();
        ActivityGroup a2 = new ActivityGroup();
        Activity activity1 = Activity.create(49, a2, "Writing the last tester method!");
        timePoints.add(null);
        a2.addTimePoints(timePoints); //returns appropriate SchedulerException for a null member of the set
    }

    @Test
    public void addTimePointsfrozenTest()  {

        expectedException.expectCause(isA(SchedulerException.class));
        HashSet<TimePoint> timePoints = new HashSet<>();
        ActivityGroup a3 = new ActivityGroup();
        Activity activity3 = Activity.create(49, a3, "Writing the last tester method!");
        TimePoint t3 = new TimePoint(activity3, TimePoint.Side.BEGIN);
        assertFalse(t3.isFrozen());
        System.out.println(t3);
        timePoints.add(t3);
        a3.addTimePoints(timePoints); //returns appropriate SchedulerException for a non-frozen TimePoint and the resulting TimePoint
    }

    @Test
    public void addTimePointsalreadyContainsTest()  {

        expectedException.expectCause(isA(SchedulerException.class));
        HashSet<TimePoint> timePoints = new HashSet<>();
        ActivityGroup a4= new ActivityGroup();
        Activity activity4 = Activity.create(49, a4, "Writing the last tester method!");
        TimePoint t4 = new TimePoint(activity4, TimePoint.Side.BEGIN);
        activity4.freeze();
        timePoints.add(t4);
        a4.addTimePoints(timePoints); //returns appropriate SchedulerException for a TimePoint already added and the resulting TimePoint
    }

    @Test
    public void addTimePointTest() {

        HashSet<TimePoint> timePoints = new HashSet<>();
        ActivityGroup a5= new ActivityGroup();
        Activity activity5 = Activity.create(49, a5, "Writing the last tester method!");
        TimePoint t5 = new TimePoint(activity5, TimePoint.Side.BEGIN);
        t5.freeze();
        timePoints.add(t5);
        a5.addTimePoints(timePoints);
    }

    @Test
    public void testtoString() {

        HashSet<TimePoint> timePoints = new HashSet<>();
        ActivityGroup a6= new ActivityGroup();
        Activity activity6 = Activity.create(50, a6, "Fixing toString!");
        TimePoint t6 = new TimePoint(activity6, TimePoint.Side.BEGIN);
        t6.freeze();
        timePoints.add(t6);
        a6.addTimePoints(timePoints);
        assertEquals("This activity group has TimePoints of [" + t6 + "]", a6.toString());
    }

    @Test
    public void testtimeLine() {

        ActivityGroup test = new ActivityGroup();
        Activity InstallOS = Activity.create(10, test, "Installing Operating System");
        Activity InstallDB = Activity.create(4, test, "Installing Database");
        TimePoint osBegin = new TimePoint(InstallOS, TimePoint.Side.BEGIN);
        TimePoint dbBegin = new TimePoint(InstallDB, TimePoint.Side.BEGIN);
        TimePoint dbEnd = new TimePoint(InstallDB, TimePoint.Side.END);
        dbEnd.addPrevious(osBegin, 7);
        dbEnd.addPrevious(dbBegin, 1);
        dbBegin.addPrevious(osBegin, 5);

        List<TimePoint> sorted = new LinkedList<>();
        sorted.add(osBegin);
        sorted.add(dbBegin);
        sorted.add(dbEnd);

        LinkedHashMap<TimePoint, Long> testtimeLine = ActivityGroup.timeline(sorted);

        for(TimePoint timepoint: testtimeLine.keySet()) {

            System.out.println(timepoint.getDescription() + ", with a timeline of " + testtimeLine.get(timepoint));
        }
    }

}