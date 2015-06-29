package com.alipay.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	private static Logger log = Logger.getLogger(AlipayConfig.class);

	/**
	 * 合作身份者ID，以2088开头由16位纯数字组成的字符串
	 */
	public static String partner;
	/**
	 * md5 商户私钥
	 */
	public static String key;

	/**
	 * rsa 商户私钥
	 */
	public static String private_key;

	/**
	 * 支付宝的公钥，无需修改该值
	 */
	public static String ali_public_key;

	/**
	 * 调试用，创建TXT日志文件夹路径
	 */
	public static String log_path;

	/**
	 * 系统账户
	 */
	public static String seller_email;

	/**
	 * 字符编码格式 目前支持 gbk 或 utf-8
	 */
	public static String input_charset = "utf-8";

	/**
	 * web的签名方式
	 */
	public static String SIGN_TYPE_MD5 = "MD5";

	/**
	 * 手机端的签名方式
	 */
	public static String SIGN_TYPE_RSA = "RSA";

	public static String DEFAULT_SIGN_TYPE = "RSA";

	/**
	 * alipay支付接口url
	 */
	public static String ALIPAY_PAY_GATEWAY = "https://mapi.alipay.com/gateway.do?";

	/**
	 * 默认支付类型，表示商品购买，具体请参考支付宝文档
	 */
	public static String DEFAULT_PAYMENT_TYPE = "1";

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
		key = properties.getProperty("alipay.key");
		partner = properties.getProperty("alipay.partner");
		private_key = properties.getProperty("alipay.private_key");
		ali_public_key = properties.getProperty("alipay.ali_public_key");
		log_path = properties.getProperty("alipay.log_path");
		seller_email = properties.getProperty("alipay.seller_email");
	}

}
