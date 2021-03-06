package jmri.implementation.configurexml;

import jmri.util.JUnitUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the MatrixSignalMastXml class
 *
 * @author   Paul Bender  Copyright (C) 2016
 */
public class MatrixSignalMastXmlTest {

    @Test
    public void testCtor(){
      Assert.assertNotNull("MatrixSignalMastXml constructor", new MatrixSignalMastXml());
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

