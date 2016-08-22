package cn.mayongfa.common;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 字符串处理类
 * 
 * @Version 1.0
 * @Remark
 */
public final class StrUtil {
	/** 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块 */
	private static final String US_ASCII = "US-ASCII";

	/** ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1 */
	private static final String ISO_8859_1 = "ISO-8859-1";

	/** 8 位 UCS 转换格式 */
	private static final String UTF_8 = "UTF-8";

	/** 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序 */
	private static final String UTF_16BE = "UTF-16BE";

	/** 16 位 UCS 转换格式，Little-endian（最高地址存放低位字节）字节顺序 */
	private static final String UTF_16LE = "UTF-16LE";

	/** 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识 */
	private static final String UTF_16 = "UTF-16";

	/** 中文超大字符集 */
	private static final String GBK = "GBK";

	/** 默认全部值 1111 */
	public static final long DEFAULT_ALL = (long) 1111;// 如下拉选择的全部 选项
	public static final int DEFAULT_ALL_Int = 1111;// 如下拉选择的全部

	/** 默认空值 0001 */
	public static final long DEFAULT_NULL = (long) 0001;// 在值转换时候，如 字符转为
														// long类型时候，先判断字符是否为空，如果为空返回这个
														// ，或者转换异常时候。

	/** 默认值 9999 */
	public static final long DEFAULT_VALUE = (long) 9999;// 如数据库非null字段情况下，给一个默认值

	/** 默认值 0001-01-01 */
	public static final String DEFAULT_DATE = "0001-01-01";// 日期为空的时候默认值，给一个默认值

	/**
	 * 将字符编码转换成GBK码
	 * 
	 * @param sStr
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public final static String toGBK(String sStr) throws UnsupportedEncodingException {
		return changeCharset(sStr, GBK);
	}

	/**
	 * 将字符编码转换成UTF-8码
	 * 
	 * @param sStr
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public final static String toUTF8(String sStr) throws UnsupportedEncodingException {
		return changeCharset(sStr, UTF_8);
	}

	/**
	 * 将字符编码转换成US-ASCII码
	 * 
	 * @param sStr
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public final static String toASCII(String sStr) throws UnsupportedEncodingException {
		return changeCharset(sStr, US_ASCII);
	}

	/**
	 * 将字符编码转换成UTF-16码
	 * 
	 * @param sStr
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public final static String toUTF16(String sStr) throws UnsupportedEncodingException {
		return changeCharset(sStr, UTF_16);
	}

	/**
	 * 字符串编码转换的实现方法
	 * 
	 * @param sStr
	 *            待转换编码的字符串
	 * @param sNewCharset
	 *            目标编码
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public final static String changeCharset(String sStr, String sNewCharset) throws UnsupportedEncodingException {
		// 用默认字符编码解码字符串。
		byte[] aBits = sStr.getBytes();
		// 用新的字符编码生成字符串
		return new String(aBits, sNewCharset);
	}

	/**
	 * 将字符编码转换成ISO-8859-1码
	 * 
	 * @param sStr
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public final static String toISO_8859_1(String sStr) throws UnsupportedEncodingException {
		return changeCharset(sStr, ISO_8859_1);
	}

	/**
	 * 将字符编码转换成UTF-16BE码
	 * 
	 * @param sStr
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public final static String toUTF16BE(String sStr) throws UnsupportedEncodingException {
		return changeCharset(sStr, UTF_16BE);
	}

	/**
	 * 将字符编码转换成UTF-16LE码
	 * 
	 * @param sStr
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public final static String toUTF16LE(String sStr) throws UnsupportedEncodingException {
		return changeCharset(sStr, UTF_16LE);
	}

	/**
	 * 字符串编码转换的实现方法
	 * 
	 * @param sStr
	 *            待转换编码的字符串
	 * @param sOldCharset
	 *            原编码
	 * @param sNewCharset
	 *            目标编码
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public final static String changeCharset(String sStr, String sOldCharset, String sNewCharset)
			throws UnsupportedEncodingException {
		// 用旧的字符编码解码字符串。解码可能会出现异常。
		byte[] aBits = sStr.getBytes(sOldCharset);
		// 用新的字符编码生成字符串
		return new String(aBits, sNewCharset);
	}

	/**
	 * 返回正则表达式的结果集
	 * 
	 * @param sStr
	 * @param sPattern
	 * @return List<String>
	 */
	public final static List<String> getRegexResult(String sStr, String sPattern) {
		ArrayList<String> aList = new ArrayList<String>();
		Pattern oPattern = Pattern.compile(sPattern);
		Matcher oMatcher = oPattern.matcher(sStr);
		while (oMatcher.find()) {
			for (int i = 1, nTotal = oMatcher.groupCount(); i <= nTotal; i++) {
				aList.add(oMatcher.group(i));
			}
		}

		return aList;
	}

	/**
	 * 字符串正则表达式替换
	 * 
	 * @param sSource
	 * @param sReplace
	 * @param sPattern
	 * @return String
	 */
	public final static String getRegexReplaceResult(String sSource, String sReplace, String sPattern) {
		Pattern oPattern = Pattern.compile(sPattern);
		Matcher oMatcher = oPattern.matcher(sSource);

		return oMatcher.replaceAll(sReplace);
	}

	/**
	 * 正则表达式检查结果
	 * 
	 * @param sStr
	 * @param sPattern
	 * @return boolean
	 */
	public final static boolean checkMather(String sStr, String sPattern) {
		Pattern oPattern = Pattern.compile(sPattern);
		Matcher oMather = oPattern.matcher(sStr);
		return oMather.matches();
	}

	/**
	 * 如果字符串为空则用默认值
	 * 
	 * @param sStr
	 * @param sDefault
	 * @return String
	 */
	public final static String toStr(Object sStr, String sDefault) {
		return sStr == null ? sDefault : sStr.toString().trim();
	}

