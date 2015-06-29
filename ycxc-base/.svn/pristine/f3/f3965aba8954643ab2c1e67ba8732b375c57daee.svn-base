package com.ycxc.base.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

/**
 * 工具类
 * 
 * @author yuanchangjian
 */
public class ToolsUtil
{

	private static Logger logger = Logger.getLogger(ToolsUtil.class);

	/**
	 * 获取length长度的随机数字字符串。
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomCode(final int length)
	{
		final char[] numbers = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		final int numbersLength = numbers.length;
		final StringBuffer authCode = new StringBuffer();
		final Random random = new Random();

		for (int i = 0; i < length; i++)
		{
			authCode.append(numbers[random.nextInt(numbersLength)]);
		}

		return authCode.toString();
	}

	/**
	 * 获取用户的IP地址
	 * 
	 * @param request
	 *        当前请求
	 * @return 用户的IP地址
	 */
	public static String getIpAddr(final HttpServletRequest request)
	{
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("HTTP_CLIENT_IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 进行HTTP请求
	 * 
	 * @param http
	 *        请求的地址
	 * @param content
	 *        请求的参数
	 * @param encoding
	 *        服务器端请求编码
	 * @return 请求结果
	 */
	public static String getResult(final String http, final String content, final String encoding) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(http);
			connection = (HttpURLConnection) url.openConnection();// 新建连接实例
			connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
			connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
			connection.setDoOutput(true);// 是否打开输出流 true|false
			connection.setDoInput(true);// 是否打开输入流true|false
			connection.setRequestMethod("POST");// 提交方法POST|GET
			connection.setUseCaches(false);// 是否缓存true|false
			connection.connect();// 打开连接端口
			final DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());// 打开输出流往对端服务器写数据
			out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
			out.flush();// 刷新
			out.close();// 关闭输出流
			final BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据
			// ,以BufferedReader流来读取
			final StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();// 关闭连接
			}
		}
		return null;
	}

	/**
	 * unicode码转换中文
	 * 
	 * @param data
	 *        unicode码
	 * @return 中文
	 */
	public static String decodeUnicode(final String data) {
		char aChar;
		final int len = data.length();
		final StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = data.charAt(x++);
			if (aChar == '\\') {
				aChar = data.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = data.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed      encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}

	/**
	 * 获取用户的IP归属地
	 * 
	 * @param ip
	 *        用户的IP
	 * @return IP归属地
	 * @throws Exception
	 */
	public static String getAddresses(final String ip) throws Exception {
		final String http = "http://ip.taobao.com/service/getIpInfo.php";
		final String data = getResult(http, "ip=" + ip, "utf-8");
		if (data != null) {
			final String[] temp = data.split(",");
			if (temp.length < 3) {
				return "0";
			}
			String region = (temp[5].split(":"))[1].replaceAll("\"", "");
			region = decodeUnicode(region);
			String country = "";
			String area = "";
			String city = "";
			String county = "";
			String isp = "";
			for (int i = 0; i < temp.length; i++) {
				switch (i) {
				case 1:
					country = (temp[i].split(":"))[2].replaceAll("\"", "");
					country = decodeUnicode(country);
					break;
				case 3:
					area = (temp[i].split(":"))[1].replaceAll("\"", "");
					area = decodeUnicode(area);
					break;
				case 5:
					region = (temp[i].split(":"))[1].replaceAll("\"", "");
					region = decodeUnicode(region);
					break;
				case 7:
					city = (temp[i].split(":"))[1].replaceAll("\"", "");
					city = decodeUnicode(city);
					break;
				case 9:
					county = (temp[i].split(":"))[1].replaceAll("\"", "");
					county = decodeUnicode(county);
					break;
				case 11:
					isp = (temp[i].split(":"))[1].replaceAll("\"", "");
					isp = decodeUnicode(isp);
					break;
				}
			}
			return region + "," + city + "," + isp;
		}
		return null;
	}

	/**
	 * 向FTP服务器上传文件。
	 * 
	 * @param ftpUrl
	 *        ftp服务器hostname（IP）
	 * @param ftpPort
	 *        ftp服务器端口
	 * @param userName
	 *        ftp服务器登录名
	 * @param password
	 *        ftp服务器登录密码
	 * @param uploadPath
	 *        上传到ftp服务器的位置路径
	 * @param fileName
	 *        上传的文件名称
	 * @param input
	 *        上传的文件输入流
	 * @return boolean 上传成功与否
	 */
	public static boolean ftpUpload(final String ftpUrl, final int ftpPort, final String userName,
			final String password, final String uploadPath, final String fileName, final InputStream input)
	{
		boolean uploadResult = false;
		final FTPClient ftp = new FTPClient();
		try
		{
			int reply;

			// 连接FTP服务器
			ftp.connect(ftpUrl, ftpPort);

			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			// 登录
			ftp.login(userName, password);

			ftp.enterLocalPassiveMode();
			// 设置FTPClient的传输模式为二进制（默认是ASCII）
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply))
			{
				logger.error("链接FTP服务器失败:[" + reply + "]");
				logger.error("ftpUrl:" + ftpUrl + " ftpPort:" + ftpPort + " userName:" + userName + " password:"
						+ password + " uploadPath:" + uploadPath + " fileName:" + fileName);
				final StringBuilder sb = new StringBuilder();
				if (ftp.getReplyStrings() != null) {
					for (final String s : ftp.getReplyStrings()) {
						sb.append(s);
					}
				}
				logger.error(ftp.getReplyString() + " " + sb.toString());
				ftp.disconnect();
			}
			else
			{

				final String[] paths = uploadPath.split("/");

				final StringBuffer sb = new StringBuffer();
				sb.append("/");
				// 循环每级目录
				for (int i = 0; i < paths.length; i++)
				{
					if (!paths[i].equals(""))
					{
						sb.append(paths[i] + "/");

						// 重新拼成目录，
						final String path = sb.toString();

						System.out.println(path + "path");
						// 切换工作目录，如果返回false表示该目录不存在，同时检查每级目录是否已经创建
						if (!ftp.changeWorkingDirectory(path))
						{
							logger.debug("上传路径不存在。开始创建该路径目录...---第" + i + "次创建---...");
							// 创建目录

							if (ftp.makeDirectory(path))
							{
								// 切换到新生成的工作目录
								ftp.changeWorkingDirectory(path);
								logger.debug("创建目录成功。");
							}
							else
							{
								logger.debug("创建目录失败！");
								return uploadResult;
							}
						}
					}
				}

				final boolean result = ftp.storeFile(fileName, input);

				if (result)
				{
					logger.debug("上传文件到FTP服务器成功。");
				}
				else
				{
					logger.debug("上传文件到FTP服务器失败!");
				}

				input.close();
				ftp.logout();
				uploadResult = true;
			}
		} catch (final IOException e)
		{
			logger.error("上传文件到FTP服务器失败!", e);
		} finally
		{
			if (ftp.isConnected())
			{
				try
				{
					ftp.disconnect();
				} catch (final IOException ioe)
				{
					logger.error("关闭FTP服务器连接失败!", ioe);
				}
			}
		}

		return uploadResult;
	}
	
	
	public static boolean delFtp(final String ftpUrl, final int ftpPort, final String userName,
			final String password, final String uploadPath, final String fileName, final InputStream input)
	{
		boolean uploadResult = false;
		final FTPClient ftp = new FTPClient();
		try
		{
			int reply;

			// 连接FTP服务器
			ftp.connect(ftpUrl, ftpPort);

			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			// 登录
			ftp.login(userName, password);

			ftp.enterLocalPassiveMode();
			// 设置FTPClient的传输模式为二进制（默认是ASCII）
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply))
			{
				logger.error("链接FTP服务器失败:[" + reply + "]");
				logger.error("ftpUrl:" + ftpUrl + " ftpPort:" + ftpPort + " userName:" + userName + " password:"
						+ password + " uploadPath:" + uploadPath + " fileName:" + fileName);
				final StringBuilder sb = new StringBuilder();
				if (ftp.getReplyStrings() != null) {
					for (final String s : ftp.getReplyStrings()) {
						sb.append(s);
					}
				}
				logger.error(ftp.getReplyString() + " " + sb.toString());
				ftp.disconnect();
			}
			else
			{

				final String[] paths = uploadPath.split("/");

				final StringBuffer sb = new StringBuffer();
				sb.append("/");
				// 循环每级目录
				for (int i = 0; i < paths.length; i++)
				{
					if (!paths[i].equals(""))
					{
						sb.append(paths[i] + "/");

						// 重新拼成目录，
						final String path = sb.toString();

						System.out.println(path + "path");
						// 切换工作目录，如果返回false表示该目录不存在，同时检查每级目录是否已经创建
						if (!ftp.changeWorkingDirectory(path))
						{
							logger.debug("上传路径不存在。开始创建该路径目录...---第" + i + "次创建---...");
							// 创建目录

							if (ftp.makeDirectory(path))
							{
								// 切换到新生成的工作目录
								ftp.changeWorkingDirectory(path);
								logger.debug("创建目录成功。");
							}
							else
							{
								logger.debug("创建目录失败！");
								return uploadResult;
							}
						}
					}
				}

				final boolean result = ftp.storeFile(fileName, input);

				if (result)
				{
					logger.debug("上传文件到FTP服务器成功。");
				}
				else
				{
					logger.debug("上传文件到FTP服务器失败!");
				}

				input.close();
				ftp.logout();
				uploadResult = true;
			}
		} catch (final IOException e)
		{
			logger.error("上传文件到FTP服务器失败!", e);
		} finally
		{
			if (ftp.isConnected())
			{
				try
				{
					ftp.disconnect();
				} catch (final IOException ioe)
				{
					logger.error("关闭FTP服务器连接失败!", ioe);
				}
			}
		}

		return uploadResult;
	}
	

	/**
	 * 按长宽进行缩放图片,不需要裁剪的情况使用<br>
	 * 
	 * @author yuanchangjian<br>
	 *         2015年4月7日17:04:19
	 * @param inputStream
	 *        文件流
	 * @param height
	 *        高度
	 * @param width
	 *        长度
	 * @param newFilePath
	 *        新文件全路径
	 * @throws IOException
	 */
	public static BufferedImage scalePic(final BufferedImage bufferedImage, final int height, final int width)
	{
		try
		{
			final BufferedImage bi = Thumbnails.of(bufferedImage).size(width, height).keepAspectRatio(true).asBufferedImage();
			return bi;
		} catch (final IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 裁剪的情况使用，以左下角的位置进行裁剪<br>
	 * 
	 * @author yuanchangjian<br>
	 *         2015年4月8日10:48:11
	 * @param inputStream
	 *        文件流
	 * @param height
	 *        高度
	 * @param width
	 *        长度
	 * @param newFilePath
	 *        新文件全路径
	 * @throws IOException
	 */
	public static BufferedImage cutPic(final BufferedImage bufferedImage, final int height, final int width)
	{
		try
		{
			final BufferedImage bi = Thumbnails.of(bufferedImage).sourceRegion(Positions.BOTTOM_LEFT, width, height)
					.size(width, height).keepAspectRatio(true).asBufferedImage();
			return bi;
		} catch (final IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将BufferedImage的图片进行处理并返回输入流<br>
	 * 2015年4月9日09:15:56
	 * 
	 * @author yuanchangjian
	 * @param stream
	 *        ,传入的BufferedImage
	 * @param width
	 *        指定的长度
	 * @param height
	 *        指定的高度
	 * @param operate
	 *        操作（cut：裁剪，scale：缩放）
	 * @return
	 */

	public static InputStream processPhoto(final BufferedImage stream, final int width, final int height,
			final String operate)
	{

		try {
			// 生成新的图片文件,需要裁剪，返回BufferedImage,如果是裁剪图片
			if (operate.equals("cut"))
			{
				final BufferedImage newfBufferedImage = ToolsUtil.cutPic(stream, width, height);
				// 将BufferedImage转为inputStream
				final ByteArrayOutputStream bs = new ByteArrayOutputStream();

				final ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);

				ImageIO.write(newfBufferedImage, "jpg", imOut);

				final InputStream newIs = new ByteArrayInputStream(bs.toByteArray());

				return newIs;
			}
			else if (operate.equals("scale"))
			{
				final BufferedImage newfBufferedImage = ToolsUtil.scalePic(stream, width, height);
				// 将BufferedImage转为inputStream
				final ByteArrayOutputStream bs = new ByteArrayOutputStream();

				final ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);

				ImageIO.write(newfBufferedImage, "jpg", imOut);

				final InputStream newIs = new ByteArrayInputStream(bs.toByteArray());

				return newIs;
			}
		} catch (final IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
