package com.innovate.cms.modules.common.entity;

/**
 * 建群后是否对报名人自动发出邀请 0-默认 1-不发 2-自动发
 * @author shifangfang
 * @date 2017年9月11日 上午11:48:45
 * @param <T>
 */
public class AutoInviteBackInfo<T> extends DataBackInfo<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int autoInvite;

	public int getAutoInvite() {
		return autoInvite;
	}

	public void setAutoInvite(int autoInvite) {
		this.autoInvite = autoInvite;
	}
}
