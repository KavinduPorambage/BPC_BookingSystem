package BPC_System.Controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import BPC_System.Models.PatientModel;
import BPC_System.Models.TimetableModel;
import BPC_System.Utilities.PatientHandler;
import BPC_System.Utilities.TimetableHandler;
import BPC_System.Models.PhysiotherapistsModel;
import BPC_System.Utilities.PhysiotherapistsHandler;

public class BookingController {

    Scanner scanner = new Scanner(System.in);

    public boolean patientExitMenu;
    public boolean exitBooking;
    public boolean exitCancelMenu;
    public boolean exitAttendMenu;
    private MasterController masterController;

    public BookingController(MasterController masterController) {
        this.masterController = masterController;
        this.scanner = new Scanner(System.in);
        this.exitBooking = false;
        this.exitCancelMenu = false;
        this.exitAttendMenu = false;
    }

    // to show avaliable slots
    private void showAvailableSlots(String expertise, List<TimetableModel> availableSlots) {
        try{
            List<PhysiotherapistsModel> physiotherapists = PhysiotherapistsHandler.readPhysiotherapistsJson();

            System.out.printf("%-5s%-25s%-30s%-30s%-15s%-15s%-15s%n", "ID", "Physiotherapists ID", "Expertise", "Treatment", "Date", "Time", "Status");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");

            for (int i = 0; i < availableSlots.size(); i++) {
                TimetableModel s = availableSlots.get(i);

                String phyName = "Unknown";
                for (PhysiotherapistsModel physio : physiotherapists) {
                    if (physio.getId() == s.getPhysioId()) {
                        phyName = physio.getName();
                        break;
                    }
                }

                System.out.printf("%-5d%-25s%-30s%-30s%-15s%-15s%-15s%n",
                        s.getId(), phyName, s.getExpertiseArea(), s.getTreatment(),
                        s.getDate(), s.getTime(), s.getStatus());
            }
        }
        catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }


    private void updateBooking(String status) {
        try{
            System.out.print("Enter Your Booking Id: ");
            String bookingID = scanner.next();

            System.out.print("Enter Your Patient Id: ");
            String patientId = scanner.next();

            // Load all patients
            ArrayList<PatientModel> patients = PatientHandler.readPatientsFromFile();
            PatientModel bookPatient = null;

            // Search patient by ID
            for (PatientModel p : patients) {
                if (p.getId() == Integer.parseInt(patientId)) {
                    bookPatient = p;
                    break;
                }
            }

            //Check whether patient registered or not by id
            if (bookPatient != null) {
                if(status == "Booked" && TimetableHandler.checkBookedTimeSlot(patientId, bookingID) ){
                    if(TimetableHandler.updateBookingFile(patientId, bookingID, status)){//only used for booking an appointment only
                        // To Display booked slot
                        List<TimetableModel> slots = TimetableHandler.readTimetableFile();
                        for (TimetableModel slot : slots) {
                            if (slot.getId() == Integer.parseInt(bookingID)) {
                                List<TimetableModel> slotList = Collections.singletonList(slot);
                                System.out.println("\nBooking Details");
                                showAvailableSlots("Booking Details", slotList);
                            }
                        }
                    }
                }
                else if(status == "Cancelled" && TimetableHandler.checkCancelTimeSlot(patientId, bookingID) ){
                    //only used for cancelling appointment only.
                    if(TimetableHandler.updateBookingFile("0", bookingID, status)){
                        // Display the booked slot
                        List<TimetableModel> slots = TimetableHandler.readTimetableFile();
                        for (TimetableModel slot : slots) {
                            if (slot.getId() == Integer.parseInt(bookingID)) {
                                List<TimetableModel> slotList = Collections.singletonList(slot);
                                System.out.println("\nBooking Details");
                                showAvailableSlots("Booking Details", slotList);
                            }
                        }
                        // next step asking another booking
                        System.out.print("\nDo you want to book an appointment again? (Y/N): ");
                        String responseReBooking = scanner.next();
                        if (responseReBooking.equalsIgnoreCase("y")) {
                            exitBooking = false;
                            bookingStart();
                        }
                    }
                }
                else if(status == "Attended" && TimetableHandler.checkAttendSlot(patientId, bookingID)){
                    // attending appointment only.
                    if(TimetableHandler.updateBookingFile(patientId, bookingID, status)){
                        // Display the booked slot
                        List<TimetableModel> slots = TimetableHandler.readTimetableFile();
                        for (TimetableModel slot : slots) {
                            if (slot.getId() == Integer.parseInt(bookingID)) {
                                List<TimetableModel> slotList = Collections.singletonList(slot);
                                System.out.println("\nBooking Details");
                                showAvailableSlots("Booking Details", slotList);
                            }
                        }
                    }
                }
            }
            else{
                System.out.println("No patient found with the ID " + patientId);
            }
        }
        catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }


