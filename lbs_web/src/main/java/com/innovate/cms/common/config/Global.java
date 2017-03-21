/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.common.config;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;

import com.aliyun.ocs.OcsClient;
import com.aliyun.ocs.OcsException;
import com.aliyun.ocs.OcsOptions;
import com.google.common.collect.Maps;
import com.innovate.cms.common.utils.PropertiesLoader;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.utils.StringUtils;
import com.qiniu.util.Auth;


/**
 * 全局配置类
 * @author gaoji
 * @version 2015-08-15
 */
public class Global {
	private static Logger logger = LoggerFactory.getLogger(Global.class);
	/**
	 * 当前对象实例
	 */
	private static Global global = new Global();
	//test123.56.215.22测服或者 online60.205.13.131线上
	public static final String DOMAIN 	= "f839f85d740d4347.m.cnbjalicm12pub001.ocs.aliyuncs.com:11211";
	
	//本地开发
//	public static final String DOMAIN 	= "101.200.193.70:11211";
	public static final String USERNAME = "f839f85d740d4347";  // 与H5推广线上一致
	public static final String PASSWORD = "b63sH2CsD";
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader loader = new PropertiesLoader("cms.properties");
	
	/**
	 * 阿里OCS链接
	 */
	private static OcsClient ocs =  new OcsClient(Global.DOMAIN, Global.USERNAME, Global.PASSWORD);
	/**
	 * 阿里缓存异步超时时间
	 */
	private static OcsOptions options = new OcsOptions(Global.TIME_OUT);
	
	private static Random random = new Random();
	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";
	/**
	 * 1是/0否
	 */
	public static final int iYES = 1;
	public static final int iNO = 0;
	/**
	 * 1是/-1否
	 */
	public static final int intYES = 1;
	public static final int intNO = -1;
	
	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String STRNULL = "-1";
	/**
	 * 成功/失败
	 */
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	/**
	 * 常见数据
	 */
	public static final String DATANULL = "数据为空,或者参数格式错误";
	public static final String NOTNUM = "参数非数字";
	public static final String BUSY = "系统繁忙，稍后再试";
	public static final String _IOS = "ios";
	public static final String _ANDROID = "android";
	public static final String _URL = getStaticUrl();
	
	public static final int ONE = 1;
	public static final int ZERO = 0;
	
	public static final String HTTP  = "http://";
	public static final String HTTPS = "https://";
	
	public static final String SIGN_PARAM = "Sign-Param";
	public static final String SIGN = "Sign";
	public static final String UID = "Uid";
	
	/**
	 * 消息类型
	 */
	public static String MESSAGE_MSG = "1"; //留言
	public static String MESSAGE_MSG_NAME = "留言";
	public static String REPLY_MSG = "2"; //回复
	public static String REPLY_MSG_NAME = "回复";
	public static String FOLLOW_MSG = "3"; //关注
	public static String FOLLOW_MSG_NAME = "关注";
	public static String INTERACTION_MSG = "4"; //提醒，即互动消息
	public static String RECOMMEND_MSG = "5"; //推荐好友
	public static String UPDATE_MSG = "6"; //系统消息, 更新
	public static String GROUP_UPDFATE_MSG = "7"; //系统消息, 专题
	public static String PRAISE_MSG = "8"; //点赞
	public static String PRAISE_MSG_NAME = "点赞";
	public static String PRAISE_MSG_CONTENT = "赞的就是你, 快去看看吧";
	public static String COMMENT_MSG = "9"; //评论
	public static String COMMENT_MSG_NAME = "评论";
	public static String COMMENT_REPLY_MSG = "10"; //评论回复
	public static String COMMENT_REPLY_MSG_NAME = "评论回复";
	public static String GROUPS_COMMENT_REPLY_MSG = "11"; //专题评论回复
	public static String GROUPS_COMMENT_REPLY_MSG_NAME = "专题评论回复";

	/**
	 * 模板标识
	 */
	public static String TEMPLATE_ZERO = "0"; //模板0
	public static String TEMPLATE_FOUR = "4"; //模板4
	
	
	/**
	 * 推送内容
	 */
	public static String PUSH_PRAISE_MSG_CONTENT = "给你的足迹点赞";
	public static String PUSH_COMMENT_MSG_CONTENT = "评论了你的足迹";
	public static String PUSH_COMMENT_REPLY_MSG_CONTENT = "回复了你的评论";
	
	/**
	 * 用户默认生日
	 */
	public static String DEFAULT_BIRTHDAY = "1900-01-01 00:00:00";
	
