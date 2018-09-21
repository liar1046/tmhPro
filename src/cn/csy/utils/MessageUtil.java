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

	// ������滻
	private static String url = WeixinTest.serverURL + "/WeixinTest";

	/*125.46.39.104
	 * ������΢�Ź��ںŵ���Ϣ����
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
	public static final String MESSAGE_SUBSCRIBE = "subscribe"; // ��ע
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe"; // ȡ����ע
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";

	/**
	 * xmlתΪmap����
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
		 * ����һ��map,����������ɶ
		 */
		/*
		 * Set<String> keySet = map.keySet(); Iterator<String> it =
		 * keySet.iterator(); while(it.hasNext()){ String key = it.next();
		 * String value = map.get(key); System.out.println(key+":"+value); }
		 */
		return map;
	}

	/**
	 * ���ı�������Ϣת��Ϊxml
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
	 * ��ͼ����Ϣת��Ϊxml
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
	 * ��ͼƬ��ϢתΪxml
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
	 * �ı���Ϣ��װ
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
	 * ���˵�����ע�ɹ�����ʾ�˲˵�
	 * 
	 * @return
	 */
	public static String menuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("��ӭʹ����������\n");
		return sb.toString();
	}

	/**
	 * �û����� 1 ��ʱ�򷵻ش˷���
	 * 
	 * @return
	 */
	public static String firstMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("�����û������������Ϣ�Լ�������Ϣ�Ĳ鿴��");
		return sb.toString();
	}
	
	/**
	 * �û����� 4 ��ʱ�򷵻ش˷���
	 * 
	 * @return
	 */
	public static String fourMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("��Ӫ��Χ������������������ҳ�����������ϵͳ���ɡ�"
				+ "�ۺϲ��߷��񣻼����Ӳ����������Ĳ����ۣ����Ӳ�Ʒ��"
				+ "ͨѶ��λ�豸�����ۣ������뾭��׼����Ŀ������ز�����׼�󷽿ɿ�չ��Ӫ���");
		return sb.toString();
	}

	/**
	 * �û�����Ƿ�����ʱ���ش˷���
	 * 
	 * @return
	 */
	public static String noMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("��������ȷ��ţ���\n");
		sb.append("�ظ�����ʾ�˵�������������\n");
		return sb.toString();
	}
	
	/**
	 * �û����룿ʱ���ش˷���
	 * @return
	 */
	public static String helpMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("��ӭ���Ĺ�ע���밴����ʾ���в�����\n\n");
		sb.append("1.���ںż��\n");
		sb.append("2.������ͼƬ\n");
		sb.append("3.��������ά��\n");
		sb.append("4.��˾���\n");
		sb.append("�ظ�����ʾ�˲˵���\n\n");
		return sb.toString();
	}
	
	/**
	 * ������������
	 * @return
	 */
	public static String myMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("love dcc\n");
		sb.append("����ֻ������֪�����ظ�����������ԣ�");
		return sb.toString();
	}

	/**
	 * ͼ����Ϣ��װ����ע�ɹ�����ʾ����Ϣ
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
		news.setTitle("��ӭ����������");
		news.setDescription("��������Ϣ��ɵ�¼��������������");
		news.setPicUrl(url + "/images/fltmh180706.png");
		news.setUrl(url+"/jsp/login.jsp");

		newslist.add(news);

		// ����ͼ����Ϣ
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
	 * ͼƬ��Ϣ��װ
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
	 * ��ά����Ϣ��װ
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
		
		System.out.println("���������ںŲ��ԺŶ�ά��");

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
