package com.redescooter.ses.tool.utils.http;

import com.redescooter.ses.tool.crypt.Base64;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestEntity {

	private static final String DEFAULT_CHARSET = "utf-8";
	private String charset = DEFAULT_CHARSET;
	private int connectTimeout = 5000;
	private int readTimeout = 10000;
	private Map<String, String> headers = new HashMap<>();
	private Map<String, String> textParams = new HashMap<>();
	private Map<String, FileItem> fileParams = new HashMap<>();
	private BasicAuthParams basicAuth;
	
	
	private HttpRequestEntity() {}

	public static HttpRequestEntity create(){
		return new HttpRequestEntity();
	}
	
	public String getCharset() {
		return charset;
	}

	public HttpRequestEntity charset(String charset) {
		this.charset = charset;
		return this;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public HttpRequestEntity connectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
		return this;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public HttpRequestEntity readTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
		return this;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public HttpRequestEntity headers(Map<String, String> headers) {
		this.headers = headers;
		return this;
	}

	public Map<String, String> getTextParams() {
		return textParams;
	}

	public HttpRequestEntity textParams(Map<String, String> textParams) {
		this.textParams = textParams;
		return this;
	}

	public Map<String, FileItem> getFileParams() {
		return fileParams;
	}

	public HttpRequestEntity fileParams(Map<String, FileItem> fileParams) {
		this.fileParams = fileParams;
		return this;
	}

	public BasicAuthParams getBasicAuth() {
		return basicAuth;
	}

	public HttpRequestEntity basicAuth(String userName,String password) {
		this.basicAuth = new BasicAuthParams(userName, password);
		return this;
	}
	
	public HttpRequestEntity addHeader(String name,String value) {
		this.headers.put(name, value);
		return this;
	}
	
	public HttpRequestEntity addTextParam(String name,String value) {
		this.textParams.put(name, value);
		return this;
	}
	
	public HttpRequestEntity addFileParam(String name,FileItem value) {
		this.fileParams.put(name, value);
		return this;
	}

	public static class BasicAuthParams{
		private String name;
		private String password;
		
		public BasicAuthParams(String name, String password) {
			super();
			this.name = name;
			this.password = password;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
		public String getEncodeBasicAuth(){
			String encoded = Base64.encodeToString((name + ":" + password).getBytes(StandardCharsets.UTF_8),false);
			return "Basic "+encoded;
		}
		
	}
	
	/**
	 * ??????????????????
	 */
	public static class FileItem {

		private String fileName;
		private String mimeType;
		private byte[] content;
		private File file;

		/**
		 * ?????????????????????????????????
		 * 
		 * @param file ????????????
		 */
		public FileItem(File file) {
			this.file = file;
		}

		/**
		 * ???????????????????????????????????????
		 * 
		 * @param filePath ??????????????????
		 */
		public FileItem(String filePath) {
			this(new File(filePath));
		}

		/**
		 * ??????????????????????????????????????????
		 * 
		 * @param fileName ?????????
		 * @param content ???????????????
		 */
		public FileItem(String fileName, byte[] content) {
			this.fileName = fileName;
			this.content = content;
		}

		/**
		 * ?????????????????????????????????????????????????????????
		 * 
		 * @param fileName ?????????
		 * @param content ???????????????
		 * @param mimeType ????????????
		 */
		public FileItem(String fileName, byte[] content, String mimeType) {
			this(fileName, content);
			this.mimeType = mimeType;
		}

		public String getFileName() {
			if (this.fileName == null && this.file != null && this.file.exists()) {
				this.fileName = file.getName();
			}
			return this.fileName;
		}

		public String getMimeType() throws IOException {
			if (this.mimeType == null) {
				this.mimeType = getMimeType(getContent());
			}
			return this.mimeType;
		}

		public byte[] getContent() throws IOException {
			if (this.content == null && this.file != null && this.file.exists()) {
				InputStream in = null;
				ByteArrayOutputStream out = null;

				try {
					in = new FileInputStream(this.file);
					out = new ByteArrayOutputStream();
					int ch;
					while ((ch = in.read()) != -1) {
						out.write(ch);
					}
					this.content = out.toByteArray();
				} finally {
					if (out != null) {
						out.close();
					}
					if (in != null) {
						in.close();
					}
				}
			}
			return this.content;
		}
		
		/**
		 * ???????????????????????????????????????????????????JPG, GIF, PNG, BMP?????????????????????
		 * 
		 * @param bytes ???????????????
		 * @return ????????????(MEME-TYPE)
		 */
		public static String getMimeType(byte[] bytes) {
			String suffix = getFileSuffix(bytes);
			String mimeType;

			if ("JPG".equals(suffix)) {
				mimeType = "image/jpeg";
			} else if ("GIF".equals(suffix)) {
				mimeType = "image/gif";
			} else if ("PNG".equals(suffix)) {
				mimeType = "image/png";
			} else if ("BMP".equals(suffix)) {
				mimeType = "image/bmp";
			}else {
				mimeType = "application/octet-stream";
			}

			return mimeType;
		}
		
		/**
		 * ????????????????????????????????????????????????JPG, GIF, PNG, BMP?????????????????????
		 * 
		 * @param bytes ???????????????
		 * @return JPG, GIF, PNG or null
		 */
		public static String getFileSuffix(byte[] bytes) {
			if (bytes == null || bytes.length < 10) {
				return null;
			}

			if (bytes[0] == 'G' && bytes[1] == 'I' && bytes[2] == 'F') {
				return "GIF";
			} else if (bytes[1] == 'P' && bytes[2] == 'N' && bytes[3] == 'G') {
				return "PNG";
			} else if (bytes[6] == 'J' && bytes[7] == 'F' && bytes[8] == 'I' && bytes[9] == 'F') {
				return "JPG";
			} else if (bytes[0] == 'B' && bytes[1] == 'M') {
				return "BMP";
			} else {
				return null;
			}
		}

	}
}
