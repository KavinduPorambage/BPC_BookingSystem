package BPC_System.Controllers;

import java.util.Scanner;

public class MasterController {

    private final PatientController patientController = new PatientController(this);
    private final PhysiotherapistsController physioController = new PhysiotherapistsController(this);
    private final BookingController bookingController = new BookingController(this);
    public boolean exit = false;

    //public MasterController() {
    //}

    public void Head() {
        System.out.println();
        System.out.println("+*******************************************+");
        System.out.println("|        Welcome to Boost Physio Clinic     |");
        System.out.println("|*******************************************|");
    }

    public void MainStart() {
        while(!this.exit) {
            System.out.println(" +-----------------------------------------+");
            System.out.println(" |     Option    |       Description       |");
            System.out.println(" +---------------+-------------------------+");
            System.out.println(" |       1       | Manage Physiotherapists |");
            System.out.println(" +---------------+-------------------------+");
            System.out.println(" |       2       |     Manage Patients     |");
            System.out.println(" +---------------+-------------------------+");
            System.out.println(" |       3       |   Book an Appointment   |");
            System.out.println(" +---------------+-------------------------+");
            System.out.println(" |       4       |     Attend a Booking    |");
            System.out.println(" +---------------+-------------------------+");
            System.out.println(" |       5       |     Change a Booking    |");
            System.out.println(" +---------------+-------------------------+");
            System.out.println(" |       6       |     Generate Reports    |");
            System.out.println(" +---------------+-------------------------+");
            System.out.println(" |       7       |          Exit           |");
            System.out.println(" +-----------------------------------------+");
            System.out.print("\nEnter your choice: ");

            try {
                int choice = (new Scanner(System.in)).nextInt();
                switch (choice) {
                    case 1:
                        this.physioController.physiostart();
                        break;
                    case 2:
                        this.patientController.patientStart();
                        break;
                    case 3:
                        this.bookingController.bookingstart();
                        break;
                    case 4:
                        this.bookingController.attendBooking();
                        break;
                    case 5:
                        this.bookingController.cancelBooking();
                        break;
                    case 6:
                        this.bookingController.bookingstart();
                        break;
                    case 7:
                        System.out.println("\nExiting the program.....");
                        System.out.println("Goodbye!");
                        this.exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice..... Please try again.");
                        System.out.println("IPlease try again.");

                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                System.out.println("Please enter a valid input!");
            }
        }

    }



}
