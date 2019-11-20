package com.alibaba.idst.nls.dm.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author niannian.ynn
 */
public class Utility {

    public static String fileToString(String file) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line).append('\n');
        }
        bufferedReader.close();
        return sb.toString();
    }
}
