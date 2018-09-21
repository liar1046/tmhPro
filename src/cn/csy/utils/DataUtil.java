package cn.csy.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import net.sf.json.JSONObject;

import cn.csy.servlet.OrderServlet;

/**
 * ����url��ȡ���ݣ�����url��jsonObejct�ύ����
 *
 */
public class DataUtil {
	/*
	 * public static void main(String[] args) throws Exception { String url =
	 * "http://"; getData(url); }
	 */

	/**
	 * �ӷ�������ȡ��Ϣ
	 * @param urlString			�������ӿ�
	 * @return
	 * @throws Exception
	 */
	public static String getData(String urlString) throws Exception {
		String res = "";
		BufferedReader reader = null;
		try {
			// ����url���ӣ���ȡ����
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			InputStream stream = OrderServlet.class.getClassLoader().getResourceAsStream("head.properties");
			Properties prop = new Properties();
			prop.load(stream);
			connection.setRequestProperty("X-Tmh-Application-Id", prop.getProperty("X-Tmh-Application-Id"));
			connection.setRequestProperty("X-Tmh-REST-API-Key", prop.getProperty("X-Tmh-REST-API-Key"));
			connection.setRequestProperty("X-Tmh-Session-Token", prop.getProperty("X-Tmh-Session-Token"));

			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			// System.out.println("��ȡ����������!");
			String line;
			while ((line = reader.readLine()) != null) {
				res += line;
				// System.out.println(res);
			}
			reader.close();
		} catch (Exception e) {
			e.getMessage();
		}

		return res;
	}

	/**
	 * ���������ύ��Ϣ
	 * @param urlString			�������ӿ�
	 * @param jsonObject		�ύ����
	 * @throws IOException
	 */
	public static void setData(String urlString, JSONObject jsonObject) throws IOException {
		// ����HttpURLConnection����
		HttpURLConnection httpurlconnection = null;
		InputStream urlstream = null;
		BufferedReader reader = null;
		// System.out.println("orderServelt");
		try {
			// ����HTTP���ӵ�URL��ַ�����ǵڶ���Ӧ�õ�URL�������ͬһ��������Ͽ���д��127.0.0.1����localhost
			URL url = new URL(urlString);
			httpurlconnection = (HttpURLConnection) url.openConnection();
			// �������������Ĭ��Ϊfalse�����ǲ��ܴ��ݲ�����
			httpurlconnection.setDoOutput(true);
			// ���÷�������ķ�ʽ��ע�⣺һ��Ҫ��д
			httpurlconnection.setRequestMethod("POST");
			// �������ӳ�ʱ��ʱ�䡣���������ã��������쳣������£����ܻ���ɳ��������޷���������ִ�У�����һ������һ����ʱʱ�䡣��λΪ����
			httpurlconnection.setConnectTimeout(30000);
			// ��������ͷ�������ļ��ж�ȡ
			InputStream stream = OrderServlet.class.getClassLoader().getResourceAsStream("head.properties");
			Properties prop = new Properties();
			prop.load(stream);
			httpurlconnection.setRequestProperty("X-Tmh-Application-Id", prop.getProperty("X-Tmh-Application-Id"));
			httpurlconnection.setRequestProperty("X-Tmh-REST-API-Key", prop.getProperty("X-Tmh-REST-API-Key"));
			httpurlconnection.setRequestProperty("X-Tmh-Session-Token", prop.getProperty("X-Tmh-Session-Token"));
			// httpurlconnection.setRequestProperty("X-Tmh-GrpCode","903510001");
			// ���ò���������json��ʽ
			httpurlconnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

			httpurlconnection.connect();

			// �����������
			OutputStreamWriter writer = new OutputStreamWriter(httpurlconnection.getOutputStream(), "utf-8");

			// ���ݵĲ������м�ʹ��&���ŷָ
			writer.write(jsonObject.toString());
			// ����ˢ�»���������ΪĬ������д�뵽�ڴ�Ļ������У���һ����������ʱ���Ż�д�룬ʹ��������������������д�룬��Ȼ����͵��ر�����
			writer.flush();
			// ���ڹر���������ر�֮��Ͳ�������������ˣ�����Ҫʹ��flushˢ�»�����
			writer.close();

			// ��÷��ص�������
			int responseCode = httpurlconnection.getResponseCode();
			// ��ʾ����ɹ�
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println("OK" + responseCode);
				// ��÷���˵���������õ����ص�����

				urlstream = httpurlconnection.getInputStream();
				reader = new BufferedReader(new InputStreamReader(urlstream));
				String line;
				String tline = "";
				while ((line = reader.readLine()) != null) {
					tline += line;
				}
				// ������е�����
				System.out.println(tline);
			} else {
				System.out.println("ERR" + responseCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			urlstream.close();
			httpurlconnection.disconnect();
		}
	}

}
