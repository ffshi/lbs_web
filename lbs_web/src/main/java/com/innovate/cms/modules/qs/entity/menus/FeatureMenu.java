/**
 * 
 */
package com.innovate.cms.modules.qs.entity.menus;

import java.io.Serializable;

/**
 * 专题组菜单返回样式
 * @author hushasha
 * @date 2016年10月31日
 */
public class FeatureMenu implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer fsid; //专题组ID
	private String featureName; //专题组名称
	public Integer getFsid() {
		return fsid;
	}
	public void setFsid(Integer fsid) {
		this.fsid = fsid;
	}
	public String getFeatureName() {
		return featureName;
	}
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

}
