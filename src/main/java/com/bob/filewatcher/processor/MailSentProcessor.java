package com.bob.filewatcher.processor;

import com.bob.filewatcher.FileReader;

import java.io.IOException;

import static com.bob.filewatcher.Util.isEmpty;

/**
 * @author Babak Eghbali (Bob)
 * @since 2019/05/15
 */
public class MailSentProcessor implements Processor {

    @Override
    public String process(FileReader fileReader) throws IOException {
        String line = fileReader.readLine();
        if (isEmpty(line)){
            return null;
        }
        if (line.contains("gonna send")){
            line = fileReader.readLine();
            if (isEmpty(line)){
                return null;
            }
            if (line.contains("now sent")){
                return "found mail sent action: "+line;
            }
        }
        return null;
    }


}