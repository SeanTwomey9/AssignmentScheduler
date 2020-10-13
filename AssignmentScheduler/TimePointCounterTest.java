package AssignmentScheduler;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TimePointCounterTest {

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Test
    public void decrement() {

        TimePointCounter c1 = new TimePointCounter(9);
        assertEquals(c1.decrement(), 8);
    }

    @Test
    public void decrementnegCounter () {

        expected.expectCause(isA(SchedulerException.class));
        TimePointCounter c2 = new TimePointCounter(-9);
        c2.decrement();

    }

    @Test
    public void negCounterConstruct() {

        expected.expectCause(isA(SchedulerException.class));
        TimePointCounter c3 = new TimePointCounter(-9);
    }

    @Test
    public void isIndependent() {

        TimePointCounter c4 = new TimePointCounter(1);
        assertFalse(c4.isIndependent());
        TimePointCounter c5 = new TimePointCounter(0);
        assertTrue(c5.isIndependent());
    }

    @Test
    public void testToString() {

        TimePointCounter c6 = new TimePointCounter(16);
        assertEquals("This TimePointCounter has a counter of " + c6.getCounter() + " and is not independent.",  c6.toString());

        TimePointCounter c7 = new TimePointCounter(0);
        assertEquals("This TimePointCounter has a counter of " + c7.getCounter() + " and is independent.",  c7.toString());
    }
}