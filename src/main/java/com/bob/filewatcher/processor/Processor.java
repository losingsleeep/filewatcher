package com.bob.filewatcher.processor;

import com.bob.filewatcher.FileReader;

import java.io.IOException;

/**
 * @author Babak Eghbali (Bob)
 * @since 2019/05/13
 */
public interface Processor {

    String process(FileReader fileReader) throws IOException;

}
