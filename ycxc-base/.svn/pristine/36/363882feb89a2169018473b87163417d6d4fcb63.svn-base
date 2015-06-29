/**
 * 
 */
package com.ycxc.base.util;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * @author luo bohui
 * 
 */
public class HttpClientUtils {

	public static String request(final Map<String, String> reqParams, final String paramName, final String url) {
		final HttpClient client = new HttpClient();
		final PostMethod method = new PostMethod(url);
		final NameValuePair[] params = new NameValuePair[reqParams.size()];
		int i = 0;
		for (final Entry<String, String> entry : reqParams.entrySet()) {
			params[i++] = new NameValuePair(entry.getKey(), entry.getValue());
		}
		method.addParameters(params);
		method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; text/html; charset=UTF-8");
		try {
			final int statusCode = client.executeMethod(method);
			System.out.println("请求statusCode为:" + statusCode);
			if (statusCode == 200) {
				return new String(method.getResponseBody(), "utf-8");
			}
		} catch (final HttpException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
