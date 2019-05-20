package com.bob.filewatcher;


import com.bob.filewatcher.processor.IPBlockedProcessor;
import com.bob.filewatcher.processor.MailSentProcessor;
import com.bob.filewatcher.processor.Processor;
import org.tinylog.Logger;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static com.bob.filewatcher.Util.isEmpty;
import static org.tinylog.Logger.debug;
import static org.tinylog.Logger.info;

/**
 * @author Babak Eghbali (Bob)
 * @since 2019/05/12
 */
public class MainProcess {

//    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ConfigManager config;
    private StateManager state;
    private List<Processor> processors = null;

    public MainProcess() throws IOException {
        init();
    }

    private void init() throws IOException {
        config = ConfigManager.instance();
        state = StateManager.getInstance();
        //
        processors = new ArrayList<>();
        processors.add(new IPBlockedProcessor());
        processors.add(new MailSentProcessor());
    }

    /**
     * The main loop for watching the log file and calling the processor if needed
     */
    public void start() throws Exception {
        info("Main process started");
        info("Watching "+config.getLogFile());

        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(Util.getFolderPath(config.getLogFile()));
        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

        FileReader reader = new FileReader(state.getLastLineNo());

        while (true) {
            // watch folder
            WatchKey watchKey = watchService.take();
            for (WatchEvent<?> event : watchKey.pollEvents()) {
                //we only register "ENTRY_MODIFY" so the context is always a Path.
                final Path changed = (Path) event.context();
                System.out.println(changed);
                if (changed.endsWith(Util.getFileName(config.getLogFile()))) {
                    debug(config.getLogFile()+ " has changed");
                    keepProcessing(reader);
                    // save new line no in state mgr
                    state.setLastLineNo(reader.getLineNo());
                }
            }
            // reset the key
            boolean valid = watchKey.reset();
            if (!valid) {
                System.out.println("Key has been unregisterede");
                throw new Exception("Panic!");
            }
            // if our file is changed
            // call entry point for read/process/write process
        }

    }

    private void keepProcessing(FileReader reader) throws Exception {

        while (reader.hasMore()){
            process(reader);
            reader.gotoNextLine();
        }

    }

    private void process(FileReader reader) throws Exception {
        StringBuilder extractedData = new StringBuilder();
        int lineNo = reader.getLineNo();
        for (Processor processor : processors) {
            String output = processor.process(reader); // lineNo inside reader might change in process()
            reader.setLineNo(lineNo); // let's rewind to the line we where before process()
            if (!isEmpty(output)){
                extractedData.append(output);
                extractedData.append(System.lineSeparator());
                writeout(extractedData.toString());
            }
        }
    }

    private void writeout(String extractedData) {
        if (!isEmpty(extractedData))
            Logger.tag("EXTRACTED_DATA").info(extractedData);
    }


}
