package com.tencent.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.tencent.service.IServiceRequest;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.ClassLoaderReference;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.mapper.DefaultMapper;

/**
 * User: rizenguo Date: 2014/10/29 Time: 14:36
 */
public class HttpsRequest implements IServiceRequest {

	public interface ResultListener {

		public void onConnectionPoolTimeoutError();

	}

	private static Logger log =
			Logger.getLogger(HttpsRequest.class);

	// 表示请求器是否已经做了初始化工作
	// private boolean hasInit = false;

	// 连接超时时间，默认10秒
	private int socketTimeout = 10000;

	// 传输超时时间，默认30秒
	private int connectTimeout = 30000;

	// 请求器的配置
	private RequestConfig requestConfig;

	// HTTP请求器 线程安全
	private static CloseableHttpClient httpClient;

	private static volatile boolean init = false;

	static {
		try {
			log.info("初始化");
			init();
		} catch (final Exception e) {
			log.error(HttpsRequest.class.getName() + "初始化失败:" + e.getMessage());
		}
	}

	public static void init() {
		log.debug("----------初始化httpClient-------------");
		FileInputStream instream = null;
		try {
			final KeyStore keyStore = KeyStore.getInstance("PKCS12");
			instream = new FileInputStream(new File(
					WxConfig.getCertLocalPath()));// 加载本地的证书进行https加密传输
			keyStore.load(instream, WxConfig.getCertPassword().toCharArray());// 设置证书密码
			// Trust own CA and all self-signed certs
			final SSLContext sslcontext = SSLContexts
					.custom()
					.loadKeyMaterial(keyStore,
							WxConfig.getCertPassword().toCharArray())
					.build();
			// Allow TLSv1 protocol only
			final SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslcontext,
					new String[] { "TLSv1" },
					null,
					SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			httpClient = HttpClients.custom()
					.setSSLSocketFactory(sslsf)
					.build();
			init = true;
		} catch (final Exception e) {
			log.error(e.getMessage());
			return;
		} finally {
			if (instream != null) {
				try {
					instream.close();
				} catch (final IOException e) {
					log.error(e.getMessage());
				}
			}
		}

	}

	public HttpsRequest() {

		initConfig();
	}

	private void initConfig() {
		if (!init) {
			init();
		}
		// 根据默认超时限制初始化requestConfig
		requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout).build();

	}

	private void resetRequestConfig() {
		requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
				.setConnectTimeout(connectTimeout).build();
	}

	/**
	 * 通过Https往API post xml数据
	 * 
	 * @param url
	 *        API地址
	 * @param xmlObj
	 *        要提交的XML数据对象
	 * @return API回包的实际数据
	 * @throws IOException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	@Override
	public String sendPost(final String url, final Object xmlObj)
			throws IOException, KeyStoreException, UnrecoverableKeyException,
			NoSuchAlgorithmException, KeyManagementException {

		String result = null;

		final HttpPost httpPost = new HttpPost(url);

		final String postDataXML = toXml(xmlObj);

		log.debug("API，POST过去的数据是：");
		log.debug(postDataXML);

		// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
		final StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
		httpPost.addHeader("Content-Type", "text/xml");
		httpPost.setEntity(postEntity);

		// 设置请求器的配置
		httpPost.setConfig(requestConfig);

		log.debug("executing request" + httpPost.getRequestLine());

		try {
			final HttpResponse response = httpClient.execute(httpPost);

			final HttpEntity entity = response.getEntity();

			result = EntityUtils.toString(entity, "UTF-8");

		} catch (final ConnectionPoolTimeoutException e) {
			log.error("http get throw ConnectionPoolTimeoutException(wait time out)");

		} catch (final ConnectTimeoutException e) {
			log.error("http get throw ConnectTimeoutException");

		} catch (final SocketTimeoutException e) {
			log.error("http get throw SocketTimeoutException");

		} catch (final Exception e) {
			log.error("http get throw Exception");

		} finally {
			httpPost.abort();
		}

		return result;
	}

	/**
	 * 设置传输超时时间
	 * 
	 * @param connectTimeout
	 *        传输时长，默认30秒
	 */
	public void setConnectTimeout(final int connectTimeout) {
		this.connectTimeout = connectTimeout;
		resetRequestConfig();
	}

	/**
	 * 允许商户自己做更高级更复杂的请求器配置
	 * 
	 * @param requestConfig
	 *        设置HttpsRequest的请求器配置
	 */
	public void setRequestConfig(final RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}

	/**
	 * 设置连接超时时间
	 * 
	 * @param socketTimeout
	 *        连接时长，默认10秒
	 */
	public void setSocketTimeout(final int socketTimeout) {
		this.socketTimeout = socketTimeout;
		resetRequestConfig();
	}

	public String toXml(final Object xmlObj) {
		// 解决XStream对出现双下划线的bug
		final XStream xStreamForRequestPostData = new XStream(new DomDriver(
				"UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		xStreamForRequestPostData
				.registerConverter(new MapConverter(
						new DefaultMapper(
								new ClassLoaderReference(XStream.class
										.getClassLoader()))));
		// 将要提交给API的数据对象转换成XML格式数据Post给API
		final String postDataXML = xStreamForRequestPostData.toXML(xmlObj);
		return postDataXML;
	}
}