	/**
	 * 如果字符串为空则用默认值
	 * 
	 * @param sStr
	 * @param sDefault
	 * @return String
	 */
	public final static String toStrNOTrim(Object sStr, String sDefault) {
		return sStr == null ? sDefault : sStr.toString();
	}

	/**
	 * 把字符串转换成整数
	 * 
	 * @param sValue
	 * @param nDefault
	 * @return int
	 */
	public final static int toInt(String sValue, int nDefault) {
		if (sValue == null || sValue.isEmpty())
			return nDefault;
		try {
			return Integer.valueOf(sValue);
		} catch (Exception ex) {
			return nDefault;
		}

	}

	/**
	 * 把字符串转换成Long数
	 * 
	 * @param sValue
	 * @param nDefault
	 * @return int
	 */
	public final static Long toLong(String sValue, Long nDefault) {
		if (sValue == null || sValue.isEmpty())
			return nDefault;
		try {
			return Long.valueOf(sValue);
		} catch (Exception ex) {
			return nDefault;
		}

	}

	/**
	 * 把字符串转换成小数
	 * 
	 * @param sValue
	 * @param nDefault
	 * @return Float
	 */
	public final static Float toFloat(String sValue, Float nDefault) {
		if (sValue == null || sValue.isEmpty())
			return nDefault;
		return Float.valueOf(sValue);
	}

	/**
	 * 把字符串转换成双精度小数
	 * 
	 * @param sValue
	 * @param nDefault
	 * @return Double
	 */
	public final static Double toDouble(String sValue, Double nDefault) {
		if (sValue == null || sValue.isEmpty())
			return nDefault;
		return Double.valueOf(sValue);
	}

	/**
	 * 把字符串转换成浮点
	 * 
	 * @param sValue
	 * @param nDefault
	 * @return float
	 */
	public final static float toFloat(String sValue, float nDefault) {
		if (sValue == null || sValue.isEmpty())
			return nDefault;
		return Float.valueOf(sValue);
	}

	/**
	 * 把字符串转换成 布尔型
	 * 
	 * @param sValue
	 * @param nDefault
	 * @return boolean
	 */
	public final static boolean toBoolean(String sValue, boolean nDefault) {
		if (sValue == null || sValue.isEmpty())
			return nDefault;
		return Boolean.valueOf(sValue);
	}

	/**
	 * 数据显示小数点问题
	 * 
	 * @param d
	 * @return
	 */
	public final static String doubleToStr(Double dd) {
		if (null == dd) {
			return "";
		}
		double d = dd;
		double c = d - (int) d;
		if (c == 0)
			return (int) d + "";
		else {
			BigDecimal b = new BigDecimal(d);
			BigDecimal one = new BigDecimal("1");
			c = b.divide(one, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return String.valueOf(c);
		}
	}

	public final static String floatToStr(float d) {
		if (Math.round(d) - d == 0) {
			return String.valueOf((long) d);
		}
		return String.valueOf(d);
	}

	/**
	 * 字符串 ： 解决数据库入库时候的非法符号 转 HTML 符号
	 * 
	 * @param sStr
	 * @param sDefault
	 * @return
	 */
	public final static String toHtml(Object sStr, String sDefault) {
		String sValue = sStr == null ? sDefault : sStr.toString();
		sValue = sValue.replaceAll("<", "&lt;");
		sValue = sValue.replaceAll(">", "&gt;");
		sValue = sValue.replaceAll("&", "&amp;");
		return sValue;
	}

	/**
	 * 获取八位不重复随机码（取当前时间戳转化为16进制）
	 * 
	 * @param time
	 * @return
	 */
	public final static String toHex(long time) {
		return Integer.toHexString((int) time);
	}

	public static String getVerificationCode(int count, Boolean isLetter) {
		String[] beforeLetterShuffle = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
				"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		String[] beforeNumberShuffle = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "8" };
		List<String> list = Arrays.asList(isLetter ? beforeLetterShuffle : beforeNumberShuffle);
		Collections.shuffle(list);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));
		}
		String afterShuffle = sb.toString();
		String result = afterShuffle.substring(0, count);
		return result;
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 * 
	 * @param value
	 * @return 为空则返回true，否则返回false
	 */
	public final static boolean isNullOrEmpty(String value) {
		return value == null || "".equals(value.trim()) || "null".equals(value.trim());
	}

	/**
	 * 判断在values数组中是否存在value元素
	 * 
	 * @param values
	 *            数组
	 * @param value
	 *            字符串
	 * @return 存在，返回true。反之，false。
	 */
	public static boolean contains(String[] values, String value) {
		if (values != null && values.length > 0) {
			for (String s : values) {
				if (s != null) {
					if (s.equals(value)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * json字符串转换为List<Map<String, Object>>
	 * 
	 * @param jsonString
	 * @return
	 */
	public final static List<Map<String, Object>> toMapList(String jsonString) {
		List<Map<String, Object>> mapList = new ArrayList<>();
		if (!StrUtil.isNullOrEmpty(jsonString)) {
			mapList = new Gson().fromJson(jsonString, new TypeToken<ArrayList<Map<String, Object>>>() {
			}.getType());
		}
		return mapList;
	}

	/**
	 * json字符串转换为Map<String, Object>
	 * 
	 * @param jsonString
	 * @return
	 */
	public final static Map<String, Object> toMap(String jsonString) {
		Map<String, Object> map = new HashMap<>();
		if (!StrUtil.isNullOrEmpty(jsonString)) {
			map = new Gson().fromJson(jsonString, new TypeToken<Map<String, Object>>() {
			}.getType());
		}
		return map;
	}
}
