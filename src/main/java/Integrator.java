import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

;

public class Integrator {

    // used as keys for the statuses of an integration attempt, the K in K,V
    public final static String STATUS_INSTALL = "INSTALL";
    public final static String STATUS_COMPILE = "COMPILE";
    public final static String STATUS_TEST = "TEST";
    // used as constant values for the resulting statuses, the V in K,V
    public final static String STATUS_SUCCESS = "SUCCESS";
    public final static String STATUS_FAILURE = "FAILURE";

    public static void main(String[] args){
        // example code to show usage without changing main file

        String target = "/";
        String commitedBranch = "TEST_total_success";
        String commitHash = "117f7fb";
        Map<String, String> statuses = null;
        if(target.equals("/"))
            statuses = Integrator.integrateBuild(commitedBranch, commitHash);
        System.out.println("Install status: " + statuses.get(STATUS_INSTALL)); // status of installing dependencies
        System.out.println("Compile status: " + statuses.get(STATUS_COMPILE)); // status of compiling build
        System.out.println("Install status: " + statuses.get(STATUS_TEST)); // status of unit tests
        if(statuses.get(STATUS_TEST).equals(STATUS_SUCCESS)){
            System.out.println("Tests succeeded, all good :)");
        }
        if(statuses.get(STATUS_COMPILE).equals(STATUS_FAILURE)){
            System.out.println("Building/compiling failed, tests should not succeed");
        }
    }

    /**
     * Creates a directory, in which the latest commit of the specified branch is cloned into. Maven dependencies are
     * installed and the result is logged to <b>{@code .mvn_install.log}</b>, Maven compiles the project and the result is
     * logged to <b>{@code .mvn_compile.log}</b>, Maven runs the project's unit tests and the result is logged to
     * <b>{@code .mvn_test.log}</b>, and a directory is created for the commit hash under the <b>{@code BUILDS/}</b> directory, found
     * in the project root. Finally, the logs as well as the built .jar-file are all copied to <b>{@code BUILDS/<commitHash>/}</b> where
     *
     * @param commitBranch The branch that is pushed to the repository and that will be built.
     * @param commitHash The 7-character hash for the latest commit on <i>commitBranch</i>, <i>commitHash</i>.jar will be the build.
     * @return A String:String mapping where the statuses of "INSTALL", "COMPILE", and "TEST" are saved.
     */
    public static Map<String,String> integrateBuild(String commitBranch, String commitHash){
        Stack<String> outputStack = new Stack<String>();
        String branchKey = "DD2480_BUILD_BRANCH";
        String commitKey = "DD2480_BUILD_COMMIT";

        // set up script environment
        ProcessBuilder pb = new ProcessBuilder();
        Map<String, String> env = pb.environment();
        env.put(branchKey, commitBranch);
        env.put(commitKey, commitHash);

        // store statuses to be returned
        Map<String, String> statuses = new HashMap<>();

        // runs at root of project
        pb.command("./build.sh");
        try {
            Process p = pb.start();
            // save script output to a stack of strings
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream()))) {
                String line;
                while((line = reader.readLine()) != null){
                    outputStack.push(line);
                }
            }
            int ret = p.waitFor();
            if(ret != 0){
                // TODO: use ret to determine other actions or assume script success?
                throw new Exception("Integration script process returned with non-zero error code.");
            }

            // save the result statuses of the maven commands
            statuses.put(STATUS_TEST, outputStack.pop().split(" ")[2]);
            statuses.put(STATUS_COMPILE, outputStack.pop().split(" ")[2]);
            statuses.put(STATUS_INSTALL, outputStack.pop().split(" ")[2]);

        }catch(Exception e){
            e.printStackTrace();
        }
        return statuses;
    }

}
