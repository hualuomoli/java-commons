package com.github.hualuomoli.constant;

import java.text.SimpleDateFormat;

/**
 * 日期格式化
 * @author hualuomoli
 *
 */
public class DateFormat {

	public static final SimpleDateFormat SDF_SLASHYMD = new SimpleDateFormat("yyyy/MM/dd");
	public static final SimpleDateFormat SDF_SLASHYMD_HMS = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
	public static final SimpleDateFormat SDF_BARYMD = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat SDF_BARYMD_HMS = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	public static final SimpleDateFormat SDF_YMD = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat SDF_YMD_HMS = new SimpleDateFormat("yyyyMMddHHmmss");

}
