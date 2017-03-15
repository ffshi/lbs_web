package qselect_3_web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.TaobaoRequest;
import com.taobao.api.TaobaoResponse;
import com.taobao.api.domain.OpenImUser;
import com.taobao.api.request.OpenimChatlogsGetRequest;
import com.taobao.api.request.OpenimCustmsgPushRequest;
import com.taobao.api.request.OpenimCustmsgPushRequest.CustMsg;
import com.taobao.api.request.OpenimImmsgPushRequest;
import com.taobao.api.request.OpenimImmsgPushRequest.ImMsg;
import com.taobao.api.request.OpenimRelationsGetRequest;
import com.taobao.api.request.OpenimTrackGetsummaryRequest;
import com.taobao.api.request.OpenimTribeCreateRequest;
import com.taobao.api.request.OpenimTribeDismissRequest;
import com.taobao.api.request.OpenimTribeExpelRequest;
import com.taobao.api.request.OpenimTribeGetalltribesRequest;
import com.taobao.api.request.OpenimTribeGetmembersRequest;
import com.taobao.api.request.OpenimTribeGettribeinfoRequest;
import com.taobao.api.request.OpenimTribeInviteRequest;
import com.taobao.api.request.OpenimTribeJoinRequest;
import com.taobao.api.request.OpenimTribeQuitRequest;
import com.taobao.api.request.OpenimTribeSetmanagerRequest;
import com.taobao.api.request.OpenimUsersAddRequest;
import com.taobao.api.request.OpenimUsersDeleteRequest;
import com.taobao.api.request.OpenimUsersGetRequest;
import com.taobao.api.request.OpenimUsersUpdateRequest;

public class OpenIMTopImpl {

	// TODO 这里替换成 开发者的 百川 Appkey ＋ secrect
	// secrect 在 my.open.taobao.com 应用控制台－证书权限管理-App Secret:
	private static TaobaoClient sClient = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", IMConfig.APPKEY, IMConfig.AppSecret);

	public static TaobaoClient getClient(String appkey) {
		return sClient;
	}

	/**
	 * 从 TaobaoResponse 中解析出一个 JSONObject
	 * 
	 * @param response
	 * @param path
	 *            json路径，使用[.]分割
	 * @return
	 */
	public static JSONObject getJsonObject(TaobaoResponse response, String path) {
		if (response != null && response.isSuccess()) {
			JSONObject json = JSONObject.parseObject(response.getBody().toString());

			for (String key : path.split("\\.")) {
				if (json.containsKey(key)) {
					json = json.getJSONObject(key);
				} else {
					return null;
				}
			}
			return json;
		}
		if (response != null) {
			System.out.println(response.getBody());
		}
		return null;
	}

	/**
	 * 从taobaoResponse中解析出一个JSONArray
	 * 
	 * @param response
	 * @param path
	 *            JSONArray路径，使用［.］分割
	 * @return
	 */
	public static JSONArray getJsonArray(TaobaoResponse response, String path) {
		if (response != null && response.isSuccess()) {
			JSONObject json = JSONObject.parseObject(response.getBody());
			String[] paths = path.split("\\.");
			for (int index = 0; index < paths.length - 1; index++) {
				String key = paths[index];
				if (json.containsKey(key)) {
					json = json.getJSONObject(key);
				} else {
					if (response != null) {
						System.out.println(response.getBody());
					}
					return null;
				}
			}
			String key = paths[paths.length - 1];
			if (json.containsKey(key)) {
				return json.getJSONArray(key);
			}
		}
		if (response != null) {
			System.out.println(response.getBody());
		}
		return null;
	}

	/**
	 * 创建一个 OpenImUser
	 * 
	 * @param appkey
	 * @param uid
	 * @param isEService
	 *            是否E客服账号
	 * @return
	 */
	public static OpenImUser createUser(String appkey, String uid, boolean isEService) {
		OpenImUser user = new OpenImUser();
		user.setUid(uid);
		user.setTaobaoAccount(isEService);
		user.setAppKey(appkey);
		return user;
	}

	public static OpenImUser createUser(String appkey, String uid) {
		return createUser(appkey, uid, false);
	}

