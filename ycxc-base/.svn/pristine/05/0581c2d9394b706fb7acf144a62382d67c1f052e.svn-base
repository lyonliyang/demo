/**
 * 
 */
package com.ycxc.pay.common;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author luo bohui
 * 
 */
public abstract class CommonConfig {

	private static Logger log = Logger.getLogger(CommonConfig.class);

	public static String PLATFORM_CODE = "";

	static {
		log.debug("#################begin load pay_sdk.properties#################");
		final Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("pay_sdk.properties"));
		} catch (final IOException e) {
			log.error("加载支付配置信息失败...");
		}
		loadProperties(properties);
	}

	private static void loadProperties(final Properties properties) {
		PLATFORM_CODE = properties.getProperty("platform_code");
	}
}
