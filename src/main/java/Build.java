public class Build {
    private int buildID;
    private buildResult buildResult;
    private testResult testResult;
    private Object rawGithubLogs;

    public Build(int buildID, buildResult buildResult, testResult testResult, Object rawGithubLogs) {
        this.buildID = buildID;
        this.buildResult = buildResult;
        this.testResult = testResult;
        this.rawGithubLogs = rawGithubLogs;
    }

    //--------------Getters and Setters ------------------
    public buildResult getBuildResult() {
        return buildResult;
    }

    public void setBuildResult(buildResult buildResult) {
        this.buildResult = buildResult;
    }

    public testResult getTestResult() {
        return testResult;
    }

    public void setTestResult(testResult testResult) {
        this.testResult = testResult;
    }

    public Object getRawGithubLogs() {
        return rawGithubLogs;
    }

    public void setRawGithubLogs(Object rawGithubLogs) {
        this.rawGithubLogs = rawGithubLogs;
    }

    public int getBuildID() {
        return buildID;
    }

    public void setBuildID(int buildID) {
        this.buildID = buildID;
    }
}

class buildResult{
    private boolean buildSuccessfull;
    private String buildLogs;

    public buildResult(boolean buildSuccessfull, String buildLogs) {
        this.buildSuccessfull = buildSuccessfull;
        this.buildLogs = buildLogs;
    }

    //--------------Getters and Setters ------------------

    public boolean isBuildSuccessfull() {
        return buildSuccessfull;
    }

    public void setBuildSuccessfull(boolean buildSuccessfull) {
        this.buildSuccessfull = buildSuccessfull;
    }

    public String getBuildLogs() {
        return buildLogs;
    }

    public void setBuildLogs(String buildLogs) {
        this.buildLogs = buildLogs;
    }


}

class testResult{
    private boolean testSuccessfull;
    private String testLogs;

    public testResult(boolean testSuccessfull, String testLogs) {
        this.testSuccessfull = testSuccessfull;
        this.testLogs = testLogs;
    }

    //--------------Getters and Setters ------------------

    public boolean isTestSuccessfull() {
        return testSuccessfull;
    }

    public void setTestSuccessfull(boolean testSuccessfull) {
        this.testSuccessfull = testSuccessfull;
    }

    public String getTestLogs() {
        return testLogs;
    }

    public void setTestLogs(String testLogs) {
        this.testLogs = testLogs;
    }
}
