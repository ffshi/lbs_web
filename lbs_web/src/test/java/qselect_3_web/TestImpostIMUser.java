package qselect_3_web;

import java.util.ArrayList;
import java.util.List;

import com.innovate.cms.common.security.Digests;
import com.innovate.cms.modules.aliIM.IMUtils;
import com.innovate.cms.modules.qs.entity.user.SystemUser;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Userinfos;
import com.taobao.api.request.OpenimUsersAddRequest;
import com.taobao.api.response.OpenimUsersAddResponse;

public class TestImpostIMUser {

	public static void main(String[] args) throws ApiException {
		// testAddUser();
		// OpenIMTopImpl.userGet(IMConfig.APPKEY,
		// "01f6d764622b497b9a6306aa853ddb9b");
//		System.out.print(OpenIMTopImpl.userGet(IMConfig.APPKEY, "4843e6b5da804baca0dd6a4c02c301a2").toJSONString());
		System.out.print(OpenIMTopImpl.userGet(IMConfig.APPKEY, "7ca5f2ef67214522a167256b800dfa00").toJSONString());

		// String string = JsonMapper.toJsonString(buildUser("123456", "xixixi",
		// "http://wx.qlogo.cn/mmopen/cbVfodwKzFHozhDuMeMmVDydxYhkhjlxdib6x5D70wibEow5iaXxXO0k6tYuibMic207rSnZoaOeuhWqRLaF7PjicWxhHiagYLSR27ic/0"));
		//
		// SystemUser user = new SystemUser();
		//
		// System.out.println(JsonMapper.toJsonString(user));
		
//		testAddUser();
	}

	public static void testAddUser() {
		TaobaoClient client = new DefaultTaobaoClient(IMConfig.Url, IMConfig.APPKEY, IMConfig.AppSecret);
		OpenimUsersAddRequest req = new OpenimUsersAddRequest();
		List<Userinfos> list2 = new ArrayList<Userinfos>();
		// Userinfos obj3 = new Userinfos();
		// list2.add(obj3);
		// obj3.setNick("king");
		// obj3.setIconUrl("http://xxx.com/xxx");
		// obj3.setEmail("uid@taobao.com");
		// obj3.setMobile("18600000000");
		// obj3.setTaobaoid("tbnick123");
		// obj3.setUserid("imuser123");
		// obj3.setPassword("xxxxxx");
		// obj3.setRemark("demo");
		// obj3.setExtra("{}");
		// obj3.setCareer("demo");
		// obj3.setVip("{}");
		// obj3.setAddress("demo");
		// obj3.setName("demo");
		// obj3.setAge(123L);
		// obj3.setGender("M");
		// obj3.setWechat("demo");
		// obj3.setQq("demo");
		// obj3.setWeibo("demo");

		 list2.add(buildUser("123456", "xixixi",
		 "http://wx.qlogo.cn/mmopen/cbVfodwKzFHozhDuMeMmVDydxYhkhjlxdib6x5D70wibEow5iaXxXO0k6tYuibMic207rSnZoaOeuhWqRLaF7PjicWxhHiagYLSR27ic/0"));
		  list2.add(buildUser("10098eee06bb45f386f94a797d51ef48", "刘旭昶",
		 
		 "http://wx.qlogo.cn/mmopen/sFPcIoESYHF4RtAIacwJfv8dTBBmhpO2cZKU0pRhG34zAE53MZv3icx2XkWGyuwQDTufL60MjhpZI7T3Mn3JicFibPYdKEh16eW/0"));
		  list2.add(buildUser("1139ae33b610470c8d43fe563bfcca33", "影流",
		 
		 "http://q.qlogo.cn/qqapp/1104974870/681475DDC4893FD6AD60CF79DFC8C1B3/100"));
		  list2.add(buildUser("189121fb8c0b479f88bbaec1f3d820fe", "亮亮の亮",
		 
		 "http://wx.qlogo.cn/mmopen/icTdbqWNOwNROZc5exBH6oOdwba21vyGJ4M7EYPWYgbvVhRPMr2aqvEvQApAjXTOKWBSIblT5MjLDUB5iaUuRIppFoW3Kv9UeL/0"));
		 req.setUserinfos(list2);
		 OpenimUsersAddResponse rsp;
		 try {
		 rsp = client.execute(req);
		 System.out.println(rsp.getBody());
		 } catch (ApiException e) {
		 e.printStackTrace();
		 }

		System.out.println(IMUtils.addUser(new SystemUser()));
	}

	/**
	 * name一律跟nickname一致
	 * 
	 * password暂时都是
	 * 
	 * @param uid
	 * @param nickName
	 * @param iconUrl
	 * @return
	 */
	public static Userinfos buildUser(String uid, String nickName, String iconUrl) {
		Userinfos info = new Userinfos();
		info.setUserid(uid);
		info.setNick(nickName);
		info.setName(nickName);
		info.setIconUrl(iconUrl);
		info.setPassword(Digests.md5(uid + uid + uid));
		// info.setPassword("abcd.1234");
		return info;
	}

}
