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
 * 根据url获取数据，根据url和jsonObejct提交数据
 *
 */
public class DataUtil {
	/*
	 * public static void main(String[] args) throws Exception { String url =
	 * "http://"; getData(url); }
	 */

	/**
	 * 从服务器获取信息
	 * @param urlString			服务器接口
	 * @return
	 * @throws Exception
	 */
	public static String getData(String urlString) throws Exception {
		String res = "";
		BufferedReader reader = null;
		try {
			// 建立url连接，读取数据
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
			// System.out.println("获取服务器数据!");
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
	 * 给服务器提交信息
	 * @param urlString			服务器接口
	 * @param jsonObject		提交数据
	 * @throws IOException
	 */
	public static void setData(String urlString, JSONObject jsonObject) throws IOException {
		// 生成HttpURLConnection连接
		HttpURLConnection httpurlconnection = null;
		InputStream urlstream = null;
		BufferedReader reader = null;
		// System.out.println("orderServelt");
		try {
			// 设置HTTP连接的URL地址，就是第二个应用的URL。如果在同一个计算机上可以写成127.0.0.1或者localhost
			URL url = new URL(urlString);
			httpurlconnection = (HttpURLConnection) url.openConnection();
			// 设置有输出流，默认为false，就是不能传递参数。
			httpurlconnection.setDoOutput(true);
			// 设置发送请求的方式。注意：一定要大写
			httpurlconnection.setRequestMethod("POST");
			// 设置连接超时的时间。不过不设置，在网络异常的情况下，可能会造成程序僵死而无法继续向下执行，所以一般设置一个超时时间。单位为毫秒
			httpurlconnection.setConnectTimeout(30000);
			// 设置请求头，配置文件中读取
			InputStream stream = OrderServlet.class.getClassLoader().getResourceAsStream("head.properties");
			Properties prop = new Properties();
			prop.load(stream);
			httpurlconnection.setRequestProperty("X-Tmh-Application-Id", prop.getProperty("X-Tmh-Application-Id"));
			httpurlconnection.setRequestProperty("X-Tmh-REST-API-Key", prop.getProperty("X-Tmh-REST-API-Key"));
			httpurlconnection.setRequestProperty("X-Tmh-Session-Token", prop.getProperty("X-Tmh-Session-Token"));
			// httpurlconnection.setRequestProperty("X-Tmh-GrpCode","903510001");
			// 设置参数类型是json格式
			httpurlconnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");

			httpurlconnection.connect();

			// 设置输出流。
			OutputStreamWriter writer = new OutputStreamWriter(httpurlconnection.getOutputStream(), "utf-8");

			// 传递的参数，中间使用&符号分割。
			writer.write(jsonObject.toString());
			// 用于刷新缓冲流。因为默认她会写入到内存的缓冲流中，到一定的数据量时，才会写入，使用这个命令可以让他立即写入，不然下面就到关闭流了
			writer.flush();
			// 用于关闭输出流，关闭之后就不可以输出数据了，所以要使用flush刷新缓冲流
			writer.close();

			// 获得返回的请求吗。
			int responseCode = httpurlconnection.getResponseCode();
			// 表示请求成功
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.out.println("OK" + responseCode);
				// 获得服务端的输出流。得到返回的数据

				urlstream = httpurlconnection.getInputStream();
				reader = new BufferedReader(new InputStreamReader(urlstream));
				String line;
				String tline = "";
				while ((line = reader.readLine()) != null) {
					tline += line;
				}
				// 输出所有的数据
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
