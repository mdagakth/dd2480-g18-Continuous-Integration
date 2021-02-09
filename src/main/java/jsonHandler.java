import com.google.gson.Gson;


import java.io.*;

public class jsonHandler {
    private Gson gson;
    private String filePath;

    public jsonHandler(){
        gson = new Gson();
        filePath = "src/main/resources/test.json";
    }
    public jsonHandler(String filePath){
        gson = new Gson();
        this.filePath = filePath;
    }

    public BuildHistory readBuildHistory(){
        BuildHistory db = null;
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filePath)) {

            // Convert JSON File to BuildHistory object
            db = gson.fromJson(reader, BuildHistory.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return db;
    }


    public void saveBuildHistory(BuildHistory db){
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(db, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
