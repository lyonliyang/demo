/**
 * 
 */
package com.unionpay.common;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unionpay.config.UnionField;

/**
 * @author luo bohui
 * 
 */
public abstract class UnionFileParser {

	/**
	 * 将对账单记录解析为map
	 * 
	 * @author luo bohui
	 * @date 2015年6月9日
	 * @param record
	 * @return
	 * @throws IOException
	 */
	public static Map<String, String> parseBillCheckingData(final String record) throws IOException {
		final String[][] fieldList = UnionField.fieldList;
		final Map<String, String> recordMap = new HashMap<String, String>(fieldList.length);
		final StringReader stringReader = new StringReader(record);

		char[] value = null;
		for (int i = 0; i < fieldList.length; i++) {
			final String[] fieldInfo = fieldList[i];
			value = new char[Integer.valueOf(fieldInfo[1])];
			if (i == 0) {
				stringReader.read(value);
			} else {
				stringReader.skip(1);
				stringReader.read(value);
			}
			recordMap.put(fieldInfo[0], String.valueOf(value).trim());
		}
		return recordMap;
	}

	/**
	 * 解析对账单记录明细文件
	 * 
	 * @author luo bohui
	 * @date 2015年6月9日
	 * @param absolutePath
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, String>> parseFile(final String absolutePath) throws IOException {
		final List<Map<String, String>> recordList = new ArrayList<Map<String, String>>();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(absolutePath)));
		String record = reader.readLine();
		while (record != null) {
			recordList.add(parseBillCheckingData(record));
			record = reader.readLine();
		}
		reader.close();
		return recordList;
	}

	/**
	 * 解析对账单记录明细文件
	 * 
	 * @author luo bohui
	 * @date 2015年6月9日
	 * @param absolutePath
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, String>> parseFile(final InputStream is) throws IOException {
		final List<Map<String, String>> recordList = new ArrayList<Map<String, String>>();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String record = reader.readLine();
		while (record != null) {
			recordList.add(parseBillCheckingData(record));
			record = reader.readLine();
		}
		reader.close();
		return recordList;
	}

	public static void main(final String[] args) throws IOException {
		System.out.println(parseFile("D:\\INN15052988ZM_824440375380004"));
	}
}
