package com.innovate.cms.modules.push.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.innovate.cms.common.config.Global;


public class BasePushClient
{
	private   static final String REGION_HANGZHOU = "cn-hangzhou";
	protected static DefaultAcsClient client  = null;
	
	public static void init()
	{
		if (null == client)
		{
			initClient();
		}
	}

	private static void initClient()
	{
		IClientProfile profile = DefaultProfile.getProfile(REGION_HANGZHOU, Global.accessKeyId, Global.accessKeySecret);
		client = new DefaultAcsClient(profile);
	}
}
