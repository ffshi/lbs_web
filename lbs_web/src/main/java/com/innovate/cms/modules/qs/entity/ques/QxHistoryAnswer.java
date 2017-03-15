package com.innovate.cms.modules.qs.entity.ques;

import com.innovate.cms.common.persistence.DataEntity;
/**
 * 
 * @ClassName: QxHistoryAnswer
 * @Description: 用户问题选项记录表
 * @author gaoji gaoji_cyou-inc_com
 * @date 2016年5月16日 下午2:34:42
 *
 */
public class QxHistoryAnswer extends DataEntity<QxHistoryAnswer> {
	
 
	private static final long serialVersionUID = 1L;
	private int haid    ;            //选项表ID
	private String uid  ;            //用户id
	private int gid     ;            //专题id
	private int qid     ;            //问题id
	private String answer  ;            //选项 A、B、C、D等
	
	public QxHistoryAnswer()
	{
		super();
	}
	public QxHistoryAnswer(String id)
	{
		super(id);
	}
	/**
	 * 
	 * <p>Title: 新增数据构造</p>
	 * <p>Description: </p>
	 * @param uid
	 * @param gid
	 * @param qid
	 * @param answer
	 */
	public QxHistoryAnswer(String uid, int gid, int qid, String answer)
	{
		super();
		this.uid = uid;
		this.gid = gid;
		this.qid = qid;
		this.answer = answer;
	}
	public int getHaid()
	{
		return haid;
	}
	public void setHaid(int haid)
	{
		this.haid = haid;
	}
	public String getUid()
	{
		return uid;
	}
	public void setUid(String uid)
	{
		this.uid = uid;
	}
	public int getGid()
	{
		return gid;
	}
	public void setGid(int gid)
	{
		this.gid = gid;
	}
	public int getQid()
	{
		return qid;
	}
	public void setQid(int qid)
	{
		this.qid = qid;
	}
	public String getAnswer()
	{
		return answer;
	}
	public void setAnswer(String answer)
	{
		this.answer = answer;
	}
}
