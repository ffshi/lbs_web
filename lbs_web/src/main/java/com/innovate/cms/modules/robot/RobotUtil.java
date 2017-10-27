package com.innovate.cms.modules.robot;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 机器人业务相关： 1：机器人注册 2：机器人发消息 3：机器人点赞他人 4：机器人评论他人 5：机器人关注他人
 * 
 * @author shifangfang
 * @date 2017年10月27日 上午9:57:46
 */
public class RobotUtil {
	static String robotUserPath = "robotUser.txt";
	static String dynamicMsgPath = "dynamicMsg.txt";
	static String uidPath = "robotUid.txt";
	static String commentPath = "robotComment.txt";

	static String httpLocalPre = "http://localhost:8080/lbs_web/api/v1/";
	static String httpTestPre = "http://123.56.215.22/api/v1/";
	static String httpLinePre = "http://60.205.13.131/api/v1/";

	// static String httpLoginUrl = httpLocalPre + "login";
	static String httpLoginUrl = httpTestPre + "login";
	// static String httpLoginUrl = httpLinePre + "login";

	// static String httpSaveMsgUrl = httpLocalPre + "msg/save";
	static String httpSaveMsgUrl = httpTestPre + "msg/save";

	// static String httpSaveMsgUrl = httpLinePre + "msg/save";

	static List<String> robotUids = null;
	static List<String> robotComments = null;
	static {
		// System.out.println(RobotUtil.class.getResource("/").getPath());
		try {
			robotUids = FileUtils.readLines(new File(RobotUtil.class.getResource("/").getPath() + uidPath), Charset.forName("utf-8"));
			robotComments = FileUtils.readLines(new File(RobotUtil.class.getResource("/").getPath() + commentPath), Charset.forName("utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		// robotRegister();
		// saveMsg();

		List<String> randomUids = radomNumUid(2);
		System.out.println(randomUids.toString());
		List<String> randomComments = radomNumComments(2);
		System.out.println(randomComments.toString());

	}

	/**
	 * 随机出num个机器人uid
	 * 
	 * @param num
	 * @return
	 */
	public static List<String> radomNumUid(int num) {
		return randomList(num, robotUids);
	}

	/**
	 * 随机出来num个评论
	 * 
	 * @param num
	 * @return
	 */
	public static List<String> radomNumComments(int num) {
		return randomList(num, robotComments);
	}

	/**
	 * 机器人消息生成
	 */
	public static void saveMsg() {
		try {

			List<String> userLines = FileUtils.readLines(new File(dynamicMsgPath), Charset.forName("utf-8"));
			int size = userLines.size();

			List<String> rList = randomList(size, robotUids);
			for (int i = 0; i < userLines.size(); i++) {
				JSONObject paramJsonObject = geneDynamicMsg(userLines.get(i), rList.get(i));
				doPost(httpSaveMsgUrl, paramJsonObject);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 机器人用户注册
	 */
	public static void robotRegister() {
		try {

			List<String> userLines = FileUtils.readLines(new File(robotUserPath), Charset.forName("utf-8"));
			for (String line : userLines) {

				JSONObject paramJsonObject = geneUserParam(line);
				doPost(httpLoginUrl, paramJsonObject);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构建DynamicMs请求参数
	 * 
	 * @return
	 */
	public static JSONObject geneDynamicMsg(String line, String uid) {
		if (null == line) {
			return null;
		}
		String[] lines = line.split("\t");
		if (lines.length == 6) {
			JSONObject params = new JSONObject();
			params.put("msgType", lines[0]);
			params.put("msg2levelType", lines[1]);
			params.put("msgSingleType", lines[2]);
			params.put("description", lines[3]);
			params.put("pics", lines[4]);
			params.put("isVirtual", 1);
			params.put("uid", uid);
			params.put("location", getRandom(500, 5000));

			return params;
		} else {
			return null;
		}
	}

	/**
	 * 构建login请求参数
	 * 
	 * @return
	 */
	public static JSONObject geneUserParam(String line) {
		if (null == line) {
			return null;
		}
		String[] lines = line.split("\t");
		if (lines.length == 11) {
			JSONObject params = new JSONObject();
			params.put("nickname", lines[0]);
			params.put("sex", lines[1]);
			params.put("mobile", lines[2]);
			params.put("password", lines[3]);
			params.put("headimgurl", lines[4]);
			params.put("personalSignature", lines[5]);
			params.put("backgroundImage", lines[6]);
			params.put("isVirtual", lines[7]);
			params.put("userType", 1);
			params.put("country", lines[9]);
			params.put("lang", lines[10]);

			params.put("unionid", UUID.randomUUID().toString().replace("-", ""));
			params.put("accessToken", "accessToken");
			params.put("os", "robot");
			params.put("version", "0");

			return params;
		} else {
			return null;
		}
	}

	public static JSONObject doPost(String url, JSONObject json) {

		CloseableHttpClient client = HttpClients.createDefault();

		HttpPost post = new HttpPost(url);

		JSONObject response = null;
		CloseableHttpResponse res = null;
		post.setHeader("Content-Type", "application/json");
		post.setHeader("_", "ffshi");
		post.setHeader("Sign-Param", "1111111111111");
		post.setHeader("Uid", json.getString("uid"));
		try {
			StringEntity s = new StringEntity(json.toJSONString(), Charset.forName("utf-8"));
			s.setContentEncoding("utf-8");
			s.setContentType("application/json");// 发送json数据需要设置contentType
			post.setEntity(s);
			res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(res.getEntity());// 返回json格式：
				System.out.println(result.toString());
				// response = JSONObject.parseObject(result);
				// System.out.println(response.toString());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				res.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	public static void testHttpclient() {

	}

	/**
	 * 从集合中随机取出num个元素
	 * 
	 * @param num
	 * @param maxValue
	 * @return
	 */
	public static List<String> randomList(int num, List<String> list) {
		List<String> res = new ArrayList<>(num);
		Collections.shuffle(list);

		for (int i = 0; i < num; i++) {
			res.add(list.get(i));
		}

		return res;
	}

	public static String getRandom(int min, int max) {
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return String.valueOf(s);

	}

}
