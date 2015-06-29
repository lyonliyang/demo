package com.tencent.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Map;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;

/**
 * User: rizenguo Date: 2014/10/23 Time: 14:59
 */
public class Util {

	// 打log用
	private static Logger logger = Logger.getLogger(Util.class);

	public static int getIntFromMap(final Map<String, Object> map,
			final String key) {
		if (key == "" || key == null) {
			return 0;
		}
		if (map.get(key) == null) {
			return 0;
		}
		return Integer.parseInt((String) map.get(key));
	}

	/**
	 * 读取本地的xml数据，一般用来自测用
	 * 
	 * @param localPath 本地xml文件路径
	 * @return 读到的xml字符串
	 */
	public static String getLocalXMLString(final String localPath)
			throws IOException {
		return Util.inputStreamToString(Util.class
				.getResourceAsStream(localPath));
	}

	public static Object getObjectFromXML(final String xml,
			final Class<?> tClass) {
		// 将从API返回的XML数据映射到Java对象
		final XStream xStreamForResponseData = new XStream();
		xStreamForResponseData.alias("xml", tClass);
		xStreamForResponseData.ignoreUnknownElements();// 暂时忽略掉一些新增的字段
		return xStreamForResponseData.fromXML(xml);
	}

	public static String getStringFromMap(final Map<String, Object> map,
			final String key,
			final String defaultValue) {
		if (key == "" || key == null) {
			return defaultValue;
		}
		final String result = (String) map.get(key);
		if (result == null) {
			return defaultValue;
		} else {
			return result;
		}
	}

	public static InputStream getStringStream(final String sInputString)
			throws UnsupportedEncodingException {
		ByteArrayInputStream tInputStringStream = null;
		if (sInputString != null && !sInputString.trim().equals("")) {
			// System.out.println("ISO-8859-1");
			tInputStringStream = new ByteArrayInputStream(
					sInputString.getBytes("UTF-8"));
		}
		return tInputStringStream;
	}

	public static String inputStreamToString(final InputStream is)
			throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i;
		while ((i = is.read()) != -1) {
			baos.write(i);
		}
		return baos.toString();
	}

	/**
	 * 打log接口
	 * 
	 * @param log 要打印的log字符串
	 * @return 返回log
	 */
	public static String log(final Object log) {
		if (log != null) {
			logger.info(log.toString());
		}
		return log != null ? log.toString() : "";
	}

	public static byte[] readInput(final InputStream in) throws IOException {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		int len = 0;
		final byte[] buffer = new byte[1024];
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		out.close();
		in.close();
		return out.toByteArray();
	}

	/**
	 * 通过反射的方式遍历对象的属性和属性值，方便调试
	 * 
	 * @param o 要遍历的对象
	 * @throws Exception
	 */
	public static void reflect(final Object o) throws Exception {
		final Class<?> cls = o.getClass();
		final Field[] fields = cls.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			final Field f = fields[i];
			f.setAccessible(true);
			log(f.getName() + " -> " + f.get(o));
		}
	}

}
