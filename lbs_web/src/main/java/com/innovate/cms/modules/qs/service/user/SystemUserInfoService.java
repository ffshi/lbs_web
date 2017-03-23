/**
 * Copyright &copy; 2012-2014 <a href="https://www.changyou.com/gaoji/cms">cms</a> All rights reserved.
 */
package com.innovate.cms.modules.qs.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.innovate.cms.common.persistence.Page;
import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.common.utils.RandomUtil;
import com.innovate.cms.common.utils.ReUtil;
import com.innovate.cms.common.utils.StringUtils;
import com.innovate.cms.modules.data.entity.BubbleInfoToJson;
import com.innovate.cms.modules.qs.dao.user.SystemUserInfoDao;
import com.innovate.cms.modules.qs.entity.user.RandomUser;
import com.innovate.cms.modules.qs.entity.user.SystemUserInfo;

/**
 * 系统用户信息Service
 * @author gaoji
 * @version 2016-04-12
 */
@Service
@Transactional(readOnly = true)
public class SystemUserInfoService extends CrudService<SystemUserInfoDao, SystemUserInfo> {

	public SystemUserInfo get(String id) {
		return super.get(id);
	}
	
	public List<SystemUserInfo> findList(SystemUserInfo systemUserInfo) {
		return super.findList(systemUserInfo);
	}
	
	public Page<SystemUserInfo> findPage(Page<SystemUserInfo> page, SystemUserInfo systemUserInfo) {
		return super.findPage(page, systemUserInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(SystemUserInfo systemUserInfo) {
		if (StringUtils.isBlank(systemUserInfo.getId()))	
		{
			systemUserInfo.setIsNewRecord(true);
		}else {
			systemUserInfo.setIsNewRecord(false);
		}
		super.save(systemUserInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(SystemUserInfo systemUserInfo) {
		super.delete(systemUserInfo);
	}
	
	/**
	 * 
	 * @Title: insertInfo
	 * @Description: toLogin-->saveUserAndInfo 登陆新增用户
	 * @param systemUserInfo    返回类型 void
	 *
	 */
	@Transactional(readOnly = false)
	public void insertInfo(SystemUserInfo systemUserInfo)
	{
		super.dao.insertInfo(systemUserInfo);
	}
	
	/**
	 * 
	 * @Title: updateInfo
	 * @Description: toLogin-->saveUserAndInfo 登陆修改用户
	 * @param systemUserInfo    返回类型 void
	 *
	 */
	@Transactional(readOnly = false)
	public void updateInfo(SystemUserInfo systemUserInfo)
	{
		super.dao.updateInfo(systemUserInfo);
	}

	/**  
	 *气泡推荐
	 * @param sex 性别：1男性 2女性
	 * @return  
	 */    
	
	public List<BubbleInfoToJson> getBubbleNominate(String sex) {
		List<BubbleInfoToJson> list = new ArrayList<BubbleInfoToJson>();
		String regex = "1|-1";
		int manNum = 0;
		int womanNum = 0;
		RandomUser randomUser = new RandomUser();
		if(ReUtil.isMatch(regex, sex)) { //按男女比例3:7来推荐交友
			randomUser = RandomUtil.randomUser("f");
		} else {//按男女比例7:3来推荐交友
			randomUser = RandomUtil.randomUser("m");
		}
		manNum = randomUser.getMan();
		womanNum = randomUser.getWoman();
		//logger.debug("---manNum: " + manNum + ", womanNum: " + womanNum);
		list = super.dao.getBubbleNorminate(womanNum, manNum);
		for(BubbleInfoToJson bubbleInfoToJson : list) {
			if(bubbleInfoToJson.getGid() != null) { //足迹有做题记录时,1:2的概率来进行随机推荐和专题推荐
				int ranNum = RandomUtil.getRandomNumByRate(0, 1, 33); //0表示随机推荐 1表示专题推荐
				//logger.debug("bubble type: " + ranNum);
				if(ranNum == 1) {
					bubbleInfoToJson.setType(1);
				}
			}
		}
		return list;
	}



}