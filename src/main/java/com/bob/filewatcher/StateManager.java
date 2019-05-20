package com.bob.filewatcher;

import org.tinylog.Logger;

import java.io.*;
import java.util.Properties;

/**
 * @author Babak Eghbali (Bob)
 * @since 2019/05/13
 */
public class StateManager {

    private static final String PROPERTY_FILE_NAME = "state.conf";
    private static final String KEY_LAST_ROW = "last_row";
    private File propertyFile = null;

    Properties prop = new Properties();

    private static StateManager INSTANCE;
    private StateManager() throws IOException {
        loadProperties();
    }
    public static StateManager getInstance() throws IOException {
        if(INSTANCE == null) {
            INSTANCE = new StateManager();
        }
        return INSTANCE;
    }

    private void loadProperties() throws IOException {
        initPropertyFile();
        try {
            prop.load(new FileInputStream(propertyFile));
        }catch (Exception e){
            Logger.info("Property file not found. Maybe it's the first time");
        }
    }

    private void initPropertyFile() throws IOException {
        String path = this.getClass().getProtectionDomain()
                .getCodeSource().getLocation().getPath();
        path = Util.getFolderPath(path);
        String fullFileName = path+File.separator+PROPERTY_FILE_NAME;
        Logger.info("Property file: "+fullFileName);
        propertyFile = new File(fullFileName);
        if (!propertyFile.exists()){
            propertyFile.createNewFile();
        }
    }

    public Integer getLastLineNo(){
        // load the value from state file and return
        return Integer.parseInt(prop.getProperty(KEY_LAST_ROW,"0"));
    }

    public void setLastLineNo(Integer lineNo) throws IOException {
        // saves the value immediately to state file to load on next startup
        prop.setProperty(KEY_LAST_ROW, lineNo.toString());
        prop.store(new FileOutputStream(propertyFile), "some comments");
    }

}
