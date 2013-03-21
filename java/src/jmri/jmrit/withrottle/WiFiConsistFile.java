package jmri.jmrit.withrottle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jmri.jmrit.consisttool.ConsistFile;

/**
 *	@author Brett Hoffman   Copyright (C) 2011
 *	@version $Revision:  $
 */

public class WiFiConsistFile extends ConsistFile{
    

    public WiFiConsistFile(jmri.ConsistManager cm){
          super();
          consistMan=cm;
          loadStoredConsistFile("wifiConsist.xml");
       }

    /**
     * Check to see if wifiConsist.xml file exists. If so load it.
     * If not, check for consist.xml file and load it. Once a wifiConsist.xml file
     * exists, the default file will not be loaded by this anymore.
     */
    private void loadStoredConsistFile(String fileName){
        if (checkFile(getFileLocation() + fileName)) {
            log.debug("Has "+fileName+" file.");
            try {
                ReadFile(getFileLocation()+fileName);
            } catch (Exception e) {
                log.warn("error reading consist file: " + e);
            }
        }else{
            log.debug("No "+fileName+" file, will check for default file.");
            if (checkFile(defaultConsistFilename())) {
                log.debug("Has default consist.xml file, will read it.");
                try {
                    ReadFile();
                } catch (Exception e) {
                    log.warn("error reading consist file: " + e);
                }
            }else{
                log.debug("No consist files found, will create if needed.");
            }
        }
    }

    static Logger log = LoggerFactory.getLogger(WiFiConsistFile.class.getName());

}
