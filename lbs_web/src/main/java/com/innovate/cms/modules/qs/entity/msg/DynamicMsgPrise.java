package com.innovate.cms.modules.qs.entity.msg;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.innovate.cms.common.persistence.DataEntity;

/**
 * user prise
 * @author shifangfang
 * @date 2017年3月17日 下午2:54:48
 */
public class DynamicMsgPrise extends DataEntity<DynamicMsgPrise>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	  `prise_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '点赞id',
//	  `mid` int(11) NOT NULL COMMENT '消息id',
//	  `uid` varchar(32) NOT NULL COMMENT '点赞用户uid',
//	  `headimgurl` varchar(1024) DEFAULT '',
//	  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
//	  `del_flag` tinyint(1) DEFAULT '0' COMMENT '取消标识0-点赞1-取消',

	private int priseId;
	private int mid;
	private String uid;
	private String headimgurl;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	public DynamicMsgPrise() {
		super();
	}
	public DynamicMsgPrise(String id) {
		super(id);
	}

	public int getPriseId() {
		return priseId;
	}
	public void setPriseId(int priseId) {
		this.priseId = priseId;
	}
	
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
