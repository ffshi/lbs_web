/**
 * 
 */
package com.innovate.cms.modules.qs.service.ques;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.modules.qs.dao.ques.QxStatNumDao;
import com.innovate.cms.modules.qs.entity.menus.Menu;
import com.innovate.cms.modules.qs.entity.ques.QxStatNum;

/**
 * 用户分渠道做题数 Service层
 * @author hushasha
 * @date 2016年8月23日
 */
@Service
@Transactional(readOnly = true)
public class QxStatNumService extends CrudService<QxStatNumDao, QxStatNum> {

	/**  
	 * 保存或更新 用户每个频道做题数量
	 * @param uid
	 * @param fsid  
	 */    
	
	@Transactional(readOnly = false)
	public void saveDoneNum(String uid, Integer fsid) {
		//根据fsid获取频道信息
		Menu channel = super.dao.getChannelByFsid(fsid);
		if(channel != null) {
			//判断该用户该频道是否有记录
			QxStatNum qxStatNum = super.dao.getQxStatNumByUidCid(uid, channel.getCid());
			if(qxStatNum != null) {
				Integer num = qxStatNum.getNum();
				qxStatNum.setNum(num + 1);
				super.dao.updateNum(qxStatNum);
			} else {
				super.dao.saveQxStatNum(uid, channel.getCid());
			}
		}
	}

}
