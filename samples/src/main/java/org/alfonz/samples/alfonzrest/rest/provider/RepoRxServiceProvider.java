package org.alfonz.samples.alfonzrest.rest.provider;

import org.alfonz.samples.alfonzrest.entity.RepoEntity;
import org.alfonz.samples.alfonzrest.rest.RetrofitClient;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;


public class RepoRxServiceProvider
{
	public static final String REPO_CALL_TYPE = "repo";

	private static volatile RepoService sService;


	public interface RepoService
	{
		@GET("repos/{owner}/{repo}")
		Observable<Response<RepoEntity>> repo(@Path("owner") String owner, @Path("repo") String repo);
	}


	private RepoRxServiceProvider() {}


	public static RepoService getService()
	{
		if(sService == null)
		{
			synchronized(RepoRxServiceProvider.class)
			{
				if(sService == null)
				{
					sService = RetrofitClient.createService(RepoService.class);
				}
			}
		}
		return sService;
	}
}
