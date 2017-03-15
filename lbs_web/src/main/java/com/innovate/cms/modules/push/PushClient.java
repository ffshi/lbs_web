package com.innovate.cms.modules.push;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.push.model.v20150827.PushRequest;
import com.aliyuncs.push.model.v20150827.PushResponse;
import com.aliyuncs.utils.ParameterHelper;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.modules.push.aliyun.BasePushClient;
import com.innovate.cms.modules.push.aliyun.PushGlobal;

public class PushClient extends BasePushClient {
	/**
	 * 日志对象
	 */
	private static Logger logger = LoggerFactory.getLogger(PushClient.class);

	/**
	 * 发送消息给安卓
	 * 
	 * @Title: PushMessageToAndroid
	 * @Description: 发送消息给安卓
	 * @param target
	 *            推送目标: PushGlobal.device:推送给设备;
	 *            PushGlobal.account:推送给指定帐号,PushGlobal.tag:推送给自定义标签;
	 *            PushGlobal.all: 推送给全部
	 * @param targetValue
	 *            根据Target来设定，如Target=device, 则对应的值为 设备id1,设备id2.
	 *            多个值使用逗号分隔.(帐号与设备有一次最多100个的限制) 如果所有填写 PushGlobal.TargetAll
	 * @param title
	 *            消息的标题
	 * @param body
	 *            消息的内容
	 * @param summary
	 *            通知的摘要
	 * @param extParameters
	 *            自定义json扩展属性 可以固定格式 保持安卓IOS一致 例
	 *            <code>{\"k1\":\"android\",\"k2\":\"v2\"}</code>
	 * @throws ServerException
	 * @throws ClientException
	 *             返回类型 void
	 *
	 */
	public static void PushMessageToAndroid(String target, String targetValue, String title, String body, String summary, String extParameters) throws ServerException, ClientException {
		init();
		PushRequest pushRequest = new PushRequest();
		// 推送目标
		pushRequest.setAppKey(Global.appKey);
		pushRequest.setTarget(target);
		pushRequest.setTargetValue(targetValue);
		pushRequest.setDeviceType(1); // 设备类型deviceType 取值范围为:0~3. iOS设备: 0;
										// Android设备: 1; 全部: 3, 这是默认值.
		// 推送配置
		pushRequest.setType(0); // 0:表示消息(默认为0), 1:表示通知
		pushRequest.setTitle(title); // 消息的标题
		pushRequest.setBody(body); // 消息的内容
		pushRequest.setSummary(summary); // 通知的摘要
		pushRequest.setAndroidExtParameters(extParameters); // 设定android类型设备通知的扩展属性
		pushRequest.setRemind(true); // 当APP不在线时候，是否通过通知提醒
		pushRequest.setStoreOffline(true);
		PushResponse pushResponse = client.getAcsResponse(pushRequest);
		logger.debug("\nRequestId:{}\nResponse:{}\nIdmessage:{}", pushResponse.getRequestId(), pushResponse.getResponseId(), pushResponse.getMessage());
	}

	/**
	 * 定时推送
	 * 
	 * @param pushContent
	 */
	public static void PushContentFixtime(PushContent pushContent, Date date) {

	}

