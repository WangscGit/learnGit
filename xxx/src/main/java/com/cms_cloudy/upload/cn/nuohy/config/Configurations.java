package com.cms_cloudy.upload.cn.nuohy.config;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * read the configurations from file `config.properties`.
 */
public class Configurations {
	static final String CONFIG_FILE = "stream-config.properties";
	static final String CG_CONFIG_FILE ="CGApp.properties";
	
	
	private static Properties properties = null;
	private static Properties cg_properties = null;
	
	
	private static final String REPOSITORY = System.getProperty(
			"java.io.tmpdir", File.separator + "tmp" + File.separator
					+ "upload-repository");

	static {
		new Configurations();
	}

	private Configurations() {
		init();
	}

	void init() {
		try {
			ClassLoader loader = Configurations.class.getClassLoader();
			InputStream in = loader.getResourceAsStream(CONFIG_FILE);
			InputStream cg_in = loader.getResourceAsStream(CG_CONFIG_FILE);
			properties = new Properties();
			cg_properties = new Properties();
			properties.load(in);
			cg_properties.load(cg_in);
			in.close();
			cg_in.close();
		} catch (IOException e) {
			System.err.println("reading `" + CONFIG_FILE + "` error!" + e);
		} 
	}

	public static String getConfig(String key) {
		return getConfig(key, null);
	}

	public static String getConfig(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	public static int getConfig(String key, int defaultValue) {
		String val = getConfig(key);
		int setting = 0;
		try {
			setting = Integer.parseInt(val);
		} catch (NumberFormatException e) {
			setting = defaultValue;
		}
		return setting;
	}

	
	//传输至自己的文件夹
	public static String getFileRepository() {
		
		String val = getConfig("STREAM_FILE_REPOSITORY");
		if (val == null || val.isEmpty()){
			System.out.println("STREAM_FILE_REPOSITORY为空！！！！");
			val = REPOSITORY;
		}
		return val;
	}

	public static String getCrossServer() {
		return getConfig("STREAM_CROSS_SERVER");
	}
	
	public static String getCrossOrigins() {
		return getConfig("STREAM_CROSS_ORIGIN");
	}
	
	public static boolean getBoolean(String key) {
		return Boolean.parseBoolean(getConfig(key));
	}
	
	public static boolean isDeleteFinished() {
		return getBoolean("STREAM_DELETE_FINISH");
	}
	
	public static boolean isCrossed() {
		return getBoolean("STREAM_IS_CROSS");
	}
	
	public static int getUploadSpeed(){
		return getConfig("STREAM_UPLOAD_SPEED", 100);
	}
	
	public static String getCGConfig(String key, String defaultValue) {
		return cg_properties.getProperty(key, defaultValue);
	}
}
