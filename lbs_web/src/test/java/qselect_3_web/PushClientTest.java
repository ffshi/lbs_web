package qselect_3_web;

import com.google.gson.JsonObject;
import com.innovate.cms.modules.push.PushClient;
import com.innovate.cms.modules.push.PushContent;

public class PushClientTest {
	public static final int ANDORID = 1;
	public static final int IOS = 0;

	public static void main(String[] args) {
		//b902b2a7ded24ec48d2e608299d208c2
		testPush(IOS, "b902b2a7ded24ec48d2e608299d208c2");
	}

	public static void testPush(int deviceType, String deviceId) {
		PushContent content = new PushContent();
		content.setDeviceType(deviceType);
		content.setTargetValue(deviceId);

		content.setBody("summary");
		content.setSummary("summary");
		content.setTitle("title");
		// 设置自定义json扩展属性
		JsonObject extParameters = new JsonObject();
		extParameters.addProperty("jumpId", "dfsdsfdsfsdfsdfd");
		extParameters.addProperty("type", PushContent.FootPrint);
		content.setExtParameters(extParameters);

		PushClient.pushContentNotice(content);
	}
}
