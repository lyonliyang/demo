package com.tencent.common;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 * User: rizenguo Date: 2014/10/29 Time: 15:23
 */
public class Signature {

	static Logger log = Logger.getLogger(Signature.class);

	/**
	 * 检验API返回的数据里面的签名是否合法，避免数据在传输的过程中被第三方篡改
	 * 
	 * @param responseString
	 *        API返回的XML数据字符串
	 * @return API签名是否合法
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static boolean checkIsSignValidFromResponseString(
			final String responseString, final String encoding)
			throws ParserConfigurationException,
			IOException, SAXException {

		final Map<String, String> map = XMLParser.getMapFromXML(responseString);
		log.debug(map.toString());

		final String signFromAPIResponse = map.get("sign").toString();
		if (signFromAPIResponse == "" || signFromAPIResponse == null) {
			log.debug("API返回的数据签名数据不存在，有可能被第三方篡改!!!");
			return false;
		}
		log.debug("服务器回包里面的签名是:" + signFromAPIResponse);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		map.put("sign", "");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		final String signForAPIResponse = Signature.getSign(map, encoding);

		if (!signForAPIResponse.equals(signFromAPIResponse)) {
			// 签名验不过，表示这个API返回的数据有可能已经被篡改了
			log.debug("API返回的数据签名验证不通过，有可能被第三方篡改!!!");
			return false;
		}
		log.debug("恭喜，API返回的数据签名验证通过!!!");
		return true;
	}

	public static String getSign(final Map<String, String> map,
			final String encoding) {
		final ArrayList<String> list = new ArrayList<String>();
		for (final Map.Entry<String, String> entry : map.entrySet()) {
			if (StringUtils.isNotEmpty(entry.getValue())) {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		final int size = list.size();
		final String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + WxConfig.getKey();
		result = MD5.MD5Encode(result, encoding).toUpperCase();
		return result;
	}

	public static Map<String, String> sign(final Map<String, String> params,
			final String encoding) {
		final Map<String, String> request = new HashMap<String, String>();
		final Iterator<String> iterator = params.keySet().iterator();
		while (iterator.hasNext()) {
			final String key = iterator.next();
			final String value = params.get(key);
			if (StringUtils.isNotEmpty(value)) {
				request.put(key, value);
			}
		}
		request.put("sign", getSign(params, encoding));
		return request;
	}

	/**
	 * 签名算法
	 * 
	 * @param o
	 *        要参与签名的数据对象
	 * @return 签名
	 * @throws IllegalAccessException
	 */
	public static String getSign(final Object o, final String encoding)
			throws IllegalAccessException {
		final ArrayList<String> list = new ArrayList<String>();
		final Class<?> cls = o.getClass();
		final Field[] fields = cls.getDeclaredFields();
		for (final Field f : fields) {
			f.setAccessible(true);
			if (f.get(o) != null && f.get(o) != "") {
				list.add(f.getName() + "=" + f.get(o) + "&");
			}
		}
		final int size = list.size();
		final String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		String result = sb.toString();
		result += "key=" + WxConfig.getKey();
		log.debug("Sign Before MD5:" + result);
		result = MD5.MD5Encode(result, encoding).toUpperCase();
		log.debug("Sign Result:" + result);
		return result;
	}

	/**
	 * 从API返回的XML数据里面重新计算一次签名
	 * 
	 * @param responseString
	 *        API返回的XML数据
	 * @return 新鲜出炉的签名
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static String getSignFromResponseString(final String responseString,
			final String encoding)
			throws IOException, SAXException, ParserConfigurationException {
		final Map<String, String> map = XMLParser.getMapFromXML(responseString);
		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名
		map.put("sign", "");
		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较
		return Signature.getSign(map, encoding);
	}

}
