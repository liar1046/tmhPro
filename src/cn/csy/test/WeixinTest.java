package cn.csy.test;

import cn.csy.pojos.AccessToken;
import cn.csy.utils.WeixinUtil;
import net.sf.json.JSONObject;

/**
 * 修改项目部署的服务器地址，获取token，创建菜单测试，建立mediaID 
 *
 */
public class WeixinTest {

	// 修改项目部署的服务器地址
//	public static final String serverURL = "http://ty0351.ewcms.cn";
	public static final String serverURL = "http://613c8fa2.ngrok.io";

	public static void main(String[] args) {
		// 获取token
		AccessToken token;
		
		try {
			token = WeixinUtil.getAccessToken();
			System.out.println("票据：" + token.getToken());
			System.out.println("有效时间：" + token.getExpiresIn());
			
		
			// 创建菜单测试

			String createMenu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
			int result = WeixinUtil.createMenu(token.getToken(), createMenu);
			if (result == 0) {
				System.out.println("创建菜单成功！");
			} else {
				System.out.println("错误码:" + result);
			}

			// 删除菜单
			/*
			 * int result2 = WeixinUtil.deleteMenu(token.getToken());
			 * if(result2==0){ System.out.println("删除菜单成功！"); }else{
			 * System.out.println("错误码:"+result2); }
			 */

			// 建立mediaID
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
