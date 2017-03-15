/**
 * 
 */
package com.innovate.cms.modules.common.entity;

/**
 * 足迹相关返回json样式
 * @author hushasha
 * @date 2016年12月7日
 */
public class FootprintBackInfo<T> extends DataBackInfo<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 用户上传题目数
	private int ugcCount;
	// 用户关注人数
	private int followsNum;
	// 用户粉丝人数
	private int followersNum;
	public int getUgcCount() {
		return ugcCount;
	}
	public void setUgcCount(int ugcCount) {
		this.ugcCount = ugcCount;
	}
	public int getFollowsNum() {
		return followsNum;
	}
	public void setFollowsNum(int followsNum) {
		this.followsNum = followsNum;
	}
	public int getFollowersNum() {
		return followersNum;
	}
	public void setFollowersNum(int followersNum) {
		this.followersNum = followersNum;
	}

}
