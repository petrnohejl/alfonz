package org.alfonz.samples.alfonzrest.rest.router;

import org.alfonz.samples.alfonzrest.entity.RepoEntity;
import org.alfonz.samples.alfonzrest.rest.RetrofitClient;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RepoRouter {
	public static final String REPO_CALL_TYPE = "repo";

	private static volatile RepoService sService;

	public interface RepoService {
		@GET("repos/{owner}/{repo}")
		Call<RepoEntity> repo(@Path("owner") String owner, @Path("repo") String repo);
	}

	private RepoRouter() {}

	public static RepoService getService() {
		if (sService == null) {
			synchronized (RepoRouter.class) {
				if (sService == null) {
					sService = RetrofitClient.createService(RepoService.class);
				}
			}
		}
		return sService;
	}
}