	/**
	 * 混淆生成token的key
	 */
	public static final String TOKEN_KEY = "OKdkDv0kLYxh1wor";
	/**
	 * 加密偏移量
	 */
	public static final String IV = "2016050100000000";	
	
	/**
	 * 百度LBS
	 */
	public static final String BAIDU_LBS_APPID="9418299";
	public static final String BAIDU_LBS_AK="7aTTP02Too2IRpxB832oQqjjruF5A7ZP";
	
	/**
	 * 微信二次分享
	 */
	//正式环境
	public static final String WX_APPID ="wxce02a5a1db507f44";
	public static final String WX_SECRET ="2849eb922f7ce0248fe7ae5f7d1c57eb";
	
	
	//public static final String WX_APPID ="wx39f1f71ca2c97794";  // 测试号信息
	//public static final String WX_SECRET ="06352defb787e5ef7a909e35d81fbeff"; // 测试号信息
	public static final String WX_GRANT_TYPE = "client_credential";
	public static final String WX_CODE = "code";
	public static final String WX_STATE = "state";
	public static final String WX_ERRSTR = "{\"errcode\":#CODE,\"errmsg\":\"#MSG\"}";
	public static final String WX_ACCESS_TOKEN = "access_token";
	public static final String WX_OPENID = "openid";
	public static final String WX_EXPIRES_IN  = "expires_in";
	public static final String WX_REFRESH_TOKEN  = "refresh_token";
	public static final String WX_SCOPE = "scope";
	public static final String WX_NICKNAME  = "nickname";
	public static final String WX_SEX  = "sex";
	public static final String WX_PROVINCE  = "province";
	public static final String WX_CITY  = "city";
	public static final String WX_COUNTRY  = "country";
	public static final String WX_HEADIMGURL  = "headimgurl";
	public static final String WX_PRIVILEGE  = "privilege";
	public static final String WX_UNIONID  = "unionid";
	public static final String WX_ERRCODE  = "errcode";
	public static final String WX_ERRMSG  = "errmsg";
	public static final String WX_ZH_CN = "zh_CN";
	
	public static final String QQ_ACCESS_TOKEN = "access_token";// 获取信息凭证
	public static final String QQ_OPENID = "openid";//唯一ID
	public static final String QQ_CLIENT_ID = "client_id";//唯一ID
	public static final String QQ_EXPIRES_IN  = "expires_in";//凭证刷新时间
	public static final String QQ_NICKNAME  = "nickname"; //用户在QQ空间的昵称
	public static final String QQ_SEX  = "gender";//性别。 如果获取不到则默认返回"男"
	public static final String QQ_CITY  = "city";//城市
	public static final String QQ_PROVINCE  = "province";//省
	public static final String QQ_HEADIMGURL  = "figureurl_qq_1";  //大小为40×40像素的QQ头像URL
	public static final String QQ_ERRCODE  = "error";
	public static final String QQ_ERRMSG  = "error_description";
	public static final String QQ_CODE = "code";
	public static final String QQ_STATE = "state";
	/**
	 * IM相关
	 */
	public static final double IM_MATCH_RANGE = 0.80; //IM第一次聊天匹配度范围>该值
	public static final int IM_MATCH_LIMIT = 3; //匹配度数据获取时间倒序前N条
	/**
	 * 微信返回的json取值
	 */
	public static final String TICKET = "ticket";
	public static final String ACCESS_TOKEN = "access_token";
	/**
	 * 缓存key前缀  accessToken:0-accessToken:999
	 */
	public static final String ACCESSTOKEN = "accessToken:";
	public static final String JSAPITICKET = "jsapiTicket:";
	/**
	 * 微博
	 */
	//public static final String WB_APPKEY = "1233914485";  // 与 client_ID 一致
	//public static final String WB_APPSECRET = "1df37a11c178226ecdb83a996720f76c";  // 与client_SERCRET 一致
	public static final String WB_APPKEY = "1378472820";  // 与 client_ID 一致
	public static final String WB_APPSECRET = "ce5f46ffc075c6efa7157b7a995e5e97";  // 与client_SERCRET 一致
	public static final String WB_JSAPITICKET = "weibo:jsapiTicket:";
	public static final String WB_ERROR = "error";
	public static final String WB_ERROR_CODE = "error_code";
	public static final String WB_ERROR_DESCRIPTION = "error_description";
	public static final String WB_ACCESS_TOKEN = "access_token";
	public static final String WB_EXPIRES_IN = "expires_in";
	public static final String WB_UID = "uid";
	public static final String WB_CODE = "code";
	public static final String WB_STATE = "state";
	
	/**
	 * 1000个副本
	 */
	public static final int LENGTH = 1000;
	public static final int LENGTH10 = 10;
	
