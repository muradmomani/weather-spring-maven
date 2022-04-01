package com.progressoft.jipfive;

import java.time.LocalDate;

public class DateNew {
//TODO NotUsed
	public static LocalDate getDate(String date) {

		int year = Integer.valueOf(date.substring(0, 4));
		int month = Integer.valueOf(date.substring(4, 6));
		int day = Integer.valueOf(date.substring(6, 8));
		LocalDate date1 = LocalDate.of(year, month, day);
		return date1;
	}

	public static String getStringOfDate(LocalDate date) {
		return date.getYear() + "" + date.getMonth() + "" + date.getDayOfMonth();

	}
}
