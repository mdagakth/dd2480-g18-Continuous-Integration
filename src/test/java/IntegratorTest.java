import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Map;


public class IntegratorTest {

    /**
     * Tests that a commit/branch where the code is not compilable fails
     * both in the compile stage and the test stage. It should also succeed in
     * the installing of dependencies.
     */
    @Test
    public void buildFail(){
        String commitedBranch = "TEST_build_fail";
        String commitHash = "ff9e1ac";
        boolean saveLocally = true;
        Map<String, String> statuses = Integrator.integrateBuild(commitedBranch, commitHash, saveLocally);
        assertEquals(Integrator.STATUS_SUCCESS, statuses.get(Integrator.STATUS_INSTALL));
        assertEquals(Integrator.STATUS_FAILURE, statuses.get(Integrator.STATUS_COMPILE));
        assertEquals(Integrator.STATUS_FAILURE, statuses.get(Integrator.STATUS_TEST));
    }

    /**
     * Tests that a commit/branch where the code is compilable
     * but the tests fail actually return a success status for
     * both the install and compiling stages, but a failure
     * status for the test stage.
     */
    @Test
    public void testFail(){
        String commitedBranch = "TEST_test_fail";
        String commitHash = "e6134f8";
        boolean saveLocally = true;
        Map<String, String> statuses = Integrator.integrateBuild(commitedBranch, commitHash, saveLocally);
        assertEquals(Integrator.STATUS_SUCCESS, statuses.get(Integrator.STATUS_INSTALL));
        assertEquals(Integrator.STATUS_SUCCESS, statuses.get(Integrator.STATUS_COMPILE));
        assertEquals(Integrator.STATUS_FAILURE, statuses.get(Integrator.STATUS_TEST));
    }

    /**
     * Tests that a commit/branch where everything is working
     * as it should actually return a success status for all
     * the three different stages. Also tests that the .jar-file
     * as well as the compile log are created in the correct location.
     */
    @Test
    public void totalSuccess(){
        String commitedBranch = "TEST_total_success";
        String commitHash = "117f7fb";
        boolean saveLocally = true;
        Map<String, String> statuses = Integrator.integrateBuild(commitedBranch, commitHash, saveLocally);
        File jarFile = new File((saveLocally ? Integrator.DIRECTORY_LOCAL : Integrator.DIRECTORY_CLOUD)+
                commitHash+"/"+commitHash+".jar");
        File compileLog = new File((saveLocally ? Integrator.DIRECTORY_LOCAL : Integrator.DIRECTORY_CLOUD)+
                commitHash+"/.mvn_compile.log");
        assertTrue(jarFile.exists());
        assertTrue(compileLog.exists());
        assertEquals(Integrator.STATUS_SUCCESS, statuses.get(Integrator.STATUS_INSTALL));
        assertEquals(Integrator.STATUS_SUCCESS, statuses.get(Integrator.STATUS_COMPILE));
        assertEquals(Integrator.STATUS_SUCCESS, statuses.get(Integrator.STATUS_TEST));
    }

}
