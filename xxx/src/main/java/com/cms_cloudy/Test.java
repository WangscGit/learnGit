package com.cms_cloudy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {

	@SuppressWarnings("resource")
	public static int getProjectFileNumber(File file, String endsWith) throws IOException {  
        int number = 0;  
        if (file.exists()) {  
            if (file.isDirectory()) {  
                for (File subFile : file.listFiles()) {  
                    number += getProjectFileNumber(subFile, endsWith);  
                }  
            } else if (file.isFile() && file.getName().endsWith(endsWith)) {  
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));  
                while (br.readLine() != null) {  
                    number += 1;  
                }  
            } else {
                System.out.println("===" + file.getAbsolutePath());  
            }  
        }  
        return number;  
    }  
  
    public static void main(String[] args) throws IOException {  
        //用法示例  E:\mes\cms_cloudy
        int num = getProjectFileNumber(new File("E:\\mes\\cms_cloudy\\src"), ".jsp");  
        System.out.println(num);  
    }  
}
