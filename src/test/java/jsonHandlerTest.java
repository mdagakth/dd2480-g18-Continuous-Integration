import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class jsonHandlerTest {


    /**
     * Test the addBuildToDB function, checks that the build is added correctly
     */
    @Test
    public void addBuildToDBTest(){
        BuildHistory db = new BuildHistory();
        Build b = new Build(1,"45a1d97","2021-02-05T15:00:11Z","Kalle-#15-JSONpersistant-feat",new installResult(true,"The install was successfull"),new buildResult(true,"The build was successfull"), new testResult(true, "Successfull on 32 out of 32 tests"), "rigorous log");
        db.addBuildToDB(b);

        ArrayList<Build> builds = db.getBuildHistory();
        Build bRes = builds.get(0);

        assertEquals(builds.size(),1);
        assertEquals(b.equals(builds.get(0)),true);

    }

    /**
     * tests if findBuild function can find a build given it's buildID
     */
    @Test
    public void findBuildSuccessfulTest(){
        BuildHistory db = new BuildHistory();
        Build b = new Build(1,"45a1d97","2021-02-05T15:00:11Z","Kalle-#15-JSONpersistant-feat",new installResult(true,"The install was successfull"),new buildResult(true,"The build was successfull"), new testResult(true, "Successfull on 32 out of 32 tests"), "rigorous log");
        db.addBuildToDB(b);

        Build bRes = db.findBuild(1);

        assertEquals( b.equals(bRes),true);
    }

    /**
     * Tests findBuild with an incorrect buildID and cehcks that null is returned
     */
    @Test
    public void findBuildFailTest(){
        BuildHistory db = new BuildHistory();
        Build b = new Build(1,"45a1d97","2021-02-05T15:00:11Z","Kalle-#15-JSONpersistant-feat",new installResult(true,"The install was successfull"),new buildResult(true,"The build was successfull"), new testResult(true, "Successfull on 32 out of 32 tests"), "rigorous log");
        db.addBuildToDB(b);

        Build bRes = db.findBuild(2);
        assertEquals(b.equals(bRes),false);
    }


    /**
     * Test that two known build objects are loaded in correctly
     */
    @Test
    public void readDBTest1() {
        jsonHandler handler = new jsonHandler("src/test/resources/BuildHistoryDBTEST.json");
        BuildHistory db = handler.readBuildHistory();
        Build b1 = db.findBuild(1);
        Build b2 = db.findBuild(2);
        Build b1res = new Build(1,"45a1d97","2021-02-05T15:00:11Z","Kalle-#15-JSONpersistant-feat",new installResult(true,"The install was successfull"),new buildResult(true,"The build was successfull"), new testResult(true, "Successfull on 32 out of 32 tests"), "rigorous log");
        Build b2res = new Build(2,"45a1d97","2021-02-05T15:00:11Z","Kalle-#15-JSONpersistant-feat",new installResult(false,"The install was successfull"),new buildResult(false,"The build was successfull"), new testResult(false, "Successfull on 32 out of 32 tests"), "rigorous log");

        assertEquals(b1.equals(b1res),true);
        assertEquals(b2.equals(b2res),true);
    }

    /**
     * Creates a build history object, save it to file, load it back in and compare it to correct version
     */
    @Test
    public void saveDBTest1() {
        jsonHandler handler = new jsonHandler("src/test/resources/saveBuildTest1.json");
        BuildHistory db = new BuildHistory();
        Build b = new Build(1,"45a1d97","2021-02-05T15:00:11Z","Kalle-#15-JSONpersistant-feat",new installResult(true,"The install was successfull"),new buildResult(true,"The build was successfull"), new testResult(true, "Successfull on 32 out of 32 tests"), "rigorous log");
        db.addBuildToDB(b);
        handler.saveBuildHistory(db);

        //
        jsonHandler correctHandler = new jsonHandler("src/test/resources/saveBuildAns1.json");
        BuildHistory correctDB = correctHandler.readBuildHistory();
        BuildHistory testDB = handler.readBuildHistory();

        Build b1 = correctDB.findBuild(1);
        Build b2 = testDB.findBuild(1);

        assertEquals(b1.equals(b2),true);

        assertEquals(b1.getInstallResult().isInstallSuccessfull(), b2.getInstallResult().isInstallSuccessfull());
        assertEquals(b1.getInstallResult().getInstallLogs(),b2.getInstallResult().getInstallLogs());

        assertEquals(b1.getBuildResult().isBuildSuccessfull(), b2.getBuildResult().isBuildSuccessfull());
        assertEquals(b1.getBuildResult().getBuildLogs(),b2.getBuildResult().getBuildLogs());

        assertEquals(b1.getTestResult().isTestSuccessfull(), b2.getTestResult().isTestSuccessfull());
        assertEquals(b1.getTestResult().getTestLogs(),b2.getTestResult().getTestLogs());
    }


    /**
     * Testing the saveGithubLogs function with a commit hash that exists.
     * First creates a directory with a dummy commithash
     * then save dummy git req to a file
     * cleanup and remove directory and file
     */
    @Test
    public void testSaveGithubLogsWithCorrectCommitHash(){
        jsonHandler jsonHandler = new jsonHandler();
        Gson gson = new Gson();
        try (Reader reader = new FileReader("src/test/resources/rawGithubReqTest.json")) {
            JsonObject gitReq = gson.fromJson(reader,JsonObject.class);
            String gitLogs = gitReq.toString();
            String commitHash = "test1234";

            //setup dummy folder
            File f = new File("localbuilds/" + commitHash);
            f.mkdir();

            //save github log
            jsonHandler.saveGithubLogs(gitReq,commitHash);

            //compare stored logs
            try (Reader r2 = new FileReader("localbuilds/"+commitHash +"/.github_req.json")){
                JsonObject gitReqRead = gson.fromJson(r2,JsonObject.class);

                assertEquals(gitLogs,gitReqRead.toString());
            } catch (IOException e){
                e.printStackTrace();
            }

            //clean up
            File f1 = new File("localbuilds/" + commitHash+"/.github_req.json");
            File f2 = new File("localbuilds/" + commitHash);
            f1.delete();
            f2.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}