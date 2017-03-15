/**
 * 
 */
package com.innovate.cms.modules.common.entity;

import java.util.List;

import com.innovate.cms.modules.qs.entity.ques.Result5;

/**
 * 返回对象数据Json样式
 * 
 * @author hushasha
 * @date 2016年4月20日
 */
public class ItemBackInfoDone extends ItemBackInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 集合数据
	 */
	private List<Result5> result;

	// 记录是否做过专题
	private int done;

	public ItemBackInfoDone() {
		super();
	}

	public int getDone() {
		return done;
	}

	public void setDone(int done) {
		this.done = done;
	}

	public List<Result5> getResult() {
		return result;
	}

	public void setResult(List<Result5> result) {
		this.result = result;
	}

	public ItemBackInfoDone(List<Result5> result, int done) {
		super();
		this.result = result;
		this.done = done;
	}

}
