/**
 * 
 */
package com.unionpay.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author luo bohui
 * 
 */
public class UnionPayConfig {

	private static Logger log = Logger.getLogger(UnionPayConfig.class);
	/**
	 * 版本号
	 */
	public static final String VERSION = "5.0.0";
	/**
	 * 编码方式
	 */
	public static final String ENCODING = "UTF-8";

	/**
	 * 交易类型 01表示消费
	 */
	public static final String TXN_TYPE = "01";

	public static final String TXN_TYPE_REFUND = "04";

	public static final String TXN_TYPE_FILE = "76";
	/**
	 * 交易子类型 01表示自助消费
	 */
	public static final String TXN_SUBTYPE = "01";

	public static final String TXN_SUBTYPE_REFUND = "00";

	public static final String TXN_SUBTYPE_FILE = "01";
	/**
	 * 产品类型 B2C网关支付
	 */
	public static final String BIZ_TYPE_B2C = "000201";
	/**
	 * 产品类型 B2B网关支付
	 */
	public static final String BIZ_TYPE_B2B = "000202";
	/**
	 * 文件传输
	 */
	public static final String BIZ_TYPE_FILE = "000000";

	public static final String FILE_TYPE = "00";

	/**
	 * 渠道类型 互联网
	 */
	public static final String CHANNEL_TYPE_WEB = "07";
	/**
	 * 渠道类型 移动
	 */
	public static final String CHANNLE_TYPE_MOBILE = "08";
	/**
	 * 签名方式 RSA
	 */
	public static final String SIGN_METHOD = "01";

	/**
	 * 商户类型 0：商户 , 1：收单, 2：平台商户
	 */
	public static final String ACCESS_TYPE = "0";
	/**
	 * 商户号
	 */
	public static String MERID = "";

	/**
	 * 移动端调用返回给后台通知地址
	 */
	public static final String MOBILE_BACK_URL = "";

	/**
	 * 对账文件输出地址
	 */
	public static String CHECK_ACCOUNT_PATH = "";
	/**
	 * 默认币种 人民币
	 */
	public static final String CURRENCY_CODE = "156";

	static {
		log.debug("#################begin load acp_sdk.properties#################");
		final Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("acp_sdk.properties"));
		} catch (final IOException e) {
			log.error("加载支付配置信息失败...");
		}
		loadProperties(properties);
	}

	private static void loadProperties(final Properties properties) {
		MERID = properties.getProperty("union.merid");
		CHECK_ACCOUNT_PATH = properties.getProperty("union.checkAccountPath");
	}
}
