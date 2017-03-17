package org.alfonz.samples.alfonzrest.entity;

import com.google.gson.annotations.SerializedName;


public class OwnerEntity
{
	@SerializedName("id") private long id;
	@SerializedName("login") private String login;
	@SerializedName("avatar_url") private String avatarUrl;


	public OwnerEntity()
	{
	}


	public long getId()
	{
		return id;
	}


	public void setId(long id)
	{
		this.id = id;
	}


	public String getLogin()
	{
		return login;
	}


	public void setLogin(String login)
	{
		this.login = login;
	}


	public String getAvatarUrl()
	{
		return avatarUrl;
	}


	public void setAvatarUrl(String avatarUrl)
	{
		this.avatarUrl = avatarUrl;
	}
}
