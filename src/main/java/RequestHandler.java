import java.lang.Thread;
import com.google.gson.*;

import java.net.URI;
import java.net.http.*;

public class RequestHandler implements Runnable{
	volatile String data;

	@Override
	public void run() {
		jsonHandler jsonHandler = new jsonHandler();
		BuildHistory db = jsonHandler.readBuildHistory();
		JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();

		String id = jsonObject.getAsJsonObject("head_commit").get("id").getAsString();

		System.out.println(data);
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://<token>:x-oauth-basic@api.github.com/repos/DD2480-group18/dd2480-g18-Continuous-Integration/statuses/" + id))
				.header("Content-type", "application/java").POST(HttpRequest.BodyPublishers.ofString("{\"status\":\"accept\"}")).build();
		try {
			HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
			System.out.println(response.body());

		}catch (Exception e){}

	}
}
//https://api.github.com/repos/politrons/proyectV/statuses/6dcb09b5b57875f334f61aebed695e2e4193db5e
//<token>:x-oauth-basic@ // add this if not working
