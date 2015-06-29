/*
 * Copyright 2008-2010 erong software, Inc. All rights reserved.
 * SHENZHEN ERONG SOFTWARE CO.,LTD. WWW.ERONGSOFT.COM
 */

package com.ycxc.base.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author joshuaxu
 * 
 */
public class DateTimeUtil {
	public static final String DATE_CHAR_PATTERN = "yyyyMMdd";
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String DATETIME_CHAR_PATTERN = "yyyyMMddHHmmss";
	public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String ORA_DATE_PATTERN = "yyyy-mm-dd";
	public static final String ORA_DATETIME_PATTERN = "yyyy-mm-dd hh24:mi:ss";
	public static final String ORA_TIMESTAMP_PATTERN = "yyyy-mm-dd hh24:mi:ss.ff3";
	public static final String DATETIME_APPOINTTIME = "yyyy-MM-dd HH:mm";//预约时间
	
	public static String format(Date date)
	{
		return format(date,null);
	}
	public static String format(Date date, String pattern)
	{
		if (pattern==null)
			pattern=DATE_PATTERN;
		if(date==null)
			return "";
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}
	
	public static String formatDatetime(Date date)
	{
		return formatDatetime(date,null);
	}
	
	public static String formatDatetime(Date date, String pattern)
	{
		if (pattern==null)
			pattern=DATETIME_PATTERN;
		
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}
	
	
	public static Date parseDateChar(String dateString) throws java.text.ParseException
	{
		return parseDate(dateString,DATE_CHAR_PATTERN);
	}
	
	public static Date parseDate(String dateString) throws java.text.ParseException
	{
		return parseDate(dateString,DATE_PATTERN);
	}
	
	public static Date parseDate(String dateString, String pattern) throws java.text.ParseException
	{
		if (pattern==null)
			pattern=DATE_PATTERN;
		
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(pattern);
		java.util.Date date=(java.util.Date)simpleDateFormat.parse(dateString);
		if (!dateString.equals(simpleDateFormat.format(date)))
			throw new java.text.ParseException("日期格式错误", 0);
		return new Date(date.getTime());
	}
	
	public static Timestamp parseDatetime(String dateString) throws java.text.ParseException
	{
		return parseDatetime(dateString,DATETIME_PATTERN);
	}
	
	public static Timestamp parseDatetime(String dateString, String pattern) throws java.text.ParseException
	{
		if (pattern==null)
			pattern=DATETIME_PATTERN;
		
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(pattern);
		java.util.Date date= (java.util.Date) simpleDateFormat.parse(dateString);
		if (!dateString.equals(simpleDateFormat.format(date)))
			throw new java.text.ParseException("日期时间格式错误", 0);
		return new Timestamp(date.getTime());
	}
	
	public static Timestamp parseTimestamp(String dateString, String pattern) throws java.text.ParseException
	{
		if (pattern==null)
			pattern=DATETIME_PATTERN;
		
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat(pattern);
		java.util.Date date= (java.util.Date) simpleDateFormat.parse(dateString);
		if (!dateString.equals(simpleDateFormat.format(date)))
			throw new java.text.ParseException("日期时间格式错误", 0);
		return new Timestamp(date.getTime());
	}
	
}
