package cn.ohyeah.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtils {

	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getCurrentDate(){
		Calendar ca = Calendar.getInstance();
		ca.setTime(new java.util.Date());
		SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
		String endTime = simpledate.format(ca.getTime());
		//System.out.println(eTime);
		return endTime;
	}

	/**
	 * 2011-01-12 00:00:00
	 * @return
	 */
	public static int getTime(){
		Calendar ca = Calendar.getInstance();
		ca.setTime(new java.util.Date());
		@SuppressWarnings("unused")
		SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd");
		int month = ca.get(Calendar.MONTH)+1;
		int day = ca.get(Calendar.DAY_OF_MONTH);
		int hour = ca.get(Calendar.HOUR_OF_DAY);
		int minute = ca.get(Calendar.MINUTE);
		int second = ca.get(Calendar.SECOND);
		String time = String.valueOf(month) + convertString(day) 
					+ convertString(hour) + convertString(minute) + convertString(second);
		System.out.println("time: "+(time));
		return Integer.parseInt(time);
	}
	
	private static String convertString(int i){
		String str = "";
		if(i<10){
			str = "0"+i;
		}else{
			str = String.valueOf(i);
		}
		return str;
	}
	
   public static void main(String[] args)throws Exception{
	   System.out.println(getTime());
   }
}
