import java.util.ArrayList;

public class BuildHistory {
    public ArrayList<Build> buildHistory;

    public BuildHistory(ArrayList<Build> buildHistory) {
        this.buildHistory = buildHistory;
    }

    public ArrayList<Build> getBuildHistory() {
        return buildHistory;
    }

    public void setBuildHistory(ArrayList<Build> buildHistory) {
        this.buildHistory = buildHistory;
    }
}
