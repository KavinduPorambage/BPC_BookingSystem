/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package BPC_System.Controllers;

import java.util.List;
import java.util.ArrayList;

import BPC_System.Utilities.PatientHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import BPC_System.Models.PatientModel;

class PatientControllerTest {

    private List<PatientModel> RealPatients;

    public PatientControllerTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        System.out.println("Starting..");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("Finished...");
    }

    @BeforeEach
    public void setUp() {
        RealPatients = new ArrayList<>(PatientHandler.readPatientsFromFile());
    }

    @AfterEach
    public void tearDown() {
        PatientHandler.saveAllPatientsToFile((ArrayList<PatientModel>) RealPatients);
    }

    @Test
    public void testReadAllPatients() {
        List<PatientModel> patients = PatientHandler.readPatientsFromFile();
        assertNotNull(patients, "Patient list should not be null");
    }

    @Test
    public void testGeneratePatientId() {
        int generatedId = PatientController.generateNextPatientId();
        List<PatientModel> patients = PatientHandler.readPatientsFromFile();
        boolean idExists = patients.stream().anyMatch(patient -> patient.getId() == generatedId);
        assertFalse(idExists, "Generated Patient ID should not already exist.");
    }


    @Test
    public void testAddPatient() {
        int initialSize = PatientHandler.readPatientsFromFile().size();

        PatientModel newPatient = new PatientModel(
                PatientController.generateNextPatientId(),
                "Kavindu Hashan",
                "Flat 2, Newlands",
                "0123456789"
        );

        boolean saved = PatientHandler.savePatientToFile(newPatient);
        assertTrue(saved, "New patient saved successfully.");
        List<PatientModel> updatedPatients = PatientHandler.readPatientsFromFile();
        assertEquals(initialSize + 1, updatedPatients.size(), "Patient list size should increase by 1 after adding.");
    }


}