    //show all the appointments for a specific expertise area
    private void checkExpertiseArea() {
        try{
            List<TimetableModel> availableSlots;
            System.out.print("Enter Expertise Area: ");
            String expertise = scanner.nextLine().trim();
            availableSlots = TimetableHandler.getSlotsExArea(expertise);
            if(!availableSlots.isEmpty()){
                System.out.println("\nAvailable Slots for " + expertise);// Display available appointments
                showAvailableSlots(expertise, availableSlots);
                System.out.print("\nDo you want to book an appointment? (Y/N): ");
                String responseBooking = scanner.next();
                if (responseBooking.equalsIgnoreCase("y")) {
                    updateBooking("Booked");
                }
            }
            else{
                System.out.println("\nNo Available Slots for " + expertise);
            }
        }
        catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private void CheckPhysiotherapists() {
        try{
            List<TimetableModel> availableSlots;
            List<PhysiotherapistsModel> physios = PhysiotherapistsHandler.readPhysiotherapistsJson();

            System.out.print("Enter Physiotherapists Name: ");
            String physiotherapistsName = scanner.nextLine().trim();

            List<Integer> matchingPhysioIds = new ArrayList<>();
            for (PhysiotherapistsModel physio : physios) {
                if (physio.getName().toLowerCase().contains(physiotherapistsName.toLowerCase())) {
                    matchingPhysioIds.add(physio.getId());
                }
            }

            if(matchingPhysioIds.size()>0){
                availableSlots = TimetableHandler.getSlotsbyPhy(matchingPhysioIds);

                if(availableSlots.size()>0){
                    // Display available appointments
                    System.out.println("\nAvailable Slots for " + physiotherapistsName);
                    showAvailableSlots(physiotherapistsName, availableSlots);

                    System.out.print("\nDo you want to book an appointment? (Y/N): ");
                    String responseBooking = scanner.next();
                    if (responseBooking.equalsIgnoreCase("y")) {
                        updateBooking("Booked");
                    }
                }
                else{
                    System.out.println("\nNo Available Slots for " + physiotherapistsName);
                }
            }
            else{
                System.out.println("No physiotherapists found with the name " + physiotherapistsName);
            }

        }
        catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void bookingStart() {
        exitBooking = false;
        while (!exitBooking) {
            System.out.println("\nBook an Appointment:");
            System.out.println("1. Search by Physiotherapist Name");
            System.out.println("2. Search by Expertise Area");
            System.out.println("3. Return to Main Menu");
            System.out.print("Enter your choice: ");
            try{
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        CheckPhysiotherapists();
                        if(exitCancelMenu){
                            Exit();
                        }
                        break;
                    case 2:
                        checkExpertiseArea();
                        if(exitCancelMenu){
                            Exit();
                        }
                        break;
                    case 3:
                        exitBooking = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
            catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                System.out.println("Please enter a valid input.");
            }
        }

    }

    private void Exit() {
        System.out.print("\nDo you want to exit the program? (Y/N): ");
        String response = scanner.next();
        if (response.equalsIgnoreCase("y")) {
            masterController.exit = true;
            exitBooking = true;
            exitCancelMenu = true;
            System.out.println("Exiting the program. Goodbye!");
        }
    }

    public void cancelBooking() {
        exitCancelMenu = false;
        while (!exitCancelMenu) {
            System.out.println("\nCancel an Appointment:");
            updateBooking("Cancelled");
            System.out.print("\nDo you want to cancel another appointment (Y/N): ");
            String responseAttend = scanner.next();
            if (responseAttend.equalsIgnoreCase("n")) {
                exitCancelMenu = true;
                exitBooking = true;
                break;
            }
        }
    }

    public void attendBooking() {
        exitAttendMenu = false;
        while (!exitAttendMenu) {
            System.out.println("\nAttend an Appointment:");
            updateBooking("Attended");
            System.out.print("\nDo you want to attend another appointment (Y/N): ");
            String responseCancel = scanner.next();
            if (responseCancel.equalsIgnoreCase("n")) {
                exitAttendMenu = true;
                exitBooking = true;
                break;
            }
        }
    }
}
