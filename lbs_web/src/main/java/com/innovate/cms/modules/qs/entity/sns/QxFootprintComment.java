package com.innovate.cms.modules.qs.entity.sns;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.persistence.DataEntity;
/**
 * 足迹评论实体类
 * @author hushasha
 * @date 2016年8月5日
 */
public class QxFootprintComment extends DataEntity<QxFootprintComment> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer fcid; //评论ID

    private Integer footid; //用户足迹ID

    private String uid; //用户ID
    
    private String msgType; //评论类型（9评论,10评论回复）

    private String fromCommentUid; //评论人ID

    private String fromCommentName; //评论人昵称

    private String fromCommentImg; //评论人头像

    private String toCommentUid; //被评论人ID

    private String toCommentName; //被评论人昵称

    private String comment; //评论内容

    private Date updateTime; //数据更新时间

    private Date createTime; //数据创建时间
    
    public QxFootprintComment() {
		super();
	}

	public Integer getFcid() {
        return fcid;
    }

    public void setFcid(Integer fcid) {
        this.fcid = fcid;
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
        this.uid = uid == null ? null : uid.trim();
    }
    
    public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		 this.msgType = msgType == null ? null : msgType.trim();
	}

	public String getFromCommentUid() {
        return fromCommentUid;
    }

    public void setFromCommentUid(String fromCommentUid) {
        this.fromCommentUid = fromCommentUid == null ? null : fromCommentUid.trim();
    }

    public String getFromCommentName() {
        return fromCommentName;
    }

    public void setFromCommentName(String fromCommentName) {
        this.fromCommentName = fromCommentName == null ? null : fromCommentName.trim();
    }

    public String getFromCommentImg() {
		String imgUrl = this.fromCommentImg;
		if (imgUrl.length()>0)
		{
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS)))
			{
					imgUrl = Global._URL+imgUrl;
			}
		}
		return imgUrl;
    }

    public void setFromCommentImg(String fromCommentImg) {
        this.fromCommentImg = fromCommentImg == null ? null : fromCommentImg.trim();
    }

    public String getToCommentUid() {
        return toCommentUid;
    }

    public void setToCommentUid(String toCommentUid) {
        this.toCommentUid = toCommentUid == null ? null : toCommentUid.trim();
    }

    public String getToCommentName() {
        return toCommentName;
    }

    public void setToCommentName(String toCommentName) {
        this.toCommentName = toCommentName == null ? null : toCommentName.trim();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}