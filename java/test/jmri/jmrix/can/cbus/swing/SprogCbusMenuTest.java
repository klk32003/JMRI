package jmri.jmrix.can.cbus.swing;

import java.awt.GraphicsEnvironment;
import jmri.jmrix.can.CanSystemConnectionMemo;
import jmri.util.JUnitUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

/**
 * Test simple functioning of CbusMenu
 *
 * @author Paul Bender Copyright (C) 2016
 */
public class SprogCbusMenuTest {


    // private TrafficController tc = null;
    private CanSystemConnectionMemo m = null;
 
    @Test
    public void testCtor() {
        Assume.assumeFalse(GraphicsEnvironment.isHeadless()); 
        SprogCbusMenu action = new SprogCbusMenu(m);
        Assert.assertNotNull("exists", action);
    }

    @Before
    public void setUp() {
        JUnitUtil.setUp();
        // tc = new TestTrafficController();
        m = new CanSystemConnectionMemo();
        m.setSystemPrefix("ABC");

    }

    @After
    public void tearDown() { 
        JUnitUtil.tearDown();
        // tc = null;
    }
}
