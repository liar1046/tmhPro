package cn.csy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import cn.csy.utils.CheckUtil;
import cn.csy.utils.MessageUtil;
/**
 * 绑定公众号，以及消息响应
 *
 */
public class WeixinServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * 公众号的绑定，前台是get方式提交的
	 * 获取四个重要参数，进行公众号绑定
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		
		//提交配置的时候生成
//		System.out.println("signature:"+signature+",timestamp:"+timestamp+",nonce:"+nonce);

		if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}

	}

	/**
	 * 消息响应，根据用户发送的消息，自定义回复信息
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {
			Map<String, String> map = MessageUtil.xmlToMap(request);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");

			String message = null;
			if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
				if ("1".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
				} else if ("2".equals(content)) {
					message = MessageUtil.initImageMessage(toUserName, fromUserName);
				} else if ("3".equals(content)) {
					message = MessageUtil.initQRCodeMessage(toUserName, fromUserName);
				} else if ("4".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.fourMenu());
				} else if ("7".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.myMenu());
				} else if ("19960501".equals(content)) {
					message = MessageUtil.initNiuerMessage(toUserName, fromUserName);
				} else if ("?".equals(content) || "？".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.helpMenu());
				} else{
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.noMenu());
				}
			} else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
				String eventType = map.get("Event");
				if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
					message = MessageUtil.initNewsMessage(toUserName, fromUserName);
				} else if (MessageUtil.MESSAGE_CLICK.equals(eventType)) {
					String key = map.get("EventKey");
					if ("20".equals(key)) {
						message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
					} else if ("34".equals(key)) {
						message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.helpMenu());
					}
				} else if (MessageUtil.MESSAGE_VIEW.equals(eventType)) {
					String url = map.get("EventKey");
					message = MessageUtil.initText(toUserName, fromUserName, url);
				}
			} else {
				message = MessageUtil.noMenu();
			}

			System.out.println(message);
			out.print(message);
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

}
