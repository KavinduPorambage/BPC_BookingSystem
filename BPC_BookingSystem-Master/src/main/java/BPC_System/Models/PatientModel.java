package BPC_System.Models;

public class PatientModel {

    private int id;
    private String Name;
    private String address;
    private String phone;

    public PatientModel(int id, String name, String address, String phone) {
        this.id = id;
        this.Name = name;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.Name;
    }
    public void setName(String name) {
        this.Name = name;
    }

    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

}
