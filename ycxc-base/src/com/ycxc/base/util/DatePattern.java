/**
 * 
 */
package com.ycxc.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author luo bohui
 * 
 */
public enum DatePattern
{
	/**
	 * 格式:yyyyMMdd
	 */
	DATE_CHAR_PATTERN("yyyyMMdd"),
	/**
	 * 格式:yyyy-MM-dd
	 */
	DATE_PATTERN("yyyy-MM-dd"),
	/**
	 * 格式:yyyyMMddHHmmss"
	 */
	DATETIME_CHAR_PATTERN("yyyyMMddHHmmss"),
	/**
	 * 格式:yyyy-MM-dd HH:mm:ss
	 */
	DATETIME_PATTERN("yyyy-MM-dd HH:mm:ss"),
	/**
	 * 格式:yyyy-MM-dd HH:mm:ss.SSS
	 */
	TIMESTAMP_PATTERN("yyyy-MM-dd HH:mm:ss.SSS");

	private String pattern;

	private DatePattern(final String pattern) {
		this.pattern = pattern;
	}

	public String format(final Date date) {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	public Date parseDate(final String dateValue) throws ParseException {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.parse(dateValue);
	}
}
