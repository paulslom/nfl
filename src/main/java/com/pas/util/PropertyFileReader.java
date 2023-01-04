package com.pas.util;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pas.constants.IAppConstants;

//import com.pas.csw.util.*;

public class PropertyFileReader {

    private ResourceBundle rb;
    protected Logger log = LogManager.getLogger(getClass());

    private PropertyFileReader(String propsfilename) {
        rb = ResourceBundle.getBundle(propsfilename);
    }

    private static PropertyFileReader dbreader;

    public static PropertyFileReader getDBConfigReader() {
        if (dbreader == null)
            dbreader = new PropertyFileReader(IAppConstants.DB_CONFIG_FILE);
        return dbreader;
    }   

    public String getProperty(String key) {
        final String methodName = "getProperty :: ";
        String retVal = null;
        try {
            retVal = rb.getString(key);
        } catch (Exception e) {
            log.error(methodName + " Error retieving property - " + key);
            log.error(methodName + " returning null value for property - " + key);
            retVal = null;
        }
        return retVal;
    }
	// test method
    private void dumpProps() {
        String methodName = "dumpProps :: ";
        Enumeration<String> keys = rb.getKeys();
        String currentKey = null;
        while (keys.hasMoreElements()) {
            currentKey = keys.nextElement();
            log.debug(methodName + "KEY : " + currentKey);
            log.debug(methodName + "Value : " + getProperty(currentKey));
        }
    }
	// test main program...
    public static void main(String[] args) {
        Logger log = LogManager.getLogger("MAIN :: ");
        PropertyFileReader dbCfg = PropertyFileReader.getDBConfigReader();
        log.debug("Reading DB config file.....");
        dbCfg.dumpProps();        
    }
}
