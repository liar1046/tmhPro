package cn.csy.pojos;

/**
 * 文本消息
 * @author 少宇
 *
 */
public class TextMessage extends BaseMessage{

	private String Content;
	private String MsgId;
	
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
	
}
