package BPC_System.Models;

import java.util.List;
import java.util.Map;

public class PhysiotherapistsModel {

    private int id;
    private String name;
    private String address;
    private String phone;
    private Map<String, List<String>> expertiseArea;

    public PhysiotherapistsModel() {
    }

    public PhysiotherapistsModel(int id, String name, String address, String phone, Map<String, List<String>> expertiseArea) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.expertiseArea = expertiseArea;
    }

    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String fullName) {
        this.name = fullName;
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

    public Map<String, List<String>> getExpertiseAreas() {
        return this.expertiseArea;
    }
    public void setExpertiseAreas(Map<String, List<String>> expertiseAreas) {
        this.expertiseArea = expertiseAreas;
    }

}
