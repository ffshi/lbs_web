package com.innovate.cms.modules.common.entity;


/**
 * 营销推广专用
 * 
 * 专题接口返回json包装 /api/v1/ques/getQuestions4Extend
 * 
 * @author shifangfang
 * @date 2016年7月27日 下午4:25:28
 */
public class GroupQuesBackInfoExtend  extends GroupQuesBackInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Object config;

	public Object getConfig() {
		return config;
	}

	public void setConfig(Object config) {
		this.config = config;
	}

}
