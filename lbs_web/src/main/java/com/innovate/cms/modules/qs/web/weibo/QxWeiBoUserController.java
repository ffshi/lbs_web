package com.innovate.cms.modules.qs.web.weibo;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.aliyun.ocs.OcsClient;
import com.aliyun.ocs.OcsException;
import com.aliyun.ocs.OcsOptions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.mapper.BeanMapper;
import com.innovate.cms.common.mapper.JsonMapper;
import com.innovate.cms.common.service.WeiBoService;
import com.innovate.cms.common.utils.HttpClientUtil;
import com.innovate.cms.common.utils.ReUtil;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.common.web.BaseController;
import com.innovate.cms.modules.aliIM.IMUtils;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.common.entity.DataBackInfo;
import com.innovate.cms.modules.data.entity.WBConfigToJson;
import com.innovate.cms.modules.data.entity.WBWebLoginToJson;
import com.innovate.cms.modules.qs.entity.user.SystemUser;
import com.innovate.cms.modules.qs.entity.user.SystemUserInfo;
import com.innovate.cms.modules.qs.service.user.SystemUserService;

/**
 * 
 * @ClassName: QxWeiBoUserController
 * @Description: 微博相关
 * @author gaoji gaoji_cyou-inc_com
 * @date 2016年8月22日 下午6:40:32
 *
 */
@Controller
@RequestMapping(value = "/api")
public class QxWeiBoUserController  extends BaseController
{
	
	private static WBConfigToJson wbToJson = new WBConfigToJson();

	private static Map<String, String> ret = Maps.newHashMap();
	
