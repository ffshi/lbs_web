package com.innovate.cms.common.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.security.Digests;
import com.innovate.cms.common.utils.Encodes;
import com.innovate.cms.common.utils.HttpUtils;

public class WeiBoService
{
    private static	Logger logger =  LoggerFactory.getLogger(WeiBoService.class);
	
	/**
	 * 
	 * @Title: getApiTicketJson
	 * @Description: 获取 微博 js_ticket 有效时间 7199 ticket 的有效时间（秒）  //作废
	 * @return    返回类型 String
	 *
	 */
	public static String getApiTicketJson() {
		 String url = "https://api.weibo.com/oauth2/js_ticket/generate?client_id=" + Global.WB_APPKEY + "&client_secret=" +Global.WB_APPSECRET;
         return HttpUtils.doHttpNoParam(url, RequestMethod.POST);
	}
	/**
	 * 
	 * @Title: sign
	 * @Description: 签名算法 map
	 * @param jsapi_tickets
	 * @param url
	 * @param timestamp 
	 * @return    返回类型 Map<String,String>
	 *
	 */
	public static Map<String, String> sign(String jsapi_ticket, String url, String timestamp) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = Global.getNoncestr();
       // String timestamp = Global.getTimestamp();
        String string1="";
        String signature = "";
        //签名字符串
        string1 = "jsapi_ticket=" + jsapi_ticket + 
        		  "&noncestr=" + nonce_str + 
        		  "&timestamp=" + timestamp + 
        		  "&url=" + url;
        logger.debug(string1);
        try
		{
			signature = Encodes.encodeHex(Digests.sha1(string1.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

        ret.put("url", url);
        ret.put("noncestr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        return ret;
	}
}
