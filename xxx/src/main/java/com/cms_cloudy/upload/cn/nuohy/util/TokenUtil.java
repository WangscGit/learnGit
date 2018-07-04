package com.cms_cloudy.upload.cn.nuohy.util;

import java.io.IOException;

import org.junit.Test;

import com.cms_cloudy.upload.cn.nuohy.config.Configurations;

/**
 * Key Util: 1> according file name|size ..., generate a key;
 * 			 2> the key should be unique.
 */
public class TokenUtil {

	/**
	 * 生成Token， A(hashcode>0)|B + |name的Hash值| +_+size的值
	 * @param name
	 * @param size
	 * @return
	 * @throws Exception
	 */
	@Test
	public void test(){
		/*try {
			System.out.println(generateToken("schoolwebVideo100.zip", "94742617"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		int code = "schoolwebVideo100.zip".hashCode();
		System.out.println(code);
		System.out.println((code > 0 ? "A" : "B") + Math.abs(code) + "_" + "94742617".trim());
		System.out.println(Configurations.getFileRepository());
	}
	
	//生成hashcode码
	public static String generateToken(String name, String size)
			throws IOException {
		if (name == null || size == null)
			return "";
		int code = name.hashCode();
		try {
			String token = (code > 0 ? "A" : "B") + Math.abs(code) + "_" + size.trim();
			/** TODO: store your token, here just create a file */
			IoUtil.storeToken(token);
			
			return token;
		} catch (Exception e) {
			throw new IOException(e);
		}
	}
}
