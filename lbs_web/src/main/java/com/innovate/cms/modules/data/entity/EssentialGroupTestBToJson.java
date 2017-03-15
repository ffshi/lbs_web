package com.innovate.cms.modules.data.entity;

/**
 * wishBone专题精选混排
 * 
 * @author shifangfang
 * @date 2017年1月5日 上午11:34:52
 */
public class EssentialGroupTestBToJson extends EssentialGroupToJson {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String groupDescription;
	private String smallIcon;

	public EssentialGroupTestBToJson() {
		super();
	}

	public String getSmallIcon() {
		return smallIcon;
	}

	public void setSmallIcon(String smallIcon) {
		this.smallIcon = smallIcon;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

}
