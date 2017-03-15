/**
 * 
 */
package com.innovate.cms.modules.qs.dao.label;

import java.util.List;

import com.innovate.cms.common.persistence.CrudDao;
import com.innovate.cms.common.persistence.annotation.MyBatisDao;
import com.innovate.cms.modules.qs.entity.label.OptionLabel;
import com.innovate.cms.modules.qs.entity.label.QxLabelThirdLevel;
import com.innovate.cms.modules.qs.entity.label.QxLabelUser;
import com.innovate.cms.modules.qs.entity.label.ResultLabel;

/**
 * 用户选项标签、模板4结果页标签Dao层
 * @author hushasha
 * @date 2016年8月31日
 */
@MyBatisDao
public interface QxLabelDao extends CrudDao<QxLabelThirdLevel> {

	/**  
	 * 获取运营配置的选项标签
	 * @param gid
	 * @return  
	 */    
	
	List<OptionLabel> getOptionLabel(Integer gid);

	/**  
	 * 获取运营配置的模板4结果页标签
	 * @param gid
	 * @return  
	 */    
	
	List<ResultLabel> getResultPageLabel(Integer gid);

	/**  
	 * 保存用户标签：如果主键重复，则更新
	 * @param labelUserList  
	 */    
	
	void saveOrUpdate(List<QxLabelUser> labelUserList);

}
