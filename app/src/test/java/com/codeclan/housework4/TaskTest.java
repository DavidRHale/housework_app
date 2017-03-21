package com.codeclan.housework4;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by David Hale on 21/03/2017.
 */

public class TaskTest {

    private Task task;

    @Before
    public void setup() {
        task = new Task("Wash dishes", "Dish washer");
    }

    @Test
    public void testChangeCompletedStatus() {
        task.changeCompletedStatus();
        assertEquals(true, task.isCompleted());
    }

    @Test
    public void testMakeIsCompletedFalse() {
        task.changeCompletedStatus();
        task.changeCompletedStatus();
        assertEquals(false, task.isCompleted());
    }
}
