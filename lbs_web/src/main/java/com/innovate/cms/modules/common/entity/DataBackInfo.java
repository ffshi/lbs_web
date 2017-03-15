package com.innovate.cms.modules.common.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * 通用backinfo
 * 
 * @ClassName: DataBackInfo
 * @Description: 统一返回对象
 * @author gaoji gaoji_cyou-inc_com
 * @date 2015年12月28日 下午2:11:32
 *
 * @param <T>
 *            泛型对象
 */
public class DataBackInfo<T> extends BaseBackInfo
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 总页数
	 */
	private int totalResult;
	/**
	 * 集合数据
	 */
	private List<T> data;

	public int getTotalResult()
	{
		return totalResult;
	}

	public void setTotalResult(int totalResult)
	{
		this.totalResult = totalResult;
	}

	@JsonInclude(Include.ALWAYS)
	public List<T> getData()
	{
		return data;
	}

	public void setData(List<T> data)
	{
		this.data = data;
	}

}