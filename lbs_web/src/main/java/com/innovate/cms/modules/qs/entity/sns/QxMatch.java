package com.innovate.cms.modules.qs.entity.sns;

import java.util.Date;

import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.persistence.DataEntity;

public class QxMatch extends DataEntity<QxMatch>
{

	private static final long serialVersionUID = 1L;

	private int qmid; // 匹配度id
	private int gid; // 专题ID
	private String myUnionid; // 我的Unionid
	private String myUid; // 我的用户ID
	private String myImg; // 我的头像
	private String myName; // 我的昵称
	private String followUnionid; // 好友Unionid
	private String followUid; // 好友用户ID
	private String followImg; // 好友头像
	private String followName; // 好友昵称
	private Double matchResult; // 匹配百分比
	private String zoneContent; // 匹配描述
	private Date updateTime; // 更新时间
	private Date createTime; // 创建时间
	private String delFlag; // 删除标志

	public QxMatch()
	{
		super();
	}

	public QxMatch(String id)
	{
		super(id);
	}

	/**
	 * 
	 * <p>Title:新增匹配度构造 </p>
	 * <p>Description: 此构造用于新增匹配度数据</p>
	 * @param gid
	 * @param myUnionid
	 * @param myUid
	 * @param myImg
	 * @param myName
	 * @param followUnionid
	 * @param followUid
	 * @param followImg
	 * @param followName
	 * @param matchResult
	 * @param zoneContent
	 * @param createTime
	 */
	public QxMatch(int gid, String myUnionid, String myUid, String myImg, String myName, String followUnionid, String followUid,
			String followImg, String followName, Double matchResult, String zoneContent, Date createTime)
	{
		super();
		this.gid = gid;
		this.myUnionid = myUnionid;
		this.myUid = myUid;
		this.myImg = myImg;
		this.myName = myName;
		this.followUnionid = followUnionid;
		this.followUid = followUid;
		this.followImg = followImg;
		this.followName = followName;
		this.matchResult = matchResult;
		this.zoneContent = zoneContent;
		this.createTime = createTime;
	}

	public int getQmid()
	{
		return qmid;
	}

	public void setQmid(int qmid)
	{
		this.qmid = qmid;
	}

	public int getGid()
	{
		return gid;
	}

	public void setGid(int gid)
	{
		this.gid = gid;
	}

	public String getMyUnionid()
	{
		return myUnionid;
	}

	public void setMyUnionid(String myUnionid)
	{
		this.myUnionid = myUnionid;
	}

	public String getMyUid()
	{
		return myUid;
	}

	public void setMyUid(String myUid)
	{
		this.myUid = myUid;
	}

	public String getMyImg()
	{
		String imgUrl = this.myImg;
		if (imgUrl.length()>0)
		{
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS)))
			{
					imgUrl = Global._URL+imgUrl;
			}
		}
		return imgUrl;
	}

	public void setMyImg(String myImg)
	{
		this.myImg = myImg;
	}

	public String getMyName()
	{
		return myName;
	}

	public void setMyName(String myName)
	{
		this.myName = myName;
	}

	public String getFollowUnionid()
	{
		return followUnionid;
	}

	public void setFollowUnionid(String followUnionid)
	{
		this.followUnionid = followUnionid;
	}

	public String getFollowUid()
	{
		return followUid;
	}

	public void setFollowUid(String followUid)
	{
		this.followUid = followUid;
	}

	public String getFollowImg()
	{
		String imgUrl = this.followImg;
		if (imgUrl.length()>0)
		{
			if (!(imgUrl.toLowerCase().trim().startsWith(Global.HTTP)||imgUrl.toLowerCase().trim().startsWith(Global.HTTPS)))
			{
					imgUrl = Global._URL+imgUrl;
			}
		}
		return imgUrl;
	}

	public void setFollowImg(String followImg)
	{
		this.followImg = followImg;
	}

	public String getFollowName()
	{
		return followName;
	}

	public void setFollowName(String followName)
	{
		this.followName = followName;
	}

	public Double getMatchResult()
	{
		return matchResult;
	}

	public void setMatchResult(Double matchResult)
	{
		this.matchResult = matchResult;
	}

	public String getZoneContent()
	{
		return zoneContent;
	}

	public void setZoneContent(String zoneContent)
	{
		this.zoneContent = zoneContent;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getDelFlag()
	{
		return delFlag;
	}

	public void setDelFlag(String delFlag)
	{
		this.delFlag = delFlag;
	}

}
