package com.innovate.cms.modules.aliIM;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import qselect_3_web.IMConfig;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.innovate.cms.common.mapper.JsonMapper;
import com.innovate.cms.common.security.Digests;
import com.innovate.cms.modules.qs.entity.user.SystemUser;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.TaobaoRequest;
import com.taobao.api.TaobaoResponse;
import com.taobao.api.domain.OpenImUser;
import com.taobao.api.domain.TribeInfo;
import com.taobao.api.domain.Userinfos;
import com.taobao.api.request.OpenimTribeGetalltribesRequest;
import com.taobao.api.request.OpenimUsersAddRequest;
import com.taobao.api.request.OpenimUsersDeleteRequest;
import com.taobao.api.request.OpenimUsersGetRequest;
import com.taobao.api.request.OpenimUsersUpdateRequest;
import com.taobao.api.response.OpenimTribeGetalltribesResponse;
import com.taobao.api.response.OpenimUsersAddResponse;
import com.taobao.api.response.OpenimUsersDeleteResponse;
import com.taobao.api.response.OpenimUsersUpdateResponse;

/**
 * 阿里云 im用户导入
 * 
 * 单个/批量用户的增删改查
 * 
 * @author shifangfang
 * @date 2016年7月22日 上午11:19:16
 */
public class IMUtils {

	protected static Logger logger = LoggerFactory.getLogger(IMUtils.class);

	public static final TaobaoClient client = new DefaultTaobaoClient(IMConfig.Url, IMConfig.APPKEY, IMConfig.AppSecret);

	/**
	 * 删除用户
	 * 
	 * @param user
	 * @return
	 */
	public static String delUser(SystemUser user) {
		OpenimUsersDeleteRequest req = new OpenimUsersDeleteRequest();
		req.setUserids(user.getUid());
		OpenimUsersDeleteResponse rsp;
		logger.info("删除用户：" + JsonMapper.toJsonString(user));
		try {
			rsp = client.execute(req);
			return rsp.getBody();
		} catch (ApiException e) {
			e.printStackTrace();
			return "del IMUser error !";
		}
	}

	/**
	 * 添加单个趣选用户到im系统
	 * 
	 * @param user
	 * @return
	 */
	public static String addUser(SystemUser user) {
		return addIMUser(suser2userinfo(user));
	}

	/**
	 * 更新单个趣选用户到im系统
	 * 
	 * @param user
	 * @return
	 */
	public static String updateUser(SystemUser user) {
		return updateIMUser(suser2userinfo(user));
	}

	/**
	 * 添加批量趣选用户到im系统
	 * 
	 * @param users
	 * @return
	 */
	public static String addUsers(List<SystemUser> users) {
		List<Userinfos> usersinfosList = new ArrayList<Userinfos>();
		for (SystemUser user : users) {
			usersinfosList.add(suser2userinfo(user));
		}
		return addIMUsers(usersinfosList);
	}

	/**
	 * 更新批量趣选用户到im系统
	 * 
	 * @param users
	 * @return
	 */
	public static String updateUsers(List<SystemUser> users) {
		List<Userinfos> usersinfosList = new ArrayList<Userinfos>();
		for (SystemUser user : users) {
			usersinfosList.add(suser2userinfo(user));
		}
		return updateIMUsers(usersinfosList);
	}

	/**
	 * 趣选用户转换为aliIM用户
	 * 
	 * @param user
	 * @return
	 */
	private static Userinfos suser2userinfo(SystemUser user) {
		Userinfos userinfos = new Userinfos();

		userinfos.setUserid(user.getUid());
		userinfos.setPassword(getPwd(user.getUid()));
		userinfos.setName(user.getNickname());
		userinfos.setNick(user.getNickname());
		userinfos.setIconUrl(user.getHeadimgurl());
		userinfos.setEmail(user.getEmail());
		// 值为1时是男性M，值为2时是女性F
		if ("1".equals(user.getSex())) {
			userinfos.setGender("M");
		} else if ("2".equals(user.getSex())) {
			userinfos.setGender("F");
		}

		userinfos.setMobile(user.getMobile());

		// 添加额外信息
		userinfos.setExtra(JsonMapper.toJsonString(user));

		return userinfos;
	}

	private static String addIMUser(Userinfos user) {
		List<Userinfos> imUsers = new ArrayList<Userinfos>();
		imUsers.add(user);
		return addIMUsers(imUsers);
	}

	private static String updateIMUser(Userinfos user) {
		List<Userinfos> imUsers = new ArrayList<Userinfos>();
		imUsers.add(user);
		return updateIMUsers(imUsers);
	}

	private static String addIMUsers(List<Userinfos> users) {
		OpenimUsersAddRequest req = new OpenimUsersAddRequest();

		OpenimUsersAddResponse rsp;
		try {
			logger.info("添加用户：" + JsonMapper.toJsonString(users));
			req.setUserinfos(users);
			rsp = client.execute(req);
			logger.info("添加用户结果：" + rsp.getBody());
			return rsp.getBody();
		} catch (ApiException e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 批量更新用户信息
	 * 
	 * @param users
	 * @return
	 */
	private static String updateIMUsers(List<Userinfos> users) {

		OpenimUsersUpdateRequest req = new OpenimUsersUpdateRequest();
		OpenimUsersUpdateResponse rsp;
		try {
			req.setUserinfos(users);
			rsp = client.execute(req);
			return rsp.getBody();
		} catch (ApiException e) {

			e.printStackTrace();
			return "error";
		}
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
		// TaobaoClient client = getClient(appkey);
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
	 * im密码生成策略
	 * 
	 * @param uid
	 * @return
	 */
	public static String getPwd(String uid) {
		return Digests.md5(uid + "qx" + uid);
	}

	/**
	 * 获取用户所属群组列表
	 */
	public static long[] getAllTribesId(String uid) {
		// TaobaoClient client = new DefaultTaobaoClient(IMConfig.Url,
		// IMConfig.APPKEY, IMConfig.AppSecret);
		OpenimTribeGetalltribesRequest req = new OpenimTribeGetalltribesRequest();
		OpenImUser obj1 = new OpenImUser();
		obj1.setUid(uid);
		obj1.setTaobaoAccount(false);
		obj1.setAppKey(IMConfig.APPKEY);
		req.setUser(obj1);
		req.setTribeTypes("0,1");
		OpenimTribeGetalltribesResponse rsp;
		long[] ids = new long[] {};
		try {
			rsp = client.execute(req);
			List<TribeInfo> infos = rsp.getTribeInfoList();
			ids = new long[infos.size()];
			for (int i = 0; i < infos.size(); i++) {
				ids[i] = infos.get(i).getTribeId();
			}
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return ids;
	}

}
