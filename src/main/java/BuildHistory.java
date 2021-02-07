import java.util.ArrayList;

public class BuildHistory {
    private ArrayList<Build> buildHistory;

    public BuildHistory(ArrayList<Build> buildHistory) {
        this.buildHistory = buildHistory;
    }
    public BuildHistory(){
        buildHistory = null;
    }

    /**
     * Find a build given its build id, returns null if build is not in build history
     * @param buildID
     * @return build
     */
    public Build findBuild(int buildID){
        for (int i = 0; i < buildHistory.size(); i++) {
            if(buildHistory.get(i).getBuildID() == buildID){
                return buildHistory.get(i);
            }
        }
        return null;
    }

    /**
     * Adds a build to the build history if the build id is not already in the db.
     * @param b build to be added to db
     */
    public void addBuildToDB(Build b){
        for (int i = 0; i < buildHistory.size(); i++) {
            if(b.getBuildID() == buildHistory.get(i).getBuildID()){
                System.err.println("A build with this build id is already present in database");
                return;
            }
        }
        //build id is unique
        buildHistory.add(b);
    }


    //--------------Getters and Setters ------------------
    public ArrayList<Build> getBuildHistory() {
        return buildHistory;
    }

    public void setBuildHistory(ArrayList<Build> buildHistory) {
        this.buildHistory = buildHistory;
    }


}
