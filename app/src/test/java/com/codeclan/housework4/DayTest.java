package com.codeclan.housework4;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DayTest {

    Day day;
    Task task;
    ArrayList<DayName> days;

    @Before
    public void setup() {
        day = new Day("Monday");
        task = new Task("Wash dishes", "Wash all the dishes");
    }

}