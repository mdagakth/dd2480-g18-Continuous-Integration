import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

public class jsonHandeler {
    private Gson gson;
    private String filePath;

    public jsonHandeler(){
        gson = new Gson();
        filePath = "src/main/resources/BuildHistoryDB.json";
    }
    public jsonHandeler(String filePath){
        gson = new Gson();
        this.filePath = filePath;
    }

    public BuildHistory readBuildHistory(){
        BuildHistory db = null;
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filePath)) {

            // Convert JSON File to BuildHistory boject
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
