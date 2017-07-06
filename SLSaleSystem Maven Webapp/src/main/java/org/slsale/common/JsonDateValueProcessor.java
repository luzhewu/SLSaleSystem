package org.slsale.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * json日期格式的处理(java对象转化为json)
 * @author lzw
 * 2017-7-2
 * 每个人都有自己的梦想。努力拼搏吧!不要让自己后悔!
 */
public class JsonDateValueProcessor implements JsonValueProcessor {
	private String datePattern = "yyyy-MM-dd";

	public JsonDateValueProcessor() {
		super();
	}

	public JsonDateValueProcessor(String datePattern) {
		super();
		this.datePattern = datePattern;
	}

	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		return process(arg0);
	}

	@Override
	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
		return process(arg1);
	}

	/**
	 * 时间格式匹配以及转化
	 * @param value
	 * @return
	 */
	private Object process(Object value) {
		try {
			if (value instanceof Date) {
				SimpleDateFormat sdf = new SimpleDateFormat(datePattern,
						Locale.UK);
				return sdf.format((Date) value);
			}
			return value == null ? "" : value.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}
}
