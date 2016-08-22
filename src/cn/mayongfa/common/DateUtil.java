package cn.mayongfa.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateUtil {

	private static Logger log = Logger.getLogger(DateUtil.class);
	/**
	 * date类型转换为String类型
	 * @param data Date类型的时间
	 * @param formatType 格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	 * @return
	 */
	public static String dateToString(Date date, String formatType) {
		SimpleDateFormat sdf=new SimpleDateFormat(formatType);
		//java.util.Date date=new java.util.Date();
		return sdf.format(date);
	}
 
	/**
	 * long类型转换为String类型
	 * @param currentTime 要转换的long类型的时间
	 * @param formatType 要转换的string类型的时间格式
	 * @return
	 * @throws ParseException
	 */
	public static String longToString(long currentTime, String formatType){
		/*SimpleDateFormat sf = new SimpleDateFormat(formatType);
		Date date = new Date(currentTime);
		return sf.format(date);*/
		SimpleDateFormat oldFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat newFormat = new SimpleDateFormat(formatType);
		Date date = null;
		try {
			date = oldFormat.parse(currentTime+"");
		} catch (Exception e) {
			log.error("", e);
			date = new Date();
			e.printStackTrace();
		}
		return newFormat.format(date);
	}
	
	/**
	 * long类型转换为String类型
	 * @param currentTime 要转换的long类型的时间
	 * @param formatType 要转换的string类型的时间格式
	 * @return
	 * @throws ParseException
	 */
	public static String longToString(long currentTime, String oldFormatType, String formatType)
			throws ParseException {
		String strTime = StrUtil.toStr(currentTime, StrUtil.DEFAULT_DATE); // long类型转成String
		Date date = stringToDate(strTime, oldFormatType);
		return dateToString(date, formatType);
	}

	/**
	 * string类型转换为date类型
	 * @param strTime 的时间格式必须要与formatType的时间格式相同
	 * @param formatType  要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日HH时mm分ss秒
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String strTime, String formatType) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(formatType);
			Date date = null;
			date = formatter.parse(strTime);
			return date;
		} catch (Exception e) {
			log.error("", e);
			return new Date();
		}
		
	}

	/**
	 * long转换为Date类型
	 * @param currentTime 要转换的long类型的时间
	 * @param formatType 要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
	 * @return
	 * @throws ParseException
	 */
	public static Date longToDate(long currentTime, String formatType) {
		try {
			Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
			String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
			Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
			return date;
		} catch (Exception e) {
			log.error("", e);
			return new Date();
		}		
	}

	/**
	 * string类型转换为long类型
	 * @param strTime 要转换的String类型的时间
	 * @param formatType 时间格式的时间格式和formatType的时间格式必须相同
	 * @return
	 * @throws ParseException
	 */
	public static long stringToLong(String strTime, String formatType) {
		try {
			Date date = stringToDate(strTime, formatType); // String类型转成date类型
			if (date == null) {
				return 0;
			} else {
				long currentTime = dateToLong(date); // date类型转成long类型
				return currentTime;
			}
		} catch (Exception e) {
			log.error("", e);
			return 0;
		}
		
	}

	/**
	 * date类型转换为long类型
	 * @param date 要转换的date类型的时间
	 * @return
	 */
	public static long dateToLong(Date date) {
		return StrUtil.toLong(dateToString(date,"yyyyMMddHHmmss"), StrUtil.DEFAULT_ALL);
	}
	
	/**
	 * 获取龙long类型时间
	 * @return yyyyMMddHHmmss
	 */
	public static long dateToLong() {
		return StrUtil.toLong(dateToString(new Date(),"yyyyMMddHHmmss"), StrUtil.DEFAULT_ALL);
	}
	
	/**
	 * 时间戳转Long类型时间
	 * @param unix 时间戳
	 * @return 若转换失败，返回当前时间
	 */
	public static long unixToLong(long unix){
		SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss");
		try{
			return Long.parseLong(format.format(unix));
		}catch(Exception e){
			log.error("", e);
			return DateUtil.dateToLong();
		}
	}
	
	/**
	 * 分析Long类型的时间距离当前时间多久
	 * @param time Long类型的时间。yyyyMMddHHmmss
	 * @return 若转换异常，返回DateUtil.longToString(time, "yyyy-MM-dd")
	 */
	public static String getTimePosition(long time){
		 SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss");  
	      Date date;
		try {
			date = format.parse(time + "");
			long conversionTime = date.getTime();
			long currentTime = System.currentTimeMillis();
			long relativeTime = currentTime - conversionTime;
			if(relativeTime < 30*60*1000){
				return "刚刚";
			}else if(relativeTime < 60*60*1000){
				return "30分钟前";
			}else{
				return DateUtil.longToString(time, "yyyy-MM-dd");
			}
		} catch (ParseException e) {
			log.error("", e);
			return DateUtil.longToString(time, "yyyy-MM-dd");
		} 
	}
}
