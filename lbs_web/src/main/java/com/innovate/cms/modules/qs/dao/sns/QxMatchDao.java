package com.innovate.cms.modules.qs.dao.sns;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.data.entity.ChatBoxMatchToJson;
import com.innovate.cms.modules.data.entity.FootprintRankToJson;
import com.innovate.cms.modules.data.entity.Templet4RankExtendToJson;
import com.innovate.cms.modules.data.entity.Templet4RankToJson;
import com.innovate.cms.modules.data.entity.TempletRankToJson;
import com.innovate.cms.modules.qs.entity.sns.QxMatch;

/*package com.innovate.cms.modules.qs.dao.sns;

 import java.util.List;

 import org.apache.ibatis.annotations.Param;

 import com.innovate.cms.common.persistence.CrudDao;
 import com.innovate.cms.common.persistence.annotation.MyBatisDao;
 import com.innovate.cms.modules.data.entity.FootprintRankToJson;
 import com.innovate.cms.modules.qs.entity.sns.QxMatch;
 /**
 * 匹配结果
 * @author gaoji
 * @version 2016-05-10
 */

@MyBatisDao
public interface QxMatchDao extends CrudDao<QxMatch> {
	/**
	 * 查询足迹排行榜
	 * 
	 * @Title: findRankByFootprintList
	 * @Description: 查询足迹排行榜列表  zone_content 修改为 title 2016-08-17 蓝图需求
	 * @param uid
	 * @param gid
	 * @return 返回类型 List<FootprintRankToJson>
	 *
	 */
	public List<FootprintRankToJson> findRankByFootprintList(@Param("uid") String uid, @Param("gid") int gid);

	/**
	 * 
	 * @Title: findRankByTemplet5List
	 * @Description: 查询模板5排行榜
	 * @param uid
	 * @param gid
	 * @return 返回类型 List<TempletRankToJson>
	 *
	 */
	public List<TempletRankToJson> findRankByTemplet5List(@Param("uid") String uid, @Param("gid") int gid);

	/**
	 * H5专用
	 * 
	 * 查询模板5排行榜
	 * 
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<TempletRankToJson> findRankByTemplet5ListH5(@Param("uid") String uid, @Param("gid") int gid);

	/**
	 * 查询模板5结果集 前50个人,按时间倒序输出
	 * 
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<TempletRankToJson> findRankByTemplet5ListTimeDesc(@Param("uid") String uid, @Param("gid") int gid);

	/**
	 * h5专用 查询模板5结果集 前50个人,按时间倒序输出
	 * 
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<TempletRankToJson> findRankByTemplet5ListTimeDescH5(@Param("uid") String uid, @Param("gid") int gid);

	/**
	 * 
	 * @Title: findRankByTemplet4List
	 * @Description: 查询模板四排行榜
	 * @param uid
	 * @param gid
	 * @return 返回类型 List<Templet4RankToJson>
	 *
	 */
	public List<Templet4RankToJson> findRankByTemplet4List(@Param("uid") String uid, @Param("gid") int gid);

	/**
	 * 
	 * @Title: findIsNewFollow
	 * @Description: 查询（某用户+某专题）是否有可更新的对比度数据
	 * @param uid
	 * @param gid
	 * @return 返回类型 int
	 *
	 */
	public int findIsNewFollow(@Param("uid") String uid, @Param("gid") int gid);

	/**
	 * 
	 * @Title: addNewRankData
	 * @Description: 批量新增（某用户+某专题）新的对比度
	 * @param uid
	 * @param gid
	 *            返回类型 void
	 *
	 */
	public void addNewRankData(@Param("uid") String uid, @Param("gid") int gid);

	/**
	 * 用 QxMatch() 构造方法创建对象
	 * 
	 * @see com.innovate.cms.modules.qs.entity.sns.QxMatch
	 * @Title: addNewMatch
	 * @Description: 单条新增（某用户+某专题）新对比度
	 * @param qxMatch
	 *            返回类型 void
	 *
	 */
	public int addNewMatch(QxMatch qxMatch);

	/**
	 * 
	 * @Title: addNewMatch
	 * @Description: 直接生成一条匹配记录
	 * @param gid
	 * @param myUid
	 * @param followUid
	 * @param matchResult
	 * @return 返回类型 int
	 *
	 */
	public int addNewMatch2(@Param("gid") int gid, @Param("myUid") String myUid, @Param("followUid") String followUid, @Param("matchResult") Double matchResult);
	
	
	/**
	 * 添加陌生人匹配
	 * @param gid
	 * @param myUid
	 * @param followUid
	 * @param matchResult
	 * @return
	 */
	public int addNewMatchStranger(@Param("gid") int gid, @Param("myUid") String myUid, @Param("followUid") String followUid, @Param("matchResult") Double matchResult);

	/**
	 * 
	 * @Title: addNewMatch
	 * @Description: 直接生成一条H5的匹配记录
	 * @param gid
	 * @param myUid
	 * @param followUid
	 * @param matchResult
	 * @return 返回类型 int
	 *
	 */
	public int addNewMatchH5(@Param("gid") int gid, @Param("myUid") String myUid, @Param("followUid") String followUid, @Param("matchResult") Double matchResult);

	/**
	 * 根据uid更新模板五匹配度的头像和昵称
	 * 
	 * @param uid
	 * @param nickname
	 * @param headimgurl
	 */

	public void updateMatchData(@Param("uid") String uid, @Param("nickname") String nickname, @Param("headimgurl") String headimgurl);

	/**
	 * 
	 *h5模板4判断是否存在匹配数据
	 * @param gid
	 * @param myUid 分享人uid
	 * @param followUid 做题人uid
	 * @return
	 */
	public int isExisth5t4(@Param("gid") int gid, @Param("myUid") String myUid, @Param("followUid") String followUid);

	/**
	 * h5模板4更新排行榜数据
		 * @param gid
	 * @param myUid 分享人uid
	 * @param followUid 做题人uid
	 * @return
	 * @param results 测试结果(美人鱼/大乌龟)
	 */
	public void updateMatchResh5t4(@Param("gid") int gid, @Param("myUid") String myUid, @Param("followUid") String followUid,@Param("results") String results);

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
	public void addNewMatchh5t4(@Param("gid") int gid, @Param("myUid") String myUid, @Param("followUid") String followUid,@Param("results") String results);

	
	/**
	 * h5模板4排行榜获取
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<Templet4RankToJson> findRankByTemplet4h5(@Param("uid") String uid, @Param("gid") int gid);

	/**
	 *  h5推广模板4排行榜获取
	 * @param uid
	 * @param gid
	 * @return
	 */
	public List<Templet4RankExtendToJson> findRankByTemplet4h5Extend(@Param("uid") String uid, @Param("gid") int gid);
	/**
	 * 
	 * @Title: getChatBoxMatch
	 * @Description: 聊天窗口第一次展现匹配度
	 * @param fromUid
	 * @param toUid
	 * @return    返回类型 List<ChatBoxMatchToJson>
	 *
	 */
	public List<ChatBoxMatchToJson> getChatBoxMatch(@Param("fromUid") String fromUid, @Param("toUid")String toUid,@Param("range") double imMatchRange, @Param("limit")int limit);

	/**
	 * 设置模板5匹配度可见
	 * @param uid
	 * @param gid
	 * @param visible
	 */
	public void setVisible(@Param("uid")String uid, @Param("gid")int gid, @Param("visible")int visible);

}
