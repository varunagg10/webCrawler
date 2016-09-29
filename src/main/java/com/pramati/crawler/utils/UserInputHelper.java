package com.pramati.crawler.utils;

import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by varuna on 27/9/16.
 */
public class UserInputHelper {

    public static String inputFromConsole(){
        Scanner sc = new Scanner(new InputStreamReader(System.in));
        String input = sc.next();
        return input;
    }
}
