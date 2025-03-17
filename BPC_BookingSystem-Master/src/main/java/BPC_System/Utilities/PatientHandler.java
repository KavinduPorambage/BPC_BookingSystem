//
// Kavindu Hashan Porambage
// 23081660
//

package BPC_System.Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import BPC_System.Models.PatientModel;

public class PatientHandler {

    private static final String FILE_PATH = "src/main/java/BPC_System/Files/PatientDetails.txt";

    // read file
    public static ArrayList<PatientModel> readPatientsFromFile() {
        ArrayList<PatientModel> patients = new ArrayList();

        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/BPC_System/Files/PatientDetails.txt"))) {
            while((line = reader.readLine()) != null) {
                String[] data = line.split(",", 3);
                if (data.length == 3) {
                    int id = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    String address = data[2].substring(0, data[2].lastIndexOf(44)).trim();
                    String phone = data[2].substring(data[2].lastIndexOf(44) + 1).trim();
                    patients.add(new PatientModel(id, name, address, phone));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading patients file: " + e.getMessage());
        }

        return patients;
    }

    // Save file
    public static boolean savePatientToFile(PatientModel patient) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) { // Append mode
            File file = new File(FILE_PATH);
            // Check if the file exists and is not empty
            boolean isEmpty = !file.exists() || file.length() == 0;
            // Write the patient data to a new line
            String patientData = patient.getId() + "," + patient.getName() + "," + patient.getAddress() + "," + patient.getPhone();
            if (!isEmpty) {
                writer.newLine();
            }
            writer.write(patientData);
            //Success
            return true;
        } catch (IOException e) {
            System.out.println("Error saving patient to file: " + e.getMessage());
            return false;
        }
    }

    // delete patient
    public static boolean saveAllPatientsToFile(ArrayList<PatientModel> patients) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (PatientModel patient : patients) {
                String patientData = patient.getId() + "," + patient.getName() + "," + patient.getAddress() + "," + patient.getPhone();
                writer.write(patientData);
                writer.newLine();
            }
            return true; // Success
        } catch (IOException e) {
            System.out.println("Error saving patients to file: " + e.getMessage());
            return false;
        }
    }

}
