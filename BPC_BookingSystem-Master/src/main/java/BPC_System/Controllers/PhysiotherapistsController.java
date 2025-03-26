package BPC_System.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import BPC_System.Models.PhysiotherapistsModel;
import BPC_System.Utilities.PhysiotherapistsHandler;

public class PhysiotherapistsController {

    Scanner scanner = new Scanner(System.in);
    public boolean physiothrerapistsExitMenu;
    private MasterController masterController;

    public PhysiotherapistsController(MasterController masterController) {
        this.masterController = masterController;
        this.scanner = new Scanner(System.in);
        this.physiothrerapistsExitMenu = false;
    }

    public void physiostart() {

        physiothrerapistsExitMenu = false;
        while (!physiothrerapistsExitMenu) {
            System.out.println("\nManage Physios:");
            System.out.println("1. Add a Physio");
            System.out.println("2. Remove a Physio");
            System.out.println("3. View All Physios");
            System.out.println("4. Return to Main Menu");
            System.out.print("Enter your choice: ");
            try{
                int choice = new java.util.Scanner(System.in).nextInt();

                switch (choice) {
                    case 1:
                        addPhysiotherapists();
                        isExit();
                        break;
                    case 2:
                        deletePhysiotherapists();
                        isExit();
                        break;
                    case 3:
                        physiotherapistslist();
                        isExit();
                        break;
                    case 4:
                        physiothrerapistsExitMenu = true;
                        break;
                    default:
                        System.out.println("Invalid choice..! Please try again.");
                        System.out.println("Please try again..!");

                }
            }
            catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                System.out.println("Please enter a valid input.");
            }
        }

    }

    private void addPhysiotherapists() {
        try{
            boolean MoreAddPhy = true;

            while (MoreAddPhy) {
                int physiotherapistsId = generatePhysiotherapiststId();
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }

                System.out.print("\nEnter Physiotherapists Full Name: ");
                String fullName = scanner.nextLine().trim();

                System.out.print("Enter Address: ");
                String address = scanner.nextLine().trim();

                System.out.print("Enter Phone Number: ");
                String phone = scanner.nextLine().trim();

                Map<String, List<String>> expertiseAreas = new HashMap<>();

                while (true) {
                    System.out.print("Enter Expertise Area (or type 'done' to finish): ");
                    String expertiseArea = scanner.nextLine().trim();

                    if (expertiseArea.equalsIgnoreCase("done")) break;

                    System.out.print("Enter Treatments for " + expertiseArea + " (comma-separated): ");
                    String[] treatmentsArray = scanner.nextLine().trim().split(",");

                    List<String> treatments = new ArrayList<>();
                    for (String treatment : treatmentsArray) {
                        treatments.add(treatment.trim());
                    }
                    expertiseAreas.put(expertiseArea, treatments);
                }

                PhysiotherapistsModel newPhysiotherapists = new PhysiotherapistsModel(physiotherapistsId, fullName, address, phone, expertiseAreas); // Create Physio object

                List<PhysiotherapistsModel> physioModel = PhysiotherapistsHandler.readPhysiotherapistsJson();

                physioModel.add(newPhysiotherapists);
                PhysiotherapistsHandler.savePhysiotherapistsJson(physioModel);

                System.out.println("\nPhysiotherapist added successfully!");
                MoreAddPhy = false;
            }
        }
        catch (Exception e) {
            System.out.println("An error occurred while adding the physio: " + e.getMessage());
        }
    }



    private void isExit() {
        System.out.print("\nDo you want to exit the program? (Y/N): ");
        String response = scanner.next();
        if (response.equalsIgnoreCase("y")) {
            masterController.exit = true;
            physiothrerapistsExitMenu = true;
            System.out.println("Exiting the program... Goodbye!");
            System.out.println("Goodbye!");

        }
    }

    private int generatePhysiotherapiststId() {
        List<PhysiotherapistsModel> physios = PhysiotherapistsHandler.readPhysiotherapistsJson();
        int maxId = 0;

        for (PhysiotherapistsModel physio : physios) { // Find the maximum ID
            try {
                int currentId = physio.getId();
                if (currentId > maxId) {
                    maxId = currentId;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Physio ID format: " + physio.getId());
            }
        }
        return maxId + 1;
    }


    private void physiotherapistslist() {

        try{
            List<PhysiotherapistsModel> phy = PhysiotherapistsHandler.readPhysiotherapistsJson();// Load physiotherapists

            if (phy != null && !phy.isEmpty()) {
                System.out.println("\n Physiotherapists Details \n");
                System.out.println("--------------------------------------------------------------------------------------------------------");
                System.out.printf("%-5s%-25s%-25s%-50s%n", "Physiotherapist ID", "Physiotherapist Name", "Physiotherapist Expertise Area", "Treatments");
                System.out.println("--------------------------------------------------------------------------------------------------------");

                for (PhysiotherapistsModel physio : phy) {
                    boolean fr = true;
                    Map<String, List<String>> expertiseAreas = physio.getExpertiseAreas();

                    if (expertiseAreas.isEmpty()) {//If no expertise areas
                        System.out.printf("%-5s%-25s%-25s%-50s%n",
                                physio.getId(),
                                physio.getName(),
                                "No Expertise Listed",
                                "Not Available"
                        );
                        continue; // Skip
                    }

                    for (Map.Entry<String, List<String>> entry : physio.getExpertiseAreas().entrySet()) {
                        String expertiseArea = entry.getKey();
                        String treatments = String.join(", ", entry.getValue()); // Convert to a string

                        if (fr) {
                            System.out.printf("%-5s%-25s%-25s%-50s%n",
                                    physio.getId(),
                                    physio.getName(),
                                    expertiseArea,
                                    treatments);
                            fr = false;
                        } else {
                            System.out.printf("%-5s%-25s%-25s%-50s%n",
                                    "", // Empty ID
                                    "", // Empty Name
                                    expertiseArea,
                                    treatments);
                        }
                    }
                    System.out.println("\n");
                }
                System.out.println("--------------------------------------------------------------------------------------------------------");
            } else {
                System.out.println("No Physiotherapist Found.");
            }
        }
        catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private void deletePhysiotherapists() {

        boolean MoreDeletePhy = true;

        while (MoreDeletePhy) {
            System.out.print("\nEnter the Physiotherapists ID to delete: ");
            String physioId = scanner.next();

            // Load all physiotherapists
            List<PhysiotherapistsModel> physios = PhysiotherapistsHandler.readPhysiotherapistsJson();
            PhysiotherapistsModel physioDelete = null;

            // Search by ID
            for (PhysiotherapistsModel physio : physios) {
                if (physio.getId() == Integer.parseInt(physioId)) {
                    physioDelete = physio;
                    break;
                }
            }

            if (physioDelete != null) {
                System.out.println("\n Physio Details \n");
                System.out.println("--------------------------------------------------------------------------------------------------------");
                System.out.printf("%-15s%-20s%-40s%-15s%n", "Physiotherapist ID", "Physiotherapist Full Name", "Physiotherapist Address", "Physiotherapist Contact Number");
                System.out.println("--------------------------------------------------------------------------------------------------------");
                System.out.printf("%-15s%-20s%-40s%-15s%n", physioDelete.getId(), physioDelete.getName(), physioDelete.getAddress(), physioDelete.getPhone());
                System.out.println("--------------------------------------------------------------------------------------------------------");

                System.out.print("Are you sure you want to delete this physiotherapist? (Y/N): ");
                String confirm = scanner.next();

                if (confirm.equalsIgnoreCase("y")) {

                    physios.remove(physioDelete);// Remove the patient and update
                    if (PhysiotherapistsHandler.savePhysiotherapistsJson(physios)) {
                        System.out.println("Physiotherapist deleted successfully!");
                    } else {
                        System.out.println("Error:");
                    }
                } else {
                    System.out.println("Physiotherapist deletion canceled.");
                }
            } else {
                System.out.println("Physiotherapist with ID " + physioId + " is not found.");
            }

            // Ask if the user wants to delete another physio
            System.out.print("\nDo you want to delete another Physiotherapist? (Y/N): ");
            String deleteAnother = scanner.next();
            MoreDeletePhy = deleteAnother.equalsIgnoreCase("y");
        }

    }

}
