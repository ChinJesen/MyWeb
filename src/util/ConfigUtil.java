/**
 * 
 */
package util;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Œ­³¿³¿
 *
 */
public class ConfigUtil {
	private static ConfigUtil configUtil;
	private static Properties pop;
	private static final String DEFALT_FILE_PATH = "/config.properties";
	private static String filePath;
	private ConfigUtil(){
		init();
	}
	/**
	 * 
	 */
	private void init() {
		pop = new Properties();
		try {
			if(filePath != null)
				pop.load(ConfigUtil.class.getResourceAsStream(filePath));
				pop.load(ConfigUtil.class.getResourceAsStream(DEFALT_FILE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ConfigUtil newInstance(String filePath){
		ConfigUtil.filePath=filePath;
		if(configUtil == null){
			configUtil = new ConfigUtil();
		}
		return configUtil;
	}
	
	public String getVal(String key){
		return pop.getProperty(key);
	}
}
