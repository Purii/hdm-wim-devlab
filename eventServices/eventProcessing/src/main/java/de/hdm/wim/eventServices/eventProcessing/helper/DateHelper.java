package de.hdm.wim.eventServices.eventProcessing.helper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

/**
 * @author Benedikt Benz
 * @createdOn 01.06.2017
 */
public class DateHelper {

	/**
	 * The Constant DATE_MONDAY.
	 */
	private static final String _DATE_MONDAY = "monday";
	/**
	 * The Constant DATE_TUESDAY.
	 */
	private static final String _DATE_TUESDAY = "tuesday";
	/**
	 * The Constant DATE_WEDNESDAY.
	 */
	private static final String _DATE_WEDNESDAY = "wednesday";
	/**
	 * The Constant DATE_THURSDAY.
	 */
	private static final String _DATE_THURSDAY = "thursday";
	/**
	 * The Constant DATE_FRIDAY.
	 */
	private static final String _DATE_FRIDAY= "friday";
	/**
	 * The Constant DATE_SATURDAY.
	 */
	private static final String _DATE_SATURDAY = "saturday";
	/**
	 * The Constant DATE_SUNDAY.
	 */
	private static final String _DATE_SUNDAY = "sunday";

	/**
	 * Unify day of week with Java.Time.
	 *
	 * @param input the input
	 * @return the day of week
	 */
	private static DayOfWeek UnifyDayOfWeek(String input){

		DayOfWeek dow = null;

		switch(input.toLowerCase()) {
			case _DATE_MONDAY:       dow = DayOfWeek.MONDAY;     break;
			case _DATE_TUESDAY:      dow = DayOfWeek.TUESDAY;    break;
			case _DATE_WEDNESDAY:    dow = DayOfWeek.WEDNESDAY;  break;
			case _DATE_THURSDAY:     dow = DayOfWeek.THURSDAY;   break;
			case _DATE_FRIDAY:       dow = DayOfWeek.FRIDAY;     break;
			case _DATE_SATURDAY:     dow = DayOfWeek.SATURDAY;   break;
			case _DATE_SUNDAY:       dow = DayOfWeek.SUNDAY;     break;
		}
		return dow;
	}

	private static TimeDirection UnifyTimeDirection(String timeDirection){

		if(timeDirection.toLowerCase().equals("previous")
			|| timeDirection.toLowerCase().equals("last"))
			return TimeDirection.PAST;
		if(timeDirection.toLowerCase().equals("next")
			|| timeDirection.toLowerCase().equals("upcoming"))
			return TimeDirection.FUTURE;

		return TimeDirection.NOW;
	}

	public static LocalDate GetNextOrPrevDoW(String timeDirection, String dayOfweek){

		DayOfWeek dow 		= UnifyDayOfWeek(dayOfweek);
		TimeDirection td 	= UnifyTimeDirection(timeDirection);
		LocalDate today 	= LocalDate.now();

		if(td == TimeDirection.FUTURE)
			return today.with(TemporalAdjusters.next(dow));
		if(td == TimeDirection.PAST)
			return today.with(TemporalAdjusters.previous(dow));

		return today;
	}

	public enum TimeDirection {
		PAST,
		FUTURE,
		NOW
	}
}
