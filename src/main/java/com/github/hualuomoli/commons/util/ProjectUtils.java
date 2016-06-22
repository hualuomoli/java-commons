package com.github.hualuomoli.commons.util;

/**
 * 项目工具类
 * @author hualuomoli
 *
 */
public class ProjectUtils {

	// 获取项目路径
	public static String getLocation() {
		String path = ProjectUtils.class.getClassLoader().getResource(".").getPath().replaceAll("\\\\", "/");
		if (path.indexOf("/target/") >= 0) {
			return path.substring(0, path.lastIndexOf("/target/"));
		}
		throw new RuntimeException("can not support project type");
	}

}
