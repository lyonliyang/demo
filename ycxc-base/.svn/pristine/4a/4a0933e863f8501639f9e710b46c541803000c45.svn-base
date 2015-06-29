/**
 * 
 */
package com.alipay.sign;

import com.alipay.config.AlipayConfig;

/**
 * @author luo bohui
 * 
 */
public class SignWrapper {

	public static boolean signVeryfy(final String preSignStr,
			final String sign, final String signType) {
		// 获得签名验证结果
		boolean isSign = false;
		if (signType.equals("MD5")) {
			isSign = MD5.verify(preSignStr, sign, AlipayConfig.key,
					AlipayConfig.input_charset);
		} else if (signType.equals("RSA")) {
			isSign = RSA.verify(preSignStr, sign, AlipayConfig.ali_public_key,
					AlipayConfig.input_charset);
		}
		return isSign;
	}
}
