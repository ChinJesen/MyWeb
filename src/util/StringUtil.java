/**
 * 
 */
package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * @author Œ­³¿³¿
 *
 */
public class StringUtil {
	public static String getJsonStr(Map<String,String> objMap)
	{
		StringBuffer s = new StringBuffer();
		s.append("{\"items\":[");
		if(objMap != null)
		{
			for(String key : objMap.keySet())
			{
				String val = objMap.get(key);
				s.append("{\"").append(key).append("\":\"").append(val).append("\"},");
			}
		}
		if(s.toString().endsWith(","))
		{
			s = new StringBuffer(s.substring(0,s.length() - 1));
		}
		s.append("]}");
		return s.toString();
	}

	/**
	 * @return
	 */
	public static String getSysTime() {

		Date date = new Date();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simple.format(date);
	}
}