	/**
	 * 推送通知
	 * 
	 * @param pushContent
	 */
	public static void pushContentNotice(PushContent pushContent) {
		try {
			// 设备类型0:iOS设备,1:Andriod设备长度必须介于 1 和 3 之间
			if (pushContent.getDeviceType() == 0) {
				//logger.info("push to ios : " + new Gson().toJson(pushContent).toString());
				PushClient.PushNoticeToiOS(PushGlobal.device, pushContent.getTargetValue(), pushContent.getTitle(), pushContent.getBody(), pushContent.getSummary(), pushContent.getExtParameters().toString());
			} else if (pushContent.getDeviceType() == 1) {
				//logger.info("push to andriod : " + new Gson().toJson(pushContent).toString());
				PushClient.PushNoticeToAndroid(PushGlobal.device, pushContent.getTargetValue(), pushContent.getTitle(), pushContent.getBody(), pushContent.getSummary(), pushContent.getExtParameters().toString());
			}

		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 定时推送通知
	 * 
	 * @param pushContent
	 */
	public static void pushContentNotice(PushContent pushContent, Date date) {
		try {
			// 设备类型0:iOS设备,1:Andriod设备长度必须介于 1 和 3 之间
			if (pushContent.getDeviceType() == 0) {
				PushClient.PushNoticeToiOS(PushGlobal.device, pushContent.getTargetValue(), pushContent.getTitle(), pushContent.getBody(), pushContent.getSummary(), pushContent.getExtParameters().toString(), date);
			} else if (pushContent.getDeviceType() == 1) {
				PushClient.PushNoticeToAndroid(PushGlobal.device, pushContent.getTargetValue(), pushContent.getTitle(), pushContent.getBody(), pushContent.getSummary(), pushContent.getExtParameters().toString(), date);
			}

		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

	// public static void PushNoticeToAndroid(Map<String, String> pushInfo) {
	// try {
	// PushNoticeToAndroid(PushGlobal.device, pushInfo.get("targetValue"),
	// pushInfo.get("title"), pushInfo.get("body"), pushInfo.get("summary"),
	// pushInfo.get("extParameters"));
	// } catch (ServerException e) {
	// e.printStackTrace();
	// } catch (ClientException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// public static void PushNoticeToiOS(Map<String, String> pushInfo) {
	// try {
	// PushNoticeToiOS(PushGlobal.device, pushInfo.get("targetValue"),
	// pushInfo.get("title"), pushInfo.get("body"), pushInfo.get("summary"),
	// pushInfo.get("extParameters"));
	// } catch (ServerException e) {
	// e.printStackTrace();
	// } catch (ClientException e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * 发送通知给android
	 * 
	 * @Title: PushNoticeToAndroid
	 * @Description: 发送通知给android
	 * @param target
	 *            推送目标: PushGlobal.device:推送给设备;
	 *            PushGlobal.account:推送给指定帐号,PushGlobal.tag:推送给自定义标签;
	 *            PushGlobal.all: 推送给全部
	 * @param targetValue
	 *            根据Target来设定，如Target=device, 则对应的值为 设备id1,设备id2.
	 *            多个值使用逗号分隔.(帐号与设备有一次最多100个的限制) 如果所有填写 PushGlobal.TargetAll
	 * @param title
	 *            消息的标题
	 * @param body
	 *            消息的内容
	 * @param summary
	 *            通知的摘要
	 * @param extParameters
	 *            自定义json扩展属性 可以固定格式 保持安卓IOS一致 例
	 *            <code>{\"k1\":\"android\",\"k2\":\"v2\"}</code>
	 * @throws ServerException
	 * @throws ClientException
	 *             返回类型 void
	 * 
	 *
	 */
	public static void PushNoticeToAndroid(String target, String targetValue, String title, String body, String summary, String extParameters) throws ServerException, ClientException {
		init();
		PushRequest pushRequest = new PushRequest();
		// 推送目标
		pushRequest.setAppKey(Global.appKey);
		pushRequest.setTarget(target);
		pushRequest.setTargetValue(targetValue);
		pushRequest.setDeviceType(1); // 设备类型deviceType 取值范围为:0~3. iOS设备: 0;
										// Android设备: 1; 全部: 3, 这是默认值.
		// 推送配置
		pushRequest.setType(1); // 0:表示消息(默认为0), 1:表示通知
		pushRequest.setTitle(title); // 消息的标题
		pushRequest.setBody(body); // 消息的内容
		pushRequest.setSummary(summary); // 通知的摘要
		pushRequest.setAndroidExtParameters(extParameters); // 设定android类型设备通知的扩展属性
		pushRequest.setRemind(false); // 当APP不在线时候，是否通过通知提醒
		pushRequest.setStoreOffline(true);
		PushResponse pushResponse = client.getAcsResponse(pushRequest);
		logger.debug("\nRequestId:{}\nResponse:{}\nIdmessage:{}", pushResponse.getRequestId(), pushResponse.getResponseId(), pushResponse.getMessage());
	}

	/**
	 * 定时发送通知给android
	 * 
	 * @Title: PushNoticeToAndroid
	 * @Description: 发送通知给android
	 * @param target
	 *            推送目标: PushGlobal.device:推送给设备;
	 *            PushGlobal.account:推送给指定帐号,PushGlobal.tag:推送给自定义标签;
	 *            PushGlobal.all: 推送给全部
	 * @param targetValue
	 *            根据Target来设定，如Target=device, 则对应的值为 设备id1,设备id2.
	 *            多个值使用逗号分隔.(帐号与设备有一次最多100个的限制) 如果所有填写 PushGlobal.TargetAll
	 * @param title
	 *            消息的标题
	 * @param body
	 *            消息的内容
	 * @param summary
	 *            通知的摘要
	 * @param extParameters
	 *            自定义json扩展属性 可以固定格式 保持安卓IOS一致 例
	 *            <code>{\"k1\":\"android\",\"k2\":\"v2\"}</code>
	 * @throws ServerException
	 * @throws ClientException
	 *             返回类型 void
	 *
	 */
	public static void PushNoticeToAndroid(String target, String targetValue, String title, String body, String summary, String extParameters, Date date) throws ServerException, ClientException {
		init();
		PushRequest pushRequest = new PushRequest();
		// 推送目标
		pushRequest.setAppKey(Global.appKey);
		pushRequest.setTarget(target);
		pushRequest.setTargetValue(targetValue);
		pushRequest.setDeviceType(1); // 设备类型deviceType 取值范围为:0~3. iOS设备: 0;
		// Android设备: 1; 全部: 3, 这是默认值.
		// 推送配置
		pushRequest.setType(1); // 0:表示消息(默认为0), 1:表示通知
		pushRequest.setTitle(title); // 消息的标题
		pushRequest.setBody(body); // 消息的内容
		pushRequest.setSummary(summary); // 通知的摘要
		pushRequest.setAndroidExtParameters(extParameters); // 设定android类型设备通知的扩展属性
		pushRequest.setRemind(false); // 当APP不在线时候，是否通过通知提醒
		pushRequest.setStoreOffline(true);
		// 推送控制
		final String pushTime = ParameterHelper.getISO8601Time(date);
		pushRequest.setPushTime(pushTime); // 延后推送。可选，如果不设置表示立即推送

		PushResponse pushResponse = client.getAcsResponse(pushRequest);
		logger.debug("\nRequestId:{}\nResponse:{}\nIdmessage:{}", pushResponse.getRequestId(), pushResponse.getResponseId(), pushResponse.getMessage());
	}

	/**
	 * 发送消息给iOS
	 * 
	 * @Title: PushMessageToiOS
	 * @Description: 发送消息给iOS
	 * @param target
	 *            推送目标: PushGlobal.device:推送给设备;
	 *            PushGlobal.account:推送给指定帐号,PushGlobal.tag:推送给自定义标签;
	 *            PushGlobal.all: 推送给全部
	 * @param targetValue
	 *            根据Target来设定，如Target=device, 则对应的值为 设备id1,设备id2.
	 *            多个值使用逗号分隔.(帐号与设备有一次最多100个的限制) 如果所有填写 PushGlobal.TargetAll
	 * @param title
	 *            消息的标题
	 * @param body
	 *            消息的内容
	 * @param summary
	 *            通知的摘要
	 * @param extParameters
	 *            自定义json扩展属性 可以固定格式 保持安卓IOS一致 例
	 *            <code>{\"k1\":\"android\",\"k2\":\"v2\"}</code>
	 * @throws ServerException
	 * @throws ClientException
	 *             返回类型 void
	 *
	 */
	public static void PushMessageToiOS(String target, String targetValue, String title, String body, String summary, String extParameters) throws ServerException, ClientException {
		init();
		PushRequest pushRequest = new PushRequest();
		// 推送目标
		pushRequest.setAppKey(Global.appKey);
		pushRequest.setTarget(target);
		pushRequest.setTargetValue(targetValue);
		pushRequest.setDeviceType(0); // 设备类型deviceType 取值范围为:0~3. iOS设备: 0;
										// Android设备: 1; 全部: 3, 这是默认值.
		// 推送配置
		pushRequest.setType(0); // 0:表示消息(默认为0), 1:表示通知
		pushRequest.setTitle(title); // 消息的标题
		pushRequest.setBody(body); // 消息的内容
		pushRequest.setSummary(summary); // 通知的摘要
		// 推送配置: iOS
		pushRequest.setiOSBadge("1"); // iOS应用图标右上角角标
										// 可以统一放到extParameters中传送保证安卓IOS一致
		pushRequest.setiOSMusic("default"); // iOS通知声音
		pushRequest.setiOSExtParameters(extParameters); // 自定义的kv结构,开发者扩展用
														// 针对iOS设备
		pushRequest.setApnsEnv(PushGlobal.DEV);
		pushRequest.setRemind(true); // 当APP不在线时候，是否通过通知提醒
		pushRequest.setStoreOffline(true);
		PushResponse pushResponse = client.getAcsResponse(pushRequest);
		logger.debug("\nRequestId:{}\nResponse:{}\nIdmessage:{}", pushResponse.getRequestId(), pushResponse.getResponseId(), pushResponse.getMessage());
	}

	/**
	 * 发送通知给iOS
	 * 
	 * @Title: PushNoticeToiOS
	 * @Description: 发送通知给iOS
	 * @param target
	 *            推送目标: PushGlobal.device:推送给设备;
	 *            PushGlobal.account:推送给指定帐号,PushGlobal.tag:推送给自定义标签;
	 *            PushGlobal.all: 推送给全部
	 * @param targetValue
	 *            根据Target来设定，如Target=device, 则对应的值为 设备id1,设备id2.
	 *            多个值使用逗号分隔.(帐号与设备有一次最多100个的限制) 如果所有填写 PushGlobal.TargetAll
	 * @param title
	 *            消息的标题
	 * @param body
	 *            消息的内容
	 * @param summary
	 *            通知的摘要
	 * @param extParameters
	 *            自定义json扩展属性 可以固定格式 保持安卓IOS一致 例
	 *            <code>{\"k1\":\"android\",\"k2\":\"v2\"}</code>
	 * @throws ServerException
	 * @throws ClientException
	 *             返回类型 void
	 *
	 */

	public static void PushNoticeToiOS(String target, String targetValue, String title, String body, String summary, String extParameters) throws ServerException, ClientException {
		init();
		PushRequest pushRequest = new PushRequest();
		// 推送目标
		pushRequest.setAppKey(Global.appKey);
		pushRequest.setTarget(target);
		pushRequest.setTargetValue(targetValue);
		pushRequest.setDeviceType(0); // 设备类型deviceType 取值范围为:0~3. iOS设备: 0;
		// Android设备: 1; 全部: 3, 这是默认值.
		// 推送配置
		pushRequest.setType(1); // 0:表示消息(默认为0), 1:表示通知
		pushRequest.setTitle(title); // 消息的标题
		pushRequest.setBody(body); // 消息的内容
		pushRequest.setSummary(summary); // 通知的摘要
		// 推送配置: iOS
		pushRequest.setiOSBadge("1"); // iOS应用图标右上角角标
		// 可以统一放到extParameters中传送保证安卓IOS一致
		pushRequest.setiOSMusic("default"); // iOS通知声音
		pushRequest.setiOSExtParameters(extParameters); // 自定义的kv结构,开发者扩展用
		// 针对iOS设备
		pushRequest.setApnsEnv(PushGlobal.DEV);
//		pushRequest.setApnsEnv(PushGlobal.PRODUCT);
		pushRequest.setRemind(false); // 当APP不在线时候，是否通过通知提醒
		pushRequest.setStoreOffline(true);
		PushResponse pushResponse = client.getAcsResponse(pushRequest);
		logger.debug("\nRequestId:{}\nResponse:{}\nIdmessage:{}", pushResponse.getRequestId(), pushResponse.getResponseId(), pushResponse.getMessage());
	}

	/**
	 * 定时发送通知给iOS
	 * 
	 * @Title: PushNoticeToiOS
	 * @Description: 发送通知给iOS
	 * @param target
	 *            推送目标: PushGlobal.device:推送给设备;
	 *            PushGlobal.account:推送给指定帐号,PushGlobal.tag:推送给自定义标签;
	 *            PushGlobal.all: 推送给全部
	 * @param targetValue
	 *            根据Target来设定，如Target=device, 则对应的值为 设备id1,设备id2.
	 *            多个值使用逗号分隔.(帐号与设备有一次最多100个的限制) 如果所有填写 PushGlobal.TargetAll
	 * @param title
	 *            消息的标题
	 * @param body
	 *            消息的内容
	 * @param summary
	 *            通知的摘要
	 * @param extParameters
	 *            自定义json扩展属性 可以固定格式 保持安卓IOS一致 例
	 *            <code>{\"k1\":\"android\",\"k2\":\"v2\"}</code>
	 * @throws ServerException
	 * @throws ClientException
	 *             返回类型 void
	 *
	 */

	public static void PushNoticeToiOS(String target, String targetValue, String title, String body, String summary, String extParameters, Date date) throws ServerException, ClientException {
		init();
		PushRequest pushRequest = new PushRequest();
		// 推送目标
		pushRequest.setAppKey(Global.appKey);
		pushRequest.setTarget(target);
		pushRequest.setTargetValue(targetValue);
		pushRequest.setDeviceType(0); // 设备类型deviceType 取值范围为:0~3. iOS设备: 0;
		// Android设备: 1; 全部: 3, 这是默认值.
		// 推送配置
		pushRequest.setType(1); // 0:表示消息(默认为0), 1:表示通知
		pushRequest.setTitle(title); // 消息的标题
		pushRequest.setBody(body); // 消息的内容
		pushRequest.setSummary(summary); // 通知的摘要
		// 推送配置: iOS
		pushRequest.setiOSBadge("1"); // iOS应用图标右上角角标
		// 可以统一放到extParameters中传送保证安卓IOS一致
		pushRequest.setiOSMusic("default"); // iOS通知声音
		pushRequest.setiOSExtParameters(extParameters); // 自定义的kv结构,开发者扩展用

		// 推送控制
		// final Date pushDate = new Date(System.currentTimeMillis() + 30 *
		// 1000); // 30秒之间的时间点, 也可以设置成你指定固定时间
		final String pushTime = ParameterHelper.getISO8601Time(date);
		pushRequest.setPushTime(pushTime); // 延后推送。可选，如果不设置表示立即推送

		// 针对iOS设备
		pushRequest.setApnsEnv(PushGlobal.DEV);
		pushRequest.setRemind(false); // 当APP不在线时候，是否通过通知提醒
		pushRequest.setStoreOffline(true);
		PushResponse pushResponse = client.getAcsResponse(pushRequest);
		logger.debug("\nRequestId:{}\nResponse:{}\nIdmessage:{}", pushResponse.getRequestId(), pushResponse.getResponseId(), pushResponse.getMessage());
	}

	/**
	 * 定时推送给所有设备
	 * 
	 * @param title
	 * @param body
	 * @param summary
	 * @param extParameters
	 * @param date
	 * @throws ServerException
	 * @throws ClientException
	 */
	public static void pushNoticeToAllDeviceTypeAndAllDevice(String title, String body, String summary, String extParameters, Date date) throws ServerException, ClientException {
		init();
		PushRequest pushRequest = new PushRequest();

		// 推送目标
		pushRequest.setAppKey(Global.appKey);
		pushRequest.setTarget("all"); // 推送目标: device:推送给设备;
										// account:推送给指定帐号,tag:推送给自定义标签; all:
										// 推送给全部
		pushRequest.setTargetValue("all"); // 根据Target来设定，如Target=device, 则对应的值为
											// 设备id1,设备id2.
											// 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
		pushRequest.setDeviceType(3); // 设备类型deviceType 取值范围为:0~3. iOS设备: 0;
										// Android设备: 1; 全部: 3, 这是默认值.

		// 推送配置
		pushRequest.setType(1); // 0:表示消息(默认为0), 1:表示通知
		pushRequest.setTitle(title); // 消息的标题
		pushRequest.setBody(body); // 消息的内容
		pushRequest.setSummary(summary); // 通知的摘要
		// 推送配置: iOS
		pushRequest.setiOSBadge("5"); // iOS应用图标右上角角标
		pushRequest.setiOSMusic("default"); // iOS通知声音
		pushRequest.setiOSExtParameters(extParameters); // 自定义的kv结构,开发者扩展用
														// 针对iOS设备
		pushRequest.setApnsEnv("DEV");
		pushRequest.setRemind(false); // 当APP不在线时候，是否通过通知提醒
		// 推送配置: Android
		// pushRequest.setAndroidOpenType("3"); // 点击通知后动作,1:打开应用 2:
		// 打开应用Activity 3:打开 url
		// pushRequest.setAndroidOpenUrl("http://www.baidu.com"); //
		// Android收到推送后打开对应的url,仅仅当androidOpenType=3有效
		pushRequest.setAndroidExtParameters(extParameters); // 设定android类型设备通知的扩展属性

		// 推送控制
		// final Date pushDate = new Date(System.currentTimeMillis() + 30 *
		// 1000); // 30秒之间的时间点, 也可以设置成你指定固定时间
		final String pushTime = ParameterHelper.getISO8601Time(date);
		pushRequest.setPushTime(pushTime); // 延后推送。可选，如果不设置表示立即推送
		pushRequest.setStoreOffline(true); // 离线消息是否保存,若保存,
											// 在推送时候，用户即使不在线，下一次上线则会收到
		// final String expireTime = ParameterHelper.getISO8601Time(new
		// Date(System.currentTimeMillis() + 12 * 3600 * 1000)); // 12小时后消息失效,
		// 不会再发送
		// pushRequest.setExpireTime(expireTime);
		// pushRequest.setBatchNumber("100010"); // 批次编号,用于活动效果统计. 设置成业务可以记录的字符串

		PushResponse pushResponse = client.getAcsResponse(pushRequest);
		System.out.printf("RequestId: %s, ResponseId: %s, message: %s\n", pushResponse.getRequestId(), pushResponse.getResponseId(), pushResponse.getMessage());
	}

	public static void pushNoticeToAll(PushContent pushContent, Date date) {
		try {
			pushNoticeToAllDeviceTypeAndAllDevice(pushContent.getTitle(), pushContent.getBody(), pushContent.getSummary(), pushContent.getExtParameters().toString(), date);
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
}
