package com.bob.filewatcher;



import java.io.*;
import java.net.URISyntaxException;

import static org.tinylog.Logger.error;

/**
 * @author Babak Eghbali (Bob)
 * @since 2019/05/07
 */
public class App{

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Welcome to Postfix log analyzer!");
        try {
            new Starter().start(args);
        }catch (Exception e){
            System.out.println("ERROR occurred. Application will exit: ");
            e.printStackTrace();
            error("ERROR occurred. Application will exit: ",e);
        }
    }

}
