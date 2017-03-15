/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.menus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.common.utils.StrUtil;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.modules.qs.dao.menus.QxGroupsDao;
import com.innovate.cms.modules.qs.entity.menus.Group;
import com.innovate.cms.modules.qs.entity.menus.GroupRecommend;
import com.innovate.cms.modules.qs.entity.menus.Groups;
import com.innovate.cms.modules.qs.entity.menus.QxGroups;
import com.innovate.cms.modules.qs.entity.ques.QxQuestions;
import com.innovate.cms.modules.qs.entity.ques.QxQuestionsOption;

/**
 * 专题表Service
 * 
 * @author gaoji
 * @version 2016-04-01
 */
@Service
@Transactional(readOnly = true)
public class QxGroupsService extends CrudService<QxGroupsDao, QxGroups> {

	public QxGroups get(String id) {
		return super.get(id);
	}

	public List<QxGroups> findList(QxGroups qxGroups) {
		return super.findList(qxGroups);
	}

	@Transactional(readOnly = false)
	public void save(QxGroups qxGroups) {
		if (StringUtils.isBlank(qxGroups.getId())) {
			qxGroups.setIsNewRecord(true);
		} else {
			qxGroups.setIsNewRecord(false);
		}
		super.save(qxGroups);
	}

	@Transactional(readOnly = false)
	public void delete(QxGroups qxGroups) {
		super.delete(qxGroups);
	}

	/**
	 * 根据专题组fsid获取所有专题
	 * 
	 * @param fsid
	 * @return
	 */
	public List<Group> getGroupByfsid(int fsid) {
		return super.dao.getGroupByfsid(fsid);
	}

	/**
	 * 根据专题组id获取前4个热门专题
	 * 
	 * @param gid
	 *            专题id
	 * @param uid
	 *            用户id
	 * @return
	 */

	public List<GroupRecommend> getHotGroup(Integer gid, String uid) {
		List<GroupRecommend> list = new ArrayList<GroupRecommend>();
		// 根据gid获取对应的fsid
		Integer cid = super.dao.getCidByGid(gid);
		if (cid != null) {
			if (StrUtil.isNotBlank(uid)) { // 用户ID不为空
				list = super.dao.getHotGroupWithUid(cid, gid, uid);
			} else {
				list = super.dao.getHotGroup(cid, gid);
			}
		}
		return list;
	}

	/**
	 * 根据gid获取group相关信息
	 * 
	 * @param parseInt
	 * @return
	 */
	public Group getGroupBygid(int gid) {
		return super.dao.getGroupBygid(gid);
	}

	/**
	 * 随机专题推荐
	 * 
	 * @param gid
	 *            专题id
	 * @return
	 */

	public List<GroupRecommend> getRandomGroup(String gid) {
		return super.dao.getRandomGroup(Integer.valueOf(gid));
	}

	/**
	 * 专题点赞接口
	 * 
	 * @param gid
	 * @param flag
	 */
	@Transactional(readOnly = false)
	public void savePraiseOrDel(Integer gid, String flag) {
		if("0".equals(flag)) { //0点赞，点赞数加1
			super.dao.updatePraiseNumForAdding(gid);
		} else { //1取消点赞，点赞数减1
			super.dao.updatePraiseNumForSubtraction(gid);
		}
	}

	/**  
	 * 创建专题
	 * @param uid 创建者ID
	 * @param groupName 专题名称
	 * @param optionA 选项A
	 * @param optionB 选项B
	 * @return  专题ID
	 */    
	@Transactional(readOnly = false)
	public int saveEssentialGroup(String uid, String groupName, String optionA, String optionB) {
		Date now = new Date();
		//保存qx_groups专题
		QxGroups group = new QxGroups();
		group.setFsid(1);
		group.setGroupName(groupName);
		group.setTemplateTag("0");
		group.setUid(uid);
		group.setCreateTime(now);
		super.dao.saveGroup(group);
		//保存qx_questions问题
		int gid = group.getGid();
		QxQuestions question = new QxQuestions();
		question.setGid(gid);
		question.setTemplateTag("0");
		question.setSubject1Type("1"); //标题1类型 1文字2图片3声音4视频
		question.setSubject1(groupName);
		question.setCreateTime(now);
		super.dao.saveQuestion(question);
		//保存qx_questions_option选项
		int qid = question.getQid();
		List<QxQuestionsOption> optionsList = Lists.newArrayList();
		QxQuestionsOption option = new QxQuestionsOption();
		option.setQid(qid);
		option.setSeqId("1"); //选项序号1、2、3、4  对应 a、b、c、d
		option.setOptionType("2"); //选项类型 1文字2图片3声音4视频
		option.setOption(optionA);
		option.setRanksToResults("");
		option.setCreateTime(now);
		optionsList.add(option);
		QxQuestionsOption option2 = new QxQuestionsOption();
		option2.setQid(qid);
		option2.setSeqId("2"); //选项序号1、2、3、4  对应 a、b、c、d
		option2.setOptionType("2"); //选项类型 1文字2图片3声音4视频
		option2.setOption(optionB);
		option2.setRanksToResults("");
		option2.setCreateTime(now);
		optionsList.add(option2);
		super.dao.saveOptions(optionsList);
		return gid;
	}

	/**  
	 * 根据fsid获取专题列表--显示最新的10条数据
	 * @param fsid
	 * @return  
	 */    
	
	public List<Groups> getGroupsListByFsid(Integer fsid) {
		return super.dao.getGroupsListByFsid(fsid);
	}

	/**  
	 * 上拉显示更多专题--显示小于gid的10条数据
	 * @param fsid
	 * @param gid
	 * @return  
	 */    
	
	public List<Groups> showMoreGroupsByFsid(Integer fsid, Integer gid) {
		return super.dao.showMoreGroupsByFsid(fsid, gid);
	}

	/**  
	 * 下拉刷新专题列表--显示大于gid的所有数据
	 * @param fsid
	 * @param gid
	 * @return  
	 */    
	
	public List<Groups> refreshGroupsByFsid(Integer fsid, Integer gid) {
		return super.dao.refreshGroupsByFsid(fsid, gid);
	}
	
}