/**
 * 
 */
package com.innovate.cms.modules.qs.dao.ques;

import org.apache.ibatis.annotations.Param;
import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.menus.Menu;
import com.innovate.cms.modules.qs.entity.ques.QxStatNum;

/**
 * 用户每个频道做题数 DAO层
 * @author hushasha
 * @date 2016年8月23日
 */
@MyBatisDao
public interface QxStatNumDao extends CrudDao<QxStatNum> {

	/**  
	 * 根据fsid获取频道信息
	 * @param fsid
	 * @return  
	 */    
	
	Menu getChannelByFsid(@Param("fsid")Integer fsid);

	/**  
	 * 根据uid, cid 获取QxStatNum信息
	 * @param uid
	 * @param cid
	 * @return  
	 */    
	
	QxStatNum getQxStatNumByUidCid(@Param("uid")String uid, @Param("cid")Integer cid);

	/**  
	 * 更新QxStatNum信息
	 * @param qxStatNum  
	 */    
	
	void updateNum(QxStatNum qxStatNum);

	/**  
	 * 保存QxStatNum信息
	 * @param uid
	 * @param cid
	 */    
	
	void saveQxStatNum(@Param("uid")String uid, @Param("cid")Integer cid);

}
