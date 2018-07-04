package com.cms_cloudy.upload.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;

public class UnZipOrRarUtils {
	/*** 这里用到了synchronized ，也就是防止出现并发问题 ***/
	public static synchronized boolean untar(String tarFileName, String extPlace) throws Exception {
		boolean flag = unRarFile(tarFileName, extPlace);
		return flag;
	}

	public static synchronized boolean unzip(String zipFileName, String extPlace) throws Exception {
		boolean flag = unZipFiles(zipFileName, extPlace);
		return flag;
	}

	/**
	 * 解压zip格式的压缩文件到指定位置
	 * 
	 * @param zipFileName
	 *            压缩文件
	 * @param extPlace
	 *            解压目录
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static boolean unZipFiles(String zipFileName, String extPlace) throws Exception {
		System.setProperty("sun.zip.encoding", System.getProperty("sun.jnu.encoding"));
		try {
			(new File(extPlace)).mkdirs();
			File f = new File(zipFileName);
			if (f.exists()) {
				System.out.println("xxx");
			}
			ZipFile zipFile = new ZipFile(zipFileName, "GBK"); // 处理中文文件名乱码的问题
			if ((!f.exists()) && (f.length() <= 0)) {
				throw new Exception("要解压的文件不存在!");
			}
			String strPath, gbkPath, strtemp;
			File tempFile = new File(extPlace);
			strPath = tempFile.getAbsolutePath();
			Enumeration<?> e = zipFile.getEntries();
			while (e.hasMoreElements()) {
				ZipEntry zipEnt = (ZipEntry) e.nextElement();
				gbkPath = zipEnt.getName();
				if (zipEnt.isDirectory()) {
					strtemp = strPath + File.separator + gbkPath;
					File dir = new File(strtemp);
					dir.mkdirs();
					continue;
				} else { // 读写文件
					InputStream is = zipFile.getInputStream(zipEnt);
					BufferedInputStream bis = new BufferedInputStream(is);
					gbkPath = zipEnt.getName();
					strtemp = strPath + File.separator + gbkPath;// 建目录
					String strsubdir = gbkPath;
					for (int i = 0; i < strsubdir.length(); i++) {
						if (strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {
							String temp = strPath + File.separator + strsubdir.substring(0, i);
							File subdir = new File(temp);
							if (!subdir.exists())
								subdir.mkdir();
						}
					}
					FileOutputStream fos = new FileOutputStream(strtemp);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					int c;
					while ((c = bis.read()) != -1) {
						bos.write((byte) c);
					}
					bos.close();
					fos.close();
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 根据原始rar路径，解压到指定文件夹下.
	 * 
	 * @param srcRarPath
	 *            原始rar路径
	 * @param dstDirectoryPath
	 *            解压到的文件夹
	 */
	public static boolean unRarFile(String srcRarPath, String dstDirectoryPath) {
		if (!srcRarPath.toLowerCase().endsWith(".rar")) {
			System.out.println("非rar文件！");
			return false;
		}
		File dstDiretory = new File(dstDirectoryPath);
		if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
			dstDiretory.mkdirs();
		}
		Archive a = null;
		try {
			a = new Archive(new File(srcRarPath));
			if (a != null) {
				// a.getMainHeader().print(); // 打印文件信息.
				FileHeader fh = a.nextFileHeader();
				while (fh != null) {

					// 防止文件名中文乱码问题的处理

					String fileName = fh.getFileNameW().isEmpty() ? fh.getFileNameString() : fh.getFileNameW();

					if (fh.isDirectory()) { // 文件夹
						File fol = new File(dstDirectoryPath + File.separator + fileName);
						fol.mkdirs();
					} else { // 文件
						File out = new File(dstDirectoryPath + File.separator + fileName.trim());
						try {
							if (!out.exists()) {
								if (!out.getParentFile().exists()) {// 相对路径可能多级，可能需要创建父目录.
									out.getParentFile().mkdirs();
								}
								out.createNewFile();
							}
							FileOutputStream os = new FileOutputStream(out);
							a.extractFile(fh, os);
							os.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					fh = a.nextFileHeader();
				}
				a.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@SuppressWarnings("static-access")
	public static void main(String[] arg) {
		System.out.println("xxx");
		try {
			String utl = "D:\\testJY\\新建文件夹.rar";
			String ut3 = "D:\\testJY\\新建文件夹2.zip";
			String utl2 = "D:\\testJY";
			try {
				new UnZipOrRarUtils().untar(utl, utl2);
				new UnZipOrRarUtils().unzip(ut3, utl2);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}