/**
 * 
 */
package com.innovate.cms.modules.qs.dao.sns;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.data.entity.FollowDynamicStateToJson;
import com.innovate.cms.modules.qs.entity.sns.QxFollowDynamicState;

/**
 * 好友动态Dao层
 * @author hushasha
 * @date 2016年12月1日
 */
@MyBatisDao
public interface QxFollowDynamicStateDao extends CrudDao<QxFollowDynamicState> {

	/**  
	 * 保存用户创建专题记录到好友动态表
	 * @param uid
	 * @param gid
	 * @param groupName  
	 */    
	
	void saveCreateGroupDynamicState(@Param("uid")String uid, @Param("gid")Integer gid, @Param("groupName")String groupName);

	/**  
	 * 保存做题记录(模板0)到好友动态表
	 * @param uid
	 * @param gid
	 * @param templateTag  
	 */    
	
	void saveGroupZeroDynamicState(@Param("uid")String uid, @Param("gid")Integer gid, @Param("templateTag")Integer templateTag);

	/**  
	 * 保存做题记录(模板4)到好友动态表
	 * @param uid
	 * @param gid
	 * @param templateTag  
	 */    
	
	void saveGroupFourDynamicState(@Param("uid")String uid, @Param("gid")Integer gid, @Param("templateTag")Integer templateTag);

	/**  
	 * 获取好友动态---最新的20条数据
	 * @param uid
	 * @return  
	 */    
	
	List<FollowDynamicStateToJson> getFollowDynamicStateList(@Param("uid")String uid);

	/**  
	 * 上拉显示更多好友动态---小于dsid的20条数据
	 * @param uid
	 * @param dsid
	 * @return  
	 */    
	
	List<FollowDynamicStateToJson> showMoreDynamicStates(@Param("uid")String uid, @Param("dsid")Integer dsid);

	/**  
	 * 下拉刷新好友动态，显示大于dsid的所有数据
	 * @param uid
	 * @param dsid
	 * @return  
	 */    
	
	List<FollowDynamicStateToJson> refreshDynamicStates(@Param("uid")String uid, @Param("dsid")Integer dsid);

	/**  
	 * 获取足迹列表(显示最新的20条数据)
	 * @param uid
	 * @return  
	 */    
	
	List<FollowDynamicStateToJson> getFootprintList(@Param("uid")String uid);

	/**  
	 * 上拉显示更多足迹 (显示小于dsid的20条数据)
	 * @param uid
	 * @param dsid
	 * @return  
	 */    
	
	List<FollowDynamicStateToJson> showMoreFootprints(@Param("uid")String uid, @Param("dsid")Integer dsid);

	/**  
	 * 下拉刷新足迹--显示大于dsid的所有数据
	 * @param uid
	 * @param dsid
	 * @return  
	 */    
	
	List<FollowDynamicStateToJson> refreshFootprints(@Param("uid")String uid, @Param("dsid")Integer dsid);

}
