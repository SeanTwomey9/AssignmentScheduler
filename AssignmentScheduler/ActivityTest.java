package AssignmentScheduler;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.opentest4j.AssertionFailedError;

import static org.hamcrest.CoreMatchers.*;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ActivityTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testextremePoint() throws Exception {

        ActivityGroup a1 = new ActivityGroup();
        Activity activity1 = Activity.create(7, a1, "Commenting classes");
        activity1.extremePoint(TimePoint.Side.BEGIN);
        assertEquals(activity1.getBeginPoint(), activity1.extremePoint(TimePoint.Side.BEGIN));

    }

    @Test
    public void testdependencies() throws Exception {

        ActivityGroup a2 = new ActivityGroup();
        Activity activity2 = Activity.create(12, a2, "Putting out fires");
        TimePoint beginPoint = activity2.extremePoint(TimePoint.Side.BEGIN); //Beginning TimePoint has dependency on finishing TimePoint, and dependencies on finishing TimePoint are returned below along with appropriate duration
        assertEquals("[This dependency has a previous AssignmentScheduler.TimePoint of " + beginPoint + " and a duration of " + activity2.getDuration() + "]", activity2.dependencies(TimePoint.Side.END).toString());
    }

    @Test
    public void testcreatenegDuration() throws Exception {

        expectedException.expectCause(isA(SchedulerException.class));
        ActivityGroup a3 = new ActivityGroup();
        Activity.create(-2, a3, "Moving servers from on prem to cloud"); //prints out IllegalArgumentException with appropriate negative duration information


    }

    @Test
    public void testcreatenullActivities() throws Exception {

        expectedException.expectCause(isA(SchedulerException.class));
        ActivityGroup a4 = new ActivityGroup();
        Activity.create(40, null, "Going to the vending machine"); //prints out IllegalArgumentException with appropriate unexpected null information

    }

    @Test
    public void testcreatenullDescription() {

        ActivityGroup a5 = new ActivityGroup();
        Activity activity5 = Activity.create(11, a5, null);
        assertEquals(activity5.getDescription(), "");
    }

    @Test
    public void testcreate()  {

        ActivityGroup a6 = new ActivityGroup();
        Activity activity6 = Activity.create(9, a6, "Creating a functional activity");
        String correctActivity = "This activity has a duration of " + activity6.getDuration() + ", ActivityGroup of " + activity6.getActivities() + " and a description of: \"" + activity6.getDescription() + "\".";
        assertEquals(correctActivity, activity6.toString());

    }

    @Test
    public void testfreeze()  {

        ActivityGroup a7 = new ActivityGroup();
        Activity activity7 = Activity.create(12, a7, "J-Unit Testing");
        activity7.freeze();
        assertTrue(activity7.activityFrozen()); //checks to see if activity was properly frozen
        assertTrue(activity7.getBeginPoint().isFrozen()); //checks if Begin TimePoint was frozen properly
        assertTrue(activity7.getEndPoint().isFrozen()); //checks if End TimePoint was frozen properly
    }

    @Test
    public void testadddependencynullOther() throws Exception {

        expectedException.expectCause(isA(SchedulerException.class));
        ActivityGroup a8 = new ActivityGroup();
        Activity activity8 = Activity.create(19, a8, "Submitting work to employer");
        activity8.addDependency(TimePoint.Side.BEGIN, null); ////prints out IllegalArgumentException with appropriate unexpected null information
    }

    @Test
    public void testadddependencynullSide() throws Exception {

        expectedException.expectCause(isA(SchedulerException.class));
        ActivityGroup a8 = new ActivityGroup();
        Activity activity8 = Activity.create(19, a8, "Submitting work to employer");
        Activity activityOther = Activity.create(81, a8, "Other work");
        activity8.addDependency(null, new TimePoint(activityOther, TimePoint.Side.BEGIN)); ////prints out IllegalArgumentException with appropriate unexpected null information
    }

    @Test
    public void testaddDependency()  {

        ActivityGroup a9 = new ActivityGroup();
        Activity activity9 = Activity.create(19, a9, "Celebrating functional code");
        TimePoint other = new TimePoint(Activity.create(2, a9, "Yay!"), TimePoint.Side.BEGIN);
        assertTrue(activity9.addDependency(TimePoint.Side.BEGIN, other)); //checks to ensure dependency was properly added from other TimePoint onto the given side
        assertEquals("[This dependency has a previous AssignmentScheduler.TimePoint of " + other + " and a duration of 0]", activity9.getBeginPoint().getDependencies().toString()); //checks to ensure a dependency with duration of 0 from other TimePoint was added
    }
}