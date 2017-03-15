package com.innovate.cms.modules.data.entity;

import java.io.Serializable;
import java.util.List;

import com.innovate.cms.modules.qs.entity.menus.Features;
/**
 * 
 * @ClassName: MenuFeaturesToJson
 * @Description: 根据分类ID输出专题组以及旗下的专题
 * @author gaoji gaoji_cyou-inc_com
 * @date 2016年8月11日 下午8:03:49
 *
 */
public class MenuFeaturesToJson implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	private int cid;// 分类ID
	private List<Features> features ; //专题集合
	
	public MenuFeaturesToJson()
	{
		super();
	}
	
	public MenuFeaturesToJson(int cid, List<Features> features)
	{
		super();
		this.cid = cid;
		this.features = features;
	}

	public int getCid()
	{
		return cid;
	}
	public void setCid(int cid)
	{
		this.cid = cid;
	}
	public List<Features> getFeatures()
	{
		return features;
	}
	public void setFeatures(List<Features> features)
	{
		this.features = features;
	}
}
