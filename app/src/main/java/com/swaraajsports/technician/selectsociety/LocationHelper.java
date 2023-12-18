package com.swaraajsports.technician.selectsociety;

public class LocationHelper {

    public String name;
    public String nameSearch = "";
    public String id;
    public String cCode;


    public LocationHelper(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public LocationHelper(String name,String nameSearch, String id) {
        this.name = name;
        this.nameSearch = nameSearch;
        this.id = id;
    }

    public LocationHelper(String name, String nameSearch, String id, String cCode) {
        this.name = name;
        this.nameSearch = nameSearch;
        this.id = id;
        this.cCode = cCode;
    }
}
