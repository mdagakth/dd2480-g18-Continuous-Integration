import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.egit.github.core.CommitStatus;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.CommitService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.fail;

public class RequestHandlerTest {

	/**
	 * this test is supposed to cause a commit to succeed and fail when further implementations are done in the code
	 * right now it only tests if the requestHandler runs
	 */
	@Test
	void testFailSucceed(){
		/* to be used for more extensive testing
		GitHubClient client = new GitHubClient();
		client.setOAuth2Token(token.token);
		RepositoryService rs = new RepositoryService(client); // get repo service
		CommitService cs = new CommitService(client);*/
		JsonObject jsonObject = new JsonObject();
		try {
			JsonParser parser = new JsonParser();
			System.out.println(System.getProperty("user.dir"));
			JsonElement jsonElement = parser.parse(new FileReader("src/test/java/testdatacommit.json"));
			jsonObject = jsonElement.getAsJsonObject();
		}catch (Exception e){System.out.println(e.getMessage()); fail();}

		RequestHandler handler = new RequestHandler();
		handler.data = jsonObject.toString();
		handler.run();
	}
}
