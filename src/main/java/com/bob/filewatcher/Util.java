package com.bob.filewatcher;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * @author Babak Eghbali (Bob)
 * @since 2019/05/13
 */
public class Util {

    public static boolean isEmpty(String s){
        return StringUtils.isBlank(s);
    }

    public static String getFolderPath(String fullFilePath){
        File file = new File(fullFilePath);
        return file.getParent();
    }

    public static String getFileName(String fullFilePath){
        File file = new File(fullFilePath);
        return file.getName();
    }

}
