package jmri.jmrit.display.configurexml;

import jmri.util.JUnitUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * MemorySpinnerIconXmlTest.java
 *
 * Test for the MemorySpinnerIconXml class
 *
 * @author   Paul Bender  Copyright (C) 2016
 */
public class MemorySpinnerIconXmlTest {

    @Test
    public void testCtor(){
      Assert.assertNotNull("MemorySpinnerIconXml constructor",new MemorySpinnerIconXml());
    }

    @Before
    public void setUp() {
        JUnitUtil.setUp();
    }

    @After
    public void tearDown() {
        JUnitUtil.tearDown();
    }

}

