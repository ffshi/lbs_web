/**
 * 
 */
package com.innovate.cms.modules.qs.service.label;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.innovate.cms.common.config.Global;
import com.innovate.cms.common.mapper.BeanMapper;
import com.innovate.cms.common.service.CrudService;
import com.innovate.cms.modules.qs.dao.label.QxLabelDao;
import com.innovate.cms.modules.qs.entity.label.OptionLabel;
import com.innovate.cms.modules.qs.entity.label.QxLabelThirdLevel;
import com.innovate.cms.modules.qs.entity.label.QxLabelUser;
import com.innovate.cms.modules.qs.entity.label.ResultLabel;

/**
 * 用户选项标签和模板4结果页标签service层
 * 
 * @author hushasha
 * @date 2016年8月31日
 */
@Service
@Transactional(readOnly = true)
public class QxLabelService extends CrudService<QxLabelDao, QxLabelThirdLevel> {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 保存用户选项标签和模板4结果页标签
	 * @param uid
	 * @param gid
	 * @param resultsTag
	 * @param qidAnswer
	 * @param templateTag
	 */
	@Transactional(readOnly = false)
	public void saveLabelUser(String uid, Integer gid, String resultsTag, String qidAnswer, String templateTag) {
		// 获取运营设置的选项标签
		List<OptionLabel> optionLabels = super.dao.getOptionLabel(gid);
		List<ResultLabel> resultLabels = Lists.newArrayList();
		if(Global.TEMPLATE_FOUR.equals(templateTag.trim())) {
			// 获取运营设置的模板4结果页标签
			resultLabels = super.dao.getResultPageLabel(gid);
		}
	

		if ((optionLabels == null || optionLabels.size() == 0) && (resultLabels == null || resultLabels.size() == 0)) {
			logger.debug("saveLabelUser()方法----运营未设置选项标签和结果页标签");
			return;
		}
		List<QxLabelUser> labelUserList = Lists.newArrayList();
		// 运营有为模板4结果页设置标签
		if (resultLabels != null && resultLabels.size() > 0) {
			for (ResultLabel item : resultLabels) {
				if (item.getResultsTag().equals(resultsTag)) {
					QxLabelUser labelUser = new QxLabelUser();
					labelUser.setUid(uid);
					BeanMapper.copy(item, labelUser);
					labelUserList.add(labelUser);
					break;
				}
			}
		}
		// 运营有为选项设置标签
		if (optionLabels != null && optionLabels.size() > 0) {
			JSONArray array = JSONArray.parseArray(qidAnswer);
			for (int i = 0; i < array.size(); i++) { // 遍历做题选项列表
				JSONObject obj = array.getJSONObject(i);
				int qid = obj.getIntValue("qid");
				String answer = obj.getString("answer");
				for(int j = 0; j < optionLabels.size(); j++) {
					OptionLabel item = optionLabels.get(j);
					if (qid == item.getQid() && answer.equals(item.getSeqId())) {
						QxLabelUser labelUser = new QxLabelUser();
						labelUser.setUid(uid);
						BeanMapper.copy(item, labelUser);
						labelUserList.add(labelUser);
						break;
					}
				}
			}
			// 有标签数据要保存或者更新
			if (labelUserList.size() > 0) { 
				super.dao.saveOrUpdate(labelUserList);
			}
		}
	}
}
