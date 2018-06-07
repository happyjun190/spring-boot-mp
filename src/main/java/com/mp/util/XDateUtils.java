package com.mp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 文件名：DateUtils.java 日期处理相关工具类
 */
public class XDateUtils {
	
	/** 定义常量 **/
	public static final String DATE_JFP_STR = "yyyyMM";
	public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_SMALL_STR = "yyyy-MM-dd";
	public static final String DATE_KEY_STR = "yyMMddHHmmss";
	public static final String DATE_FULLKEY_STR = "yyyyMMddHHmmss";
	public static final String DATE_MONTH_STR = "yyyy-MM";
	public static final String DATE_DAY_STR = "yyyyMMdd";
	public static final String DATE_WEEK_STR = "yyyyww";

	public static final String DATETIME_LOCAL_FMT = "yyyy-MM-dd'T'HH:mm:ss";
	
	/**
	 * 格式化串的正则表达式
	 */
	@SuppressWarnings("serial")
	public static final Map<String, String> format = new HashMap<String, String>(){{
		put("yyyy-MM-dd'T'HH:mm:ss", "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$");
		put("yyyy-MM-dd'T'HH:mm", "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}$");
		put("yyyy-MM-dd HH:mm:ss", "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$");
	}};
	
	/**
	 * 使用预设格式提取字符串日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @return
	 */
	public static Date parse(String strDate) {
		return parse(strDate, DATE_FULL_STR);
	}

	/**
	 * 使用用户格式提取字符串日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 两个时间比较
	 * 
	 * @param date1
	 * @return
	 */
	public static int compareDateWithNow(Date date1) {
		Date date2 = new Date();
		int rnum = date1.compareTo(date2);
		return rnum;
	}

	/**
	 * 两个时间比较(时间戳比较)
	 * 
	 * @param date1
	 * @return
	 */
	public static int compareDateWithNow(long date1) {
		long date2 = dateToUnixTimestamp();
		if (date1 > date2) {
			return 1;
		} else if (date1 < date2) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @return
	 */
	public static String getNowTime() {
		SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);
		return df.format(new Date());
	}

	/**
	 * 获取系统当前时间
	 * 
	 * @return
	 */
	public static String getNowTime(String type) {
		SimpleDateFormat df = new SimpleDateFormat(type);
		return df.format(new Date());
	}

	/**
	 * 获取系统当前计费期
	 * 
	 * @return
	 */
	public static String getJFPTime() {
		SimpleDateFormat df = new SimpleDateFormat(DATE_JFP_STR);
		return df.format(new Date());
	}

	/**
	 * 将指定的日期转换成Unix时间戳
	 * 
	 * @param date 需要转换的日期，使用格式：yyyy-MM-dd HH:mm:ss
	 * @return long 时间戳，单位：秒
	 */
	public static long dateToUnixTimestamp(String date) {
		String d = date.trim();
		//判断date类型自动选择格式化串：
		for(String k : format.keySet()){
			if(RegexUtils.matches(d, format.get(k))){
				return dateToUnixTimestamp(d, k);
			}
		}
		
		return dateToUnixTimestamp(d, DATE_FULL_STR);
	}

	/**
	 * 将指定的日期转换成Unix时间戳
	 * 
	 * @param String date 需要转换的日期
	 * @param String dateFormat 需要转换的日期格式
	 * @return long 时间戳，单位：秒
	 */
	public static long dateToUnixTimestamp(String date, String dateFormat) {
		long timestamp = 0;
		try {
			timestamp = new SimpleDateFormat(dateFormat).parse(date).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timestamp/1000;
	}

	/**
	 * 将当前日期转换成Unix时间戳
	 * 
	 * @return long 时间戳
	 */
	public static long dateToUnixTimestamp() {
		long timestamp = new Date().getTime();
		return timestamp;
	}

	/**
	 * 将当前日期转换成Int类型，便于插入数据库中
	 * 
	 * @return int (长度10位)
	 * @author zhangzhang
	 */
	public static int dateToInt() {
		String strTime = new Date().getTime() + "";
		return Integer.parseInt(strTime.substring(0, 10));
	}

	/**
	 * 将Unix时间戳转换成日期
	 * 
	 * @param long timestamp 时间戳
	 * @return String 日期字符串
	 */
	public static Date unixTimestampToDate(long timestamp) {
		SimpleDateFormat sd = new SimpleDateFormat(DATE_FULL_STR);
		sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		return new Date(timestamp);
	}

	/**
	 * 日期转String
	 * 
	 * @return
	 */
	public static String getStringTime(Date date, String type) {
		SimpleDateFormat df = new SimpleDateFormat(type);
		return df.format(date);
	}

	/**
	 * 将Unix时间戳转换成日期
	 * 
	 * @param long timestamp 时间戳
	 * @return String 日期字符串
	 */
	public static String getStringTime(long timestamp, String type) {
		long tt = timestamp * 1000;
		Date date = new Date(tt);
		SimpleDateFormat df = new SimpleDateFormat(type);
		return df.format(date);
	}

	/**
	 * 将当前日期转换成Unix时间戳
	 * 
	 * @return String 时间戳
	 */
	public static String dataToStringUnixTimestamp() {
		return String.valueOf(new Date().getTime() / 1000);
	}

	/**
	 * 根据数据库取出来的时间戳获取yyyy-MM-dd格式的日期字符串
	 * 
	 * @param time
	 * @return
	 */
	public static String getTimeStrWithDbTimeStamp(int time) {
		Date date = new Date(time * 1000L);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String timeStr = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH);
		return timeStr;
	}

	/**
	 * 根据数据库取出来的时间戳，转化成formart格式的日期字符串
	 * 
	 * @param time
	 * @param formart
	 *            日期格式，如: yyyy/MM/dd
	 * @return
	 */
	public static String getTimeStrWithDbTimeStampFormat(int time, String formart) {
		return getStringTime(new Date(time * 1000L), formart);
	}
	
	
	
	/**
	 * 把日期往后增加interval周期.整数往后推,负数往前移动 
	 * @param date
	 * @param intervalPeriods
	 * @param type
	 * @return
	 */
	public static String getIntervalPeriod(Date date, int intervalPeriods, int periodType, String formatType) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(periodType, intervalPeriods);//把日期往后增加一个周期.整数往后推,负数往前移动 
		date=calendar.getTime();
		String dateStr = getStringTime(date, formatType);
		return dateStr;
	}
	
	/**
	 * 获取几天前00:00:00的时间戳
	 */
	public static int getBeforeDate(int day) {
		Date d = parse(getNowTime(XDateUtils.DATE_DAY_STR),DATE_DAY_STR);
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.add(Calendar.DATE, -day);
		Date beforeDate = c.getTime();
		return (int)(beforeDate.getTime()/1000);
	}

	public static void main(String[] args) {
		System.out.println(getBeforeDate(7));
	}
}