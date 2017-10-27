package com.innovate.cms.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.mapper.JsonMapper;
import com.innovate.cms.common.security.Cryptos;
import com.innovate.cms.common.security.Digests;
import com.innovate.cms.common.utils.BodyReaderHttpServletRequestWrapper;
import com.innovate.cms.common.utils.Encodes;
import com.innovate.cms.common.utils.HttpHelperUtils;
import com.innovate.cms.common.utils.SpringContextHolder;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.modules.common.entity.BaseBackInfo;
import com.innovate.cms.modules.qs.entity.user.SystemUser;
import com.innovate.cms.modules.qs.service.user.SystemUserService;

/**
 * Servlet Filter implementation class SignFilter
 */

public class SignFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private static SystemUserService sysUserService = (SystemUserService) SpringContextHolder.getBean("systemUserService");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		// 不过滤的uri
		// String[] notFilter = new String[] {"/config/channel", "/config/time",
		// "/user/report", "/isToken","/opt/openQuestions"};
		// boolean doFilter = true; // 是否过滤

		// response.setHeader("Access-Control-Allow-Origin", "*");
		// // response.setHeader("Access-Control-Allow-Origin",
		// DictionaryParam.get(Constant2.DICTIONARY_GROUP_GLOBAL_SETTING,
		// "AccessControlAllowOrigin"));
		// response.setHeader("Access-Control-Allow-Methods",
		// "POST, GET, OPTIONS, DELETE");
		// response.setHeader("Access-Control-Max-Age", "3600");
		// response.setHeader("Access-Control-Allow-Headers",
		// "x-requested-with");

		String url = request.getRequestURI(); // 请求的url

		// 头文件中参数
		String timestamp = request.getHeader("Sign-Param");
		String sign = request.getHeader("Sign");
		String uid = request.getHeader("Uid");
		String _sign = ""; // 本地计算sign
		boolean doSendError = false; // 是否返回错误
		StringBuilder sb = new StringBuilder(); // 拼接的Builder

		// 方便调试测试，测试时候用这个一header就可以了
		String ff = request.getHeader("_");

		// 新增日志内容
		String ip = StringUtils.getRemoteAddr(request);
		MDC.put("ip", ip);
		// nginx负载均衡https跳转不需要多余的加密验证
		if (ip.equals("123.56.251.16") || ip.equals("123.56.242.51") || ip.equals("123.56.248.173") || ip.equals("123.57.50.235") || ip.equals("124.207.11.41") || ip.equals("127.0.0.1")) {
			chain.doFilter(request, response);
		} else {
			// 1.看数据是否被篡改 （列表内的只看篡改） POST 或者 PUT 有body数据
			if (RequestMethod.POST.toString().equalsIgnoreCase(request.getMethod()) || RequestMethod.PUT.toString().equalsIgnoreCase(request.getMethod())) {

				HttpServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(request);
				String body = HttpHelperUtils.getBodyString(requestWrapper);
				BaseBackInfo backInfo = new BaseBackInfo();
				SystemUser sysUser = null;
				String _token = "";
				String secretKey = "";
				// 0 校验 Header是否缺失
				if (StrUtil.isBlank(sign) || StrUtil.isBlank(uid) || StrUtil.isBlank(url) || !StrUtil.isPositiveNum(timestamp)) {
					logger.debug("非法请求，Header参数缺失" + "isBlank(sign)=" + StrUtil.isBlank(sign) + " isBlank(uid)=" + StrUtil.isBlank(uid) + " isBlank(uri)=" + StrUtil.isBlank(url) + " isPositiveNum(timestamp)=" + StrUtil.isPositiveNum(timestamp));
					backInfo.setStateCode(Global.int300202);
					backInfo.setRetMsg(Global.str300202);
					doSendError = true;
				}
				// 1 校验链接是否超时
				else if (Math.abs(System.currentTimeMillis() - Long.parseLong(timestamp)) > 300000L) {
					logger.debug("非法请求，链接超时" + System.currentTimeMillis() + "-" + Long.parseLong(timestamp));
					backInfo.setStateCode(Global.int300101);
					backInfo.setRetMsg(Global.str300101);
					doSendError = true;
				}
				// 2 校验body是否存在
				else if (StrUtil.isBlank(body)) {
					logger.debug("非法请求，没有jsonData数据" + body);
					backInfo.setStateCode(Global.int300203);
					backInfo.setRetMsg(Global.str300203);
					doSendError = true;
				} else {
					// 用户不等于-1的时候
					if (!uid.equals(Global.STRNULL)) {
						try {
							// 查询用户token
							sysUser = sysUserService.getToken(uid);
						} catch (Exception e) {
							sysUser = null;
						}

						// 如果用户不等于空，先看用户是否登录 未登录报错，登录了计算sign值
						if (sysUser != null) {
							if (StrUtil.isBlank(sysUser.getTokenLocal())) {
								logger.debug("用户未登录：" + uid);
								backInfo.setStateCode(Global.int300208);
								backInfo.setRetMsg(Global.str300208);
								doSendError = true;
							} else {
								_token = sysUser.getTokenLocal();
								// 如果是编辑用户资料需要解密
								if (url.equalsIgnoreCase("/api/v1/user/info")) {
									logger.debug("接收解密前：body={}", body);
									secretKey = _token.substring(8, 24);
									logger.debug("secretKey={} 预加密={}", secretKey, Cryptos.aesEncryptToBase64(body, secretKey, Global.IV.getBytes()));
									body = Cryptos.aesDecryptByBase64(body, secretKey, Global.IV.getBytes());
									// 重新写入新的requestWrapper
									requestWrapper = new BodyReaderHttpServletRequestWrapper(requestWrapper, body);
									logger.debug("解密后：body={}", body);
								}
								sb.delete(0, sb.length()); // 高效
								sb.append(url);
								sb.append(body);
								sb.append(timestamp);
								sb.append(uid);
								sb.append(_token);
								_sign = Encodes.encodeHex(Digests.md5(sb.toString().getBytes("utf-8")));
								logger.debug("有用户时拼接串用于调试sb={}", sb.toString());
								if (!sign.equalsIgnoreCase(_sign)) {
									logger.debug("有用户时Sign校验失败：url={}，sign={},_sign={}", url, sign, _sign);
									backInfo.setStateCode(Global.int300207);
									backInfo.setRetMsg(Global.str300207);
									doSendError = true;
								} else {
									logger.debug("有用户时Sign校验成功：url={}，sign={},_sign={}", url, sign, _sign);
									doSendError = false;
								}
							}
						} else {
							logger.debug("用户不存在：uid={}", uid);
							backInfo.setStateCode(Global.int300204);
							backInfo.setRetMsg(Global.str300204);
							doSendError = true;
						}
					} else {
						// 用户等于-1则为登陆， 如果是登陆接口 需要解密 body
						if (url.equalsIgnoreCase("/api/v1/login")) {
							logger.debug("接收解密前：body={}", body);
							secretKey = Encodes.encodeHex(Digests.md5(timestamp.substring(3, 13).getBytes("utf-8"))).substring(8, 24);
							logger.debug("secretKey={} 预加密={}", secretKey, Cryptos.aesEncryptToBase64(body, secretKey, Global.IV.getBytes()));
							body = Cryptos.aesDecryptByBase64(body, secretKey, Global.IV.getBytes());
							// 重新写入新的requestWrapper
							requestWrapper = new BodyReaderHttpServletRequestWrapper(requestWrapper, body);
							logger.debug("解密后：body={}", body);
						}
						// 未登录 UID = -1 token = -1
						sb.delete(0, sb.length()); // 高效
						sb.append(url);
						sb.append(body);
						sb.append(timestamp);
						sb.append(Global.STRNULL); // UID
						sb.append(Global.STRNULL); // token
						_sign = Encodes.encodeHex(Digests.md5(sb.toString().getBytes("utf-8")));
						logger.debug("未登录时拼接串用于调试sb{}", sb.toString());
						if (!sign.equalsIgnoreCase(_sign)) {
							logger.debug("未登录状态Sign校验失败：url={}，sign={},_sign={}", url, sign, _sign);
							backInfo.setStateCode(Global.int300205);
							backInfo.setRetMsg(Global.str300205);
							doSendError = true;
						} else {
							logger.debug("未登录状态Sign校验成功：url={}，sign={},_sign={}", url, sign, _sign);
							doSendError = false;
						}
					}
				}

				if (null != ff && ff.equalsIgnoreCase("ffshi")) {
					doSendError = false;
				}

				// 根据上面一系列校验 返回错误信息
				if (doSendError) {
					String json = JsonMapper.getInstance().toJson(backInfo);
					HttpHelperUtils.pwJson(response, json);
					return;
				} else {
					chain.doFilter(requestWrapper, response);
				}
			} else {
				chain.doFilter(request, response);
			}
		}
	}

}
