/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.web.user;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.mapper.BeanMapper;
import com.innovate.cms.common.mapper.JsonMapper;
import com.innovate.cms.common.security.Cryptos;
import com.innovate.cms.common.security.Digests;
import com.innovate.cms.common.utils.DateUtils;
import com.innovate.cms.common.utils.Encodes;
import com.innovate.cms.common.utils.ReUtil;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.aliIM.IMUtils;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.data.entity.IsLogoutToJson;
import com.innovate.cms.modules.data.entity.IsTokenToJson;
import com.innovate.cms.modules.data.entity.LoginToJson;
import com.innovate.cms.modules.data.entity.PostLoginJson;
import com.innovate.cms.modules.qs.entity.user.SystemUser;
import com.innovate.cms.modules.qs.entity.user.SystemUserInfo;
import com.innovate.cms.modules.qs.service.user.SystemUserService;

/**
 * 系统用户表管理
 * 
 * @ClassName: SystemUserController
 * @Description: 用户表管理
 * @author gaoji gaoji_cyou-inc_com
 * @date 2016年4月12日 下午4:49:01
 *
 */
@Controller
@RequestMapping(value = "/api")
public class SystemUserController extends BaseController {

	@Autowired
	private SystemUserService systemUserService;

	/**
	 * 
	 * @Title: toLogin
	 * @Description: 用户注册登录一体
	 * @param postLoginJson
	 * @param request
	 * @param response
	 * @return 返回类型 String
	 *
	 */
	@RequestMapping(value = "/v1/login", method = RequestMethod.POST)
	public @ResponseBody String toLogin(@RequestBody PostLoginJson postLoginJson, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("enter /v1/login....");
		DataBackInfo<LoginToJson> backInfo = new DataBackInfo<LoginToJson>();
		String sign_param = request.getHeader(Global.SIGN_PARAM);// 接收时间戳
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
			return JsonMapper.toJsonString(backInfo);
		}
		// 如果post对象为空 返回错误信息
		if (postLoginJson == null) {
			logger.debug("postLoginJson 参数错误 - {}", postLoginJson);
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
			body = JsonMapper.toJsonString(backInfo);
			body = Cryptos.aesEncryptToBase64(body, secretKey, Global.IV.getBytes());
			return body;
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
		if (StringUtils.isBlank(unionid) || StringUtils.isBlank(accessToken) || StringUtils.isBlank(userType) || StringUtils.isBlank(os) || !"1".equals(version)) {
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
					body = JsonMapper.toJsonString(backInfo);
					logger.debug("backBody = {}", body);
					body = Cryptos.aesEncryptToBase64(body, secretKey, Global.IV.getBytes());
				}
			} else {
				logger.debug("账号或者密码不正确 userType={}", userType);
				backInfo.setStateCode(Global.int3002041);
				backInfo.setRetMsg(Global.str3002041);
				body = JsonMapper.toJsonString(backInfo);
				body = Cryptos.aesEncryptToBase64(body, secretKey, Global.IV.getBytes());
			}
		}
		logger.debug("加密后字符串 = {}", body);
		return body;
	}

	/**
	 * 退出登录
	 * 
	 * @Title: toLogout
	 * @Description: 退出登录
	 * @param map
	 * @param request
	 * @param response
	 * @return 返回类型 IsLogoutToJson
	 *
	 */
	@RequestMapping(value = "/v1/logout", method = RequestMethod.POST)
	public @ResponseBody IsLogoutToJson toLogout(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		IsLogoutToJson isLogoutToJson = new IsLogoutToJson();
		String uid = request.getHeader("Uid");
		String token = map.get("token");
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(token) || uid.trim().length() != 32) {
			logger.debug("jsonData中参数错误-{} - {} - {}", uid, token, uid.trim().length());
			isLogoutToJson.setStateCode(Global.int300209);
			isLogoutToJson.setRetMsg(Global.str300209);
		} else {
			// 默认无效
			Integer isLogout = Global.iNO;
			try {
				// 根据UID查询token
				SystemUser systemUser = systemUserService.getToken(uid);
				if (systemUser != null) {
					// 如果token对比成功 则退出用户
					if (Digests.md5(systemUser.getTokenLocal()).equalsIgnoreCase(token)) {
						systemUser.setUid(uid);
						systemUser.setTokenLocal("");
						// 退出登录
						isLogout = systemUserService.logout(systemUser);
						isLogoutToJson.setIsLogout(isLogout);

						isLogoutToJson.setStateCode(Global.intYES);
						isLogoutToJson.setRetMsg(Global.SUCCESS);
					} else {
						logger.debug("Token失效- {}", token);
						isLogoutToJson.setStateCode(Global.int300207);
						isLogoutToJson.setRetMsg(Global.str300207);
					}
				} else {
					logger.debug("User不存在- {}", uid);
					isLogoutToJson.setStateCode(Global.int300204);
					isLogoutToJson.setRetMsg(Global.str300204);
				}
			} catch (Exception e) {
				logger.debug("SystemUserController - toLogout- {}", e.getMessage());
				isLogoutToJson.setStateCode(Global.int300302);
				isLogoutToJson.setRetMsg(Global.str300302);
			}
		}
		return isLogoutToJson;
	}

	/**
	 * 
	 * @Title: isToken
	 * @Description: 校验token是否有效
	 * @param map
	 * @param request
	 * @param response
	 * @return 返回类型 IsTokenToJson
	 *
	 */
	@RequestMapping(value = "/v1/isToken", method = RequestMethod.POST)
	public @ResponseBody IsTokenToJson isToken(@RequestBody Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
		IsTokenToJson backInfo = new IsTokenToJson();
		String uid = request.getHeader("Uid");
		String token = map.get("token");
		if (StrUtil.isBlank(uid) || StrUtil.isBlank(token) || uid.trim().length() != 32) {
			logger.debug("jsonData中参数错误-{} - {} - {}", uid, token, uid.trim().length());
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
		} else {
			// 默认无效
			Integer isToken = Global.iNO;
			try {
				// 根据UID查询token
				SystemUser systemUser = systemUserService.getToken(uid);
				if (systemUser != null) {
					// 加密查询后的token后跟map.get("token")对比
					isToken = Digests.md5(systemUser.getTokenLocal()).equals(token) ? Global.iYES : Global.iNO;
				}
			} catch (Exception e) {
				logger.debug("SystemUserController - isToken- {}", e.getMessage());
				backInfo.setStateCode(Global.int300302);
				backInfo.setRetMsg(Global.str300302);
			}

			backInfo.setIsToken(isToken);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
		}
		return backInfo;
	}

	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	public @ResponseBody BaseBackInfo exp(HttpMessageNotReadableException ex) {
		String retMsg = ReUtil.extractMulti("(^.*)\\n at (\\[.*$)", ex.getMessage().substring(0, 150), "$1");

		BaseBackInfo backError = new BaseBackInfo();
		if (StrUtil.isEmpty(retMsg)) {
			logger.info("参数错误" + retMsg + "	ex:" + ex.getMessage());
			retMsg = Global.ERROR;
		}
		backError.setRetMsg(retMsg);
		backError.setStateCode(Global.int300301);
		return backError;
	}
}