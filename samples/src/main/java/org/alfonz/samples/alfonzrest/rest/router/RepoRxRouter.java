package org.alfonz.samples.alfonzrest.rest.router;

import org.alfonz.samples.alfonzrest.entity.RepoEntity;
import org.alfonz.samples.alfonzrest.rest.RetrofitClient;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RepoRxRouter {
	public static final String REPO_CALL_TYPE = "repo";

	private static volatile RepoService sService;

	public interface RepoService {
		@GET("repos/{owner}/{repo}")
		Single<Response<RepoEntity>> repo(@Path("owner") String owner, @Path("repo") String repo);
	}

	private RepoRxRouter() {}

	public static RepoService getService() {
		if (sService == null) {
			synchronized (RepoRxRouter.class) {
				if (sService == null) {
					sService = RetrofitClient.createService(RepoService.class);
				}
			}
		}
		return sService;
	}
}
