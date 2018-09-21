package cn.csy.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import cn.csy.menu.Button;
import cn.csy.menu.ClickButton;
import cn.csy.menu.Menu;
import cn.csy.menu.ViewButton;
import cn.csy.pojos.AccessToken;
import cn.csy.test.WeixinTest;
import net.sf.json.JSONObject;

public class WeixinUtil {
	//WeixinTestUrl��Ҫ�޸�
	private static String WeixinTestUrl = WeixinTest.serverURL + "/WeixinTest";

	// �����˺�
//	 private static final String APPID = "wx6f86461409046f0b";
//	 private static final String APPSECRET = "da56ec9a8a5a6ba004697db149e66f8e";

	// �����˺�
	private static final String APPID = "wxf0c41685bb77ecf4";
	private static final String APPSECRET = "b443b776d28b39cc9b242e5090455260";

	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	private static final String QUERY_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

	private static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	/**
	 * get����
	 * 
	 * @param url
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doGetStr(String url) throws ParseException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		HttpResponse httpResponse = client.execute(httpGet);
		HttpEntity entity = httpResponse.getEntity();
		if (entity != null) {
			String result = EntityUtils.toString(entity, "UTF-8");
			jsonObject = JSONObject.fromObject(result);
		}
		return jsonObject;
	}

	/**
	 * POST����
	 * 
	 * @param url
	 * @param outStr
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doPostStr(String url, String outStr) throws ParseException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		httpost.setEntity(new StringEntity(outStr, "UTF-8"));
		HttpResponse response = client.execute(httpost);
		String result = EntityUtils.toString(response.getEntity(), "UTF-8");
		jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}

	/**
	 * �ļ��ϴ�
	 * 
	 * @param filePath
	 * @param accessToken
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String upload(String filePath, String accessToken, String type)
			throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("�ļ�������");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);

		URL urlObj = new URL(url);
		// ����
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);

		// ��������ͷ��Ϣ
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");

		// ���ñ߽�
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		// ��������
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// �����ͷ
		out.write(head);

		// �ļ����Ĳ���
		// ���ļ������ļ��ķ�ʽ ���뵽url��
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();

		// ��β����
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// ����������ݷָ���

		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			// ����BufferedReader����������ȡURL����Ӧ
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println(jsonObj);
		String typeName = "media_id";
		if (!"image".equals(type)) {
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
	
	
	/**
	 * ��ȡaccessToken
	 * 
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static AccessToken getAccessToken() throws ParseException, IOException {
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if (jsonObject != null) {
			token.setToken(jsonObject.getString("access_token"));
			token.setExpiresIn(jsonObject.getInt("expires_in"));
		}
		return token;
	}
	/*
	
	// ����JsapiTicket
	private static String getJsapiTicket() throws ParseException, IOException{
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?";
		String params = "grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET + "";
		JSONObject result = doGetStr(requestUrl+params);
		String access_token = result.getString("access_token");
		requestUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?";
		params = "access_token=" + access_token + "&type=jsapi";
		result = doGetStr(requestUrl+params);
		String jsapi_ticket = result.getString("ticket");
		Integer.parseInt(result.getString("expires_in"));
		return jsapi_ticket;
	}
	*/
	
	/*
	public static void main(String[] args) throws ParseException, IOException {
		System.out.println(WeixinUtil.getJsapiTicket());
	}
	*/
	
	/**
	 * ��װ�˵�
	 * @return
	 */
	public static Menu initMenu(){
		Menu menu = new Menu();
		
		ViewButton button11 = new ViewButton();
		button11.setName("�µ�");
		button11.setType("view");
		button11.setUrl(WeixinTestUrl+"/jsp/order.jsp");
		
		ViewButton button12 = new ViewButton();
		button12.setName("��¼");
		button12.setType("view");
		button12.setUrl(WeixinTestUrl+"/jsp/login.jsp");
		
		ViewButton button13 = new ViewButton();
		button13.setName("����ǩ��");
		button13.setType("view");
		button13.setUrl(WeixinTestUrl+"/jsp/ordersToConfirm.jsp");
		
		Button button10 = new Button();
		button10.setName("���˷���");
		button10.setSub_button(new Button[]{button12,button11,button13});
		
		ClickButton button20 = new ClickButton();
		button20.setName("����");
		button20.setType("click");
		button20.setKey("20");
		
		ViewButton button31 = new ViewButton();
		button31.setName("�ҵ��˺�");
		button31.setType("view");
		button31.setUrl(WeixinTestUrl+"/jsp/personInfo.jsp");
		
		ViewButton button32 = new ViewButton();
		button32.setName("��ϵ�ͷ�");
		button32.setType("view");
		button32.setUrl(WeixinTestUrl+"/jsp/contactUS.jsp");
		
		ViewButton button33 = new ViewButton();
		button33.setName("���˹���");
		button33.setType("view");
		button33.setUrl(WeixinTestUrl+"/jsp/erweima.jsp");
		
		// click�˵���Ӧ����¼�
		ClickButton button34 = new ClickButton();
		button34.setName("����");
		button34.setType("click");
		button34.setKey("34");
		
		Button button30 = new Button();
		button30.setName("�ҵ�");
		button30.setSub_button(new Button[]{button31,button32,button33,button34});
		
		// ��װ�˵�
		menu.setButton(new Button[]{button10,button20,button30});
		
		return menu;
	}
	
	/**
	 * �����˵�
	 * @param token
	 * @param menu
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static int createMenu(String token,String menu) throws ParseException, IOException{
		int result = 0;
		String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, menu);
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
	
	/**
	 * ��ѯ�˵�
	 * @param token
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject queryMenu(String token) throws ParseException, IOException{
		String url = QUERY_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		return jsonObject;
	}
	
	/**
	 * ɾ���˵�
	 * @param token
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static int deleteMenu(String token) throws ParseException, IOException{
		String url = DELETE_MENU_URL.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doGetStr(url);
		int result = 0;
		if(jsonObject != null){
			result = jsonObject.getInt("errcode");
		}
		return result;
	}
}
