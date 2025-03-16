package BPC_System.Controllers;

import java.util.ArrayList;
import java.util.Scanner;
import BPC_System.Models.PatientModel;
import BPC_System.Utilities.PatientHandler;

public class PatientController {

    Scanner scanner = new Scanner(System.in);
    public boolean patientExitMenu;
    private MasterController masterController;

    public PatientController(MasterController masterController) {
        this.scanner = new Scanner(System.in);
        this.patientExitMenu = false;
    }

    public void patientStart() {
        patientExitMenu = false;
        while (!patientExitMenu) {
            System.out.println("\nManage Patients:");
            System.out.println("1. Add a Patient");
            System.out.println("2. Remove a Patient");
            System.out.println("3. View All Patients");
            System.out.println("4. Return to Main Menu");
            System.out.print("\nEnter your choice: ");
            try{
                int choice = new java.util.Scanner(System.in).nextInt();

                switch (choice) {
                    case 1:
                        addPatient();
                        isExit();
                        break;
                    case 2:
                        deletePatient();
                        isExit();
                        break;
                    case 3:
                        patientDetails();
                        isExit();
                        break;
                    case 4:
                        patientExitMenu = true;
                        break;
                    default:
                        System.out.println("Invalid choice..... Please try again.");
                        System.out.println("Please try again.");

                }
            }
            catch (Exception e) {
                System.out.println("ERROR!!: " + e.getMessage());
                System.out.println("Please enter a valid input!");
            }
        }

    }


    // exit or continue
    public void isExit(){
        // Ask if the user wants to exit or continue
        System.out.print("\nDo you want to exit the program? (Y/N): ");
        String response = scanner.next();
        if (response.equalsIgnoreCase("y")) {
            masterController.exit = true;
            patientExitMenu = true;
            System.out.println("Exiting the program.....");
            System.out.println("Goodbye!");
        }
    }

    // Load patients from patientDetails.txt
    public void patientDetails(){

        ArrayList<PatientModel> patients = PatientHandler.readPatientsFromFile();

        if (!patients.isEmpty()) {
            System.out.println("\n Patients Details \n");
            System.out.println("--------------------------------------------------------------------------------------------------------");
            System.out.printf("%-15s%-20s%-40s%-15s%n", "Patient ID", "Full Name", "Address", "Phone");
            System.out.println("--------------------------------------------------------------------------------------------------------");
            for (PatientModel patient : patients) {
                System.out.printf("%-15s%-20s%-40s%-15s%n", patient.getId(), patient.getName(), patient.getAddress(), patient.getPhone());
            }
            System.out.println("--------------------------------------------------------------------------------------------------------");
        } else {
            System.out.println("No patients found!");
        }
    }


    // set a new ID to the newly added patient
    private int generateNextPatientId() {
        ArrayList<PatientModel> patients = PatientHandler.readPatientsFromFile();
        int maxId = 0;

        // Find the maximum existing ID
        for (PatientModel patient : patients) {
            try {
                int currentId = patient.getId();
                if (currentId > maxId) {
                    maxId = currentId;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Patient ID format: " + patient.getId());
            }
        }

        // Increment ID by 1
        return maxId + 1;
    }


    // register patient
    public void addPatient() {
        try {
            // Generate auto-incremented ID
            int newId = generateNextPatientId();

            System.out.print("\nEnter Full Name: ");
            String fullName = scanner.next();

            System.out.print("Enter Address: ");
            scanner.nextLine(); // Consume leftover newline
            String address = scanner.nextLine();

            System.out.print("Enter Phone Number: ");
            String phone = scanner.next();

            // Create a new patient object
            PatientModel newPatient = new PatientModel(newId, fullName, address, phone);

            // Save the patient to the file
            if (PatientHandler.savePatientToFile(newPatient)) {
                System.out.println("Patient added successfully and saved to file!");
            } else {
                System.out.println("Error: Could not save the patient to the file.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while adding the patient: " + e.getMessage());
        }
    }


    //delete a patients
    public void deletePatient() {
        boolean delete = true;

        while (delete) {
            System.out.print("\nEnter the Patient ID to delete: ");
            String patientId = scanner.next();

            // Load all patients from the patientDetails.txt
            ArrayList<PatientModel> patients = PatientHandler.readPatientsFromFile();
            PatientModel needToDelete = null;

            // Search for the patient by ID
            for (PatientModel patient : patients) {
                if (patient.getId() == Integer.parseInt(patientId)) {
                    needToDelete = patient;
                    break;
                }
            }

            if (needToDelete != null) {
                // Display the patient details
                System.out.println("\n Our Patient Details \n");
                System.out.println("--------------------------------------------------------------------------------------------------------");
                System.out.printf("%-15s%-20s%-40s%-15s%n", "Patient ID", "Full Name", "Address", "Phone");
                System.out.println("--------------------------------------------------------------------------------------------------------");
                System.out.printf("%-15s%-20s%-40s%-15s%n", needToDelete.getId(), needToDelete.getName(), needToDelete.getAddress(), needToDelete.getPhone());
                System.out.println("--------------------------------------------------------------------------------------------------------");

                // confirmation to delete
                System.out.print("Are you sure you want to delete this patient? (Y/N): ");
                String confirmation = scanner.next();

                if (confirmation.equalsIgnoreCase("y")) {
                    // Remove the patient and update the patientDetails.txt
                    patients.remove(needToDelete);
                    if (PatientHandler.saveAllPatientsToFile(patients)) {
                        System.out.println("Patient deleted successfully!");
                    } else {
                        System.out.println("Error: Could not update the file.");
                    }
                } else {
                    System.out.println("Patient deletion canceled.");
                }
            } else {
                System.out.println("Patient with ID " + patientId + " not found.");
            }

            // Ask if the user wants to delete another patient
            System.out.print("\nDo you want to delete another patient? (Y/N): ");
            String response = scanner.next();
            delete = response.equalsIgnoreCase("y");
        }
    }

}
