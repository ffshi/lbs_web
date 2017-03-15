/**
 * 
 */
package com.innovate.cms.modules.qs.entity.ads;

import java.io.Serializable;

/**
 * 首页json格式的部分样式
 * 
 * @author hushasha
 * @date 2016年4月6日
 */
public class PageHome implements Serializable {

	 
	private static final long serialVersionUID = 1L;
	private String refType; // 1、专题 2、专题组 3、H5
	private String templateTag; //模板标记模板1,2,3,4 三期做1,4,5
	private String ref; // 引用参数，受refType限制
	private String refTitle; // 引用资源标题
	private String img; // 该 专题/组/H5 对应图片
	private int num; // 专题参与人数，默认为0
	
	
	public PageHome()
	{
		super();
	}
	public PageHome(String refType, String templateTag, String ref, String refTitle, String img, int num)
	{
		super();
		this.refType = refType;
		this.templateTag = templateTag;
		this.ref = ref;
		this.refTitle = refTitle;
		this.img = img;
		this.num = num;
	}
	public String getRefType()
	{
		return refType;
	}
	public void setRefType(String refType)
	{
		this.refType = refType;
	}
	public String getTemplateTag()
	{
		return templateTag;
	}
	public void setTemplateTag(String templateTag)
	{
		this.templateTag = templateTag;
	}
	public String getRef()
	{
		return ref;
	}
	public void setRef(String ref)
	{
		this.ref = ref;
	}
	public String getRefTitle()
	{
		return refTitle;
	}
	public void setRefTitle(String refTitle)
	{
		this.refTitle = refTitle;
	}
	public String getImg()
	{
		return img;
	}
	public void setImg(String img)
	{
		this.img = img;
	}
	public int getNum()
	{
		return num;
	}
	public void setNum(int num)
	{
		this.num = num;
	}

}
