package jmri.jmrit.display.layoutEditor;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import jmri.util.JUnitUtil;

/**
 * Test simple functioning of BlockValueFile
 *
 * @author	Paul Bender Copyright (C) 2016
 */
public class BlockValueFileTest extends TestCase {

    public void testCtor() {
        BlockValueFile  f = new BlockValueFile();
        Assert.assertNotNull("exists", f );
    }

    // from here down is testing infrastructure
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        apps.tests.Log4JFixture.setUp();
        // dispose of the single PanelMenu instance
        jmri.jmrit.display.PanelMenu.dispose();
        // reset the instance manager.
        JUnitUtil.resetInstanceManager();
    }
 
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        // dispose of the single PanelMenu instance
        jmri.jmrit.display.PanelMenu.dispose();
        JUnitUtil.resetInstanceManager();
        apps.tests.Log4JFixture.tearDown();
    }



    public BlockValueFileTest(String s) {
        super(s);
    }

    // Main entry point
    static public void main(String[] args) {
        String[] testCaseName = {"-noloading", BlockValueFileTest.class.getName()};
        junit.textui.TestRunner.main(testCaseName);
    }

    // test suite from all defined tests
    public static Test suite() {
        TestSuite suite = new TestSuite(BlockValueFileTest.class);
        return suite;
    }

}