	@Autowired
	private SystemUserService systemUserService;
	/**
	 * 
	 * @Title: getWBConfig
	 * @Description: 微博令牌申请   //作废
	 * @param request
	 * @param response
	 * @return    返回类型 String
	 *
	 */
	@RequestMapping(value = "/v1/getWBConfig", method = RequestMethod.GET)
	public @ResponseBody String getWBConfig(HttpServletRequest request, HttpServletResponse response)
	{
		String callback = request.getParameter("callback");
		String url = request.getParameter("url");
		String timestamp = request.getParameter("timestamp");
		DataBackInfo<WBConfigToJson> backInfo = new DataBackInfo<WBConfigToJson>();
		if (StrUtil.isBlank(url) || StrUtil.isBlank(callback)|| StrUtil.isBlank(timestamp))
		{
			callback = "callback";
			backInfo.setStateCode(Global.int300209);
			backInfo.setRetMsg(Global.str300209);
		}else{
			String _WB_JSAPITICKET = "";
			List<WBConfigToJson> data = Lists.newArrayList();
			OcsClient ocs = Global.getOcsClient();
			OcsOptions options = Global.getOcsOptions();
			int num = Global.getLength();
			logger.debug("ocs=[{}],options=[{}],num[{}]", ocs, options, num);
			try
			{
				_WB_JSAPITICKET = (String) ocs.syncGet(Global.WB_JSAPITICKET + num, options).getValue();
				logger.debug("WB_JSAPITICKET=[{},{}]",  Global.WB_JSAPITICKET + num, _WB_JSAPITICKET);
				logger.debug("getError:[{}],getStatus[{}]", ocs.syncGet(Global.WB_JSAPITICKET + num, options).getError(), ocs.syncGet(Global.WB_JSAPITICKET + num, options).getStatus());
			} catch (OcsException e1)
			{
				logger.debug("获取值错误：", e1.getMessage());
			}
			ret = WeiBoService.sign(_WB_JSAPITICKET, url,timestamp);
			wbToJson.setUrl(url);
			try
			{
				if (ret.size() > 0)
				{
					BeanMapper.copy(ret, wbToJson);
					logger.debug("BeanMapper.copy:{}复制成功", wbToJson.toString());
				} else
				{
					logger.debug("ret:{}参数不全", ret.toString());
				}
			} catch (Exception e)
			{
				logger.debug("BeanMapper.copy:{}失败", e.getMessage());
				backInfo.setStateCode(Global.int300301);
				backInfo.setRetMsg(Global.str300301);
			}
			// 添加对象
			data.add(wbToJson);
			backInfo.setStateCode(Global.intYES);
			backInfo.setRetMsg(Global.SUCCESS);
			backInfo.setData(data);
		}
		return JsonMapper.getInstance().toJsonP(callback, backInfo);
	}
	/**
	 * 
	 * @Title: getOauth2ByWeiBo
	 * @Description:微博 Oauth2登陆
	 * @param request
	 * @param response
	 * @return    返回类型 String
	 *
	 */
	@RequestMapping(value = "/v2/oauth2/weibo", method = RequestMethod.GET)
	public @ResponseBody String getOauth2ByWeiBo(HttpServletRequest request, HttpServletResponse response)
	{
		String callback = request.getParameter("callback");
		String code = request.getParameter(Global.WB_CODE);
		@SuppressWarnings("unused")
		String state = request.getParameter(Global.WB_STATE);
		//String req_state = (String) request.getSession().getAttribute("REQ_STATE");
		
		String ip = StringUtils.getRemoteAddr(request);
		BaseBackInfo wbBackInfo = new BaseBackInfo();
		
		if (StrUtil.isBlank(callback))
		{
			callback = "callback";
			wbBackInfo.setStateCode(Global.intNO);
			wbBackInfo.setRetMsg("缺少callback参数");
			return JsonMapper.getInstance().toJsonP(callback, wbBackInfo);
		}
		/*else if (StrUtil.isBlank(state) || !req_state.equals(state)) {
			wbBackInfo.setStateCode(Global.intNO);
			wbBackInfo.setRetMsg("非法请求被拒绝...");
			return JsonMapper.getInstance().toJsonP(callback, wbBackInfo);
		}*/
		else if (StrUtil.isBlank(code)){
			wbBackInfo.setStateCode(Global.intNO);
			wbBackInfo.setRetMsg("缺少code 参数");
			return JsonMapper.getInstance().toJsonP(callback, wbBackInfo);
		}else {
			String jsonStr = HttpClientUtil.doPost(Global.getConfig("accessTokenURL"), Global.getWeiBoAccessToken(code));
			JSONObject jsObj = null;
			try
			{
				jsObj = JSONObject.parseObject(jsonStr);
			} catch (Exception e2)
			{
				logger.debug("jsObj解析出错：{}", e2.getMessage());
				wbBackInfo.setStateCode(Global.intNO);
				wbBackInfo.setRetMsg("jsObj解析出错");
				return JsonMapper.getInstance().toJsonP(callback, wbBackInfo);
			}  
			String errcode = jsObj.getString(Global.WB_ERROR_CODE);
			if (StrUtil.isNotBlank(errcode))
			{
				// 如果有错误打印出来
				logger.debug("错误代码：{}", jsonStr);
				return JsonMapper.getInstance().toJsonP(callback, jsObj);
			}else{
				// 如果没有错误 把所有值取出来
				String access_token = jsObj.getString(Global.WB_ACCESS_TOKEN);//用户授权的唯一票据，用于调用微博的开放接口，同时也是第三方应用验证微博用户登录的唯一票据
				String expires_in 	= jsObj.getString(Global.WB_EXPIRES_IN); //access_token的生命周期，单位是秒数
				String unionid 		= jsObj.getString(Global.WB_UID); //授权用户的UID
				String lang 		= Global.WX_ZH_CN; // 默认
				String user_country = "CN";
				logger.debug("access_token={} | expires_in={} | unionid={}",access_token,expires_in,unionid);
				Map<String, Object> params = Maps.newHashMap();
				params.put("uid", unionid);
				params.put("access_token", access_token);
				String jsonShowUserStr = HttpClientUtil.doGet(Global.getMethodGetUrl(Global.getConfig("baseURL")+"users/show.json", params));
				JSONObject jsUserObj = null;
				try
				{
					jsUserObj = JSONObject.parseObject(jsonShowUserStr);
				} catch (Exception e2)
				{
					logger.debug("jsUserObj解析出错：{}", e2.getMessage());
					wbBackInfo.setStateCode(Global.intNO);
					wbBackInfo.setRetMsg("jsUserObj解析出错");
					return JsonMapper.getInstance().toJsonP(callback, wbBackInfo);
				}
				String _errcode 	   = jsObj.getString(Global.WB_ERROR_CODE);
				if (StrUtil.isNotBlank(_errcode))
				{
					// 如果有错误打印出来
					logger.debug("错误代码：{}", jsonShowUserStr);
					return JsonMapper.getInstance().toJsonP(callback, jsUserObj);
				}
				String sex = jsUserObj.getString("gender");
				if ("m".equals(sex))
				{
					sex = "1";
				}else if ("f".equals(sex)) {
					sex = "2";
				}else {
					sex = "-1";
				}
				String nickname   = jsUserObj.getString("screen_name");
				String headimgurl = jsUserObj.getString("profile_image_url");

				// 查询用户是否存在 如果不存在创建一个用户
				SystemUser weiboUser = new SystemUser();
				try
				{
					weiboUser = systemUserService.getUserByThreeUnionid(unionid);
				} catch (Exception e)
				{
					// 查询用户必须处理，否则可能存在多添加重复的用户
					wbBackInfo.setStateCode(Global.int300301);
					wbBackInfo.setRetMsg(Global.str300301 + "[" + e.getMessage() + "]");
					return JsonMapper.getInstance().toJsonP(callback, wbBackInfo);
				}
				SystemUserInfo systemUserInfo = new SystemUserInfo();
				String uid = "";
				
				if (null == weiboUser)
				{
					// 如果不存在则新增用户
					weiboUser = new SystemUser(
							unionid, //唯一标识，保持跟微信同步命名 微博中为 UID
							unionid, //openid
							nickname, // 昵称
							sex, //性别
							"-1", // 星座
							null, // 生日
							null, // 省会
							null, // 城市
							user_country, //用户国家
							headimgurl, //用户头像
							lang, // 用户语言
							"weibo", // 用户客户端ios或者android
							"5", // 用户类型0、官方1、普通2、微信3、web微信4、QQ5、微博
							new Date());
					systemUserInfo = new SystemUserInfo(
							unionid, //唯一标识
							unionid, //唯一标识
							access_token, // 微博票据
							"", //刷新时间
							Integer.parseInt(expires_in), //生命周期
							ip, //
							new Date(), //
							new Date()); // 有俩Date()
					try
					{
						uid = systemUserService.saveUserAndInfo(weiboUser, systemUserInfo);
						// #趣选新用户是否立刻同步到aliIM 0:不同步 1：立即同步
						try
						{
							if ("1".equals(Global.getSycnAliIM()))
							{
								weiboUser.setUid(uid);
								IMUtils.addUser(weiboUser);        //阿里IM名用戶同步
							}
						} catch (Exception e1)
						{
							logger.debug("IMUtils.addUser(weiboUser),更新接口报错：[{}]", e1.getMessage());
						}
					} catch (Exception e)
					{
						// 新增用户必须处理，用户都没有新增成功 返回信息没有任何意义
						wbBackInfo.setStateCode(Global.int300301);
						wbBackInfo.setRetMsg(Global.str300301 + "[" + e.getMessage() + "]");
						return JsonMapper.getInstance().toJsonP(callback, wbBackInfo);
					}

				} else
				{
					// 如果存在 则更新用户 ，web登陆情况下的主对象，暂时没有token（session）的概念所以不需要更新
					systemUserInfo.setUid(weiboUser.getUid());
					systemUserInfo.setWebAccessToken(access_token);
					systemUserInfo.setWebExpiresIn(Integer.parseInt(expires_in));
					systemUserInfo.setWebRefreshToken("");
					try
					{
						uid = systemUserService.saveUserAndInfo(weiboUser, systemUserInfo);
						// #趣选新用户是否立刻同步到aliIM 0:不同步 1：立即同步
						try
						{
							if ("1".equals(Global.getSycnAliIM()))
							{
								weiboUser.setUid(uid);
								IMUtils.updateUser(weiboUser);        //阿里IM名用戶更新
							}
						} catch (Exception e1)
						{
							logger.debug("IMUtils.updateUser(weiboUser),更新接口报错：[{}]", e1.getMessage());
						}
					} catch (Exception e)
					{
						// 更新报错可以不做处理,最多用户信息更新不成功，不要影响正常返回，但是需要记录日志
						logger.debug("systemUserService.saveUserAndInfo,更新接口报错：[{}]", e.getMessage());
					}
				}
				WBWebLoginToJson webLogin = new WBWebLoginToJson(uid, nickname, headimgurl, sex);
				
				logger.debug("用户数据：{}", weiboUser.toString());
				return JsonMapper.getInstance().toJsonP(callback, webLogin);
			}
		} 
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