	/**
	 * 调用 TaobaoRequest 的方法
	 * 
	 * @param appkey
	 * @param request
	 * @return
	 * @throws ApiException
	 */
	@SuppressWarnings("unchecked")
	private static TaobaoResponse execute(String appkey, @SuppressWarnings("rawtypes") TaobaoRequest request) throws ApiException {
		TaobaoClient client = getClient(appkey);
		if (client == null || request == null) {
			return null;
		}

		// TODO 如果有问题，可以记录日志，把 response.getBody() 输出到日志中，并反馈给 openim官方客服:server。
		// 内部的环境不是特别稳定，所以自己做了一个重试，开发者根据需要处理
		TaobaoResponse response = null;
		int times = 10;
		do {
			response = client.execute(request);
			if (response.isSuccess()) {
				return response;
			}
			String subCode = response.getSubCode();
			if (subCode != null && !subCode.equals("isp.http-connection-refuse")) {
				break;
			}
		} while (times-- > 0);
		return response;
	}

	private static Random sRandom = new Random();
	static {
		sRandom.setSeed(System.currentTimeMillis());
	}

	public static int random(int max) {
		if (max > 1) {
			return sRandom.nextInt(max);
		}
		return 0;
	}

	private static String[] mAvatars = new String[] { "http://img01.taobaocdn.com/top/i1/LB1RG6xKXXXXXbDXpXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB1B0rBKXXXXXagXpXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB10YLjKXXXXXX_XVXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB1WR6tKXXXXXXhXFXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB1dLPrKXXXXXaEXFXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB1NxjBKXXXXXagXpXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB1cb_eKXXXXXceXVXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB1MIPNKXXXXXXaXXXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB1gTnFKXXXXXcdXXXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB1L9DuKXXXXXcUXpXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB1GTjmKXXXXXc2XFXXXXXXXXXX",
			"http://img01.taobaocdn.com/top/i1/LB1ajjfKXXXXXbRXVXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB1n6fJKXXXXXayXXXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB1UoPyKXXXXXa0XpXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB1eOnJKXXXXXaAXXXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB1e86uKXXXXXcwXpXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB1LQDoKXXXXXbVXFXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB1IcvMKXXXXXXAXXXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB1OCnMKXXXXXXgXXXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB1pNYCKXXXXXXSXpXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB1MtYkKXXXXXXMXVXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB16pbqKXXXXXbrXFXXXXXXXXXX", "http://img01.taobaocdn.com/top/i1/LB18azuKXXXXXb1XpXXXXXXXXXX",
			"http://img01.taobaocdn.com/top/i1/LB1ncHuKXXXXXXgXFXXXXXXXXXX" };
	private static String[] mNicks = new String[] { "宋江", "林冲", "武松", "鲁智深", "吴用", "秦明", "花荣", "柴进", "李逵", "杨志", "史进", "戴宗", "卢俊义", "阮小二", "张顺", "燕青", "孙二娘", "扈三娘", "杨雄", "呼延灼" };

	public static boolean userAdd(String appkey, String uid, String pwd) {
		return userAdd(appkey, uid, pwd, String.format("%s-%s", mNicks[random(mNicks.length)], uid));
	}

