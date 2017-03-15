package com.innovate.cms.modules.data.entity;

import java.io.Serializable;

public class ChatBoxMatchToJson implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	private String groupName;

	private String templateTag;

	private String results;
	
	public ChatBoxMatchToJson()
	{
		super();
	}
	
	public ChatBoxMatchToJson(String groupName, String templateTag, String results)
	{
		super();
		this.groupName = groupName;
		this.templateTag = templateTag;
		this.results = results;
	}

	public String getGroupName()
	{
		return groupName;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	public String getTemplateTag()
	{
		return templateTag;
	}

	public void setTemplateTag(String templateTag)
	{
		this.templateTag = templateTag;
	}

	public String getResults()
	{
		return results;
	}

	public void setResults(String results)
	{
		this.results = results;
	}
	
}
