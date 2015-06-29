package com.tencent.common;

import java.security.MessageDigest;

/**
 * User: rizenguo Date: 2014/10/23 Time: 15:43
 */
public class MD5 {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7",
			"8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(final byte[] b) {
		final StringBuilder resultSb = new StringBuilder();
		for (final byte aB : b) {
			resultSb.append(byteToHexString(aB));
		}
		return resultSb.toString();
	}

	/**
	 * 转换byte到16进制
	 * 
	 * @param b 要转换的byte
	 * @return 16进制格式
	 */
	private static String byteToHexString(final byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		final int d1 = n / 16;
		final int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * MD5编码
	 * 
	 * @param origin 原始字符串
	 * @return 经过MD5加密之后的结果
	 */
	public static String MD5Encode(final String origin, final String encoding) {
		String resultString = null;
		try {
			resultString = origin;
			final MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes(encoding)));
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}

}