	/**
	 * 阿里push  这里的参数千万不要泄露 
	 */
	public static final long appKey = 23386827 ;
	public static final String accessKeyId= "kYo7u724wOmFoYAP";
	public static final String accessKeySecret = "vBYCd4wJo0Eyy4qbctENG2K5gZymNF";
	
	/**
	 * 阿里Memcache 分享主  f839f85d740d4347 废除
	 */
	//public static final String DOMAIN 	= "5eadf5a66c5b4f76.m.cnbjalicm12pub001.ocs.aliyuncs.com:11211";
	// public static final String DOMAIN 	= "101.200.193.70:11211";
	//public static final String USERNAME = "5eadf5a66c5b4f76";
	

	
	public static final int EXPIRE_TIME = 600;   // 单位秒
	public static final int TIME_OUT 	= 5*1000;   // 单位毫秒
	
	/**
	 * 七牛上传KEY
	 */
	public static final String ACCESS_KEY ="Ql1NUEjLl_r1z0MzejvC8zzvAIo_MQZdvzaOP8ui";
	public static final String SECRET_KEY ="AQTV4-VylgcGqlao30dtrp1qjYWDp14ux1Yb6Ysk";

	public static final String BUCKET_HEAD = "qselect3-head";
	public static final String UPTOKEN_HEAD = "QNUpToken:head:";
	
	public static final String BUCKET = "qselect";
	public static final String UPTOKEN = "QNUpToken:";
	
	public static final String SPLIT_TIME = "08:00:00";
	/**
	 * 用户请求过期
	 */
	public static final int int300101 = 300101;
	public static final String str300101 = "User's request is expired.";
	/**
	 * url无法解析
	 */
	public static final int int300201 = 300201;
	public static final String str300201 = "URL cannot be resolved.";
	/**
	 * 缺少Header参数，或header错误
	 */
	public static final int int300202 = 300202;
	public static final String str300202 = "Missing header param or  param error.";
	/**
	 * Body不存在
	 */
	public static final int int300203 = 300203;
	public static final String str300203 = "Body does not exist.";	
	/**
	 * User 不存在
	 */
	public static final int int300204 = 300204;
	public static final String str300204 = "User does not exist.";
	
	/**
	 * 用户密码错误
	 */
	public static final int int3002042 = 3002042;
	public static final String str3002042 = "name or pwd error";
	/**
	 * 账号或密码不正确
	 */
	public static final int int3002041 = 3002041;
	public static final String str3002041 = "Sorry, the account with this keycode was not found.";
	
	/**
	 * Sign校验失败
	 */
	public static final int int300205 = 300205;
	public static final String str300205 = "Sign check failed.";
	/**
	 * 用户请求超时
	 */
	public static final int int300206 = 300206;
	public static final String str300206 = "User's request is timeout.";
	/**
	 * Token失效
	 */
	public static final int int300207 = 300207;
	public static final String str300207 = "Token Invalid.";
	/**
	 * 用户未登录
	 */
	public static final int int300208 = 300208;
	public static final String str300208 = "User has not logged.";
	/**
	 * jsonData中参数错误
	 */
	public static final int int300209 = 300209;
	public static final String str300209 = "jsonData param error.";
	/**
	 *权限不足或留言不存在
	 */
	public static final int int300210 = 300210;
	public static final String str300210 = "Insufficient permissions or message does not exist.";
	/**
	 *留言不存在
	 */
	public static final int int300211 = 300211;
	public static final String str300211 = "Message does not exist.";
	/**
	 *权限不足或回复不存在
	 */
	public static final int int300212 = 300212;
	public static final String str300212 = "Insufficient permissions or reply does not exist.";
	/**
	 *自己不能给自己留言
	 */
	public static final int int300213 = 300213;
	public static final String str300213 = "You cannot give yourself a message.";
	/**
	 *自己不能给自己回复
	 */
	public static final int int300214 = 300214;
	public static final String str300214 = "You cannot give yourself a reply.";
	/**
	 *足迹不存在
	 */
	public static final int int300220 = 300220;
	public static final String str300220 = "Footprint does not exist.";
	/**
	 *权限不足或评论不存在
	 */
	public static final int int300221 = 300221;
	public static final String str300221 = "No permission or comment does not exist.";
	
	/**
	 * 内部错误
	 */
	public static final int int300301 = 300301;
	public static final String str300301 = "Internal error.";
	/**
	 * 系统繁忙稍候再试
	 */
	public static final int int300302 = 300302;
	public static final String str300302 = "Sorry,The system is busy. Please try again late.";
	/**
	 * 数据库找不到数据
	 */
	public static final int int300303 = 300303;
	public static final String str300303 = "Sorry, Can't find more data.";

