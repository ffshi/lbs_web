package com.innovate.cms.modules.push.aliyun;
/**
 * 
 * @ClassName: PushGlobal
 * @Description: Push相关配置类
 * @author gaoji gaoji_cyou-inc_com
 * @date 2016年6月15日 上午12:42:02
 *
 */
public class PushGlobal
{
	//推送目标
	/**
	 * 推送给指定设备
	 */
	public static final String device = "device";
	/**
	 * 推送给指定帐号
	 */
	public static final String account = "account";
	/**
	 * 推送给指定Tag
	 */
	public static final String tag = "tag";
	/**
	 * 推送给全部设备
	 */
	public static final String all = "all";
	
	//根据Target来设定
	public static final String TargetAll = "all";
	
	// 设备类型deviceType 取值范围为:0~3. iOS设备: 0; Android设备: 1; 全部: 3, 这是默认值.
	/**
	 * 推送iOS系统
	 */
	public static final int dev_iOS = 0;
	/**
	 * 推送安卓系统
	 */
	public static final int dev_Android = 1;
	/**
	 * 推送所有系统
	 */
	public static final int dev_all = 3;
	
	// 推送配置  0:表示消息(默认为0), 1:表示通知
	/**
	 * 表示消息
	 */
	public static final int typeMessage = 0;
	/**
	 * 表示通知
	 */
	public static final int typeNotice  = 1;
	/**
	 * iOS 表示开发环境
	 */
	public static final String DEV = "DEV";
	/**
	 * iOS 表示生产环境
	 */
	public static final String PRODUCT = "PRODUCT";
	
	
	
}
