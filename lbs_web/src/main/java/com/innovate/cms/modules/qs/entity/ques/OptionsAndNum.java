package com.innovate.cms.modules.qs.entity.ques;

import java.io.Serializable;

import com.google.common.base.Strings;
import com.innovate.cms.common.config.Global;

/**
 * 问题选项及每个选项被选择的数量 返回样式
 * 
 * @author shifangfang
 *
 */
public class OptionsAndNum implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer gid; //专题ID
	private Integer qaid; //选项编号ID
	private Integer seqId; //选项序号1、2、3、4  对应 a、b、c、d
	private String option; //选项内容
	private int count; //选择该选项的数量
	public OptionsAndNum() {
		super();
	}
	public Integer getGid() {
		return gid;
	}
	public void setGid(Integer gid) {
		this.gid = gid;
	}
	public Integer getQaid() {
		return qaid;
	}
	public void setQaid(Integer qaid) {
		this.qaid = qaid;
	}
	public Integer getSeqId() {
		return seqId;
	}
	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}
	public String getOption() {
		String imgUrl = this.option;
		
		if (!Strings.isNullOrEmpty(imgUrl) && imgUrl.length() > 0) {
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS))) {
				imgUrl = Global._URL + imgUrl;
			}
		}
//		if (!Strings.isNullOrEmpty(imgUrl) && imgUrl.length() > 0) {
//			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS))) {
//				imgUrl = Global._URL + imgUrl;
//			}
//		}
		return imgUrl;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}
