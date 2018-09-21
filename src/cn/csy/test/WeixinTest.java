package cn.csy.test;

import cn.csy.pojos.AccessToken;
import cn.csy.utils.WeixinUtil;
import net.sf.json.JSONObject;

/**
 * �޸���Ŀ����ķ�������ַ����ȡtoken�������˵����ԣ�����mediaID 
 *
 */
public class WeixinTest {

	// �޸���Ŀ����ķ�������ַ
//	public static final String serverURL = "http://ty0351.ewcms.cn";
	public static final String serverURL = "http://613c8fa2.ngrok.io";

	public static void main(String[] args) {
		// ��ȡtoken
		AccessToken token;
		
		try {
			token = WeixinUtil.getAccessToken();
			System.out.println("Ʊ�ݣ�" + token.getToken());
			System.out.println("��Чʱ�䣺" + token.getExpiresIn());
			
		
			// �����˵�����

			String createMenu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
			int result = WeixinUtil.createMenu(token.getToken(), createMenu);
			if (result == 0) {
				System.out.println("�����˵��ɹ���");
			} else {
				System.out.println("������:" + result);
			}

			// ɾ���˵�
			/*
			 * int result2 = WeixinUtil.deleteMenu(token.getToken());
			 * if(result2==0){ System.out.println("ɾ���˵��ɹ���"); }else{
			 * System.out.println("������:"+result2); }
			 */

			// ����mediaID
			/*
			 * // String path = "H:/companyWork/fltmh180706/fltmh180706.png"; //
			 * String path = "H:/companyWork/tmh20180615/test.png"; String mediaId =
			 * WeixinUtil.upload(path, token.getToken(), "image");
			 * System.out.println(mediaId);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
