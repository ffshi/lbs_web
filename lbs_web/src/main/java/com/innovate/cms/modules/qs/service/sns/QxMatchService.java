package com.innovate.cms.modules.qs.service.sns;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.modules.data.entity.ChatBoxMatchToJson;
import com.innovate.cms.modules.data.entity.FootprintRankToJson;
import com.innovate.cms.modules.data.entity.Templet4RankExtendToJson;
import com.innovate.cms.modules.data.entity.Templet4RankToJson;
import com.innovate.cms.modules.data.entity.TempletRankToJson;
import com.innovate.cms.modules.qs.dao.sns.QxMatchDao;
import com.innovate.cms.modules.qs.entity.sns.QxMatch;

/**
 * 做题匹配结果Service
 * 
 * @author gaoji
 * @version 2016-05-10
 */
@Service
@Transactional(readOnly = true)
public class QxMatchService extends CrudService<QxMatchDao, QxMatch> {
	/**
	 * 
	 * @Title: findRankByFootprint
	 * @Description: 查询足迹排行榜 前50个人
	 * @param uid
	 * @param gid
	 * @param page
	 * @return 返回类型 List<FootprintRankToJson>
	 *
	 */
	public List<FootprintRankToJson> findRankByFootprint(String uid, int gid) {
		return super.dao.findRankByFootprintList(uid, gid);
	}

	/**
	 * 
	 * @Title: findRankByTemplet5
	 * @Description: 查询模板5结果集 前50个人
	 * @param uid
	 * @param gid
	 * @param page
	 * @return 返回类型 List<TempletRankToJson>
	 *
	 */
	public List<TempletRankToJson> findRankByTemplet5(String uid, int gid) {
		return super.dao.findRankByTemplet5List(uid, gid);
	}

	/**
	 * 查询模板5结果集 前50个人,按时间倒序输出
	 * 
	 * @param uid
	 * @param _gid
	 * @return
	 */
	public List<TempletRankToJson> findRankByTemplet5ListTimeDesc(String uid, int gid) {
		return super.dao.findRankByTemplet5ListTimeDesc(uid, gid);
	}

	/**
	 * h5专用 查询模板5结果集 前50个人,按时间倒序输出
	 * 
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<TempletRankToJson> findRankByTemplet5ListTimeDescH5(String uid, int gid) {
		return super.dao.findRankByTemplet5ListTimeDescH5(uid, gid);
	}

	/**
	 * 
	 * @Title: findRankByTemplet4
	 * @Description: 模板四 排行榜 按照时间倒序（简单查询不是真正意义的排行榜）
	 * @param uid
	 * @param _gid
	 * @return 返回类型 List<Templet4RankToJson>
	 *
	 */
	public List<Templet4RankToJson> findRankByTemplet4(String uid, int gid) {
		return super.dao.findRankByTemplet4List(uid, gid);
	}

	/**
	 * 
	 * @Title: updateRankDatas
	 * @Description: 查询是否有需要更新的好友Rank 如果有则更新
	 * @param uid
	 * @param gid
	 *            返回类型 void
	 *
	 */
	@Transactional(readOnly = false)
	public void updateRankDatas(String uid, int gid) {
		// 1.查询 是否有需要更新的人
		// 2.如果有则更新
		int isUpdate = super.dao.findIsNewFollow(uid, gid);
		logger.debug("需要更新的人数为{}人", isUpdate);
		if (isUpdate > 0) {
			super.dao.addNewRankData(uid, gid);
		}
	}

	/**
	 * 用 QxMatch() 构造方法创建对象 用完整对象生成
	 * 
	 * @see com.innovate.cms.modules.qs.entity.sns.QxMatch
	 * @Title: addNewMatch
	 * @Description: 单条新增（某用户+某专题）新对比度
	 * @param qxMatch
	 *            返回类型 void
	 *
	 */
	@Transactional(readOnly = false)
	public int addNewMatch(QxMatch qxMatch) {
		return super.dao.addNewMatch(qxMatch);
	}

	/**
	 * 
	 * @Title: addNewMatch
	 * @Description: 直接生成一条匹配记录 (用关联查询生成)
	 * @param gid
	 *            专题ID
	 * @param myUid
	 *            我的uid
	 * @param followUid
	 *            被匹配人的uid
	 * @param matchResult
	 *            匹配百分比
	 * @return 返回类型 int
	 *
	 */
	@Transactional(readOnly = false)
	public int addNewMatch(int gid, String myUid, String followUid, Double matchResult) {
		return super.dao.addNewMatch2(gid, myUid, followUid, matchResult);
	}
	
