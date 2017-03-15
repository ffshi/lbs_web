package com.innovate.cms.modules.qs.web.qq;

import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.mapper.JsonMapper;
import com.innovate.cms.common.utils.HttpClientUtil;
import com.innovate.cms.common.utils.ReUtil;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.aliIM.IMUtils;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.data.entity.QQWebLoginToJson;
import com.innovate.cms.modules.qs.entity.user.SystemUser;
import com.innovate.cms.modules.qs.entity.user.SystemUserInfo;
import com.innovate.cms.modules.qs.service.user.SystemUserService;

/**
 * 
 * @ClassName: QxQQUserController
 * @Description: QQ互联
 * @author gaoji gaoji_cyou-inc_com
 * @date 2016年8月29日 下午12:09:06
 *
 */
@Controller
@RequestMapping(value = "/api")
public class QxQQUserController extends BaseController
{
	private String accessToken = "";
	private String expireIn = "";
	private String refreshToken = "";
	private String openid = "";
	private String oauth_consumer_key = "";
	@Autowired
	private SystemUserService systemUserService;


	@RequestMapping(value = "/v2/oauth2/qq", method = RequestMethod.GET)
	public @ResponseBody String getOauth2ByQQ(HttpServletRequest request, HttpServletResponse response)
	{
		String callback = request.getParameter("callback");
		String code = request.getParameter(Global.QQ_CODE);
		@SuppressWarnings("unused")
		String state = request.getParameter(Global.QQ_STATE);
		//String req_state = (String) request.getSession().getAttribute("REQ_STATE");
		String ip = StringUtils.getRemoteAddr(request);	
		String lang = Global.WX_ZH_CN; // 默认
		String user_country = "CN";
		BaseBackInfo qqBackInfo = new BaseBackInfo();

		if (StrUtil.isBlank(callback)){
			callback = "callback";
			return checkBackJson(callback, qqBackInfo,Global.intNO,"qq授权..缺少callback参数");
		} else if (StrUtil.isBlank(code)){
			return checkBackJson(callback, qqBackInfo,Global.intNO,"qq授权..缺少code参数");
		} else
		{
			String accessTokenStr = HttpClientUtil.doGet(Global.getMethodGetUrl(Global.getConfig("qq_accessTokenURL"), getTokenParams(code)));		
			JSONObject jsonATObj = parseObjectByQQ(accessTokenStr);			 
			if (null!=jsonATObj)
			{
				logger.debug("qq授权-doGet-qq_accessTokenURL-返回错误{}", accessTokenStr);
				return JsonMapper.getInstance().toJsonP(callback, jsonATObj);
			}else
			{
				logger.debug("qq授权-doGet-qq_accessTokenURL-返回正常值{}", accessTokenStr);
				String openidStr = HttpClientUtil.doGet(Global.getMethodGetUrl(Global.getConfig("qq_getOpenIDURL"),getOpenidParams(accessTokenStr)));
				JSONObject jsonOpenidObj = parseObjectByQQ(openidStr);
				if (null!=jsonOpenidObj)
				{
					// 判断是否有  error 参数
					if (StrUtil.isNotBlank(jsonOpenidObj.getString(Global.QQ_ERRCODE)))
					{
						logger.debug("qq授权-doGet-qq_getOpenIDURL-返回错误{}", openidStr);
						return JsonMapper.getInstance().toJsonP(callback, jsonOpenidObj);
					}else {
						logger.debug("qq授权-doGet-qq_getOpenIDURL-返回正常{}", openidStr);
						String userInfoStr = HttpClientUtil.doGet(Global.getMethodGetUrl(Global.getConfig("qq_getUserInfoURL"),getUserInfoParams(jsonOpenidObj)));
						JSONObject jsonUserInfoObj = JSONObject.parseObject(userInfoStr);
						//判断返回码是否为0，非0为成功
						if (0 != Integer.parseInt(jsonUserInfoObj.getString("ret")))
						{
							logger.debug("qq授权-doGet-qq_getUserInfoURL-返回错误{}", userInfoStr);
							return JsonMapper.getInstance().toJsonP(callback, jsonUserInfoObj);
						} else
						{
							String uid = "";
							String nickname = jsonUserInfoObj.getString(Global.QQ_NICKNAME);
							String province = jsonUserInfoObj.getString(Global.QQ_PROVINCE);
							String city = jsonUserInfoObj.getString(Global.QQ_CITY);
							String headimgurl = jsonUserInfoObj.getString(Global.QQ_HEADIMGURL);
							String sex = "男".equals(jsonUserInfoObj.getString(Global.QQ_SEX)) == true ? "1" : "2";
							// 查询用户是否存在 如果不存在创建一个用户
							SystemUser qqUser = new SystemUser();
							SystemUserInfo systemUserInfo = new SystemUserInfo();
							try
							{
								qqUser = systemUserService.getUserByThreeUnionid(this.openid);
							} catch (Exception e)
							{
								// 查询用户必须处理，否则可能存在多添加重复的用户,如果查询失败返回错误信息
								return checkBackJson(callback, qqBackInfo, Global.int300301, Global.str300301 + "[" + e.getMessage() + "]");
							}
							if (null == qqUser)
							{
								// 如果不存在则新增用户
								qqUser = new SystemUser(
												this.openid, // 唯一标识，保持跟微信同步命名 微博中为 UID
												this.openid, // openid
												nickname, // 昵称
												sex, // 性别
												"-1", // 星座
												null, // 生日
												province, // 省会
												city, // 城市
												user_country, // 用户国家
												headimgurl, // 用户头像
												lang, // 用户语言
												"qq", // 用户客户端ios或者android
												"6", // 用户类型0、官方1、普通2、微信3、web微信4、QQ5、微博
												new Date());
								systemUserInfo = new SystemUserInfo(
												this.openid, // 唯一标识
												this.openid, // 唯一标识
												this.accessToken, // 微博票据
												this.refreshToken, // 用户刷新access_token
												Integer.parseInt(this.expireIn), // 生命周期
												ip, //
												new Date(), //
												new Date()); // 有俩Date()

								try
								{
									uid = systemUserService.saveUserAndInfo(qqUser, systemUserInfo);
									// #趣选新用户是否立刻同步到aliIM 0:不同步 1：立即同步
									try
									{
										if ("1".equals(Global.getSycnAliIM()))
										{
											qqUser.setUid(uid);
											String resStr =IMUtils.addUser(qqUser); // 阿里IM名用戶同步
											logger.debug("IMUtils.addUser(qqUser) -\n {}",resStr);
										}
									} catch (Exception e1)
									{
										logger.debug("qq授权..IMUtils.addUser(weiboUser),更新接口报错：[{}]", e1.getMessage());
									}
								} catch (Exception e)
								{
									// 新增用户必须处理，用户都没有新增成功 返回信息没有任何意义
									return checkBackJson(callback, qqBackInfo, Global.int300301, Global.str300301 + "[" + e.getMessage() + "]");
								}

							} else
							{
								// 如果存在 则更新用户 ，web登陆情况下的主对象，暂时没有token（session）的概念所以不需要更新
								systemUserInfo.setUid(qqUser.getUid());
								systemUserInfo.setWebAccessToken(this.accessToken);
								systemUserInfo.setWebExpiresIn(Integer.parseInt(this.expireIn));
								systemUserInfo.setWebRefreshToken(this.refreshToken);
								try
								{
									uid = systemUserService.saveUserAndInfo(qqUser, systemUserInfo);
									// #趣选新用户是否立刻同步到aliIM 0:不同步 1：立即同步
									try
									{
										if ("1".equals(Global.getSycnAliIM()))
										{
											qqUser.setUid(uid);
											String resStr = IMUtils.updateUser(qqUser); // 阿里IM名用戶更新
											logger.debug("IMUtils.addUser(qqUser) -\n {}",resStr);
										}
									} catch (Exception e1)
									{
										logger.debug("qq授权..IMUtils.updateUser(qqUser),更新接口报错：[{}]", e1.getMessage());
									}
								} catch (Exception e)
								{
									// 更新报错可以不做处理,最多用户信息更新不成功，不要影响正常返回，但是需要记录日志
									logger.debug("qq授权..systemUserService.saveUserAndInfo,更新接口报错：[{}]", e.getMessage());
									return checkBackJson(callback, qqBackInfo, Global.int300301, Global.str300301 + "[" + e.getMessage() + "]");
								}
							}
							QQWebLoginToJson qqWebLogin = new QQWebLoginToJson(uid, nickname, headimgurl, sex);
							logger.debug("qq授权..用户数据：{}", qqUser.toString());
							return JsonMapper.getInstance().toJsonP(callback, qqWebLogin);
						}
					}
				}else{
					// 如果jsonOpenidObj 等于空，腾讯返回值有问题
					logger.debug("qq授权..jsonOpenidObj 等于空,腾讯返回值有问题：[{}]", openidStr);
					return checkBackJson(callback, qqBackInfo,Global.intNO,"jsonOpenidObj等于空,腾讯返回值错误："+openidStr);
				}	
			}
			
		}
	}
	/**
	 * 为获取UserInfo接口填充参数
	 * @Title: getUserInfoParams
	 * @Description: 为获取UserInfo接口填充参数
	 * @param jsonOpenidObj openid的json字符串
	 * @return    返回类型 Map<String,Object>
	 *
	 */
	private Map<String, Object> getUserInfoParams(JSONObject jsonOpenidObj)
	{
		Map<String, Object> get_user_info_params = Maps.newHashMap();
		this.openid = jsonOpenidObj.getString(Global.QQ_OPENID);
		this.oauth_consumer_key = jsonOpenidObj.getString(Global.QQ_CLIENT_ID);
		get_user_info_params.put("access_token", this.accessToken);
		get_user_info_params.put("oauth_consumer_key", this.oauth_consumer_key);
		get_user_info_params.put("openid", this.openid);
		return get_user_info_params;
	}
	/**
	 * 为获取Openid接口填充参数
	 * @Title: getOpenidParams
	 * @Description: 为获取Openid接口填充参数
	 * @param accessTokenStr token的json字符串
	 * @return    返回类型 Map<String,Object>
	 *
	 */
	private Map<String, Object> getOpenidParams(String accessTokenStr)
	{
		Map<String, Object> get_openid_params = Maps.newHashMap();
		Matcher m2 = Pattern.compile("^access_token=(\\w+)&expires_in=(\\w+)&refresh_token=(\\w+)$").matcher(accessTokenStr);
		if (m2.find())
		{
			this.accessToken = m2.group(1);
			this.expireIn = m2.group(2);
			this.refreshToken = m2.group(3);
		} else
		{
			Matcher m3 = Pattern.compile("^access_token=(\\w+)&expires_in=(\\w+)$").matcher(accessTokenStr);
			if (m3.find())
			{
				this.accessToken = m3.group(1);
				this.expireIn = m3.group(2);
			}
		}
		logger.debug("accessToken={} | expireIn={} | refreshToken={}", accessToken, expireIn,refreshToken);
		get_openid_params.put("access_token", this.accessToken);
		return get_openid_params;
	}
	/**
	 * 为获取accessToken接口填充参数
	 * @Title: getTokenParams
	 * @Description: 为获取accessToken接口填充参数
	 * @param code 前端提供腾讯返回的code
	 * @return    返回类型 Map<String,Object>
	 *
	 */
	private Map<String, Object> getTokenParams(String code)
	{
		Map<String, Object> get_token_params = Maps.newHashMap();
		get_token_params.put("grant_type", "authorization_code");
		get_token_params.put("client_id", Global.getConfig("qq_app_ID"));
		get_token_params.put("client_secret", Global.getConfig("qq_app_KEY"));
		get_token_params.put("code", code);
		get_token_params.put("redirect_uri", Global.getConfig("qq_redirect_URI"));
		return get_token_params;
	}
	/**
	 * 缩减返回报错语句代码
	 * @Title: checkBackJson
	 * @Description: 缩减返回报错语句代码
	 * @param callback
	 * @param qqBackInfo
	 * @param stateCode
	 * @param backMsg
	 * @return    返回类型 String
	 *
	 */
	private String checkBackJson(String callback, BaseBackInfo qqBackInfo,int stateCode,String backMsg)
	{
		qqBackInfo.setStateCode(stateCode);
		qqBackInfo.setRetMsg(backMsg);
		return JsonMapper.getInstance().toJsonP(callback, qqBackInfo);
	}

	/**
	 * 对这类返回值做处理，如果找不到，返回null
	 * @Title: parseObjectByQQ
	 * @Description: callback\\( ({.+}) \\)
	 * @param jsonStr
	 * @return    返回类型 JSONObject
	 *
	 */
	private JSONObject parseObjectByQQ(String jsonStr)
	{
		JSONObject openidObj = null;
		Matcher _m = Pattern.compile("^callback\\((.+)\\)").matcher(jsonStr);
		if (_m.find())
		{
			jsonStr = _m.group(1);
			openidObj = JSONObject.parseObject(jsonStr);
		}
		return openidObj;
	}
	
	@ExceptionHandler(value = { HttpMessageNotReadableException.class })
	public @ResponseBody BaseBackInfo exp(HttpMessageNotReadableException ex)
	{
		String retMsg = ReUtil.extractMulti("(^.*)\\n at (\\[.*$)", ex.getMessage().substring(0, 150), "$1");

		BaseBackInfo backError = new BaseBackInfo();
		if (StrUtil.isEmpty(retMsg))
		{
			logger.info("参数错误" + retMsg);
			retMsg = Global.ERROR;
		}
		backError.setRetMsg(retMsg);
		backError.setStateCode(Global.int300301);
		return backError;
	}
}