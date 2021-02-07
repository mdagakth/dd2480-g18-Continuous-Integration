

public class Build {
    public String commitID;
    public buildResult buildResult;
    public testResult testResult;
    public Object rawGithubLogs;
}

class buildResult{
    public boolean buildSuccessfull;
    public String buildLogs;
}

class testResult{
    public boolean testSuccessfull;
    public String testLogs;
}
