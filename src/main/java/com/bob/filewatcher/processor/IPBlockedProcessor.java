package com.bob.filewatcher.processor;

import com.bob.filewatcher.FileReader;

import java.io.IOException;

import static com.bob.filewatcher.Util.isEmpty;

/**
 * @author Babak Eghbali (Bob)
 * @since 2019/05/13
 */
public class IPBlockedProcessor implements Processor {

    @Override
    public String process(FileReader fileReader) throws IOException {
        String line = fileReader.readLine();
        if (isEmpty(line)){
            return null;
        }
        if (line.contains("wanna block")){
            line = fileReader.readLine();
            if (isEmpty(line)){
                return null;
            }
            if (line.contains("now blocked")){
                return "found a block action: "+line;
            }
        }

        return null;
    }


}
