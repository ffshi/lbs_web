/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.web.weixin;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.ocs.OcsClient;
import com.aliyun.ocs.OcsException;
import com.aliyun.ocs.OcsOptions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.mapper.BeanMapper;
import com.innovate.cms.common.mapper.JsonMapper;
import com.innovate.cms.common.security.Cryptos;
import com.innovate.cms.common.security.Digests;
import com.innovate.cms.common.service.WeiXinService;
import com.innovate.cms.common.utils.AES;
import com.innovate.cms.common.utils.DateUtils;
import com.innovate.cms.common.utils.Encodes;
import com.innovate.cms.common.utils.ReUtil;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.aliIM.IMUtils;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.data.entity.LoginToJson;
import com.innovate.cms.modules.data.entity.PostLoginJson;
import com.innovate.cms.modules.data.entity.WXConfigToJson;
import com.innovate.cms.modules.qs.entity.user.SystemUser;
import com.innovate.cms.modules.qs.entity.user.SystemUserInfo;
import com.innovate.cms.modules.qs.service.user.SystemUserService;

/**
 * 微信账号管理Controller
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@Controller
@RequestMapping(value = "/api")
public class QxWeixinUserController extends BaseController {

	private static WXConfigToJson wToJson = new WXConfigToJson();
	private static Map<String, String> ret = Maps.newHashMap();

	@Autowired
	private SystemUserService systemUserService;

	/**
	 * 小程序用户登录
	 * 
	 * @param map
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/v1/xcx/login", method = RequestMethod.POST)
	public @ResponseBody BaseBackInfo delUserMsg(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {

		String encryptedData = map.get("encryptedData");
		String sessionKey = map.get("sessionKey");
		String iv = map.get("iv");
		String os = map.get("os");

		BaseBackInfo backInfo = new BaseBackInfo();
		if (StrUtil.isBlank(encryptedData) || StrUtil.isBlank(sessionKey) || StrUtil.isBlank(iv)) {
			BaseBackInfo info = new BaseBackInfo();
			info.setStateCode(Global.int300209);
			info.setRetMsg(Global.str300209);
			return info;
		}
		try {
			// 解密userinfo
			AES aes = new AES();
			byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
			if (null != resultByte && resultByte.length > 0) {
				String userInfo = new String(resultByte, "UTF-8");
				// System.out.println(userInfo);
				JSONObject userinfObject = JSONObject.parseObject(userInfo);
				PostLoginJson json = json2postLoginJson(userinfObject);
				json.setOs(os);
				return toLogin(json, request, response);
			}

			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		} catch (Exception e) {
			logger.debug("[" + Thread.currentThread().getStackTrace()[1].getClassName() + " - " + Thread.currentThread().getStackTrace()[1].getMethodName() + "()接口报错：{}]", e.getMessage());
			backInfo.setRetMsg(Global.ERROR);
			backInfo.setStateCode(Global.intNO);
		}
		return backInfo;
	}

	/**
	 * 对象转换
	 * 
	 * @param userinfObject
	 * @return
	 */
	private PostLoginJson json2postLoginJson(JSONObject userinfObject) {
		PostLoginJson postLoginJson = new PostLoginJson();
		postLoginJson.setCountry(userinfObject.getString("country"));
		postLoginJson.setUnionid(userinfObject.getString("unionId"));
		postLoginJson.setSex(userinfObject.getString("gender"));
		postLoginJson.setProvince(userinfObject.getString("province"));
		postLoginJson.setCity(userinfObject.getString("city"));
		postLoginJson.setHeadimgurl(userinfObject.getString("avatarUrl"));
		postLoginJson.setOpenid(userinfObject.getString("openId"));
		postLoginJson.setNickname(userinfObject.getString("nickName"));
		postLoginJson.setLang(userinfObject.getString("language"));
		postLoginJson.setUserType("7");
		postLoginJson.setConstellation("-1");
		return postLoginJson;
	}

	public BaseBackInfo toLogin(PostLoginJson postLoginJson, HttpServletRequest request, HttpServletResponse response) {
		DataBackInfo<LoginToJson> backInfo = new DataBackInfo<LoginToJson>();
		String sign_param = String.valueOf(System.currentTimeMillis());// 接收时间戳
		String body = "";// 初始化body体
		String secretKey = "";// 初始化秘钥
		String ip = StringUtils.getRemoteAddr(request);
		// 把用户传过来的时间戳返回去
		response.setHeader(Global.SIGN_PARAM, sign_param);

		// 理论上过滤器里面已经过滤，为了兼容关闭过滤器的情况
		if (StringUtils.isNotBlank(sign_param) && StrUtil.isNum(sign_param) && sign_param.length() == 13) {
			try {
				secretKey = Encodes.encodeHex(Digests.md5(sign_param.substring(3, 13).getBytes("utf-8"))).substring(8, 24);
			} catch (UnsupportedEncodingException e) {
				logger.debug("sign_param 参数错误-{}", e.getMessage());
				secretKey = "0020160005000100";
			}
		} else {
			logger.debug("sign_param 参数为空,或参数错误");
			backInfo.setStateCode(Global.int300202);
			backInfo.setRetMsg(Global.str300202);
			return backInfo;
		}
		// 如果post对象为空 返回错误信息
		if (postLoginJson == null) {
			logger.debug("postLoginJson 参数错误 - {}", postLoginJson);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			// body = JsonMapper.toJsonString(backInfo);
			// body = Cryptos.aesEncryptToBase64(body, secretKey,
			// Global.IV.getBytes());
			return backInfo;
		}
		logger.info(postLoginJson.toString());
		// 提取参数
		String unionid = postLoginJson.getUnionid(); // 获取用户个人信息（UnionID机制）
		String openid = postLoginJson.getOpenid(); // 当前来源渠道唯一标识
		String accessToken = postLoginJson.getAccessToken(); // SDK接口调用凭证 微信 微博
																// QQ公用
		String refreshToken = postLoginJson.getRefreshToken(); // SDK用户刷新access_token微信独享
		int expiresIn = postLoginJson.getExpiresIn(); // SDKaccess_token的生命周期，单位是秒数
		String nickname = postLoginJson.getNickname(); // 昵称
		String sex = postLoginJson.getSex(); // 用户的性别，值为1时是男性，值为2时是女性，默认值为0时是未知
		String constellation = postLoginJson.getConstellation(); // 星座
		Date birthday = postLoginJson.getBirthday(); // 生日
		String province = postLoginJson.getProvince(); // 归属省会
		String city = postLoginJson.getCity(); // 归属市级城市
		String country = postLoginJson.getCountry(); // 国家，如中国为CN
		String headimgurl = postLoginJson.getHeadimgurl(); // 用户头像,最后一个数值代表正方形头像大小
		String lang = postLoginJson.getLang(); // 返回国家地区语言版本，zh_CN 简体，zh_TW
												// 繁体，en 英语
		String os = postLoginJson.getOs(); // 用户客户端ios或者android
		String userType = postLoginJson.getUserType(); // 用户类型0、官方1、普通2、微信3、web微信4、QQ5、微博
		String loginName = postLoginJson.getLoginName(); // 登录名-可为账号
		String password = postLoginJson.getPassword(); // 密码
		String mobile = postLoginJson.getMobile(); // 手机-可为账号
		String version = postLoginJson.getVersion();
		// 简单校验 后期用 bean 校验
		if (StringUtils.isBlank(unionid) || StringUtils.isBlank(userType) || StringUtils.isBlank(os)) {
			logger.debug("SystemUserController - 参数错误：getUnionid()={}/getAccessToken()={}/getUserType()={}/getOs()={}/version={}", unionid, accessToken, userType, os, version);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
		} else {
			logger.debug("/v1/login校验完毕");
			// 是否为有效登陆 校验登陆串（待实现）默认无效
			boolean isAccountValid = false;
			// 用户类型 0、官方 1、普通 2、微信 3、web微信 4、QQ 5、微博
			switch (userType.charAt(0)) {
			case '0':
				// TODO 待实现 调用微信 接口确认 login_name + password合法
				isAccountValid = false;
				break;
			case '1':
				// TODO 待实现 调用微信 接口确认 mobile + password合法
				isAccountValid = true;
				break;
			case '2':
				// TODO 待实现 调用微信 接口确认 openid + accessToken合法
				isAccountValid = true;
				logger.debug("sdk微信用户登陆，校验成功...");
				break;
			case '3':
				// TODO 待实现 调用微信 接口确认 openid + accessToken合法
				isAccountValid = true;
				logger.debug("web微信用户登陆，校验成功...");
				break;
			case '4':
				// TODO 待实现 调用微信 接口确认 openid + accessToken合法
				isAccountValid = true;
				logger.debug("QQ用户登陆，校验成功...");
				break;
			case '5':
				// TODO 待实现 调用微信 接口确认 openid + accessToken合法
				isAccountValid = true;
				logger.debug("微博用户登陆，校验成功...");
				break;
			case '6':
				// TODO 待实现 调用微信 接口确认 openid + accessToken合法
				isAccountValid = true;
				logger.debug("QQ Web用户登陆，校验成功...");
				break;
			case '7':
				// TODO 待实现 调用微信 接口确认 openid + accessToken合法
				isAccountValid = true;
				logger.debug("小程序用户登陆，校验成功...");
				break;
			default:
				logger.debug("非法userType");
				isAccountValid = false;
				break;
			}

			// 如果校验成功
			if (isAccountValid) {
				LoginToJson loginToJson = new LoginToJson();
				// 生成用户本地token= md5（account + userType+ os + Global.TOKEN_KEY +
				// DateUtils.getDateTime()） 不管用户是否存在都要先生成 token
				String tokenLocal = Digests.md5(unionid + userType + os + Global.TOKEN_KEY + DateUtils.getDateTime());
				logger.debug("为用户生成了新token={}", tokenLocal);
				SystemUser systemUser = new SystemUser();
				SystemUserInfo systemUserInfo = new SystemUserInfo();
				// 校验用户是否存在
				switch (userType.charAt(0)) {
				case '0':
					systemUser = systemUserService.getUserByLoginName(loginName, password);
					break;
				case '1':
					systemUser = systemUserService.getUserByMobile(mobile, password);
					break;
				case '2':
				case '3':
				case '4':
				case '5':
				case '7':
					systemUser = systemUserService.getUserByThreeUnionid(unionid);
					break;
				default:
					// 其他情况在外层已经过滤
					break;
				}

				List<LoginToJson> data = Lists.newArrayList();
				// 如果用户 存在----则重新生成token 带上 accessToken,os 更新用户信息 并返回用户带新token数据
				if (systemUser != null) {
					// 主对象
					systemUser.setTokenLocal(tokenLocal);
					systemUser.setOs(os);
					// 子对象 中 unionid、openid不必更新 用uid即可更新
					systemUserInfo.setUid(systemUser.getUid());
					if (userType.charAt(0) == '3') {
						// web微信登陆
						systemUserInfo.setWebAccessToken(accessToken);
						systemUserInfo.setWebExpiresIn(expiresIn);
						systemUserInfo.setWebRefreshToken(refreshToken);
					} else {
						// 其他的都统一保存在这些字段
						systemUserInfo.setAccessToken(accessToken);
						systemUserInfo.setExpiresIn(expiresIn);
						systemUserInfo.setRefreshToken(refreshToken);
					}
					systemUserInfo.setLoginIp(ip);
					systemUserInfo.setLoginDate(new Date());

					// 放到同一事物内部提交 判断是否存在 Uid来确认是否为新数据
					String _uid = systemUserService.saveUserAndInfo(systemUser, systemUserInfo);
					// 更新阿里IM用户
					try {
						if (StrUtil.isNotBlank(_uid) && "1".equals(Global.getSycnAliIM())) {
							IMUtils.updateUser(systemUser);
						}
					} catch (Exception e) {
						logger.debug("/v1/login IMUtils.updateUser(systemUser)出错 {}", e.getMessage());
					}
					// copy
					BeanMapper.copy(systemUser, loginToJson);
					loginToJson.setToken(tokenLocal);
					data.add(loginToJson);// 添加到集合
					// 添加到返回对象
					backInfo.setStateCode(Global.intYES);
					backInfo.setRetMsg(Global.SUCCESS);
					backInfo.setData(data);
					// 登陆返回信息加密操作
					body = JsonMapper.toJsonString(backInfo);
					logger.debug("backBody = {}", body);
					body = Cryptos.aesEncryptToBase64(body, secretKey, Global.IV.getBytes());
				} else {
					// 主对象-----如果不存在 ---new一个新对象 -新增一个用户
					systemUser = new SystemUser(unionid, openid, nickname, sex, constellation, birthday, province, city, country, headimgurl, lang, os, userType, new Date());
					systemUser.setTokenLocal(tokenLocal);// 给新对象赋值token

					if (userType.charAt(0) == '3') {
						systemUserInfo = new SystemUserInfo(unionid, openid, accessToken, refreshToken, expiresIn, ip, new Date(), new Date());
					} else {
						systemUserInfo = new SystemUserInfo(unionid, openid, accessToken, refreshToken, expiresIn, ip, new Date());
					}
					// 放到同一事物内部提交 先保存用户获得UID后在保存子对象
					String _uid = systemUserService.saveUserAndInfo(systemUser, systemUserInfo);
					// 同步阿里IM用户
					try {
						if (StrUtil.isNotBlank(_uid) && "1".equals(Global.getSycnAliIM())) {
							systemUser.setUid(_uid);
							IMUtils.addUser(systemUser);
						}
					} catch (Exception e) {
						logger.debug("/v1/login IMUtils.addUser(systemUser)出错 {}", e.getMessage());
					}
					// copy
					BeanMapper.copy(systemUser, loginToJson);
					loginToJson.setToken(tokenLocal);
					// 添加到集合
					data.add(loginToJson);
					backInfo.setStateCode(Global.intYES);
					backInfo.setRetMsg(Global.SUCCESS);
					backInfo.setData(data);
					// 登陆返回信息加密操作
					// body = JsonMapper.toJsonString(backInfo);
					// System.out.println(backInfo);
					// logger.debug("backBody = {}", body);
					// body = Cryptos.aesEncryptToBase64(body, secretKey,
					// Global.IV.getBytes());
				}
			} else {
				logger.debug("账号或者密码不正确 userType={}", userType);
				backInfo.setStateCode(Global.int3002041);
				backInfo.setRetMsg(Global.str3002041);
				// body = JsonMapper.toJsonString(backInfo);
				// body = Cryptos.aesEncryptToBase64(body, secretKey,
				// Global.IV.getBytes());
			}
		}
		// logger.debug("加密后字符串 = {}", body);
		return backInfo;
	}

	/**
	 * 
	 * @Title: getWXConfig
	 * @Description: 微信分享
	 * @param request
	 * @param response
	 * @return 返回类型 String
	 *
	 */
	@CrossOrigin
	@RequestMapping(value = "/v1/getWXConfig", method = RequestMethod.GET)
	// public @ResponseBody String getWXConfig(HttpServletRequest request,
	// HttpServletResponse response) {
	public @ResponseBody BaseBackInfo getWXConfig(HttpServletRequest request, HttpServletResponse response) {
		String callback = request.getParameter("callback");
		String url = request.getParameter("url");
		DataBackInfo<WXConfigToJson> backInfo = new DataBackInfo<WXConfigToJson>();
		if (StrUtil.isBlank(url) || StrUtil.isBlank(callback)) {
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
		} else {
			String ACCESSTOKEN = "";
			String JSAPITICKET = "";
			List<WXConfigToJson> data = Lists.newArrayList();
			// WXConfigToJson wToJson = new WXConfigToJson();
			OcsClient ocs = Global.getOcsClient();
			OcsOptions options = Global.getOcsOptions();
			int num = Global.getLength();

			logger.debug("ocs=[{}],options=[{}],num[{}]", ocs, options, num);
			try {
				ACCESSTOKEN = (String) ocs.syncGet(Global.ACCESSTOKEN + num, options).getValue();
				JSAPITICKET = (String) ocs.syncGet(Global.JSAPITICKET + num, options).getValue();
				logger.debug("ACCESSTOKEN=[{},{}],JSAPITICKET=[{},{}]", Global.ACCESSTOKEN + num, ACCESSTOKEN, Global.JSAPITICKET + num, JSAPITICKET);
				logger.debug("getError:[{}],getStatus[{}]", ocs.syncGet(Global.JSAPITICKET + num, options).getError(), ocs.syncGet(Global.JSAPITICKET + num, options).getStatus());
			} catch (OcsException e1) {
				logger.debug("获取值错误：", e1.getMessage());
			}
			ret = WeiXinService.sign(JSAPITICKET, url);
			wToJson.setUrl(url);
			try {
				if (ret.size() > 0) {
					BeanMapper.copy(ret, wToJson);
					logger.debug("BeanMapper.copy:{}复制成功", wToJson.toString());
				} else {
					logger.debug("ret:{}参数不全", ret.toString());
				}
			} catch (Exception e) {
				logger.debug("BeanMapper.copy:{}失败", e.getMessage());
				backInfo.setStateCode(Global.int300301);
				backInfo.setRetMsg(Global.str300301);
			}
			// 添加对象
			data.add(wToJson);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(data);
		}
		//jsonp格式返回
		// return JsonMapper.getInstance().toJsonP(callback, backInfo);
		return backInfo;
	}

	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	public @ResponseBody BaseBackInfo exp(HttpMessageNotReadableException ex) {
		String retMsg = ReUtil.extractMulti("(^.*)\\n at (\\[.*$)", ex.getMessage().substring(0, 150), "$1");

		BaseBackInfo backError = new BaseBackInfo();
		if (StrUtil.isEmpty(retMsg)) {
			logger.info("参数错误" + retMsg);
			retMsg = Global.ERROR;
		}
		backError.setRetMsg(retMsg);
		backError.setStateCode(Global.int300301);
		return backError;
	}
}