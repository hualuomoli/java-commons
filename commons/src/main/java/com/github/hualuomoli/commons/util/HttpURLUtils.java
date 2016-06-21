package com.github.hualuomoli.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.constant.Charset;

/**
 * HTTP工具
 * @author hualuomoli
 *
 */
public class HttpURLUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpURLUtils.class);

	// charset
	public static final String CHARSET_UTF8 = Charset.UTF8.name();

	// method
	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";

	// mime
	public static final String MIME_TEXT = "text/plain";
	public static final String MIME_JSON = "application/json";
	public static final String MIME_XML = "application/xml";
	public static final String MIME_URLENCODED = "application/x-www-form-urlencoded";

	// do get
	public static String doGet(String urlStr) {
		return doGet(urlStr, StringUtils.EMPTY);
	}

	// do get
	public static String doGet(String urlStr, Map<String, String> dataMap) {
		String data = "";
		for (String key : dataMap.keySet()) {
			data += "&" + key + "=" + dataMap.get(key);
		}
		return doGet(urlStr, data);
	}

	// do get
	public static String doGet(String urlStr, String data) {
		try {
			return invoke(METHOD_GET, urlStr, data, null);
		} catch (IOException e) {
			logger.error("invoke http get error.", e);
			return null;
		}
	}

	// do post
	public static String doPost(String urlStr) {
		return doPost(urlStr, StringUtils.EMPTY);
	}

	// do post
	public static String doPost(String urlStr, final Map<String, ?> dataMap) {
		StringBuilder buffer = new StringBuilder();
		if (dataMap != null) {
			for (String key : dataMap.keySet()) {
				buffer.append("&").append(key).append("=").append(dataMap.get(key).toString());
			}
		}
		return doPost(urlStr, buffer.toString());
	}

	// do post
	public static String doPost(String urlStr, String data) {
		try {
			return invoke(METHOD_POST, urlStr, data, MIME_URLENCODED);
		} catch (IOException e) {
			logger.error("invoke http post error.", e);
			return null;
		}
	}

	// do payload as post method
	public static String doPayload(String urlStr) {
		return doPayload(urlStr, StringUtils.EMPTY);
	}

	// do payload as post method
	public static String doPayload(String urlStr, final Map<String, ?> dataMap) {
		StringBuilder buffer = new StringBuilder();
		if (dataMap != null) {
			for (String key : dataMap.keySet()) {
				buffer.append("&").append(key).append("=").append(dataMap.get(key).toString());
			}
		}
		return doPayload(urlStr, buffer.toString());
	}

	// do payload as post method
	public static String doPayload(String urlStr, String data) {
		try {
			return invoke(METHOD_POST, urlStr, data, MIME_JSON);
		} catch (IOException e) {
			logger.error("invoke http payload error.", e);
			return null;
		}
	}

	// invoke
	public static String invoke(String methodName, String urlStr, String data, String acceptType) throws IOException {

		if (logger.isDebugEnabled()) {
			logger.debug("methodName {}", methodName);
			logger.debug("urlStr {}", urlStr);
			logger.debug("data {}", data);
		}
		if (StringUtils.isBlank(methodName) || StringUtils.isBlank(urlStr)) {
			throw new RuntimeException("please set method name and url.");
		}

		HttpURLConnection conn = null;
		OutputStream os = null;
		InputStream is = null;

		try {

			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			os = conn.getOutputStream();
			is = conn.getInputStream();

			// method
			conn.setRequestMethod(methodName);

			conn.setDoInput(true); // input
			conn.setDoOutput(true); // output

			// header
			if (acceptType != null) {
				conn.setRequestProperty("Accept", acceptType);
				conn.setRequestProperty("Content-Type", acceptType + "; charset=" + CHARSET_UTF8);
			}

			// output data
			if (StringUtils.isNotBlank(data)) {
				os.write(data.getBytes());
			}

			// get response
			String outStr = IOUtils.toString(is, CHARSET_UTF8);

			if (logger.isDebugEnabled()) {
				logger.debug("response {}", outStr);
			}
			return outStr;
		} catch (IOException e) {
			throw e;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}

	}

}
