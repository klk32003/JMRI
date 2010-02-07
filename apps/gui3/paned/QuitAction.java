// QuitAction.java

package apps.gui3.paned;

import javax.swing.*;
import java.awt.event.ActionEvent;

import jmri.util.swing.*;

/**
 * Action to quit the program
 *
 * Ignores WindowInterface.
 *
 * @author		Bob Jacobsen Copyright (C) 2010
 * @version		$Revision: 1.1 $
 */
 
public class QuitAction extends jmri.util.swing.JmriAbstractAction {

 	public QuitAction(String s, WindowInterface wi) {
    	super(s, wi);
    }
     
 	public QuitAction(String s, Icon i, WindowInterface wi) {
    	super(s, i, wi);
    }
       
    public void actionPerformed(ActionEvent e) {
        try {
            jmri.InstanceManager.shutDownManagerInstance().shutdown();
        } catch (Exception ex) {
            System.err.println("Continuing after error in handleQuit: "+ex);
        }
    }
    
    // never invoked, because we overrode actionPerformed above
    public void dispose() {
        throw new IllegalArgumentException("Should not be invoked");
    }
    
    // never invoked, because we overrode actionPerformed above
    public JmriPanel makePanel() {
        throw new IllegalArgumentException("Should not be invoked");
    }

}

/* @(#)QuitAction.java */