	/**
	 * taobao.openim.users.add (添加用户)
	 * 
	 * @param appkey
	 * @param uid
	 * @param pwd
	 * @param nick
	 * @param avatar
	 * @return
	 */
	public static boolean userAdd(String appkey, String uid, String pwd, String nick, String avatar) {
		String list;
		if (avatar != null) {
			list = String.format("[{\"userid\":\"%s\",\"password\":\"%s\", \"nick\":\"%s\", \"icon_url\":\"%s\"}]", uid, pwd, nick, avatar);
		} else {
			list = String.format("[{\"userid\":\"%s\",\"password\":\"%s\", \"nick\":\"%s\"}]", uid, pwd, nick);
		}
		OpenimUsersAddRequest req = new OpenimUsersAddRequest();
		req.setUserinfos(list);
		try {
			JSONArray json = getJsonArray(execute(appkey, req), "openim_users_add_response.uid_succ.string");
			if (json != null && json.size() != 0 && 0 == uid.compareToIgnoreCase((String) json.get(0))) {
				return true;
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean userAdd(String appkey, String uid, String pwd, String nick) {
		String avatar = null;
		int useAvatar = random(3);
		if (useAvatar != 0) {
			int pos = random(mAvatars.length);
			avatar = mAvatars[pos];
		}
		return userAdd(appkey, uid, pwd, nick, avatar);
	}

	/**
	 * taobao.openim.users.delete (删除用户)
	 * 
	 * @param appkey
	 * @param uid
	 * @return
	 */
	public static boolean userDelete(String appkey, String uid) {
		OpenimUsersDeleteRequest req = new OpenimUsersDeleteRequest();
		String userids = uid;
		req.setUserids(userids);
		try {
			JSONArray json = getJsonArray(execute(appkey, req), "openim_users_delete_response.result.string");
			if (json != null && json.size() != 0 && json.get(0).equals("ok")) {
				return true;
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean userUpdate(String appkey, String uid, String pwd) {
		return userUpdate(appkey, uid, pwd, "Top-" + uid);
	}

	public static boolean userUpdate(String appkey, String uid, String pwd, String nick) {
		return userUpdate(appkey, uid, pwd, nick, "");
	}

	/**
	 * taobao.openim.users.update (批量更新用户信息)
	 * 
	 * @param appkey
	 * @param uid
	 * @param pwd
	 * @param nick
	 * @param icon_url
	 * @return
	 */
	public static boolean userUpdate(String appkey, String uid, String pwd, String nick, String icon_url) {
		OpenimUsersUpdateRequest req = new OpenimUsersUpdateRequest();
		String list = String.format("[{\"userid\":\"%s\",\"password\":\"%s\", \"nick\":\"%s\", \"icon_url\":\"%s\"}]", uid, pwd, nick, icon_url);
		req.setUserinfos(list);

		try {
			JSONArray json = getJsonArray(execute(appkey, req), "openim_users_update_response.uid_succ.string");
			if (json != null && json.size() != 0 && 0 == uid.compareToIgnoreCase((String) json.get(0))) {
				return true;
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * taobao.openim.users.get (批量获取用户信息)
	 * 
	 * @param appkey
	 * @param uid
	 * @return
	 */
	public static JSONObject userGet(String appkey, String uid) {
		OpenimUsersGetRequest req = new OpenimUsersGetRequest();
		req.setUserids(uid);
		try {
			JSONObject json = getJsonObject(execute(appkey, req), "openim_users_get_response.userinfos");
			if (json != null && json.containsKey("userinfos")) {
				return json.getJSONArray("userinfos").getJSONObject(0);
			}
			// if (json != null && json.size()!=0) {
			// return (JSONObject)json.get(0);
			// }
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * taobao.openim.immsg.push (openim标准消息发送)
	 * 
	 * @param appkey
	 * @param fromId
	 * @param fromAppkey
	 * @param toIds
	 * @param toAppkey
	 * @param msgType
	 * @param mediaAttr
	 * @param context
	 * @return
	 */
	public static JSONObject immsgPush(String appkey, String fromId, String fromAppkey, String toIds, String toAppkey, long msgType, String mediaAttr, String context) {
		OpenimImmsgPushRequest req = new OpenimImmsgPushRequest();
		ImMsg immsg = new ImMsg();
		// immsg.setFromAppkey(fromAppkey);
		immsg.setFromUser(fromId);
		immsg.setToAppkey(toAppkey);
		immsg.setToUsers(Arrays.asList(toIds.split(",")));
		immsg.setMsgType(msgType);
		immsg.setMediaAttr(mediaAttr);
		immsg.setContext(context);
		req.setImmsg(immsg);
		try {
			JSONObject json = getJsonObject(execute(appkey, req), "openim_immsg_push_response");
			if (json != null && json.containsKey("msgid")) {
				Long msgId = json.getLong("msgid");
				json = new JSONObject();
				json.put("sender", fromId.toLowerCase());
				json.put("msgId", msgId);
				return json;
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject immsgPush(String appkey, String fromId, String toIds, long msgType, String mediaAttr, String context) {
		return immsgPush(appkey, fromId, appkey, toIds, appkey, msgType, mediaAttr, context);
	}

	/**
	 * taobao.openim.custmsg.push (推送自定义openim消息)
	 * 
	 * @param appkey
	 * @param fromId
	 * @param fromAppkey
	 * @param toIds
	 * @param toAppkey
	 * @param summary
	 * @param data
	 * @param aps
	 * @param apnsParam
	 * @return
	 */
	public static JSONObject custmsgPush(String appkey, String fromId, String fromAppkey, String toIds, String toAppkey, String summary, String data, String aps, String apnsParam) {
		OpenimCustmsgPushRequest req = new OpenimCustmsgPushRequest();
		CustMsg custmsg = new CustMsg();
		// custmsg.setFromAppkey(fromAppkey);
		custmsg.setFromUser(fromId);
		custmsg.setToAppkey(toAppkey);
		custmsg.setToUsers(Arrays.asList(toIds.split(",")));
		custmsg.setSummary(summary);
		custmsg.setData(data);
		custmsg.setAps(aps);
		custmsg.setApnsParam(apnsParam);
		req.setCustmsg(custmsg);
		try {
			JSONObject json = getJsonObject(execute(appkey, req), "openim_custmsg_push_response");
			if (json != null && json.containsKey("msgid")) {
				Long msgId = json.getLong("msgid");
				json = new JSONObject();
				json.put("sender", fromId.toLowerCase());
				json.put("msgId", msgId);
				return json;
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject custmsgPush(String appkey, String fromId, String toIds, String summary, String data, String aps, String apnsParam) {
		return custmsgPush(appkey, fromId, appkey, toIds, appkey, summary, data, aps, apnsParam);
	}

	// public static JSONObject sendDiscountMsg(String appkey, String
	// strReceivers) {
	// List<String> receivers = Arrays.asList(strReceivers.split(","));
	// // ... set receivers
	// OpenimCustmsgPushRequest request = new OpenimCustmsgPushRequest();
	// CustMsg custmsg = new CustMsg();
	// custmsg.setFromAppkey(appkey);
	// String fromId = "openim运营消息2";
	// custmsg.setFromUser(fromId); // 发送方ID，您也可以虚拟一个会话用于运营消息的发送
	// custmsg.setToAppkey(appkey);
	// custmsg.setToUsers(receivers);
	// custmsg.setSummary("您有一张优惠券"); // 本文案将显示在会话列表的最后一条消息中
	// JSONObject jsonData = new JSONObject(); // 用JSON串来保存数据，方便扩展。
	// jsonData.put("type", 1); // 消息类型，可以用来实现多种自定义消息
	// jsonData.put("url", "http://www.taobao.com"); // 领取优惠券的url
	// jsonData.put("text", "您有一张优惠券！"); // 领取优惠券的文案
	// custmsg.setData(jsonData.toString());
	// JSONObject jsonAps = new JSONObject();
	// jsonAps.put("alert", "Test您有一张优惠券");
	// custmsg.setAps(jsonAps.toString()); // 本字段为可选，若alert为空，则表示不进行apns推送
	// custmsg.setApnsParam("apnsParam"); // apns推送的附带数据
	// request.setCustmsg(custmsg);
	// try {
	// JSONObject json = getJsonObject(execute(appkey, request),
	// "openim_custmsg_push_response");
	// if (json != null && json.containsKey("msgid")) {
	// Long msgId = json.getLong("msgid");
	// json = new JSONObject();
	// json.put("sender", fromId.toLowerCase());
	// json.put("msgId", msgId);
	// return json;
	// }
	// } catch (ApiException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

	/**
	 * taobao.openim.relations.get (获取openim账号的聊天关系), 不支持查询E客服的聊天记录
	 * 
	 * @param appkey
	 * @param uid
	 * @return
	 */
	public static JSONArray relationsGet(String appkey, String uid) {
		OpenimRelationsGetRequest req = new OpenimRelationsGetRequest();
		req.setBegDate(Common.getPreDateString(30));
		req.setEndDate(Common.getCurrentDateString());
		req.setUser(createUser(appkey, uid));
		try {
			JSONArray json = getJsonArray(execute(appkey, req), "openim_relations_get_response.users.open_im_user");
			return json;
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONArray chatlogsGet(String appkey, String uid, String targetId) {
		return chatlogsGet(appkey, uid, targetId, null);
	}

	public static JSONArray chatlogGetWithEService(String appkey, String uid, String targetId) {
		return chatlogsGet(appkey, uid, targetId, appkey, true);
	}

	/**
	 * taobao.openim.chatlogs.get (openim聊天记录查询接口)
	 * 
	 * @param appkey
	 * @param uid
	 * @param targetId
	 * @param targetAppkey
	 * @param isEService
	 *            是否是 E客服账号
	 * @return
	 */
	public static JSONArray chatlogsGet(String appkey, String uid, String targetId, String targetAppkey, boolean isEService) {
		OpenimChatlogsGetRequest req = new OpenimChatlogsGetRequest();
		if (targetAppkey == null)
			targetAppkey = appkey;
		req.setUser1(createUser(appkey, uid));
		req.setUser2(createUser(targetAppkey, targetId, isEService));
		req.setBegin(System.currentTimeMillis() / 1000 - IMConfig.DAY30);
		req.setEnd(System.currentTimeMillis() / 1000);
		req.setCount(IMConfig.RECENT_MESSAGE);
		// req.setNextKey("sdjfshkhdj = = ");
		try {
			JSONObject json = getJsonObject(execute(appkey, req), "openim_chatlogs_get_response.result.messages");
			if (json.containsKey("roaming_message")) {
				return json.getJSONArray("roaming_message");
			} else {
				return new JSONArray();
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONArray chatlogsGet(String appkey, String uid, String targetId, String targetAppkey) {
		return chatlogsGet(appkey, uid, targetId, targetAppkey, false);
	}

	/**
	 * 查询账户，如果账户不存在，则创建一个
	 * 
	 * @param appkey
	 * @param uid
	 * @return
	 */
	public static boolean getAccount(String appkey, String uid) {
		JSONObject json = userGet(appkey, uid);
		if (json != null) {
			return true;
		}
		return userAdd(appkey, uid, "taobao1234");
	}

	/**
	 * taobao.openim.tribe.getalltribes (获取用户群列表)
	 * 
	 * @param appkey
	 * @param uid
	 * @param tribeType
	 * @return
	 */
	public static JSONArray tribeGetAllTribes(String appkey, String uid, String tribeType) {
		OpenimTribeGetalltribesRequest req = new OpenimTribeGetalltribesRequest();
		req.setUser(createUser(appkey, uid));
		req.setTribeTypes(tribeType);
		try {
			JSONObject json = getJsonObject(execute(appkey, req), "openim_tribe_getalltribes_response.tribe_info_list");
			if (json != null) {
				final String tribeInfo = "tribe_info";
				if (json.containsKey(tribeInfo)) {
					return json.getJSONArray(tribeInfo);
				} else {
					return new JSONArray();
				}
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONArray tribeGetAllTribes(String appkey, String uid) {
		return tribeGetAllTribes(appkey, uid, "0");
	}

	public static JSONArray tribeGetAllGroups(String appkey, String uid) {
		return tribeGetAllTribes(appkey, uid, "1");
	}

	public static JSONObject tribeCreateTribe(String appkey, String uid, String name, String notice) {
		return tribeCreate(appkey, uid, name, notice, 0, null);
	}

	public static JSONObject tribeCreateGroup(String appkey, String uid, String name, String notice, String... memberIds) {
		List<OpenImUser> members = new ArrayList<OpenImUser>();
		for (String member : memberIds) {
			members.add(createUser(appkey, member));
		}

		return tribeCreate(appkey, uid, name, notice, 0, members);
	}

	/**
	 * taobao.openim.tribe.create (创建群)
	 * 
	 * @param appkey
	 * @param uid
	 * @param name
	 * @param notice
	 * @param type
	 * @param members
	 * @return
	 */
	// type=0, members 必须是空， type＝1，则 members 不能为空
	public static JSONObject tribeCreate(String appkey, String uid, String name, String notice, long type, List<OpenImUser> members) {
		OpenimTribeCreateRequest req = new OpenimTribeCreateRequest();
		req.setUser(createUser(appkey, uid));
		req.setTribeName(name);
		req.setNotice(notice);
		req.setTribeType(type);

		req.setMembers(members);
		try {
			return getJsonObject(execute(appkey, req), "openim_tribe_create_response.tribe_info");
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * taobao.openim.tribe.dismiss (OPENIM群解散)
	 * 
	 * @param appkey
	 * @param uid
	 * @param tribeId
	 * @return
	 */
	public static boolean tribeDismiss(String appkey, String uid, long tribeId) {
		OpenimTribeDismissRequest req = new OpenimTribeDismissRequest();
		req.setUser(createUser(appkey, uid));
		req.setTribeId(tribeId);
		try {
			JSONObject json = getJsonObject(execute(appkey, req), "openim_tribe_dismiss_response");
			if (json != null && json.containsKey("tribe_code")) {
				return json.getInteger("tribe_code") == 0;
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * taobao.openim.tribe.gettribeinfo (获取群信息)
	 * 
	 * @param appkey
	 * @param uid
	 * @param tribeId
	 * @return
	 */
	public static JSONObject tribeGetTribeInfo(String appkey, String uid, long tribeId) {
		OpenimTribeGettribeinfoRequest req = new OpenimTribeGettribeinfoRequest();
		req.setUser(createUser(appkey, uid));
		req.setTribeId(tribeId);
		try {
			return getJsonObject(execute(appkey, req), "openim_tribe_gettribeinfo_response.tribe_info");
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * taobao.openim.tribe.invite (OPENIM群邀请加入)
	 * 
	 * @param appkey
	 * @param uid
	 * @param target
	 * @param tribeId
	 * @return
	 */
	public static boolean tribeInvite(String appkey, String uid, String target, Long tribeId) {
		OpenimTribeInviteRequest req = new OpenimTribeInviteRequest();
		req.setUser(createUser(appkey, uid));
		req.setMembers(Arrays.asList(createUser(appkey, target)));
		req.setTribeId(tribeId);
		try {
			JSONObject json = getJsonObject(execute(appkey, req), "openim_tribe_invite_response");
			if (json != null && json.containsKey("tribe_code")) {
				return json.getInteger("tribe_code") == 0;
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * taobao.openim.tribe.setmanager (OPENIM群设置管理员)
	 * 
	 * @param appkey
	 * @param uid
	 * @param target
	 * @param tribeId
	 * @return
	 */
	public static boolean tribeSetManager(String appkey, String uid, String target, Long tribeId) {
		OpenimTribeSetmanagerRequest req = new OpenimTribeSetmanagerRequest();
		req.setUser(createUser(appkey, uid));
		req.setMember(createUser(appkey, target));
		req.setTid(tribeId);
		try {
			JSONObject json = getJsonObject(execute(appkey, req), "openim_tribe_setmanager_response");
			if (json != null && json.containsKey("tribe_code")) {
				return json.getInteger("tribe_code") == 0;
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * taobao.openim.tribe.expel (OPENIM群踢出成员)
	 * 
	 * @param appkey
	 * @param uid
	 * @param target
	 * @param tribeId
	 * @return
	 */
	public static boolean tribeExpel(String appkey, String uid, String target, Long tribeId) {
		OpenimTribeExpelRequest req = new OpenimTribeExpelRequest();
		req.setUser(createUser(appkey, uid));
		req.setMember(createUser(appkey, target));
		req.setTribeId(tribeId);
		try {
			JSONObject json = getJsonObject(execute(appkey, req), "openim_tribe_expel_response");
			if (json != null && json.containsKey("tribe_code")) {
				return json.getInteger("tribe_code") == 0;
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * taobao.openim.tribe.join (OPENIM群主动加入)
	 * 
	 * @param appkey
	 * @param uid
	 * @param tribeId
	 * @return
	 */
	public static boolean tribeJoin(String appkey, String uid, long tribeId) {
		OpenimTribeJoinRequest req = new OpenimTribeJoinRequest();
		req.setUser(createUser(appkey, uid));
		req.setTribeId(tribeId);
		try {
			JSONObject json = getJsonObject(execute(appkey, req), "openim_tribe_join_response");
			if (json != null && json.getLong("tribe_code") == 0) {
				return true;
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取某个群成员的身份
	 * 
	 * @param appkey
	 * @param uid
	 * @param tribeId
	 * @param memberId
	 * @return
	 */
	public static String tribeGetMemberRole(String appkey, String uid, long tribeId, String memberId) {
		JSONArray members = tribeGetMembers(appkey, uid, tribeId);
		for (int index = 0; index < members.size(); index++) {
			JSONObject member = members.getJSONObject(index);
			if (member.getString("uid").equals(memberId)) {
				return member.getString("role");
			}
		}
		return null;
	}

	public static String tribeGetMemberRole(String appkey, String uid, long tribeId) {
		return tribeGetMemberRole(appkey, uid, tribeId, uid);
	}

	// public static List<Long> tribeQuitAllTribes(String appkey, String uid) {
	// List<Long> result = new ArrayList<Long>();
	// JSONArray tribeIds = tribeGetAllTribes(appkey, uid);
	// for (int index = 0; index < tribeIds.size(); index++) {
	// long tribeId = tribeIds.getJSONObject(index).getLong("tribe_id");
	// if ("host".equals(tribeGetMemberRole(appkey, uid, tribeId))) {
	// if (tribeDismiss(appkey, uid, tribeId)) {
	// result.add(tribeId);
	// }
	// } else {
	// if (tribeQuit(appkey, uid, tribeId)) {
	// result.add(tribeId);
	// }
	// }
	// }
	//
	// tribeIds = tribeGetAllGroups(appkey, uid);
	// for (int index = 0; index < tribeIds.size(); index++) {
	// long tribeId = tribeIds.getJSONObject(index).getLong("tribe_id");
	// if (tribeQuit(appkey, uid, tribeId)) {
	// result.add(tribeId);
	// }
	// }
	//
	// return result;
	// }

	/**
	 * taobao.openim.tribe.quit (OPENIM群成员退出)
	 * 
	 * @param appkey
	 * @param uid
	 * @param tribeId
	 * @return
	 */
	public static boolean tribeQuit(String appkey, String uid, long tribeId) {
		OpenimTribeQuitRequest req = new OpenimTribeQuitRequest();
		req.setUser(createUser(appkey, uid));
		req.setTribeId(tribeId);
		try {
			JSONObject json = getJsonObject(execute(appkey, req), "openim_tribe_quit_response");
			if (json != null && json.getLong("tribe_code") == 0) {
				return true;
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * taobao.openim.tribe.getmembers (OPENIM群成员获取)
	 * 
	 * @param appkey
	 * @param uid
	 * @param tribeId
	 * @return
	 */
	public static JSONArray tribeGetMembers(String appkey, String uid, long tribeId) {
		OpenimTribeGetmembersRequest req = new OpenimTribeGetmembersRequest();
		req.setTribeId(tribeId);
		req.setUser(createUser(appkey, uid));
		try {
			JSONObject json = getJsonObject(execute(appkey, req), "openim_tribe_getmembers_response.tribe_user_list");
			if (json != null && json.containsKey("tribe_user")) {
				return json.getJSONArray("tribe_user");
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	// public static void testMaxTribeMember(String appkey, String uid, long
	// tribeId) {
	// int index = 1;
	// while (true) {
	// String tempId = "测" + index++;
	// if (!tribeJoin(appkey, tempId, tribeId)) {
	// System.out.println("jion error null:" + tempId);
	// break;
	// } else {
	// System.out.println("jion :" + tempId);
	// }
	// }
	// JSONArray members = tribeGetMembers(appkey, uid, tribeId);
	// System.out.println(members);
	// System.out.println(members.size());
	// }

	/**
	 * 退出所有群, 群主的解散群
	 * 
	 * @param appkey
	 * @param uid
	 * @return
	 */
	public static boolean tribeQuitAllTribe(String appkey, String uid) {
		List<Integer> tribeTypes = Arrays.asList(0, 1);
		for (Integer tribeType : tribeTypes) {
			JSONArray tribes = tribeGetAllTribes(appkey, uid, "" + tribeType);
			System.out.println("total:" + tribes.size());
			if (tribes != null && tribes.size() != 0) {
				for (int index = 0; index < tribes.size(); index++) {
					Long tribeId = tribes.getJSONObject(index).getLong("tribe_id");
					JSONArray members = tribeGetMembers(appkey, uid, tribeId);
					for (int j = 0; j < members.size(); j++) {
						JSONObject member = members.getJSONObject(j);
						if (member.getString("role").equals("host")) {
							if (!tribeDismiss(appkey, member.getString("uid"), tribeId)) {
								System.out.println("tribeInfo:" + tribeGetTribeInfo(appkey, uid, tribeId));
								continue;
							}
						} else {
							if (!tribeQuit(appkey, uid, tribeId)) {
								System.out.println("tribeInfo:" + tribeGetTribeInfo(appkey, uid, tribeId));
								continue;
							}
						}
					}
				}
			}
		}
		return true;
	}

	// public static boolean createTribeForOpen(String appkey, int index) {
	// String prefix = "testpro";
	// // String prefix = "visitor";
	// String host = prefix + index;
	// String manager = prefix + (index + 1);
	// String normal = prefix + (index + 2);
	// getAccount(appkey, host);
	// getAccount(appkey, manager);
	// getAccount(appkey, normal);
	// JSONObject tribeInfo;
	// JSONArray tribes = tribeGetAllTribes(appkey, host);
	// if (tribes == null) {
	// tribeInfo = tribeCreateTribe(appkey, host, "体验群_群主_" + index, "体验群公告");
	// if (tribeInfo == null) {
	// System.out.println("==========createTribe faild!" + index);
	// return false;
	// }
	// Long tribeId = tribeInfo.getLong("tribe_id");
	// boolean bRes = tribeJoin(appkey, manager, tribeId);
	// if (!bRes) {
	// System.out.println("==========manager joinTribe faild!" + index);
	// return false;
	// }
	// bRes = tribeJoin(appkey, normal, tribeId);
	// if (!bRes) {
	// System.out.println("==========normal joinTribe faild!" + index);
	// return false;
	// }
	// }
	// // tribeGetMembers(appkey, host, tribeId);
	//
	// tribeInfo = tribeCreateGroup(appkey, host, "体验讨论组_群主" + index, "体验讨论组公告",
	// manager, normal);
	// if (tribeInfo == null) {
	// System.out.println("==========createGroup faild!" + index);
	// return false;
	// }
	// return true;
	// // tribeId = tribeInfo.getLong("tribe_id");
	// // tribeGetMembers(appkey, host, tribeId);
	// }

	// public static void createTribeForOpen(String appkey) {
	// boolean allSuccess = true;
	// String fails = "";
	// for (int i = 0; i < 100; i++) {
	// if (!createTribeForOpen(appkey, i)) {
	// allSuccess = false;
	// fails += i + ",";
	// }
	// }
	// if (allSuccess) {
	// System.out.println("done");
	// } else {
	// System.out.println("done for fails." + fails);
	// }
	// }

	public static boolean tribeCreateTribe(String appkey, String tribeName, String host, String manager, List<String> normals) {
		JSONObject tribe = tribeCreateTribe(appkey, host, tribeName, "测试群公告");
		if (tribe == null)
			return false;
		Long tribeId = tribe.getLong("tribe_id");
		normals.add(manager);
		for (String member : normals) {
			if (!tribeJoin(appkey, member, tribeId)) {
				tribeDismiss(appkey, host, tribeId);
				return false;
			}
		}
		if (!tribeSetManager(appkey, host, manager, tribeId)) {
			tribeDismiss(appkey, host, tribeId);
			return false;
		}
		return true;
	}

	// public static void createTribes(String appkey, String prefix, int count)
	// {
	// for (int i = 10; i < count; i++) {
	// // System.out.println(userUpdate(appkey, prefix+i, "taobao1234",
	// // prefix+i));
	// String host = String.format("%s%03d", prefix, i);
	// String manager = String.format("%s%03d", prefix, i + 1);
	// String normal = String.format("%s%03d", prefix, i + 2);
	// String tribeEndding = String.format("_%d_%d_%d", i, i + 1, i + 2);
	// System.out.println(i);
	// System.out.println(tribeCreateTribe(appkey, "群" + tribeEndding, host,
	// manager, Arrays.asList(normal)));
	// System.out.println(tribeCreateGroup(appkey, host, "讨" + tribeEndding,
	// "测试讨论组公告", manager, normal));
	// }
	// }

	// public static void userUpdate(String appkey, String prefix, int count) {
	// int length = mNicks.length < mAvatars.length ? mNicks.length :
	// mAvatars.length;
	// for (int i = 1; i < count; i++) {
	// String uid = prefix + i;
	// String nick = uid + "-" + mNicks[i % length];
	// String avatar = mAvatars[i % length];
	//
	// System.out.println(userUpdate(appkey, uid, "taobao1234", nick, avatar));
	// }
	// }

	/**
	 * taobao.openim.track.getsummary (获取用户足迹信息)
	 * 
	 * @param appkey
	 * @param uid
	 * @return
	 */
	public static JSONObject trackGet(String appkey, String uid) {
		OpenimTrackGetsummaryRequest req = new OpenimTrackGetsummaryRequest();
		req.setUid(uid);
		req.setTargetAppKey(appkey);
		try {
			JSONObject json = getJsonObject(execute(appkey, req), "openim_track_getsummary_response");
			if (json != null) {
				return json.getJSONObject("tracksummary");
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	// /**
	// *
	// 消息实时路由:http://baichuan.taobao.com/doc2/detail.htm?spm=0.0.0.0.KyUPd2&treeId=41&articleId=103623&docType=1
	// * 未经测试,另该功能开通需要联系 书通
	// * @param appkey
	// */
	// public static void msgComsume(String appkey) {
	// TmcClient client = sMapTmcClient.get(appkey);
	// client.setMessageHandler(new MessageHandler() {
	// public void onMessage(Message message, MessageStatus status) {
	// try {
	// System.out.println(message.getContent());
	// System.out.println(message.getTopic());
	// // 默认不抛出异常则认为消息处理成功
	// } catch (Exception e) {
	// e.printStackTrace();
	// status.fail();// 消息处理失败回滚，服务端需要重发
	// }
	// }
	// });
	// try {
	// System.out.println("connect start");
	// client.connect();
	// System.out.println("sleep start");
	// Thread.sleep(30000);
	// System.out.println("sleep end");
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// System.out.println("msgComsume End");
	// }

	public static void main(String[] args) {
		String appkey = "23015524";
		String uid1 = "dev" + System.currentTimeMillis();
		System.out.println(userAdd(appkey, uid1, "taobao1234"));
	}
}
