package com.bob.filewatcher;

import com.beust.jcommander.Parameter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static com.bob.filewatcher.Util.isEmpty;
import static org.tinylog.Logger.info;

/**
 * @author Babak Eghbali (Bob)
 * @since 2019/05/12
 */
public class ConfigManager {

    private final Properties properties = new Properties();

    private static final ConfigManager theInstance = new ConfigManager();
    private ConfigManager() {}
    public static ConfigManager instance(){
        return theInstance;
    }

    @Parameter(names = "-config", description = "Config file full path and name")
    private String configFile;
    @Parameter(names = "-logfile", required = true, description = "Log file full path and name to watch and process")
    private String logFile;

    public void init() throws IOException {

        try (final InputStream stream = getConfigFileAsStream()) {
            properties.load(stream);
        }

    }

    private InputStream getConfigFileAsStream() throws FileNotFoundException {
        if (isEmpty(configFile)){
            info("Config file is not specified. Going for default config file.");
            return this.getClass().getResourceAsStream("/application.properties");
        }else {
            info("Loading config file "+configFile);
            return new FileInputStream(configFile);
        }
    }

    public String get(String key){
        return properties.getProperty(key);
    }

    public String getConfigFile() {
        return configFile;
    }

    public String getLogFile() {
        return logFile;
    }

}

