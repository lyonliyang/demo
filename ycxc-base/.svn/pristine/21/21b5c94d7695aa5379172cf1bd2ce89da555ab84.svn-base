package com.ycxc.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公用参数校验工具类。
 * @Author weiguobin
 * @Date 2015年3月25日
 */
public class ValidateUtil 
{
	/**
	 * 校验字符串是否是英文字母，不分大小写
	 * @author yuanchangjian
	 * @return
	 */
	public static boolean isEnglish(String value)
	{
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}
	
	public static boolean isNumber(String value)
	{
		Pattern pattern = Pattern.compile("^[0-9]*$");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}
	
	/**
	 * 校验电话号码（座机）
	 * @author yuanchangjian
	 * @return
	 */
	public static boolean checkPhone(String value)
	{
		Pattern pattern = Pattern.compile("^(\\d{3,4}-)?\\d{7,8}$");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}
	
	/**
	 * 校验电话号码（手机）
	 * @author yuanchangjian
	 * @return
	 */
	public static boolean checkMobile(String value)
	{
		Pattern pattern = Pattern.compile("^1(4[0-9]|3[0-9]|5[0-9]|8[0-9]|7[0-9])\\d{8}$");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}
	
	/**
	 * 校验电子邮箱
	 * @author yuanchangjian
	 * @return
	 */
	public static boolean checkEmail(String value)
	{
		Pattern pattern = Pattern.compile("^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$");
		Matcher m = pattern.matcher(value);
		return m.matches();
	}
	
	/**
	 * 校验密码
	 * @author yuanchangjian
	 * @return
	 */
	public static boolean checkPassWord(String value)
	{
		return value.length()>=6&&value.length()<=20?true:false;
	}
	
	/**
	 * 判断字符串是否为空或者空字符串。
	 * @author weiguobin
	 * @param value
	 * @return 如果为空或者空字符串，返回true；否则返回false。
	 */
	public static boolean isEmptyString(String value)
	{
		return (value == null || "".equals(value.trim()))?true:false;
	}
	
//	public static void main(String[] args) {
//		
//		System.out.println(ValidateUtil.isEnglish("aaa"));
//	
//		String s = null;
//		System.out.println(isEmptyString(s));
//		s = "    ";
//		System.out.println(isEmptyString(s));
//		s = "ad ";
//		System.out.println(isEmptyString(s));
//	}
}
