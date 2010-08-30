// ComponentFactory.java

package jmri.jmrix.lenz.swing;

import jmri.jmrix.lenz.XNetSystemConnectionMemo;

/**
 * Provide access to Swing components for the XPressNet subsystem.
 *
 * @author		Bob Jacobsen  Copyright (C) 2010
 * @author		Paul Bender   Copyright (C) 2010
 * @version             $Revision: 1.1 $
 * @since 2.11.1
 */
public class ComponentFactory extends jmri.jmrix.swing.ComponentFactory {

    public ComponentFactory(XNetSystemConnectionMemo memo) {
        this.memo = memo;
        new java.lang.Exception().printStackTrace();
    }
    
    XNetSystemConnectionMemo memo;
    
    /**
     * Provide a menu with all items attached to this system connection
     */
    public javax.swing.JMenu getMenu() {
        if (memo.getDisabled()) return null;
        return new XNetMenu(memo);
    }
}


/* @(#)ComponentFactory.java */
