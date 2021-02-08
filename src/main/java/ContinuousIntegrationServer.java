import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/** 
 Skeleton of a ContinuousIntegrationServer which acts as webhook
 See the Jetty documentation for API documentation of those classes.
*/
public class ContinuousIntegrationServer extends AbstractHandler {


    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        System.out.println(target);

        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        // 2nd compile the code
        switch (target) {
            case "/" -> build(baseRequest, request, response);
            case "/favicon.ico" -> response.getWriter().write("NO");
            default -> {
                String[] targetParams;
                targetParams = target.split("/");
                // splitting on "/" gives an empty string as first element since first character of target is a "/"
                switch (targetParams[1]) {
                    case "build" -> search(targetParams[2], baseRequest, request, response);
                    case "allBuilds" -> history(baseRequest, request, response);
                    default -> response.getWriter().write(
                            "<html>" +
                                "<head>" +
                                    "<title>404 - not found</title>" +
                                "</head>" +
                                "<body style=\"vertical-align: center\">" +
                                    "<div style=\"display: inline-block; text-align: center;\">" +
                                        "<img src=\"https://cdn.searchenginejournal.com/wp-content/uploads/2020/08/killer-404-page-coschedule-5f3d58c828b04.png\">" +
                                    "</div>" +
                                "</body>" +
                            "</html>");
                }
            }
        }
        response.getWriter().flush();
        //response.getWriter().println("CI job done");
    }


    /**
     * Here we handle the build (clone repo, run tests, store results etc. a.k.a bash magic)
     *
     * @param baseRequest: the base HTTP request
     * @param request: HTTP request as per Servlet's implementation
     * @param response: Where to send the result
     */
    private void build(Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Something got pushed");
        response.getWriter().write("wow, some thing happened!");
    }

    /**
     * Fetches information about the build with id "buildID"
     *
     * @param buildID: the id of the build to fetch
     * @param baseRequest: the base HTTP request
     * @param request: HTTP request as per Servlet's implementation
     * @param response: Where to send the result
     */
    private void search(String buildID, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
         * Fetch the correct build information file
         */
        // boolean found = <build file exists>;
        boolean found = buildID.equals("1");
        if (!found) {
            //some inline HTML as a 404-page if buildID is invalid
            response.setStatus(404);
            response.getWriter().write(
                    "<html>" +
                        "<head>" +
                            "<title>404 - not found</title>" +
                        "</head>" +
                        "<body style=\"vertical-align: center\">" +
                            "<div style=\"width: 100%; display: inline-block; text-align: center;\">" +
                                "The page you are trying to find does not exist" +
                            "</div>" +
                        "</body>" +
                    "</html>");
        }
        System.out.println("in Search!");
    }

    /**
     * Fetches information about all builds that have been processed
     *
     * @param baseRequest: the base HTTP request
     * @param request: HTTP request as per Servlet's implementation
     * @param response: Where to send the result
     */
    private void history(Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
         * Fetch the full commit-history and write it to the response
         */
        response.getWriter().write(
                "<html>" +
                    "<head>" +
                        "<title>Build history</title>" +
                    "</head>" +
                    "<body style=\"vertical-align: center\">" +
                        "<div style=\"display: inline-block; text-align: center; width: 100%;\">" +
                            "<span>Your build history will be visible here in the future</span>" +
                        "</div>" +
                    "</body>" +
                "</html>");
    }

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }
}
