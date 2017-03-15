/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.entity.ques;

import com.innovate.cms.common.persistence.DataEntity;

/**
 * 趣选答案选项统计表
 * @author shifangfang
 *
 */
public class QxQuestionsOptionCount extends DataEntity<QxQuestionsOptionCount> {

	private static final long serialVersionUID = 1L;

	private int count;

	public QxQuestionsOptionCount() {
		super();
	}

	public QxQuestionsOptionCount(int count) {
		super();
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}