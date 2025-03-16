package BPC_System.Models;

public class TimetableModel {

    private int id;
    private int physioId;
    private int patientId;
    private String status;
    private String date;
    private String time;
    private String expertiseArea;
    private String treatment;

    public TimetableModel() {
    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getPhysioId() {
        return this.physioId;
    }
    public void setPhysioId(int physioId) {
        this.physioId = physioId;
    }

    public int getPatientId() {
        return this.patientId;
    }
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getExpertiseArea() {
        return this.expertiseArea;
    }
    public void setExpertiseArea(String expertiseArea) {
        this.expertiseArea = expertiseArea;
    }

    public String getTreatment() {
        return this.treatment;
    }
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

}
