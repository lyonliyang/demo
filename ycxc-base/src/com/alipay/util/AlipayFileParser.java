/**
 * 
 */
package com.alipay.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author luo bohui
 * 
 */
public class AlipayFileParser {

	private static final String RECORD_SPLIT = ",";

	private static final Logger log = Logger.getLogger(AlipayFileParser.class);

	/**
	 * 解析从支付宝导出的对账文件<br/>
	 * 若文件格式发生变化,该方法将不可用
	 * 
	 * @author luo bohui
	 * @date 2015年6月12日
	 * @param absolutePath
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, String>> parseCSVFile(final String absolutePath) throws IOException {
		final List<Map<String, String>> recordList = new ArrayList<Map<String, String>>();
		// TODO 编码怎么处理
		final BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(absolutePath), "gbk"));
		String descLine = reader.readLine();
		while (descLine != null) {
			if (descLine.contains("账务明细列表")) {
				break;
			}
			descLine = reader.readLine();
		}
		String record = reader.readLine();
		final String[] headArray = record.split(",");
		while (record != null) {
			if (record.startsWith("#")) {
				break;
			}
			recordList.add(parseBillCheckingData(record, headArray));
			record = reader.readLine();
		}
		reader.close();
		return recordList;
	}

	private static Map<String, String> parseBillCheckingData(final String record, final String[] headerArray) {
		if (StringUtils.isEmpty(record)) {
			return Collections.emptyMap();
		}
		final int headerLength = headerArray.length;
		final Map<String, String> valueMap = new HashMap<String, String>(headerLength);
		final String[] valueList = record.split(RECORD_SPLIT);
		if (valueList.length != headerLength) {
			log.error("支付宝对账记录与头部不匹配,返回空map");
			return Collections.emptyMap();
		}

		for (int i = 0; i < headerLength; i++) {
			valueMap.put(headerArray[i], valueList[i]);
		}
		return valueMap;
	}

	public static void main(final String[] args) throws IOException {
		for (final Map<String, String> record : parseCSVFile("D:\\20884118550861540156_20150601_账务明细.csv")) {
			System.out.println(record);
		}
	}
}
