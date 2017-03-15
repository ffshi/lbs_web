package com.innovate.cms.modules.push;

import com.google.gson.JsonObject;

/**
 * 推送内容设置： title固定字符：趣选，body andriod通知展示，summary ios通知展示
 * extParameters需要自定义，前端获取相应信息进行操作（跳转到应用/网页等）
 * 
 * @author shifangfang
 * @date 2016年6月21日 上午11:13:58
 */
public class PushContent {

	// 给我留言
	public static final int LevaeMessage = 1;
	// 给我的回复
	public static final int Replay = 2;
	// 关注我
	public static final int Follow = 3;
	// 足迹内做了模板5
	public static final int FootPrint = 4;
	// 用户自定义1 跳到用户主页
	public static final int toUserMainpage = 5;
	// 用户自定义2 跳到指定专题
	public static final int toQuestion = 6;
	// 用户自定义3 打开应用首页
	public static final int toAppMainPage = 7;
	//给我的足迹点赞
	public static final int PRAISE_ME = 8;
	//足迹评论
	public static final int COMMENT = 9;
	//专题评论回复
	public static final int GROUPS_COMMENT_REPLY = 11;
	//结果页点赞
	public static final int RESULT_PAGE_PRAISE = 12;

	// 设备类型0:iOS设备,1:Andriod设备长度必须介于 1 和 3 之间
	private int deviceType;
	// 设备id1,设备id2. 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制) 如果所有填写 PushGlobal.TargetAll
	private String targetValue;
	// title 消息的标题固定
	private String title = "趣选";
	// 消息的内容 安卓默认展示 = summart
	private String body;
	// 通知的摘要，描述 ios默认展示=body
	private String summary;
	// 自定义json扩展属性
	private JsonObject extParameters;

	public PushContent() {
		super();
	}

	public PushContent(int deviceType, String targetValue, String title, String body, String summary, JsonObject extParameters) {
		super();
		this.deviceType = deviceType;
		this.targetValue = targetValue;
		this.title = title;
		this.body = body;
		this.summary = summary;
		this.extParameters = extParameters;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	public String getTargetValue() {
		return targetValue;
	}

	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		if (null != body && body.length() > 60) {
			this.body = body.substring(0, 57) + "...";
		} else {
			this.body = body;
		}
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		if (null != summary && summary.length() > 60) {
			this.summary = summary.substring(0, 57) + "...";
		} else {
			this.summary = summary;
		}
	}

	public JsonObject getExtParameters() {
		return extParameters;
	}

	public void setExtParameters(JsonObject extParameters) {
		this.extParameters = extParameters;
	}

	// Map<String, String> pushInfo = new HashMap<String, String>();
	// pushInfo.put("targetValue", "7482ca16b1b622b3c73a6fac7a359c32");
	// pushInfo.put("title", "title");
	// pushInfo.put("body", "body");
	// pushInfo.put("summary", "summary");
	// // extParameters信息配置
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("uid", "用户id");
	// map.put("umid", "消息id-留言/回复/关注");
	// map.put("gid", "someone做了您足迹内的any专题");
	// pushInfo.put("extParameters", JSON.toJSON(map).toString());
	//
	// PushClient.PushNoticeToAndroid(pushInfo);

}
