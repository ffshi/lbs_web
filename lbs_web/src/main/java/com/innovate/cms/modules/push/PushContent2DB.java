package com.innovate.cms.modules.push;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 数据库存储推送记录
 * 
 * @author shifangfang
 * @date 2017年9月4日 下午6:06:11
 */
public class PushContent2DB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// `push_con_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '推送内容记录id',
	// `puid` varchar(32) DEFAULT '' COMMENT '发起推送的用户',
	// `uid` varchar(32) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT
	// '接收推送的用户',
	// `mid` int(11) DEFAULT '0' COMMENT '消息id',
	// `content` varchar(512) CHARACTER SET utf8 DEFAULT '' COMMENT '推送内容',
	// `ptype` tinyint(4) DEFAULT '0' COMMENT '推送类型',
	// `jump_id` varchar(64) DEFAULT '' COMMENT '推送跳转id',
	// `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE
	// CURRENT_TIMESTAMP,
	@JSONField(name = "push_con_id")
	private int pushConId;

	@JSONField(name = "puid")
	private String puid = "";

	@JSONField(name = "uid")
	private String uid = "";

	@JSONField(name = "content")
	private String content = "";

	@JSONField(name = "ptype")
	private int ptype;

	@JSONField(name = "mid")
	private int mid;

	@JSONField(name = "jump_id")
	private String jumpId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JSONField(name = "create_time")
	private Date createTime;

	private String headimgurl;
	private String nickname;

	// 群发通知推送id
	@JSONField(name = "mass_push_id")
	private long massPushId;

	public PushContent2DB() {
		super();
	}

	public PushContent2DB(String puid, String uid, String content, int ptype, int mid, String jumpId) {
		super();
		this.puid = puid;
		this.uid = uid;
		this.content = content;
		this.ptype = ptype;
		this.mid = mid;
		this.jumpId = jumpId;
	}

	public PushContent2DB(String puid, String uid, String content, int ptype, int mid, String jumpId, long massPushId) {
		super();
		this.puid = puid;
		this.uid = uid;
		this.content = content;
		this.ptype = ptype;
		this.mid = mid;
		this.jumpId = jumpId;
		this.massPushId = massPushId;
	}

	public long getMassPushId() {
		return massPushId;
	}

	public void setMassPushId(long massPushId) {
		this.massPushId = massPushId;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getPushConId() {
		return pushConId;
	}

	public void setPushConId(int pushConId) {
		this.pushConId = pushConId;
	}

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getPtype() {
		return ptype;
	}

	public void setPtype(int ptype) {
		this.ptype = ptype;
	}

	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getJumpId() {
		return jumpId;
	}

	public void setJumpId(String jumpId) {
		this.jumpId = jumpId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
