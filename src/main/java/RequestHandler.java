import com.google.gson.*;
import org.eclipse.egit.github.core.CommitStatus;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RequestHandler implements Runnable{
	volatile String data;

	@Override
	public void run() {

		JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();


		try {
			sendCommitStatus(jsonObject, CommitStatus.STATE_PENDING);
		}catch (Exception e){}



		try {
			Thread.sleep(2000); // do busywork
		}catch (Exception e){}



		if (true) { //successfull build
			try {
				sendCommitStatus(jsonObject, CommitStatus.STATE_SUCCESS);
			}catch (Exception e){}
		}else{ //failed build
			try {
				sendCommitStatus(jsonObject, CommitStatus.STATE_FAILURE);
			}catch (Exception e){}
		}
	}

	private static void sendCommitStatus(JsonObject commit, String commitStatus) throws IOException {
		GitHubClient ghc = new GitHubClient(); // Create GitHub Client
		ghc.setOAuth2Token(System.getProperty("DD2480_TOKEN")); // Authenticate.
		RepositoryService rs = new RepositoryService(ghc); // get repo service
		CommitService cs = new CommitService(ghc);

		Repository repo = rs.getRepository(commit.getAsJsonObject("repository").getAsJsonObject("owner").get("name").getAsString(), commit.getAsJsonObject("repository").get("name").getAsString()); // get the repository
		// Set commit status
		CommitStatus status = cs.createStatus(repo, commit.get("after").getAsString(), new CommitStatus().setState(commitStatus));
	}

}