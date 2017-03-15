package qselect_3_web;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Common {
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	
	public static Date getPreDate(Date current, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(current);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}
	
	public static Date getPreDate(int days) {
		return getPreDate(new Date(), days);
	}
	
	public static String getPreDateString(Date current, int days) {
		Date date = getPreDate(current, days);
		return formatter.format(date);
	}
	
	public static String getPreDateString(int days) {
		return getPreDateString(new Date(), days);
	}
	
	public static String getCurrentDateString() {
		return formatter.format(new Date());
	}
	
	public static List<String> string2List(String src, String split) {
		String[] array = src.split(split);
		return Arrays.asList(array);
	}
}
