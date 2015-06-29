package com.ycxc.base.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;

import sun.misc.BASE64Encoder;


/**
 * 发送短信工具类。
 * 使用的是luosimao提供的短信服务。
 * @author WeiGuobin
 */
public class SMSUtil 
{
	private static final Logger logger = Logger.getLogger(SMSUtil.class);
	
    /**
     * 使用luosimao短信服务发送短信。
     * @param mobile 目的手机号码
     * @param message 短信内容
     * @param apiKey 短信服务中心的秘钥，必须是final
     * @param smsRequestPath 短信服务中心请求路径
     * @return boolean true：发送成功；false：发送失败
     * @throws Exception
     */
	public static boolean sendSMS(String mobile,String message,final String apiKey,String smsRequestPath) throws Exception
	{
		logger.debug("发送短信到：" + mobile);
		
        DefaultHttpClient client = httpClientTrustingAllSSLCerts();  
        client.addRequestInterceptor(new HttpRequestInterceptor()
        {  
  
            public void process(HttpRequest request, HttpContext context)  
                    throws HttpException, IOException
            {
                request.addHeader("Accept-Encoding", "gzip");  
                request.addHeader("Authorization", "Basic "  
                        + new BASE64Encoder()  
                                .encode(apiKey.getBytes("utf-8")));  
            }  
        });  
        client.getParams().setIntParameter(  
                CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);  
        client.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT,  
                30000);
        
        HttpPost request = new HttpPost(smsRequestPath);  
        ByteArrayOutputStream bos = null;  
        InputStream bis = null;  
        byte[] buf = new byte[10240];  
        String content = null;
        
        try
        {
            List<NameValuePair> params = new ArrayList<NameValuePair>();  
            params.add(new BasicNameValuePair("mobile", mobile)); 
            params.add(new BasicNameValuePair("message",message));
            request.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            HttpResponse response = client.execute(request);
            
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            { 
                bis = response.getEntity().getContent();  
                Header[] gzip = response.getHeaders("Content-Encoding");  
                bos = new ByteArrayOutputStream();  
                int count; 
                
                while ((count = bis.read(buf)) != -1)
                {  
                    bos.write(buf, 0, count);  
                } 
                
                bis.close();
                
                if (gzip.length > 0  
                        && gzip[0].getValue().equalsIgnoreCase("gzip"))
                { 
                    GZIPInputStream gzin = new GZIPInputStream(  
                            new ByteArrayInputStream(bos.toByteArray()));  
                    StringBuffer sb = new StringBuffer();  
                    int size;
                    
                    while ((size = gzin.read(buf)) != -1)
                    {  
                        sb.append(new String(buf, 0, size, "utf-8"));  
                    }  
                    gzin.close();  
                    bos.close();
                    content = sb.toString();  
                }
                else
                {  
                    content =  bos.toString();
                } 
                
                logger.debug("短信接口返回的结果：" + content);
            }
            else
            {  
            	logger.debug("短信接口请求返回的结果：" + response.getStatusLine().getStatusCode());
            } 
            
            //短息服务返回结果压缩格式正确，返回结果信息（Json字符串，结果码的key是“error”，为0：发送成功）
            //如果压缩格式不正确，返回null
            String resultCode = JsonUtil.getValue("error", content);
            
            return "0".equals(resultCode)?true:false;
        }
        finally 
        {  
            if (bis != null)
            {  
                try 
                {  
                    bis.close();// 最后要关闭BufferedReader  
                }
                catch (Exception e)
                {
                	e.printStackTrace();
                }
            }
            
            logger.debug("发送短信操作结束。");
        }  
    }  
  
    private static DefaultHttpClient httpClientTrustingAllSSLCerts()  
            throws NoSuchAlgorithmException, KeyManagementException 
    {  
        DefaultHttpClient httpclient = new DefaultHttpClient();  
  
        SSLContext sc = SSLContext.getInstance("SSL");  
        sc.init(null, getTrustingManager(), new java.security.SecureRandom());  
  
        SSLSocketFactory socketFactory = new SSLSocketFactory(sc);  
        Scheme sch = new Scheme("https", 443, socketFactory);  
        httpclient.getConnectionManager().getSchemeRegistry().register(sch);  
        return httpclient;  
    }  
  
    private static TrustManager[] getTrustingManager()
    {  
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager()
        {  
            public java.security.cert.X509Certificate[] getAcceptedIssuers()
            {  
                return null;  
            }  
  
            public void checkClientTrusted(X509Certificate[] certs,  
                    String authType)
            {  
                // Do nothing  
            }  
  
            public void checkServerTrusted(X509Certificate[] certs,  
                    String authType)
            {  
                // Do nothing  
            }  
  
        } };  
        return trustAllCerts;  
    }
    
//    public static void main(String[] args)
//	{
//		final String apiKey = "api:key-075baadd38c5b7200643e9c69a64567d";
//		String smsRequestPath = "https://sms-api.luosimao.com/v1/send.json";
//		try {
//			boolean result = sendSMS("13798529269","欢迎注册熊孩子，你的注册码是345623。【中车信息】",apiKey,smsRequestPath);
//			System.out.println(result);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
