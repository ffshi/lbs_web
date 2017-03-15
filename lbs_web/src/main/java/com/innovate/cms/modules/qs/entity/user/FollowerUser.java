package com.innovate.cms.modules.qs.entity.user;

/**
 * 关注好友
 * @author shifangfang
 * @date 2016年11月10日 下午3:12:35
 */
public class FollowerUser extends SystemUser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//是否需要关注：0-需要 1-不需要
	private int needFollow;

	public int getNeedFollow() {
		return needFollow;
	}

	public void setNeedFollow(int needFollow) {
		this.needFollow = needFollow;
	}
}
