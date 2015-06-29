/**
 * 
 */
package com.tencent.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
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
public class WxFileParser {

	private static final String RECORD_SPLIT = ",`";

	private static final String Header_SPLIT = ",";

	private static final String START_CHAR = "`";

	private static final Logger log = Logger.getLogger(WxFileParser.class);

	public static Map<String, String> parseRecord(final String record, final String[] headerArray) {
		if (StringUtils.isEmpty(record)) {
			return Collections.emptyMap();
		}
		final int headerLength = headerArray.length;
		final Map<String, String> valueMap = new HashMap<String, String>(headerLength);
		final String[] valueList = record.split(RECORD_SPLIT);
		if (valueList.length != headerLength) {
			log.error("微信对账记录与头部不匹配,返回空map");
			return Collections.emptyMap();
		}

		for (int i = 0; i < headerLength; i++) {
			valueMap.put(headerArray[i], valueList[i]);
		}
		return valueMap;
	}

	/**
	 * 返回微信对账单,最后一行记录为汇总信息
	 * 
	 * @author luo bohui
	 * @date 2015年6月10日
	 * @param checkData
	 * @return
	 */
	public static List<Map<String, String>> parseCheckingData(final String checkData) throws IOException {
		String parseData = checkData;
		if (parseData.charAt(0)==65279) {
			parseData = parseData.substring(1);
		}
		final BufferedReader reader = new BufferedReader(new StringReader(parseData));

		final List<Map<String, String>> checkMap = internalParse(reader);
		reader.close();
		return checkMap;
	}

	public static List<Map<String, String>> internalParse(final BufferedReader reader) throws IOException {
		final List<Map<String, String>> checkingMap = new ArrayList<Map<String, String>>();
		final String headerRecord = reader.readLine();
		final String[] headerArray = headerRecord.split(Header_SPLIT);
		String record = reader.readLine();
		while (record != null) {
			if (!record.startsWith(START_CHAR)) {
				break;
			}
			checkingMap.add(parseRecord(record.substring(1), headerArray));
			record = reader.readLine();
		}
		// 处理汇总记录(未做任何检查,如过微信返回信息格式发生变化,必须人工核查)
		checkingMap.add(parseRecord(reader.readLine().substring(1), record.split(Header_SPLIT)));
		return checkingMap;
	}

	public static List<Map<String, String>> parseCheckingFile(final String absolutePath) throws IOException {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(absolutePath)));

		final List<Map<String, String>> checkMap = internalParse(reader);
		reader.close();
		return checkMap;
	}

	/**
	 * @author luo bohui
	 * @date 2015年6月10日
	 * @param args
	 */
	public static void main(final String[] args) throws IOException {
		for (final Map<String, String> record : parseCheckingFile("D:\\wx_bill")) {
			System.out.println(record);
		}

	}

}
