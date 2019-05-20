package com.bob.filewatcher;

import org.junit.Test;

import java.io.*;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * @author Babak Eghbali (Bob)
 * @since 2019/05/13
 */
public class AppTest {

    @Test
    public void test1() throws IOException {
        //org.junit.Assert.assertEquals(1,13);
//        String s = "13 DUP 4 POP 5 DUP + DUP + -";
//        System.out.println(solution(s));
        //
        fileAccess3();
    }

    private void fileAccess3() throws IOException {
        System.out.println("-------------------------------------");
        String filename = "c:\\logs\\myFile.txt";
        RandomAccessFile f = new RandomAccessFile(filename,"r");
        int i;
        for (i = 0; i < 3; i++){
            System.out.println(f.readLine());
        }
        System.out.println("--- continue:");
        f.seek(0);
        // skip lines to last position:
        for (int j = 0; j < i ; j++) {
            f.readLine();
        }
        System.out.println(f.readLine());
        f.close();
    }

    private void fileAccess2() throws IOException {
        System.out.println("-------------------------------------");
        String filename = "c:\\logs\\myFile.txt";
        RandomAccessFile f = new RandomAccessFile(filename,"r");
        String str;
        int i = 0;
        while ( (str = f.readLine()) != null){
            System.out.println(str);
            i++;
        }
        System.out.println("again:");
        f.seek(0);
        for (int j = 0; j < i ; j++) {
            System.out.println(f.readLine());
        }
        f.close();
    }

    private void fileAccess1() throws IOException {
        System.out.println("-------------------------------------");
        Map<Integer,Long> map = new HashMap<>();
        String filename = "c:\\logs\\myFile.txt";
        RandomAccessFile f = new RandomAccessFile(filename,"r");
        for (int i = 0; i < 5; i++){
            map.put(i,f.getFilePointer());
            System.out.println(f.readLine());
        }
        System.out.println("again:");
        for (Map.Entry<Integer,Long> entry : map.entrySet()) {
            f.seek(entry.getValue());
            System.out.println(f.readLine());
        }
        f.close();
    }

    private int solution(String S) {
        String[] input = S.split(" ");
        Stack<Integer> stack = new Stack<>();

        for (int x = 0; x < input.length; x++) {
            String e = input[x];
            if (isNum(e)) {
                stack.push(Integer.parseInt(e));
            } else if (e.equals("POP")) {
                if (stack.size() < 1) return -1;
                stack.pop();
            } else if (e.equals("DUP")) {
                if (stack.size() < 1) return -1;
                stack.push(stack.peek());
            } else if (e.equals("+")) {
                if (stack.size() < 2) return -1;
                Integer newNum = stack.pop() + stack.pop();
                if (newNum > Math.pow(2, 20) - 1) return -1;
                stack.push(newNum);
            } else if (e.equals("-")) {
                if (stack.size() < 2) return -1;
                Integer newNum = stack.pop() - stack.pop();
                if (newNum < 0) return -1;
                stack.push(newNum);
            }

        }
        if (stack.size() < 1) return -1;
        return stack.peek();
    }

    private boolean isNum(String strNum) {
        boolean ret = true;
        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException e) {
            ret = false;
        }
        return ret;
    }

}