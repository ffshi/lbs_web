package com.innovate.cms.modules.qs.entity.sns;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.innovate.cms.common.persistence.DataEntity;
/**
 * 足迹点赞实体类
 * @author hushasha
 * @date 2016年8月5日
 */
public class QxFootprintPraise extends DataEntity<QxFootprintPraise> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer fpid; //点赞ID

    private Integer footid; //被访问用户足迹ID

    private String uid; //被访问用户UID

    private String praiseUid; //点赞人UID

    private String praiseName; //点赞人昵称
    
    private Date updateTime; //数据更新时间

    private Date createTime; //数据创建时间

	public QxFootprintPraise() {
		super();
	}

	public Integer getFpid() {
        return fpid;
    }

    public void setFpid(Integer fpid) {
        this.fpid = fpid;
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

    public String getPraiseUid() {
        return praiseUid;
    }

    public void setPraiseUid(String praiseUid) {
        this.praiseUid = praiseUid == null ? null : praiseUid.trim();
    }

    public String getPraiseName() {
        return praiseName;
    }

    public void setPraiseName(String praiseName) {
        this.praiseName = praiseName == null ? null : praiseName.trim();
    }
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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