	/**
	 * 获取当前对象实例
	 */
	public static Global getInstance() {
		return global;
	}
	
	/**
	 * 
	 * @Title: getStaticUrl
	 * @Description: 获取静态文件路径
	 * @return    返回类型 String
	 *
	 */
	public static String getStaticUrl()
	{
		return getConfig("qiniu.url1");
	}
	
	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getConfig("urlSuffix");
	}
	/**
	 * 获取sycnAliIM
	 */
	public static String getSycnAliIM() {
		return getConfig("sycnAliIM");
	}
	
	/**
	 * 获取配置
	 * @see ${fns:getConfig('adminPath')}
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = loader.getProperty(key);
			map.put(key, value != null ? value : StringUtils.EMPTY);
		}
		return value;
	}
    
	/**
	 * 页面获取常量
	 * @see ${fns:getConst('YES')}
	 */
	public static Object getConst(String field) {
		try {
			return Global.class.getField(field).get(null);
		} catch (Exception e) {
			// 异常代表无配置，这里什么也不做
		}
		return null;
	}

    /**
     * 获取工程路径
     * @return
     */
    public static String getProjectPath(){
    	// 如果配置了工程路径，则直接返回，否则自动获取。
		String projectPath = Global.getConfig("projectPath");
		if (StringUtils.isNotBlank(projectPath)){
			return projectPath;
		}
		try {
			File file = new DefaultResourceLoader().getResource("").getFile();
			if (file != null){
				while(true){
					File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
					if (f == null || f.exists()){
						break;
					}
					if (file.getParentFile() != null){
						file = file.getParentFile();
					}else{
						break;
					}
				}
				projectPath = file.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath;
    }
	/**
     * 
     * @Title: getNoncestr
     * @Description: 获取随机字符串
     * @return    返回类型 String
     *
     */
    public static String getNoncestr() {
        return UUID.randomUUID().toString();
    }
    /**
     * 
     * @Title: getTimestamp
     * @Description: 获取时间戳
     * @return    返回类型 String
     *
     */
    public static String getTimestamp() {
	        return Long.toString(System.currentTimeMillis() / 1000);
	}
    
    /**
     * 返回OCS缓存链接对象
     * @Title: getOcsClient
     * @Description: 返回OCS缓存链接对象
     * @return    返回类型 OcsClient
     *
     */
    public static OcsClient getOcsClient()
	{
    	if (null == ocs)
		{
    		ocs = new OcsClient(Global.DOMAIN, Global.USERNAME, Global.PASSWORD);
		}
    	return ocs; 
	}
    /**
     * 返回缓存过期时间对象
     * @Title: getOcsOptions
     * @Description: 返回缓存过期时间对象 默认3600秒
     * @return    返回类型 OcsOptions
     *
     */
    public static OcsOptions getOcsOptions()
	{
    	if (null == options)
		{
    		options = new OcsOptions(Global.TIME_OUT);
		}
    	return options;
	}
    /**
     * 
     * @Title: getOcsOptions
     * @Description:    异步获取对象 超时时间
     * @param timeout   0 表示同步 其他表示异步  单位（秒 ）
     * @return    返回类型 OcsOptions
     *
     */
    public static OcsOptions getOcsOptions(int timeout)
   	{
    	OcsOptions op ;
       	if (0 == timeout)
   		{
       		op = new OcsOptions();
   		}else {
   			op = new OcsOptions(timeout*1000);
		}
       	return op;
   	}
    
    /**
     * 
     * @Title: getLength
     * @Description: 获取LENGTH -1 范围内的随机正整数
     * @return    返回类型 Integer
     *
     */
    public static Integer getLength()
	{
		return Math.abs(random.nextInt(LENGTH-1));
	}
    /**
     * 
     * @Title: getLength
     * @Description: length 根据传入的长度 产生随机值
     * @param length
     * @return    返回类型 Integer
     *
     */
    public static Integer getLength(int length)
   	{
   		return Math.abs(random.nextInt(length-1));
   	}
    /**
     * 获取微信access_token请求地址
     * @Title: getAccessTokenUrl
     * @Description: 获取微信access_token请求地址
     * @param appid
     * @param secret
     * @param code
     * @return    返回类型 String
     *
     */
	public static String getAccessTokenUrl(String appid, String secret, String code)
	{
		String access_token_url="https://api.weixin.qq.com/sns/oauth2/access_token?"
				+ "appid="+ appid
				+ "&secret="+ secret
				+ "&code="+ code
				+ "&grant_type=authorization_code";
		return access_token_url;
	}
	
	public static String getMethodGetUrl(String baseURL, Map<String, Object> params)
	{
		StringBuilder sBuilder = new StringBuilder(baseURL);
		sBuilder.append("?");
		Set<String> keySet = params.keySet();
		int i = 0 ; 
		for (Object obj : keySet)
		{
			if (0==i)
			{
				sBuilder.append(obj+"=");
			}else {
				sBuilder.append("&"+obj+"=");
			}
			sBuilder.append(params.get(obj));
			
			i++;
		}
		return sBuilder.toString();
	}
	/**
	 * 
	 * @Title: getWeiBoAccessTokenUrl
	 * @Description: 接收code拼接出换取Access Token  的 请求地址
	 * @param code
	 * @return    返回类型 String
	 *
	 */
	public static Map<String, Object> getWeiBoAccessToken(String code)
	{
		Map<String, Object> params = Maps.newHashMap();
		params.put("client_id", getConfig("client_ID"));
		params.put("client_secret", getConfig("client_SERCRET"));
		params.put("grant_type", "authorization_code");
		params.put("redirect_uri", getConfig("redirect_URI"));
		params.put("code", code);
		return params;
	}
	
	/**
	 * 获取微信Userinfo请求地址
	 * @Title: getUserinfo
	 * @Description: 获取微信Userinfo请求地址
	 * @param access_token
	 * @param openid
	 * @param lang
	 * @return    返回类型 String
	 *
	 */
	public static String getUserinfo(String access_token, String openid, String lang)
	{
		String get_userinfo="https://api.weixin.qq.com/sns/userinfo?"
				+ "access_token=" + access_token
				+ "&openid=" + openid
				+ "&lang="+ lang;
		return get_userinfo;
	}
	/**
	 * 获取 AccessToken刷新链接
	 * @Title: getRefreshAccessToken
	 * @Description: 获取 AccessToken刷新链接
	 * @param appid
	 * @param _refresh_token
	 * @return    返回类型 String
	 *
	 */
	public static String getRefreshAccessToken(String appid, String _refresh_token)
	{
		String refresh_token = "https://api.weixin.qq.com/sns/oauth2/refresh_token?"
				+ "appid=" + appid
				+ "&grant_type=refresh_token"
				+ "&refresh_token=" + _refresh_token ;
		return refresh_token;
	}
	
	public static String getErrorJson(String code,String msg)
	{
		String errorJson = WX_ERRSTR;
		
		if (StrUtil.isNotBlank(code))
		{
			errorJson = errorJson.replace("#CODE", code);
		}
		if (StrUtil.isNotBlank(code))
		{
			errorJson = errorJson.replace("#MSG", msg);
		}
		return errorJson;
	}

	/**
	 * 
	 * @Title: getUpToken
	 * @Description: 获取七牛上传token
	 * @return    返回类型 String
	 *
	 */
	public static String getUpTokenForHead()
	{
		String upToken = "";
		OcsClient _ocs = Global.getOcsClient();
		OcsOptions _options = Global.getOcsOptions();
		try
		{
			// 先去缓存获取 
			int num = Global.getLength(Global.LENGTH10);// 取10个镜像
			logger.debug("getUpToken 中 ---- ocs=[{}],options1=[{}],num[{}]",ocs,_options,num);
			upToken = (String)_ocs.syncGet(Global.UPTOKEN_HEAD + num, _options).getValue();
			// 如果没有取到值，改变num再获取一次 
			if (StrUtil.isBlank(upToken))
			{
				num = Global.getLength(Global.LENGTH10);
				upToken = (String)_ocs.syncGet(Global.UPTOKEN_HEAD + num, _options).getValue();
				logger.debug("一次获取失败，再次获取upToken = 【{}】",upToken);
			}
			// 如果仍然为空，发起请求获取新的upToken，并写入缓存 ，最后返回
			if (StrUtil.isBlank(upToken))
			{
				final Auth auth = Auth.create(Global.ACCESS_KEY, Global.SECRET_KEY);
				upToken = auth.uploadToken(Global.BUCKET_HEAD);
				logger.debug("获取新upToken = 【{}】",upToken);
				for (int i = 0; i < Global.LENGTH10; i++)
				{
					try
					{
						_ocs.set(Global.UPTOKEN_HEAD + i, upToken, Global.EXPIRE_TIME, _options); // 存10个镜像 300秒
					} catch (OcsException e)
					{
						continue;
					}
				}
				logger.debug("新upToken存入缓存成功..........");
			}
		} catch (OcsException e)
		{
			upToken = "";
			logger.debug("获取值错误：",e.getMessage());
		}
	    return upToken;
	}
	
}
