package com.bob.filewatcher;

import com.beust.jcommander.JCommander;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.tinylog.Logger.info;

/**
 * @author Babak Eghbali (Bob)
 * @since 2019/05/12
 */

public class Starter {

//    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Initializes and starts the application.
     * Initialization might be only building configuration manager or extra work.
     * Start tends to be running the main process when  there's no startup issue.
     * @param args The CLI arguments to use
     * @throws IOException If loading config file fails
     * @throws URISyntaxException If loading config file fails
     */
    public void start(String[] args) throws Exception {

        info("Starter started");

        JCommander.newBuilder().addObject(ConfigManager.instance()).build().parse(args);
        ConfigManager.instance().init();

        new MainProcess().start();
    }

}
