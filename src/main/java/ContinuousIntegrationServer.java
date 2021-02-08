import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
 
import java.io.IOException;
import java.util.stream.Collectors;
import java.lang.Thread;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/** 
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
*/
public class ContinuousIntegrationServer extends AbstractHandler {
    String data;

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        System.out.println(target);
        System.out.println("got a catch");

        if (baseRequest.getMethod().equals("POST")) {
            response.getWriter().println("POST received");
            if (!baseRequest.getHeader("X-Github-Event").equals("ping")) {
                data = baseRequest.getReader().lines().collect(Collectors.joining());
                RequestHandler a = new RequestHandler();
                a.data = data;
                a.run();
            }
        }
    }

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception {
        jsonHandler jsonHandler = new jsonHandler();
        BuildHistory db = jsonHandler.readBuildHistory();


        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }

}
