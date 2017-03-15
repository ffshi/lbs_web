/**
 * 
 */
package com.innovate.cms.modules.qs.entity.ques;

import java.io.Serializable;

/**
 * @author hushasha
 * @date 2016年7月7日
 */
public class QuestionAnswer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer qid;
	private String answer;
	
	public QuestionAnswer() {
		super();
	}
	
	public QuestionAnswer(Integer qid, String answer) {
		super();
		this.qid = qid;
		this.answer = answer;
	}

	public Integer getQid() {
		return qid;
	}
	public void setQid(Integer qid) {
		this.qid = qid;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
}
