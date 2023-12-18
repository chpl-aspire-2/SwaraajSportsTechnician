package com.swaraajsports.technician.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class LocationResponse implements Serializable {

    @SerializedName("countries")
    @Expose
    private List<Country> countries = null;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("states")
    @Expose
    private List<State> states = null;


    @SerializedName("cities")
    @Expose
    private List<Cities> cities = null;


    @SerializedName("demo_status")
    @Expose
    private String demoStatus;

    @SerializedName("user_mobile")
    @Expose
    private String userMobile;

    @SerializedName("user_password")
    @Expose
    private String userPassword;

    @SerializedName("society_address")
    @Expose
    private String society_address;

    @SerializedName("society_latitude")
    @Expose
    private String society_latitude;

    @SerializedName("society_longitude")
    @Expose
    private String society_longitude;

    @SerializedName("society_name")
    @Expose
    private String society_name;

    @SerializedName("society_logo")
    @Expose
    private String society_logo;

    @SerializedName("sub_domain")
    @Expose
    private String subDomain;

    @SerializedName("api_key")
    @Expose
    private String apiKey;

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public List<Cities> getCities() {
        return cities;
    }

    public void setCities(List<Cities> cities) {
        this.cities = cities;
    }

    public String getSubDomain() {
        return subDomain;
    }

    public void setSubDomain(String subDomain) {
        this.subDomain = subDomain;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getDemoStatus() {
        return demoStatus;
    }

    public void setDemoStatus(String demoStatus) {
        this.demoStatus = demoStatus;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSociety_address() {
        return society_address;
    }

    public void setSociety_address(String society_address) {
        this.society_address = society_address;
    }

    public String getSociety_latitude() {
        return society_latitude;
    }

    public void setSociety_latitude(String society_latitude) {
        this.society_latitude = society_latitude;
    }

    public String getSociety_longitude() {
        return society_longitude;
    }

    public void setSociety_longitude(String society_longitude) {
        this.society_longitude = society_longitude;
    }

    public String getSociety_name() {
        return society_name;
    }

    public void setSociety_name(String society_name) {
        this.society_name = society_name;
    }

    public String getSociety_logo() {
        return society_logo;
    }

    public void setSociety_logo(String society_logo) {
        this.society_logo = society_logo;
    }

    public class Country {

        @SerializedName("country_id")
        @Expose
        private String countryId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("name_search")
        @Expose
        private String nameSearch;
        @SerializedName("iso3")
        @Expose
        private String iso3;
        @SerializedName("iso2")
        @Expose
        private String iso2;
        @SerializedName("phonecode")
        @Expose
        private String phonecode;
        @SerializedName("capital")
        @Expose
        private String capital;
        @SerializedName("currency")
        @Expose
        private String currency;

        @SerializedName("country_code")
        @Expose
        private String countryCode;

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIso3() {
            return iso3;
        }

        public void setIso3(String iso3) {
            this.iso3 = iso3;
        }

        public String getIso2() {
            return iso2;
        }

        public void setIso2(String iso2) {
            this.iso2 = iso2;
        }

        public String getPhonecode() {
            return phonecode;
        }

        public void setPhonecode(String phonecode) {
            this.phonecode = phonecode;
        }

        public String getCapital() {
            return capital;
        }

        public void setCapital(String capital) {
            this.capital = capital;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }


        public String getNameSearch() {
            return nameSearch;
        }

        public void setNameSearch(String nameSearch) {
            this.nameSearch = nameSearch;
        }
    }

    public class State {

        @SerializedName("state_id")
        @Expose
        private String stateId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("name_search")
        @Expose
        private String nameSearch;
        @SerializedName("country_id")
        @Expose
        private String countryId;

        public String getStateId() {
            return stateId;
        }

        public void setStateId(String stateId) {
            this.stateId = stateId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }


        public String getNameSearch() {
            return nameSearch;
        }

        public void setNameSearch(String nameSearch) {
            this.nameSearch = nameSearch;
        }
    }

    public class Cities {

        @SerializedName("city_id")
        @Expose
        private String city_id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("name_search")
        @Expose
        private String nameSearch;
        @SerializedName("state_id")
        @Expose
        private String state_id;

        @SerializedName("country_id")
        @Expose
        private String country_id;

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getState_id() {
            return state_id;
        }

        public void setState_id(String state_id) {
            this.state_id = state_id;
        }

        public String getCountry_id() {
            return country_id;
        }

        public void setCountry_id(String country_id) {
            this.country_id = country_id;
        }

        public String getNameSearch() {
            return nameSearch;
        }

        public void setNameSearch(String nameSearch) {
            this.nameSearch = nameSearch;
        }
    }
}
