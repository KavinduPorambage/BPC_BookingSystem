package BPC_System.Utilities;

import BPC_System.Models.TimetableModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimetableHandler {
    private static final String _Timetable_FILE_PATH = "src/main/java/BPC_System/files/TimetableDetails.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    //Read all timetable
    public static List<TimetableModel> readTimetableFile() {
        File file = new File(_Timetable_FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(file, new TypeReference<List<TimetableModel>>() {});
        } catch (IOException e) {
            System.out.println("Error : " + e.getMessage());
            return new ArrayList<>();
        }
    }
    //Get all time slots for physiotherapists
    public static List<TimetableModel> getSlotsbyPhy(List<Integer> matchingPhy) {
        List<TimetableModel> allSlots = readTimetableFile();

        return allSlots.stream()
                .filter(slot -> matchingPhy.contains(slot.getPhysioId()))
                .collect(Collectors.toList());
    }

    // Check if the patient can attend the specific booking
    public static boolean checkAttendSlot(String patientId, String bookingId) {
        List<TimetableModel> slots = readTimetableFile();
        TimetableModel checkSelectedSlot = slots.stream()
                .filter(slot -> slot.getId() == Integer.parseInt(bookingId))
                .findFirst()
                .orElse(null);
        if(checkSelectedSlot == null){
            System.out.println("No Appointment found with the reservation ID " + bookingId);
            return false;
        }
        if(checkSelectedSlot.getStatus().equalsIgnoreCase("Attended")){
            System.out.println("This slot is already attended");
            return false;
        }
        if(!checkSelectedSlot.getStatus().equalsIgnoreCase("Booked") || checkSelectedSlot.getPatientId() != Integer.parseInt(patientId)) {
            System.out.println("This slot is not booked by the patient Id " + patientId);
            return false;
        }
        return true; // Attending is allowed
    }

    //Get all time slots for expertise area
    public static List<TimetableModel> getSlotsExArea(String expertisearea) {
        List<TimetableModel> allSlots = readTimetableFile();

        return allSlots.stream()
                .filter(slot -> slot.getExpertiseArea().toLowerCase().contains(expertisearea.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Check if the patient has booked the specific booking(cancelling)
    public static boolean checkCancelTimeSlot(String patientId, String checkBookingId) {
        List<TimetableModel> slots = readTimetableFile();
        TimetableModel checkSelectedSlot = slots.stream()
                .filter(slot -> slot.getId() == Integer.parseInt(checkBookingId))
                .findFirst()

                .orElse(null);

        if(checkSelectedSlot == null){
            System.out.println("No appointment was found with the reservation ID " + checkBookingId);
            return false;
        }
        if(checkSelectedSlot.getStatus().equalsIgnoreCase("Attended")){
            System.out.println("This slot is already attended");
            return false;
        }
        if(!checkSelectedSlot.getStatus().equalsIgnoreCase("Booked") || checkSelectedSlot.getPatientId() != Integer.parseInt(patientId)) {
            System.out.println("This slot is not booked by the patient Id " + patientId);
            return false;
        }
        return true; // Cancelling is allowed
    }

    // update the booking
    public static boolean updateBookingFile(String patientId, String checkBookingId, String checkStatus) {
        List<TimetableModel> slots = readTimetableFile();
        boolean done = false;
        try{
            for (TimetableModel slot : slots) {
                if (slot.getId() == Integer.parseInt(checkBookingId)) {
                    slot.setPatientId(Integer.parseInt(patientId));
                    slot.setStatus(checkStatus);
                }
            }
            objectMapper.writeValue(new File(_Timetable_FILE_PATH), slots);
            System.out.println("Booking is successful " + checkStatus);
            done = true;
            return done;
        }
        catch (IOException e) {
            System.out.println("Error with reading timetable: " + e.getMessage());
            return false;
        }
    }

    // check whether the patient has duplicate booking
    public static boolean checkBookedTimeSlot(String patientId, String checkBookingId) {
        List<TimetableModel> slots = readTimetableFile();
        TimetableModel checkSelectedSlot = slots.stream()
                .filter(slot -> slot.getId() == Integer.parseInt(checkBookingId))
                .findFirst()
                .orElse(null);
        if (checkSelectedSlot == null) {
            System.out.println("No appointment was found with the reservation ID " + checkBookingId);
            return false;
        }
        if (!(checkSelectedSlot.getStatus().equalsIgnoreCase("Available") || checkSelectedSlot.getStatus().equalsIgnoreCase("Cancelled"))) {
            System.out.println("This section is already reserved.. Please try another slot.");
            return false;
        }
        boolean checkDuplicateBooking = slots.stream()
                .anyMatch(slot ->
                        slot.getPatientId() == Integer.parseInt(patientId) &&
                                slot.getDate().equals(checkSelectedSlot.getDate()) &&
                                slot.getTime().equals(checkSelectedSlot.getTime())
                );
        if (checkDuplicateBooking) {
            System.out.println("You already have another booking at this same time.");
            return false;
        }
        return true; // Booking is allowed
    }
}
