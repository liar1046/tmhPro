package cn.csy.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

import cn.csy.pojos.Image;
import cn.csy.pojos.ImageMessage;
import cn.csy.pojos.News;
import cn.csy.pojos.NewsMessage;
import cn.csy.pojos.TextMessage;
import cn.csy.test.WeixinTest;

public class MessageUtil {

	// 这个得替换
	private static String url = WeixinTest.serverURL + "/WeixinTest";

	/*125.46.39.104
	 * 以下是微信公众号的消息类型
	 */
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_SCANCODE = "scancode";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe"; // 关注
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe"; // 取消关注
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";

	/**
	 * xml转为map集合
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();

		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);

		Element root = doc.getRootElement();

		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		/*
		 * 遍历一下map,看看里面有啥
		 */
		/*
		 * Set<String> keySet = map.keySet(); Iterator<String> it =
		 * keySet.iterator(); while(it.hasNext()){ String key = it.next();
		 * String value = map.get(key); System.out.println(key+":"+value); }
		 */
		return map;
	}

	/**
	 * 将文本对象消息转化为xml
	 * 
	 * @param testMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage testMessage) {
		XStream xStream = new XStream();
		xStream.alias("xml", testMessage.getClass());
		return xStream.toXML(testMessage);
	}
	
	/**
	 * 将图文消息转换为xml
	 * 
	 * @param newsMessage
	 * @return
	 */
	public static String newsMessageToXml(NewsMessage newsMessage) {
		XStream xStream = new XStream();
		xStream.alias("xml", newsMessage.getClass());
		xStream.alias("item", new News().getClass());
		return xStream.toXML(newsMessage);
	}
	
	/**
	 * 将图片信息转为xml
	 * 
	 * @param imageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	
	/**
	 * 文本信息组装
	 * @param fromUserName
	 * @param toUserName
	 * @param content
	 * @return
	 */
	public static String initText(String toUserName, String fromUserName, String content) {
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);		
		
		System.out.println(content);	
		
		return textMessageToXml(text);
	}

	/**
	 * 主菜单，关注成功后显示此菜单
	 * 
	 * @return
	 */
	public static String menuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎使用砼盟荟！\n");
		return sb.toString();
	}

	/**
	 * 用户输入 1 的时候返回此方法
	 * 
	 * @return
	 */
	public static String firstMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("方便用户对任务调度信息以及车辆信息的查看。");
		return sb.toString();
	}
	
	/**
	 * 用户输入 4 的时候返回此方法
	 * 
	 * @return
	 */
	public static String fourMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("经营范围：计算机软件开发、网页制作、计算机系统集成、"
				+ "综合布线服务；计算机硬件、配件、耗材销售；电子产品、"
				+ "通讯定位设备的销售（依法须经批准的项目，经相关部门批准后方可开展经营活动）");
		return sb.toString();
	}

	/**
	 * 用户输入非法请求时返回此方法
	 * 
	 * @return
	 */
	public static String noMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("请输入正确编号！！\n");
		sb.append("回复？显示菜单！！！！！！\n");
		return sb.toString();
	}
	
	/**
	 * 用户输入？时返回此方法
	 * @return
	 */
	public static String helpMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注，请按照提示进行操作：\n\n");
		sb.append("1.公众号简介\n");
		sb.append("2.砼盟荟图片\n");
		sb.append("3.砼盟荟二维码\n");
		sb.append("4.公司简介\n");
		sb.append("回复？显示此菜单。\n\n");
		return sb.toString();
	}
	
	/**
	 * 董晨晨，哈哈
	 * @return
	 */
	public static String myMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("love dcc\n");
		sb.append("这里只有咱俩知道，回复你的生日试试：");
		return sb.toString();
	}

	/**
	 * 图文消息组装，关注成功后显示此信息
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName, String fromUserName) {
		String message = null;
		List<News> newslist = new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();

		News news = new News();
		news.setTitle("欢迎订阅砼盟荟");
		news.setDescription("请点击本消息完成登录，以体验跟多服务！");
		news.setPicUrl(url + "/images/fltmh180706.png");
		news.setUrl(url+"/jsp/login.jsp");

		newslist.add(news);

		// 设置图文信息
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newslist);
		newsMessage.setArticleCount(newslist.size());

		message = newsMessageToXml(newsMessage);
		
		return message;
	}
	
	/**
	 * 图片信息组装
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImageMessage(String toUserName, String fromUserName) {
		String message = null;

		Image image = new Image();
		image.setMediaId("OB2a5pAacdIpyArlVbk0AjV7yFPpm3iBJHXkXb70q2hz3ow8XYwi8dQtE-LX0ml7");

		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);

		message = imageMessageToXml(imageMessage);
		
		return message;
	}
	
	/**
	 * 二维码信息组装
	 * 
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initQRCodeMessage(String toUserName, String fromUserName) {
		String message = null;

		Image image = new Image();
		image.setMediaId("qsgEc4PcDRvtajfH5jAMIRzOXgVmfxFqZDMczCmLLsEt9PtDbbYuqv0bPFDWEr4O");

		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);

		message = imageMessageToXml(imageMessage);
		
		System.out.println("砼盟荟公众号测试号二维码");

		return message;
	}
	
	/**
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNiuerMessage(String toUserName, String fromUserName) {
		String message = null;

		Image image = new Image();
		image.setMediaId("W-4I5RZmIqjom3PR9z5za4Hfy0R6g3Z8J4lqcJvmC-_9Fk1dqIXcC3Sk8QYFXl7X");

		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setImage(image);

		message = imageMessageToXml(imageMessage);
		
		return message;
	}

}
