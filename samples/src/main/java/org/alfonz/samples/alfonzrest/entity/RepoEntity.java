package org.alfonz.samples.alfonzrest.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class RepoEntity
{
	@SerializedName("id") private long id;
	@SerializedName("name") private String name;
	@SerializedName("owner") private OwnerEntity owner;
	@SerializedName("private") private boolean isPrivate;
	@SerializedName("html_url") private String htmlUrl;
	@SerializedName("description") private String description;
	@SerializedName("fork") private boolean fork;
	@SerializedName("url") private String url;
	@SerializedName("created_at") private Date createdAt;
	@SerializedName("updated_at") private Date updatedAt;
	@SerializedName("pushed_at") private Date pushedAt;
	@SerializedName("stargazers_count") private int stargazersCount;
	@SerializedName("watchers_count") private int watchersCount;
	@SerializedName("language") private String language;
	@SerializedName("forks_count") private int forksCount;
	@SerializedName("open_issues_count") private int openIssuesCount;
	@SerializedName("subscribers_count") private int subscribersCount;


	public RepoEntity()
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


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}


	public OwnerEntity getOwner()
	{
		return owner;
	}


	public void setOwner(OwnerEntity owner)
	{
		this.owner = owner;
	}


	public boolean isPrivate()
	{
		return isPrivate;
	}


	public void setPrivate(boolean isPrivate)
	{
		this.isPrivate = isPrivate;
	}


	public String getHtmlUrl()
	{
		return htmlUrl;
	}


	public void setHtmlUrl(String htmlUrl)
	{
		this.htmlUrl = htmlUrl;
	}


	public String getDescription()
	{
		return description;
	}


	public void setDescription(String description)
	{
		this.description = description;
	}


	public boolean isFork()
	{
		return fork;
	}


	public void setFork(boolean fork)
	{
		this.fork = fork;
	}


	public String getUrl()
	{
		return url;
	}


	public void setUrl(String url)
	{
		this.url = url;
	}


	public Date getCreatedAt()
	{
		return createdAt;
	}


	public void setCreatedAt(Date createdAt)
	{
		this.createdAt = createdAt;
	}


	public Date getUpdatedAt()
	{
		return updatedAt;
	}


	public void setUpdatedAt(Date updatedAt)
	{
		this.updatedAt = updatedAt;
	}


	public Date getPushedAt()
	{
		return pushedAt;
	}


	public void setPushedAt(Date pushedAt)
	{
		this.pushedAt = pushedAt;
	}


	public int getStargazersCount()
	{
		return stargazersCount;
	}


	public void setStargazersCount(int stargazersCount)
	{
		this.stargazersCount = stargazersCount;
	}


	public int getWatchersCount()
	{
		return watchersCount;
	}


	public void setWatchersCount(int watchersCount)
	{
		this.watchersCount = watchersCount;
	}


	public String getLanguage()
	{
		return language;
	}


	public void setLanguage(String language)
	{
		this.language = language;
	}


	public int getForksCount()
	{
		return forksCount;
	}


	public void setForksCount(int forksCount)
	{
		this.forksCount = forksCount;
	}


	public int getOpenIssuesCount()
	{
		return openIssuesCount;
	}


	public void setOpenIssuesCount(int openIssuesCount)
	{
		this.openIssuesCount = openIssuesCount;
	}


	public int getSubscribersCount()
	{
		return subscribersCount;
	}


	public void setSubscribersCount(int subscribersCount)
	{
		this.subscribersCount = subscribersCount;
	}
}
