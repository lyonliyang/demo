/**
 * 
 */
package com.unionpay.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;
import com.unionpay.acp.sdk.SecureUtil;
import com.unionpay.config.UnionPayConfig;

/**
 * @author luo bohui
 * 
 */
public class UnionPayUtil {

	private static final String encoding = UnionPayConfig.ENCODING;
	private static final Logger log = Logger.getLogger(UnionPayUtil.class);

	public static String buildRequest(final String requestUrl,
			final Map<String, String> hiddens) {

		final StringBuilder sb = new StringBuilder();
		sb.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
		sb.append("<form id = \"pay_form\" action=\"" + requestUrl
				+ "\" method=\"post\">");
		if (null != hiddens && 0 != hiddens.size()) {
			final Set<Entry<String, String>> set = hiddens.entrySet();
			final Iterator<Entry<String, String>> it = set.iterator();
			while (it.hasNext()) {
				final Entry<String, String> ey = it.next();
				final String key = ey.getKey();
				final String value = ey.getValue();
				sb.append("<input type=\"hidden\" name=\"" + key + "\" id=\""
						+ key + "\" value=\"" + value + "\"/>");
			}
		}
		sb.append("</form>");
		sb.append("</body>");
		sb.append("<script type=\"text/javascript\">");
		sb.append("document.all.pay_form.submit();");
		sb.append("</script>");
		sb.append("</html>");
		return sb.toString();
	}

	public static Map<String, String> signData(final Map<String, String> params) {
		// 若报文中的数据元标识的key对应的value为空，不上送该报文域
		final Map<String, String> request = new HashMap<String, String>();
		request.putAll(params);
		final Iterator<String> iterator = params.keySet().iterator();
		while (iterator.hasNext()) {
			final String key = iterator.next();
			final String value = params.get(key);
			if (StringUtils.isNotEmpty(value)) {
				request.put(key, value);
			}
		}
		SDKUtil.sign(request, UnionPayConfig.ENCODING);
		return request;
	}

	public static String submitUrl(
			final Map<String, String> submitFromData, final String requestUrl)
			throws Exception {
		String resultString = "";
		// 发送http请求
		final HttpClient hc = new HttpClient(requestUrl, 30000, 30000);

		final int status = hc.send(submitFromData, encoding);
		if (200 == status) {
			resultString = hc.getResult();
		} else {
			log.error("银联请求返回状态码[" + status + "],错误信息为[" + hc.getResult() + "]");
		}
		return resultString;
	}

	public static String deCodeFileContent(final Map<String, String> resData) {
		// 解析返回文件
		final String fileContent = resData.get(SDKConstants.param_fileContent);
		if (StringUtils.isNotEmpty(fileContent)) {
			try {
				final byte[] fileArray = SecureUtil.inflater(SecureUtil
						.base64Decode(fileContent.getBytes(encoding)));
				final String root = UnionPayConfig.CHECK_ACCOUNT_PATH;
				String filePath = null;
				if (SDKUtil.isEmpty(resData.get("fileName"))) {
					filePath = root + File.separator + resData.get("merId")
							+ "_" + resData.get("batchNo") + "_"
							+ resData.get("txnTime") + ".txt";
				} else {
					filePath = root + File.separator + resData.get("fileName");
				}
				log.debug(filePath);
				final File parentDir = new File(root);
				if (!parentDir.exists()) {
					if (!parentDir.mkdirs()) {
						throw new IOException("创建对账文件根目录失败");
					}
				}
				final File file = new File(filePath);
				if (file.exists()) {
					file.delete();
				}
				file.createNewFile();
				final FileOutputStream out = new FileOutputStream(file);
				out.write(fileArray, 0, fileArray.length);
				out.flush();
				out.close();
				return filePath;
			} catch (final UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		} else {
			log.error("银联返回的对账文件信息内容为空~,请检查当日是否有订单生产");
		}
		return "";
	}
}
