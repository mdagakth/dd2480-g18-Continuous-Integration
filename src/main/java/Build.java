public class Build {
    private int buildID;
    private installResult installResult;
    private buildResult buildResult;
    private testResult testResult;
    private Object rawGithubLogs;

    public Build(int buildID, installResult installResult, buildResult buildResult, testResult testResult, Object rawGithubLogs) {
        this.buildID = buildID;
        this.installResult = installResult;
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

    public installResult getInstallResult() {
        return installResult;
    }

    public void setInstallResult(installResult installResult) {
        this.installResult = installResult;
    }
}

class buildResult{
    private boolean buildSuccessfull;
    private String buildLogs;
    private String rawBuildLogs;

    public buildResult(boolean buildSuccessfull, String buildLogs, String rawBuildLogs) {
        this.buildSuccessfull = buildSuccessfull;
        this.buildLogs = buildLogs;
        this.rawBuildLogs = rawBuildLogs;
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


    public String getRawBuildLogs() {
        return rawBuildLogs;
    }

    public void setRawBuildLogs(String rawBuildLogs) {
        this.rawBuildLogs = rawBuildLogs;
    }
}

class testResult{
    private boolean testSuccessfull;
    private String testLogs;
    private String rawTestLogs;


    public testResult(boolean testSuccessfull, String testLogs, String rawTestLogs) {
        this.testSuccessfull = testSuccessfull;
        this.testLogs = testLogs;
        this.rawTestLogs = rawTestLogs;
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

    public String getRawTestLogs() {
        return rawTestLogs;
    }

    public void setRawTestLogs(String rawTestLogs) {
        this.rawTestLogs = rawTestLogs;
    }
}



class installResult{
    private boolean installSuccessfull;
    private String installLogs;
    private String rawInstallLogs;

    public installResult(boolean installSuccessfull, String installLogs, String rawInstallLogs) {
        this.installSuccessfull = installSuccessfull;
        this.installLogs = installLogs;
        this.rawInstallLogs = rawInstallLogs;
    }

    //--------------Getters and Setters ------------------

    public boolean isInstallSuccessfull() {
        return installSuccessfull;
    }

    public void setInstallSuccessfull(boolean installSuccessfull) {
        this.installSuccessfull = installSuccessfull;
    }

    public String getInstallLogs() {
        return installLogs;
    }

    public void setInstallLogs(String installLogs) {
        this.installLogs = installLogs;
    }

    public String getRawInstallLogs() {
        return rawInstallLogs;
    }

    public void setRawInstallLogs(String rawInstallLogs) {
        this.rawInstallLogs = rawInstallLogs;
    }
}

