package org.slsale.common;

/**
 * mybatis 防止SQL注入的工具类
 * @author lzw
 *
 */
public class SQLTools {
	/**
	 * mybatis 模糊查询防止SQL注入（字符替换）
	 * @param keyword 要替换的条件
	 * @return 替换后的条件
	 */
	public static String transfer(String keyword) {
		if (keyword.contains("%") || keyword.contains("_")) {
			keyword = keyword.replaceAll("\\\\", "\\\\\\\\")
					.replaceAll("\\%", "\\\\%").replaceAll("\\_", "\\\\_");
		}
		return keyword;
	}
}
