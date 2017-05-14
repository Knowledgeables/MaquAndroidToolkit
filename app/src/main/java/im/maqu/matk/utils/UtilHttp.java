/*

 *
@author : Maqu
 */

package im.maqu.matk.utils;


import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import im.maqu.matk.constant.CstCharset;

public class UtilHttp {
	private static final String URL_AND_PARA_SEPARATOR = "?";
	private static final String PARAMETERS_SEPARATOR = "&";
	private static final String PATHS_SEPARATOR = "/";
	private static final String EQUAL_SIGN = "=";

	/**
	 * 生成key-value形式的请求数据
	 *
	 * @param paramsMap
	 * @return
	 */
	public static String joinParams(Map<String, String> paramsMap) {
		return joinParamsWithEncode(paramsMap, CstCharset.UTF_8);
	}

	/**
	 * 生成key-value形式的请求数据
	 *
	 * @param paramsMap
	 * @return
	 */
	public static String joinParamsWithEncode(Map<String, String> paramsMap, String encoder) {
		StringBuilder params = new StringBuilder();
		if (UtilMap.isNotEmpty(paramsMap)) {
			Iterator<Map.Entry<String, String>> ite = paramsMap.entrySet().iterator();
			try {
				while (ite.hasNext()) {
					Map.Entry<String, String> entry = ite.next();
					params.append(entry.getKey()).append(EQUAL_SIGN).append(URLEncoder.encode(entry.getValue(), encoder));
					if (ite.hasNext()) {
						params.append(PARAMETERS_SEPARATOR);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return params.toString();
	}



}