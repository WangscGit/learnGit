package com.cms_cloudy.upload.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class FileUtil {
	 private static ExecutorService service;
	 final private static BlockingQueue<Long> fileSizes = new ArrayBlockingQueue<Long>(
	            500);
	  final static AtomicLong pendingFileVisits = new AtomicLong();
     //剪切文件
	public static void cutFile(File file1, File file2){  
        FileOutputStream fileOutputStream = null;  
        InputStream inputStream = null;  
        byte[] bytes = new byte[1024];  
        int temp = 0;  
        try {  
            inputStream = new FileInputStream(file1);  
            fileOutputStream = new FileOutputStream(file2);  
            while((temp = inputStream.read(bytes)) != -1){  
                fileOutputStream.write(bytes, 0, temp);  
                fileOutputStream.flush();  
            }  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        }catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            if (inputStream != null) {  
                try {  
                    inputStream.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (fileOutputStream != null) {  
                try {  
                    fileOutputStream.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  

	// copy文件
	public static void IOCopy(File path, File path1) {
		FileInputStream fi = null;

		FileOutputStream fo = null;

		FileChannel in = null;

		FileChannel out = null;

		try {

			fi = new FileInputStream(path);

			fo = new FileOutputStream(path1);

			in = fi.getChannel();// 得到对应的文件通道

			out = fo.getChannel();// 得到对应的文件通道

			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				fi.close();

				in.close();

				fo.close();

				out.close();

			} catch (IOException e) {

				e.printStackTrace();

			}
		}
	}
	 public static void copy(String path, String copyPath) throws IOException{
		  File filePath = new File(path);
		  DataInputStream read ;
		  DataOutputStream write;
		  if(filePath.isDirectory()){
		   File[] list = filePath.listFiles();
		   for(int i=0; i<list.length; i++){
		    String newPath = path + File.separator + list[i].getName();
		    String newCopyPath = copyPath + File.separator + list[i].getName();
		    File newFile = new File(copyPath);
		    if(!newFile.exists()){
		     newFile.mkdir();
		    }
		    copy(newPath, newCopyPath);
		   }
		  }else if(filePath.isFile()){
		   read = new DataInputStream(
		     new BufferedInputStream(new FileInputStream(path)));
		   write = new DataOutputStream(
		     new BufferedOutputStream(new FileOutputStream(copyPath)));
		   byte [] buf = new byte[1024*512];
		   while(read.read(buf) != -1){
		    write.write(buf);
		   }
		   read.close();
		   write.close();
		  }else{
		   System.out.println("请输入正确的文件名或路径名");
		  }
		 }
	 private static void startExploreDir(final File file) {
	        pendingFileVisits.incrementAndGet();
	        service.execute(new Runnable() {
	            public void run() {
	                exploreDir(file);
	            }
	        });
	    }
	    private static void exploreDir(final File file) {
	        long fileSize = 0;
	        if (file.isFile())
	            fileSize = file.length();
	        else {
	            final File[] children = file.listFiles();
	            if (children != null)
	                for (final File child : children) {
	                    if (child.isFile())
	                        fileSize += child.length();
	                    else {
	                        startExploreDir(child);
	                    }
	                }
	        }
	        try {
	            fileSizes.put(fileSize);
	        } catch (Exception ex) {
	            throw new RuntimeException(ex);
	        }
	        pendingFileVisits.decrementAndGet();
	    }
	    public static long getTotalSizeOfFile(final String fileName)
	            throws InterruptedException {
	        service = Executors.newFixedThreadPool(100);
	        try {
	            startExploreDir(new File(fileName));
	            long totalSize = 0;
	            while (pendingFileVisits.get() > 0 || fileSizes.size() > 0) {
	                final Long size = fileSizes.poll(10, TimeUnit.SECONDS);
	                totalSize += size;
	            }
	            return totalSize;
	        } finally {
	            service.shutdown();
	        }
	    }
	    //去除路径最后文件名，获取文件夹路径
	   public static String getDocPart(File file){
	        StringBuilder sb = new StringBuilder();
	        File temp = file;
	        while (temp.getParentFile() != null && temp.getParentFile().getName().length() != 0) {
	            sb.insert(0, "/" + temp.getParentFile().getName());
	            temp = temp.getParentFile();
	        }
	        sb.append("/");
	        return sb.toString();
	   }
}
