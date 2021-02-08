import org.junit.jupiter.api.Test;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class jsonHandlerTest {
    //TODO: read db, save db, add build to db, find build;


    /**
     * Test the addBuildToDB function, checks that the build is added correctly
     */
    @Test
    public void addBuildToDBTest(){
        BuildHistory db = new BuildHistory();
        Build b = new Build(1,new installResult(true,"The install was successfull", "raw install logs"),new buildResult(true,"The build was successfull","raw build logs"), new testResult(true, "Successfull on 32 out of 32 tests", "raw test logs"),"rawLogs");
        db.addBuildToDB(b);

        ArrayList<Build> builds = db.getBuildHistory();
        Build bRes = builds.get(0);

        assertEquals(builds.size(),1);
        assertEquals(bRes.getBuildID(),1);

        assertEquals(bRes.getInstallResult().isInstallSuccessfull(),true);
        assertEquals(bRes.getBuildResult().isBuildSuccessfull(),true);
        assertEquals(bRes.getTestResult().isTestSuccessfull(),true);
    }

    /**
     * tests if findBuild function can find a build given it's buildID
     */
    @Test
    public void findBuildSuccessfulTest(){
        BuildHistory db = new BuildHistory();
        Build b = new Build(1,new installResult(true,"The install was successfull", "raw install logs"),new buildResult(true,"The build was successfull","raw build logs"), new testResult(true, "Successfull on 32 out of 32 tests", "raw test logs"),"rawLogs");
        db.addBuildToDB(b);

        Build bRes = db.findBuild(1);

        assertEquals(bRes.getBuildID(),1);

        assertEquals(bRes.getInstallResult().isInstallSuccessfull(),true);
        assertEquals(bRes.getBuildResult().isBuildSuccessfull(),true);
        assertEquals(bRes.getTestResult().isTestSuccessfull(),true);
    }

    /**
     * Tests findBuild with an incorrect buildID and cehcks that null is returned
     */
    @Test
    public void findBuildFailTest(){
        BuildHistory db = new BuildHistory();
        Build b = new Build(1,new installResult(true,"The install was successfull", "raw install logs"),new buildResult(true,"The build was successfull","raw build logs"), new testResult(true, "Successfull on 32 out of 32 tests", "raw test logs"),"rawLogs");
        db.addBuildToDB(b);

        Build bRes = db.findBuild(2);
        assertEquals(bRes,null);
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

        assertEquals(b1.getInstallResult().isInstallSuccessfull(), true);
        assertEquals(b1.getBuildResult().isBuildSuccessfull(), true);
        assertEquals(b1.getTestResult().isTestSuccessfull(), true);


        assertEquals(b2.getInstallResult().isInstallSuccessfull(), false);
        assertEquals(b2.getBuildResult().isBuildSuccessfull(), false);
        assertEquals(b2.getTestResult().isTestSuccessfull(), false);
    }

    /**
     * Creates a build history object, save it to file, load it back in and compare it to correct version
     */
    @Test
    public void saveDBTest1() {
        jsonHandler handler = new jsonHandler("src/test/resources/saveBuildTest1.json");
        BuildHistory db = new BuildHistory();
        Build b = new Build(1,new installResult(true,"The install was successfull", "raw install logs"),new buildResult(true,"The build was successfull","raw build logs"), new testResult(true, "Successfull on 32 out of 32 tests", "raw test logs"),"rawLogs");
        db.addBuildToDB(b);
        handler.saveBuildHistory(db);

        //
        jsonHandler correctHandler = new jsonHandler("src/test/resources/saveBuildAns1.json");
        BuildHistory correctDB = correctHandler.readBuildHistory();
        BuildHistory testDB = handler.readBuildHistory();

        Build b1 = correctDB.findBuild(1);
        Build b2 = testDB.findBuild(1);

        assertEquals(b1.getInstallResult().isInstallSuccessfull(), b2.getInstallResult().isInstallSuccessfull());
        assertEquals(b1.getInstallResult().getInstallLogs(),b2.getInstallResult().getInstallLogs());
        assertEquals(b1.getInstallResult().getRawInstallLogs(),b2.getInstallResult().getRawInstallLogs());

        assertEquals(b1.getBuildResult().isBuildSuccessfull(), b2.getBuildResult().isBuildSuccessfull());
        assertEquals(b1.getBuildResult().getBuildLogs(),b2.getBuildResult().getBuildLogs());
        assertEquals(b1.getBuildResult().getRawBuildLogs(),b2.getBuildResult().getRawBuildLogs());

        assertEquals(b1.getTestResult().isTestSuccessfull(), b2.getTestResult().isTestSuccessfull());
        assertEquals(b1.getTestResult().getTestLogs(),b2.getTestResult().getTestLogs());
        assertEquals(b1.getTestResult().getRawTestLogs(),b2.getTestResult().getRawTestLogs());
    }



}