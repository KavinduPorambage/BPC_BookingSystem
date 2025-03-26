package BPC_System.Utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import BPC_System.Models.PhysiotherapistsModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhysiotherapistsHandler {

    // save
    public static List<PhysiotherapistsModel> readPhysiotherapistsJson() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>(); // Return empty list if file does not exist
        }
        try {
            return objectMapper.readValue(file, new TypeReference<List<PhysiotherapistsModel>>() {});
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static final String FILE_PATH = "src/main/java/BPC_System/Files/Physiotherapists.json";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Save
    public static boolean savePhysiotherapistsJson(List<PhysiotherapistsModel> physios) {
        try {
            objectMapper.writeValue(new File(FILE_PATH), physios);
            return true;
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

}
