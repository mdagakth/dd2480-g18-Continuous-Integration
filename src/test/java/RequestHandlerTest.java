import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.egit.github.core.CommitStatus;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class RequestHandlerTest {


	/**
	 * gets the status of a commit
	 * @param commit the commit which status is to be retrieved
	 * @return the status of the commit eg. "pending", "success", "failure"
	 */
	String getCommitStatus(JsonObject commit){
		GitHubClient ghc = new GitHubClient(); // Create GitHub Client
		ghc.setOAuth2Token(System.getProperty("DD2480_TOKEN")); // Authenticate.
		RepositoryService rs = new RepositoryService(ghc); // get repo service
		CommitService cs = new CommitService(ghc); // get commit service

		List<CommitStatus> statuses;
		try {
			Repository repo = rs.getRepository(commit.getAsJsonObject("repository").getAsJsonObject("owner").get("name").getAsString(), commit.getAsJsonObject("repository").get("name").getAsString()); // get the repository
			statuses = cs.getStatuses(repo, commit.get("after").getAsString());

		}catch (Exception e){System.out.println("couldn't get commit status");fail();return "no";}

		CommitStatus status = null;
		if (statuses.size() < 1){
			System.out.println("got no status");
			fail();
		}else {
			status = statuses.get(0);
		}

		return status.getState();
	}

	/**
	 * sets the status of a commit eg  "pending", "success", "failure"
	 * @param object the commit to set the status of
	 * @param state the state to be set
	 */
	void sendCommitStatus(JsonObject object, String state){
		GitHubClient ghc = new GitHubClient();
		ghc.setOAuth2Token(System.getProperty("DD2480_TOKEN"));
		CommitService cs = new CommitService(ghc);
		RepositoryService rs = new RepositoryService(ghc);

		CommitStatus cStatus = new CommitStatus();
		cStatus.setDescription("for testing");
		cStatus.setState(state);

		try {
			Repository repository = rs.getRepository(object.getAsJsonObject("repository").getAsJsonObject("owner").get("name").getAsString(), object.getAsJsonObject("repository").get("name").getAsString());
			cs.createStatus(repository, object.get("after").getAsString(), cStatus);

		}catch (Exception e){e.printStackTrace();fail();}

	}

	/**
	 * this test checks that a commit succeeds in getting updated on github
	 */
	@Test
	void testSucceed(){
		ContinuousIntegrationServer continuousIntegrationServer = new ContinuousIntegrationServer();
		JsonObject jsonObject;
		try {
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(new FileReader("src/test/java/testdatacommit.json"));
			jsonObject = jsonElement.getAsJsonObject();

			sendCommitStatus(jsonObject, CommitStatus.STATE_PENDING);
			String status = getCommitStatus(jsonObject);
			if (!status.equals(CommitStatus.STATE_PENDING)){
				System.out.println("status not updated on github, maybe bad connection");
				System.out.println();
				fail();
			}

			RequestHandler requestHandler = new RequestHandler();
			requestHandler.data = jsonObject.toString();
			requestHandler.start();
			requestHandler.join();

			status = getCommitStatus(jsonObject);
			if (!status.equals(CommitStatus.STATE_SUCCESS)){
				System.out.println("Wrong status on github " + status);
				fail();
			}
		}catch (Exception e){e.printStackTrace(); fail();}
	}

	/**
	 * this test checks if a commit is properly failed
	 */
	@Test
	void testFail(){
		ContinuousIntegrationServer continuousIntegrationServer = new ContinuousIntegrationServer();
		JsonObject jsonObject;
		try {
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(new FileReader("src/test/java/testdatacommitfail.json"));
			jsonObject = jsonElement.getAsJsonObject();

			sendCommitStatus(jsonObject, CommitStatus.STATE_PENDING);
			String status = getCommitStatus(jsonObject);
			if (!status.equals(CommitStatus.STATE_PENDING)){
				System.out.println("status not updated on github, maybe bad connection");
				System.out.println();
				fail();
			}

			RequestHandler requestHandler = new RequestHandler();
			requestHandler.data = jsonObject.toString();
			requestHandler.start();
			requestHandler.join();

			status = getCommitStatus(jsonObject);
			if (!status.equals(CommitStatus.STATE_FAILURE)){
				System.out.println("Wrong status on github " + status);
				fail();
			}
		}catch (Exception e){e.printStackTrace(); fail();}
	}
}
