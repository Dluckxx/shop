package com.dluck.shop.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
	//得到x天后的时间
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	//根据传入的Date返回当前月一号的Timestamp
	public static Timestamp getTimestampThisMonthStart(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

		String start = sdf.format(date);

		try {
			return new Timestamp(sdf.parse(start).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return new Timestamp(new Date().getTime());
		}
	}

	//根据传入的Date返回下一个月一号的Timestamp
	public static Timestamp getTimestampNextMonthStart(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthSdf = new SimpleDateFormat("MM");

		int year = Integer.parseInt(yearSdf.format(date));
		int month = Integer.parseInt(monthSdf.format(date));

		if (month == 12) {
			year++;
			month = 1;
		} else {
			month++;
		}

		String end = Integer.toString(year) + month;

		try {
			return new Timestamp(sdf.parse(end).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return new Timestamp(new Date().getTime());
		}
	}

	//根据传入的Date返回当前月一号的java.sql.Date
	public static java.sql.Date getSqlDateThisMonthStart(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

		String start = sdf.format(date);

		try {
			return new java.sql.Date(sdf.parse(start).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return new java.sql.Date(new Date().getTime());
		}
	}

	//根据传入的Date返回下一个月一号的java.sql.Date
	public static java.sql.Date getSqlDateNextMonthStart(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat monthSdf = new SimpleDateFormat("MM");

		int year = Integer.parseInt(yearSdf.format(date));
		int month = Integer.parseInt(monthSdf.format(date));

		if (month == 12) {
			year++;
			month = 1;
		} else {
			month++;
		}

		String end = Integer.toString(year) + month;

		try {
			return new java.sql.Date(sdf.parse(end).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return new java.sql.Date(new Date().getTime());
		}
	}
}
