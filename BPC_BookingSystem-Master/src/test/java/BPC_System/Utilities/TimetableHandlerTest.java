/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package BPC_System.Utilities;

import BPC_System.Models.TimetableModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author kavinduhashan
 */
public class TimetableHandlerTest {
    
    public TimetableHandlerTest() {
    }

    private static final String TEST_FILE_PATH = "src/main/java/BPC_System/Files/TimetableDetails.json";
    private List<TimetableModel> originalTimetable;

    @BeforeEach
    public void setUp() throws IOException {
        // Backup original file content
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            originalTimetable = new ObjectMapper().readValue(file, new TypeReference<List<TimetableModel>>() {});
        } else {
            originalTimetable = new ArrayList<>();
        }
    }

    @AfterEach
    public void tearDown() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        FileWriter writer = new FileWriter(TEST_FILE_PATH);
        mapper.writeValue(writer, originalTimetable);
        writer.close();
    }

    @Test
    public void testReadTimetable() throws IOException {
        List<TimetableModel> dummyData = new ArrayList<>();
        dummyData.add(new TimetableModel());

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(TEST_FILE_PATH), dummyData);
        List<TimetableModel> readData = TimetableHandler.readTimetableFile();
        assertNotNull(readData, "The returned list should not be null");
        //assertEquals(1, readData.size(), "There should be exactly one Timetable entry");
        assertNotNull(readData.get(0), "The first Timetable object should not be null");
    }
    
}
