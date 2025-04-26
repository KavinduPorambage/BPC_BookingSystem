/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package BPC_System.Controllers;

import BPC_System.Utilities.PhysiotherapistsHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import BPC_System.Models.PhysiotherapistsModel;
import org.junit.jupiter.api.*;
import java.util.List;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

/**
 *
 * @author kavinduhashan
 */
public class PhysiotherapistsControllerTest {

    private List<PhysiotherapistsModel> RealPhysios;

    @BeforeAll
    public void setUpClass() {
        RealPhysios = PhysiotherapistsHandler.readPhysiotherapistsJson();
    }

    @AfterAll
    public void tearDownClass() {
        PhysiotherapistsHandler.savePhysiotherapistsJson(RealPhysios);
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {


    }

    @Test
    public void testDeletePhysio() {
        List<PhysiotherapistsModel> physios = PhysiotherapistsHandler.readPhysiotherapistsJson();
        int initialSize = physios.size();

        if (initialSize == 0) {
            PhysiotherapistsModel dummy = new PhysiotherapistsModel(
                    PhysiotherapistsController.generatePhysiotherapiststId(),
                    "Dummy Physio",
                    "Somewhere",
                    "9876543210",
                    new java.util.HashMap<>()
            );
            physios.add(dummy);
            PhysiotherapistsHandler.savePhysiotherapistsJson(physios);
        }
        List<PhysiotherapistsModel> updatedPhysios = PhysiotherapistsHandler.readPhysiotherapistsJson();
        PhysiotherapistsModel delete = updatedPhysios.get(updatedPhysios.size() - 1);
        updatedPhysios.remove(delete);
        PhysiotherapistsHandler.savePhysiotherapistsJson(updatedPhysios);
        List<PhysiotherapistsModel> finalList = PhysiotherapistsHandler.readPhysiotherapistsJson();
        assertEquals(initialSize - 1, finalList.size(), "Physio should be deleted successfully.");
    }

    @Test
    public void testAllPhysiosReadNotNull() {
        List<PhysiotherapistsModel> physios = PhysiotherapistsHandler.readPhysiotherapistsJson();
        assertNotNull(physios, "The list of physios should not be null.");
    }

    @Test
    public void testGeneratePhysioIdIncrements() {
        int uid01 = PhysiotherapistsController.generatePhysiotherapiststId();
        int uid02 = PhysiotherapistsController.generatePhysiotherapiststId();
        assertTrue(uid02 >= uid01, "The new physio ID should be greater or equal to the previous ID.");
    }

    @Test
    public void testAddPhysio() {
        List<PhysiotherapistsModel> before = PhysiotherapistsHandler.readPhysiotherapistsJson();
        int sizeBefore = before.size();
        PhysiotherapistsModel newPhysio = new PhysiotherapistsModel(
                PhysiotherapistsController.generatePhysiotherapiststId(),
                "Test Physio",
                "123 Test Street",
                "0123456789",
                new java.util.HashMap<>()
        );
        before.add(newPhysio);
        PhysiotherapistsHandler.savePhysiotherapistsJson(before);
        List<PhysiotherapistsModel> physiosAfter = PhysiotherapistsHandler.readPhysiotherapistsJson();
        int sizeAfter = physiosAfter.size();

        assertEquals(sizeBefore + 1, sizeAfter, "Physio should be added successfully.");
    }


    
}
