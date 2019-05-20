package com.bob.filewatcher;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Babak Eghbali (Bob)
 * @since 2019/05/13
 */
public class FileReader {

    private int lineNo;
    private ConfigManager config = ConfigManager.instance();
    private RandomAccessFile reader;
    Map<Integer,Long> map = new HashMap<>();

    public FileReader(int lineNo) throws IOException {
        this.lineNo = lineNo;
        openFile();
        seekToLine();
    }

    public void reloadAndRewind() throws IOException {
        lineNo = 0;
        openFile();
    }

    private void openFile() throws IOException {
        if (reader != null){
            reader.close();
        }
        reader = new RandomAccessFile(config.getLogFile(),"r");
    }

    public int getLineNo() {
        return lineNo;
    }

    public void setLineNo(int lineNo) throws Exception {
        if (this.lineNo != lineNo){
            this.lineNo = lineNo;
            if (!map.containsKey(lineNo)){
                throw new Exception("Invalid line number to set: "+lineNo);
            }
            reader.seek(map.get(lineNo));
        }
    }

    public void gotoNextLine() throws Exception {
        setLineNo(lineNo+1);
    }

    public boolean hasMore() throws IOException {
        boolean hasMore = false;
        long pointer = reader.getFilePointer();
        String line = reader.readLine();
        if (line != null){
            hasMore = true;
        }
        reader.seek(pointer); // back to before readLine()
        return hasMore;
    }

    public String readLine() throws IOException {
        String line = reader.readLine();
        if (line != null){
            lineNo++;
        }
        saveCursor(lineNo);
        return line;
    }

    private void seekToLine() throws IOException {
        for (int i = 0; i < lineNo; i++) {
            // no need to check if i < lineNo
            if (reader.readLine() == null) {
                // reached the EOF before target line is met. Means that file is rotated. So let's reset to 0
                reloadAndRewind();
                return;
            }
        }
        saveCursor(lineNo);
    }

    private void saveCursor(int lineNo) throws IOException {
        map.put(lineNo, reader.getFilePointer());
    }

}
