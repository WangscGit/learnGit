package com.cms_cloudy.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Properties文件读取
 * @author WangSc
 */
public class PropertiesUtils {
	  private static final Logger log = Logger.getLogger(PropertiesUtils.class);
	  private static String config = "/fieldGroupSetting.properties";
	  private static Properties props;
	  public static Properties getProperties()
			    throws IOException
			  {
			    try
			    {
			      if (props == null) {
			        props = new Properties();
			      }
			      props.load(new InputStreamReader(PropertiesUtils.class.getResourceAsStream(config), "UTF-8"));
//		            value = pros.get(key).toString();
//			      props.load(PropertiesUtils.class.getResourceAsStream(config),"UTF-8"));
			      return props;
			    } catch (IOException e) {
			      log.error("找不到对应的配置文件！");
			      throw e;
			    }
			  }
}