	/**
	 * 添加陌生人匹配
	 * @param gid
	 * @param myUid
	 * @param followUid
	 * @param matchResult
	 * @return
	 */
	@Transactional(readOnly = false)
	public int addNewMatchStranger(int gid, String myUid, String followUid, Double matchResult) {
		return super.dao.addNewMatchStranger(gid, myUid, followUid, matchResult);
	}

	/**
	 * 
	 * @Title: addNewMatch
	 * @Description: 直接生成一条h5的匹配记录 (用关联查询生成)
	 * @param gid
	 *            专题ID
	 * @param myUid
	 *            我的uid
	 * @param followUid
	 *            被匹配人的uid
	 * @param matchResult
	 *            匹配百分比
	 * @return 返回类型 int
	 *
	 */
	@Transactional(readOnly = false)
	public int addNewMatchH5(int gid, String myUid, String followUid, Double matchResult) {
		return super.dao.addNewMatchH5(gid, myUid, followUid, matchResult);
	}

	public List<TempletRankToJson> findRankByTemplet5ListH5(String uid, int gid) {
		return super.dao.findRankByTemplet5ListH5(uid, gid);
	}

	/**
	 * 根据uid更新模板五匹配度的头像和昵称
	 * 
	 * @param uid
	 * @param nickname
	 * @param headimgurl
	 */
	@Transactional(readOnly = false)
	public void updateMatchData(String uid, String nickname, String headimgurl) {
		super.dao.updateMatchData(uid, nickname, headimgurl);
	}

	/**
	 * h5模板4判断是否存在匹配数据
	 * 
	 * @param gid
	 * @param myUid
	 *            分享人uid
	 * @param followUid
	 *            做题人uid
	 * @return
	 */
	@Transactional(readOnly = false)
	public int isExisth5t4(int gid, String myUid, String followUid) {
		return super.dao.isExisth5t4(gid, myUid, followUid);
	}

	/**
	 * h5模板4更新排行榜数据
	 * 
	 * @param gid
	 * @param myUid
	 *            分享人uid
	 * @param followUid
	 *            做题人uid
	 * @return
	 * @param results
	 *            测试结果(美人鱼/大乌龟)
	 */
	@Transactional(readOnly = false)
	public void updateMatchResh5t4(int gid, String myUid, String followUid, String results) {
		super.dao.updateMatchResh5t4(gid, myUid, followUid, results);
	}

	/**
	 * h5模板4添加排行榜数据
	 * 
	 * @param gid
	 * @param myUid
	 *            分享人uid
	 * @param followUid
	 *            做题人uid
	 * @return
	 * @param results
	 *            测试结果(美人鱼/大乌龟)
	 */
	@Transactional(readOnly = false)
	public void addNewMatchh5t4(int gid, String myUid, String followUid, String results) {
		super.dao.addNewMatchh5t4(gid, myUid, followUid, results);

	}

	
	/**
	 * h5模板4排行榜获取
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<Templet4RankToJson> findRankByTemplet4h5(String uid, int gid) {
		
		return super.dao.findRankByTemplet4h5(uid,gid);
	}

	/**
	 *  h5推广模板4排行榜获取
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<Templet4RankExtendToJson> findRankByTemplet4h5Extend(String uid, int gid) {
		return super.dao.findRankByTemplet4h5Extend(uid,gid);
	}
	/**
	 * 
	 * @Title: getChatBoxMatch
	 * @Description:聊天窗口第一次展现匹配度
	 * @param fromUid
	 * @param toUid
	 * @return    返回类型 List<ChatBoxMatchToJson>
	 *
	 */
	public List<ChatBoxMatchToJson> getChatBoxMatch(String fromUid, String toUid)
	{
		
		return super.dao.getChatBoxMatch(fromUid, toUid, Global.IM_MATCH_RANGE, Global.IM_MATCH_LIMIT);
	}

	/**
	 * 设置模板5匹配度可见
	 * @param uid
	 * @param gid
	 * @param visible
	 */
	@Transactional(readOnly = false)
	public void setVisible(String uid, int gid, int visible) {
		super.dao.setVisible(uid,gid,visible);
	}

}
