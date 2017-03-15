/**
 * 
 */
package com.innovate.cms.modules.qs.entity.sns;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.innovate.cms.common.config.Global;

/**
 * 足迹详情json样式
 * @author hushasha
 * @date 2016年8月10日
 */
public class FootprintWithPraiseAndComment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer footid;//客户端需要缓存      int(11) NOT NULL AUTO_INCREMENT COMMENT '用户消息id',
	private String uid;//      varchar(64) NOT NULL COMMENT '用户uid',
	private String footName;//      varchar(200) NOT NULL DEFAULT '' COMMENT '用户昵称',
	private String footImg;//      varchar(300) NOT NULL DEFAULT '' COMMENT '用户头像',
	private String footType;//      varchar(2) NOT NULL COMMENT '足迹类型（1、专题 2、模板五无数据 3、模板5有数据）',
	private String footToId;//      varchar(64) NOT NULL COMMENT '对应请求id',
	private String footContent;//      varchar(300) NOT NULL DEFAULT '' COMMENT '足迹文字描述',
	private String footResult;//varchar(300) NOT NULL DEFAULT '' COMMENT '专题做题结果',
	private String footContentImg;//      varchar(300) NOT NULL DEFAULT '' COMMENT '足迹图片描述',
	private String footVsUid;//      varchar(64) NOT NULL DEFAULT '' COMMENT '交互人uid',
	private String footVsName;
	private String footVsImg;//      varchar(300) NOT NULL DEFAULT '' COMMENT '交互人头像',
	private String footVsResult;//      varchar(64) NOT NULL DEFAULT '' COMMENT '交互结果',
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;//      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新时间',
	private String footResultDesc;//模板4足迹做题结果描述
	private List<QxFootprintPraise> praiseList; // 足迹点赞列表
	private List<QxFootprintComment> commentList; //足迹评论列表
	
	public FootprintWithPraiseAndComment() {
		super();
	}

	public FootprintWithPraiseAndComment(Integer footid, String uid, String footName, String footImg, String footType,
			String footToId, String footContent, String footResult, String footContentImg, String footVsUid,
			String footVsName, String footVsImg, String footVsResult, Date updateTime, String footResultDesc,
			List<QxFootprintPraise> praiseList, List<QxFootprintComment> commentList) {
		super();
		this.footid = footid;
		this.uid = uid;
		this.footName = footName;
		this.footImg = footImg;
		this.footType = footType;
		this.footToId = footToId;
		this.footContent = footContent;
		this.footResult = footResult;
		this.footContentImg = footContentImg;
		this.footVsUid = footVsUid;
		this.footVsName = footVsName;
		this.footVsImg = footVsImg;
		this.footVsResult = footVsResult;
		this.updateTime = updateTime;
		this.footResultDesc = footResultDesc;
		this.praiseList = praiseList;
		this.commentList = commentList;
	}



	public Integer getFootid() {
		return footid;
	}

	public void setFootid(Integer footid) {
		this.footid = footid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFootName() {
		return footName;
	}

	public void setFootName(String footName) {
		this.footName = footName;
	}

	public String getFootImg() {
		String imgUrl = this.footImg;
		if (imgUrl.length()>0)
		{
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS)))
			{
					imgUrl = Global._URL+imgUrl;
			}
		}
		return imgUrl;
	}

	public void setFootImg(String footImg) {
		this.footImg = footImg;
	}

	public String getFootType() {
		return footType;
	}

	public void setFootType(String footType) {
		this.footType = footType;
	}

	public String getFootToId() {
		return footToId;
	}

	public void setFootToId(String footToId) {
		this.footToId = footToId;
	}

	public String getFootContent() {
		return footContent;
	}

	public void setFootContent(String footContent) {
		this.footContent = footContent;
	}

	public String getFootResult() {
		return footResult;
	}

	public void setFootResult(String footResult) {
		this.footResult = footResult;
	}

	public String getFootContentImg() {
		return footContentImg;
	}

	public void setFootContentImg(String footContentImg) {
		this.footContentImg = footContentImg;
	}

	public String getFootVsUid() {
		return footVsUid;
	}

	public void setFootVsUid(String footVsUid) {
		this.footVsUid = footVsUid;
	}

	public String getFootVsName() {
		return footVsName;
	}

	public void setFootVsName(String footVsName) {
		this.footVsName = footVsName;
	}

	public String getFootVsImg() {
		String imgUrl = this.footVsImg;
		if (imgUrl.length()>0)
		{
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS)))
			{
					imgUrl = Global._URL+imgUrl;
			}
		}
		return imgUrl;
	}

	public void setFootVsImg(String footVsImg) {
		this.footVsImg = footVsImg;
	}

	public String getFootVsResult() {
		return footVsResult;
	}

	public void setFootVsResult(String footVsResult) {
		this.footVsResult = footVsResult;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getFootResultDesc() {
		return footResultDesc;
	}

	public void setFootResultDesc(String footResultDesc) {
		this.footResultDesc = footResultDesc;
	}
	@JsonInclude(Include.ALWAYS)
	public List<QxFootprintPraise> getPraiseList() {
		return praiseList;
	}

	public void setPraiseList(List<QxFootprintPraise> praiseList) {
		this.praiseList = praiseList;
	}
	@JsonInclude(Include.ALWAYS)
	public List<QxFootprintComment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<QxFootprintComment> commentList) {
		this.commentList = commentList;
	}
	
}
